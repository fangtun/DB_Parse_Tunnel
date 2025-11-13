package org.dromara.tunnelenvironmentmonitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelenvironmentmonitor.domain.*;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEnvEquipmentValueVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEquipmentVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TUnitGasAlarmlVo;
import org.dromara.tunnelenvironmentmonitor.service.IEnvironmentMonitorService;
import org.dromara.tunnelsecurityguarantee.domain.ApiRequest;
import org.dromara.tunnelsecurityguarantee.domain.ExternalApiProperties;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;
import org.dromara.tunnelsecurityguarantee.service.ISecurityGuaranteeService;
import org.dromara.tunnelsecurityguarantee.domain.vo.PileNoResultVo;
import org.dromara.tunnelsecurityguarantee.domain.vo.PileNoZResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tunnelenviroment")
public class EnvironmentMonitorController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalApiProperties externalApiProperties;

    private final ISecurityGuaranteeService securityGuaranteeService;

    private final IEnvironmentMonitorService environmentMonitorService;

    /**
     * 接收全量人员/车辆实时数据
     *
     * @param request 全量人员/车辆实时数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadLocationDataList")
    public R<Object> securityGuarantee(@Valid @RequestBody ApiRequest request) {
        // 洞内人员/车辆表分开时使用
        if (request.getData() != null && !request.getData().isEmpty()) {

            List<SecurityGuaranteeData> dataList = request.getData();

            // 左洞数据
            List<SecurityGuaranteeData> leftData = dataList.stream()
                .filter(data -> data.getRegionName().contains("左洞"))
                .toList();

            // 左洞桩号处理，以及xy坐标参数做成
            List<Map<String, String>> postLeftData = new ArrayList<>();
            for (SecurityGuaranteeData data : leftData) {
                String pileNo = data.getPileNo();
                if (pileNo != null && !pileNo.isEmpty()) {
                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
                    postLeftData.add(Map.of(
                        "stake", stakeValue.toPlainString(),
                        "type_name", "DLGS_60-04.0003.00.00_1"
                    ));
                }
            }

            // 调用外部接口获取左洞xy坐标数据
            ResponseEntity<List<PileNoResultVo>> pmResponse = restTemplate.exchange(
                externalApiProperties.getPm().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postLeftData),
                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
            );

            // 获取左洞xy坐标返回值
            List<PileNoResultVo> pmResults = pmResponse.getBody();

            if (pmResults == null) {
                pmResults = Collections.emptyList();  // 设置为空列表避免空指针
            }

            // 调用外部接口获取左洞z坐标数据
            ResponseEntity<List<PileNoZResultVo>> zdczResponse = restTemplate.exchange(
                externalApiProperties.getZdcz().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postLeftData),
                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
            );

            // 获取左洞z坐标返回值
            List<PileNoZResultVo> zResults = zdczResponse.getBody();


            // 将 x, y ,z 设置回 SecurityGuaranteeData
            for (int i = 0; i < Math.min(leftData.size(), pmResults.size()); i++) {
                SecurityGuaranteeData data = leftData.get(i);
                PileNoResultVo result = pmResults.get(i);
                PileNoZResultVo zResult = zResults.size() > i ? zResults.get(i) : null;

                data.setCoordinatex(result.getX());
                data.setCoordinatey(result.getY());
                if (zResult != null) {
                    data.setCoordinatez(zResult.getJg());
                }
            }

            // 右洞数据
            List<SecurityGuaranteeData> rightData = dataList.stream()
                .filter(data -> data.getRegionName().contains("右洞"))
                .toList();

            // 右洞桩号处理，以及xy坐标参数做成
            List<Map<String, String>> postRightData = new ArrayList<>();
            for (SecurityGuaranteeData data : rightData) {
                String pileNo = data.getPileNo();
                if (pileNo != null && !pileNo.isEmpty()) {
                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
                    postRightData.add(Map.of(
                        "stake", stakeValue.toPlainString(),
                        "type_name", "DLGS_60-04.0003.00.00_2"
                    ));
                }
            }

            // 调用外部接口获取右洞xy坐标数据
            ResponseEntity<List<PileNoResultVo>> pmResponseRight = restTemplate.exchange(
                externalApiProperties.getPm().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postRightData),
                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
            );

            // 获取右洞xy坐标数据
            List<PileNoResultVo> pmResultsRight = pmResponseRight.getBody();

            if (pmResultsRight == null) {
                pmResultsRight = Collections.emptyList();  // 设置为空列表避免空指针
            }

            // 调用外部接口获取右洞z坐标数据
            ResponseEntity<List<PileNoZResultVo>> zdczResponseRight = restTemplate.exchange(
                externalApiProperties.getZdcz().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postRightData),
                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
            );

            // 获取右洞z坐标数据
            List<PileNoZResultVo> zResultsRight = zdczResponseRight.getBody();


            // 将 x, y,z 设置回 SecurityGuaranteeData
            for (int i = 0; i < Math.min(rightData.size(), pmResultsRight.size()); i++) {
                SecurityGuaranteeData data = rightData.get(i);
                PileNoResultVo result = pmResultsRight.get(i);
                PileNoZResultVo zResult = zResultsRight.size() > i ? zResultsRight.get(i) : null;

                data.setCoordinatex(result.getX());
                data.setCoordinatey(result.getY());
                if (zResult != null) {
                    data.setCoordinatez(zResult.getJg());
                }
            }

//            request.getData().forEach(locationData -> {
//                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
//                    securityGuaranteeService.insertPerson(locationData);
//                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
//                    securityGuaranteeService.insertCars(locationData);
//                }
//            });
            rightData.forEach(locationData -> {
                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
                    securityGuaranteeService.insertPerson(locationData);
                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
                    securityGuaranteeService.insertCars(locationData);
                }
            });
            leftData.forEach(locationData -> {
                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
                    securityGuaranteeService.insertPerson(locationData);
                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
                    securityGuaranteeService.insertCars(locationData);
                }
            });
        }
        //洞内人员车辆在一张表时使用
//        if (request.getData() != null && !request.getData().isEmpty()) {
//            request.getData().forEach(securityGuaranteeService::insertPersonAndCars);
//        }
        return R.ok("success");
    }

    public BigDecimal calculateProcessedPileNo(String pileNo) {
        // 正则表达式：匹配 K 或 ZK 开头，后接整数+小数部分
        String regex = "^(?:ZK|K)(\\d+)\\+(\\d+\\.?\\d*)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pileNo);

        if (matcher.matches()) {
            String mainPart = matcher.group(1);
            String decimalPart = matcher.group(2);

            BigDecimal main = new BigDecimal(mainPart);
            BigDecimal decimal = new BigDecimal(decimalPart);

            return main.multiply(BigDecimal.valueOf(1000)).add(decimal);
        } else {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 接收推送设备基础信息
     *
     * @param request 设备基础信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadDeviceInfo")
    public R<Object> deviceInfo(@Valid @RequestBody DeviceInfoApiRequest request) {
        if (request.getData() != null && !request.getData().isEmpty()) {
            request.getData().stream()
                .filter(data -> data != null && "10210".equals(data.getDeviceType().toString()))
                .forEach(environmentMonitorService::insertDeviceData);
        }
        return R.ok("success");
    }

    /**
     * 接收全量实时气体数据
     *
     * @param request 全量实时气体数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadSensorDataList")
    public R<Object> environmentMonitor(@Valid @RequestBody EnvApiRequest request) {
        if (request.getData() != null && !request.getData().isEmpty()) {
            request.getData().forEach(environmentMonitorService::insertEnvironmentData);
        }
        return R.ok("success");
    }

    /**
     * 接收气体报警数据
     *
     * @param request 气体报警数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadSensorAlarm")
    public R<Object> environmentMonitorAlarm(@Valid @RequestBody EnvAlarmApiRequest request) {
        if (request.getData() != null && !request.getData().isEmpty()) {
            request.getData().forEach(environmentMonitorService::insertEnvironmentAlarmData);
        }
        return R.ok("success");
    }

    /**
     * 接收推送实时台车位置数据
     *
     * @param request 实时台车位置数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadTrolleyPosition")
    public R<Object> trolleyPosition(@Valid @RequestBody TrolleyRequest request) {
        if (request.getData() != null && !request.getData().isEmpty()) {

            List<TrolleyData> dataList = request.getData();

            // 左洞数据
            List<TrolleyData> leftData = dataList.stream()
                .filter(data -> data.getRegionName().contains("左洞"))
                .toList();

            // 左洞桩号处理，以及xy坐标参数做成
            List<Map<String, String>> postLeftData = new ArrayList<>();
            for (TrolleyData data : leftData) {
                String pileNo = data.getPileNo();
                if (pileNo != null && !pileNo.isEmpty()) {
                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
                    postLeftData.add(Map.of(
                        "stake", stakeValue.toPlainString(),
                        "type_name", "DLGS_60-04.0003.00.00_1"
                    ));
                }
            }

            // 调用外部接口获取左洞xy坐标数据
            ResponseEntity<List<PileNoResultVo>> pmResponse = restTemplate.exchange(
                externalApiProperties.getPm().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postLeftData),
                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
            );

            // 获取左洞xy坐标返回值
            List<PileNoResultVo> pmResults = pmResponse.getBody();

            if (pmResults == null) {
                pmResults = Collections.emptyList();  // 设置为空列表避免空指针
            }

            // 调用外部接口获取左洞z坐标数据
            ResponseEntity<List<PileNoZResultVo>> zdczResponse = restTemplate.exchange(
                externalApiProperties.getZdcz().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postLeftData),
                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
            );

            // 获取左洞z坐标返回值
            List<PileNoZResultVo> zResults = zdczResponse.getBody();


            // 将 x, y ,z 设置回 SecurityGuaranteeData
            for (int i = 0; i < Math.min(leftData.size(), pmResults.size()); i++) {
                TrolleyData data = leftData.get(i);
                PileNoResultVo result = pmResults.get(i);
                PileNoZResultVo zResult = zResults.size() > i ? zResults.get(i) : null;

                data.setCoordinatex(result.getX());
                data.setCoordinatey(result.getY());
                if (zResult != null) {
                    data.setCoordinatez(zResult.getJg());
                }
            }

            // 右洞数据
            List<TrolleyData> rightData = dataList.stream()
                .filter(data -> data.getRegionName().contains("右洞"))
                .toList();

            // 右洞桩号处理，以及xy坐标参数做成
            List<Map<String, String>> postRightData = new ArrayList<>();
            for (TrolleyData data : rightData) {
                String pileNo = data.getPileNo();
                if (pileNo != null && !pileNo.isEmpty()) {
                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
                    postRightData.add(Map.of(
                        "stake", stakeValue.toPlainString(),
                        "type_name", "DLGS_60-04.0003.00.00_2"
                    ));
                }
            }

            // 调用外部接口获取右洞xy坐标数据
            ResponseEntity<List<PileNoResultVo>> pmResponseRight = restTemplate.exchange(
                externalApiProperties.getPm().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postRightData),
                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
            );

            // 获取右洞xy坐标数据
            List<PileNoResultVo> pmResultsRight = pmResponseRight.getBody();

            if (pmResultsRight == null) {
                pmResultsRight = Collections.emptyList();  // 设置为空列表避免空指针
            }

            // 调用外部接口获取右洞z坐标数据
            ResponseEntity<List<PileNoZResultVo>> zdczResponseRight = restTemplate.exchange(
                externalApiProperties.getZdcz().getUrl(),
                HttpMethod.POST,
                new HttpEntity<>(postRightData),
                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
            );

            // 获取右洞z坐标数据
            List<PileNoZResultVo> zResultsRight = zdczResponseRight.getBody();


            // 将 x, y,z 设置回 SecurityGuaranteeData
            for (int i = 0; i < Math.min(rightData.size(), pmResultsRight.size()); i++) {
                TrolleyData data = rightData.get(i);
                PileNoResultVo result = pmResultsRight.get(i);
                PileNoZResultVo zResult = zResultsRight.size() > i ? zResultsRight.get(i) : null;

                data.setCoordinatex(result.getX());
                data.setCoordinatey(result.getY());
                if (zResult != null) {
                    data.setCoordinatez(zResult.getJg());
                }
            }

            rightData.forEach(environmentMonitorService::updateTrolleyPositionData);
            leftData.forEach(environmentMonitorService::updateTrolleyPositionData);
        }

        return R.ok("success");
    }

    /**
     * 获取气体设备状态
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getEnvironmentMonitorStatus")
    public R<Map<String, Integer>> getEnvironmentMonitorStatus(String projectCode, String unitCode) {
        return R.ok(environmentMonitorService.getDeviceStatusCount(projectCode, unitCode));
    }

    /**
     * 获取气体设备列表(有害气体+粉尘)
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getEnvironmentMonitorList")
    public TableDataInfo<TEquipmentVo> getEnvironmentMonitorList(String projectCode, String unitCode) {
        return environmentMonitorService.getDeviceList(projectCode, unitCode);
    }

    /**
     * 获取粉尘设备列表
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getDustDeviceList")
    public R<List<Map<String, Object>>> getDustDeviceList(String projectCode, String unitCode) {
        return R.ok(environmentMonitorService.getDustDeviceList(projectCode, unitCode));
    }



    /**
     * 获取气体设备数值列表-天
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getEnvironmentMonitorValueList")
    public TableDataInfo<TEnvEquipmentValueVo> getEnvironmentMonitorValueList(String projectCode, String unitCode, String deviceSn,String gasType) {
        return environmentMonitorService.getDeviceValueList(projectCode, unitCode,deviceSn,gasType);
    }

    /**
     * 获取气体设备数值列表-小时
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getEnvironmentValueByHourList")
    public TableDataInfo<TEnvEquipmentValueVo> getEnvironmentValueByHourList(String projectCode, String unitCode, String deviceSn,String gasType) {
        return environmentMonitorService.getDeviceValueByHourList(projectCode, unitCode,deviceSn,gasType);
    }

    /**
     * 获取气体报警列表
     *
     * @return 状态
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:warning:list")
    @GetMapping("/getEnvironmentAlarmValueList")
    public TableDataInfo<TUnitGasAlarmlVo> getEnvironmentAlarmValueList(String projectCode, String unitCode) {
        return environmentMonitorService.getEnvironmentAlarmValueList(projectCode, unitCode);
    }

    /**
     * 获取粉尘实时浓度
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getDustNowValueList")
    public R<List<Map<String, Object>>>  getDustNowValueList(String projectCode, String unitCode, String deviceSn) {
        return R.ok(environmentMonitorService.getDustNowValueList(projectCode, unitCode,deviceSn));
    }

    /**
     * 获取粉尘当日平均浓度
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getDustDayAvgValueList")
    public R<List<Map<String, Object>>>  getDustDayAvgValueList(String projectCode, String unitCode, String deviceSn) {
        return R.ok(environmentMonitorService.getDustDayAvgValueList(projectCode, unitCode,deviceSn));
    }

    /**
     * 获取粉尘七日平均浓度
     *
     * @return 状态
     */
    @SaIgnore
    @GetMapping("/getDustWeekAvgValueList")
    public R<List<Map<String, Object>>>  getDustWeekAvgValueList(String projectCode, String unitCode, String deviceSn) {
        return R.ok(environmentMonitorService.getDustWeekAvgValueList(projectCode, unitCode,deviceSn));
    }
}

package org.dromara.tunnelsecurityguarantee.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.tunnelsecurityguarantee.domain.ApiRequest;
import org.dromara.tunnelsecurityguarantee.domain.ExternalApiProperties;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;
import org.dromara.tunnelsecurityguarantee.domain.vo.*;
import org.dromara.tunnelsecurityguarantee.service.ISecurityGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.dromara.common.core.domain.R;
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
@RequestMapping("/tunnelguarantee")
public class SecurityGuaranteeController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExternalApiProperties externalApiProperties;

    private final ISecurityGuaranteeService securityGuaranteeService;

//    /**
//     * 接收全量人员/车辆实时数据
//     *
//     * @param request 全量人员/车辆实时数据
//     * @return 结果
//     */
//    @SaIgnore
//    @PostMapping("/uploadLocationDataList")
//    public R<Object> securityGuarantee(@Valid @RequestBody ApiRequest request) {
//        // 洞内人员/车辆表分开时使用
//        if (request.getData() != null && !request.getData().isEmpty()) {
//
//            List<SecurityGuaranteeData> dataList = request.getData();
//
//            // 左洞数据
//            List<SecurityGuaranteeData> leftData = dataList.stream()
//                .filter(data -> data.getRegionName().contains("左洞"))
//                .toList();
//
//            // 左洞桩号处理，以及xy坐标参数做成
//            List<Map<String, String>> postLeftData = new ArrayList<>();
//            for (SecurityGuaranteeData data : leftData) {
//                String pileNo = data.getPileNo();
//                if (pileNo != null && !pileNo.isEmpty()) {
//                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
//                    postLeftData.add(Map.of(
//                        "stake", stakeValue.toPlainString(),
//                        "type_name", "DLGS_60-04.0003.00.00_1"
//                    ));
//                }
//            }
//
//            // 调用外部接口获取左洞xy坐标数据
//            ResponseEntity<List<PileNoResultVo>> pmResponse = restTemplate.exchange(
//                externalApiProperties.getPm().getUrl(),
//                HttpMethod.POST,
//                new HttpEntity<>(postLeftData),
//                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
//            );
//
//            // 获取左洞xy坐标返回值
//            List<PileNoResultVo> pmResults = pmResponse.getBody();
//
//            if (pmResults == null) {
//                pmResults = Collections.emptyList();  // 设置为空列表避免空指针
//            }
//
//            // 调用外部接口获取左洞z坐标数据
//            ResponseEntity<List<PileNoZResultVo>> zdczResponse = restTemplate.exchange(
//                externalApiProperties.getZdcz().getUrl(),
//                HttpMethod.POST,
//                new HttpEntity<>(postLeftData),
//                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
//            );
//
//            // 获取左洞z坐标返回值
//            List<PileNoZResultVo> zResults = zdczResponse.getBody();
//
//
//            // 将 x, y ,z 设置回 SecurityGuaranteeData
//            for (int i = 0; i < Math.min(leftData.size(), pmResults.size()); i++) {
//                SecurityGuaranteeData data = leftData.get(i);
//                PileNoResultVo result = pmResults.get(i);
//                PileNoZResultVo zResult = zResults.size() > i ? zResults.get(i) : null;
//
//                data.setCoordinatex(result.getX());
//                data.setCoordinatey(result.getY());
//                if (zResult != null) {
//                    data.setCoordinatez(zResult.getJg());
//                }
//            }
//
//            // 右洞数据
//            List<SecurityGuaranteeData> rightData = dataList.stream()
//                .filter(data -> data.getRegionName().contains("右洞"))
//                .toList();
//
//            // 右洞桩号处理，以及xy坐标参数做成
//            List<Map<String, String>> postRightData = new ArrayList<>();
//            for (SecurityGuaranteeData data : rightData) {
//                String pileNo = data.getPileNo();
//                if (pileNo != null && !pileNo.isEmpty()) {
//                    BigDecimal stakeValue = calculateProcessedPileNo(pileNo);
//                    postRightData.add(Map.of(
//                        "stake", stakeValue.toPlainString(),
//                        "type_name", "DLGS_60-04.0003.00.00_2"
//                    ));
//                }
//            }
//
//            // 调用外部接口获取右洞xy坐标数据
//            ResponseEntity<List<PileNoResultVo>> pmResponseRight = restTemplate.exchange(
//                externalApiProperties.getPm().getUrl(),
//                HttpMethod.POST,
//                new HttpEntity<>(postRightData),
//                new ParameterizedTypeReference<List<PileNoResultVo>>() {}
//            );
//
//            // 获取右洞xy坐标数据
//            List<PileNoResultVo> pmResultsRight = pmResponseRight.getBody();
//
//            if (pmResultsRight == null) {
//                pmResultsRight = Collections.emptyList();  // 设置为空列表避免空指针
//            }
//
//            // 调用外部接口获取右洞z坐标数据
//            ResponseEntity<List<PileNoZResultVo>> zdczResponseRight = restTemplate.exchange(
//                externalApiProperties.getZdcz().getUrl(),
//                HttpMethod.POST,
//                new HttpEntity<>(postRightData),
//                new ParameterizedTypeReference<List<PileNoZResultVo>>() {}
//            );
//
//            // 获取右洞z坐标数据
//            List<PileNoZResultVo> zResultsRight = zdczResponseRight.getBody();
//
//
//            // 将 x, y,z 设置回 SecurityGuaranteeData
//            for (int i = 0; i < Math.min(rightData.size(), pmResultsRight.size()); i++) {
//                SecurityGuaranteeData data = rightData.get(i);
//                PileNoResultVo result = pmResultsRight.get(i);
//                PileNoZResultVo zResult = zResultsRight.size() > i ? zResultsRight.get(i) : null;
//
//                data.setCoordinatex(result.getX());
//                data.setCoordinatey(result.getY());
//                if (zResult != null) {
//                    data.setCoordinatez(zResult.getJg());
//                }
//            }
//
////            request.getData().forEach(locationData -> {
////                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
////                    securityGuaranteeService.insertPerson(locationData);
////                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
////                    securityGuaranteeService.insertCars(locationData);
////                }
////            });
//            rightData.forEach(locationData -> {
//                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
//                    securityGuaranteeService.insertPerson(locationData);
//                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
//                    securityGuaranteeService.insertCars(locationData);
//                }
//            });
//            leftData.forEach(locationData -> {
//                if (locationData.getCardType().equals(10510)) { //卡类型，10510：人员
//                    securityGuaranteeService.insertPerson(locationData);
//                } else if (locationData.getCardType().equals(10520)) { //卡类型，10520：车辆
//                    securityGuaranteeService.insertCars(locationData);
//                }
//            });
//        }
//        //洞内人员车辆在一张表时使用
////        if (request.getData() != null && !request.getData().isEmpty()) {
////            request.getData().forEach(securityGuaranteeService::insertPersonAndCars);
////        }
//        return R.ok("success");
//    }
//
//    public BigDecimal calculateProcessedPileNo(String pileNo) {
//        // 正则表达式：匹配 K 或 ZK 开头，后接整数+小数部分
//        String regex = "^(?:ZK|K)(\\d+)\\+(\\d+\\.?\\d*)$";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(pileNo);
//
//        if (matcher.matches()) {
//            String mainPart = matcher.group(1);
//            String decimalPart = matcher.group(2);
//
//            BigDecimal main = new BigDecimal(mainPart);
//            BigDecimal decimal = new BigDecimal(decimalPart);
//
//            return main.multiply(BigDecimal.valueOf(1000)).add(decimal);
//        } else {
//            return BigDecimal.ZERO;
//        }
//    }

    /**
     * 获取洞内人员车辆列表
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
    @SaIgnore
    @GetMapping("/personCarsListInTunnel")
    public TableDataInfo<PersonCarsListInTunnelVo> personCarsListInTunnel(String projectCode, String unitCode) {
        return securityGuaranteeService.getPersonCarsListInTunnel(projectCode, unitCode);
    }


    /**
     * 获取洞内人员列表
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:person:list")
    @GetMapping("/personListInTunnel")
    public TableDataInfo<PersonListInTunnelVo> personListInTunnel(String projectCode, String unitCode) {
        return securityGuaranteeService.getPersonListInTunnel(projectCode, unitCode);
    }

    /**
     * 获取洞内车辆列表
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:cars:list")
    @GetMapping("/carsListInTunnel")
    public TableDataInfo<CarsListInTunnelVo> carsListInTunnel(String projectCode, String unitCode) {
        return securityGuaranteeService.getCarsListInTunnel(projectCode, unitCode);
    }

}

package org.dromara.tunnelintelligentconstruction.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginBody;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelintelligentconstruction.domain.TLargeScaleMachinery;
import org.dromara.tunnelintelligentconstruction.domain.TLargeScaleMachineryData;
import org.dromara.tunnelintelligentconstruction.domain.TLargeScaleMachineryStatus;
import org.dromara.tunnelintelligentconstruction.domain.TLargeScaleMachineryWorktime;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryDataVo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryVo;
import org.dromara.tunnelintelligentconstruction.mapper.LargeScaleMachineryDataMapper;
import org.dromara.tunnelintelligentconstruction.mapper.LargeScaleMachineryMapper;
import org.dromara.tunnelintelligentconstruction.mapper.LargeScaleMachineryStatusMapper;
import org.dromara.tunnelintelligentconstruction.mapper.LargeScaleMachineryWorkTimeMapper;
import org.dromara.tunnelintelligentconstruction.service.ILargeScaleMachineryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.dromara.common.json.utils.JsonUtils;

import java.util.*;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class LargeScaleMachineryServiceImpl implements ILargeScaleMachineryService {

    @Autowired
    private LargeScaleMachineryMapper largeScaleMachineryMapper;

    @Autowired
    private LargeScaleMachineryDataMapper largeScaleMachineryDataMapper;

    @Autowired
    private LargeScaleMachineryWorkTimeMapper largeScaleMachineryWorkTimeMapperMapper;

    @Autowired
    private LargeScaleMachineryStatusMapper largeScaleMachineryStatusMapper;

    /**
     * 新增养护台车设备信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLargeScaleMachinery(String decodedStr, String devicesn) {
        TLargeScaleMachinery InfoData = new TLargeScaleMachinery();
        Date nowDate = DateUtils.getNowDate();

        if (devicesn.startsWith("002")) {
            InfoData.setName("喷淋养护台车");
            InfoData.setEquipmenttype(Long.valueOf("0"));
        } else if (devicesn.startsWith("003")) {
            InfoData.setName("蒸汽台车");
            InfoData.setEquipmenttype(Long.valueOf("1"));
        }
        InfoData.setDevicesn(devicesn);
        InfoData.setStatus(Long.valueOf("1")); //在线
        if (decodedStr.equals("offline")) {
            InfoData.setStatus(Long.valueOf("0")); //离线
        }
        InfoData.setProjcode("DLGS");
        InfoData.setUnitcode("60-04.0003.00.00");
        InfoData.setCreationtime(nowDate);

        TLargeScaleMachinery existing = largeScaleMachineryMapper.selectOne(new LambdaQueryWrapper<TLargeScaleMachinery>().eq(TLargeScaleMachinery::getDevicesn, devicesn));

        int rows = 0;

        if (existing == null) {
            // 新增大机设备信息
            rows = largeScaleMachineryMapper.insert(InfoData);
        } else {
            // 数据存在，更新信息
            InfoData.setId(existing.getId());
            InfoData.setCreationtime(existing.getCreationtime());
            InfoData.setLastmodificationtime(nowDate);
            rows = largeScaleMachineryMapper.updateById(InfoData);

        }

        //新增状态记录数据
        TLargeScaleMachineryStatus StatusInfoData = new TLargeScaleMachineryStatus();
        StatusInfoData.setDevicesn(devicesn);
        StatusInfoData.setStatus(Long.valueOf("1"));
        if (decodedStr.equals("offline")) {
            StatusInfoData.setStatus(Long.valueOf("0")); //离线
        }
        StatusInfoData.setCreationtime(nowDate);
        rows = largeScaleMachineryStatusMapper.insert(StatusInfoData);


//        return rows;
    }

    /**
     * 新增养护台车数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLargeScaleMachineryData(String decodedStr) {
        // 解析JSON字符串
        JSONObject jsonObject = new JSONObject(decodedStr);

        if (jsonObject.get("command").equals("screen_update_sensor_data")) {
            String devicesn = jsonObject.get("device_random_code").toString();
            // 获取时间戳并转换为Date对象
            Long timestamp = jsonObject.getLong("timestamp");
            Date recordTime = new Date(timestamp * 1000); // 转换秒为毫秒

            // 获取数据部分
            JSONObject data = jsonObject.getJSONObject("data");

            // 获取温度数据
            JSONObject temperatureData = data.getJSONObject("temperature");
            // 获取湿度数据
            JSONObject humidityData = data.getJSONObject("humidity");

            // 处理温度数据
            if (temperatureData != null) {
                for (String sensorName : temperatureData.keySet()) {
                    TLargeScaleMachineryData InfoData = new TLargeScaleMachineryData();
                    String temperatureValue = temperatureData.getStr(sensorName);
                    InfoData.setMetrickey(sensorName);
                    InfoData.setMetricvalue(temperatureValue);
                    InfoData.setDevicesn(devicesn);
                    InfoData.setCreationtime(recordTime);
                    largeScaleMachineryDataMapper.insert(InfoData);
                }
            }

            // 处理湿度数据
            if (humidityData != null) {
                for (String sensorName : humidityData.keySet()) {
                    TLargeScaleMachineryData InfoData = new TLargeScaleMachineryData();
                    String humidityValue = humidityData.getStr(sensorName);
                    InfoData.setMetrickey(sensorName);
                    InfoData.setMetricvalue(humidityValue);
                    InfoData.setDevicesn(devicesn);
                    InfoData.setCreationtime(recordTime);
                    largeScaleMachineryDataMapper.insert(InfoData);
                }
            }

        } else if (jsonObject.get("command").equals("screen_update_water_tank_status")) {//水箱状态 （每次水箱缺水情况变化时触发，默认左右水箱均不缺水）
            TLargeScaleMachinery InfoData = new TLargeScaleMachinery();
            String devicesn = jsonObject.get("device_random_code").toString();
            String side = jsonObject.get("side").toString();
            String status = jsonObject.get("low_water").toString();
            TLargeScaleMachinery existing = largeScaleMachineryMapper.selectOne(new LambdaQueryWrapper<TLargeScaleMachinery>().eq(TLargeScaleMachinery::getDevicesn, devicesn));
            int rows = 0;
            InfoData.setId(existing.getId());
            if (side.equals("left")) {
                InfoData.setLeftlowwater(status.equals("True") ? Long.valueOf("1") : Long.valueOf("0")); //缺水True 不缺水False  水箱缺水状态(0:正常，1:缺水)
            } else {
                InfoData.setRightlowwater(status.equals("True") ? Long.valueOf("1") : Long.valueOf("0")); //缺水True 不缺水False  水箱缺水状态(0:正常，1:缺水)
            }
            InfoData.setLastmodificationtime(DateUtils.getNowDate());
            rows = largeScaleMachineryMapper.updateById(InfoData);
        } else if (jsonObject.get("command").equals("screen_update_dolly_state")) {//养护状态
            TLargeScaleMachineryWorktime InfoData = new TLargeScaleMachineryWorktime();
            String devicesn = jsonObject.get("device_random_code").toString();
            String status = jsonObject.get("data").toString();
            Long timestamp = jsonObject.getLong("timestamp");
            Date recordTime = new Date(timestamp * 1000); // 转换秒为毫秒
            InfoData.setDevicesn(devicesn);
            InfoData.setStatus(status.equals("True") ? Long.valueOf("0") : Long.valueOf("1"));//True:开始 False:停止  0开始 1结束
            InfoData.setTimestamp(recordTime);
            InfoData.setCreationtime(DateUtils.getNowDate());
            int rows = 0;
            rows = largeScaleMachineryWorkTimeMapperMapper.insert(InfoData);
        }

        int rows = 0;

    }


    /**
     * 新增钻注锚一体机设备信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDrillingAnchorDevice(Map<String, Object> requestData) {
        TLargeScaleMachinery InfoData = new TLargeScaleMachinery();
        Date nowDate = DateUtils.getNowDate();
        InfoData.setName("钻注锚一体机");
        InfoData.setEquipmenttype(Long.valueOf("2"));
        InfoData.setDevicesn(requestData.get("deviceSn").toString());
        InfoData.setStatus(Long.valueOf("1")); //在线
        if (requestData.get("status").toString().equals("1")) {// 传入的数据中 0：开始工作  1:结束工作
            InfoData.setStatus(Long.valueOf("0")); //离线
        }
        InfoData.setProjcode("DLGS");
        InfoData.setUnitcode("60-04.0003.00.00");
        InfoData.setCreationtime(nowDate);

        TLargeScaleMachinery existing = largeScaleMachineryMapper.selectOne(new LambdaQueryWrapper<TLargeScaleMachinery>().eq(TLargeScaleMachinery::getDevicesn, requestData.get("deviceSn").toString()));

        int rows = 0;

        if (existing == null) {
            // 新增大机设备信息
            rows = largeScaleMachineryMapper.insert(InfoData);
        } else {
            // 数据存在，更新信息
            InfoData.setId(existing.getId());
            InfoData.setCreationtime(existing.getCreationtime());
            InfoData.setLastmodificationtime(nowDate);
            rows = largeScaleMachineryMapper.updateById(InfoData);

        }

        //新增状态记录数据
        TLargeScaleMachineryStatus StatusInfoData = new TLargeScaleMachineryStatus();
        StatusInfoData.setDevicesn(requestData.get("deviceSn").toString());
        StatusInfoData.setStatus(Long.valueOf("1"));
        if (requestData.get("status").toString().equals("1")) {// 传入的数据中 0：开始工作  1:结束工作
            InfoData.setStatus(Long.valueOf("0")); //离线
        }
        StatusInfoData.setCreationtime(nowDate);
        rows = largeScaleMachineryStatusMapper.insert(StatusInfoData);


        return rows;
    }

    /**
     * 钻注锚点传感器数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveDrillingAnchorSensorData(Map<String, Object> requestData) {

        int rows = 0;
        try {
            String devicesn = requestData.get("deviceSn").toString();
            Date updateTime = (Date) requestData.get("updateTime");

            // 获取data部分并进行类型检查
            Object dataObj = requestData.get("data");
            if (dataObj instanceof Map) {
                Map<String, Object> data = (Map<String, Object>) dataObj;

                // 循环处理data中的每个键值对
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    String sensorName = entry.getKey();
                    Object valueObj = entry.getValue();
                    String sensorValue = (valueObj != null) ? valueObj.toString() : "";

                    TLargeScaleMachineryData infoData = new TLargeScaleMachineryData();
                    infoData.setMetrickey(sensorName);
                    infoData.setMetricvalue(sensorValue);
                    infoData.setDevicesn(devicesn);
                    infoData.setCreationtime(updateTime);

                    rows += largeScaleMachineryDataMapper.insert(infoData);
                }
            }
        } catch (Exception e) {
            log.error("处理钻注锚传感器数据异常", e);
            throw new RuntimeException("数据处理失败", e);
        }

        return rows;

    }

    /**
     * 查询 大机设备信息
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 结果
     */
    @Override
    public TableDataInfo<LargeScaleMachineryVo> getLargeScaleMachineryList(String projectCode, String unitCode) {

        // 查询设备信息
        List<LargeScaleMachineryVo> machineryList = largeScaleMachineryMapper.selectLargeScaleMachineryList(projectCode, unitCode);


        // 转换为VO对象
        List<LargeScaleMachineryVo> voList = new ArrayList<>();
        for (LargeScaleMachineryVo machineryData : machineryList) {
            LargeScaleMachineryVo vo = new LargeScaleMachineryVo();
            vo.setName(machineryData.getName());
            vo.setStatus(machineryData.getStatus());
            vo.setDevicesn(machineryData.getDevicesn());
            vo.setPileNo(machineryData.getPileNo());
            vo.setDirection(machineryData.getDirection() != null ? (machineryData.getDirection().equals("1") ? "左洞" : "右洞") : "");
            LargeScaleMachineryVo.Location location = new LargeScaleMachineryVo.Location(machineryData.getCoordinatex(),machineryData.getCoordinatey(),machineryData.getCoordinatez());
            vo.setLocation( location);

            // 改进的判空处理
            String runDuration = machineryData.getRunduration();
            if (runDuration != null && !runDuration.trim().isEmpty() && !runDuration.equals("null")) {
                try {
                    Long seconds = Long.valueOf(runDuration);
                    vo.setRunduration(formatDuration(seconds));
                } catch (NumberFormatException e) {
                    // 如果转换失败，设置默认值
                    vo.setRunduration("-");
                }
            } else {
                vo.setRunduration("-");
            }

            voList.add(vo);
        }

        return TableDataInfo.build(voList);
    }

    /**
     * 将秒数转换为可读的时间格式
     *
     * @param seconds 秒数
     * @return 格式化后的时间字符串
     */
    private String formatDuration(Long seconds) {
        if (seconds == null || seconds <= 0) {
            return "-";
        }

        long days = seconds / (24 * 3600);
        long hours = (seconds % (24 * 3600)) / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append("天");
        }
        if (hours > 0) {
            sb.append(hours).append("小时");
        }
        if (minutes > 0) {
            sb.append(minutes).append("分钟");
        }
        if (secs > 0 && days == 0 && hours == 0) { // 只有在没有天和小时时才显示秒
            sb.append(secs).append("秒");
        }

        // 如果所有单位都是0
        if (sb.length() == 0) {
            sb.append("0分钟");
        }

        return sb.toString();
    }

    /**
     * 查询 大机设备设备数据
     *
     * @param deviceSn 设备编码
     * @return 结果
     */
    @Override
    public List<Map<String, Object>> getLargeScaleMachineryDataList(String deviceSn) {

        // 查询设备数据
        List<LargeScaleMachineryDataVo> machineryDataList = largeScaleMachineryMapper.selectLargeScaleMachineryDataList(deviceSn);

        Map<String, Object> info = new HashMap<>();
        List<Map<String, Object>> dataList = new ArrayList<>();

        if (machineryDataList != null && !machineryDataList.isEmpty()) {
            LargeScaleMachineryDataVo firstMachineryData = machineryDataList.get(0);

            info.put("name", firstMachineryData.getName());
            info.put("devicesn", firstMachineryData.getDevicesn());
            info.put("dollystate", firstMachineryData.getDollystate());

            Long seconds = Long.valueOf(firstMachineryData.getWorkingtime());
            info.put("workingtime", formatDuration(seconds));
            String tankStatus = "";
            if (firstMachineryData.getLeftwataertankStatus().equals("1") && firstMachineryData.getRightwataertankStatus().equals("1")) {
                tankStatus = "两侧缺水";
            } else if (firstMachineryData.getLeftwataertankStatus().equals("1") && firstMachineryData.getRightwataertankStatus().equals("0")) {
                tankStatus = "左侧缺水";
            } else if (firstMachineryData.getLeftwataertankStatus().equals("0") && firstMachineryData.getRightwataertankStatus().equals("1")) {
                tankStatus = "右侧缺水";
            } else if (firstMachineryData.getLeftwataertankStatus().equals("0") && firstMachineryData.getRightwataertankStatus().equals("0")) {
                tankStatus = "不缺水";
            }
            info.put("watertankstatus", tankStatus);

            info.put("pileno", "");

            Map<String, Map<String, String>> machinerydata = new HashMap<>();

            Map<String, String> temperatureData = new LinkedHashMap<>();
            Map<String, String> humidityData = new LinkedHashMap<>();


            for (LargeScaleMachineryDataVo machineryDataData : machineryDataList) {

                String metricKey = machineryDataData.getMetrickey();
                String metricValue = machineryDataData.getMetricvalue();

                if (machineryDataData.getMetrickey().contains("温感")) {
                    temperatureData.put(metricKey, metricValue);
                } else if (machineryDataData.getMetrickey().contains("湿感")) {
                    humidityData.put(metricKey, metricValue);
                }

            }

            // 将传感器数据放入data map中
            if (!temperatureData.isEmpty()) {
                machinerydata.put("temperature", temperatureData);
            }
            if (!humidityData.isEmpty()) {
                machinerydata.put("humidity", humidityData);
            }
            // 将传感器数据添加到返回结果中
            info.put("machinerydata", machinerydata);

            dataList.add(info);
        }


        return dataList;

    }

}

package org.dromara.tunnelenvironmentmonitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelenvironmentmonitor.domain.*;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEnvEquipmentValueVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEquipmentVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TUnitGasAlarmlVo;
import org.dromara.tunnelenvironmentmonitor.mapper.DeviceMapper;
import org.dromara.tunnelenvironmentmonitor.mapper.DustValueMapper;
import org.dromara.tunnelenvironmentmonitor.mapper.EnvAlarmMapper;
import org.dromara.tunnelenvironmentmonitor.mapper.EnvionmentMonitorMapper;
import org.dromara.tunnelenvironmentmonitor.service.IEnvironmentMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class EnvionmentMonitorServiceImpl implements IEnvironmentMonitorService {

    @Autowired
    private EnvionmentMonitorMapper envionmentMonitorMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private EnvAlarmMapper envAlarmMapper;

    @Autowired
    private DustValueMapper dustValueMapper;

    public static final Long GAS_SENSOR_TYPE = 1L;
    public static final Long DUST_SENSOR_TYPE = 2L;

    public static final Long GAS_TYPE_CH4 = 0L;
    public static final Long GAS_TYPE_CO = 1L;
    public static final Long GAS_TYPE_H2S = 2L;
    public static final Long GAS_TYPE_O2 = 3L;

    public static final Long DUST_TYPE_PM25 = 0L;
    public static final Long DUST_TYPE_PM10 = 1L;

    /**
     * 新增环保设备数据
     *
     * @param info 环保设备数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDeviceData(DeviceInfoData info) {
        TEquipment InfoData = new TEquipment();
        InfoData.setName(info.getDeviceName());
        InfoData.setDeviceaddress(info.getDeviceAddress());
        InfoData.setDevicesn(info.getDeviceSn());
        InfoData.setStatus(Long.valueOf(info.getOnlineStatus()));
        if(info.getDeviceName()!=null && info.getDeviceName().contains("气体")){
            InfoData.setEquipmenttype(Long.valueOf("0")); // 1:气体传感器
        } else if (info.getDeviceName()!=null && info.getDeviceName().contains("粉尘")) {
            InfoData.setEquipmenttype(Long.valueOf("1")); // 1:气体传感器
        }
        InfoData.setDirection(info.getRegionName().contains("左洞") ? 1L : 2L);
        InfoData.setElevation(info.getElevation());
        InfoData.setInlx(info.getInlX());
        InfoData.setNx(info.getNX());
        InfoData.setNy(info.getNY());
        InfoData.setPx(info.getPX());
        InfoData.setPy(info.getPY());
        InfoData.setLat(info.getLatitude());
        InfoData.setLon(info.getLongitude());
        InfoData.setProjcode("DLGS");
        InfoData.setUnitcode("60-04.0003.00.00");
        InfoData.setCreationtime(DateUtils.getNowDate());
        InfoData.setCreatoruserid(Long.valueOf("0"));

        TEquipment existing = deviceMapper.selectOne(new LambdaQueryWrapper<TEquipment>().eq(TEquipment::getDevicesn, info.getDeviceSn()));

        int rows = 0;

        if (existing == null) {
            // 新增环保监测信息
            rows = deviceMapper.insert(InfoData);
        } else {
            // 数据存在，更新信息
            InfoData.setId(existing.getId());
            InfoData.setCreationtime(existing.getCreationtime());
            InfoData.setCreatoruserid(existing.getCreatoruserid());
            InfoData.setLastmodificationtime(DateUtils.getNowDate());
            InfoData.setLastmodifieruserid(Long.valueOf("0"));
            rows = deviceMapper.updateById(InfoData);
        }

        return rows;
    }

    /**
     * 新增环保监测数据
     *
     * @param info 环保监测数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertEnvironmentData(EnvironmentMonitorData info) {
        TEnvEquipment InfoData = new TEnvEquipment();
        InfoData.setDevicecode(info.getDeviceCode());
        InfoData.setName(info.getSensorName());
        InfoData.setDeviceaddress(info.getDeviceAddress());
        InfoData.setDevicesn(info.getDeviceSn());
        InfoData.setStatus(Long.valueOf(info.getOnlineStatus()));
        InfoData.setEnvequipmenttype(Long.valueOf("1")); // 1:气体传感器
//        if(info.getSymbols().equals("CH4") || info.getSymbols().equals("CO")|| info.getSymbols().equals("H2S")|| info.getSymbols().equals("O2") ){
//            InfoData.setEnvequipmenttype(Long.valueOf("1")); // 1:气体传感器
//        }else if(info.getSymbols().equals("PM2.5") || info.getSymbols().equals("PM10")){
//            InfoData.setEnvequipmenttype(Long.valueOf("2")); // 2:粉尘传感器
//        }
//        if (info.getSymbols().equals("CH4")) {
//            InfoData.setGastype(Long.valueOf("0"));
//        } else if (info.getSymbols().equals("CO")) {
//            InfoData.setGastype(Long.valueOf("1"));
//        } else if (info.getSymbols().equals("H2S")) {
//            InfoData.setGastype(Long.valueOf("2"));
//        } else if(info.getSymbols().equals("O2")) {
//            InfoData.setGastype(Long.valueOf("3"));
//        } else if (info.getSymbols().equals("PM2.5")) {
//            InfoData.setDusttype(Long.valueOf("0"));
//        }else if (info.getSymbols().equals("PM10")) {
//            InfoData.setDusttype(Long.valueOf("1"));
//        }
        String symbol = info.getSymbols();
        if (symbol != null) {
            // 设置传感器设备类型
            if (symbol.equals("CH4") || symbol.equals("CO") || symbol.equals("H2S") || symbol.equals("O2")) {
                InfoData.setEnvequipmenttype(GAS_SENSOR_TYPE); // GAS_SENSOR_TYPE = 1: 气体传感器
            } else if (symbol.equals("PM2.5") || symbol.equals("PM10")) {
                InfoData.setEnvequipmenttype(DUST_SENSOR_TYPE); // DUST_SENSOR_TYPE = 2: 粉尘传感器
            }

            // 设置气体类型
            switch (symbol) {
                case "CH4":
                    InfoData.setGastype(GAS_TYPE_CH4); // GAS_TYPE_CH4 = 0
                    break;
                case "CO":
                    InfoData.setGastype(GAS_TYPE_CO); // GAS_TYPE_CO = 1
                    break;
                case "H2S":
                    InfoData.setGastype(GAS_TYPE_H2S); // GAS_TYPE_H2S = 2
                    break;
                case "O2":
                    InfoData.setGastype(GAS_TYPE_O2); // GAS_TYPE_O2 = 3
                    break;
                default:
                    break;
            }

            // 设置粉尘类型
            if (symbol.equals("PM2.5")) {
                InfoData.setDusttype(DUST_TYPE_PM25); // DUST_TYPE_PM25 = 0
            } else if (symbol.equals("PM10")) {
                InfoData.setDusttype(DUST_TYPE_PM10); // DUST_TYPE_PM10 = 1
            }
        }
        InfoData.setConcentration(info.getSensorValue().longValue());
        InfoData.setLowthreshold(info.getLowAlarm().longValue());
        InfoData.setHighthreshold(info.getHighAlarm().longValue());
        InfoData.setDirection(info.getRegionName().contains("左洞") ? 1L : 2L);
        InfoData.setRegionname(info.getRegionName());
        InfoData.setProjcode("DLGS");
        InfoData.setUnitcode("60-04.0003.00.00");
        InfoData.setCreationtime(DateUtils.getNowDate());
        InfoData.setCreatoruserid(Long.valueOf("0"));
        // 新增环保监测信息
        int rows = envionmentMonitorMapper.insert(InfoData);
        return rows;
    }

    /**
     * 新增环保监测报警数据
     *
     * @param info 环保监测报警数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertEnvironmentAlarmData(EnvironmentAlarmData info) {

        TUnitWarning existing = envAlarmMapper.selectOne(new LambdaQueryWrapper<TUnitWarning>().eq(TUnitWarning::getAlarmid, info.getAlarmId()));

        TUnitWarning InfoData = new TUnitWarning();
        int rows = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long status = 0L;
        if (info.getOnLine().equals(0)) {//报警结束
            status = 1L; // 1:已解决
        }

        if (existing != null) {
            InfoData.setId(existing.getId());
            InfoData.setLastmodificationtime(DateUtils.getNowDate());
            InfoData.setLastmodifieruserid(Long.valueOf("0"));
            InfoData.setWarningcontent((info.getRegionName().contains("左洞")? "左洞" : "右洞") + info.getWorkareaName() + info.getSensorName() + "浓度报警，当前浓度为：" + info.getDisplayText());
            InfoData.setDevicesn(info.getDeviceSn());
            InfoData.setDevicecode(info.getDeviceCode());
            try {
                InfoData.setStarttime(dateFormat.parse(info.getFirstTime()));
                InfoData.setEndtime(dateFormat.parse(info.getLastTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            InfoData.setStatus(status);
            rows = envAlarmMapper.updateById(InfoData);
        }else {
            InfoData.setStatus(status);
            InfoData.setProjcode("DLGS");
            InfoData.setUnitcode("60-04.0003.00.00");
            InfoData.setWarningcontent((info.getRegionName().contains("左洞")? "左洞" : "右洞") + info.getWorkareaName() + info.getSensorName() + "浓度报警，当前浓度为：" + info.getDisplayText());
            InfoData.setAlarmid(info.getAlarmId());
            try {
                InfoData.setStarttime(dateFormat.parse(info.getFirstTime()));
                InfoData.setEndtime(dateFormat.parse(info.getLastTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            InfoData.setDevicesn(info.getDeviceSn());
            InfoData.setDevicecode(info.getDeviceCode());
            InfoData.setCreationtime(DateUtils.getNowDate());
            InfoData.setCreatoruserid(Long.valueOf("0"));
            // 新增环保监测信息
            rows = envAlarmMapper.insert(InfoData);
        }
        return rows;
    }

    /**
     * 新台车位置数据
     *
     * @param trolleyData 台车位置数据
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTrolleyPositionData(TrolleyData trolleyData) {

        Long direction = trolleyData.getRegionName().contains("左洞")?1L:2L;
        envionmentMonitorMapper.updateLargeLocation(direction,trolleyData.getCoordinatex(),trolleyData.getCoordinatey(),trolleyData.getCoordinatez(),trolleyData.getTrolleyId(),trolleyData.getPileNo());

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
     * 获取设备状态数量
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备状态数量
     */
    @Override
    public Map<String, Integer> getDeviceStatusCount(String projectCode, String unitCode) {

        QueryWrapper<TEquipment> wrapper = new QueryWrapper<>();

        // 条件判断
        if (StringUtils.isNotBlank(projectCode)) { //项目代码
            wrapper.eq("projcode", projectCode);

            if (StringUtils.isNotBlank(unitCode)) { //隧道代码
                wrapper.eq("unitcode", unitCode);
            }
        }

        List<TEquipment> deviceStatusList = deviceMapper.selectList(wrapper);

        int gasOnlineCount = (int) deviceStatusList.stream().filter(d -> d.getStatus() == 1 && d.getEquipmenttype() == 0).count();

        int gasOfflineCount = (int) deviceStatusList.stream().filter(d -> d.getStatus() == 0 && d.getEquipmenttype() == 0).count();

        int dustOnlineCount = (int) deviceStatusList.stream().filter(d -> d.getStatus() == 1 && d.getEquipmenttype() == 1).count();

        int dustOfflineCount = (int) deviceStatusList.stream().filter(d -> d.getStatus() == 0 && d.getEquipmenttype() == 1).count();

        return Map.of("gasOnlineCount", gasOnlineCount, "gasOfflineCount", gasOfflineCount, "dustOnlineCount", dustOnlineCount , "dustOfflineCount", dustOfflineCount);
    }

    /**
     * 获取设备列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public TableDataInfo<TEquipmentVo> getDeviceList(String projectCode, String unitCode) {

        QueryWrapper<TEquipment> wrapper = new QueryWrapper<>();

        // 条件判断
        if (StringUtils.isNotBlank(projectCode)) { //项目代码
            wrapper.eq("projcode", projectCode);

            if (StringUtils.isNotBlank(unitCode)) { //隧道代码
                wrapper.eq("unitcode", unitCode);
            }
        }

        List<TEquipment> equipmentList = deviceMapper.selectList(wrapper);

        List<TEquipmentVo> voList = new ArrayList<>();

        for (TEquipment tEquipment : equipmentList) {
            TEquipmentVo vo = new TEquipmentVo();
            vo.setName(tEquipment.getName());
            vo.setDeviceaddress(tEquipment.getDeviceaddress());
            vo.setDevicesn(tEquipment.getDevicesn());
            vo.setStatus(tEquipment.getStatus());
            vo.setDirection(tEquipment.getDirection());
            vo.setProjcode(tEquipment.getProjcode());
            vo.setUnitcode(tEquipment.getUnitcode());
            vo.setLat(tEquipment.getLat());
            vo.setLon(tEquipment.getLon());
            voList.add(vo);
        }

        return TableDataInfo.build(voList);

    }

    /**
     * 获取设备浓度数据列表-天
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public TableDataInfo<TEnvEquipmentValueVo> getDeviceValueList(String projectCode, String unitCode, String deviceSn, String gasType) {
        var type = "";
        switch (gasType) {
            case "CH4":
                type = GAS_TYPE_CH4.toString(); // GAS_TYPE_CH4 = 0
                break;
            case "CO":
                type = GAS_TYPE_CO.toString(); // GAS_TYPE_CO = 1
                break;
            case "H2S":
                type = GAS_TYPE_H2S.toString(); // GAS_TYPE_H2S = 2
                break;
            case "O2":
                type = GAS_TYPE_O2.toString(); // GAS_TYPE_O2 = 3
                break;
        }
        List<TEnvEquipmentValueVo> envEquipmentList = envionmentMonitorMapper.selectDeviceValueList(projectCode, unitCode, deviceSn, type);

        return TableDataInfo.build(envEquipmentList);
    }

    /**
     * 获取设备浓度数据列表-小时
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public TableDataInfo<TEnvEquipmentValueVo> getDeviceValueByHourList(String projectCode, String unitCode, String deviceSn, String gasType) {
        var type = "";
        switch (gasType) {
            case "CH4":
                type = GAS_TYPE_CH4.toString(); // GAS_TYPE_CH4 = 0
                break;
            case "CO":
                type = GAS_TYPE_CO.toString(); // GAS_TYPE_CO = 1
                break;
            case "H2S":
                type = GAS_TYPE_H2S.toString(); // GAS_TYPE_H2S = 2
                break;
            case "O2":
                type = GAS_TYPE_O2.toString(); // GAS_TYPE_O2 = 3
                break;
        }
        List<TEnvEquipmentValueVo> envEquipmentList = envionmentMonitorMapper.selectDeviceValueHourList(projectCode, unitCode, deviceSn, type);

        return TableDataInfo.build(envEquipmentList);
    }

    /**
     * 获取气体报警数据列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public TableDataInfo<TUnitGasAlarmlVo> getEnvironmentAlarmValueList(String projectCode, String unitCode) {

        List<TUnitGasAlarmlVo> envEquipmentList = envAlarmMapper.selectAlarmValueList(projectCode, unitCode);

        return TableDataInfo.build(envEquipmentList);
    }

    /**
     * 获取粉尘实时数据
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public List<Map<String, Object>> getDustNowValueList(String projectCode, String unitCode, String deviceSn) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        List<DustValueData> dustList = dustValueMapper.selecDustNowValuetList(projectCode,unitCode, deviceSn);

        for (DustValueData dustValueData : dustList) {
            Map<String, Object> info = new HashMap<>();
            info.put("dustType", dustValueData.getDustType());
//            info.put("position", dustValueData.getPosition());
            info.put("concentration", dustValueData.getConcentration());
            info.put("dateTimes", dustValueData.getDateTimes());
            dataList.add( info);
        }

        return dataList;
    }

    /**
     * 获取粉尘当日平均数据
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public List<Map<String, Object>> getDustDayAvgValueList(String projectCode, String unitCode, String deviceSn) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        List<DustValueData> dustList = dustValueMapper.selecDustDayAvgValuetList(projectCode,unitCode, deviceSn);

        for (DustValueData dustValueData : dustList) {
            Map<String, Object> info = new HashMap<>();
            info.put("dustType", dustValueData.getDustType());
//            info.put("position", dustValueData.getPosition());
            info.put("concentration", dustValueData.getConcentration());
            info.put("dateTimes", dustValueData.getDateTimes());
            dataList.add( info);
        }

        return dataList;
    }

    /**
     * 获取粉尘周平均数据
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public List<Map<String, Object>> getDustWeekAvgValueList(String projectCode, String unitCode, String deviceSn) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        List<DustValueData> dustList = dustValueMapper.selecDustWeekAvgValuetList(projectCode,unitCode, deviceSn);

        for (DustValueData dustValueData : dustList) {
            Map<String, Object> info = new HashMap<>();
            info.put("dustType", dustValueData.getDustType());
//            info.put("position", dustValueData.getPosition());
            info.put("concentration", dustValueData.getConcentration());
            info.put("dateTimes", dustValueData.getDateTimes());
            dataList.add( info);
        }

        return dataList;
    }

    /**
     * 获取粉尘设备列表
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public List<Map<String, Object>> getDustDeviceList(String projectCode, String unitCode) {

        LambdaQueryWrapper<TEquipment> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(TEquipment::getName, TEquipment::getDevicesn)
            .like(TEquipment::getName, "粉尘");

        List<TEquipment> deviceList = deviceMapper.selectList(wrapper);

        List<Map<String, Object>> dataList = new ArrayList<>();

        for (TEquipment tEquipment : deviceList) {
            Map<String, Object> info = new HashMap<>();
            info.put("name", tEquipment.getName());
            info.put("devicesn", tEquipment.getDevicesn());
            dataList.add(info);
        }

        return dataList;
    }

}

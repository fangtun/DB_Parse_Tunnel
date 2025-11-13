package org.dromara.tunnelenvironmentmonitor.service;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelenvironmentmonitor.domain.DeviceInfoData;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentAlarmData;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentMonitorData;
import org.dromara.tunnelenvironmentmonitor.domain.TrolleyData;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEnvEquipmentValueVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEquipmentVo;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TUnitGasAlarmlVo;

import java.util.List;
import java.util.Map;


/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface IEnvironmentMonitorService {

    /**
     * 新增设备数据
     *
     * @param deviceData 设备信息
     * @return 结果
     */
    int insertDeviceData(DeviceInfoData deviceData);

    /**
     * 新增环保监测数据
     *
     * @param envData 环保监测信息
     * @return 结果
     */
    int insertEnvironmentData(EnvironmentMonitorData envData);

    /**
     * 新增环保监测告警数据
     *
     * @param environmentAlarmData 环保监测告警信息
     * @return 结果
     */
    int insertEnvironmentAlarmData(EnvironmentAlarmData environmentAlarmData);

    /**
     * 台车位置数据
     *
     * @param trolleyData 台车位置数据
     * @return 结果
     */
    void updateTrolleyPositionData(TrolleyData trolleyData);

    /**
     * 获取设备状态统计
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 结果
     */
    Map<String, Integer> getDeviceStatusCount(String projectCode, String unitCode);

    /**
     * 获取设备列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    TableDataInfo<TEquipmentVo> getDeviceList(String projectCode, String unitCode);

    /**
     * 获取设备数据列表-天
     *
     * @param projectCode 项目编号
     * @param unitCode    单位工程编号
     * @param deviceSn    设备编号
     * @param gasType     气体类型
     * @return 列表
     */
    TableDataInfo<TEnvEquipmentValueVo> getDeviceValueList(String projectCode, String unitCode, String deviceSn,String gasType);

    /**
     * 获取设备数据列表-小时
     *
     * @param projectCode 项目编号
     * @param unitCode    单位工程编号
     * @param deviceSn    设备编号
     * @param gasType     气体类型
     * @return 列表
     */
    TableDataInfo<TEnvEquipmentValueVo> getDeviceValueByHourList(String projectCode, String unitCode, String deviceSn,String gasType);

    /**
     * 获取气体告警列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    TableDataInfo<TUnitGasAlarmlVo> getEnvironmentAlarmValueList(String projectCode, String unitCode);

    /**
     * 获取粉尘实时数据
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    List<Map<String, Object>> getDustNowValueList(String projectCode, String unitCode, String deviceSn);

    /**
     * 获取粉尘日平均数据
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    List<Map<String, Object>> getDustDayAvgValueList(String projectCode, String unitCode, String deviceSn);

    /**
     * 获取粉尘周平均数据
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    List<Map<String, Object>> getDustWeekAvgValueList(String projectCode, String unitCode, String deviceSn);

    /**
     * 获取粉尘设备列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    List<Map<String, Object>> getDustDeviceList(String projectCode, String unitCode);

}

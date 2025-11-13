package org.dromara.tunnelintelligentconstruction.service;

import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryDataVo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryVo;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface ILargeScaleMachineryService {

    /**
     * 新增人大机设备
     *
     * @param decodedStr 大机设备信息
     * @return 结果
     */
    void insertLargeScaleMachinery(String decodedStr,String devicesn);

    /**
     * 新增大机设备数据
     *
     * @param decodedStr 大机设备数据
     * @return 结果
     */
    void insertLargeScaleMachineryData(String decodedStr);

    /**
     * 查询 大机设备信息
     *
     * @param projectCode 项目编号
     * @param unitCode    单元编号
     * @return 大机设备信息
     */
    TableDataInfo<LargeScaleMachineryVo> getLargeScaleMachineryList(String projectCode, String unitCode);

    /**
     * 获取 大机设备数据
     *
     * @param deviceSn 设备编号
     * @return 大机设备数据
     */
    List<Map<String, Object>> getLargeScaleMachineryDataList(String deviceSn);

    /**
     * 保存 钻注锚点设备信息
     *
     * @param requestData 钻注锚点设备信息
     * @return 结果
     */
    int saveDrillingAnchorDevice(Map<String, Object> requestData);

    /**
     * 保存 钻注锚点传感器数据
     *
     * @param requestData 钻注锚点传感器数据
     * @return 结果
     */
    int saveDrillingAnchorSensorData(Map<String, Object> requestData);
}

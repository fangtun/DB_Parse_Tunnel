package org.dromara.tunnelenvironmentmonitor.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentMonitorData;
import org.dromara.tunnelenvironmentmonitor.domain.TEnvEquipment;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TEnvEquipmentValueVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface EnvionmentMonitorMapper extends BaseMapperPlus<TEnvEquipment, EnvironmentMonitorData> {
    List<TEnvEquipment> selectDeviceList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    List<TEnvEquipmentValueVo> selectDeviceValueList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode , @Param("deviceSn") String deviceSn , @Param("gasType") String gasType);

    List<TEnvEquipmentValueVo> selectDeviceValueHourList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode , @Param("deviceSn") String deviceSn , @Param("gasType") String gasType);

    void updateLargeLocation(Long direction, BigDecimal coordinatex, BigDecimal coordinatey, BigDecimal coordinatez, String trolleyId, String pileNo);
}

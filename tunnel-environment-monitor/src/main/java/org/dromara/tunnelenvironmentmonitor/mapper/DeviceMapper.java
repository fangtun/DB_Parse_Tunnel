package org.dromara.tunnelenvironmentmonitor.mapper;


import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelenvironmentmonitor.domain.DeviceInfoData;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentMonitorData;
import org.dromara.tunnelenvironmentmonitor.domain.TEnvEquipment;
import org.dromara.tunnelenvironmentmonitor.domain.TEquipment;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface DeviceMapper extends BaseMapperPlus<TEquipment, DeviceInfoData> {

}

package org.dromara.tunnelenvironmentmonitor.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelenvironmentmonitor.domain.DustValueData;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentMonitorData;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface DustValueMapper extends BaseMapperPlus<EnvironmentMonitorData, DustValueData> {

    List<DustValueData> selecDustNowValuetList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode, @Param("deviceSn") String deviceSn);

    List<DustValueData> selecDustDayAvgValuetList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode, @Param("deviceSn") String deviceSn);

    List<DustValueData> selecDustWeekAvgValuetList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode, @Param("deviceSn") String deviceSn);


}

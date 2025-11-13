package org.dromara.tunnelenvironmentmonitor.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelenvironmentmonitor.domain.EnvironmentAlarmData;
import org.dromara.tunnelenvironmentmonitor.domain.TUnitWarning;
import org.dromara.tunnelenvironmentmonitor.domain.vo.TUnitGasAlarmlVo;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface EnvAlarmMapper extends BaseMapperPlus<TUnitWarning, EnvironmentAlarmData> {

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<TUnitGasAlarmlVo> selectAlarmValueList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);
}

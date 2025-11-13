package org.dromara.tunnelbaseinfo.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelbaseinfo.domain.PcmsTunnelBaseInformation;
import org.dromara.tunnelbaseinfo.domain.TunnelBaseInfoVo;
import org.dromara.tunnelbaseinfo.domain.UnitList;
import org.dromara.tunnelbaseinfo.domain.vo.TunnelOverviewInfoVo;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface TunnelBaseInfoMapper extends BaseMapperPlus<PcmsTunnelBaseInformation, TunnelBaseInfoVo> {

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<PcmsTunnelBaseInformation> selectTunnelBaseInfo(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<PcmsTunnelBaseInformation> selectTunnelOverviewInfo(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<PcmsTunnelBaseInformation> selectTunnelOverviewtotalMileageInfo(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<UnitList> selectTunnelUnitList();

}

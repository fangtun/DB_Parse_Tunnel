package org.dromara.tunnelmonitorvideo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorYsyCamera;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelBaseInfo;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelMonitorVideoVo;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface TunnelMonitorVideoMapper extends BaseMapper<PcmsTunnelMonitorYsyCamera> {

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<TunnelBaseInfo> getTunnelBaseInfo();

    PcmsTunnelMonitorYsyCamera getById(Long id);

    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<PcmsTunnelMonitorYsyCamera> getTunnelVideoList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

}

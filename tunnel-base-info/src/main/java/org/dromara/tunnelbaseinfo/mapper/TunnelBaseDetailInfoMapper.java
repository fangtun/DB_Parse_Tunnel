package org.dromara.tunnelbaseinfo.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelbaseinfo.domain.PcmsTunnelBaseInformation;
import org.dromara.tunnelbaseinfo.domain.PcmsTunnelBaseInformationDetail;
import org.dromara.tunnelbaseinfo.domain.TunnelBaseInfoVo;
import org.dromara.tunnelbaseinfo.domain.UnitList;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface TunnelBaseDetailInfoMapper extends BaseMapperPlus<PcmsTunnelBaseInformationDetail, PcmsTunnelBaseInformationDetail> {

}

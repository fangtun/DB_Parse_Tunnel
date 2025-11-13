package org.dromara.tunnelbaseinfo.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelbaseinfo.domain.PcmsTunnelBaseInformation;
import org.dromara.tunnelbaseinfo.domain.ProjectList;
import org.dromara.tunnelbaseinfo.domain.TProject;
import org.dromara.tunnelbaseinfo.domain.TunnelBaseInfoVo;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface TunnelProjectListMapper extends BaseMapperPlus<TProject, ProjectList> {

}

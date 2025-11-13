package org.dromara.tunnelintelligentconstruction.mapper;


import org.apache.ibatis.annotations.Param;

import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelintelligentconstruction.domain.TLargeScaleMachinery;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryDataVo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryVo;


import java.util.List;


/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface LargeScaleMachineryMapper extends BaseMapperPlus<TLargeScaleMachinery,TLargeScaleMachinery> {

    List<LargeScaleMachineryVo> selectLargeScaleMachineryList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    List<LargeScaleMachineryDataVo> selectLargeScaleMachineryDataList(@Param("deviceSn") String deviceSn);


}

package org.dromara.tunnelsecurityguarantee.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;
import org.dromara.tunnelsecurityguarantee.domain.TPersonCarsInfo;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface SecurityGuaranteeForPersonAndCarsMapper extends BaseMapperPlus<TPersonCarsInfo, SecurityGuaranteeData> {

    List<TPersonCarsInfo> personAndCarsList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);
}

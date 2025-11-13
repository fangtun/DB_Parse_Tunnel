package org.dromara.tunnelsecurityguarantee.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelsecurityguarantee.domain.Carsinfo;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface SecurityGuaranteeForCarsMapper extends BaseMapperPlus<Carsinfo, SecurityGuaranteeData> {
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<Carsinfo> selectCarsList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);

    void updateLargeLocation(Long direction, BigDecimal coordinatex, BigDecimal coordinatey, BigDecimal coordinatez, String personId, String pileNo);
}

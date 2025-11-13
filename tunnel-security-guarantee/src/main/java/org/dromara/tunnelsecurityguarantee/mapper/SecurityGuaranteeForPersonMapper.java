package org.dromara.tunnelsecurityguarantee.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.tunnelsecurityguarantee.domain.Person;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author Lion Li
 */
public interface SecurityGuaranteeForPersonMapper extends BaseMapperPlus<Person, SecurityGuaranteeData> {


    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    List<Person> selectPersonList(@Param("projectCode") String projectCode, @Param("unitCode") String unitCode);
}

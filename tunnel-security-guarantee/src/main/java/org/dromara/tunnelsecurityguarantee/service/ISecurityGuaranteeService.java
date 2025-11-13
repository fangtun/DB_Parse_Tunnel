package org.dromara.tunnelsecurityguarantee.service;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;
import org.dromara.tunnelsecurityguarantee.domain.vo.CarsListInTunnelVo;
import org.dromara.tunnelsecurityguarantee.domain.vo.PersonCarsListInTunnelVo;
import org.dromara.tunnelsecurityguarantee.domain.vo.PersonListInTunnelVo;

import java.util.Map;


/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface ISecurityGuaranteeService {

    /**
     * 新增人员入场信息
     *
     * @param person 人员入场信息
     * @return 结果
     */
    int insertPerson(SecurityGuaranteeData person);

    /**
     * 新增车辆入场信息
     *
     * @param cars 车辆入场信息
     * @return 结果
     */
    int insertCars(SecurityGuaranteeData cars);


    /**
     * 获取人员列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 结果
     */
    TableDataInfo<PersonListInTunnelVo> getPersonListInTunnel(String projectCode, String unitCode);

    /**
     * 获取人员列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 结果
     */
    TableDataInfo<CarsListInTunnelVo> getCarsListInTunnel(String projectCode, String unitCode);

    /**
     * 新增人员车辆入场信息
     *
     * @param locationData 人员车辆入场信息
     * @return 结果
     */
    int insertPersonAndCars(SecurityGuaranteeData locationData);

    /**
     * 获取人员车辆列表
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    TableDataInfo<PersonCarsListInTunnelVo> getPersonCarsListInTunnel(String projectCode, String unitCode);

}

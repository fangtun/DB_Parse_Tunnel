package org.dromara.tunnelbaseinfo.service;

import org.dromara.tunnelbaseinfo.domain.ProjectList;
import org.dromara.tunnelbaseinfo.domain.UnitList;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface ITunnelBaseInfoService {

    /**
     * 获取隧道基础信息
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    Map<String, Object> getTunnelBaseInfo(String projectCode, String unitCode);

    /**
     * 获取隧道概览信息
     *
     * @param projectCode 项目编号
     * @param unitCode    部门编号
     * @return 列表
     */
    Map<String, Object> getTunnelOverviewInfo(String projectCode, String unitCode);

    /**
     * 获取隧道状态信息
     *
     * @param projectCode 项目编号
     * @return 列表
     */
    Map<String, Integer> getTunnelStatusInfo(String projectCode);

    /**
     * 获取项目列表
     *
     * @return 列表
     */
    List<ProjectList> getProjectlist();

    /**
     * 获取单位工程名下拉列表
     *
     * @return 列表
     */
    List<UnitList> getUnitList();

    /**
     * 获取项目投资进度
     *
     * @return 列表
     */
    Map<String, Object> getHomeInvestmentProgress();

    /**
     * 获取项目形象进度
     *
     * @return 列表
     */
    Map<String, Object> getHomeImageProgressAcquisition();
}

package org.dromara.tunnelbaseinfo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.tunnelbaseinfo.domain.ProjectList;
import org.dromara.tunnelbaseinfo.domain.UnitList;
import org.dromara.tunnelbaseinfo.service.ITunnelBaseInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tunnelinfo")
public class TunnelBaseInfoController {

    private final ITunnelBaseInfoService tunnelBaseInfoService;

    /**
     * 获取隧道概况
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:overview:list")
    @GetMapping("/getTunnelOverviewInfo")
//    public TableDataInfo<TunnelOverviewInfoVo> getTunnelOverviewInfo(String projectCode, String unitCode) {
    public R<Map<String, Object>> getTunnelOverviewInfo(String projectCode, String unitCode) {
        return R.ok(tunnelBaseInfoService.getTunnelOverviewInfo(projectCode, unitCode));
    }

    /**
     * 获取隧道基础信息
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:base:list")
    @GetMapping("/getTunnelBaseInfo")
//    public TableDataInfo<TunnelBaseInfoVo> getTunnelBaseInfo(String projectCode, String unitCode) {
    public R<Map<String, Object>> getTunnelBaseInfo(String projectCode, String unitCode) {
        return R.ok(tunnelBaseInfoService.getTunnelBaseInfo(projectCode, unitCode));
    }

    /**
     * 获取隧道施工进度状态
     *
     * @param projectCode 项目编号
     * @return 结果
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:status:list")
    @GetMapping("/getTunnelStatusInfo")
    public R<Map<String, Integer>> getTunnelStatusInfo(String projectCode) {
        return R.ok(tunnelBaseInfoService.getTunnelStatusInfo(projectCode));
    }


    /**
     * 获取项目名下拉列表
     *
     * @return 列表
     */
    @SaIgnore
    @GetMapping("/getTunnelProjectList")
    public R<List<ProjectList>> getProjectList() {
        List<ProjectList> list = tunnelBaseInfoService.getProjectlist();
        return R.ok(list);
    }

    /**
     * 获取地图上隧道列表
     *
     * @return 列表
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:unit:list")
    @GetMapping("/getTunnelUnitList")
    public R<List<UnitList>> getUnitList() {
        List<UnitList> list = tunnelBaseInfoService.getUnitList();
        return R.ok(list);
    }

    /**
     * 获取项目投资进度
     *
     * @return 列表
     */
    //    @SaIgnore
    @SaCheckPermission("tunnel:investment:progress")
    @GetMapping("/GetHomeInvestmentProgress")
    public R<Map<String, Object>> getHomeInvestmentProgress() {
        return R.ok(tunnelBaseInfoService.getHomeInvestmentProgress());
    }

    /**
     * 获取项目形象进度
     *
     * @return 列表
     */
//    @SaIgnore
    @SaCheckPermission("tunnel:image:progress")
    @GetMapping("/GetHomeImageProgressAcquisition")
    public R<Map<String, Object>> getHomeImageProgressAcquisition() {
        return R.ok(tunnelBaseInfoService.getHomeImageProgressAcquisition());
    }

}

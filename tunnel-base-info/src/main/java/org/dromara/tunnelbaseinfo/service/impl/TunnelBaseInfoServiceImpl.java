package org.dromara.tunnelbaseinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.annotation.DataColumn;
import org.dromara.common.mybatis.annotation.DataPermission;
import org.dromara.tunnelbaseinfo.domain.*;
import org.dromara.tunnelbaseinfo.mapper.*;
import org.dromara.tunnelbaseinfo.service.ITunnelBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class TunnelBaseInfoServiceImpl implements ITunnelBaseInfoService {

    @Autowired
    private TunnelBaseInfoMapper tunnelBaseInfoMapper;

    @Autowired
    private TunnelBaseDetailInfoMapper tunnelBaseDetailInfoMapper;

    @Autowired
    private TunnelMeteringProgressMapper tunnelMeteringProgressMapper;

    @Autowired
    private TunnelImageProgressMapper tunnelImageProgressMapper;

    @Autowired
    private TunnelProjectListMapper tunnelProjectListMapper;

    /**
     * 获取隧道概况
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public Map<String, Object> getTunnelOverviewInfo(String projectCode, String unitCode) {

        Map<String, Object> info = new HashMap<>();
            // 查询隧道信息
        List<PcmsTunnelBaseInformation> overViewInfo = tunnelBaseInfoMapper.selectTunnelOverviewInfo(projectCode, unitCode);

        List<PcmsTunnelBaseInformation> totalMileageInfo = tunnelBaseInfoMapper.selectTunnelOverviewtotalMileageInfo(projectCode, unitCode);

        if (!overViewInfo.isEmpty()) {
            PcmsTunnelBaseInformation tunnelInfo = overViewInfo.get(0);
            info.put("total", tunnelInfo.getTotal());
            info.put("totalMileage", tunnelInfo.getTotalmileage());
            info.put("totalInvestment", tunnelInfo.getTotalinvestment());
            info.put("completedInvestment", tunnelInfo.getCompletedinvestment());
        }

        if (!totalMileageInfo.isEmpty()) {
            info.put("completeMileage", totalMileageInfo.get(0).getCompletemileage());
        }

        return info;

    }


    /**
     * 获取隧道基础信息
     *
     * @param projectCode 项目代码
     * @param unitCode    隧道代码
     * @return 设备列表
     */
    @Override
    public Map<String, Object> getTunnelBaseInfo(String projectCode, String unitCode) {

        Map<String, Object> info = new HashMap<>();

        // 查询隧道基础信息
        List<PcmsTunnelBaseInformation> baseInfo = tunnelBaseInfoMapper.selectTunnelBaseInfo(projectCode, unitCode);

        for (PcmsTunnelBaseInformation tunnelBaseInformation : baseInfo) {
            info.put("name", tunnelBaseInformation.getFTunnelname());
            info.put("constructionUnit", tunnelBaseInformation.getFConstructionunit());
            info.put("constructionCompany", tunnelBaseInformation.getFConstructioncompany());
            info.put("supervisoryUnit", tunnelBaseInformation.getFSupervisoryunit());
            info.put("designUnit", tunnelBaseInformation.getFDesignunit());
            info.put("leftLength", tunnelBaseInformation.getLeftlength());
            info.put("rightLength", tunnelBaseInformation.getRightlength());
            info.put("constructionScale", tunnelBaseInformation.getFConstructionscale());
            info.put("designSpeed", tunnelBaseInformation.getFDesignspeed());
        }

        return info;
    }

    /**
     * 获取隧道施工进度状态
     *
     * @param projectCode 项目代码
     * @return 设备状态数量
     */
    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    public Map<String, Integer> getTunnelStatusInfo(String projectCode) {

        LambdaQueryWrapper<PcmsTunnelBaseInformation> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(PcmsTunnelBaseInformation::getFTunnelcode, PcmsTunnelBaseInformation::getFStatus);

        // 条件判断
        if (StringUtils.isNotBlank(projectCode)) { //项目代码
            wrapper.eq(PcmsTunnelBaseInformation::getFProjectcode, projectCode);
        }

        List<PcmsTunnelBaseInformation> tunnrlStatusList = tunnelBaseInfoMapper.selectList(wrapper);

//        int notStartCount = (int) tunnrlStatusList.stream().filter(d -> d.getFStatus() == null || d.getFStatus() == 0).count();

        int startCount = (int) tunnrlStatusList.stream().filter(d -> d.getFStatus() != null && d.getFStatus() == 0).count();

        int completed = (int) tunnrlStatusList.stream().filter(d -> d.getFStatus() != null && d.getFStatus() == 1).count();

        return Map.of("start", startCount, "completed", completed);

//        return Map.of("notstart", notStartCount, "start", startCount, "completed", completed);
    }

    /**
     * 获取项目列表
     *
     * @return 项目列表
     */
    @Override
    public List<ProjectList> getProjectlist() {
        QueryWrapper<TProject> wrapper = new QueryWrapper<>();
        List<TProject> projectList = tunnelProjectListMapper.selectList(wrapper);

        List<ProjectList> voList = new ArrayList<>();
        for (TProject TProject : projectList) {
            ProjectList vo = new ProjectList();
            vo.setProjectCode(TProject.getProjcode());
            vo.setProjectName(TProject.getProAbbreviation());
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 获取隧道列表
     *
     * @return 隧道列表
     */
    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    public List<UnitList> getUnitList() {

//        LambdaQueryWrapper<PcmsTunnelBaseInformation> wrapper = new LambdaQueryWrapper<>();
//        wrapper.select(PcmsTunnelBaseInformation::getFTunnelcode, PcmsTunnelBaseInformation::getFTunnelname);
//
//        // 条件判断
//        if (StringUtils.isNotBlank(projectCode)) { //项目代码
//            wrapper.eq(PcmsTunnelBaseInformation::getFProjectcode, projectCode);
//        }
//        List<PcmsTunnelBaseInformation> tunnrlStatusList = tunnelBaseInfoMapper.selectList(wrapper);
        List<UnitList> tunnrlStatusList = tunnelBaseInfoMapper.selectTunnelUnitList();

//        List<UnitList> voList = new ArrayList<>();
//        for (UnitList unitList : tunnrlStatusList) {
//            UnitList vo = new UnitList();
//            vo.setUnitCode(unitList.getUnitCode());
//            vo.setUnitName(unitList.getUnitName());
//            voList.add(vo);
//        }
        return tunnrlStatusList;
    }

    /**
     * 获取首页投资进度
     *
     * @return 首页投资进度
     */
    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    public Map<String, Object> getHomeInvestmentProgress() {

        // 依据参数查询隧道基础信息
        List<PcmsTunnelBaseInformation> parentLevelData = tunnelBaseInfoMapper.selectList(new LambdaQueryWrapper<PcmsTunnelBaseInformation>()
            .select(PcmsTunnelBaseInformation::getFId));

        if (parentLevelData == null || parentLevelData.isEmpty()) {
            throw new RuntimeException("未查询到该隧道相关信息。");
        }

        Map<String, Object> result = new HashMap<>();
        List<String> x = new ArrayList<>();
        List<BigDecimal> y1 = new ArrayList<>(); // 总金额
        List<BigDecimal> y2 = new ArrayList<>(); // 已完成金额

        for (PcmsTunnelBaseInformation value : parentLevelData) {
            // 依据基础数据查询基础信息子表数据

            List<PcmsTunnelBaseInformationDetail> subLevelData = tunnelBaseDetailInfoMapper.selectList(
                new LambdaQueryWrapper<PcmsTunnelBaseInformationDetail>()
                    .select(PcmsTunnelBaseInformationDetail::getFOriginalid, PcmsTunnelBaseInformationDetail::getFTunnelname)
                    .eq(PcmsTunnelBaseInformationDetail::getFUnitid, value.getFId())
            );

            // 总金额
            BigDecimal fTotalAmount = BigDecimal.ZERO;

            // 已完成金额
            BigDecimal fCompletedAmount = BigDecimal.ZERO;

            // 隧道名字
            String name = "";

            for (PcmsTunnelBaseInformationDetail valueSon : subLevelData) {
                PcmsTunnelMeteringProgress pcmsTunnelMeteringProgressEntityOne = tunnelMeteringProgressMapper.selectOne(
                    new QueryWrapper<PcmsTunnelMeteringProgress>()
                        .eq("F_TunnelId", valueSon.getFOriginalid())
                        .isNull("F_DeleteMark")
                );

                name = valueSon.getFTunnelname().replace("左洞", "").replace("右洞", "").replace("隧道", "");

                if (pcmsTunnelMeteringProgressEntityOne != null) {
                    fTotalAmount = fTotalAmount.add(pcmsTunnelMeteringProgressEntityOne.getFTotalamount() != null ?
                        BigDecimal.valueOf(pcmsTunnelMeteringProgressEntityOne.getFTotalamount()) : BigDecimal.ZERO);
                    fCompletedAmount = fCompletedAmount.add(pcmsTunnelMeteringProgressEntityOne.getFCompletedamount() != null ?
                        BigDecimal.valueOf(pcmsTunnelMeteringProgressEntityOne.getFCompletedamount()) : BigDecimal.ZERO);
                }
            }

            x.add(name);
            y1.add(fTotalAmount);
            y2.add(fCompletedAmount);
        }

        result.put("x", x);
        result.put("fTotalAmount", y1);
        result.put("fCompletedAmount", y2);

        return result;
    }

    /**
     * 首页形象进度数据查询
     *
     * @return 形象进度数据
     */
    @Override
    @DataPermission({
        @DataColumn(key = "deptName", value = "dept_id")
    })
    public Map<String, Object> getHomeImageProgressAcquisition() {
        // 查询基础信息表和基础信息子表
        List<PcmsTunnelBaseInformation> baseParentLevel = tunnelBaseInfoMapper.selectList(new LambdaQueryWrapper<PcmsTunnelBaseInformation>()
            .select(PcmsTunnelBaseInformation::getFId));

        Map<String, Object> result = new HashMap<>();
        List<String> x = new ArrayList<>();
        List<BigDecimal> lengthList = new ArrayList<>();
        List<BigDecimal> footageList = new ArrayList<>();

        // 依据父表查询子表
        for (PcmsTunnelBaseInformation value : baseParentLevel) {
            // 查询左洞数据
            PcmsTunnelBaseInformationDetail subParentLevelLeft = tunnelBaseDetailInfoMapper.selectOne(
                new LambdaQueryWrapper<PcmsTunnelBaseInformationDetail>()
                    .eq(PcmsTunnelBaseInformationDetail::getFUnitid, value.getFId())
                    .eq(PcmsTunnelBaseInformationDetail::getFDirection, 1)
            );

            // 查询右洞数据
            PcmsTunnelBaseInformationDetail subParentLevelRight = tunnelBaseDetailInfoMapper.selectOne(
                new LambdaQueryWrapper<PcmsTunnelBaseInformationDetail>()
                    .eq(PcmsTunnelBaseInformationDetail::getFUnitid, value.getFId())
                    .eq(PcmsTunnelBaseInformationDetail::getFDirection, 2)
            );

            // 检查数据是否存在
            if (subParentLevelLeft != null && subParentLevelRight != null) {
                // X轴数据
                x.add(subParentLevelLeft.getFTunnelname().replace("隧道", ""));
                x.add(subParentLevelRight.getFTunnelname().replace("隧道", ""));

                // 隧道总长
                lengthList.add(subParentLevelLeft.getFLength() != null ? BigDecimal.valueOf(subParentLevelLeft.getFLength()) : BigDecimal.ZERO);
                lengthList.add(subParentLevelRight.getFLength() != null ? BigDecimal.valueOf(subParentLevelRight.getFLength()) : BigDecimal.ZERO);

                // 查询该隧道的开挖进度 因为有可能隧道从两边同时开挖 所以会有两组数据
                List<PcmsTunnelImageProgressStatistics> progressLeft = tunnelImageProgressMapper.selectList(
                    new LambdaQueryWrapper<PcmsTunnelImageProgressStatistics>()
                        .eq(PcmsTunnelImageProgressStatistics::getFTunnelid, subParentLevelLeft.getFOriginalid())
                        .isNull(PcmsTunnelImageProgressStatistics::getFDeletemark)  // 添加删除标志为空的条件
                );

                List<PcmsTunnelImageProgressStatistics> progressRight = tunnelImageProgressMapper.selectList(
                    new LambdaQueryWrapper<PcmsTunnelImageProgressStatistics>()
                        .eq(PcmsTunnelImageProgressStatistics::getFTunnelid, subParentLevelRight.getFOriginalid())
                        .isNull(PcmsTunnelImageProgressStatistics::getFDeletemark)
                );

                // 开挖进度
                BigDecimal footageLeft = BigDecimal.ZERO;
                for (PcmsTunnelImageProgressStatistics progressValue : progressLeft) {
                    if (progressValue.getFFootage() != null) {
                        footageLeft = footageLeft.add(BigDecimal.valueOf(progressValue.getFFootage()));
                    }
                }
                footageList.add(footageLeft);

                // 开挖进度
                BigDecimal footageRight = BigDecimal.ZERO;
                for (PcmsTunnelImageProgressStatistics progressValue : progressRight) {
                    if (progressValue.getFFootage() != null) {
                        footageRight = footageRight.add(BigDecimal.valueOf(progressValue.getFFootage()));
                    }
                }
                footageList.add(footageRight);
            }
        }

        result.put("X", x);
        result.put("length", lengthList);
        result.put("footage", footageList);

        return result;
    }

}

package org.dromara.tunnelbaseinfo.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_project
 *
 * @author Lion Li
 * @date 2025-07-08
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_project")
public class TProject {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID(自增)
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 项目代码（施工）
     */
    private String projcode;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目简称
     */
    private String proAbbreviation;

    /**
     * 建设单位
     */
    private String constructionUnit;

    /**
     * 设计单位
     */
    private String designUnit;

    /**
     * 计划开工日期
     */
    private Long planstartDate;

    /**
     * 计划完工日期
     */
    private Long planendDate;

    /**
     * 实际开工日期
     */
    private Long startDate;

    /**
     * 实际完工日期
     */
    private Long endDate;

    /**
     *
     */
    private String quantities;

    /**
     * 投标价
     */
    private Long bid;

    /**
     * 省投资额
     */
    private Long provinceinvest;

    /**
     * 中央投资额
     */
    private Long centreinvest;

    /**
     * 概算总投资（万元）
     */
    private Long estimate;

    /**
     * 项目所在县、市、区
     */
    private String address;

    /**
     * 起始桩号
     */
    private String startStation;

    /**
     * 结束桩号
     */
    private String endStation;

    /**
     * 里程
     */
    private Long mileage;

    /**
     * 路线全长
     */
    private Long spanlength;

    /**
     * 监理单位
     */
    private String supervisorUnit;

    /**
     * 养护单位
     */
    private String maintenanceUnit;

    /**
     * 项目介绍
     */
    private String introduce;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目属性：续建项目：1 新建项目：2 完工项目：3
     */
    private Long attribute;

    /**
     * 建设性质：新建：1 改建：2 养护：3
     */
    private Long property;

    /**
     * 投资模式：省投资：1 地方投资：2
     */
    private Long invmodel;

    /**
     * 项目级别：高速公路：1 一级公路：2 二级公路：3
     */
    private Long level;

    /**
     * 创建时间
     */
    private Date creationtime;

    /**
     * 创建用户
     */
    private Long creatoruserid;

    /**
     * 最后修改时间
     */
    private Date lastmodificationtime;

    /**
     * 最后修改用户
     */
    private Long lastmodifieruserid;

    /**
     * 是否删除
     */
    private Long isdeleted;

    /**
     * 删除用户
     */
    private Long deleteruserid;

    /**
     * 删除时间
     */
    private Date deletiontime;

    /**
     * 经度
     */
    private Long lon;

    /**
     * 纬度
     */
    private Long lat;

    /**
     * 高度
     */
    private Long height;

    /**
     * 编码标准版本
     */
    private String csEdition;


}

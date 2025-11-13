package org.dromara.tunnelintelligentconstruction.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serial;

/**
 * 环境设备对象 t_large_scale_machinery
 *
 * @author Lion Li
 * @date 2025-08-12
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_large_scale_machinery")
public class TLargeScaleMachinery {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境设备ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备序列号
     */
    private String devicesn;

    /**
     * 设备种类(0:喷淋台车 1：蒸汽台车)
     */
    private Long equipmenttype;

    /**
     * 状态(0:离线 1:在线)
     */
    private Long status;

    /**
     * 左侧水箱缺水状态(0:正常，1:缺水)
     */
    private Long leftlowwater;

    /**
     * 右侧水箱缺水状态(0:正常，1:缺水)
     */
    private Long rightlowwater;

    /**
     * 位置(1左洞；2右洞)
     */
    private Long direction;

    /**
     * 桩号：米
     */
    private String pileno;

    /**
     * 解析后桩号：米
     */
    private Long processedpileno;

    /**
     * 定位用坐标X
     */
    private String coordinatex;

    /**
     * 定位用坐标Y
     */
    private String coordinatey;

    /**
     * 定位用坐标Z
     */
    private String coordinatez;

    /**
     * 项目编码
     */
    private String projcode;

    /**
     * 所属单位工程编码
     */
    private String unitcode;

    /**
     * 创建时间
     */
    private Date creationtime;

    /**
     * 最后修改时间
     */
    private Date lastmodificationtime;

    /**
     * 是否删除
     */
    private Long isdeleted;

    /**
     * 删除时间
     */
    private Date deletiontime;


}

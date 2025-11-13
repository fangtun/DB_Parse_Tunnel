package org.dromara.tunnelenvironmentmonitor.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_equipment
 *
 * @author Lion Li
 * @date 2025-07-02
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_equipment")
public class TEquipment {

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
     * 安装地址
     */
    private String deviceaddress;

    /**
     * 设备序列号
     */
    private String devicesn;

    /**
     * 设备种类(0:气体传感器)
     */
    private Long equipmenttype;

    /**
     * 状态(0:离线 1:在线)
     */
    private Long status;

    /**
     * 位置(1:左洞 2:右洞)
     */
    private Long direction;

    /**
     * 海拔
     */
    private String elevation;

    /**
     * 内部X坐标
     */
    private String inlx;

    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 北向X坐标
     */
    private String nx;

    /**
     * 北向Y坐标
     */
    private String ny;

    /**
     * 平面X坐标
     */
    private String px;

    /**
     * 平面Y坐标
     */
    private String py;

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


}

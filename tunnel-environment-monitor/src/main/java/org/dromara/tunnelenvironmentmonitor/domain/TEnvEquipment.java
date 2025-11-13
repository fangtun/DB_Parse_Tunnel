package org.dromara.tunnelenvironmentmonitor.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_env_equipment
 *
 * @author Lion Li
 * @date 2025-06-30
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_env_equipment")
public class TEnvEquipment {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境设备ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 设备编码
     */
    private String devicecode;

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
     * 设备种类(0:通风设备 1:气体传感器 2：粉尘传感器)
     */
    private Long envequipmenttype;

    /**
     * 气体类型(0:甲烷 1:一氧化碳 2:二氧化硫)
     */
    private Long gastype;

    /**
     * 粉尘类型(0:PM2.5 1:PM10)
     */
    private Long dusttype;

    /**
     * 浓度
     */
    private Long concentration;

    /**
     * 低报警阈值
     */
    private Long lowthreshold;

    /**
     * 高报警阈值
     */
    private Long highthreshold;

    /**
     * 状态(0:离线 1:在线)
     */
    private Long status;

    /**
     * 位置(1:左洞 2:右洞)
     */
    private Long direction;

    /**
     * 隧洞名称
     */
    private String regionname;

    /**
     * 经度
     */
    private Long lon;

    /**
     * 纬度
     */
    private Long lat;

    /**
     * 运行时长
     */
    private Long workingtime;

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

    /**
     * 设备经度
     */
    private Long devicelon;

    /**
     * 设备纬度
     */
    private Long devicelat;


}

package org.dromara.tunnelenvironmentmonitor.domain.vo;

import java.util.Date;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.tunnelenvironmentmonitor.domain.TEnvEquipment;

import java.io.Serial;
import java.io.Serializable;



/**
 * 【请填写功能名称】视图对象 t_env_equipment
 *
 * @author Lion Li
 * @date 2025-06-30
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TEnvEquipment.class)
public class TEnvEquipmentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境设备ID
     */
    @ExcelProperty(value = "环境设备ID")
    private Long id;

    /**
     * 设备编码
     */
    @ExcelProperty(value = "设备编码")
    private String devicecode;

    /**
     * 设备名称
     */
    @ExcelProperty(value = "设备名称")
    private String name;

    /**
     * 安装地址
     */
    @ExcelProperty(value = "安装地址")
    private String deviceaddress;

    /**
     * 设备序列号
     */
    @ExcelProperty(value = "设备序列号")
    private String devicesn;

    /**
     * 设备种类(0:通风设备 1:气体传感器 2：粉尘传感器)
     */
    @ExcelProperty(value = "设备种类(0:通风设备 1:气体传感器 2：粉尘传感器)")
    private Long envequipmenttype;

    /**
     * 气体类型(0:甲烷 1:一氧化碳 2:二氧化硫)
     */
    @ExcelProperty(value = "气体类型(0:甲烷 1:一氧化碳 2:二氧化硫)")
    private Long gastype;

    /**
     * 粉尘类型(0:PM2.5 1:PM10)
     */
    @ExcelProperty(value = "粉尘类型(0:PM2.5 1:PM10)")
    private Long dusttype;

    /**
     * 浓度
     */
    @ExcelProperty(value = "浓度")
    private Long concentration;

    /**
     * 状态(0:离线 1:在线)
     */
    @ExcelProperty(value = "状态(0:离线 1:在线)")
    private Long status;

    /**
     * 位置(1:左洞 2:右洞)
     */
    @ExcelProperty(value = "位置(1:左洞 2:右洞)")
    private Long direction;

    /**
     * 经度
     */
    @ExcelProperty(value = "经度")
    private Long lon;

    /**
     * 纬度
     */
    @ExcelProperty(value = "纬度")
    private Long lat;

    /**
     * 运行时长
     */
    @ExcelProperty(value = "运行时长")
    private Long workingtime;

    /**
     * 项目编码
     */
    @ExcelProperty(value = "项目编码")
    private String projcode;

    /**
     * 所属单位工程编码
     */
    @ExcelProperty(value = "所属单位工程编码")
    private String unitcode;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date creationtime;

    /**
     * 创建用户
     */
    @ExcelProperty(value = "创建用户")
    private Long creatoruserid;

    /**
     * 最后修改时间
     */
    @ExcelProperty(value = "最后修改时间")
    private Date lastmodificationtime;

    /**
     * 最后修改用户
     */
    @ExcelProperty(value = "最后修改用户")
    private Long lastmodifieruserid;

    /**
     * 是否删除
     */
    @ExcelProperty(value = "是否删除")
    private Long isdeleted;

    /**
     * 删除用户
     */
    @ExcelProperty(value = "删除用户")
    private Long deleteruserid;

    /**
     * 删除时间
     */
    @ExcelProperty(value = "删除时间")
    private Date deletiontime;


}

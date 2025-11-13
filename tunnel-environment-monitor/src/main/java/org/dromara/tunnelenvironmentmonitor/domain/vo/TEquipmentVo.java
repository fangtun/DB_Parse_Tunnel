package org.dromara.tunnelenvironmentmonitor.domain.vo;

import java.util.Date;
import org.dromara.tunnelenvironmentmonitor.domain.TEquipment;
import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;



/**
 * 【请填写功能名称】视图对象 t_equipment
 *
 * @author Lion Li
 * @date 2025-07-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TEquipment.class)
public class TEquipmentVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境设备ID
     */
    @ExcelProperty(value = "环境设备ID")
    private Long id;

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
     * 设备种类(0:气体传感器)
     */
    @ExcelProperty(value = "设备种类(0:气体传感器)")
    private Long equipmenttype;

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
     * 海拔
     */
    @ExcelProperty(value = "海拔")
    private String elevation;

    /**
     * 内部X坐标
     */
    @ExcelProperty(value = "内部X坐标")
    private Long inlx;

    /**
     * 经度
     */
    @ExcelProperty(value = "经度")
    private String lon;

    /**
     * 纬度
     */
    @ExcelProperty(value = "纬度")
    private String lat;

    /**
     * 北向X坐标
     */
    @ExcelProperty(value = "北向X坐标")
    private Long nx;

    /**
     * 北向Y坐标
     */
    @ExcelProperty(value = "北向Y坐标")
    private Long ny;

    /**
     * 平面X坐标
     */
    @ExcelProperty(value = "平面X坐标")
    private Long px;

    /**
     * 平面Y坐标
     */
    @ExcelProperty(value = "平面Y坐标")
    private Long py;

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

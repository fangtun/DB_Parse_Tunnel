package org.dromara.tunnelsecurityguarantee.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

import java.io.Serial;

/**
 * 对象 t_person
 *
 * @author Lion Li
 * @date 2025-06-26
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_person")
public class Person {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 人员ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 职务
     */
    private String duty;

    /**
     * 岗位
     */
    private String station;

    /**
     * 状态(0:离线 1:在线)
     */
    private Long status;

    /**
     * 位置(1:左洞 2:右洞)
     */
    private Long direction;

    /**
     * 经度
     */
    private String lon;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 距离洞口：米
     */
    private BigDecimal inlx;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 入场时间
     */
    private Date comingtime;

    /**
     * 离场时间
     */
    private Date leavingtime;

    /**
     * 工作时间
     */
    private Long workingtime;

    /**
     * 项目代码
     */
    private String projcode;

    /**
     * 所属单位工程
     */
    private String unitcode;

    /**
     * 员工编号
     */
    private String empcode;

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
     * 坐标X
     */
    private String coordinatex;

    /**
     * 坐标Y
     */
    private String coordinatey;

    /**
     * 坐标Z
     */
    private String coordinatez;

    /**
     * 桩号：米
     */
    private String pileno;

    /**
     * 解析后桩号：米
     */
    private BigDecimal processedpileno;

    private  String peojectName;

    private  String unitName;


}

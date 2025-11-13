package org.dromara.tunnelsecurityguarantee.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_carsinfo
 *
 * @author Lion Li
 * @date 2025-06-26
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_carsinfo")
public class Carsinfo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 车辆名称
     */
    private String name;

    /**
     * 车辆种类
     */
    private String cartype;

    /**
     * 车牌号
     */
    private String licenseplatenum;

    /**
     * 联系方式
     */
    private String contact;

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
     * 入场时间
     */
    private Date comingtime;

    /**
     * 离场时间
     */
    private Date leavingtime;

    /**
     * 运行时长
     */
    private Long workingtime;

    /**
     * 项目代码
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

    private  String peojectName;

    private  String unitName;

    private  String carsuniqueid;

}

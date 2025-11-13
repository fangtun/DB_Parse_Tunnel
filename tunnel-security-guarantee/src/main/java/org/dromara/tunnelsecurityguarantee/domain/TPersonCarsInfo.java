package org.dromara.tunnelsecurityguarantee.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_person_cars_info
 *
 * @author Lion Li
 * @date 2025-06-27
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_person_cars_info")
public class TPersonCarsInfo  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 名称(人员姓名/车辆车牌号)
     */
    private String name;

    /**
     * 人员车辆区分(0:人员 1:车辆)
     */
    private Integer type;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 职务/类型
     */
    private String duty;

    /**
     * 岗位
     */
    private String station;

    /**
     * 状态(0:正常 1:求救报警)
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
     * 工号
     */
    private String worknumber;

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

    private  String peojectName;

    private  String unitName;


}

package org.dromara.tunnelenvironmentmonitor.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 【请填写功能名称】对象 t_unit_warning
 *
 * @author Lion Li
 * @date 2025-07-14
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_unit_warning")
public class TUnitWarning {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 警告ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 状态(0:未处理 1:已解决)
     */
    private Long status;

    /**
     * 项目代码
     */
    private String projcode;

    /**
     * 所属单位工程编码
     */
    private String unitcode;

    /**
     * 警告内容
     */
    private String warningcontent;

    /**
     * 设备序列号
     */
    private String devicesn;

    /**
     * 设备编码
     */
    private String devicecode;

    /**
     * 接口推送的报警ID
     */
    private String alarmid;

    /**
     * 报警开始时间
     */
    private Date starttime;

    /**
     * 报警结束时间
     */
    private Date endtime;

    /**
     * 创建时间
     */
    private Date creationtime;

    /**
     * 创建用户
     */
    private Long creatoruserid;

    /**
     * 最后修改事件
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

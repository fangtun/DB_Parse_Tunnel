package org.dromara.tunnelbaseinfo.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 隧道-基本信息对象 pcms_tunnel_base_information
 *
 * @author Lion Li
 * @date 2025-07-07
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_base_information")
public class PcmsTunnelBaseInformation {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "F_Id")
    private String fId;

    /**
     * 路科表中Id
     */
    private String fOriginalid;

    /**
     * 项目编码
     */
    private String fProjectcode;

    /**
     * 标段Id
     */
    private String fContractcode;

    /**
     * 单位工程（隧道）编码
     */
    private String fTunnelcode;

    /**
     * 单位工程（隧道）名称
     */
    private String fTunnelname;

    /**
     * wbsId
     */
    private String fWbsid;

    /**
     * 建设单位
     */
    private String fConstructionunit;

    /**
     * 施工单位
     */
    private String fConstructioncompany;

    /**
     * 监理单位
     */
    private String fSupervisoryunit;

    /**
     * 设计单位
     */
    private String fDesignunit;

    /**
     * 建设规模
     */
    private String fConstructionscale;

    /**
     * 设计时速
     */
    private String fDesignspeed;

    /**
     * 总投资（万元）
     */
    private Long fTotalinvestment;

    /**
     * 状态 0 未开工 1 已开工 2 已交工
     */
    private Long fStatus;

    /**
     * 部门id
     */
    private Long deptid;

    /**
     *
     */
    private String fCreatoruserid;

    /**
     * 创建时间
     */
    private Date fCreatortime;

    /**
     *
     */
    private String fLastmodifyuserid;

    /**
     * 修改时间
     */
    private Date fLastmodifytime;

    /**
     *
     */
    private String fDeleteuserid;

    /**
     * 删除时间
     */
    private Date fDeletetime;

    /**
     * 有效标志
     */
    private Long fEnabledmark;

    /**
     * 删除标志
     */
    private Long fDeletemark;

    private  String leftlength;

    private  String rightlength;

    private int total   ; //总隧道数
    private String totalmileage; //总里程
    private String completemileage; //完成里程
    private String totalinvestment; //总投资额
    private String completedinvestment; //完成投资额


}

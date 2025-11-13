package org.dromara.tunnelbaseinfo.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 隧道-计量进度对象 pcms_tunnel_metering_progress
 *
 * @author Lion Li
 * @date 2025-08-22
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_metering_progress")
public class PcmsTunnelMeteringProgress {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "F_Id")
    private String fId;

    /**
     * 项目编码
     */
    private String fProjectcode;

    /**
     * 标段Id
     */
    private String fContractcode;

    /**
     * 单位工程（隧道）Id
     */
    private String fTunnelid;

    /**
     * wbsId
     */
    private String fWbsid;

    /**
     * 出入口，进口是1，出口是2
     */
    private Long fIsentrance;

    /**
     * 统计时间
     */
    private Date fStatisticaltime;

    /**
     * 分部分项Id
     */
    private String fSubsectionid;

    /**
     * 分部分项名称
     */
    private String fSubsectionname;

    /**
     * 完成金额
     */
    private Long fCompletedamount;

    /**
     * 总金额
     */
    private Long fTotalamount;

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


}

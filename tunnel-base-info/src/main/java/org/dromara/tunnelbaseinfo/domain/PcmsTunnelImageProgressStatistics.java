package org.dromara.tunnelbaseinfo.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 隧道-形象进度统计对象 pcms_tunnel_image_progress_statistics
 *
 * @author Lion Li
 * @date 2025-08-23
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_image_progress_statistics")
public class PcmsTunnelImageProgressStatistics {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "F_Id")
    private Long fId;

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
     * 进尺深度
     */
    private Long fFootagedepth;

    /**
     * 桩号
     */
    private String fStakemark;

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

    /**
     * 开挖进尺
     */
    private Long fFootage;

    /**
     * 开挖桩号
     */
    private String fEndstation;

    /**
     * 仰拱进尺
     */
    private Long fArchfootage;

    /**
     * 仰拱桩号
     */
    private String fArchstation;

    /**
     * 二衬进尺
     */
    private Long fSecondfootage;

    /**
     * 二衬桩号
     */
    private String fSecondstation;

    /**
     * 围岩等级
     */
    private String fWallrocklevel;


}

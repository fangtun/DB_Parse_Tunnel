package org.dromara.tunnelbaseinfo.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 隧道-基本信息详情对象 pcms_tunnel_base_information_detail
 *
 * @author Lion Li
 * @date 2025-08-22
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_base_information_detail")
public class PcmsTunnelBaseInformationDetail {

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
     * 父Id
     */
    private String fUnitid;

    /**
     * 单位工程（隧道）名称
     */
    private String fTunnelname;

    /**
     * wbsId
     */
    private String fWbsid;

    /**
     * 左洞右洞，左洞是1，右洞是2
     */
    private Long fDirection;

    /**
     * 开始桩号
     */
    private String fStartstakenumber;

    /**
     * 结束桩号
     */
    private String fEndstakenumber;

    /**
     * 长度
     */
    private Long fLength;

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
     * 状态 0 未开工 1 已开工 2 已交工
     */
    private Long fStatus;


}

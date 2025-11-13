package org.dromara.tunnelintelligentconstruction.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 大机设备养在线离线记录对象 t_large_scale_machinery_status
 *
 * @author Lion Li
 * @date 2025-08-13
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_large_scale_machinery_status")
public class TLargeScaleMachineryStatus {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 设备序列号
     */
    private String devicesn;

    /**
     * 状态(0:离线 1:在线)
     */
    private Long status;

    /**
     * 创建时间
     */
    private Date creationtime;

    /**
     * 最后修改时间
     */
    private Date lastmodificationtime;

    /**
     * 是否删除
     */
    private Long isdeleted;

    /**
     * 删除时间
     */
    private Date deletiontime;


}

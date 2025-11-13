package org.dromara.tunnelintelligentconstruction.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 大机设备养护状态对象 t_large_scale_machinery_worktime
 *
 * @author Lion Li
 * @date 2025-08-13
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_large_scale_machinery_worktime")
public class TLargeScaleMachineryWorktime {

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
     * 养护状态(0:开始 1:结束)
     */
    private Long status;

    /**
     * 开始/结束时间
     */
    private Date timestamp;

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

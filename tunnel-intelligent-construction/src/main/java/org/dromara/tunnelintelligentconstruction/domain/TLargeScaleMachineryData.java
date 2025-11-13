package org.dromara.tunnelintelligentconstruction.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serial;

/**
 * 环境设备对象 t_large_scale_machinery_data
 *
 * @author Lion Li
 * @date 2025-08-12
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@TableName("t_large_scale_machinery_data")
public class TLargeScaleMachineryData {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 环境设备ID
     */
    @TableId(value = "Id")
    private Long id;

    /**
     * 设备序列号
     */
    private String devicesn;

    /**
     * key值
     */
    private String metrickey;

    /**
     * value值
     */
    private String metricvalue;

    /**
     * 计量单位
     */
    private String metricunit;

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

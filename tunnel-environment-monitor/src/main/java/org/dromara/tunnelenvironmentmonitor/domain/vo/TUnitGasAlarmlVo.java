package org.dromara.tunnelenvironmentmonitor.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 气体报警列表
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TUnitGasAlarmlVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 警告表字段
    private String WarningContent;
    private Date StartTime;

    // 项目表字段
    private String proName;

    // 隧道表字段
    private String unitName;
}

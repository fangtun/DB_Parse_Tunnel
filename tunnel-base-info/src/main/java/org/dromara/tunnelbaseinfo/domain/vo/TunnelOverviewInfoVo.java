package org.dromara.tunnelbaseinfo.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 隧道安全保证-隧道概况
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TunnelOverviewInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int total   ; //总隧道数
    private String totalMileage; //总里程
    private String completeMileage; //完成里程
    private String totalInvestment; //总投资额
    private String completedInvestment; //完成投资额

}

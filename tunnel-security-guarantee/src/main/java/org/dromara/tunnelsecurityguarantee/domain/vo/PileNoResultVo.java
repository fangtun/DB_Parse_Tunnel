package org.dromara.tunnelsecurityguarantee.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 隧道安全保证-获取类型相同的的多组平面数据结果
 *
 * @author zzt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PileNoResultVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal x;
    private BigDecimal y;
    private Double j;
    private Double zh;
    private String dlzh;
    private Integer zy;


}

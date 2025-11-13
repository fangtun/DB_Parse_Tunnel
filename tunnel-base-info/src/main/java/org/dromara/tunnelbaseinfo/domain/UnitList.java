package org.dromara.tunnelbaseinfo.domain;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;

@Data
public class UnitList {
    private String unitCode;
    private String unitName;
    private String projectCode;
    private BigDecimal lon;
    private BigDecimal lat;
}

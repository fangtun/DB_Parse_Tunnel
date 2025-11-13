package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DustValueData {
    private Integer DustType;
    private Integer Direction;
    private BigDecimal concentration;
    private Date DateTimes;
}


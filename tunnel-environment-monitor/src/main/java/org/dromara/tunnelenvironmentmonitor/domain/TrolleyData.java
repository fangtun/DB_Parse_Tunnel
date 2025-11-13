package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TrolleyData {
    private Integer alarm;
    private String deviceSn;
    private String elevation;
    private Double inlX;
    private Integer isInTunnel;
    private String latitude;
    private String longitude;
    private Double nX;
    private Double nXMajor;
    private Double nY;
    private Double pX;
    private Double pY;
    private String pileNo;
    private String projectId;
    private String regionCode;
    private String regionId;
    private String regionName;
    private String ringNum;
    private String trolleyId;
    private String trolleyName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer volume;
    private String workareaId;
    private BigDecimal coordinatex;
    private BigDecimal coordinatey;
    private BigDecimal coordinatez;
}

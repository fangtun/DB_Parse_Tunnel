package org.dromara.tunnelsecurityguarantee.domain;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SecurityGuaranteeData {
    private Integer alarm;
    private String cardNo;
    private Integer cardType;
    private String companyId;
    private String companyName;
    private String department;
    private String deviceName;
    private String deviceSn;
    private String elevation;
    private String firstTime;
    private Integer frequency;
    private String gender;
    private Integer heartRate;
    private String idNumber;
    private Integer isInTunnel;
    private Double inlX;
    private Integer speed;
    private String lastTime;
    private String latitude;
    private String longitude;
    private Double nX;
    private Integer nXMajor;
    private Double nY;
    private Integer oximeter;
    private Double pX;
    private Double pY;
    private String personId;
    private String personName;
    private String phone;
    private String pileNo;
    private String plateNumber;
    private String projectId;
    private String regionCode;
    private String regionId;
    private String regionName;
    private String ringNum;
    private Integer stayTime;
    private String teamId;
    private String teamName;
    private String updateTime;
    private String vehicleType;
    private Integer volume;
    private String workTypeName;
    private String workareaId;
    private BigDecimal coordinatex;
    private BigDecimal coordinatey;
    private BigDecimal coordinatez;
}

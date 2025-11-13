package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;

@Data
public class OnlineDeviceData {
    private String deviceSn;
    private Integer onlineStatus;
    private String projectId;
    private String updateTime;
}


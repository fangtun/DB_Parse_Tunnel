package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;

@Data
public class EnvironmentMonitorData {
    private Integer alarmLevel;
    private String deviceAddress;
    private String deviceCode;
    private String deviceName;
    private String deviceSn;
    private String displayText;
    private Double highAlarm;
    private Double lowAlarm;
    private Integer onlineStatus;
    private String projectId;
    private String regionCode;
    private String regionId;
    private String regionName;
    private String sensorName;
    private Number sensorValue;
    private String symbols;
    private String unitValue;
    private String updateTime;
    private String workareaId;
}

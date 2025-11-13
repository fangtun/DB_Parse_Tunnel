package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;

@Data
public class EnvironmentAlarmData {
    private String alarmId;
    private Integer alarmLevel;
    private String deviceAddress;
    private String deviceCode;
    private String deviceName;
    private String deviceSn;
    private String displayText;
    private Integer durationTime;
    private String firstTime;
    private String handleInfo;
    private Long higAlarmTime;
    private Integer highAlarm;
    private String lastTime;
    private Integer lowAlarm;
    private Integer onLine;
    private String projectId;
    private String regionCode;
    private String regionId;
    private String regionName;
    private String sensorName;
    private Integer sensorValue;
    private String symbols;
    private String unitValue;
    private String updateTime;
    private String uploadStatus;
    private String workareaId;
    private String workareaName;
}

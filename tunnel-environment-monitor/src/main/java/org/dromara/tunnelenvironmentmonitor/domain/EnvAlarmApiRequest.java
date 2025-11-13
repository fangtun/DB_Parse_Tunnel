package org.dromara.tunnelenvironmentmonitor.domain;

import lombok.Data;

import java.util.List;

@Data
public class EnvAlarmApiRequest {
    private Integer count;
    private List<EnvironmentAlarmData> data;
    private String wholeId;
}

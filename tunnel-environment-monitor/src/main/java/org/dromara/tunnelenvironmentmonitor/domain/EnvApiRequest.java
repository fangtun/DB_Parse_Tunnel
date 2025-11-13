package org.dromara.tunnelenvironmentmonitor.domain;

import lombok.Data;

import java.util.List;

@Data
public class EnvApiRequest {
    private Integer count;
    private List<EnvironmentMonitorData> data;
    private String wholeId;
}

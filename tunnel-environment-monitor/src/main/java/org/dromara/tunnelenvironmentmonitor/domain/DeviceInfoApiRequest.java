package org.dromara.tunnelenvironmentmonitor.domain;

import lombok.Data;

import java.util.List;

@Data
public class DeviceInfoApiRequest {
    private Integer count;
    private List<DeviceInfoData> data;
    private Integer deviceTotal;
    private List<OnlineDeviceData> onlineData;
    private String wholeId;
}

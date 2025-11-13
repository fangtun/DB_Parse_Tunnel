package org.dromara.tunnelintelligentconstruction.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 智能建造-智能设备数据
 *
 * @author zzt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LargeScaleMachineryDataVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 设备名称
    private String name;
    // 设备编号
    private String devicesn;
    // 设备位置-桩号
    private String pileno;
    // 设备养护状态（）
    private String dollystate;
    // 设备养护时长
    private String workingtime;

    private String metrickey;
    private String metricvalue;
    private String leftwataertankStatus;
    private String rightwataertankStatus;

    // 显示定位用 location 字段
    private Location location;

    @Data
    public static class Location{
        private String x;
        private String y;
        private String z;

        public Location(String x, String y, String z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // 大机数据 - 使用通用Map结构支持任意传感器类型
    private Map<String, Map<String, String>> sensorData = new HashMap<>();

    // 提供便捷方法
    public void addSensorType(String sensorType, Map<String, String> data) {
        this.sensorData.put(sensorType, data);
    }

    public Map<String, String> getSensorType(String sensorType) {
        return this.sensorData.get(sensorType);
    }

    public boolean hasSensorType(String sensorType) {
        return this.sensorData.containsKey(sensorType);
    }

    // 获取所有传感器类型
    public java.util.Set<String> getSensorTypes() {
        return this.sensorData.keySet();
    }

    // 获取传感器数据大小
    public int getSensorTypeCount() {
        return this.sensorData.size();
    }

}

package org.dromara.tunnelintelligentconstruction.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 智能建造-智能设备列表
 *
 * @author zzt
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LargeScaleMachineryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 设备名称
    private String name;
    // 设备编号
    private String devicesn;
    // 设备状态
    private Long status;
    // 设备运行时长
    private String runduration;
    // 隧道方向
    private String direction;
    // 桩号
    private String pileNo;

    // 显示定位用 location 字段
    private Location location;

    private String peojectname;
    private String unitname;

    @JsonIgnore
    private String coordinatex;
    @JsonIgnore
    private String coordinatey;
    @JsonIgnore
    private String coordinatez;
    @JsonIgnore
    private String CarsCardNo;




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

        public Location(Location coordinateX, Location coordinateY, Location coordinateZ) {
        }
    }

}

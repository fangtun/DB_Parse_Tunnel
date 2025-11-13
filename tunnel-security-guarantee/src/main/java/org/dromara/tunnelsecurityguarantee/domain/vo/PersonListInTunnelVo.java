package org.dromara.tunnelsecurityguarantee.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 隧道安全保证-人员列表
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class PersonListInTunnelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 人员表字段
    private String personName;
    private Date comingTime;
    private String direction;
    private String duty;
    private String contact;
    private BigDecimal inlx;

    // 项目表字段
    private String proName;

    // 隧道表字段
    private String unitName;

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
}

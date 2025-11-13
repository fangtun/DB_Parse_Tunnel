package org.dromara.tunnelsecurityguarantee.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 隧道安全保证-人员车辆列表
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class PersonCarsListInTunnelVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 人员车辆表字段
    private String Name;
    private Date comingTime;
    private String direction;
    private String duty;
    private String contact;

    // 项目表字段
    private String proName;

    // 隧道表字段
    private String unitName;
}

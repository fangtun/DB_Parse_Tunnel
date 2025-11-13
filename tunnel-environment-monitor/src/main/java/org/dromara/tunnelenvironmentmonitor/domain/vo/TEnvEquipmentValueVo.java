package org.dromara.tunnelenvironmentmonitor.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 【请填写功能名称】视图对象 t_env_equipment
 *
 * @author Lion Li
 * @date 2025-06-30
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TEnvEquipmentValueVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 气体类型(0:甲烷 1:一氧化碳 2:硫化氢 3:氧气)
     */
    @ExcelProperty(value = "气体类型(0:甲烷 1:一氧化碳 2:硫化氢 3:氧气)")
    private Long gastype;

    /**
     * 浓度
     */
    @ExcelProperty(value = "浓度")
    private Long concentration;

    /**
     * 位置(1:左洞 2:右洞)
     */
    @ExcelProperty(value = "位置(1:左洞 2:右洞)")
    private Long direction;


    /**
     * 日期
     */
    @ExcelProperty(value = "日期")
    private Date dates;


    @ExcelProperty(value = "设备序列号")
    private String devicesn;

    @ExcelProperty(value = "隧洞名称")
    private String regionname;

    @ExcelProperty(value = "低报警阈值")
    private String lowthreshold;

    @ExcelProperty(value = "高报警阈值")
    private String highthreshold;

}

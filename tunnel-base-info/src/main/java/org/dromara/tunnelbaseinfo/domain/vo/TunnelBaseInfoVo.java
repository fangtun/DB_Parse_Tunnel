package org.dromara.tunnelbaseinfo.domain;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 隧道安全保证-隧道基本信息
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TunnelBaseInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 隧道表字段
    private String name; //项目名称
    private String constructionUnit; //建设单位
    private String constructionCompany; //施工单位
    private String supervisoryUnit; //监理单位
    private String designUnit; //设计单位
    private String leftLength; //左洞长度
    private String rightLength; //右洞长度
    private String constructionScale; //建设规模
    private String designSpeed; //设计速度

}

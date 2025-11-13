package org.dromara.tunnelmonitorvideo.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorYsyCamera;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 隧道安全保证-隧道基本信息
 *
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TunnelBaseInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String projectName;
    private String unitName;
    private String unitCode;

}

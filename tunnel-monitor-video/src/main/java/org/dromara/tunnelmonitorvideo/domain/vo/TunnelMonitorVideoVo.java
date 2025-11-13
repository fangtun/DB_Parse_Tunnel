package org.dromara.tunnelmonitorvideo.domain.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorYsyCamera;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 隧道安全保证-隧道基本信息
 *
 * @author zzt
 */
@Data
@ExcelIgnoreUnannotated
@AllArgsConstructor
@NoArgsConstructor
public class TunnelMonitorVideoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String ProjectName;
    private String UnitName;
    private List<PcmsTunnelMonitorYsyCamera> caremaList;

}

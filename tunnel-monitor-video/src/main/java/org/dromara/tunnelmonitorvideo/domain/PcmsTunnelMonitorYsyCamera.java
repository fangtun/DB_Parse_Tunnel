package org.dromara.tunnelmonitorvideo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 隧道安全保证-隧道基本信息
 *
 * @author zzt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_monitor_ysy_carema")
public class PcmsTunnelMonitorYsyCamera extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long fId;//	ID主键
    private String fDeviceSerial;//	设备序列号
    private String fChannelNo;//	通道号
    private String fChannelName;//	通道名
    private String fNewName;//	重命名后监控名称
    private String fProjectCode;//	所属项目
    private String fContractCode;//	所属标段
    private String fUnitCode;//	所属工点
    private String fKeyId;//	密钥ID
    private long fLat;//	纬度
    private long fLon;//	经度
    private long  fHeight;//	高度

    private String url;//	重命名后监控名称
    private String accessToken;//	重命名后监控名称



}

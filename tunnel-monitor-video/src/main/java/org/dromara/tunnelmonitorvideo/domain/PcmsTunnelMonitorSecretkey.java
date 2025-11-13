package org.dromara.tunnelmonitorvideo.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 隧道安全保证-监控APP密钥信息
 *
 * @author zzt
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pcms_tunnel_monitor_secretkey")
public class PcmsTunnelMonitorSecretkey extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long fId;//	ID主键
    private String fAppKey;//	AppKey
    private String fAppSecret;//	AppSecret
    private String fSecretName;//	密钥名称


}

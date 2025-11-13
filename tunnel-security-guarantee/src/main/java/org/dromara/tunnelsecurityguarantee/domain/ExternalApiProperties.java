package org.dromara.tunnelsecurityguarantee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 隧道安全保证-获取类型相同的的多组平面数据结果
 *
 * @author zzt
 */
@Data
@Component
@ConfigurationProperties(prefix = "external")
public class ExternalApiProperties {

    private Pm pm;
    private Zdcz zdcz;

    @Data
    public static class Pm {
        private String url;
    }

    @Data
    public static class Zdcz {
        private String url;
    }

}

package org.dromara.tunnelsecurityguarantee.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityGuaranteeConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

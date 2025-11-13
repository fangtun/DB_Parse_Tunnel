package org.dromara.tunnelsecurityguarantee.domain;
import lombok.Data;
import java.util.List;

@Data
public class ApiRequest {
    private Integer count;
    private List<SecurityGuaranteeData> data;
    private String wholeId;
}

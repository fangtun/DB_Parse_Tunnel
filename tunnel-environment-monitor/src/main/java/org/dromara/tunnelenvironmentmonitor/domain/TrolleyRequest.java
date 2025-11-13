package org.dromara.tunnelenvironmentmonitor.domain;
import lombok.Data;
import org.dromara.tunnelsecurityguarantee.domain.SecurityGuaranteeData;

import java.util.List;

@Data
public class TrolleyRequest {
    private Integer count;
    private List<TrolleyData> data;
    private String wholeId;
}

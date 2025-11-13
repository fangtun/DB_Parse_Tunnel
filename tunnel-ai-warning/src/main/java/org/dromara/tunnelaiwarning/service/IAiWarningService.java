package org.dromara.tunnelaiwarning.service;

import java.util.Map;


/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface IAiWarningService {

    /**
     * 新增AI报警
     *
     * @param requestData AI报警信息
     * @return 列表
     */
    int insertAiWarnning(Map<String, Object> requestData);
}

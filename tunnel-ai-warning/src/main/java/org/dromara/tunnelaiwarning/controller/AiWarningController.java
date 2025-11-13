package org.dromara.tunnelaiwarning.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.tunnelaiwarning.service.IAiWarningService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/aiwarning")
public class AiWarningController {

    private final IAiWarningService aiWarningService;

    /**
     * 接收AI报警数据
     *
     * @param requestData AI报警数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadAiWarningDataList")
    public R<Object> aiWarning(@RequestBody Map<String, Object> requestData) {
        int rows = aiWarningService.insertAiWarnning(requestData);
        if (rows > 0) {
            return R.ok("success");
        }else {
            return R.fail("fail");
        }
    }

}

package org.dromara.tunnelintelligentconstruction.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryDataVo;
import org.dromara.tunnelintelligentconstruction.domain.vo.LargeScaleMachineryVo;
import org.dromara.tunnelintelligentconstruction.service.ILargeScaleMachineryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/largescalemachinery")
public class LargeScaleMachineryController extends BaseController {

    private final ILargeScaleMachineryService largeScaleMachineryService;

    /**
     * 获取大机设备信息
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
    @SaIgnore
    @GetMapping("/largeScaleMachineryList")
    public TableDataInfo<LargeScaleMachineryVo> largeScaleMachineryList(String projectCode, String unitCode) {
        return largeScaleMachineryService.getLargeScaleMachineryList(projectCode, unitCode);
    }

    /**
     * 获取大机设备数据
     *
     * @param deviceSn 设备编码
     * @return 结果
     */
    @SaIgnore
    @GetMapping("/largeScaleMachineryDataList")
    public R<List<Map<String, Object>>> largeScaleMachineryDataList(String deviceSn) {
        return R.ok(largeScaleMachineryService.getLargeScaleMachineryDataList(deviceSn));
    }

    /**
     * 接收推送钻注锚设备基础信息
     *
     * @param requestData 设备基础信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadDeviceInfo")
    public R<Object> deviceInfo(@RequestBody Map<String, Object> requestData) {
        int rows = largeScaleMachineryService.saveDrillingAnchorDevice(requestData);
        if (rows > 0) {
            return R.ok("success");
        } else {
            return R.fail("fail");
        }
    }

    /**
     * 接收推送钻注锚设备数据
     *
     * @param requestData 钻注锚设备数据
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/uploadSensorDataList")
    public R<Object> uploadSensorDataList(@RequestBody Map<String, Object> requestData) {
        int rows = largeScaleMachineryService.saveDrillingAnchorSensorData(requestData);
        if (rows > 0) {
            return R.ok("success");
        } else {
            return R.fail("fail");
        }
    }


}

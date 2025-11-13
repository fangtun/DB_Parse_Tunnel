package org.dromara.tunnelmonitorvideo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelMonitorVideoVo;
import org.dromara.tunnelmonitorvideo.service.ITunnelMonitorVideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tunnelvideo")
public class TunnelMonitorVideoController {

    @Resource
    ITunnelMonitorVideoService tunnelMonitorVideoService;


    /**
     * 获取默认播放地址
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
    @SaCheckPermission("tunnelvideo:camera:base")
    @GetMapping("/getTunnelBaseVideoUrl")
    public R<Map<String, Object>> getTunnelBaseVideoUrl(String projectCode, String unitCode) {

        return R.ok(tunnelMonitorVideoService.getTunnelBaseVideoUrl(projectCode,unitCode));
    }
    /**
     * 获取播放列表
     *
     * @param projectCode 项目编号
     * @param unitCode    隧道编号
     * @return 结果
     */
    @SaCheckPermission("tunnelvideo:camera:list")
    @GetMapping("/getPlayList")
    public R<List<TunnelMonitorVideoVo>> getPlayList(String projectCode, String unitCode) {

        return R.ok(tunnelMonitorVideoService.getPlayList(projectCode,unitCode));
    }
    @SaCheckPermission("tunnelvideo:camera:url")
    @GetMapping("/getTunnelVideoUrl")
    public R<Map<String, Object>> getTunnelVideoUrl(Long id) {

        return R.ok(tunnelMonitorVideoService.getTunnelVideoUrl(id));
    }
}

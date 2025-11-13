package org.dromara.tunnelmonitorvideo.service;

import org.dromara.tunnelmonitorvideo.domain.vo.TunnelMonitorVideoVo;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 *
 * @author Lion Li
 */
public interface ITunnelMonitorVideoService {
    Map<String, Object> getTunnelBaseVideoUrl(String projectCode, String unitCode);

    List<TunnelMonitorVideoVo> getPlayList(String projectCode, String unitCode);

    Map<String, Object> getTunnelVideoUrl(Long id);

}

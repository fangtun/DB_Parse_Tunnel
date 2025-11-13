package org.dromara.tunnelmonitorvideo.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.MathArrays;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorSecretkey;
import org.dromara.tunnelmonitorvideo.domain.PcmsTunnelMonitorYsyCamera;
import org.dromara.tunnelmonitorvideo.domain.util.YsyUtils;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelBaseInfo;
import org.dromara.tunnelmonitorvideo.domain.vo.TunnelMonitorVideoVo;
import org.dromara.tunnelmonitorvideo.mapper.TunnelMonitorSecretkeyMapper;
import org.dromara.tunnelmonitorvideo.mapper.TunnelMonitorVideoMapper;
import org.dromara.tunnelmonitorvideo.service.ITunnelMonitorVideoService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 入场信息 业务层处理
 *
 * @author ZZT
 */
@Slf4j
@Service
public class TunnelMonitorVideoServiceImpl implements ITunnelMonitorVideoService {

    @Resource
    TunnelMonitorVideoMapper tunnelMonitorVideoMapper;
    @Resource
    TunnelMonitorSecretkeyMapper tunnelMonitorSecretkeyMapper;

    @Override
    public Map<String, Object> getTunnelBaseVideoUrl(String projectCode, String unitCode) {
        Map<String, Object> results = new HashMap<>();

        List<PcmsTunnelMonitorYsyCamera> caremas = tunnelMonitorVideoMapper.getTunnelVideoList(projectCode, unitCode);

        if (caremas == null || caremas.size() == 0) {
            return results;
        }
        PcmsTunnelMonitorYsyCamera carema = caremas.get(0);
        //根据设备编号获取播放地址和token
        String accessTokenRedisKey = "VIDEO#TOKEN#" + carema.getFDeviceSerial() + "_" + carema.getFChannelNo();
        String urlRedisKey = "VIDEO#URL#" + carema.getFDeviceSerial() + "_" + carema.getFChannelNo();

        if (!RedisUtils.hasKey(accessTokenRedisKey)) {
            //查询该项目所在的APPkey
            PcmsTunnelMonitorSecretkey key = tunnelMonitorSecretkeyMapper.getById(carema.getFKeyId());
            Map<String, Object> result = YsyUtils.getAccessToken(key.getFAppKey(), key.getFAppSecret());
            String accessToken = (String) result.get("accessToken");
            Long expireTime = (Long) result.get("expireTime");
            Instant start = Instant.ofEpochMilli(System.currentTimeMillis());
            Instant end = Instant.ofEpochMilli(expireTime);

            RedisUtils.setCacheObject(accessTokenRedisKey, accessToken, Duration.between(start, end));
        }
        String accessToken = RedisUtils.getCacheObject(accessTokenRedisKey);

        if (!RedisUtils.hasKey(urlRedisKey)) {
            String url = YsyUtils.getPlayAddress(accessToken, carema.getFDeviceSerial(), carema.getFChannelNo());

            RedisUtils.setCacheObject(urlRedisKey, url);

        }
        String url = RedisUtils.getCacheObject(urlRedisKey);

        results.put("url", url);
        results.put("accessToken", accessToken);
        return results;
    }

    @Override
    public List<TunnelMonitorVideoVo> getPlayList(String projectCode, String unitCode) {


        List<PcmsTunnelMonitorYsyCamera> caremas = tunnelMonitorVideoMapper.getTunnelVideoList(projectCode, unitCode);


        Map<String, List<PcmsTunnelMonitorYsyCamera>> map = caremas.stream().collect(Collectors.groupingBy(PcmsTunnelMonitorYsyCamera::getFUnitCode));

        List<TunnelBaseInfo> infoList = tunnelMonitorVideoMapper.getTunnelBaseInfo();
        Map<String, TunnelBaseInfo> infoMap = infoList.stream().collect(
            Collectors.toMap(TunnelBaseInfo::getUnitCode, Function.identity()));


        List<TunnelMonitorVideoVo> result = new ArrayList<>();
        map.forEach((String k, List<PcmsTunnelMonitorYsyCamera> v) -> {
            TunnelMonitorVideoVo vo = new TunnelMonitorVideoVo();
            vo.setProjectName(infoMap.containsKey(k)?infoMap.get(k).getProjectName():"");
            vo.setUnitName(infoMap.containsKey(k)?infoMap.get(k).getUnitName():"");
            vo.setCaremaList(v);
            result.add(vo);
        });
        return result;
    }

    @Override
    public Map<String, Object> getTunnelVideoUrl(Long id) {
        //TODO 获取用户信息，获取用户项目权限
        //查询
        PcmsTunnelMonitorYsyCamera carema = tunnelMonitorVideoMapper.getById(id);
        //根据设备编号获取播放地址和token
        String accessTokenRedisKey = "VIDEO#TOKEN#" + carema.getFDeviceSerial() + "_" + carema.getFChannelNo();
        String urlRedisKey = "VIDEO#URL#" + carema.getFDeviceSerial() + "_" + carema.getFChannelNo();

        if (!RedisUtils.hasKey(accessTokenRedisKey)) {
            //查询该项目所在的APPkey
            PcmsTunnelMonitorSecretkey key = tunnelMonitorSecretkeyMapper.getById(carema.getFKeyId());
            Map<String, Object> result = YsyUtils.getAccessToken(key.getFAppKey(), key.getFAppSecret());
            String accessToken = (String) result.get("accessToken");
            Long expireTime = (Long) result.get("expireTime");
            Instant start = Instant.ofEpochMilli(System.currentTimeMillis());
            Instant end = Instant.ofEpochMilli(expireTime);

            RedisUtils.setCacheObject(accessTokenRedisKey, accessToken, Duration.between(start, end));
        }
        String accessToken = RedisUtils.getCacheObject(accessTokenRedisKey);

        if (!RedisUtils.hasKey(urlRedisKey)) {
            String url = YsyUtils.getPlayAddress(accessToken, carema.getFDeviceSerial(), carema.getFChannelNo());

            RedisUtils.setCacheObject(urlRedisKey, url);

        }
        String url = RedisUtils.getCacheObject(urlRedisKey);

        Map<String, Object> result = new HashMap<>();
        result.put("url", url);
        result.put("accessToken", accessToken);
        return result;
    }
}

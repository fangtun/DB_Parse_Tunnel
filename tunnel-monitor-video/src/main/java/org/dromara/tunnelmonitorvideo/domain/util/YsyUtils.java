package org.dromara.tunnelmonitorvideo.domain.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class YsyUtils {

    public static  Map<String, Object> getAccessToken(String appKey, String appSecret) {
        Map<String, String> map = MapUtil.newHashMap();
        map.put("appKey", appKey);
        map.put("appSecret", appSecret);

        HttpResponse response = HttpUtil.createRequest(Method.POST, "https://open.ys7.com/api/lapp/token/get")
            .formStr(map)
            .executeAsync();
        String msg = new JSONObject(response.body()).getStr("data");
        JSONObject json = new JSONObject(msg);
        String accessToken = json.getStr("accessToken");
        Long expireTime = Long.valueOf(json.getStr("expireTime"));

        Map<String, Object> result =new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("expireTime", expireTime);

        return result;
    }

    public static String getPlayAddress(String accessToken, String deviceSerial, String channelNo) {
        Map<String, String> map = MapUtil.newHashMap();
        map.put("accessToken", accessToken);
        map.put("deviceSerial", deviceSerial);
        map.put("channelNo", channelNo);

        HttpResponse response = HttpUtil.createRequest(Method.POST, "https://open.ys7.com/api/lapp/v2/live/address/get")
            .formStr(map)
            .executeAsync();
        String result = response.body();
        String msg = new JSONObject(result).getStr("data");
        JSONObject json = new JSONObject(msg);
        String url = json.getStr("url");
        return url;
    }

    public void sendHttpPost() {

//        HttpClient httpClient = HttpClientBuilder.c().build();
//
//        // 创建请求参数
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("username", "admin"));
//        params.add(new BasicNameValuePair("password", "123456"));
//
//        // 创建 HttpPost 对象，并设置请求 URL
//        HttpPost httpPost = new HttpPost("http://example.com/login");
//
//        // 将请求参数设置到请求体中
//        httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//        // 执行 HttpPost 请求，并获取响应结果
//        HttpResponse response = httpClient.execute(httpPost);
    }
}

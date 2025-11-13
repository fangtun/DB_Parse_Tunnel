package org.dromara.tunnelintelligentconstruction.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.dromara.tunnelintelligentconstruction.service.ILargeScaleMachineryService;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class MqttConsumerCallBack implements MqttCallback {

    private final ILargeScaleMachineryService largeScaleMachineryService;

    @Autowired
    private MqttConsumerConfig client;

    @Autowired
    public MqttConsumerCallBack(ILargeScaleMachineryService largeScaleMachineryService) {
        this.largeScaleMachineryService = largeScaleMachineryService;
    }

    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        client.connect();
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String Str = new String(message.getPayload(), StandardCharsets.UTF_8);
            String decodedStr = StringEscapeUtils.unescapeJava(Str);
            log.info("收到消息内容[{}]{}", topic, Str);
            log.info("解码--收到消息内容[{}]{}", topic, decodedStr);
            String[] topic_parts = topic.split("/");
            if (topic_parts.length == 3 && topic_parts[0].equals("device") && topic_parts[1].equals("screen_status")) {
                String deviceSn = topic_parts[2];
                largeScaleMachineryService.insertLargeScaleMachinery(decodedStr,deviceSn);
            }else {
                largeScaleMachineryService.insertLargeScaleMachineryData(decodedStr);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println(String.format("接收消息成功"));
    }


}

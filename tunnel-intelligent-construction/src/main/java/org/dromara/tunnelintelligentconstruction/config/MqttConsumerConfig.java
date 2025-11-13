package org.dromara.tunnelintelligentconstruction.config;

import org.dromara.tunnelintelligentconstruction.service.ILargeScaleMachineryService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.KeyStore;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Base64;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
public class MqttConsumerConfig {

    @Autowired
    private ILargeScaleMachineryService largeScaleMachineryService;

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.consumer.id}")
    private String clientId;

    @Value("${spring.mqtt.client.consumer.topic}")
    private String consumerTopic;

    @Value("${spring.mqtt.client.consumer.qos}")
    private String consumerQos;

    @Value("${spring.mqtt.default.delay}")
    private String defaultDelay;
    /**
     * 客户端对象
     */
    private MqttClient client;

    /**
     * 在bean初始化后连接到服务器
     */
    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        connect();
    }

    public static String delay;


    @EventListener(ContextRefreshedEvent.class)
    public void getDelay() {
        delay = this.defaultDelay;
    }

    public static String area;

    /**
     * 客户端连接服务端
     */
    public void connect() {
        try {
            //创建MQTT客户端对象
            client = new MqttClient(hostUrl, clientId, new MemoryPersistence());
            //连接设置
            MqttConnectOptions options = new MqttConnectOptions();

            // 设置MQTTv5协议
            // options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_5);

            //是否清空session，设置为false表示服务器会保留客户端的连接记录，客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
            //设置为true表示每次连接到服务端都是以新的身份
            options.setCleanSession(true);
            //设置连接用户名
            options.setUserName(username);
            //设置连接密码
            options.setPassword(password.toCharArray());
            //设置超时时间，单位为秒
            options.setConnectionTimeout(100);
            //设置心跳时间 单位为秒，表示服务器每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
            options.setKeepAliveInterval(30);
            //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic", (clientId + "与服务器断开连接").getBytes(), 0, false);

            // 配置SSL
            configureSSL(options);

            //设置回调
            client.setCallback(new MqttConsumerCallBack(largeScaleMachineryService));
            options.setAutomaticReconnect(true);
            client.connect(options);
            //订阅主题
            //主题
            String[] topics = consumerTopic.split(",");
            //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息

            String[] qosStr = consumerQos.split(",");
            int[] qos = new int[qosStr.length];
            for (int i = 0; i < qosStr.length; i++) {
                qos[i] = Integer.valueOf(qosStr[i]);
            }
            //订阅主题
            client.subscribe(topics, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置SSL
     */
    private void configureSSL(MqttConnectOptions options) {
        try {
            // 创建SSL上下文
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // 创建KeyStore
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            // 加载CA证书到信任库
            ClassPathResource caCertResource = new ClassPathResource("certs/ca.crt");
            if (caCertResource.exists()) {
                try (InputStream caInput = caCertResource.getInputStream()) {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate caCert = (X509Certificate) cf.generateCertificate(caInput);
                    keyStore.setCertificateEntry("ca", caCert);
                    System.out.println("CA证书加载成功");
                }
            } else {
                System.err.println("警告: 未找到CA证书文件");
            }

            // 加载客户端证书和私钥
            ClassPathResource clientCertResource = new ClassPathResource("certs/client.crt");
            ClassPathResource clientKeyResource = new ClassPathResource("certs/client.key");

            if (clientCertResource.exists() && clientKeyResource.exists()) {
                // 加载客户端证书
                Certificate clientCert = loadCertificate(clientCertResource);

                // 加载客户端私钥
                PrivateKey clientKey = loadPrivateKey(clientKeyResource);

                if (clientCert != null && clientKey != null) {
                    // 构建证书链（客户端证书 + CA证书）
                    Certificate[] certChain = new Certificate[2];
                    certChain[0] = clientCert;

                    // 添加CA证书到证书链
                    if (caCertResource.exists()) {
                        try (InputStream caInput = caCertResource.getInputStream()) {
                            CertificateFactory cf = CertificateFactory.getInstance("X.509");
                            X509Certificate caCert = (X509Certificate) cf.generateCertificate(caInput);
                            certChain[1] = caCert;
                        }
                    }

                    // 将客户端密钥和证书添加到KeyStore
                    keyStore.setKeyEntry("client", clientKey, "keypassword".toCharArray(), certChain);
                    System.out.println("客户端证书和密钥加载成功");
                }
            } else {
                System.err.println("警告: 未找到客户端证书或密钥文件");
            }

            // 创建TrustManagerFactory（验证服务器证书）
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // 创建KeyManagerFactory（客户端证书认证）
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, "keypassword".toCharArray());

            // 初始化SSL上下文
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            options.setSocketFactory(sslContext.getSocketFactory());
            System.out.println("SSL配置完成");

        } catch (Exception e) {
            System.err.println("SSL配置失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 加载证书
     */
    private Certificate loadCertificate(ClassPathResource resource) {
        try (InputStream in = resource.getInputStream()) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return cf.generateCertificate(in);
        } catch (Exception e) {
            System.err.println("证书加载失败: " + e.getMessage());
            return null;
        }
    }

    /**
     * 加载密钥
     */
    private PrivateKey loadPrivateKey(ClassPathResource resource) {
        try (InputStream in = resource.getInputStream()) {
            String pem = new String(in.readAllBytes());

            // 检查私钥格式
            if (pem.contains("-----BEGIN PRIVATE KEY-----")) {
                // PKCS#8格式
                String privateKeyPEM = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
                byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return keyFactory.generatePrivate(keySpec);

            } else if (pem.contains("-----BEGIN RSA PRIVATE KEY-----")) {
                // PKCS#1格式 - 使用BouncyCastle处理
                String privateKeyPEM = pem.replace("-----BEGIN RSA PRIVATE KEY-----", "")
                    .replace("-----END RSA PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

                byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);

                // 使用BouncyCastle解析PKCS#1私钥
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

                // PKCS#1是RSAPrivateKey结构，不是PKCS#8的PrivateKeyInfo结构
                // 需要使用BouncyCastle的特定类来处理
                org.bouncycastle.asn1.pkcs.RSAPrivateKey rsaPrivateKey =
                    org.bouncycastle.asn1.pkcs.RSAPrivateKey.getInstance(decoded);

                RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(
                    rsaPrivateKey.getModulus(),
                    rsaPrivateKey.getPrivateExponent()
                );

                return keyFactory.generatePrivate(rsaPrivateKeySpec);
            }

            System.err.println("不支持的私钥格式");
            return null;

        } catch (Exception e) {
            System.err.println("私钥加载失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 断开连接
     */
    public void disConnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnect() {
        return client.isConnected();
    }


    /**
     * 订阅主题
     */
    public void subscribe(String topic, int qos) {
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}

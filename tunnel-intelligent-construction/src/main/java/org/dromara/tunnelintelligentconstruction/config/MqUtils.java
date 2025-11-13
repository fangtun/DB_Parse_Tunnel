package org.dromara.tunnelintelligentconstruction.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//import javax.annotation.PostConstruct;

/**
 *
 * @date 2019/4/2 13:06
 */
@Component
public class MqUtils {

    @Autowired
    private RedisTemplate redisTemplate;

//    @PostConstruct
    @EventListener(ContextRefreshedEvent.class)
    public void init(){
        MqUtils.getInstance().redisTemplate = this.redisTemplate;
    }

    /**
     *  实现单例 start
     */
    private static class SingletonHolder {
        private static final MqUtils INSTANCE = new MqUtils();
    }
    private MqUtils(){}
    public static final MqUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }
    /**
     *  实现单例 end
     */
    public RedisTemplate getRedisTemplate(){
        return MqUtils.getInstance().redisTemplate;
    }
}

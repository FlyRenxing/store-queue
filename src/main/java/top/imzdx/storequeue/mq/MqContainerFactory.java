package top.imzdx.storequeue.mq;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imzdx.flashmq.JmsConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 9:48
 */
@Configuration
public class MqContainerFactory {

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public ConnectionFactory connectionFactory() {
//        Config config = null;
//        try {
//            config = Config.fromYAML(MqContainerFactory.class.getClassLoader().getResource("redisson-config.yml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        RedissonClient client = Redisson.create(redissonClient.getConfig());
        ConnectionFactory connectionFactory = new JmsConnectionFactory("store", client);
        return connectionFactory;
    }

    @Bean
    public JMSContext jmsContext() {
        JMSContext context = connectionFactory().createContext();
        return context;
    }
}

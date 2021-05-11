package top.imzdx.storequeue.mq;

import com.ltsoft.jms.JmsConnectionFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import java.io.IOException;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 9:48
 */
@Configuration
public class MqContainerFactory {

//    @Bean
//    public JmsListenerContainerFactory<?> topicListenerContainer(ConnectionFactory activeMQConnectionFactory) {
//        DefaultJmsListenerContainerFactory topicListenerContainer = new DefaultJmsListenerContainerFactory();
//        topicListenerContainer.setPubSubDomain(true);
//        topicListenerContainer.setConnectionFactory(activeMQConnectionFactory);
//        return topicListenerContainer;
//    }

    @Bean
    public ConnectionFactory connectionFactory() {
        Config config = null;
        try {
            config = Config.fromYAML(MqContainerFactory.class.getClassLoader().getResource("redisson-config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        RedissonClient client = Redisson.create(config);
        ConnectionFactory connectionFactory = new JmsConnectionFactory("store", client);
        return connectionFactory;
    }

    @Bean
    public JMSContext jmsContext() {
        JMSContext context = connectionFactory().createContext();
        return context;
    }
}

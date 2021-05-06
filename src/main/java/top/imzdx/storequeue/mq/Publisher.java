package top.imzdx.storequeue.mq;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 9:33
 */
@Service
public class Publisher {
    @Resource
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void publish(String queueName, Object meg) {
        Destination queue = new ActiveMQTopic(queueName);
        //System.out.println("发布topic消息" + meg);
        jmsMessagingTemplate.convertAndSend(queue, meg);
    }
}

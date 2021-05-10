package top.imzdx.storequeue.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 9:13
 */
@Component
public class Producer {
    @Autowired
    private ConnectionFactory connectionFactory;

    public void sendMsg(String queueName, String message) {
        try (JMSContext context = connectionFactory.createContext()) {
            Queue queue = context.createQueue(queueName);
            JMSProducer producer = context.createProducer();
            producer.send(queue, message);
        }
    }

//    public void send(String destinationName,Object meg){
//        jmsQueueTemplate.send(destinationName, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                return session.createTextMessage(meg.toString());
//            }
//        });
//    }
//
//    /**
//     * 发送消息自动转换成原始消息
//     */
//    public void convertAndSend(String destinationName,Object meg){
//        jmsQueueTemplate.convertAndSend(destinationName, meg);
//    }
}

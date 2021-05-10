package top.imzdx.storequeue.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.service.BuyService;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;


/**
 * @author Renxing
 * @date 2021/4/26 13:40
 */
@Service
public class MqListener {
    @Autowired
    private BuyService buyService;

    @Autowired
    public MqListener(JMSContext context) {
        buyListener(context);
        buyStockListener(context);
        buySeckillListener(context);
        buyOrderListener(context);
    }

    public void buyListener(JMSContext context) {
        Queue queue = context.createQueue("buy.create");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buy(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        try {
            System.out.println(queue.getQueueName() + "已建立监听");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void buyStockListener(JMSContext context) {
        Queue queue = context.createQueue("buy.stock");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buyStock(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        try {
            System.out.println(queue.getQueueName() + "已建立监听");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void buySeckillListener(JMSContext context) {
        Queue queue = context.createQueue("buy.seckill");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buySeckill(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        try {
            System.out.println(queue.getQueueName() + "已建立监听");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void buyOrderListener(JMSContext context) {
        Queue queue = context.createQueue("buy.order");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buyOrder(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        try {
            System.out.println(queue.getQueueName() + "已建立监听");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}

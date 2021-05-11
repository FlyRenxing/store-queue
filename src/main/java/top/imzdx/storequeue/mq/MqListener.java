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
    private final JMSContext context;

    @Autowired
    public MqListener(JMSContext context) {
        this.context = context;
        buyListener();
        buyStockListener();
        buySeckillListener();
        buyOrderListener();
    }

    public void buyListener() {
        Queue queue = context.createQueue("buy.create");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buy(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void buyStockListener() {
        Queue queue = context.createQueue("buy.stock");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buyStock(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void buySeckillListener() {
        Queue queue = context.createQueue("buy.seckill");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buySeckill(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    public void buyOrderListener() {
        Queue queue = context.createQueue("buy.order");
        JMSConsumer consumer = context.createConsumer(queue);
        consumer.setMessageListener(message -> {
            try {
                buyService.buyOrder(message.getBody(String.class));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}

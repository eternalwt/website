package com.greengiant.infrastructure.messaging;

import com.greengiant.website.shiro.CustomCachedSessionDAO;
import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    private static Logger LOGGER = LogManager.getLogger(Consumer.class);

    @RabbitListener(queues = "cord")// todo 监听多个队列。公司的代码好像
    //@RabbitListener(queues = "cord", containerFactory="myFactory")
    public void processMessage(String msg) {
        System.out.format("Receiving Message: -----[%s]----- \n.", msg);
    }

    /**
     *
     * @param message 队列中的消息;
     * @param channel 当前的消息队列;
     * @param tag 取出来当前消息在队列中的的索引,
     * 用这个@Header(AmqpHeaders.DELIVERY_TAG)注解可以拿到;
     * @throws IOException
     */
//    @RabbitListener(queues = "direct_boot_queue")
//    public void myAckListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
//        LOGGER.info(message);
//        try {
//            /**
//             * 无异常就确认消息
//             * basicAck(long deliveryTag, boolean multiple)
//             * deliveryTag:取出来当前消息在队列中的的索引;
//             * multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
//             * deliveryTag为5及其以下的消息;一般设置为false
//             */
//            channel.basicAck(tag, false);
//        } catch (Exception ex){
//            LOGGER.error("myAckListener failed,", ex);
//            /**
//             * 有异常就绝收消息
//             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
//             * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
//             *         false:将消息丢弃
//             */
//            channel.basicNack(tag,false,true);
//        }
//    }

}

package com.greengiant.infrastructure.messaging;

import com.rabbitmq.client.Channel;
import io.lettuce.core.dynamic.annotation.Value;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

//    @Value("${spring.rabbitmq.listener.direct.acknowledge-mode}")
//    private AcknowledgeMode ackMode;

    // todo MessageListener
    // todo 再看序列化【结合springboot、kafka的序列化配置，把这个打通、搞透】：
    //  Spring-AMQP 1.6之后就支持参数类型推断了：
    // todo 思考怎么提供接口和包，让其他微服务直接使用？（如果不确定，再看看公司代码）【要达到和公司一样的效果，能从一个方法直接响应，并且要比公司的简单，不用在配置文件里面配置】
    // todo 监听多种数据类型的消息【这个和上面一个问题才是最关键的】
    // todo 3.怎么灵活绑定、怎么搞多个topic、queue的应用？主要考虑几方面：exchange、queue、消息类型
    // todo 消费失败的情形没考虑？
    // todo spring-rabbit消费过程解析及AcknowledgeMode选择【多图】：https://blog.csdn.net/weixin_38380858/article/details/84963944
    // todo 内部类RabbitTemplateConfiguration：https://blog.csdn.net/hry2015/article/details/79597281
    // todo cache-mode，缓存连接模式，默认值为CHANNEL(单个connection连接，连接之后关闭，自动销毁)：https://www.cnblogs.com/nizuimeiabc1/p/9608763.html
    // todo HttpServletResponse之类的也是可变参数吗？

    // todo channel.basicConsume ConfirmListener

    private static Logger LOGGER = LogManager.getLogger(Consumer.class);// todo 我为啥一直用的是LogManager？

    /**
     * 1.直接配置@RabbitListener(queues = "cord", ackMode = "MANUAL")可以达到手动ack的作用
     */

    //@RabbitListener(queues = "cord", containerFactory="myFactory")// todo rabbitListenerContainerFactory
//    @RabbitListener(queues = "cord")
    @RabbitListener(queues = "hello")
    // todo msg前面加@Payload注解的作用是什么？ https://blog.csdn.net/m0_37556444/article/details/82627723
    public void processMessage(String msg,  @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {// todo 1.为啥这个函数可以任意加参数，例如Channel？2.手动ack起作用应该分2个层面把：(1)消息要是手动ack模式，(2)ack的动作；
        // todo 把Channel的方法看一下、测一下
        System.out.println(channel == null);
        channel.basicAck(deliveryTag, false);// todo 队列被删除了，是哪里配置的问题？
        // todo 从哪里获取是不是手动ack，看看类。实在找不到看公司代码
        // todo AcknowledgeMode类
        // todo 搜一下RabbitProperties这个类
        LOGGER.info("Receiving Message: -----[%s]----- ", msg);
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

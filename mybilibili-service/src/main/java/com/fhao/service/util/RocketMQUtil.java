package com.fhao.service.util;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * author: FHao
 * create time: 2023-03-20 12:34
 * description:
 */
public class RocketMQUtil {
    public static void syncSendMsg(DefaultMQProducer producer, Message message) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult);
    }
    //异步
    public static void asyncSendMsg(DefaultMQProducer producer, Message message) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
//        SendResult sendResult = producer.send(message);
//        System.out.println(sendResult);
        int messageCount = 2;
        CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);
        for (int i = 0; i < messageCount; i++) {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    countDownLatch.countDown();
                    System.out.println(sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.println("出现异常！"+throwable);
                    throwable.printStackTrace();
                }
            });

        }
        countDownLatch.await(5, TimeUnit.SECONDS);
    }
}

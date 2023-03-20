package com.fhao.service.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhao.domin.UserFollowing;
import com.fhao.domin.UserMoment;
import com.fhao.domin.constant.UserMomentConstant;
import com.fhao.service.UserFollowingService;
import com.mysql.cj.util.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-20 12:20
 * description:
 */
@Configuration
public class RocketMQConfig {
    @Value("${rocketmq.name.server.address}")
    private String nameServerAddr;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserFollowingService userFollowingService;
    @Bean("momentsProducer")
    public DefaultMQProducer momentsProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(UserMomentConstant.GROUP_MOMENTS);
        producer.setNamesrvAddr(nameServerAddr);
        producer.start();
        return producer;
    }
    @Bean("momentsConsumer")
    public DefaultMQPushConsumer momentsConsumer()throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UserMomentConstant.GROUP_MOMENTS);
        consumer.setNamesrvAddr(nameServerAddr);
        consumer.subscribe(UserMomentConstant.TOPIC_MOMENTS,"*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                //获取消息
                MessageExt msg = msgs.get(0);
                //如果动态消息为空
                if(msg == null){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                //得到动态信息
                String bodyStr = new String(msg.getBody());
                //把动态信息字符串转换为对应的对象
                UserMoment userMoment = JSONObject.toJavaObject(JSONObject.parseObject(bodyStr), UserMoment.class);
                //获取动态表中的用户id
                Long userId = userMoment.getUserId();
                //得到该用户的粉丝们，为他们推送消息
                List<UserFollowing> fanList= userFollowingService.getUserFans(userId);
                //遍历粉丝
                for (UserFollowing fan : fanList) {
                    //创建key
                    String key = "subscribed-" + fan.getUserId();
                    //根据key到redis中找数据
                    String subscribedListStr = redisTemplate.opsForValue().get(key);
                    //创建一个保存动态信息的列表
                    List<UserMoment> subscribedList;
                    //如果redis中没有给对应粉丝推送的消息
                    if(StringUtils.isNullOrEmpty(subscribedListStr)){
                        //创建一个新的List
                        subscribedList = new ArrayList<>();
                    }else {
                        //如果有的话转换成动态信息
                        subscribedList = JSONArray.parseArray(subscribedListStr,UserMoment.class);
                    }
                    //为动态列表添加动态信息
                    subscribedList.add(userMoment);
                    //为该粉丝保存动态信息到redis中
                    redisTemplate.opsForValue().set(key,JSONObject.toJSONString(subscribedList));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        return consumer;
    }
}

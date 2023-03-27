package com.fhao.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhao.dao.UserMomentsDao;
import com.fhao.domain.UserMoment;
import com.fhao.domain.constant.MQConstant;
import com.fhao.service.util.RocketMQUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-20 13:20
 * description:
 */
@Service
public class UserMomentsService {
    @Autowired
    private UserMomentsDao userMomentsDao;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
        Message message = new Message(MQConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes());
        RocketMQUtil.syncSendMsg(producer,message);
    }

    public List<UserMoment> getUserSubscribedMoments(Long userId) {
//        momentsConsumer
        String key = MQConstant.REDIS_SUBSCRIBED_PREFIX + userId;
        String listStr = redisTemplate.opsForValue().get(key);
        return JSONArray.parseArray(listStr,UserMoment.class);
    }
}

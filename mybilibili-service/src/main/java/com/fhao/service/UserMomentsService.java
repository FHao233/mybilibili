package com.fhao.service;

import com.alibaba.fastjson.JSONObject;
import com.fhao.dao.UserMomentsDao;
import com.fhao.domin.UserMoment;
import com.fhao.domin.constant.UserMomentConstant;
import com.fhao.service.util.RocketMQUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public void addUserMoments(UserMoment userMoment) throws Exception {
        userMoment.setCreateTime(new Date());
        userMomentsDao.addUserMoments(userMoment);
        DefaultMQProducer producer = (DefaultMQProducer) applicationContext.getBean("momentsProducer");
        Message message = new Message(UserMomentConstant.TOPIC_MOMENTS, JSONObject.toJSONString(userMoment).getBytes());
        RocketMQUtil.syncSendMsg(producer,message);
    }
}

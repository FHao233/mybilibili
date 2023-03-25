package com.fhao.service;

import com.fhao.dao.UserCoinDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * author: FHao
 * create time: 2023-03-25 12:30
 * description:
 */
@Service
public class UserCoinService {
    @Autowired
    private UserCoinDao userCoinDao;
    //TODO 硬币获得方式等需要完成
    public Integer getUserCoinsAmount(Long userId) {
        return userCoinDao.getUserCoinsAmount(userId);
    }

    public void updateUserCoinsAmount(Long userId, Integer amount) {
        Date updateTime = new Date();
        userCoinDao.updateUserCoinAmount(userId, amount, updateTime);
    }
}

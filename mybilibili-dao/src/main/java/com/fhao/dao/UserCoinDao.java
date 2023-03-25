package com.fhao.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * author: FHao
 * create time: 2023-03-25 12:30
 * description:
 */
@Mapper
public interface UserCoinDao {
    Integer getUserCoinsAmount(Long userId);

    Integer updateUserCoinAmount(@Param("userId") Long userId,
                                 @Param("amount") Integer amount,
                                 @Param("updateTime") Date updateTime);
}

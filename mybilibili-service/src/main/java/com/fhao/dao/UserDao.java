package com.fhao.dao;

import com.fhao.domin.User;
import com.fhao.domin.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * author: FHao
 * create time: 2023-03-19 13:06
 * description:
 */
@Mapper
public interface UserDao {

    User getUserByPhone(String phone);

    Integer addUser(User user);

    Integer addUserInfo(UserInfo userInfo);
}

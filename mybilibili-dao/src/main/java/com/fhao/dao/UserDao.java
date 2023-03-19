package com.fhao.dao;

import com.fhao.domin.User;
import com.fhao.domin.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

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

    User getUserById(Long id);

    UserInfo getUserInfoByUserId(Long userId);

    Integer updateUsers(User user);

    Integer updateUserInfos(UserInfo userInfo);

    User getUserByPhoneOrEmail(String phone, String email);

    List<UserInfo> getUserInfoByUserIds(Set<Long> userIdList);
}

package com.fhao.dao;

import com.fhao.domain.UserMoment;
import org.apache.ibatis.annotations.Mapper;

/**
 * author: FHao
 * create time: 2023-03-20 13:20
 * description:
 */
@Mapper
public interface UserMomentsDao {
    Integer addUserMoments(UserMoment userMoment);
}

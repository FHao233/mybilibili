package com.fhao.dao;

import com.fhao.domain.auth.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-20 21:06
 * description:
 */
@Mapper
public interface UserRoleDao {
    List<UserRole> getUserRoleByUserId(Long userId);
}

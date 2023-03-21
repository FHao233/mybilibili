package com.fhao.dao;

import com.fhao.domain.auth.AuthRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * author: FHao
 * create time: 2023-03-21 10:31
 * description:
 */
@Mapper
public interface AuthRoleDao {
     AuthRole getRoleByCode(String code);
}

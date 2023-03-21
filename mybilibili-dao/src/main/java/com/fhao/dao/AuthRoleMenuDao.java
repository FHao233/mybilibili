package com.fhao.dao;

import com.fhao.domain.auth.AuthMenu;
import com.fhao.domain.auth.AuthRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * author: FHao
 * create time: 2023-03-20 21:19
 * description:
 */
@Mapper
public interface AuthRoleMenuDao {
    List<AuthRoleMenu> getAuthRoleMenusByRoleIds(@Param("roleIdSet") Set<Long> roleIdSet);

}

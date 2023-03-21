package com.fhao.service;

import com.fhao.dao.UserRoleDao;
import com.fhao.domain.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: FHao
 * create time: 2023-03-20 21:04
 * description:
 */
@Service
public class UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;
    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }

    public void addUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }
}

package com.fhao.service;

import com.fhao.domain.auth.*;
import com.fhao.domain.constant.AuthRoleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: FHao
 * create time: 2023-03-20 21:02
 * description:
 */
@Service
public class UserAuthoService {
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private AuthRoleService authRoleService;
    public UserAuthorities getUserAuthorities(Long userId) {
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdSet = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<AuthRoleElementOperation> roleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdSet);
        List<AuthRoleMenu> authRoleMenus = authRoleService.getAuthRoleMenusByRoleIds(roleIdSet);
        UserAuthorities userAuthorities = new UserAuthorities();
        userAuthorities.setRoleMenuList(authRoleMenus);
        userAuthorities.setRoleElementOperationList(roleElementOperationList);
        return userAuthorities;
    }

    public void addUserDefaultRole(Long id) {
        UserRole userRole = new UserRole();
        AuthRole authRole = authRoleService.getRoleByCode(AuthRoleConstant.ROLE_LV0);
        userRole.setRoleId(authRole.getId());
        userRole.setUserId(id);
        userRole.setCreateTime(new Date());
        userRoleService.addUserRole(userRole);
    }
}

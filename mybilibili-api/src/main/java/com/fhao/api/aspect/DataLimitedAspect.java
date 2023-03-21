package com.fhao.api.aspect;

import com.fhao.api.support.UserSupport;
import com.fhao.domain.UserMoment;
import com.fhao.domain.annotation.ApiLimitedRole;
import com.fhao.domain.auth.UserRole;
import com.fhao.domain.constant.AuthRoleConstant;
import com.fhao.domain.exception.ConditionException;
import com.fhao.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * author: FHao
 * create time: 2023-03-21 09:46
 * description:
 */
@Aspect
@Order(1)
@Component
public class DataLimitedAspect {
    @Autowired
    private UserSupport userSupport;
    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(com.fhao.domain.annotation.DataLimited)")
    public void check() {

    }

    @Before("check() ")
    public void doBefore(JoinPoint joinPoint) {
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof UserMoment) {
                UserMoment userMoment = (UserMoment) arg;
                String type = userMoment.getType();
                if (roleCodeSet.contains(AuthRoleConstant.ROLE_LV1) && !"0".equals(type)) {
                    throw new ConditionException("参数异常");
                }
            }
        }
    }
}

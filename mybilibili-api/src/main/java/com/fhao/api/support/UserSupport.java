package com.fhao.api.support;

import com.fhao.domin.exception.ConditionException;
import com.fhao.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * author: FHao
 * create time: 2023-03-19 14:18
 * description:
 */
@Component
public class UserSupport {
    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        if (userId < 0){
            throw new ConditionException("非法用户！");
        }
        return userId;
    }
}

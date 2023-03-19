package com.fhao.service.handler;

import com.fhao.domin.JsonResponse;
import com.fhao.domin.exception.ConditionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * author: FHao
 * create time: 2023-03-19 12:28
 * description:全局异常处理
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonGlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResponse<String> commonExceptionHandler(HttpServletRequest request,Exception e){
        String errorMsg = e.getMessage();
        if(e instanceof ConditionException){
            String errorCode = ((ConditionException) e).getCode();
            return new JsonResponse<>(errorCode,errorMsg);
        }else {
            return new JsonResponse<>("500",errorMsg);
        }
    }
}

package com.nebula.framework.config;

import cn.dev33.satoken.exception.NotLoginException;
import com.nebula.common.common.BaseResult;
import com.nebula.framework.exception.LoginException;
import com.nebula.framework.exception.PermissionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionConfig {

//    NotLoginException

    @ExceptionHandler(NotLoginException.class)
    public BaseResult<?> handlerNotLoginException(NotLoginException e) {
        return BaseResult.error("当前访问未登录或登录超时，请登陆后尝试");
    }

    @ExceptionHandler(LoginException.class)
    public BaseResult<?> handleLoginException(LoginException e){
        return BaseResult.error(e.getErrorMsg());
    }

    @ExceptionHandler(PermissionException.class)
    public BaseResult<?> handlePermissionException(PermissionException e){
        return BaseResult.error(e.getErrorMsg());
    }
}

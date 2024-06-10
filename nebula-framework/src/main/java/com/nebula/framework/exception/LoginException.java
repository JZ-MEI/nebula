package com.nebula.framework.exception;

import lombok.Data;

@Data
public class LoginException extends RuntimeException{

    public static final String USER_NOT_EXIST = "1";
    public static final String USER_NOT_EXIST_MESSAGE = "登录用户不存在";

    public static final String PASSWORD_ERROR = "2";

    public static final String PASSWORD_ERROR_MESSAGE = "密码错误";

    private String errorMsg;

    public LoginException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

package com.nebula.framework.exception;


import lombok.Data;

@Data
public class PermissionException extends RuntimeException{

    private String errorMsg;

    public PermissionException(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

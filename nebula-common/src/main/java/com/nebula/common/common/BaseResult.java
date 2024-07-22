package com.nebula.common.common;

import cn.hutool.http.HttpStatus;
import lombok.Data;

@Data
public class BaseResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public BaseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResult (Integer code) {
        new BaseResult<T>(code, null, null);
    }

    public BaseResult() {

    }

    public BaseResult(Integer code, String msg) {
        new BaseResult<T>(code, msg, null);
    }

    public BaseResult(Integer code, T data) {
        new BaseResult<T> (code, null, data);
    }

    public  static <T> BaseResult<T> success(String msg, T data) {
        return new BaseResult<T>(HttpStatus.HTTP_OK, msg, data);
    }

    public static <T> BaseResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> BaseResult<T> success(T data) {
        return success(null, data);
    }

    public static <T> BaseResult<T> success() {
        return success(null, null);
    }

    public static <T> BaseResult<T> error(String msg, T data) {
        return new BaseResult<T>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }

    public static <T> BaseResult<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> BaseResult<T> error(T data) {
        return error(null, data);
    }

    public static <T> BaseResult<T> error() {
        return error(null, null);
    }

    public boolean isSuccess(){
        return code == HttpStatus.HTTP_OK;
    }
}

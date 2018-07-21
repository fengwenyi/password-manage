package com.fengwenyi.password_manage.enums;

import com.fengwenyi.javalib.result.IReturnCode;

/**
 * @author Wenyi Feng
 */
public enum ReturnCodeEnum implements IReturnCode {

    INIT(-1, "(Error)init"),

    ERROR_LOGIN_ACCOUNT_NOT(14, "(Error)帐号异常或不存在"),
    ERROR_LOGIN_ACCOUNT_NOLL(11, "(Error)帐号不能为空"),
    ERROR_LOGIN_TOKEN_SAFE_FAIL(12, "(Error)Token加密失败"),
    ERROR_LOGIN_ACCOUNT_INCORRECT(13, "(Error)帐号或密码不正确"),
    ERROR_LOGIN_PASSWORD_SAFE_FAIL(15, "(Error)密码加密失败"),

    ERROR_TOKEN_VERIFY_FAIL(21, "(Error)Token验证失败"),

    ERROR_PASSWORD_PARAM_NULL(31, "(Error)名称和帐号不能为空"),

    ERROR_DB_SAVE_FAIL(41, "(Error)数据库保存失败"),

    SUCCESS(0, "Success")
    ;

    private Integer code;
    private String msg;

    ReturnCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}

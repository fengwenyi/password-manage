package com.fengwenyi.password_manage.enums;

import com.fengwenyi.javalib.result.IReturnCode;

/**
 * @author Wenyi Feng
 */
public enum ReturnCodeEnum implements IReturnCode {

    INIT(-1, "(Error)init"),

    ERROR_LOGIN_ACCOUNT_NOT(1101, "(Error)帐号异常或不存在"),
    ERROR_LOGIN_ACCOUNT_NOLL(1102, "(Error)帐号不能为空"),
    ERROR_LOGIN_TOKEN_SAFE_FAIL(1103, "(Error)Token加密失败"),
    ERROR_LOGIN_ACCOUNT_INCORRECT(1104, "(Error)帐号或密码不正确"),
    ERROR_LOGIN_PASSWORD_SAFE_FAIL(1105, "(Error)密码加密失败"),

    ERROR_TOKEN_VERIFY_FAIL(1201, "(Error)Token验证失败"),

    ERROR_PASSWORD_PARAM_NULL(1301, "(Error)名称和帐号不能为空"),

    ERROR_DB_SAVE_FAIL(1401, "(Error)数据库保存失败"),
    ERROR_DB_QUERY_FAIL(1402, "(Error)数据库查询失败"),

    ERROR_ADMIN_ACCOUNT_NULL(1501, "(Error)管理员账号不能为空"),
    ERROR_ADMIN_ACCOUNT_EXIST(1502, "(Error)管理员账号已存在，不能新增"),

    ERROR_EXCEPTION(1601, "(Error)500错误"),

    ERROR_KEY_NULL(1701, "(Error)密钥不能为空"),
    ERROR_KEY_EXIST(1702, "(Error)密钥已存在，不能新增"),

    ERROR_APP_ACCOUNT_EXIST(1801, "(Error)此账号在应用下已存在"),

    ERROR_PARAM_WRONGFUL(1901, "(Error)参数不合法"),

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

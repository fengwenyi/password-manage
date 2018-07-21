package com.fengwenyi.password_manage.enums;

/**
 * 系统初始化
 * @author Wenyi Feng
 */
public enum InitEnum {

    N(0, "未初始化"),
    Y(1, "已初始化")
    ;

    // 0:未初始化
    // 1：已初始化
    private Integer code;
    private String msg;

    InitEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

package com.fengwenyi.password_manage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fengwenyi.password_manage.utils.LongJsonDeserializer;
import com.fengwenyi.password_manage.utils.LongJsonSerializer;
import lombok.Data;

/**
 * @author Wenyi Feng
 */
@Data
public class AppInfoVO {

    // id
    private Long id;

    // app name
    private String name;

    // account
    private String account;

    // password
    private String password;

    // explain
    private String explain;

}

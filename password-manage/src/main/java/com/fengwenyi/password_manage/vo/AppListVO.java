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
public class AppListVO {

    // Id
    private Long id;

    // App name
    private String name;

    private String createTime;

    private String updateTime;

}

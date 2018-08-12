package com.fengwenyi.password_manage.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fengwenyi.password_manage.utils.LongJsonDeserializer;
import com.fengwenyi.password_manage.utils.LongJsonSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 密码
 * @author Wenyi Feng
 * @since 2018-07-22
 */
@Data
@TableName("t_password")
public class Password extends Model<Password> {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 帐号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 说明，备注
     */
    private String explain;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Password{" +
        "id=" + id +
        ", name=" + name +
        ", account=" + account +
        ", password=" + password +
        ", explain=" + explain +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

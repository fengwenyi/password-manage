package com.fengwenyi.password_manage.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员账号
 * @author Wenyi Feng
 * @since 2018-07-22
 */
@Data
@TableName("t_admin")
public class Admin extends Model<Admin> {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
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
        return "Admin{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

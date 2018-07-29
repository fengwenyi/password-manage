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
 * 密钥
 * @author Wenyi Feng
 * @since 2018-07-22
 */
@Data
@TableName("t_key")
public class Key extends Model<Key> {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    /**
     * 公钥
     */
    @TableField("public_key")
    private String publicKey;
    /**
     * 私钥
     */
    @TableField("private_key")
    private String privateKey;
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
        return "Key{" +
        "id=" + id +
        ", publicKey=" + publicKey +
        ", privateKey=" + privateKey +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}

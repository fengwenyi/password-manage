package com.fengwenyi.password_manage.vo;

import lombok.Data;

/**
 * 密钥
 * @author Wenyi Feng
 */
@Data
public class KeyVO {

    /** 私钥 */
    private String privateKey;

    /** 公钥 */
    private String publicKey;

}

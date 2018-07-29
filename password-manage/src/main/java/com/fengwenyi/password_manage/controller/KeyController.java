package com.fengwenyi.password_manage.controller;

import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.javalib.util.RSAUtil;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.domain.Key;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.KeyService;
import com.fengwenyi.password_manage.utils.Constant;
import com.fengwenyi.password_manage.utils.Utils;
import com.fengwenyi.password_manage.vo.KeyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * 密钥控制器
 * <p>
 *     生成密钥、保存密钥
 * </p>
 * @author Wenyi Feng
 */
@RestController
@RequestMapping(value = "/key", produces = {"application/json; charset=utf-8"})
@Slf4j
public class KeyController {

    @Autowired
    private KeyService keyService;

    // 生成密钥
    @GetMapping("/getKey")
    public String getKey(@RequestHeader String token) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            try {
                String[] keys = RSAUtil.getKey();
                String privateKey = keys[0];
                String publicKey = keys[1];
                KeyVO keyVO = new KeyVO();
                keyVO.setPrivateKey(privateKey);
                keyVO.setPublicKey(publicKey);
                result.setResult(ReturnCodeEnum.SUCCESS, keyVO);
            } catch (NoSuchAlgorithmException e) {
                result.setResult(ReturnCodeEnum.ERROR_EXCEPTION);
                log.error("生成密钥失败：{}", e.getMessage());
            }
        } else {
            result.setResult(ReturnCodeEnum.ERROR_TOKEN_VERIFY_FAIL);
        }
        return Utils.gson().toJson(result);
    }

    /**
     * 将密钥写入数据库
     * @param privateKey 私钥
     * @param publicKey  公钥
     * @return
     */
    @PostMapping("/addKey")
    public String addKey(@RequestHeader(Constant.KEY_TOKEN) String token,
                         String privateKey, String publicKey) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            if (StringUtil.isNullStr(privateKey)
                    || StringUtil.isNullStr(publicKey))
                result.setResult(ReturnCodeEnum.ERROR_KEY_NULL);
            else {
                // 如果存在多个密钥，那么就无法准确对密钥进行解密
                List<Key> keyList = keyService.selectList(null);
                if (keyList == null || keyList.size() == 0) {
                    Key key = new Key();
                    key.setPrivateKey(privateKey);
                    key.setPublicKey(publicKey);
                    key.setCreateTime(new Date());
                    boolean rs = keyService.insert(key);
                    if (rs)
                        result.setResult(ReturnCodeEnum.SUCCESS);
                    else
                        result.setResult(ReturnCodeEnum.ERROR_DB_SAVE_FAIL);
                } else
                    result.setResult(ReturnCodeEnum.ERROR_KEY_EXIST);
            }
        } else {
            result.setResult(ReturnCodeEnum.ERROR_TOKEN_VERIFY_FAIL);
        }
        return Utils.gson().toJson(result);
    }

}

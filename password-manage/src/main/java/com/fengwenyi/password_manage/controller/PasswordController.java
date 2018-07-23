package com.fengwenyi.password_manage.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.javalib.util.RSAUtil;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.domain.Key;
import com.fengwenyi.password_manage.domain.Password;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.KeyService;
import com.fengwenyi.password_manage.service.PasswordService;
import com.fengwenyi.password_manage.utils.Constact;
import com.fengwenyi.password_manage.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 密码管理控制器
 * 加密解密：
 * 采用公钥加密，私钥解密
 *
 * 密码列表、查询密钥、编辑密码、删除密码
 *
 * @author Wenyi Feng
 * @since 2018-07-18
 */
@RestController
@RequestMapping(value = "/password", produces = {"application/json; charset=utf-8"})
@Slf4j
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    public KeyService keyService;

    // 添加
    @PostMapping("/addPassword")
    public String addPassword(@RequestHeader(Constact.KEY_TOKEN) String token,
                              String name, String account, String password, String explain) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            if (StringUtil.isNullStr(name)
                    || StringUtil.isNullStr(account)) {
                Password passwordEntity = new Password();
                passwordEntity.setName(name);
                passwordEntity.setAccount(account);
                passwordEntity.setExplain(explain);
                // 需要加密
                List<Key> keyList = keyService.selectList(null);
                if (keyList != null && keyList.size() == 1) {
                    String publicKey = keyList.get(0).getPublicKey();
                    try {
                        String cipherText = RSAUtil.publicKeyEncrypt(publicKey, password);
                        passwordEntity.setPassword(cipherText);
                        boolean rs = passwordService.insert(passwordEntity);
                        if (rs)
                            result.setResult(ReturnCodeEnum.SUCCESS);
                        else
                            result.setResult(ReturnCodeEnum.ERROR_DB_SAVE_FAIL);
                    } catch (Exception e) {
                        result.setResult(ReturnCodeEnum.ERROR_EXCEPTION);
                        log.error("生成密文失败：{}", e.getMessage());
                    }
                }
            } else {
                result.setResult(ReturnCodeEnum.ERROR_PASSWORD_PARAM_NULL);
            }
        } else {
            result.setResult(ReturnCodeEnum.ERROR_TOKEN_VERIFY_FAIL);
        }
        return Utils.gson().toJson(result);
    }

    // 查询所有
    @GetMapping("all")
    public String All() {
        return null;
    }

    /**
     * 分页查询
     * @param page 页码
     * @return
     */
    @GetMapping("/getPasswordPage/{page}")
    public Page<Password> getPasswordPage(@PathVariable("page") Integer page) {

        Page<Password> passwordPage = passwordService.selectPage(new Page<>(page, Constact.PAGE_SIZE));

        return passwordPage;
    }

    /**
     * 通过主键ID查询密码
     * @param id 主键ID
     * @return
     */
    @GetMapping("/getPasswordById/{id}")
    public Password getPasswordById(@PathVariable("id") Long id) {
        if (id == null)
            return null;
        return passwordService.selectById(id);
    }

    /**
     * 通过主键ID删除密码
     * @param id 主键ID
     */
    @DeleteMapping("/deletePasswordById/{id}")
    public void deletePasswordById(@PathVariable("id") Long id) {
        if (id != null)
            passwordService.deleteById(id);
    }

    /**
     * 通过主键ID，批量删除
     * @param idList
     */
    @DeleteMapping("/deletePasswordListById/{idList}")
    public void deletePasswordListById(@PathVariable("idList") List<Long> idList) {
        if (idList != null && idList.size() > 0)
            for (Long id : idList)
                passwordService.deleteById(id);
    }

    /**
     * 删除全部密码
     */
    @DeleteMapping("/deletePasswordAll")
    public void deletePasswordAll() {
        List<Password> passwordList = passwordService.selectList(null);
        for (Password password : passwordList) {
            String id = password.getId();
            passwordService.deleteById(id);
        }

    }

}


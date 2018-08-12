package com.fengwenyi.password_manage.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.javalib.util.Console;
import com.fengwenyi.javalib.util.RSAUtil;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.business.AuthBusiness;
import com.fengwenyi.password_manage.domain.Key;
import com.fengwenyi.password_manage.domain.Password;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.KeyService;
import com.fengwenyi.password_manage.service.PasswordService;
import com.fengwenyi.password_manage.utils.Constant;
import com.fengwenyi.password_manage.utils.Utils;
import com.fengwenyi.password_manage.vo.AppInfoVO;
import com.fengwenyi.password_manage.vo.AppListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
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
    private KeyService keyService;

    @Autowired
    private AuthBusiness authBusiness;

    // 添加
    @PostMapping("/addPassword")
    public String addPassword(@RequestHeader(Constant.KEY_TOKEN) String token,
                              String name, String account, String password, String explain) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            if (StringUtil.isNotEmpty(name)
                    || StringUtil.isNotEmpty(account)) {
                // 判断 name 和 账号是否已经存在
                Wrapper<Password> wrapper = new EntityWrapper<>();
                wrapper.eq("name", name);
                wrapper.eq("account", name);
                List<Password> passwordList = passwordService.selectList(wrapper);
                if (passwordList != null && passwordList.size() > 0)
                    result.setResult(ReturnCodeEnum.ERROR_APP_ACCOUNT_EXIST);
                else {
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
    @GetMapping("getAll")
    public String getAll() {

        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);

        List<Password> passwordList = passwordService.selectList(null);

        List<AppListVO> appListVOList = new ArrayList<>();
        for (Password obj : passwordList) {
            AppListVO appListVO = new AppListVO();
            appListVO.setId(obj.getId());
            appListVO.setName(obj.getName());
            appListVO.setCreateTime(Utils.timeFormat(obj.getCreateTime()));
            appListVO.setUpdateTime(Utils.timeFormat(obj.getUpdateTime()));
            appListVOList.add(appListVO);
        }

        result.setResult(ReturnCodeEnum.SUCCESS, appListVOList);
        //return Utils.gson().toJson(result);
        return Utils.createGson().toJson(result);
    }

    /**
     * 分页查询
     * @param page 页码
     * @return
     */
    @GetMapping("/getPasswordPage1/{page}")
    public String getPasswordPage4(@RequestHeader(Constant.KEY_TOKEN) String token,
                                          @PathVariable("page") Integer page) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            if (page != null && page > 0) {
                Page<Password> passwordPage = passwordService.selectPage(new Page<>(page, Constant.PAGE_SIZE));
            } else
                result.setResult(ReturnCodeEnum.ERROR_PARAM_WRONGFUL);
        } else
            result.setResult(ReturnCodeEnum.ERROR_TOKEN_VERIFY_FAIL);
        return Utils.gson().toJson(result);
    }

    /**
     * 分页查询
     * @param page 页码
     * @return
     */
    @GetMapping("/getPasswordPage/{page}")
    public Page<Password> getPasswordPage(@PathVariable("page") Integer page) {

        Page<Password> passwordPage = passwordService.selectPage(new Page<>(page, Constant.PAGE_SIZE));

        return passwordPage;
    }

    /**
     * 通过主键ID查询密码
     * @param id 主键ID
     * @return
     */
    @PostMapping("/getPasswordById")
    public String getPasswordById(String username, String password, String id) {
        Console.info(id);
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        if (StringUtil.isEmpty(username)
                || StringUtil.isEmpty(password)
                || StringUtil.isEmpty(id))
            result.setResult(ReturnCodeEnum.ERROR_PARAM_WRONGFUL);
        else {
            try {
                // 身份验证
                boolean isPass = authBusiness.auth(username, password);
                if (isPass) {

                    List<Key> keyList = keyService.selectList(null);
                    if (keyList != null && keyList.size() == 1) {
                        // 解密
                        String privateKey = keyList.get(0).getPrivateKey();

                        Password pwdObj = passwordService.selectById(id);
                        if (pwdObj == null)
                            //
                            result.setResult(ReturnCodeEnum.ERROR_DB_QUERY_FAIL);
                        else{
                            try {
                                String pwd = RSAUtil.privateKeyDecrypt(privateKey, pwdObj.getPassword());
                                AppInfoVO appInfoVO = new AppInfoVO();
                                appInfoVO.setId(pwdObj.getId());
                                appInfoVO.setName(pwdObj.getName());
                                appInfoVO.setPassword(pwd);
                                appInfoVO.setExplain(pwdObj.getExplain());

                                result.setResult(ReturnCodeEnum.SUCCESS, appInfoVO);
                            } catch (Exception e) {
                                log.error("解密失败：{}", e.getMessage());
                            }
                        }
                    } else {
                        result.setResult(ReturnCodeEnum.ERROR_DB_QUERY_FAIL);
                    }
                } else
                    result.setResult(ReturnCodeEnum.ERROR_LOGIN_ACCOUNT_INCORRECT);
            } catch (NoSuchAlgorithmException e) {
                log.error("验证加密失败：{}", e.getMessage());
            }
        }
        return Utils.gson().toJson(result);
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
            Long id = password.getId();
            passwordService.deleteById(id);
        }

    }

}


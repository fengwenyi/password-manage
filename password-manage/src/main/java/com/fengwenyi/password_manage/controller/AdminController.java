package com.fengwenyi.password_manage.controller;


import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.javalib.util.SafeUtil;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.domain.Admin;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.AdminService;
import com.fengwenyi.password_manage.utils.Constact;
import com.fengwenyi.password_manage.utils.TokenUtil;
import com.fengwenyi.password_manage.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员控制器
 * <p>
 *     有以下功能：
 * </p>
 * <ul>
 *     <li>登录认证</li>
 *     <li>生成Token</li>
 *     <li>初始化管理员账号</li>
 * </ul>
 *
 * @author Wenyi Feng
 */
@RestController
@RequestMapping(value = "/admin", produces = {"application/json; charset=utf-8"})
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录认证
     * 关于账号，我们想设计成数据库只保留一条数据
     * 在代码中也确实有这样做了
     * 但是，难免有部分玩家往数据库灌输
     * 所以我们默认取第一条验证
     * 但是这也有一个问题：多条如何认证？
     * 比如第2条才是真正的登录信息，第1/3/4或是其他都是不正确的
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public String login(String username, String password) {

        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        // 校验帐号是否为空
        if (StringUtil.isNullStr(username)
                || StringUtil.isNullStr(password)) {
            result.setResult(ReturnCodeEnum.ERROR_LOGIN_ACCOUNT_NOLL);
        } else {
            List<Admin> adminList = adminService.selectList(null);
            // 查询结果是否为空
            if (adminList == null || adminList.size() < 1) {
                result.setResult(ReturnCodeEnum.ERROR_LOGIN_ACCOUNT_NOT);
            } else {
                Admin admin = adminList.get(0);
                // 帐号密码是否正确
                try {
                    if (admin.getPassword().equals(username)
                            && admin.getPassword().equals(SafeUtil.MD5(password))) {
                        try {
                            String s = username + System.currentTimeMillis()
                                    + com.fengwenyi.javalib.util.Utils.getUUID();
                            String token = SafeUtil.MD5(s);
                            TokenUtil.newInstance().setToken(token);
                            Map<String, String> map = new HashMap<>();
                            map.put(Constact.KEY_TOKEN, token);
                            result.setResult(ReturnCodeEnum.SUCCESS, map);
                        } catch (NoSuchAlgorithmException e) {
                            result.setResult(ReturnCodeEnum.ERROR_LOGIN_TOKEN_SAFE_FAIL);
                        }
                    } else {
                        result.setResult(ReturnCodeEnum.ERROR_LOGIN_ACCOUNT_INCORRECT);
                    }
                } catch (NoSuchAlgorithmException e) {
                    result.setResult(ReturnCodeEnum.ERROR_LOGIN_PASSWORD_SAFE_FAIL);
                }
            }
        }
        return Utils.gson().toJson(result);
    }

    /**
     * 设置管理员账号
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/addAdmin")
    public String addAdmin(@RequestBody String username, String password) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        if (!StringUtil.isNullStr(username)) {
            // 保证管理员账号只存在一个
            List<Admin> adminList = adminService.selectList(null);
            if (adminList == null || adminList.size() == 0) {
                Admin admin = new Admin();
                admin.setUsername(username);
                try {
                    admin.setPassword(SafeUtil.MD5(password));
                    boolean rs = adminService.insert(admin);
                    if (rs)
                        result.setResult(ReturnCodeEnum.SUCCESS);
                    else
                        result.setResult(ReturnCodeEnum.ERROR_DB_SAVE_FAIL);
                } catch (NoSuchAlgorithmException e) {
                    result.setResult(ReturnCodeEnum.ERROR_EXCEPTION);
                    log.error("MD5加密失败：{}", e.getMessage());
                }
            } else {
                result.setResult(ReturnCodeEnum.ERROR_ADMIN_ACCOUNT_EXIST);
            }
        } else {
            result.setResult(ReturnCodeEnum.ERROR_ADMIN_ACCOUNT_NULL);
        }
        return Utils.gson().toJson(result);
    }

}


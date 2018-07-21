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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  验证
 * </p>
 *
 * @author Wenyi Feng
 * @since 2018-07-18
 */
@RestController
@RequestMapping(value = "/admin", produces = {"application/json; charset=utf-8"})
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 默认取第一条验证
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

    // 设置管理员账号

}


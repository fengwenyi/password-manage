package com.fengwenyi.password_manage.controller;

import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.password_manage.domain.Admin;
import com.fengwenyi.password_manage.domain.Key;
import com.fengwenyi.password_manage.enums.InitEnum;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.AdminService;
import com.fengwenyi.password_manage.service.KeyService;
import com.fengwenyi.password_manage.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * index
 * @author Wenyi Feng
 */
@RestController
@RequestMapping(value = "/index", produces = {"application/json; charset=utf-8"})
public class IndexController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private KeyService keyService;

    // 判断是否需要初始化
    @GetMapping("/getIsInit")
    public String getIsInit() {

        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);

        boolean rs = false;

        // admin
        List<Admin> adminList = adminService.selectList(null);
        if (adminList != null && adminList.size() == 1) {
            // key
            List<Key> keyList = keyService.selectList(null);
            if (keyList != null && keyList.size() == 1) {
                rs = true;
            }
        }

        if (rs)
            result.setResult(ReturnCodeEnum.SUCCESS, InitEnum.Y);
        else
            result.setResult(ReturnCodeEnum.SUCCESS, InitEnum.N);

        return Utils.gson().toJson(result);
    }



}

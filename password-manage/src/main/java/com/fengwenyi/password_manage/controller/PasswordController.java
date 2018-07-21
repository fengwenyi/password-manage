package com.fengwenyi.password_manage.controller;


import com.fengwenyi.javalib.result.Result;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.domain.Password;
import com.fengwenyi.password_manage.enums.ReturnCodeEnum;
import com.fengwenyi.password_manage.service.PasswordService;
import com.fengwenyi.password_manage.utils.Constact;
import com.fengwenyi.password_manage.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  密码
 * </p>
 *
 * @author Wenyi Feng
 * @since 2018-07-18
 */
@RestController
@RequestMapping(value = "/password", produces = {"application/json; charset=utf-8"})
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    // 添加
    @PostMapping("/add")
    public String add(@RequestHeader(Constact.KEY_TOKEN) String token, String name, String account, String password) {
        Result result = new Result();
        result.setResult(ReturnCodeEnum.INIT);
        boolean isVerify = Utils.verify(token);
        if (isVerify) {
            if (StringUtil.isNullStr(name)
                    || StringUtil.isNullStr(account)) {
                Password passwordEntity = new Password();
                passwordEntity.setName(name);
                passwordEntity.setAccount(account);
                // 需要加密
                passwordEntity.setPassword(password);
                boolean rs = passwordService.insert(passwordEntity);
                if (rs)
                    result.setResult(ReturnCodeEnum.SUCCESS);
                else
                    result.setResult(ReturnCodeEnum.ERROR_DB_SAVE_FAIL);
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

    // 查询分页
    @GetMapping("/page/{page}")
    public String page(@PathVariable("page") Integer page) {
        return null;
    }

    // 根据id查询
    @GetMapping("/get/{id}")
    public String getById(@PathVariable("id") String id) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        return null;
    }

    @DeleteMapping("/deletes/{ids}")
    public String deleteByIds(@PathVariable("ids") List<String> idList) {
        return null;
    }

    @DeleteMapping("/delete-all")
    public String deleteAll() {
        return null;
    }

    @PostMapping("/update")
    public String updateById(String id) {
        return null;
    }
}


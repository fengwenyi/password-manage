package com.fengwenyi.password_manage.business.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fengwenyi.javalib.util.SafeUtil;
import com.fengwenyi.javalib.util.StringUtil;
import com.fengwenyi.password_manage.business.AuthBusiness;
import com.fengwenyi.password_manage.domain.Admin;
import com.fengwenyi.password_manage.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @author Wenyi Feng
 */
@Service
public class AuthBusinessImpl implements AuthBusiness {

    @Autowired
    private AdminService adminService;

    @Override
    public boolean auth(String username, String password) throws NoSuchAlgorithmException {

        if (StringUtil.isEmpty(username))
            return false;

        Wrapper<Admin> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", SafeUtil.MD5(password));

        List<Admin> adminList = adminService.selectList(wrapper);
        if (adminList != null
                && adminList.size() == 1)
            return true;

        return false;
    }
}

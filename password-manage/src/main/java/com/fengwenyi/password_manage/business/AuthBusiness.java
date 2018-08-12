package com.fengwenyi.password_manage.business;


import java.security.NoSuchAlgorithmException;

/**
 * @author Wenyi Feng
 */
public interface AuthBusiness {

    // 身份验证
    boolean auth(String username, String password) throws NoSuchAlgorithmException;

}

package com.fengwenyi.password_manage.utils;

import com.fengwenyi.javalib.util.StringUtil;
import com.google.gson.Gson;

/**
 * common utils
 * @author Wenyi Feng
 */
public class Utils {

    /**
     * Gson 对象
     * @return
     */
    public static Gson gson() {
        return new Gson();
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        String s = TokenUtil.newInstance().getToken();
        return !StringUtil.isNullStr(token)
                && !StringUtil.isNullStr(s)
                && token.equals(s);
    }

}

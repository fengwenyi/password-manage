package com.fengwenyi.password_manage.utils;

/**
 * token util
 * @author Wenyi Feng
 */
public class TokenUtil {

    /**
     * 外部不能创建对象
     */
    private TokenUtil() {}

    /**
     * 对象私有化
     */
    private static TokenUtil tokenUtil;

    /**
     * token
     */
    private static String token;

    /**
     * TokenUtil对线
     * @return
     */
    public static TokenUtil newInstance() {
        if (tokenUtil == null)
            tokenUtil = new TokenUtil();
        return tokenUtil;
    }

    /**
     * get token
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * set token
     * @param value
     */
    public void setToken(String value) {
        token = value;
    }

}

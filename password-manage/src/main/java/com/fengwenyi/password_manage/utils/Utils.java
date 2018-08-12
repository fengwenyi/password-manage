package com.fengwenyi.password_manage.utils;

import com.fengwenyi.javalib.util.StringUtil;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;


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
     * 如果 Long 的数字超过15位，转换为String，在json中数字两边有引号
     * @return
     */
    public static Gson createGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        LongSerializer serializer = new LongSerializer();
        gsonBuilder.registerTypeAdapter(Long.class, serializer);
        gsonBuilder.registerTypeAdapter(long.class, serializer);
        Gson gson = gsonBuilder.create();
        return gson;
    }

    static class LongSerializer implements JsonSerializer<Long> {
        public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
            if(src!=null){
                String strSrc = src.toString();
                if(strSrc.length()>15){
                    return new JsonPrimitive(strSrc);
                }
            }
            return new JsonPrimitive(src);
        }
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        String s = TokenUtil.newInstance().getToken();
        return StringUtil.isNotEmpty(token)
                && StringUtil.isNotEmpty(s)
                && token.equals(s);
    }

    /**
     * 时间格式化
     * @param time Date time
     * @return
     */
    public static String timeFormat(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return simpleDateFormat.format(time);
    }

}

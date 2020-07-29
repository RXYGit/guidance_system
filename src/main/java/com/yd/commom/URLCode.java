package com.yd.commom;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URLCode 的编码的编码和解码的工具类
 */
public class URLCode {


    /**
     * 编码
     * @param obj
     * @return
     */
    public static String enCode(Object obj) {
        String str = String.valueOf(obj);
        String encode;
        try{
            encode = URLEncoder.encode(str,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return encode;
    }

    /**
     * 解码
     * @param obj
     * @return
     */
    public static String  deCode(Object  obj) {
        String str = String.valueOf(obj);
        String t;
        try {
            t = URLDecoder.decode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return t;
    }
}

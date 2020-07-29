package com.yd.testclass;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLCodeTest {

    //编码
    @Test
    public void enCode() throws UnsupportedEncodingException {
        String s = "你好+\"\"+ ";
        String encode = URLEncoder.encode(s,"UTF-8");
        System.out.println(encode);//12345%3C%3E678
    }

    //解码
    @Test
    public void decode() throws UnsupportedEncodingException {
        String s = "12345%3C%3E678";
        String encode = URLDecoder.decode(s,"UTF-8");
        System.out.println(encode);
    }
}

package com.hp.ssm.utils;

import org.springframework.util.DigestUtils;

public class JavaMD5 {
    public static String getMd5Code(String code){
        return DigestUtils.md5DigestAsHex(code.getBytes());
    }
}

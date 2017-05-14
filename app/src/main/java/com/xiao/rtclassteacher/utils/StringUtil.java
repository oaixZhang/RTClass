package com.xiao.rtclassteacher.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiao on 2017/4/13.
 */

public class StringUtil {
    public static String baseUrl = "http://192.168.1.106:8080/";

    public static String readFromStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int i;
        while ((i = is.read(buffer)) != -1)
            sb.append(new String(buffer, 0, i));
        return sb.toString();
    }
}

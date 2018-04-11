package com.xaut.util;

import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * 向页面输出信息工具类
 */
public class ResponseUtil {

    public static void write(HttpServletResponse response, int code, String msg) throws IOException {
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), Charset.forName("UTF-8")));
        writer.write("{\"statusCode\": " + code
                + ",\"message\": \"" + msg
                + "\",\"data\": null}");
        writer.close();
    }
}

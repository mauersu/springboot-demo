package com.mauersu.demo.oauth2.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class JsonUtil {

    public static void makeResponse(HttpServletResponse response,
                             int status, Object value) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        response.getOutputStream().write(JSON.toJSONString(value).getBytes("utf-8"));
    }


    /**
     * 判断是否为 ajax请求
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }


}

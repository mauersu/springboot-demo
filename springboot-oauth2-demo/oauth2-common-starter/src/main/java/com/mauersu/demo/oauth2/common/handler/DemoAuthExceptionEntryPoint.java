package com.mauersu.demo.oauth2.common.handler;


import com.alibaba.fastjson.JSON;
import com.mauersu.demo.oauth2.common.result.Result;
import com.mauersu.demo.oauth2.common.result.ResultEnum;
import com.mauersu.demo.oauth2.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class DemoAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String requestUri = request.getRequestURI();
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String message = "访问令牌不合法";
        log.error("客户端访问{}请求失败: {}", requestUri, message, authException);
        if (StringUtils.containsIgnoreCase(authException.getMessage(), "Access token expired:")) {
            message = "访问令牌过期失效";
            JsonUtil.makeResponse(response, 401, Result.other(ResultEnum.TOKEN_EXPIRED.getCode(), null,message));
        } else {
            message = "访问令牌不合法";
            JsonUtil.makeResponse(response, 401, Result.other(ResultEnum.TOKEN_EXPIRED.getCode(), null,message));
        }
    }


}

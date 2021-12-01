package com.mauersu.demo.oauth2.server.core.handler;

import com.mauersu.demo.oauth2.common.result.Result;
import com.mauersu.demo.oauth2.common.result.ResultEnum;
import com.mauersu.demo.oauth2.common.util.JsonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class DemoWebLoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException {
        String message = "";
        String username = httpServletRequest.getParameter("username");
        if (exception instanceof BadCredentialsException) {
            message = "用户名或密码错误！";
        } else if (exception instanceof LockedException) {
            message = "用户已被锁定！";
        } else {
            message = "认证失败，请联系网站管理员！";
        }
        JsonUtil.makeResponse(httpServletResponse,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Result.other(ResultEnum.FAIL.getCode(), null,message));
    }



}

package com.mauersu.demo.oauth2.common.handler;

import com.mauersu.demo.oauth2.common.result.Result;
import com.mauersu.demo.oauth2.common.result.ResultEnum;
import com.mauersu.demo.oauth2.common.util.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DemoAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        JsonUtil.makeResponse(response, HttpServletResponse.SC_FORBIDDEN, Result.other(ResultEnum.NO_POWER_ERROR.getCode(), null,"没有权限访问该资源"));
    }
}

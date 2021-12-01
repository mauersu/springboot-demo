package com.mauersu.demo.oauth2.server.core.handler;

import com.mauersu.demo.oauth2.common.result.Result;
import com.mauersu.demo.oauth2.common.result.ResultEnum;
import com.mauersu.demo.oauth2.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
@Component
public class DemoWebLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object attribute = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            log.info("跳转到登录页的地址为: {}", attribute);
        }
        if (JsonUtil.isAjaxRequest(request)) {
            if (savedRequest == null) {
                JsonUtil.makeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Result.other(ResultEnum.NO_POWER_ERROR.getCode(), null,"请通过授权码模式跳转到该页面"));
                return;
            }
            JsonUtil.makeResponse(response, HttpServletResponse.SC_OK, Result.ok(savedRequest.getRedirectUrl()));
        } else {
            if (savedRequest == null) {
                super.onAuthenticationSuccess(request, response, authentication);
                return;
            }
            clearAuthenticationAttributes(request);
            getRedirectStrategy().sendRedirect(request, response, savedRequest.getRedirectUrl());
        }
    }
}

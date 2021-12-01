package com.mauersu.demo.oauth2.server.core.translator;

import com.mauersu.demo.oauth2.common.result.Result;
import com.mauersu.demo.oauth2.common.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 异常翻译
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class DemoWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<?> translate(Exception e) {
        ResponseEntity.BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        Result result = Result.failed();
        String message = "认证失败";
        log.error(message, e);
        if (e instanceof UnsupportedGrantTypeException) {
            message = "不支持该认证类型";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        if (e instanceof InvalidTokenException
                && StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token (expired)")) {
            message = "刷新令牌已过期，请重新登录";
            status = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            return status.body(Result.other(ResultEnum.NO_POWER_ERROR.getCode(), null, message));
        }
        if (e instanceof InvalidScopeException) {
            message = "不是有效的scope值";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        if (e instanceof RedirectMismatchException) {
            message = "redirect_uri值不正确";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        if (e instanceof BadClientCredentialsException) {
            message = "client值不合法";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        if (e instanceof UnsupportedResponseTypeException) {
            String code = StringUtils.substringBetween(e.getMessage(), "[", "]");
            message = code + "不是合法的response_type值";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        if (e instanceof InvalidGrantException) {
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid refresh token")) {
                message = "refresh token无效";
                return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "Invalid authorization code")) {
                String code = StringUtils.substringAfterLast(e.getMessage(), ": ");
                message = "授权码" + code + "不合法";
                return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
            }
            if (StringUtils.containsIgnoreCase(e.getMessage(), "locked")) {
                message = "用户已被锁定，请联系管理员";
                return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
            }
            message = "用户名或密码错误";
            return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
        }
        return status.body(Result.other(ResultEnum.AUTH_FAILED.getCode(), null, message));
    }
}

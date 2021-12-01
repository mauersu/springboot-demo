package com.mauersu.demo.oauth2.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 功能描述:错误枚举，编码和消息
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {

  SUCCESS(200, "处理成功"),

  FAIL(500, "处理失败"),

  PARAM_ERROR(400, "参数错误"),

  NO_POWER_ERROR(403, "无权限"),

  AUTH_FAILED(497, "认证失败"),

  TOKEN_EXPIRED(498, "token失效"),

  REFRESH_TOKEN_EXPIRED(499, "refresh token失效");

  private final int code;

  private final String msg;

}

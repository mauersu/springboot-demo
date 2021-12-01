package com.mauersu.demo.oauth2.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 功能描述:统一结果，请求响应对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Result<T> {
  /** 响应代码 */
  private int code;

  /** 返回的提示信息 */
  private String msg;

  /** 返回的数据 */
  private T data;

  /**
   * 正确返回
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> ok() {
    return new Result(200, "请求成功", null);
  }

  /**
   * 带数据的正确返回
   * @param data 返回的数据
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> ok(T data) {
    return new Result(200, "请求成功", data);
  }

  /**
   * 带数据以及提示信息的正确返回
   * @param data 返回的数据
   * @param msg 返回的提示信息
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> ok(T data, String msg) {
    return new Result(200, msg, data);
  }

  /**
   * 错误返回
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> failed() {
    return new Result(500, "请求失败", null);
  }

  /**
   * 带提示信息的错误返回
   * @param msg 返回的提示信息
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> failed(String msg) {
    return new Result(500, msg, null);
  }

  /**
   * 带数据的错误返回
   * @param data 返回的数据
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> failed(T data) {
    return new Result(500, "请求失败", data);
  }

  /**
   *  带数据以及提示信息的错误返回
   * @param data 返回的数据
   * @param msg 返回的提示信息
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> failed(T data, String msg) {
    return new Result(500, msg, data);
  }

  /**
   *  自定义状态码
   * @param code 状态码
   * @param data 返回的数据
   * @param msg 返回的提示信息
   * @param <T> 返回的数据类型
   * @return Result
   */
  public static <T> Result<T> other(int code,T data, String msg) {
    return new Result(code, msg, data);
  }
}

package com.mauersu.demo.oauth2.resource.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "oauth2-server")
public interface IUserServiceClient {

    /**
     * 测试远程调用
     * @return String
     */
    @GetMapping("/provider")
    String provider();

}

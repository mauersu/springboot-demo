package com.mauersu.demo.alibabacloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "alibabacloud-provider-demo")
public interface FeignService {

    /**
     * 使用feign调用provider提供的服务=
     */
    @GetMapping("/provider")
    String test();
}

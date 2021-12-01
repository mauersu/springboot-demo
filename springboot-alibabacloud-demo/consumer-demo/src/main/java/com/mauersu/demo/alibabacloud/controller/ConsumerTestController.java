package com.mauersu.demo.alibabacloud.controller;

import com.mauersu.demo.alibabacloud.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerTestController {

    @Value("${provider.name}")
    private String name;

    @GetMapping("/provider/name")
    public String test() {
        return this.name;
    }

    @Autowired
    private FeignService feignService;

    @GetMapping("/consumer")
    public String consumer() {
        return name + ":" + feignService.test();
    }


    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer1")
    public String consumer1() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("alibabacloud-provider-demo");
        return restTemplate.getForObject(serviceInstance.getUri() + "/provider", String.class);
    }


    @Autowired
    private RestTemplate restTemplate1;

    /**
     * @description 消费nacos-standalone-provider服务提供的/provider功能
     */
    @GetMapping("/consumer2")
    public String restTemplate1() {
        return restTemplate1.getForObject("http://alibabacloud-provider-demo/provider", String.class);
    }

}

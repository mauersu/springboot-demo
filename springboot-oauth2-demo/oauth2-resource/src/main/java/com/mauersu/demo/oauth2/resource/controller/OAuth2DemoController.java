package com.mauersu.demo.oauth2.resource.controller;

import com.alibaba.fastjson.JSON;
import com.mauersu.demo.oauth2.common.entity.AuthUser;
import com.mauersu.demo.oauth2.common.util.DemoSecurityUtil;
import com.mauersu.demo.oauth2.resource.feign.IUserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/resource")
public class OAuth2DemoController {

    @Autowired
    private IUserServiceClient userServiceClient;

    @ResponseBody
    @GetMapping("/testfeign")
    @PreAuthorize("hasAuthority('demo:test')")
    public String testfeign() {
        String name = userServiceClient.provider();
        return name;
    }

    @ResponseBody
    @GetMapping("/testAuth")
    @PreAuthorize("hasAuthority('demo:test')")
    public AuthUser test() {
        String username = DemoSecurityUtil.getCurrentUsername();
        AuthUser authUser = DemoSecurityUtil.getCurrentUser();
        log.info("username:" + username + ", authUser:" + JSON.toJSONString(authUser));
        return authUser;
    }

}

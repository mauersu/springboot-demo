package com.mauersu.demo.oauth2.server.core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class SecurityController {

    @ResponseBody
    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

}

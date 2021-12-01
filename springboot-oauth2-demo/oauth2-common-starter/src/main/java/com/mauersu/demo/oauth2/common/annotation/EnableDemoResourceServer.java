package com.mauersu.demo.oauth2.common.annotation;

import com.mauersu.demo.oauth2.common.configure.DemoResourceServerConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DemoResourceServerConfigure.class)
public @interface EnableDemoResourceServer {
}

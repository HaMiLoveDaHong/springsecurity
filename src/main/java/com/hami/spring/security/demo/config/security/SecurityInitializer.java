package com.hami.spring.security.demo.config.security;

import com.hami.spring.security.demo.config.RedisConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.security.Security;

/**
 * 拦截器 实现 seesion 共享
 * @author zhanghongwei
 * @since 2019/11/7
 */
@Configuration
public class SecurityInitializer  extends AbstractSecurityWebApplicationInitializer {
    protected SecurityInitializer() {
        super(Security.class, RedisConfig.class);
    }
}

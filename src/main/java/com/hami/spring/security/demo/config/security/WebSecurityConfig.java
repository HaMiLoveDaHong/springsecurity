package com.hami.spring.security.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * WebSecurity 拦截器
 * @author zhanghongwei
 * @sice 2019/10/31
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/","/home").permitAll()
                .antMatchers("/api/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    //官方文档 缺少加密密码的配置
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        //创建用户
        UserDetails admin = User.withUsername("admin").password(new BCryptPasswordEncoder().encode("123456")).roles("USER").build();
        return new InMemoryUserDetailsManager(admin);
    }

    //官网文档 缺少密码加密验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean())//用户认证
            .passwordEncoder(new BCryptPasswordEncoder());//使用加密验证
    }
}

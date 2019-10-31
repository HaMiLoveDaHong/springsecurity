package com.hami.spring.security.demo.api;

import com.hami.spring.security.demo.vo.GreetingVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhanghongwei
 * @sice 2019/10/31
 */
@RestController
@RequestMapping(value = {"/api"})
public class GreetingApiController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public GreetingVo greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingVo(counter.incrementAndGet(),
                String.format(template, name));
    }
}

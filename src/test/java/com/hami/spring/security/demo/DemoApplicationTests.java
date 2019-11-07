package com.hami.spring.security.demo;

import com.hami.spring.security.demo.example.RedisExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    RedisExample redisExample;

    @Test
    public void testRedis() throws MalformedURLException {
        URL url  = new URL("http://www.baidu.com");
        redisExample.addLink("TEST",url);
    }

}

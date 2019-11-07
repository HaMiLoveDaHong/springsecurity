package com.hami.spring.security.demo.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URL;

/**
 * @author zhanghongwei
 * @since 2019/11/7
 */
@Component
public class RedisExample {

    //inject the actual template
    @Autowired
    private RedisTemplate<String,String> template;

    //inject the template as ListOperations
    //can also inject as value,set zset ,and HashOperations
    @Resource(name = "redisTemplate")
    private ListOperations<String,String> listOperations;

    public void addLink(String userId, URL url){
        listOperations.leftPush(userId,url.toExternalForm());
        //or use template directly
        template.boundListOps(userId+"1").leftPush(url.toExternalForm());
    }

}

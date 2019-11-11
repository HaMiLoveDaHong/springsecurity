package com.hami.spring.security.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.hami.spring.security.demo.service.UserService;
import com.hami.spring.security.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhanghongwei
 * @since 2019/11/11
 */
@Service
public class UserServiceImpl implements UserService {

    private static  final String USERlIST ="user:list";

    private static final String USER = "user:name";

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public List<UserVo> addBithDefault() {
        List<UserVo> userVos = new UserVo().assemblyData();
        for (UserVo userVo:userVos){
            redisTemplate.opsForList().leftPush(USERlIST, JSON.toJSONString(userVo));
        }
        return userVos;
    }

    @Override
    public UserVo addDefault() {
        UserVo userVo = new UserVo("100","李白","libai@qq.com");
        stringRedisTemplate.opsForValue().set(USER,JSON.toJSONString(userVo));
        return userVo;
    }

    @Override
    public List<UserVo> findByListDefault() {
        List<String> str = redisTemplate.opsForList().range(USERlIST,0,-1);
        List<UserVo> result = null;
        if (!ObjectUtils.isEmpty(str)){
            result = new LinkedList();
            for (String param: str){
                UserVo userVo = JSON.parseObject(param,UserVo.class);
                result.add(userVo);
            }
        }
        return result;
    }

    @Override
    public UserVo findByDefault() {
        String str= stringRedisTemplate.opsForValue().get(USER);
        UserVo userVo = null;
        if (!ObjectUtils.isEmpty(str)){
            userVo = JSON.parseObject(str,UserVo.class);
        }
        return userVo;
    }
}

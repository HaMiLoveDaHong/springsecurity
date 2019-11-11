package com.hami.spring.security.demo.api;

import com.hami.spring.security.demo.service.UserService;
import com.hami.spring.security.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制层
 * @author zhanghongwei
 * @since 2019/11/11
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserApiController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/addDefault", method = RequestMethod.GET)
    public String addDefault(){
        userService.addBithDefault();
        userService.addDefault();
        return "success";
    }


    @RequestMapping(value = "/findListDefault", method = RequestMethod.GET)
    public List<UserVo> findListDefault(){
        List<UserVo> userVos = userService.findByListDefault();
        return userVos;
    }

    @RequestMapping(value = "/findDefault", method = RequestMethod.GET)
    public UserVo findDefault(){
        UserVo userVo = userService.findByDefault();
        return userVo;
    }




}

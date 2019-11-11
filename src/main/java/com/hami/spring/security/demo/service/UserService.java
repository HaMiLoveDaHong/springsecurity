package com.hami.spring.security.demo.service;

import com.hami.spring.security.demo.vo.UserVo;
import org.w3c.dom.ls.LSInput;

import java.util.List;

/**
 * @author zhanghongwei
 * @since 2019/11/11
 */
public interface UserService {

    /**
     * 默认添加用户到redis
     * @return
     */
    List<UserVo> addBithDefault();

    /**
     * 默认增加一个用户
     * @return
     */
    UserVo addDefault();

    /**
     * 查询默认保存redis列表用户
     * @return
     */
    List<UserVo> findByListDefault();

    /**
     * 查询默认保存redis用户
     * @return
     */
    UserVo findByDefault();
}

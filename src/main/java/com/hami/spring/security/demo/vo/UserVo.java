package com.hami.spring.security.demo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhanghongwei
 * @since 2019/11/11
 */
@Data
public class UserVo implements Serializable {
    private static final long serialVersionUID = 3976416733028953796L;

    private String id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 邮件
     */
    private String email;

    public UserVo() {
    }

    public UserVo(String id, String userName, String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    /**
     * 组装用户数据
     */
    public List<UserVo> assemblyData(){
        List<UserVo> userVos = new LinkedList();
        for (int i=0;i<20;i++){
            UserVo userVo = new UserVo(String.valueOf(i),"某"+i,"24324325"+i+"@qq.com");
            userVos.add(userVo);
        }

        return userVos;
    }

}

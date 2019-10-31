package com.hami.spring.security.demo.vo;

/**
 * @author zhanghongwei
 * @sice 2019/10/31
 */
public class GreetingVo {

    private final long id;
    private final String content;

    public GreetingVo(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

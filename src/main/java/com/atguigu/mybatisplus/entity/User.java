package com.atguigu.mybatisplus.entity;

import lombok.Data;

/**
 * @author hxld
 * @create 2022-07-31 17:22
 */
@Data
public class User {
    private long id;
    private String name;
    private Integer age;
    private String email;

    public User(long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public User() {

    }
}

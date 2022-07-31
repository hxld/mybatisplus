package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.entity.User;

import java.util.List;

/**
 * @author hxld
 * @create 2022-07-31 17:26
 */
public interface TestMapper {
    /**
     * 查询所有用户信息
     * @return
     */


    List<User> getAllUser();
}

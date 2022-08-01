package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

//未使用mybatisplus
//public interface UserMapper  {
//    /**
//     * 查询所有用户信息
//     * @return
//     */
//
//
//    List<User> getAllUser();
//}


//使用mybatisplus
public interface UserMapper extends BaseMapper<User> {


}
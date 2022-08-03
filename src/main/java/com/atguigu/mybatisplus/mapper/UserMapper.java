package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 通过年龄查询用户信息并分页
     * @param page mybatis-plus所提供的分页对象，必须位于第一个参数的位置
     * @param age
     * @return
     */
    //如果我们想要使用分页插件在我们自定义的查询语句上，参数返回值必须是Page,第一个参数也必须是Page..
    Page<User> selectPageVo(@Param("page") Page<User> page,@Param("age") Integer age);





}
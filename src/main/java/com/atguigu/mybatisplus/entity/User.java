package com.atguigu.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author hxld
 * @create 2022-07-31 17:22
 */
//lombok插件简化实体类开发
@Data
//设置实体类对应的表名
//@TableName("t_user")
public class User {
    //将这个uid这个属性所对应的字段作为主键（实体类和数据库表中都是uid时，可以不写value）
    //value属性用于指定主键的字段（实体类中字段是id,表中字段是uid时，设置value来指定主键字段）
    //type属性设置主键生成策略，默认是雪花算法(ASSIGN_ID)，主键递增（AUTO）
   @TableId(value="uid",type = IdType.AUTO)
    private long id;

   //注意：如果实体类中属性名为:name,表中字段名也要为:name
   // 在不加@TableField注解的情况下，表中字段为:user_name 代码会报错。
   //指定属性对应的字段名
    //当实体类中是name,表中所对应的字段名是：user_name,可以使用该注解来实现指定属性对应的字段名
    @TableField("user_name")
    private String name;
    private Integer age;
    private String email;
    //逻辑删除
    @TableLogic
    private Integer isDeleted;


    public User() {

    }
}

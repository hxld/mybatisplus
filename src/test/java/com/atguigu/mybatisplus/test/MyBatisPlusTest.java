package com.atguigu.mybatisplus.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;


import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.service.UserService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.*;

/**
 * @author hxld
 * @create 2022-08-01 8:20
 */
////spring测试类写法1
//    //在spring的环境中进行测试
//@RunWith(SpringJUnit4ClassRunner.class)
////指定spring的配置文件
//@ContextConfiguration("classpath:spring-persist.xml")


//spring测试类写法2
@SpringJUnitConfig(locations = {"classpath:spring-persist.xml"})
public class MyBatisPlusTest {

    @Autowired
    private DruidDataSource dataSource;

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试数据库连接
     * @throws SQLException
     */
    @Test
    public void getConn() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();

        logger.debug(connection.toString());

    }


    @Autowired
    private UserMapper userMapper;

    //使用mybatisplus
    @Test
    public  void testMybatisPlus(){
        //根据id查询用户信息
        System.out.println(userMapper.selectById(1));
    }

    //查询列表
    @Test
    public void testlist(){
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }

    //未使用mybatisplus
//    @Test
//    public void testUserMapper(){
//        List<User> UserList = userMapper.getAllUser();
//        for (User user:UserList){
//            System.out.println("user = " + user);
//        }
//    }



    //---------------简单CRUD测试-------------------------//

    //插入
    @Test
    public void testInsert(){
//        INSERT INTO user  ( id, name, age, email )  VALUES  ( ?, ?, ?, ? )
        User user = new User();
//        user.setUserName("王6");
        user.setAge(23);
        user.setEmail("wangwu@126.com");
        int result = userMapper.insert(user);

        System.out.println("result="+result);

    }

    //删除
    @Test
    public void testDelete(){
//        //根据Id删除用户
////        DELETE FROM user WHERE id=?
//        int result = userMapper.deleteById(2L);

        //根据map集合删除
//        DELETE FROM user WHERE name = ? AND age = ?
//        Map<String,Object> map = new HashMap<>();
//        map.put("name","张三");
//        map.put("age",23);
//        int result = userMapper.deleteByMap(map);

        // 根据id批量删除
//        DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Integer> list = Arrays.asList(1, 2, 3);

        int result = userMapper.deleteBatchIds(list);
        System.out.println("result="+result);
    }

    //修改
    @Test
    public void testUpdate(){
//        UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
//        user.setId(4L);
//        user.setName("李四");
        user.setEmail("lisi@atguigu.com");
        int result = userMapper.updateById(user);
        System.out.println("result="+result);
    }

    //查询
    @Test
    public void testSelect(){
        //根据Id查询用户
//        SELECT id,name,age,email FROM user WHERE id=?
//        User user = userMapper.selectById(5L);
//        System.out.println(user);

//        SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
//        List<Integer> list = Arrays.asList(1, 2, 4);
//        List<User> users = userMapper.selectBatchIds(list);
//       users.forEach(System.out::println);
        Map<String,Object> map = new HashMap<>();
        map.put("age",23);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);

    }



    //测试service接口
    @Autowired
    private UserService userService;

    //总记录数
    @Test
    public void testGetCount(){
        long count = userService.count();
        System.out.println("总记录数："+count);
    }

    //批量添加
    //单个Insert语句循环100次完成批量添加
    @Test
    public void testAllInsert(){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100 ; i++) {
            User user = new User();
//            user.setName("abc"+i);
            user.setAge(20+i);
            user.setEmail("test"+i+"@atguigu.com");
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        System.out.println("b:"+b);
    }

    //测试Enum

    @Test
    public void testEnum(){
        User user = new User();
        user.setName("admin");
        user.setAge(33);
        user.setSex(SexEnum.MALE);
        int result = userMapper.insert(user);
        System.out.println(result);
    }
}




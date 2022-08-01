package com.atguigu.mybatisplus.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;


import com.atguigu.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

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


    @Test
    public void getConn() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();

        logger.debug(connection.toString());

    }

//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void testUserMapper(){
//        List<User> UserList = userMapper.getAllUser();
//        for (User user:UserList){
//            System.out.println("user = " + user);
//        }
//    }
@Autowired
private UserMapper userMapper;

    //使用mybatisplus
    @Test
    public  void testMybatisPlus(){
        //根据id查询用户信息
        System.out.println(userMapper.selectById(1));
    }

}




package com.atguigu.mybatisplus.test;

import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;

/**
 * @author hxld
 * @create 2022-08-02 14:45
 */
@SpringJUnitConfig(locations = {"classpath:spring-persist.xml"})
public class MyBatisPlusWrapperTest {
    @Autowired
    private UserMapper userMapper;



    //*******************  selectList  *********************//



    @Test
    public void test01(){
        //查询用户名包含王,年龄在80到90之间，邮箱名不为空的用户信息
        // SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
      QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
      queryWrapper.like("user_name","王")
              .between("age","80","90")
              .isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);

    }


    @Test
    public void test02(){
        //查询用户信息，按照年龄的降序排序，若年龄相同，则按照Id升序排序。（mysql中默认是升序排序）
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,uid ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("uid");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }


    //*******************  delete  *********************//

    @Test
    public void test03(){
        //删除邮箱为null的用户
        //UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        //出现这个是因为我们设置了逻辑删除，即删除变成了修改，将状态从0(未删除状态)--->1(已删除状态);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println(result);
    }



    //*******************  update  *********************//

    /**
     * update(User entity,Wrapper<User> updateWrapper)
     * 第一个参数为修改的内容，第二个参数为设置修改的条件
     参数1设置修改的字段和值，参数2是查询符合的条件
     **/

    @Test
    public void test04(){
        //条件包装器中条件之间默认是and连接。我们不需要手动设置，但是如果是或，我们需要手动设置or.
        //将（年龄大于80并且用户名中包含有王）或邮箱为null的用户信息修改
        //UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //gt  大于   like 模糊查询
        queryWrapper.gt("age",80)
                .like("user_name","王")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("测试修改1");
        user.setEmail("test@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result:"+ result);

    }

    @Test
    public void test05(){
        //将用户名中包含王并且（年龄大于80或邮箱为null）的用户信息修改
        //当条件构造器中有and和or时，lambda中的条件优先执行
       // UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? AND email IS NULL))
        //i 指的是条件构造器。
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name","王")
                .and(i -> i.gt("age",80).isNull("email"));
        User user  = new User();
        user.setName("小红");
        user.setEmail("test@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result:"+result);

    }


    @Test
    public void test06(){
        //查询用户的用户名，年龄，邮箱信息
        //SELECT user_name,age,email FROM t_user WHERE is_deleted=0
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name","age","email");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }


    @Test
    public void test07(){
        //查询id小于等于3的用户信息
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid <= 3))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("uid","select uid from t_user where uid <= 3");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }



    //**************** updatewrapper ****************//

    @Test
    public void test08(){
        //将用户名中包含王并且（年龄大于80或邮箱为null）的用户信息修改
        //UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        //lambda 中消费者接口模型
        //updatewrapper可以直接设置查询条件和设置修改的字段并设置值。不需要设置实体类。
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("user_name","王")
                .and(i -> i.gt("age","80").or().isNull("email"));
        updateWrapper.set("user_name","小黑").set("email","abc@atguigu.com");
        userMapper.update(null,updateWrapper);
    }

   //**************** 模拟开发环境中组装条件***********

    @Test
    public void test09(){
        //接收到的浏览器的数据传输到服务器中
        String username= "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
        if(StringUtils.isNotBlank(username)){
            //isnotblank判断某个字符不为空字符串，不为null,不为空白符
            queryWrapper.like("user_name",username);
        }
        if(ageBegin != null){
            queryWrapper.ge("age",ageBegin);
        }
        if(ageEnd != null){
            queryWrapper.le("age",ageEnd);
        }
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }


    @Test
    public void test10(){
        //接收到的浏览器的数据传输到服务器中
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
        String username= "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"user_name",username)
                .ge(ageBegin != null,"age",ageBegin)
                .le(ageEnd != null,"age",ageEnd);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);

    }


    @Test
    public void test11(){
        //接收到的浏览器的数据传输到服务器中
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (age >= ? AND age <= ?)
        //LambdaQueryWrapper防止字段名写错，可以使用函数式接口（User::getAge），来访问实体类中的某一个属性所对应的字段名。自动将其作为我们的条件。
        String username= "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,username)
                .ge(ageBegin != null,User::getAge,ageBegin)
                .le(ageEnd != null,User::getAge,ageEnd);

        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);

    }

    @Test
    public void test12(){
        //将用户名中包含王并且（年龄大于80或邮箱为null）的用户信息修改
        //UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        //lambda 中消费者接口模型
        //updatewrapper可以直接设置查询条件和设置修改的字段并设置值。不需要设置实体类。
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName,"王")
                .and(i -> i.gt(User::getAge,"80").or().isNull(User::getEmail));
        updateWrapper.set(User::getName,"小黑").set(User::getEmail,"abc@atguigu.com");
        userMapper.update(null,updateWrapper);
    }


}
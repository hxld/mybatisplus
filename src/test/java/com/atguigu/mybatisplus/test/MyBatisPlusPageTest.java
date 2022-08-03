package com.atguigu.mybatisplus.test;

import com.atguigu.mybatisplus.entity.Product;
import com.atguigu.mybatisplus.entity.User;
import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

/**
 * @author hxld
 * @create 2022-08-02 21:34
 */

@SpringJUnitConfig(locations = {"classpath:spring-persist.xml"})
public class MyBatisPlusPageTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testPage(){
        //设置分页参数(参数1：当前页数，参数2：每页显示记录条数)
        Page<User> page = new Page<>(1,4);
        userMapper.selectPage(page,null);
        //获取分页数据
        List<User> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());

    }


    //测试xml自定义分页

    @Test
    public void testPageVo(){
        Page<User> page = new Page<>(1,3);
        userMapper.selectPageVo(page,20);
        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());

    }


    @Test
    public void test01(){
        //小李查询商品价格
        Product productli = productMapper.selectById(1);
        System.out.println("小李查询的商品价格："+productli.getPrice());
        //小王查询商品价格
        Product productwang = productMapper.selectById(1);
        System.out.println("小王查询的商品价格："+productwang.getPrice());
        //小李将价格+50
        productli.setPrice(productli.getPrice() + 50);
        productMapper.updateById(productli);
        //小王将价格-30
        productwang.setPrice(productwang.getPrice() - 30);
        int result = productMapper.updateById(productwang);
        if(result == 0){
            //操作失败，重新尝试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice() - 30);
            productMapper.updateById(productNew);
        }
        //老板查询价格
        Product productlaoban = productMapper.selectById(1);
        System.out.println("老板查询的商品价格："+productlaoban.getPrice());
    }
}

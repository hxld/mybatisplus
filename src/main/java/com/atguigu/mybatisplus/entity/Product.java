package com.atguigu.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author hxld
 * @create 2022-08-02 22:27
 */
@Data
public class Product {
    private long id;
    private String name;
    private Integer price;
    @Version  //表示乐观锁版本号字段
    private Integer version;
}

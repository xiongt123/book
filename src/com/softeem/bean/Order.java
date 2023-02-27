package com.softeem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订 单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private Timestamp createTime;
    private BigDecimal price;
    // 0 未发货，1 已发货，2 表示已签收
    //以后的大项目 则采取枚举的方式
    private Integer status = 0;
    private Integer userId;
}

package com.softeem.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 购物车的商品项
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer id; //商品编号
    private String name; //商品名称
    private Integer count; //商品总量
    private BigDecimal price; //价格
    private BigDecimal totalPrice; //总金额
}

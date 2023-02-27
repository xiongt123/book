package com.softeem.service;

import com.softeem.bean.Order;
import com.softeem.service.impl.Cart;
import com.softeem.utils.Page;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId) throws SQLException;

    public Page<Order> page(Integer userId, int pageNo, int pageSize) throws SQLException;

    public Page<Order> page( int pageNo, int pageSize) throws SQLException;

    public void updateStatus(String orderId, int i) throws SQLException;
}

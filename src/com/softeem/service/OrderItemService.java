package com.softeem.service;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.service.impl.Cart;
import com.softeem.utils.Page;

import java.sql.SQLException;

public interface OrderItemService {
    public Page<OrderItem> page(String orderId, int pageNo, int pageSize) throws SQLException;
}

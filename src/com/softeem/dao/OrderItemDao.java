package com.softeem.dao;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.User;
import com.softeem.utils.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends BaseInterface<OrderItemDao> {
    public int save(OrderItem orderItem) throws SQLException;

    public Integer queryForPageTotalCount(String orderId) throws SQLException;

    List<OrderItem> queryForPageItems(String orderId, int begin, int pageSize) throws SQLException;
}

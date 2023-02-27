package com.softeem.dao;

import com.softeem.bean.Order;
import com.softeem.utils.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends BaseInterface<OrderDao> {
    public int save(Order order) throws SQLException;

    public Integer queryForPageTotalCount(Integer userId) throws SQLException;

    public List<Order> queryForPageItems(Integer userId,int begin, int pageSize) throws SQLException;

    public Integer queryForPageTotalCount() throws SQLException;

    public List<Order> queryForPageItems(int begin, int pageSize) throws SQLException;

    public void updateStatus(String orderId, int i) throws SQLException;
}

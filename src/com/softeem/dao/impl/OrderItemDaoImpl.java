package com.softeem.dao.impl;

import com.softeem.bean.OrderItem;
import com.softeem.dao.OrderItemDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int save(OrderItem orderItem) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        return queryRunner.update(connection,sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public Integer queryForPageTotalCount(String orderId) throws SQLException{
        String sql = "select count(*) from t_order_item where order_id=?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        //进阶版:
        Long query = queryRunner.query(sql,handler,orderId);
        return query.intValue();
    }

    @Override
    public List<OrderItem> queryForPageItems(String orderId, int begin, int pageSize) throws SQLException {
        String sql = "select * from t_order_item where order_id=? order by order_id desc limit ?,?";

        BeanListHandler<OrderItem> handler = new BeanListHandler<>(OrderItem.class, hump());
        //进阶版:
        List<OrderItem> query = queryRunner.query(sql, handler,orderId,begin,pageSize);
        return query;
    }

    @Override
    public List<OrderItemDao> findAll() throws SQLException {
        return null;
    }

    @Override
    public Long save(OrderItemDao orderItemDao) throws SQLException {
        return null;
    }

    @Override
    public int updateById(OrderItemDao orderItemDao) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        return 0;
    }

    @Override
    public OrderItemDao findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItemDao> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}

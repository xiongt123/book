package com.softeem.dao.impl;

import com.softeem.bean.Order;
import com.softeem.bean.Tbook;
import com.softeem.dao.OrderDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public int save(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        return queryRunner.update(connection,sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public Integer queryForPageTotalCount(Integer userId) throws SQLException{
        String sql = "select count(*) from t_order where user_id=?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        //进阶版:
        Long query = queryRunner.query(sql,handler,userId);
        return query.intValue();
    }

    @Override
    public List<Order> queryForPageItems(Integer userId,int begin, int pageSize) throws SQLException {
        String sql = "select * from t_order where user_id=? order by order_id desc limit ?,?";

        BeanListHandler<Order> handler = new BeanListHandler<>(Order.class, hump());
        //进阶版:
        List<Order> query = queryRunner.query(sql, handler,userId,begin,pageSize);
        return query;
    }

    @Override
    public Integer queryForPageTotalCount() throws SQLException{
        String sql = "select count(*) from t_order";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        //进阶版:
        Long query = queryRunner.query(sql,handler);
        return query.intValue();
    }

    @Override
    public List<Order> queryForPageItems(int begin, int pageSize) throws SQLException {
        String sql = "select * from t_order order by order_id desc limit ?,?";

        BeanListHandler<Order> handler = new BeanListHandler<>(Order.class, hump());
        //进阶版:
        List<Order> query = queryRunner.query(sql, handler,begin,pageSize);
        return query;
    }

    @Override
    public void updateStatus(String orderId, int i) throws SQLException {
        String sql="update t_order set status=? where order_id=? ";
        queryRunner.update(sql,i,orderId);
    }

    @Override
    public List<OrderDao> findAll() throws SQLException {
        return null;
    }

    @Override
    public Long save(OrderDao orderDao) throws SQLException {
        return null;
    }

    @Override
    public int updateById(OrderDao orderDao) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        return 0;
    }

    @Override
    public OrderDao findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderDao> page(Integer pageNumber) throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }
}

package com.softeem.service.impl;

import com.softeem.bean.CartItem;
import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.Tbook;
import com.softeem.dao.OrderDao;
import com.softeem.dao.OrderItemDao;
import com.softeem.dao.TbookDao;
import com.softeem.dao.impl.OrderDaoImpl;
import com.softeem.dao.impl.OrderItemDaoImpl;
import com.softeem.dao.impl.TbookDaoImpl;
import com.softeem.service.OrderService;
import com.softeem.utils.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private TbookDao bookDao = new TbookDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) throws SQLException {
        // 订单号===唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        // 创建一个订单对象
        Order order = new Order(orderId, new Timestamp(System.currentTimeMillis()), cart.getTotalPrice(), 0, userId);
        // 保存订单
        orderDao.save(order);
        // 遍历购物车中每一个商品项转换成为订单项保存到数据库
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            // 获取每一个购物车中的商品项
            CartItem cartItem = entry.getValue();
            // 转换为每一个订单项
            OrderItem orderItem = new OrderItem(null, cartItem.getName(), cartItem.getCount(), cartItem.getPrice(), cartItem.getTotalPrice(), orderId);
            // 保存订单项到数据库
            orderItemDao.save(orderItem);
            // 更新库存和销量
            Tbook book = bookDao.findById(cartItem.getId());
            book.setSales(book.getSales() + cartItem.getCount());
            book.setStock(book.getStock() - cartItem.getCount());
            bookDao.updateById(book);

        }
        // 清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public Page<Order> page(Integer userId,int pageNo, int pageSize) throws SQLException {

        Page<Order> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        Integer totalCount = orderDao.queryForPageTotalCount(userId);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount+pageSize-1)/pageSize);//设置总页数
        page.setPageNo(pageNo);// 设置当前页码
        page.setItems(orderDao.queryForPageItems(userId,(page.getPageNo()-1)*pageSize,pageSize));// 求当前页数据
        return page;
    }

    @Override
    public Page<Order> page(int pageNo, int pageSize) throws SQLException {
        Page<Order> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        Integer totalCount = orderDao.queryForPageTotalCount();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount+pageSize-1)/pageSize);//设置总页数
        page.setPageNo(pageNo);// 设置当前页码
        page.setItems(orderDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize));// 求当前页数据
        return page;
    }

    @Override
    public void updateStatus(String orderId, int i) throws SQLException {
        orderDao.updateStatus(orderId,i);
    }
}

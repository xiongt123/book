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
import com.softeem.service.OrderItemService;
import com.softeem.service.OrderService;
import com.softeem.utils.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Override
    public Page<OrderItem> page(String orderId, int pageNo, int pageSize) throws SQLException {

        Page<OrderItem> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        Integer totalCount = orderItemDao.queryForPageTotalCount(orderId);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount+pageSize-1)/pageSize);//设置总页数
        page.setPageNo(pageNo);// 设置当前页码
        page.setItems(orderItemDao.queryForPageItems(orderId,(page.getPageNo()-1)*pageSize,pageSize));// 求当前页数据
        return page;
    }
}

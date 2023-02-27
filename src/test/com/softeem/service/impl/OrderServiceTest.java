package test.com.softeem.service.impl;

import com.softeem.bean.CartItem;
import com.softeem.bean.Order;
import com.softeem.service.OrderService;
import com.softeem.service.impl.Cart;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.utils.Page;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;

public class OrderServiceTest {
    @Test
    public void createOrder() {
        Cart cart = new Cart();

        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(1, "java 从入门到精通", 1, new BigDecimal(1000), new BigDecimal(1000)));
        cart.addItem(new CartItem(2, "数据结构与算法", 1, new BigDecimal(100), new BigDecimal(100)));
        OrderService orderService = new OrderServiceImpl();

        try {
            System.out.println("订单号是：" + orderService.createOrder(cart, 1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void page()  {
        OrderServiceImpl orderService = new OrderServiceImpl();
        Page<Order> page = null;
        try {
            page = orderService.page(0, 3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("page = " + page);
    }
}

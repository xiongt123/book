package com.softeem.servlet;

import com.softeem.bean.Order;
import com.softeem.bean.Tbook;
import com.softeem.bean.User;
import com.softeem.service.BookService;
import com.softeem.service.OrderService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.service.impl.Cart;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生成订单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // 先获取Cart 购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 获取Userid
        User loginUser = (User) request.getSession().getAttribute("user");

        if (loginUser == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            return;
        }
        Integer userId = loginUser.getId();
        //	调用orderService.createOrder(Cart,Userid);生成订单
        String orderId = null;
        /*try {*/
            orderId = orderService.createOrder(cart, userId);
        /*} catch (SQLException throwables) {
            throw new RuntimeException(throwables);
            //throwables.printStackTrace();
        }*/

        //	req.setAttribute("orderId", orderId);
        // 请求转发到/pages/cart/checkout.jsp
        //	req.getRequestDispatcher("/pages/cart/checkout.jsp").forward(req, resp);

        //为什么选择用重定向跳转,而没有选择服务器转发?
        //为了防止表单重复提交
        //重定向 -> 避免 购物订单 重复提交
        request.getSession().setAttribute("orderId", orderId);
        //重定向跳转前面不要加/
        //因为它会变成 http://localhost:8080/pages/cart/checout.jsp 差了项目名
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }

    /**
     * 查找所有订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllOrderByUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取Userid
        User loginUser = (User) request.getSession().getAttribute("user");
        if (loginUser == null) {
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            return;
        }
        Integer userId = loginUser.getId();
        try {
            int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到默认显示第1页
            int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取到默认每页显示4条数据
            Page<Order> page = orderService.page(userId,pageNo,pageSize);
            page.setUrl("OrderServlet?action=findAllOrderByUser&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);
           /* response.sendRedirect(request.getContextPath() + "/pages/order/order.jsp");*/

        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
            //throwables.printStackTrace();
        }
    }
}

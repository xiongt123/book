package com.softeem.servlet.manager;

import com.softeem.bean.Order;
import com.softeem.bean.User;
import com.softeem.service.OrderService;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrderManagerServlet", value = "/OrderManagerServlet")
public class OrderManagerServlet extends BaseServlet {
    OrderService orderService = new OrderServiceImpl();
    /**
     * 查找所有订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到默认显示第1页
            int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取到默认每页显示4条数据
            Page<Order> page = orderService.page(pageNo,pageSize);
            page.setUrl("OrderManagerServlet?action=findAllOrder&");
            request.setAttribute("page", page);
            request.setAttribute("pageNo",pageNo);
            request.getRequestDispatcher("pages/manager/order_manager.jsp").forward(request, response);
            /* response.sendRedirect(request.getContextPath() + "/pages/order/order.jsp");*/
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    protected void deliver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        System.out.println("orderId = " + orderId);
        try {
            orderService.updateStatus(orderId,1);
            int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到默认显示第1页
            response.sendRedirect("OrderManagerServlet?action=findAllOrder&pageNo="+pageNo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

package com.softeem.servlet;

import com.softeem.bean.CartItem;
import com.softeem.bean.Tbook;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.service.impl.Cart;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {
    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BookService bookService = new BookServiceImpl();
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用bookService.queryBookById(id):Book 得到图书的信息
        try {
            Tbook book = bookService.queryBookById(id);

            // 把图书信息，转换成为CartItem 商品项
            CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
            // 调用Cart.addItem(CartItem);添加商品项
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                request.getSession().setAttribute("cart", cart);
            }
            cart.addItem(cartItem);
            System.out.println(cart);
            System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));

            //首页 , 购物车数据回显
            //获取最后一个添加的商品名称
            request.getSession().setAttribute("lastName", cartItem.getName());

            // 重定向回原来商品所在的地址页面
            response.sendRedirect(request.getHeader("Referer"));
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
            //throwables.printStackTrace();
        }
    }

    /**
     * 删除商品项
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart != null) {
            // 删除 了购物车商品项
            cart.deleteItem(id);
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    /**
     * 清空购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            // 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }


    /**
     * 修改商品数量
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数 商品编号、商品数量
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        // 获取Cart 购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (cart != null) {
            // 修改商品数量
            cart.updateCount(id, count);
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}

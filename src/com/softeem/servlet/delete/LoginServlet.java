package com.softeem.servlet.delete;

import com.softeem.bean.User;
import com.softeem.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");//解决post乱码问题 ,get不会乱码
        //解决输出数据乱码
        //response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserServiceImpl userService = new UserServiceImpl();
        try {
            User user = new User(null, username, password,null);
            if(userService.login(user)!=null){
                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("user",user);
                request.setAttribute("msg","欢迎回来!");
                request.getRequestDispatcher("pages/user/success.jsp").forward(request,response);
            }else{
                System.out.println("用户[" + username + "]不存在!");
                request.setAttribute("msg","账号名或登陆密码不正确");
                request.setAttribute("username",username);
                request.setAttribute("password",password);

                request.getRequestDispatcher("pages/user/login.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }
}

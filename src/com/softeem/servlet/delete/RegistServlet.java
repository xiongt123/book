package com.softeem.servlet.delete;

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //解决输出数据乱码
        //response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        //回显
        request.setAttribute("username",username);
        request.setAttribute("password",password);
        request.setAttribute("email",email);
        request.setAttribute("code",code);
        UserServiceImpl userService = new UserServiceImpl();
        if("1234".equalsIgnoreCase(code)){
            try {
                if(!userService.existsUsername(username)){
                    User user = new User(null, username, password, email);
                    userService.registUser(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                    request.setAttribute("msg","注册成功!");
                    request.getRequestDispatcher("pages/user/success.jsp").forward(request,response);
                }else{
                    System.out.println("用户名[" + username + "]已存在!");
                    request.setAttribute("msg","用户名已存在请更换");
                    request.getRequestDispatcher("pages/user/regist.jsp").forward(request,response);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println(" 验 证 码 [" + code + "] 错 误 ");
            request.setAttribute("msg","验证码不正确");
            request.getRequestDispatcher("pages/user/regist.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);//regist表单是使用的post提交
    }
}

package com.softeem.servlet.manager;

import com.softeem.bean.Admin;
import com.softeem.bean.User;
import com.softeem.service.AdminService;
import com.softeem.service.impl.AdminServiceImpl;
import com.softeem.service.impl.UserServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.CookieUtils;
import com.softeem.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "AdminServlet", value = "/AdminServlet")
public class AdminServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        WebUtils.copyParamToBean(parameterMap,admin);

        AdminService adminService = new AdminServiceImpl();
        try {
            /*Admin admin = new Admin(null, adminname, password,null);*/
            Admin login = adminService.login(admin);
            if(login!=null){
                //登录成功
                Cookie admincookie = new Cookie("adminname", login.getUsername());
                Cookie passwordcookie = new Cookie("adminpassword", login.getPassword());
                admincookie.setMaxAge(60 * 60 * 24 * 7);//当前Cookie 一周内有效resp.addCookie(cookie);
                passwordcookie.setMaxAge(60 * 60 * 24 * 7);//当前Cookie 一周内有效resp.addCookie(cookie);
                response.addCookie(admincookie);
                response.addCookie(passwordcookie);

                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("admin",login);
                request.setAttribute("msg","欢迎回来!");

                if(request.getParameter("url") != null && !request.getParameter("url").equals("")){
                    request.getRequestDispatcher(request.getParameter("url")).forward(request,response);
                }else{
                    request.getRequestDispatcher("pages/manager/manager.jsp").forward(request,response);
                }
            }else{
                //登录失败
                System.out.println("用户[" + admin.getUsername() + "]不存在!");
                request.setAttribute("msg","账号名或登陆密码不正确");
                request.setAttribute("username",admin.getUsername());
                request.setAttribute("password",admin.getUsername());

                request.getRequestDispatcher("pages/manager/login.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
            //throwables.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取Session 中的验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        String code = request.getParameter("code");
        // 获取用户名
        String username = request.getParameter("username");


        //String code = request.getParameter("code");
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);

        request.setAttribute("u",user);//回显
        UserServiceImpl userService = new UserServiceImpl();
        try {
            if (token != null && token.equalsIgnoreCase(code)) {

                if(!userService.existsUsername(user.getUsername())){
                    /*User user = new User(null, username, password, email);*/
                    userService.registUser(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user",user);
                    request.setAttribute("msg","注册成功!");
                    request.getRequestDispatcher("pages/user/success.jsp").forward(request,response);
                }else{
                    System.out.println("用户名[" + user.getUsername() + "]已存在!");
                    request.setAttribute("msg","用户名已存在请更换");
                    request.getRequestDispatcher("pages/user/regist.jsp").forward(request,response);
                }
            }else{
                System.out.println(" 验 证 码 [" + code + "] 错 误 ");
                request.setAttribute("msg","验证码不正确");
                request.getRequestDispatcher("pages/user/regist.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * 注销
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse  response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //	1、销毁Session 中用户登录的信息（或者销毁Session）
        session.invalidate();

        Cookie username = CookieUtils.findCookie("username", request.getCookies());
        Cookie password = CookieUtils.findCookie("password", request.getCookies());
        if(username != null){
            username.setMaxAge(0);//立刻失效
            response.addCookie(username);
        }
        if(password != null){
            password.setMaxAge(0);//立刻失效
            response.addCookie(password);
        }

        //	2、重定向到首页（或登录页面）。
        response.sendRedirect("index.jsp");/*request.getContextPath()  index.jsp  /book */
    }
}

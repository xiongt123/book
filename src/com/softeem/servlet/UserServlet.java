package com.softeem.servlet;

import com.softeem.bean.User;
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

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);

        UserServiceImpl userService = new UserServiceImpl();
        try {
            /*User user = new User(null, username, password,null);*/
            User login = userService.login(user);
            if(login!=null){
                //登录成功
                Cookie usercookie = new Cookie("username", login.getUsername());
                Cookie passwordcookie = new Cookie("password", login.getPassword());
                usercookie.setMaxAge(60 * 60 * 24 * 7);//当前Cookie 一周内有效resp.addCookie(cookie);
                passwordcookie.setMaxAge(60 * 60 * 24 * 7);//当前Cookie 一周内有效resp.addCookie(cookie);
                response.addCookie(usercookie);
                response.addCookie(passwordcookie);

                HttpSession session = request.getSession();//会话作用域
                session.setAttribute("user",login);
                request.setAttribute("msg","欢迎回来!");

                if(request.getParameter("url") != null && !request.getParameter("url").equals("")){
                    request.getRequestDispatcher(request.getParameter("url")).forward(request,response);
                }else{
                    request.getRequestDispatcher("pages/user/success.jsp").forward(request,response);
                }
            }else{
                //登录失败
                System.out.println("用户[" + user.getUsername() + "]不存在!");
                request.setAttribute("msg","账号名或登陆密码不正确");
                request.setAttribute("username",user.getUsername());
                request.setAttribute("password",user.getUsername());

                request.getRequestDispatcher("pages/user/login.jsp").forward(request,response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*//response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        //回显
        request.setAttribute("username",username);
        request.setAttribute("password",password);
        request.setAttribute("email",email);
        request.setAttribute("code",code);*/
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

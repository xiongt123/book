package com.softeem.filter;

import com.softeem.utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "TransactionFilter",urlPatterns = "/*")
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            System.out.println("进入TransactionFilter.");
            filterChain.doFilter(servletRequest, servletResponse);//向后执行

            System.out.println("回到TransactionFilter`~~~~~~");
            JdbcUtils.commitAndClose();// 提交事务
        } catch (Exception e) {
            System.out.println("回到TransactionFilter因为出现了异常~~~");
            JdbcUtils.rollbackAndClose();//回滚事务
            e.printStackTrace();//打印异常信息
            //filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
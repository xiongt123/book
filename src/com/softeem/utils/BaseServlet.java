package com.softeem.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");//解决post乱码问题 ,get不会乱码
        response.setContentType("text/html;charset=utf-8");//解决输出数据乱码
        String getmethod = request.getParameter("action");
        System.out.println("getmethod = " + getmethod);
        Class<? extends BaseServlet> aClass = this.getClass();
        //会得到一个方法对象
        try {
            Method declaredMethod = aClass.getDeclaredMethod(getmethod, HttpServletRequest.class, HttpServletResponse.class);
            declaredMethod.setAccessible(true);//取消访问检查
            declaredMethod.invoke(this, request, response);
        /*} catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }*/
        } catch (Exception e) {
            System.out.println("BaseServlet的异常处理..继续抛");
            throw new RuntimeException(e);//把异常抛给Filter 过滤器
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);//regist表单是使用的post提交
    }

}

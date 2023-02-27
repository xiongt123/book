package com.softeem.servlet;

import com.softeem.bean.Tbook;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/BookServlet")
public class BookServlet extends BaseServlet {

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try {
            List<Tbook> tbooks = bookService.queryBooks();
            request.setAttribute("bookList", tbooks);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Tbook book = new Tbook();
        BookService bookService = new BookServiceImpl();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环这6段数据并处理它们
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        //System.out.println(fileItem.getFieldName() +" = " + MyUtils.parseString(fileItem.getString()));
                        if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        }
                    } else {
                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        //文件名 = 123.jpg       suffix = .jpg
                        String suffix = filename.substring(filename.lastIndexOf("."));
                        //通过时间毫秒 + 后缀 = 新文件名
                        String newfilename = System.currentTimeMillis() + suffix;
                        ServletContext application = this.getServletContext();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String format = simpleDateFormat.format(new Date());
                        File file = new File("D:/bookimg/" + format + "/");
                        if (!file.exists()) {//判断要上传的文件目录是否存在
                            file.mkdirs();//创建目录
                        }
                        System.out.println(file.getAbsolutePath()); //打印创建的根目录
                        fileItem.write(new File(file, newfilename));//上传图片
                        book.setImgPath("/bookimg/" + format + "/" + newfilename);//图书封面
                    }
                }
                bookService.addBook(book);//将图片信息保存到数据库

                int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
                response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }

    protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        BookService bookService = new BookServiceImpl();
        try {
            Tbook tbook = bookService.queryBookById(Integer.valueOf(id));
            request.setAttribute("book", tbook);
            //response.sendRedirect("/book/pages/manager/book_edit.jsp");
            int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
            request.setAttribute("pageNo", pageNo);
            System.out.println("pageNo = " + pageNo);
            request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Tbook book = new Tbook();
        BookService bookService = new BookServiceImpl();
        if (ServletFileUpload.isMultipartContent(request)) {
            //创建FileItemFactory 工厂实现类
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // 创建用于解析上传数据的工具类ServletFileUpload 类
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // 解析上传的数据，得到每一个表单项FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //循环这6段数据并处理它们
                for (FileItem fileItem : list) {
                    //判断那些是普通表单项,还是上传的文件类型
                    System.out.println("fileItem = " + fileItem);
                    if (fileItem.isFormField()) {
                        //处理普通表单项
                        //System.out.println(fileItem.getFieldName() +" = " + MyUtils.parseString(fileItem.getString()));
                        if ("name".equals(fileItem.getFieldName())) {
                            book.setName(fileItem.getString("utf-8"));//图书名
                        } else if ("author".equals(fileItem.getFieldName())) {
                            book.setAuthor(fileItem.getString("utf-8"));//图书作者
                        } else if ("price".equals(fileItem.getFieldName())) {
                            book.setPrice(new BigDecimal(fileItem.getString()));//图书价格
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            book.setSales(Integer.valueOf(fileItem.getString()));//图书销量
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            book.setStock(Integer.parseInt(fileItem.getString()));//图书库存
                        } else if ("id".equals(fileItem.getFieldName())) {
                            book.setId(Integer.valueOf(fileItem.getString()));//图书id
                        }
                    } else {
                        Tbook tbook = bookService.queryBookById(book.getId());
                        String imgPath = tbook.getImgPath();

                        //处理文件类型(文件上传)
                        String filename = fileItem.getName();//获取文件名
                        //文件名 = 123.jpg       suffix = .jpg
                        if (filename != "") {
                            //如果修改了图片
                            //调用封装的删除文件方法
                            WebUtils.deleteFile("D:" + tbook.getImgPath());

                            String suffix = filename.substring(filename.lastIndexOf("."));
                            //通过时间毫秒 + 后缀 = 新文件名
                            String newfilename = System.currentTimeMillis() + suffix;
                            ServletContext application = this.getServletContext();

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = simpleDateFormat.format(new Date());
                            //得到了工程路径
                            /*String realPath = application.getRealPath("/");*/
                            //                        File file = new File(realPath + "/bookimg/" + format + "/");//out 文件见
                            File newfile = new File("d:/bookimg/" + format + "/"); //虚拟目录
                            if (!newfile.exists()) {//判断要上传的文件目录是否存在
                                newfile.mkdirs();//创建目录
                            }
                            System.out.println(newfile.getAbsolutePath());
                            fileItem.write(new File(newfile, newfilename));//上传图片
                            book.setImgPath("/bookimg/" + format + "/" + newfilename);//图书封面
                        } else {
                            //如果没有修改图片
                            System.out.println(imgPath);
                            book.setImgPath(imgPath);
                        }
                    }
                }
                System.out.println("book = " + book);

                bookService.updateBook(book);//将图片信息保存到数据库

                int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
                System.out.println("pageNo = " + pageNo);
                response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.println("不是多段数据..无法上传文件!");
        }
    }

    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try {
            Integer id = WebUtils.parseInt(request.getParameter("id"), 0);
            //先删除图片
            Tbook tbook = bookService.queryBookById(id);
            //调用封装的删除文件方法
            WebUtils.deleteFile("D:" + tbook.getImgPath());

            //删除数据库中的数据
            bookService.deleteBookById(id);
            /*response.sendRedirect("BookServlet?action=list");*/

            int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
            response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        //1 获取请求的参数pageNo he pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到值默认显示第1页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取不到值默认每页显示4条
        try {
            Page<Tbook> page = bookService.page(pageNo, pageSize);
            page.setUrl("BookServlet?action=page&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    protected void searchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        //1 获取请求的参数 pageNo 和 pageSize name author min max
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        String author = request.getParameter("author") == null ? "" : request.getParameter("author");
        BigDecimal min = WebUtils.parseBigDecimal(request.getParameter("min"), new BigDecimal(0));
        BigDecimal max = WebUtils.parseBigDecimal(request.getParameter("max"), new BigDecimal(0));
        //同时设置查询条件的回显参数
        request.setAttribute("name", name);
        request.setAttribute("author", author);
        request.setAttribute("min", min);/*request.getParameter("min")*/
        request.setAttribute("max", max);
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到默认显示第1页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取到默认每页显示4条数据
        try {
            Page<Tbook> page = bookService.page(name, author, min, max, pageNo, pageSize);
            page.setUrl("BookServlet?action=searchPage&name=" + name + "&author=" + author + "&min=" + (min == new BigDecimal(0) ? "" : min) + "&max=" + (max == new BigDecimal(0) ? "" : max) + "&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

/*    protected void searchPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        //1 获取请求的参数 pageNo 和 pageSize
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);//取不到默认显示第1页
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);//取到默认每页显示4条数据
        try {
            Page<Tbook> page = bookService.searchPricePage(new BigDecimal(min),new BigDecimal(max),pageNo, pageSize);
            page.setUrl("BookServlet?action=searchPrice&min="+min+"&max="+max+"&");
            request.setAttribute("page",page);
            request.setAttribute("min",min);
            request.setAttribute("max",max);
            request.getRequestDispatcher("/home.jsp").forward(request,response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }*/



}

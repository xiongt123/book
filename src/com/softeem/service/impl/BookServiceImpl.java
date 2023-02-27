package com.softeem.service.impl;

import com.softeem.bean.Tbook;
import com.softeem.dao.TbookDao;
import com.softeem.dao.impl.TbookDaoImpl;
import com.softeem.service.BookService;
import com.softeem.utils.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {

    private TbookDao bookDao = new TbookDaoImpl();

    @Override
    public void addBook(Tbook tbook) throws SQLException {
        bookDao.save(tbook);
    }

    @Override
    public void deleteBookById(Integer id) throws SQLException {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(Tbook tbook) throws SQLException{
        bookDao.updateById(tbook);
    }

    @Override
    public Tbook queryBookById(Integer id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<Tbook> queryBooks() throws SQLException {
        return bookDao.findAll();
    }

    @Override
    public Page<Tbook> page(int pageNo, int pageSize) throws SQLException {
        Page<Tbook> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        Integer totalCount = bookDao.queryForPageTotalCount();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount+pageSize-1)/pageSize);//设置总页数
        page.setPageNo(pageNo);// 设置当前页码
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize));// 求当前页数据
        return page;
    }

    @Override
    public Page<Tbook> page(String name,String author,BigDecimal min, BigDecimal max,int pageNo, int pageSize) throws SQLException {

        Page<Tbook> page = new Page<>();
        // 设置每页显示的数量
        page.setPageSize(pageSize);
        Integer totalCount = bookDao.queryForPageTotalCount(name,author,min,max);//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount+pageSize-1)/pageSize);//设置总页数
        page.setPageNo(pageNo);// 设置当前页码
        page.setItems(bookDao.queryForPageItems(name,author,min, max,(page.getPageNo()-1)*pageSize,pageSize));// 求当前页数据
        return page;
    }
}

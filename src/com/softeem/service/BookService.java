package com.softeem.service;


import com.softeem.bean.Tbook;
import com.softeem.utils.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


public interface BookService {

    /**
     * 增加图书
     * @param tbook
     * @throws SQLException
     */
    public void addBook(Tbook tbook) throws SQLException;

    /**
     * 删除图书
     * @param id
     * @throws SQLException
     */
    public void deleteBookById(Integer id) throws SQLException;

    /**
     * 修改图书
     * @param tbook
     * @throws SQLException
     */
    public void updateBook(Tbook tbook) throws SQLException;

    /**
     * 查询图书id
     * @param id
     * @return
     * @throws SQLException
     */
    public Tbook queryBookById(Integer id) throws SQLException;

    /**
     * 查询图书
     * @return
     * @throws SQLException
     */
    public List<Tbook> queryBooks() throws SQLException;

    /**
     * 分页查询
     * @param
     * @param pageSize
     * @return
     * @throws SQLException
     */
    public Page<Tbook> page(int pageNo, int pageSize)  throws SQLException;

    /**
     *
     */
    public Page<Tbook> page(String name,String author,BigDecimal min, BigDecimal max,int pageNo, int pageSize)  throws SQLException;
}

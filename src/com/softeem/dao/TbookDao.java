package com.softeem.dao;

import com.softeem.bean.Tbook;
import com.softeem.utils.BaseInterface;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface TbookDao extends BaseInterface<Tbook> {

    public Integer queryForPageTotalCount() throws SQLException;

    public List<Tbook> queryForPageItems(int begin, int pageSize)  throws SQLException;

    public Integer queryForPageTotalCount(String name,String author,BigDecimal min, BigDecimal max) throws SQLException;

    public List<Tbook> queryForPageItems(String name,String author,BigDecimal min,BigDecimal max,int begin,int pageSize) throws SQLException;


}

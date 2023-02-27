package com.softeem.dao.impl;

import com.softeem.bean.Admin;
import com.softeem.dao.AdminDao;
import com.softeem.utils.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl extends BaseDao implements AdminDao {

    @Override
    public List<Admin> findAll() throws SQLException {
        BeanListHandler<Admin> handler = new BeanListHandler<>(Admin.class);
        List<Admin> query = queryRunner.query("select * from t_admin;", handler);
        return query;
    }

    @Override
    public Long save(Admin admin) throws SQLException {
        /*int update = queryRunner.update("insert into t_admin values(null,?,?,?)",
                admin.getAdminname(), admin.getPassword(), admin.getEmail());*/
        Long insert = queryRunner.insert("insert into t_admin values(null,?,?)",
                new ScalarHandler<Long>(), admin.getUsername(), admin.getPassword());
        admin.setId(insert.intValue());
        return insert;
    }

    @Override
    public int updateById(Admin admin) throws SQLException {
        int update = queryRunner.update("update t_admin set username=?,password=? where id=?",
                admin.getUsername(), admin.getPassword(), admin.getId());
        return update;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        int update = queryRunner.update("delete from t_admin where id=?", id);
        return update;
    }

    @Override
    public Admin findById(Integer id) throws SQLException {
        BeanHandler<Admin> handler = new BeanHandler<>(Admin.class);
        Admin query = queryRunner.query("select * from t_admin where id=?", handler, id);
        return query;
    }

    @Override
    public List<Admin> page(Integer pageNumber) throws SQLException {
        BeanListHandler<Admin> handler = new BeanListHandler<>(Admin.class);
        List<Admin> query = queryRunner.query("select * from t_admin limit ?,?", handler, (pageNumber - 1) * pageSize, pageSize);
        return query;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query("select count(*) from t_admin;", handler);
        return query.intValue();
    }

    @Override
    public Admin queryAdminByUsernameAndPassword(String username, String password) throws SQLException {
        String sql="select * from t_admin where username=? and password=?";
        return queryRunner.query(sql,new BeanHandler<>(Admin.class),username,password);
    }
}

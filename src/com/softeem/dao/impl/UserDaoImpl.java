package com.softeem.dao.impl;

import com.softeem.bean.User;
import com.softeem.dao.UserDao;
import com.softeem.utils.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public List<User> findAll() throws SQLException {
        BeanListHandler<User> handler = new BeanListHandler<>(User.class);
        List<User> query = queryRunner.query("select * from t_user;", handler);
        return query;
    }

    @Override
    public Long save(User user) throws SQLException {
        /*int update = queryRunner.update("insert into t_user values(null,?,?,?)",
                user.getUsername(), user.getPassword(), user.getEmail());*/
        Long insert = queryRunner.insert("insert into t_user values(null,?,?,?)",
                new ScalarHandler<Long>(), user.getUsername(), user.getPassword(), user.getEmail());
        user.setId(insert.intValue());
        return insert;
    }

    @Override
    public int updateById(User user) throws SQLException {
        int update = queryRunner.update("update t_user set username=?,password=?,email=? where id=?",
                user.getUsername(), user.getPassword(), user.getEmail(),
                user.getId());
        return update;
    }

    @Override
    public int deleteById(Integer id) throws SQLException {
        int update = queryRunner.update("delete from t_user where id=?", id);
        return update;
    }

    @Override
    public User findById(Integer id) throws SQLException {
        BeanHandler<User> handler = new BeanHandler<>(User.class);
        User query = queryRunner.query("select * from t_user where id=?", handler, id);
        return query;
    }

    @Override
    public List<User> page(Integer pageNumber) throws SQLException {
        BeanListHandler<User> handler = new BeanListHandler<>(User.class);
        List<User> query = queryRunner.query("select * from t_user limit ?,?", handler, (pageNumber - 1) * pageSize, pageSize);
        return query;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query("select count(*) from t_user;", handler);
        return query.intValue();
    }

    @Override
    public User queryUserByUsername(String username) throws SQLException {
        String sql="select * from t_user where username=?";
        return queryRunner.query(sql,new BeanHandler<>(User.class),username);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException{
        String sql="select * from t_user where username=? and password=?";
        return queryRunner.query(sql,new BeanHandler<>(User.class),username,password);
    }
}

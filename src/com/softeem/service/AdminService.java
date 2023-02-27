package com.softeem.service;

import com.softeem.bean.Admin;
import com.softeem.bean.User;

import java.sql.SQLException;

public interface AdminService {

    /**
     *	注册用户
     *	@param admin
     */
    public void registUser(Admin admin) throws SQLException;

    /**
     *	登录
     *	@param admin
     *	@return 如果返回null，说明登录失败，返回有值，是登录成功
     */
    public Admin login(Admin admin) throws SQLException;
}

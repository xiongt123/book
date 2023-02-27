package com.softeem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    private Integer id;//id
    private String username;//用户名
    private String password;//密码
}

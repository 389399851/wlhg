package com.yd.taozi.service;

import com.yd.taozi.pojo.User;

/**
 * Created by xiaotaozi on 2019/6/29.
 */
public interface AdminService {
    //登录注册
    public User findUserByName(String username);
    public void insertUser(User user);
}

package com.yd.taozi.service;

import com.yd.taozi.pojo.User;

/**
 * Created by xiaotaozi on 2019/7/6.
 */
public interface UserService {
    public User findUserByName(String username);
    public void insertUser(User user);
}

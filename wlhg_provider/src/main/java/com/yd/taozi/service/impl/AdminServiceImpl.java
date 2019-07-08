package com.yd.taozi.service.impl;
import com.yd.taozi.mapper.UserMapper;
import com.yd.taozi.pojo.User;
import com.yd.taozi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaotaozi on 2019/6/29.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}

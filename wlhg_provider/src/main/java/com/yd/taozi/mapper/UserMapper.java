package com.yd.taozi.mapper;

import com.yd.taozi.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by xiaotaozi on 2019/6/28.
 */
@Mapper
public interface UserMapper {
    //登录注册
    public User findUserByName(@Param("username") String username);
    public void insertUser(User user);
}

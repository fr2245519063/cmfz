package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> findByPage(Integer page, Integer rows);

    void insert(User user);

    void delete(String[] id);

    void update(User user);

    Integer getCount();

    Integer findByCount(String sex,Integer bay);

    List<Users> findAll();
}

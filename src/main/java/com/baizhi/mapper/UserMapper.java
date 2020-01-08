package com.baizhi.mapper;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> findByPage(@Param("start") Integer start, @Param("rows")Integer rows);

    void insert(User user);

    void delete(String[] id);

    void update(User user);

    Integer getCount();

    Integer findByCount(@Param("sex")String sex,@Param("day")Integer bay);

    List<Users> findAll();

    User findOne(String id);
}

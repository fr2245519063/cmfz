package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import com.baizhi.mapper.UserMapper;
import org.apache.ibatis.annotations.Arg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Integer start = (page-1)* rows;
        Integer count = userMapper.getCount();
        Integer total = count % rows == 0 ? count/rows:count/rows+1;
        List<User> byPage = userMapper.findByPage(start,rows);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",byPage);
        map.put("total",count);
        map.put("records",count);
        return map;
    }

    @Override
    public void insert(User user) {
        user.setNewdata(new Date());
        userMapper.insert(user);
    }

    @Override
    public void delete(String[] id) {
    userMapper.delete(id);
    }

    @Override
    public void update(User user) {
    userMapper.update(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCount() {
        return userMapper.getCount();
    }

    @Override
    public Integer findByCount(String sex, Integer bay) {
        return userMapper.findByCount(sex,bay);
    }

    @Override
    public List<Users> findAll() {
        return userMapper.findAll();
    }
}

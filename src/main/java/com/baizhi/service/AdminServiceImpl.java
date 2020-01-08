package com.baizhi.service;

import com.baizhi.entity.Admin;
import com.baizhi.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> login(String username, String password, String enCode, HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        String  code = (String) session.getAttribute("code");
        if(enCode.equals(code)){
            Admin login = adminMapper.login(username);
            if(login!=null){
                if(password.equals(login.getPassword())){
                    map.put("mgs","ok");
                    return map;
                }else {
                    map.put("mgs","密码错误");
                    return map;
                }
            }else {
                map.put("mgs","用户不存在");
                return map;
            }
        }else {
            map.put("mgs","验证码错误");
            return map;
        }
    }
}

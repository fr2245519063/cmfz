package com.baizhi.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baizhi.entity.Banner;
import com.baizhi.entity.City;
import com.baizhi.entity.User;
import com.baizhi.entity.Users;
import com.baizhi.mapper.CityMapper;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.goeasy.GoEasy;
import net.minidev.json.JSONUtil;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CityMapper cityMapper;

    @RequestMapping("findByPage")
    public Map<String,Object> findByPage(Integer page,Integer rows){
        Map<String, Object> byPage = userService.findByPage(page, rows);
        return byPage;
    }

    @RequestMapping("edit")
    public String edit(String oper,String[] id,User user){
        if(oper.equals("add")){
            String s = UUID.randomUUID().toString();
            user.setId(s);
            userService.insert(user);
            String s2 = JSON.toJSONString(user);
            System.out.println(s2);

            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-488783aef3ac412aa56aeb68c163816c");
            goEasy.publish("cmfz", s2);           return s;
        }else if(oper.equals("del")){
            userService.delete(id);
        }else {
            if(user.getHead_img()==""){
                user.setHead_img(null);
                userService.update(user);
            }else{
                userService.update(user);
            }
        }
        return null;
    }

    @RequestMapping("upload")
    public void uploda(MultipartFile head_img, String bannerId, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
            //递归创建文件夹
        }
        String originalFilename = head_img.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            head_img.transferTo(new File(realPath, newFileName));
            User user = new User();
            user.setId(bannerId);
            user.setHead_img(newFileName);
            userService.update(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 修改图片路径

    }

    @RequestMapping("city")
    public List<City> city(){
        List<City> all = cityMapper.findAll();
        return all;
    }

    @RequestMapping("findByCount")
    public Map<String ,Object> findByCount(){
        Map<String, Object> map = new HashMap<>();
        List manList = new ArrayList();
        manList.add(userService.findByCount("男",1));
        manList.add(userService.findByCount("男",7));
        manList.add(userService.findByCount("男",30));
        manList.add(userService.findByCount("男",365));
        List womanList = new ArrayList();
        womanList.add(userService.findByCount("女",1));
        womanList.add(userService.findByCount("女",7));
        womanList.add(userService.findByCount("女",30));
        womanList.add(userService.findByCount("女",365));
        map.put("man",manList);
        map.put("woman",womanList);
        return map;
    }

    @RequestMapping("findCity")
    public List<Users> findCity(){
        return userService.findAll();
    }


}

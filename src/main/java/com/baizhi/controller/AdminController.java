package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.HttpsURLConnection;
import javax.print.attribute.standard.JobOriginatingUserName;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(String username, String enCode, String password, HttpSession sessiond){
        Map<String, Object> login = adminService.login(username,password,enCode,sessiond);
        sessiond.setAttribute("username",username);
        return login;
    }

    @RequestMapping("quit")
    public String quit(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }







}

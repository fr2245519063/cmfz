package com.baizhi.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.entity.*;
import com.baizhi.mapper.*;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("user1")
public class JeiController {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BannerMapper bannerMapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    GuruMapper guruMapper;
    @Autowired
    TaskMapper taskMapper;

    @RequestMapping("login")
    public Map<String ,Object> login(String username, String password){
        HashMap<String, Object> map = new HashMap<>();
        Admin login = adminMapper.login(username);
        if(login!=null){
            if(username.equals(login.getUsername())){
                if(password.equals(login.getPassword())){
                    map.put("status","200");
                    map.put("user",login);
                    return map;
                }else {
                    map.put("status","-200");
                    map.put("message","密码错误");
                    return map;
                }
            }else {
                map.put("status","-200");
                map.put("message","请检查账号");
                return map;
            }
        }else{
            map.put("status","-200");
            map.put("message","用户不存在");
            return map;
        }
    }
        @RequestMapping("iphone")
        public Map<String,Object> iphone(String phone) {
            HashMap<String, Object> map = new HashMap<>();
            String s = UUID.randomUUID().toString();
            String code = s.substring(0, 4);
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fq2F47eTzwKVHaG167n", "aTOq8oIE9rYUHR2omtBOUBz54mVHPB");
            IAcsClient client = new DefaultAcsClient(profile);
            CommonRequest request = new CommonRequest();
            request.setMethod(MethodType.POST);
            request.setDomain("dysmsapi.aliyuncs.com");
            request.setVersion("2017-05-25");
            request.setAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", "得物");
            request.putQueryParameter("TemplateCode", "SMS_181862981");
            request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
            try {
                CommonResponse response = client.getCommonResponse(request);
                System.out.println(response.getData());
                map.put("status","200");
                map.put("message","验证码发送成功");
                return map;
            } catch (ClientException e) {
                e.printStackTrace();
                map.put("status","-200");
                map.put("message","验证码发送失败");
                return map;
            }
        }

        @RequestMapping("update")
        public Map<String ,Object> update(User user){
            User one = userMapper.findOne(user.getId());
            HashMap<String, Object> map = new HashMap<>();
            if(one!=null){
                userMapper.update(one);
                map.put("status","200");
                map.put("user",one);
                return map;
            }else{
                map.put("status","-200");
                map.put("message","用户不存在");
                return map;
            }
        }

        @RequestMapping("onePage")
        public Map onePage(String uid,String type,String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> time = bannerMapper.findTime();
                List<Album> byPage = albumMapper.findByPage(0, 5);
                List<Article> list = articleMapper.selectAll();
                hashMap.put("status",200);
                hashMap.put("head",time);
                hashMap.put("albums",byPage);
                hashMap.put("articles",list);
            }else if (type.equals("wen")){
                List<Album> byPage = albumMapper.findByPage(0, 5);
                hashMap.put("status",200);
                hashMap.put("albums",byPage);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> list = articleMapper.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",list);
                }else {
                    List<Article> list = articleMapper.selectAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",list);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }

    @RequestMapping("article")
    public Map<String,Object> article(String uid,String aid){
        User one = userMapper.findOne(uid);
        Article one1 = articleMapper.findOne(aid);
        Map hashMap = new HashMap();
        if(one!=null){
            hashMap.put("status",200);
            hashMap.put("message",one1);
            return hashMap;
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","error");
            return hashMap;
        }
    }


    @RequestMapping("KingKong")
    public Map<String,Object> KingKong(String id){
        Map hashMap = new HashMap<String ,Object>();
        User one = userMapper.findOne(id);
        List<User> byPage = userMapper.findByPage(0, 5);
        for (User user : byPage) {
            if(user.getId()!=one.getId()){
                hashMap.put("status",200);
                hashMap.put("message",user);
                return hashMap;
            }else {
                hashMap.put("status","-200");
                hashMap.put("message","error");
                return hashMap;
            }
        }
        hashMap.put("status","-200");
        hashMap.put("message","error");
        return hashMap;
    }


    @RequestMapping("album")
    public Map<String,Object> album(String uid,String aid){
        User one = userMapper.findOne(uid);
        Album one1 = albumMapper.findOne(aid);
        Map hashMap = new HashMap();
        if(one!=null){
            hashMap.put("status",200);
            hashMap.put("message",one1);
            return hashMap;
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","error");
            return hashMap;
        }
    }

    @RequestMapping("guru")
    public Map<String , Object> guru(){
        List<Guru> all = guruMapper.findAll();
        Map hashMap = new HashMap<String ,Object>();
        hashMap.put("status","200");
        hashMap.put("message",all);
        return hashMap;
    }

    @RequestMapping("attention")
    public Map<String,Object> attention(String uid,String gid){
        User one = userMapper.findOne(uid);
        Map hashMap = new HashMap<String ,Object>();
        Guru one1 = guruMapper.findOne(gid);
        if(one1!=null){
            one.setFaname(one1.getDharma());
            hashMap.put("status","200");
            hashMap.put("message",one);
            return hashMap;
        }else {
            hashMap.put("status","-200");
            hashMap.put("message","上师不存在");
            return hashMap;
        }

    }

    @RequestMapping("findTask")
    public Map<String,Object> findTask(String uid){
        Map hashMap = new HashMap<String ,Object>();
        Task all = taskMapper.findAll(uid);
        if(uid!=null){
            hashMap.put("status","200");
            hashMap.put("message",all);
            return hashMap;
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","error");
            return hashMap;
        }

    }

    @RequestMapping("deleteTask")
    public Map<String,Object> deleteTask(String uid,String tid){
        Map hashMap = new HashMap<String ,Object>();
        User one = userMapper.findOne(uid);
        if(one!=null){
            taskMapper.delete(tid);
            hashMap.put("status","200");
            hashMap.put("message","删除成功");
            return hashMap;
        }else{
            hashMap.put("status","-200");
            hashMap.put("message","删除失败");
            return hashMap;
        }
    }

}

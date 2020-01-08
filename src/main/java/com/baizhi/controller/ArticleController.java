package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

   @RequestMapping("findByPage")
    public Map<String,Object> findByPage(Integer page,Integer rows){
       Map<String, Object> byPage = articleService.findByPage(page, rows);
       return  byPage;
   }
   @RequestMapping("add")
    public void add(Article article){
       articleService.insert(article);
   }
   @RequestMapping("update")
    public void update(Article article){
       articleService.update(article);
   }

    @RequestMapping("del")
    public void del(String [] id){
       articleService.delete(id);
    }

    @RequestMapping("upload")
    public  Map<String ,Object> updata(MultipartFile img, HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if(!file.mkdirs()){
            file.exists();
        }
        String newFilename=new Date().getTime()+"_"+img.getOriginalFilename();
        img.transferTo(new File(realPath,newFilename));
        //获取当前的网站的协议名
        String scheme = request.getScheme();
        //获取本机的id地址
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //获取当前的端口号
        int serverPort = request.getServerPort();
        //获取当前项目名
        String path = request.getContextPath();
        //拼接
        String url=scheme+"://"+hostAddress+":"+serverPort+path+"/img/"+newFilename;
        System.out.println(url);
        map.put("error",0);
        map.put("url",url);
        return  map;
    }

    @RequestMapping("allImages")
    public Map<String,Object> all(HttpServletRequest request,HttpSession session) throws UnknownHostException {
        Map<String, Object> map = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        //拿到文件的相对路径
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        //拿到文件的数量
        String[] strings = file.list();
        //遍历
        for (String string : strings) {
            Map<String, Object> map1 = new HashMap<>();
            //富文本编辑器中图片的参数列表
            map1.put("is_dir",false);
            map1.put("has_file",false);
            //获取文件的大小
            File file1 = new File(realPath,string);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            //获取文件名的后缀
            String s = string.substring(string.lastIndexOf(".") + 1);
            map1.put("filetype",s);
            map1.put("filename",string);
            if(s.contains("_")){
                String s2=s.split("_")[0];
                Long aLong = Long.valueOf(s2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(aLong);
                map1.put("datetime",format);
            }else {
                map1.put("datetime",new Date());
            }
            list.add(map1);
        }
        map.put("moveup_dir_path","");
        map.put("current_dir_path","");
        //网站的协议名
        String scheme = request.getScheme();
        //本机id
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        //端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String path = request.getContextPath();
        String url=scheme+"://"+hostAddress+":"+serverPort+path+"/img/";
        map.put("current_url",url);
        //文件的数量
        int size = list.size();
        map.put("total_count",size);
        map.put("file_list",list);

        return map;
    }

}

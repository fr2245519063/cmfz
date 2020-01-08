package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @ResponseBody
    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> byPage = bannerService.findByPage(page, rows);
        return byPage;
    }

    @RequestMapping("upload")
    public void uploda(MultipartFile img, String bannerId, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
            //递归创建文件夹
        }
        String originalFilename = img.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            img.transferTo(new File(realPath, newFileName));
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setImg(newFileName);
            bannerService.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 修改图片路径

    }

    @RequestMapping("edit")
    public String edit(Banner banner, String oper, String[] id) {
        if (oper.equals("add")) {
            String s = UUID.randomUUID().toString();
            banner.setId(s);

            bannerService.insert(banner);
            return s;
        } else if (oper.equals("del")) {
            bannerService.delete(id);
        } else {
            if (banner.getImg() == "") {
                banner.setImg(null);
                bannerService.update(banner);
            } else {
                bannerService.update(banner);
                return banner.getId();
            }
        }
        return null;
    }

    @RequestMapping("queryAll")
    public List<Banner> queryAll(HttpServletResponse response) throws IOException {
        List<Banner> all = bannerService.findAll(response);
        return all;
    }




}

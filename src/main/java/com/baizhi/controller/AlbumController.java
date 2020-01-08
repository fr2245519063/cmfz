package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;


    @ResponseBody
    @RequestMapping("findByPage")
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Map<String, Object> map = albumService.findByPage(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Album album, String oper, String[] id) {
        if (oper.equals("add")) {
            albumService.save(album);
        }
        if (oper.equals("del")) {
            albumService.del(id);
        } else {
            if (album.getImg() == "") {
                album.setImg(null);
                albumService.update(album);
            } else {
                albumService.update(album);
                return album.getId();
            }
        }

        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile img, String bannerId, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = img.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            img.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Album album = new Album();
        album.setId(bannerId);
        album.setImg(newFileName);
        albumService.update(album);

    }
}

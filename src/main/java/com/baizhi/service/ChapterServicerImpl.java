package com.baizhi.service;

import com.baizhi.entity.Chapter;
import com.baizhi.entity.User;
import com.baizhi.mapper.ChapterMapper;
import com.mysql.jdbc.ByteArrayRow;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections4.Put;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@Service
@Transactional
public class ChapterServicerImpl implements ChapterService{
    @Autowired
    ChapterMapper chapterMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findByPage(Integer page, Integer rows, String id) {
        Integer start=(page - 1 )*  rows;
        Integer count = chapterMapper.getcount(id);
        Integer total = count % rows == 0 ? count /rows : count / rows + 1;
        HashMap<String, Object> map = new HashMap<>();
        List<Chapter> byPage = chapterMapper.findByPage(start, rows, id);
        map.put("page",page);
        map.put("rows",byPage);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    public void delete(String[] id) {
    chapterMapper.delete(id);
    }

    @Override
    public void update(Chapter chapter) {
    chapterMapper.update(chapter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getcount(String id) {
        return chapterMapper.getcount(id);
    }

    @Override
    public String insert(Chapter chapter) {
        String s= UUID.randomUUID().toString();
        chapter.setId(s);
    chapterMapper.insert(chapter);
    return  s;

    }


}

package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    Map<String,Object> findByPage(Integer page,Integer rows,String id);

    void delete(String[] id);

    void update(Chapter chapter);

    Integer getcount(String id);

    String insert(Chapter chapter);
}

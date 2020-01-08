package com.baizhi.service;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.OBJ_ADAPTER;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Map<String,Object> findByPage(Integer page, Integer rows);

    void insert(Article article);

    void delete(String[] id);

    void update(Article article);

    Integer getCount();
}

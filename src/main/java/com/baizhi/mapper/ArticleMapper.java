package com.baizhi.mapper;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.Array;
import java.util.List;

public interface ArticleMapper {
    List<Article> findByPage(@Param("start")Integer start,@Param("rows")Integer rows);

    void insert(Article article);

    void delete(String [] id);

    void update(Article article);

    Integer getCount();

    List<Article> selectAll();

    Article findOne(String id);

}

package com.baizhi.service;

import com.baizhi.entity.Article;
import com.baizhi.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements  ArticleService{
    @Autowired
    ArticleMapper articleMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Integer count = articleMapper.getCount();
        Integer start = (page -1) * rows;
        Integer total = count % rows ==0 ? count/rows:count/rows+1;
        List<Article> byPage = articleMapper.findByPage(start, rows);
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",byPage);
        map.put("total",total);
        map.put("records",count);
        return map;
    }

    @Override
    public void insert(Article article) {
        article.setId(UUID.randomUUID().toString());
        article.setCreateDate(new Date());
    articleMapper.insert(article);
    }

    @Override
    public void delete(String[] id) {
    articleMapper.delete(id);
    }

    @Override
    public void update(Article article) {
    articleMapper.update(article);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getCount() {
        return articleMapper.getCount();
    }
}

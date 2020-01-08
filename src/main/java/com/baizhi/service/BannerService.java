package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BannerService {
    void insert(Banner banner);

    void update(Banner banner);

    List<Banner> findAll(HttpServletResponse response) throws IOException;

    void delete(String[] id);

    public Map<String, Object> findByPage(Integer page, Integer rows);

    Integer count();
}

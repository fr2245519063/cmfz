package com.baizhi.service;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String ,Object> findByPage( Integer page, Integer rows);

    void save(Album album);

    void del(String[] id);

    void update(Album album);

    Integer getcount();

    void updateCount(Album album);
}

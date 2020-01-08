package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    List<Album> findByPage(@Param("start")Integer start,@Param("rows")Integer rows);

    void save(Album album);

    void del(String[] id);

    void upd(Album album);

    Integer getcount();

    void updateCount(Album album);

    Album findOne(String id);
}

package com.baizhi.mapper;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    List<Chapter> findByPage(@Param("start")Integer start,@Param("rows")Integer rows,@Param("id")String id);

    void delete(String[] id);

    void update(Chapter chapter);

    Integer getcount(String id);

    void insert(Chapter chapter);

    void updateCount(Album album);
}

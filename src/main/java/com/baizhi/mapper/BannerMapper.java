package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    void insert(Banner banner);

    void update(Banner banner);

    List<Banner> findAll();

    void delete(String[] id);

    List<Banner> findByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    Integer count();

    List<Banner> findTime();

}

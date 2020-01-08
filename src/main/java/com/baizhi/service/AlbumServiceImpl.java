package com.baizhi.service;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Integer start= (page -1) * rows;
        Integer count = albumMapper.getcount();
        List<Album> byPage = albumMapper.findByPage(start, rows);
        Integer total=count % rows == 0 ? count / rows:count / rows + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("rows",byPage);
        map.put("records",count);
        map.put("total",total);
        return map;
    }

    @Override
    public void save(Album album) {
        album.setId(UUID.randomUUID().toString());
        album.setPublish_date(new Date());
        albumMapper.save(album);
    }

    @Override
    public void del(String[] id) {
    albumMapper.del(id);
    }

    @Override
    public void update(Album album) {
    albumMapper.upd(album);
    }

    @Override
    public Integer getcount() {
        return albumMapper.getcount();
    }

    @Override
    public void updateCount(Album album) {
        albumMapper.updateCount(album);
    }
}

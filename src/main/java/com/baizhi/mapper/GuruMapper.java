package com.baizhi.mapper;

import com.baizhi.entity.Guru;

import java.util.List;

public interface GuruMapper {
    List<Guru> findAll();

    Guru findOne(String id);

}

package com.baizhi.mapper;

import com.baizhi.entity.Task;

import java.util.List;

public interface TaskMapper {
    void insert(Task task);

    void delete(String id);

    Task findAll(String id);
}

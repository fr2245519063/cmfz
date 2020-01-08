package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
    private String id;
    private String filepath;
    private String title;
    private String size;
    private String longs;
    private String status;
    private String album_id;
}

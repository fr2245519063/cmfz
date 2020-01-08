package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Album {
    private String id;
    private String title;
    private String img;
    private Integer score;
    private String author;
    private String beam;
    private Integer counts;
    private Date publish_date;
    private String content;
}

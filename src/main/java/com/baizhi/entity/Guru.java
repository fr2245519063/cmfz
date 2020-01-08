package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Guru {
    private String id;
    private String dharma;
    private String head_img;
    private String status;
    private String sex;
    private Date create_date;
}

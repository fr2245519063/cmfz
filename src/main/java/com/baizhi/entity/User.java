package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String head_img;
    private String faname;
    private String names;
    private String sex;
    private String city;
    private String sign;
    private String username;
    private String password;
    private String status;
    private Date newdata;


}

package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {
    @ExcelIgnore
    private String id;
    @Excel(name = "图片",type = 2)//到处type 2是图片
    private String img;
    @Excel(name = "标题")
    private String title;
    @Excel(name="状态")
    private String status;
    @Excel(name = "描述")
    private String des;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name="创建时间",format = "yyyy-MM-dd")
    private Date  create_date;
}

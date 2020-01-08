package com.baizhi;




import com.baizhi.entity.*;
import com.baizhi.mapper.*;
import com.baizhi.service.AdminService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.goeasy.GoEasy;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {
        @Autowired
        AdminMapper adminMapper;
        @Autowired
        AdminService adminService;
        @Autowired
        HttpSession session;
        @Autowired
         AlbumMapper albumMapper;
        @Autowired
        ChapterMapper chapterMapper;
        @Autowired
        UserMapper userMapper;
        @Autowired
        ArticleMapper articleMapper;
        @Autowired
        BannerMapper bannerMapper;

    @Test
    public void contextLoads() {
        Map<String, Object> login = adminService.login("admin", "admin", "asdw", session);
        for (String s : login.keySet()) {
            System.out.println(KeyStroke.getKeyStroke(s));
        }
    }

    @Test
    public void album() {
       /* List<Album> byPage = albumMapper.findByPage(0, 3);
        for (Album album : byPage) {
            System.out.println(album);
        }*/
        /*Integer getcount = albumMapper.getcount();
        System.out.println(getcount);*/

        /*Album album = new Album();
        album.setId("1");
        album.setCounts(2);
        album.setPublish_date(new Date());
        album.setImg("1");
        album.setScore(3);
        album.setContent("1");
        album.setAuthor("sda");
        album.setTitle("sss");
        albumMapper.save(album);*/

        /*String[] list={"1"};
        albumMapper.del(list);*/
        Album album = new Album();
        album.setId("1");
        album.setCounts(333);
        albumMapper.upd(album);
    }

    @Test
    public void ss() {
        List<Chapter> byPage = chapterMapper.findByPage(0, 1, "1cbb352d-52ac-4785-81a3-2744889b68c9");
        for (Chapter chapter : byPage) {
            System.out.println(chapter);
        }
    }

    @Test
    public void aa() {
        //String [] list={"0e886c5d-8183-4e64-a6f1-eaba31221f3a"};
        //chapterMapper.delete(list);
        Chapter chapter = new Chapter();
        chapter.setId("1");
        chapter.setFilepath("asdasd");
        chapterMapper.update(chapter);
    }

    @Test
    public void user() {
      /*  User user = new User();
        user.setId("3");
        user.setUsername("fanrui");
        user.setPassword("123456");
        user.setCity("山西");
        user.setFaname("释迦");
        user.setHead_img("sss");
        user.setSex("sssd");
        user.setSign("qwe");
        user.setStatus("激活");
        user.setName("樊睿");
        user.setNames("摩尼");
        //userMapper.insert(user);
        userMapper.update(user);*/
        /*String[] list={"3"};
        userMapper.delete(list);*/
        Integer count = userMapper.getCount();
        System.out.println(count);
    }

    @Test
    public void article() {
       /* Article article = new Article();
        article.setId("2");
        article.setContent("222");
        article.setTitle("22");
        article.setAuthor("sadas");
        article.setStatus("2222222");
        article.setCreateDate(new Date());*/
        //articleMapper.insert(article);
        //articleMapper.update(article);
        /*Integer count = articleMapper.getCount();
        System.out.println(count);*/
        String [] list={"2"};
        articleMapper.delete(list);

    }

    @Test
    public void s() {
        List<Banner> all = bannerMapper.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        String[]str = {"id","标题","状态","描述","创建时间","图片"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }

        //通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        cellStyle.setDataFormat(format);


        for (int i = 0; i < all.size(); i++) {
            Banner banner = all.get(i);
            HSSFRow row1 = sheet.createRow(i);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getStatus());
            row1.createCell(3).setCellValue(banner.getDes());
            HSSFCell cell = row1.createCell(4);

            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreate_date());

            row1.createCell(5).setCellValue(banner.getImg());
        }
        try {
            workbook.write(new File("D:\\CloudMusic\\"+new Date().getTime()+".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void usser() {
        List<Article> list = articleMapper.selectAll();
        for (Article article : list) {
            System.out.println(article);
        }

    }

    @Test
    public void sss() {
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-488783aef3ac412aa56aeb68c163816c");
                goEasy.publish("cmfz", "Hello, GoEasy!");
    }
}

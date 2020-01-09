package com.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.util.ClearCache;
import com.baizhi.util.SelectCache;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class BannerServicelmpl implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public void insert(Banner banner) {
        banner.setCreate_date(new Date());
        bannerMapper.insert(banner);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.update(banner);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> findAll(HttpServletResponse response) throws IOException {
        List<Banner> all = bannerMapper.findAll();
        for (Banner banner : all) {
            banner.setImg("D:/newdisan/cmfz/cmfz/src/main/webapp/img/" + banner.getImg());

        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图", "轮播图"),
                Banner.class, all);

       /* try {
            workbook.write(new FileOutputStream(new File("D:/banner.xls")));
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        String encode = URLEncoder.encode("轮播图信息.xls", "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + encode);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);

        return null;
    }

    @Override
    public void delete(String[] id) {
        bannerMapper.delete(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @SelectCache
    @ClearCache
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        //总条数
        Integer count = bannerMapper.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;

        List<Banner> byPage = bannerMapper.findByPage(start, rows);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", byPage);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = bannerMapper.count();
        return count;
    }
}

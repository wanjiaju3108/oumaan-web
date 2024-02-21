package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.WebCfgBannerMapper;
import com.oumaan.data.po.WebCfgBannerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wjj
 * @Date: 2024/1/5
 */
@Repository
public class WebCfgBannerDao {
    @Autowired
    private WebCfgBannerMapper webCfgBannerMapper;

    public List<WebCfgBannerEntity> listAll() {
        return webCfgBannerMapper.selectList(new LambdaQueryWrapper<WebCfgBannerEntity>()
                .eq(WebCfgBannerEntity::getDel, false));
    }

    public Integer addOne(Integer imgId) {
        WebCfgBannerEntity entity = new WebCfgBannerEntity();
        entity.setImgId(imgId);
        webCfgBannerMapper.insert(entity);
        return entity.getId();
    }

    public WebCfgBannerEntity getById(Integer id) {
        return webCfgBannerMapper.selectById(id);
    }

    public void deleteById(Integer id) {
        webCfgBannerMapper.deleteById(id);
    }
}

package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.WebCfgMapper;
import com.oumaan.data.po.WebCfgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: wjj
 * @Date: 2024/1/5
 */
@Repository
public class WebCfgDao {
    @Autowired
    private WebCfgMapper webCfgMapper;

    public WebCfgEntity get() {
        return webCfgMapper.selectOne(new LambdaQueryWrapper<WebCfgEntity>()
                .eq(WebCfgEntity::getDel, false));
    }

    public void updateTitle(Integer imgId) {
        WebCfgEntity entity = new WebCfgEntity();
        entity.setTitleImg(imgId);
        webCfgMapper.update(entity, new LambdaQueryWrapper<WebCfgEntity>()
                .eq(WebCfgEntity::getId, 1));
    }
}

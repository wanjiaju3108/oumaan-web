package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.PageConfMapper;
import com.oumaan.data.po.PageConfEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Repository
public class PageConfDao {

    @Autowired
    private PageConfMapper pageConfMapper;

    public Integer addOne(String name) {
        PageConfEntity entity = new PageConfEntity();
        entity.setName(name);
        return pageConfMapper.insert(entity);
    }

    public PageConfEntity getById(Integer id) {
        return pageConfMapper.selectById(id);
    }

    public List<PageConfEntity> listAll() {
        return pageConfMapper.selectList(new LambdaQueryWrapper<PageConfEntity>()
                .eq(PageConfEntity::getDel, false));
    }

    public void update(Integer id, String name, Integer imgId) {
        PageConfEntity entity = new PageConfEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setImgId(imgId);
        pageConfMapper.updateById(entity);
    }

}
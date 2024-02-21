package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.SubPageConfMapper;
import com.oumaan.data.po.SubPageConfEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Repository
public class SubPageConfDao {

    @Autowired
    private SubPageConfMapper subPageConfMapper;

    public Integer addOne(Integer pageId, String name) {
        SubPageConfEntity entity = new SubPageConfEntity();
        entity.setPageId(pageId);
        entity.setName(name);
        return subPageConfMapper.insert(entity);
    }

    public List<SubPageConfEntity> list(List<Integer> pageIds) {
        return subPageConfMapper.selectList(new LambdaQueryWrapper<SubPageConfEntity>()
                .in(SubPageConfEntity::getPageId, pageIds)
                .eq(SubPageConfEntity::getDel, false));
    }

    public List<SubPageConfEntity> listAll() {
        return subPageConfMapper.selectList(new LambdaQueryWrapper<SubPageConfEntity>()
                .eq(SubPageConfEntity::getDel, false));
    }

    public void update(Integer id, String name, String content) {
        SubPageConfEntity entity = new SubPageConfEntity();
        entity.setId(id);
        entity.setContent(content);
        entity.setName(name);
        subPageConfMapper.updateById(entity);
    }

    public SubPageConfEntity get(Integer id) {
        return subPageConfMapper.selectOne(new LambdaQueryWrapper<SubPageConfEntity>()
                .in(SubPageConfEntity::getId, id)
                .eq(SubPageConfEntity::getDel, false));

    }
}
package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.PictureInfoMapper;
import com.oumaan.data.po.PictureInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@Repository
public class PictureInfoDao {

    @Autowired
    private PictureInfoMapper pictureInfoMapper;

    public List<PictureInfoEntity> listByIds(List<Integer> ids) {
        return pictureInfoMapper.selectList(new LambdaQueryWrapper<PictureInfoEntity>()
                .in(PictureInfoEntity::getId, ids)
                .eq(PictureInfoEntity::getDel, false));
    }

    public Integer addOne(String fileName) {
        PictureInfoEntity entity = new PictureInfoEntity();
        entity.setFilename(fileName);
        pictureInfoMapper.insert(entity);
        return entity.getId();
    }

    public void deleteById(Integer id) {
        pictureInfoMapper.deleteById(id);
    }

    public void deleteByFilename(String filename) {
        pictureInfoMapper.delete(new LambdaQueryWrapper<PictureInfoEntity>()
                .eq(PictureInfoEntity::getFilename, filename));
    }
}

package com.oumaan.service.background;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.oumaan.data.dao.*;
import com.oumaan.data.po.*;
import com.oumaan.vo.BannerVO;
import com.oumaan.vo.PageVO;
import com.oumaan.vo.SubPageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Slf4j
@Service
public class WebConfService {
    @Autowired
    private PageConfDao pageConfDao;

    @Autowired
    private SubPageConfDao subPageConfDao;

    @Autowired
    private PictureInfoDao pictureInfoDao;

    @Autowired
    private WebCfgDao webCfgDao;

    @Autowired
    private WebCfgBannerDao webCfgBannerDao;

    @Value("${html.resource}")
    private String resourcePath;


    @Value("${html.upload-path}")
    private String uploadPath;

    private Set<String> whiteList = Sets.newHashSet("contact.png"
            , "login-box-bg.svg"
            , "thumbnail1.jpg"
            , "thumbnail2.jpg"
            , "thumbnail3.jpg"
            , "thumbnail4.jpg"
            , "product.jpg");

    public Boolean addPage(String name) {
        return pageConfDao.addOne(name) > 0;
    }

    public PageVO getPage(Integer id) {
        PageConfEntity pageEntity = pageConfDao.getById(id);
        List<SubPageConfEntity> subPageEntityList = subPageConfDao.list(Lists.newArrayList(id));
        PictureInfoEntity pictureEntity = pictureInfoDao.listByIds(Lists.newArrayList(pageEntity.getImgId())).stream().findAny().orElse(null);
        PageVO pageVO = new PageVO();
        pageVO.setId(pageEntity.getId());
        pageVO.setName(pageEntity.getName());
        Optional.ofNullable(pictureEntity).ifPresent(entity -> pageVO.setImgUrl(resourcePath + entity.getFilename()));
        pageVO.setSubPageList(Collections.emptyList());
        if (CollectionUtils.isNotEmpty(subPageEntityList)) {
            pageVO.setSubPageList(subPageEntityList.stream().map(subPage -> {
                SubPageVO subPageVO = new SubPageVO();
                BeanUtils.copyProperties(subPage, subPageVO);
                return subPageVO;
            }).collect(Collectors.toList()));
        }
        return pageVO;
    }

    public void updatePage(Integer id, String name, Integer imgId) {
        pageConfDao.update(id, name, imgId);
    }

    public List<PageVO> listPages() {
        List<PageConfEntity> pageEntityList = pageConfDao.listAll();
        List<Integer> pageIdList = Lists.newArrayList();
        List<Integer> pictureIdList = Lists.newArrayList();
        for (PageConfEntity entity : pageEntityList) {
            pageIdList.add(entity.getId());
            pictureIdList.add(entity.getImgId());
        }
        if (CollectionUtils.isEmpty(pageIdList)) {
            return Collections.emptyList();
        }
        List<SubPageConfEntity> subPageEntityList = subPageConfDao.list(pageIdList);
        Map<Integer, List<SubPageConfEntity>> subPageEntityMap = subPageEntityList.stream().collect(Collectors.groupingBy(SubPageConfEntity::getPageId));
        List<PictureInfoEntity> pictureEntityList = pictureInfoDao.listByIds(pictureIdList);
        Map<Integer, String> pictureMap = pictureEntityList.stream().collect(Collectors.toMap(PictureInfoEntity::getId, PictureInfoEntity::getFilename));
        List<PageVO> voList = Lists.newArrayList();
        for (PageConfEntity entity : pageEntityList) {
            PageVO pageVO = new PageVO();
            pageVO.setName(entity.getName());
            pageVO.setId(entity.getId());
            String imgUrl = pictureMap.get(entity.getImgId());
            if (StringUtils.isNoneBlank(imgUrl)) {
                pageVO.setImgUrl(resourcePath + imgUrl);
            }
            List<SubPageConfEntity> subPageEntities = subPageEntityMap.get(entity.getId());
            pageVO.setSubPageList(Collections.emptyList());
            if (CollectionUtils.isNotEmpty(subPageEntities)) {
                pageVO.setSubPageList(subPageEntities.stream().map(subPage -> {
                    SubPageVO subPageVO = new SubPageVO();
                    BeanUtils.copyProperties(subPage, subPageVO);
                    return subPageVO;
                }).collect(Collectors.toList()));
            }
            voList.add(pageVO);
        }
        return voList;
    }

    public List<BannerVO> listBanners() {
        List<WebCfgBannerEntity> webCfgBannerEntityList = webCfgBannerDao.listAll();
        List<Integer> imgIdList = webCfgBannerEntityList.stream().map(WebCfgBannerEntity::getImgId).collect(Collectors.toList());
        List<PictureInfoEntity> pictureInfoEntityList = pictureInfoDao.listByIds(imgIdList);
        Map<Integer, String> pictureMap = pictureInfoEntityList.stream().collect(Collectors.toMap(PictureInfoEntity::getId, PictureInfoEntity::getFilename));
        return webCfgBannerEntityList.stream().map(entity -> {
            BannerVO vo = new BannerVO();
            vo.setId(entity.getId());
            vo.setImgUrl(resourcePath + pictureMap.get(entity.getImgId()));
            return vo;
        }).collect(Collectors.toList());
    }

    public void deleteBanner(Integer id) {
        webCfgBannerDao.deleteById(id);
        pictureInfoDao.deleteById(id);
    }

    public String getTitle() {
        Integer imgId = webCfgDao.get().getTitleImg();
        return resourcePath + pictureInfoDao.listByIds(Collections.singletonList(imgId)).stream().findAny().orElse(new PictureInfoEntity()).getFilename();
    }

    public Boolean addSubPage(Integer pageId, String name) {
        return subPageConfDao.addOne(pageId, name) > 0;
    }

    public void updateSubPage(Integer id, String name, String content) {
        subPageConfDao.update(id, name, content);
    }

    public SubPageVO getSubPage(Integer id) {
        SubPageConfEntity entity = subPageConfDao.get(id);
        SubPageVO vo = new SubPageVO();
        BeanUtils.copyProperties(entity, vo);
        PageConfEntity pageConfEntity = pageConfDao.getById(vo.getPageId());
        PictureInfoEntity pictureInfoEntity = pictureInfoDao.listByIds(Collections.singletonList(pageConfEntity.getImgId())).stream().findAny().orElse(new PictureInfoEntity());
        vo.setImgUrl(resourcePath + pictureInfoEntity.getFilename());
        return vo;
    }

    public Boolean cleanPicture() {
        List<PageConfEntity> pageList = pageConfDao.listAll();
        List<SubPageConfEntity> subPageList = subPageConfDao.listAll();
        List<Integer> imgIdList = pageList.stream().map(PageConfEntity::getImgId).collect(Collectors.toList());
        List<WebCfgBannerEntity> webCfgBannerList = webCfgBannerDao.listAll();
        imgIdList.addAll(webCfgBannerList.stream().map(WebCfgBannerEntity::getImgId).collect(Collectors.toList()));
        WebCfgEntity webCfg = webCfgDao.get();
        imgIdList.add(webCfg.getTitleImg());
        List<PictureInfoEntity> pictureInfoList = pictureInfoDao.listByIds(imgIdList);
        Set<String> filenameSet = pictureInfoList.stream().map(PictureInfoEntity::getFilename).collect(Collectors.toSet());
        for (SubPageConfEntity subPage : subPageList) {
            Document document = Jsoup.parse(subPage.getContent());
            String url = document.select("img").attr("src");
            String filename = url.substring(url.lastIndexOf("/") + 1);
            filenameSet.add(filename);
        }
        File directory = new File(uploadPath);
        if (!directory.exists() || !directory.isDirectory()) {
            return false;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && !filenameSet.contains(file.getName()) && !whiteList.contains(file.getName())) {
                log.info(file.getName());
                pictureInfoDao.deleteByFilename(file.getName());
                file.delete();
            }
        }
        return true;
    }
}

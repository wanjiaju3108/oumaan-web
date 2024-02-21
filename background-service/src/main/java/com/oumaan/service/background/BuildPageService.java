package com.oumaan.service.background;

import com.google.common.collect.Lists;
import com.oumaan.data.dao.PageConfDao;
import com.oumaan.data.dao.PictureInfoDao;
import com.oumaan.data.dao.SubPageConfDao;
import com.oumaan.data.dao.WebCfgBannerDao;
import com.oumaan.data.po.PageConfEntity;
import com.oumaan.data.po.PictureInfoEntity;
import com.oumaan.data.po.SubPageConfEntity;
import com.oumaan.data.po.WebCfgBannerEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @Author: wjj
 * @Date: 2024/1/7
 */
@Slf4j
@Service
public class BuildPageService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WebCfgBannerDao webCfgBannerDao;

    @Autowired
    private PictureInfoDao pictureInfoDao;

    @Autowired
    private PageConfDao pageConfDao;

    @Autowired
    private SubPageConfDao subPageConfDao;

    @Value("${html.resource}")
    private String resourcePath;

    @Value("${html.path}")
    private String htmlPath;

    public void buildIndex() {
        String filePath = htmlPath + "index.html";
        List<WebCfgBannerEntity> webCfgBannerList = webCfgBannerDao.listAll();
        List<Integer> imgIdList = webCfgBannerList.stream().map(WebCfgBannerEntity::getImgId).collect(Collectors.toList());

        List<PageConfEntity> pageList = pageConfDao.listAll();
        List<Integer> pageIdList = Lists.newArrayList();
        for (PageConfEntity entity : pageList) {
            pageIdList.add(entity.getId());
            imgIdList.add(entity.getImgId());
        }
        List<SubPageConfEntity> subPageEntityList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pageIdList)) {
            subPageEntityList = subPageConfDao.list(pageIdList);
        }
        Map<Integer, List<SubPageConfEntity>> subPageMap = subPageEntityList.stream().collect(Collectors.groupingBy(SubPageConfEntity::getPageId));

        List<PictureInfoEntity> pictureInfoEntityList = pictureInfoDao.listByIds(imgIdList);
        Map<Integer, String> pictureMap = pictureInfoEntityList.stream().collect(Collectors.toMap(PictureInfoEntity::getId, PictureInfoEntity::getFilename));
        try (InputStream inputStream = applicationContext.getResource("classpath:/index.model").getInputStream()
             ; BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("${bannerLis}")) {
                    line = line.replace("${bannerLis}", this.getBannerLis(webCfgBannerList));
                } else if (line.contains("${bannerImgs}")) {
                    line = line.replace("${bannerImgs}", this.getBannerImgs(webCfgBannerList, pictureMap));
                } else if (line.contains("${pageLis}")) {
                    line = line.replace("${pageLis}", this.getPageLis(pageList, subPageMap));
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            log.info("读入模板错误:", e);
        }
    }

    public void buildPage() {
        String filePath = htmlPath + "page.html";
        List<PageConfEntity> pageList = pageConfDao.listAll();
        List<Integer> pageIdList = Lists.newArrayList();
        for (PageConfEntity entity : pageList) {
            pageIdList.add(entity.getId());
        }
        List<SubPageConfEntity> subPageList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(pageIdList)) {
            subPageList = subPageConfDao.list(pageIdList);
        }
        Map<Integer, List<SubPageConfEntity>> subPageMap = subPageList.stream().collect(Collectors.groupingBy(SubPageConfEntity::getPageId));

        try (InputStream inputStream = applicationContext.getResource("classpath:/page.model").getInputStream()
             ; BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("${pageLis}")) {
                    line = line.replace("${pageLis}", this.getPageLis(pageList, subPageMap));
                }
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            log.info("读入模板错误:", e);
        }
    }

    private String getBannerLis(List<WebCfgBannerEntity> webCfgBannerEntityList) {
        boolean first = true;
        String text = "";
        for (int i = 0; i < webCfgBannerEntityList.size(); i++) {
            text += "                    <li data-target=\"#banner\" data-slide-to=\"" + i + "\"" + (first ? " class=\"active\"" : "") + "></li>\n";
            first = false;
        }
        return text;
    }

    private String getBannerImgs(List<WebCfgBannerEntity> webCfgBannerEntityList, Map<Integer, String> pictureMap) {
        boolean first = true;
        String text = "";
        for (WebCfgBannerEntity entity : webCfgBannerEntityList) {
            text += "                    <div class=\"item" + (first ? " active" : "") + "\">\n";
            text += "                        <img class=\"img-responsive center-block img-banner\" draggable=\"false\" src=\"" + resourcePath + pictureMap.get(entity.getImgId()) + "\"/>\n" +
                    "                    </div>\n";
            first = false;
        }
        return text;
    }

    private String getPageLis(List<PageConfEntity> pageList, Map<Integer, List<SubPageConfEntity>> subPageMap) {
        String text = "";
        for (PageConfEntity page : pageList) {
            List<SubPageConfEntity> subPageList = subPageMap.getOrDefault(page.getId(), Collections.emptyList());
            text += "                <li class=\"dropdown\">\n" +
                    "                    <a href=\"overview.html\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\"\n" +
                    "                        aria-expanded=\"false\">" + page.getName() + "</a>\n" +
                    "                    <ul class=\"dropdown-menu\" style=\"background-color: rgba(255,255,255,0.75);\">\n";
            for (SubPageConfEntity subPage : subPageList) {
                text += "                        <li id=\"subPage" + subPage.getId() + "\"><a href=\"/page/" + subPage.getId() + "\">" + subPage.getName() + "</a></li>\n";
            }
            text += "                    </ul>\n" +
                    "                </li>\n";
        }
        return text;
    }
}

package com.oumaan.service.background;

import com.oumaan.data.dao.PageConfDao;
import com.oumaan.data.dao.PictureInfoDao;
import com.oumaan.data.dao.WebCfgBannerDao;
import com.oumaan.data.dao.WebCfgDao;
import com.oumaan.util.FileUtil;
import com.oumaan.vo.LayUIPictureVO;
import com.oumaan.vo.PictureVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: wjj
 * @Date: 2023/12/26
 * 后台配置请求
 */
@Slf4j
@Service
public class UploadService {

    @Value("${html.upload-path}")
    private String uploadPath;

    @Value("${html.resource}")
    private String resourcePath;

    @Autowired
    private PictureInfoDao pictureInfoDao;

    @Autowired
    private WebCfgDao webCfgDao;

    @Autowired
    private WebCfgBannerDao webCfgBannerDao;

    @Autowired
    private PageConfDao pageConfDao;

    public PictureVO uploadPicture(MultipartFile picture) {
        String filename = String.format("%d.%s", System.currentTimeMillis(), FileUtil.getFileExtName(picture.getOriginalFilename()));
        if (this.saveFile(filename, picture)) {
            PictureVO vo = new PictureVO();
            vo.setId(pictureInfoDao.addOne(filename));
            vo.setUrl(resourcePath + filename);
            return vo;
        }
        return null;
    }

    public String uploadTitlePicture(MultipartFile picture) {
        String filename = String.format("%d.%s", System.currentTimeMillis(), FileUtil.getFileExtName(picture.getOriginalFilename()));
        if (this.saveFile(filename, picture)) {
            Integer imgId = pictureInfoDao.addOne(filename);
            webCfgDao.updateTitle(imgId);
            return resourcePath + filename;
        }
        return null;
    }

    public void uploadBannerPicture(MultipartFile picture) {
        String filename = String.format("%d.%s", System.currentTimeMillis(), FileUtil.getFileExtName(picture.getOriginalFilename()));
        if (this.saveFile(filename, picture)) {
            Integer imgId = pictureInfoDao.addOne(filename);
            webCfgBannerDao.addOne(imgId);
        }
    }

    public LayUIPictureVO uploadContentPicture(MultipartFile picture) {
        String filename = String.format("%d.%s", System.currentTimeMillis(), FileUtil.getFileExtName(picture.getOriginalFilename()));
        if (this.saveFile(filename, picture)) {
            pictureInfoDao.addOne(filename);
            LayUIPictureVO.LayUIData layUIData = new LayUIPictureVO.LayUIData();
            layUIData.setSrc(resourcePath + filename);
            LayUIPictureVO layUIPictureVO = new LayUIPictureVO();
            layUIPictureVO.setCode(0);
            layUIPictureVO.setData(layUIData);
            return layUIPictureVO;
        }
        return null;
    }

    private Boolean saveFile(String filename, MultipartFile uploadFile) {
        File file = new File(uploadPath + filename);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
        }
        try {
            uploadFile.transferTo(file);
            return true;
        } catch (IOException e) {
            log.error("上传失败:", e);
            return false;
        }
    }
}

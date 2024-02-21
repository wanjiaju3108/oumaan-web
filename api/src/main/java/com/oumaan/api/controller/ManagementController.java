package com.oumaan.api.controller;

import com.oumaan.api.common.ResponseVO;
import com.oumaan.api.request.background.AddPageRequest;
import com.oumaan.api.request.background.AddSubPageRequest;
import com.oumaan.api.request.background.UpdatePageRequest;
import com.oumaan.api.request.background.UpdateSubPageRequest;
import com.oumaan.service.background.BuildPageService;
import com.oumaan.service.background.UploadService;
import com.oumaan.service.background.WebConfService;
import com.oumaan.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: wjj
 * @Date: 2023/12/26
 * 后台管理
 */
@Slf4j
@RestController
@RequestMapping("management")
public class ManagementController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private WebConfService webConfService;

    @Autowired
    private BuildPageService buildPageService;

    @PostMapping("upload/picture")
    public ResponseVO<PictureVO> uploadPage(@RequestParam("picture") MultipartFile picture) {
        return ResponseVO.success(uploadService.uploadPicture(picture));
    }

    @PostMapping("upload/picture/title")
    public ResponseVO<String> uploadTitlePicture(@RequestParam("picture") MultipartFile picture) {
        String url = uploadService.uploadTitlePicture(picture);
        CompletableFuture.runAsync(buildPageService::buildIndex);
        CompletableFuture.runAsync(buildPageService::buildPage);
        return ResponseVO.success(uploadService.uploadTitlePicture(picture));
    }

    @PostMapping("upload/picture/banner")
    public ResponseVO<Void> uploadBannerPicture(@RequestParam("picture") MultipartFile picture) {
        uploadService.uploadBannerPicture(picture);
        CompletableFuture.runAsync(buildPageService::buildIndex);
        return ResponseVO.success(null);
    }

    @PostMapping("upload/picture/content")
    public LayUIPictureVO uploadContentPicture(@RequestParam("file") MultipartFile picture) {
        return uploadService.uploadContentPicture(picture);
    }

    @DeleteMapping("banner/{id}")
    public ResponseVO<Void> deleteBannerById(@PathVariable("id") Integer id) {
        webConfService.deleteBanner(id);
        CompletableFuture.runAsync(buildPageService::buildIndex);
        return ResponseVO.success(null);
    }

    @GetMapping("title")
    public ResponseVO<String> getTitle() {
        return ResponseVO.success(webConfService.getTitle());
    }

    @PostMapping("page")
    public ResponseVO<Boolean> addPage(@RequestBody AddPageRequest request) {
        Boolean result = webConfService.addPage(request.getName());
        CompletableFuture.runAsync(buildPageService::buildIndex);
        CompletableFuture.runAsync(buildPageService::buildPage);
        return ResponseVO.success(result);
    }

    @GetMapping("page")
    public ResponseVO<PageVO> getPage(@RequestParam("id") Integer id) {
        return ResponseVO.success(webConfService.getPage(id));
    }

    @PutMapping("page")
    public ResponseVO<Void> putPage(@RequestBody UpdatePageRequest request) {
        webConfService.updatePage(request.getId(), request.getName(), request.getImgId());
        CompletableFuture.runAsync(buildPageService::buildIndex);
        CompletableFuture.runAsync(buildPageService::buildPage);
        return ResponseVO.success(null);
    }

    @PostMapping("subPage")
    public ResponseVO<Boolean> addSubPage(@RequestBody AddSubPageRequest request) {
        Boolean result = webConfService.addSubPage(request.getPageId(), request.getName());
        CompletableFuture.runAsync(buildPageService::buildIndex);
        CompletableFuture.runAsync(buildPageService::buildPage);
        return ResponseVO.success(result);
    }

    @PutMapping("subPage")
    public ResponseVO<Void> putSubPage(@RequestBody UpdateSubPageRequest request) {
        webConfService.updateSubPage(request.getId(), request.getName(), request.getContent());
        CompletableFuture.runAsync(buildPageService::buildIndex);
        CompletableFuture.runAsync(buildPageService::buildPage);
        return ResponseVO.success(null);
    }

    @GetMapping("allBanners")
    public ResponseVO<List<BannerVO>> getAllBanners() {
        return ResponseVO.success(webConfService.listBanners());
    }

    @GetMapping("subPage/{id}")
    public ResponseVO<SubPageVO> getSubPage(@PathVariable("id") Integer id) {
        return ResponseVO.success(webConfService.getSubPage(id));
    }

    @GetMapping("clearPicture")
    public ResponseVO<Boolean> clearPicture(){
        return ResponseVO.success(webConfService.cleanPicture());
    }

}

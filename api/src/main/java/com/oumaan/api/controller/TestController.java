package com.oumaan.api.controller;

import com.oumaan.service.background.BuildPageService;
import com.oumaan.service.background.WebConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wjj
 * @Date: 2024/1/7
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private BuildPageService buildPageService;

    @Autowired
    private WebConfService webConfService;

    @GetMapping
    public void test() {
//        buildPageService.buildIndex();
//        buildPageService.buildPage();
        webConfService.cleanPicture();
    }
}

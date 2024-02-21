package com.oumaan.api.controller;

import com.oumaan.api.common.ResponseVO;
import com.oumaan.service.background.WebConfService;
import com.oumaan.vo.SubPageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wjj
 * @Date: 2024/1/8
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("main")
public class MainController {

    @Autowired
    private WebConfService webConfService;

    @GetMapping("subPage/{id}")
    public ResponseVO<SubPageVO> getSubPage(@PathVariable("id") Integer id) {
        return ResponseVO.success(webConfService.getSubPage(id));
    }
}

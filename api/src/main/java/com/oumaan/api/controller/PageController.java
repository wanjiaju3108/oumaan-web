package com.oumaan.api.controller;

import com.oumaan.service.background.WebConfService;
import com.oumaan.vo.BannerVO;
import com.oumaan.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: wjj
 * @Date: 2023/12/26
 * 页面请求
 */
@Controller
public class PageController {

    @Autowired
    private WebConfService webConfService;

    @Value("${html.resource}")
    private String resourcePath;

    @ModelAttribute
    public void loadResource(Model model) {
        /*静态资源加载*/
        model.addAttribute("layuiCss", "/layui/css/layui.css");
        model.addAttribute("customCss", "/layui/css/custom.css");
        model.addAttribute("layuiJs", "/layui/layui.js");
        model.addAttribute("jqueryJs", "/layui/jquery-3.7.1.js");
        model.addAttribute("commonJs", "/layui/common.js");
        model.addAttribute("cssJs", "/layui/css.js");
        model.addAttribute("functionJs", "/layui/function.js");
    }

    @GetMapping
    public String getDefault(Model model) {
        /*列表加载*/
        List<PageVO> pageVOList = webConfService.listPages();
        model.addAttribute("pageList", pageVOList);
        /*首页图片*/
        model.addAttribute("imgUrl", resourcePath + "login-box-bg.svg");
        return "index";
    }
}

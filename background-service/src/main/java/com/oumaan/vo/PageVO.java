package com.oumaan.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Data
public class PageVO {
    private Integer id;
    private List<SubPageVO> subPageList;
    private String imgUrl;
    private String name;
}

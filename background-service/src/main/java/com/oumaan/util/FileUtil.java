package com.oumaan.util;


import org.apache.commons.lang3.StringUtils;

/**
 * @Author: wjj
 * @Date: 2023/12/26
 */
public class FileUtil {
    public static String getFileExtName(String filename) {
        if (StringUtils.isNotBlank(filename)) {
            return filename.substring(filename.lastIndexOf(".") + 1);
        }
        return "";
    }
}

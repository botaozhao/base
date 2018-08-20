package com.kviuff.common.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtil {
    /**
     * 获取pageInfo实体
     */
    public static PageInfo pageInstance(List list) {
        return new PageInfo(list);
    }


}

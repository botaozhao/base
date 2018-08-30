package com.kviuff.common.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageUtils {
    /**
     * 获取pageInfo实体
     */
    public static PageInfo pageInstance(List list) {
        return new PageInfo(list);
    }


}

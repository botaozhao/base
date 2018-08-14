package com.kviuff.common.page;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页bean
 * 分页查询的结果
 * @author kanglan
 * @date 2018/08/02
 */
@Data
public class PageBean<T> implements Serializable {
    /**
     * 当前页码
     */
    private Integer pageNo = 1;

    /**
     * 每页显示的总行数
     */
    private Integer pageSize = 20;

    /**
     * 总条数
     */
    private long total;

    /**
     * 分页查询的结果
     */
    private List<T> result;

    /**
     * 构造pageBean实体
     */
    public PageBean() {
        super();
    }

    /**
     * 构造pageBean实体
     *
     * @param pageNo
     * @param pageSize
     * @param total
     */
    public PageBean(Integer pageNo, Integer pageSize, Integer total) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * 构造pageBean实体
     *
     * @param pageNo
     * @param pageSize
     * @param total
     * @param result
     */
    public PageBean(Integer pageNo, Integer pageSize, long total, List<T> result) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.result = result;
    }

    /**
     * 获取pageBean实体
     *
     * @param pageNo
     * @param pageSize
     * @param total
     * @param result
     * @return
     */
    public static PageBean getInstance(Integer pageNo, Integer pageSize, long total, List result) {
        return new PageBean(pageNo, pageSize, total, result);
    }


}

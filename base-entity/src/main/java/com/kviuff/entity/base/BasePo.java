package com.kviuff.entity.base;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础字段
 */
@Data
public class BasePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 当前页码
     */
    @Transient
    private Integer pageNo;


    /**
     * 每页显示的条数
     */
    @Transient
    private Integer pageSize;

    public Integer getPageNo() {
        return this.pageNo == null ? 1 : this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize == null ? 20 : this.pageSize;
    }

}

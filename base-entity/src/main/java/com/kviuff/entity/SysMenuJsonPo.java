package com.kviuff.entity;

import lombok.Data;

import java.util.List;

/**
 * 菜单实体JSON
 * @author kanglan
 * @date 2018/07/20
 */
@Data
public class SysMenuJsonPo{

    /**
     * 菜单编码
     */
    private String id;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 链接
     */
    private String href;

    /**
     * 是否展开
     */
    private Boolean spread;

    /**
     * 是否选中
     */
    private String checked;

    /**
     *
     */
    private List<SysMenuJsonPo> children;

}

package com.kviuff.entity;

import com.kviuff.entity.base.BasePo;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 菜单实体
 * @author kanglan
 * @date 2018/07/20
 */
@Data
@Table(name = "sys_menu")
public class SysMenuPo extends BasePo {

    /**
     * 菜单编码
     */
    @Id
    private String menuCode;

    /**
     * 父级编号
     */
    private String parentCode;

    /**
     * 所有父级编号
     */
    private String parentCodes;

    /**
     * 本级排序号（升序）
     */
    private Integer treeSort;

    /**
     * 是否最末级
     */
    private Integer treeLeaf;

    /**
     * 层次级别
     */
    private Integer treeLevel;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型（1菜单 2权限 3开发）
     */
    private String menuType;

    /**
     * 链接
     */
    private String menuHref;

    /**
     * 图标
     */
    private String menuIcon;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单权重
     */
    private String weight;

    /**
     * 是否显示（1显示 0隐藏）
     */
    private String isShow;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remarks;

    /**
     *
     */
    @Transient
    private List<SysMenuPo> childMenuList;

}

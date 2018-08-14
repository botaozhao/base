package com.kviuff.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * 角色与菜单关联实体
 * @author kanglan
 * @date 2018/08/02
 */
@Data
@Table(name = "sys_role_menu")
public class SysRoleMenuPo {

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 菜单编码
     */
    private String menuCode;
}

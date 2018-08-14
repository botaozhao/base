package com.kviuff.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * 用户与角色关联实体
 * @author kanglan
 * @date 2018/08/02
 */
@Data
@Table(name = "sys_user_role")
public class SysUserRolePo {

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 角色编码
     */
    private String roleCode;
}

package com.kviuff.entity;

import com.kviuff.entity.base.BasePo;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统角色
 * @author kanglan
 * @date 2018/08/02
 */
@Table(name = "sys_role")
@Data
public class SysRolePo extends BasePo {

    /**
     * 主键
     */
    @Id
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer roleSort;

    /**
     * 是否系统内置  1是 0否
     */
    private String isSys;

    /**
     * 备注
     */
    private String remarks;

    @Transient
    private String layChecked;

    public String getLayChecked() {
        return this.layChecked;
    }
}

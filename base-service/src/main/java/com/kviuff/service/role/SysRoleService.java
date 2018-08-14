package com.kviuff.service.role;

import com.github.pagehelper.PageInfo;
import com.kviuff.entity.SysRolePo;

/**
 * 角色接口
 *
 * @author kanglan
 * @date 2018/08/09
 */
public interface SysRoleService {


    /**
     * 保存角色
     * @param sysRolePo
     */
    void insertRole(SysRolePo sysRolePo);

    /**
     * 删除角色
     * @param roleCode 角色编码
     */
    void deleteRole(String roleCode);

    /**
     * 修改角色
     * @param sysRolePo
     */
    void updateRole(SysRolePo sysRolePo);

    /**
     * 查询角色信息
     * @param roleCoce
     * @return
     */
    SysRolePo selectRole(String roleCoce);

    /**
     * 分页查询
     * @param sysRolePo
     * @return
     */
    PageInfo<SysRolePo> findPageList(SysRolePo sysRolePo);
}

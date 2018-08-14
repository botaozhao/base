package com.kviuff.service.role;


import com.kviuff.entity.SysRoleMenuPo;

import java.util.List;

/**
 * 角色权限配置接口
 *
 * @author kanglan
 * @date 2018/08/13
 */
public interface SysRoleMenuService {

    /**
     * 批量插入
     * @param sysRoleMenuPoList
     */
    void insertBatch(List<SysRoleMenuPo> sysRoleMenuPoList);

    /**
     * 根据角色编码删除角色权限配置
     * @param sysRoleMenuPo
     */
    void deleteByExample(SysRoleMenuPo sysRoleMenuPo);

    /**
     * 根据条件查询列表
     * @param sysRoleMenuPo
     * @return
     */
    List<SysRoleMenuPo> selectByExample(SysRoleMenuPo sysRoleMenuPo);
}

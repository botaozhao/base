package com.kviuff.service.user;

import com.kviuff.entity.SysUserRolePo;

import java.util.List;

/**
 * 用户角色接口
 * @author kanglan
 * @Date   2018/08/13
 */
public interface SysUserRoleService {

    /**
     * 批量插入
     * @param sysUserRolePoList
     */
    void insertBatch(List<SysUserRolePo> sysUserRolePoList);

    /**
     * 根据用户编码删除用户角色配置
     * @param sysUserRolePo
     */
    void deleteByExample(SysUserRolePo sysUserRolePo);

    /**
     * 根据用户编码查询用户下的权限
     * @param sysUserRolePo
     * @return
     */
    List<SysUserRolePo> selectByExample(SysUserRolePo sysUserRolePo);
}

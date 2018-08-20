package com.kviuff.service.user;


import com.github.pagehelper.PageInfo;
import com.kviuff.entity.SysUserPo;

/**
 * 用户接口
 * @author kanglan
 * @Date   2018/06/01
 */
public interface SysUserService {

    /**
     * 保存用户信息
     * @param sysUserPo
     */
    void insertUser(SysUserPo sysUserPo);

    /**
     * 删除用户信息
     * @param userCode
     */
    void deleteUser(String userCode);

    /**
     * 更新用户信息
     * @param sysUserPo
     */
    void updateUser(SysUserPo sysUserPo);

    /**
     * 查询用户信息
     * @param userCode
     * @return
     */
    SysUserPo selectUser(String userCode);

    /**
     * 分页查询
     * @param sysUserPo
     * @return
     */
    PageInfo<SysUserPo> selectPageList(SysUserPo sysUserPo);

    /**
     * 根据条件获取用户单条信息
     * @param sysUserPo
     * @return
     */
    SysUserPo selectOneByExample(SysUserPo sysUserPo);
}

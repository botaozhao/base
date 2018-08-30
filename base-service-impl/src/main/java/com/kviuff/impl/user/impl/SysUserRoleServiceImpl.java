package com.kviuff.impl.user.impl;

import com.kviuff.entity.SysUserRolePo;
import com.kviuff.mapper.user.SysUserRoleMapper;
import com.kviuff.service.user.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 用户角色实现
 * @author kanglan
 * @Date   2018/08/13
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 批量插入
     * @param sysUserRolePoList
     */
    @Override
    public void insertBatch(List<SysUserRolePo> sysUserRolePoList) {
        sysUserRoleMapper.insertList(sysUserRolePoList);
    }

    /**
     * 根据用户编码删除用户角色配置
     * @param sysUserRolePo
     */
    @Override
    public void deleteByExample(SysUserRolePo sysUserRolePo) {
        // 拼接删除条件
        Example example = new Example(SysUserRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userCode", sysUserRolePo.getUserCode());
        sysUserRoleMapper.deleteByExample(example);
    }

    /**
     * 根据用户编码查询用户下的权限
     * @param sysUserRolePo
     * @return
     */
    @Override
    public List<SysUserRolePo> selectByExample(SysUserRolePo sysUserRolePo) {
        Example example = new Example(SysUserRolePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userCode", sysUserRolePo.getUserCode());
        List<SysUserRolePo> sysUserRolePoList = sysUserRoleMapper.selectByExample(example);
        return sysUserRolePoList;
    }
}

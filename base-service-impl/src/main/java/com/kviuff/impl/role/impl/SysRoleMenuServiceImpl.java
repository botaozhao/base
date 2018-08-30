package com.kviuff.impl.role.impl;

import com.kviuff.entity.SysRoleMenuPo;
import com.kviuff.mapper.role.SysRoleMenuMapper;
import com.kviuff.service.role.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 角色权限配置实现
 *
 * @author kanglan
 * @date 2018/08/13
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 批量插入
     * @param sysRoleMenuPoList
     */
    @Override
    public void insertBatch(List<SysRoleMenuPo> sysRoleMenuPoList) {
        sysRoleMenuMapper.insertList(sysRoleMenuPoList);
    }

    /**
     * 根据角色编码删除角色权限配置
     * @param sysRoleMenuPo
     */
    @Override
    public void deleteByExample(SysRoleMenuPo sysRoleMenuPo) {

        // 拼接删除条件
        Example example = new Example(SysRoleMenuPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", sysRoleMenuPo.getRoleCode());
        sysRoleMenuMapper.deleteByExample(example);
    }

    /**
     * 根据条件查询列表
     * @param sysRoleMenuPo
     * @return
     */
    @Override
    public List<SysRoleMenuPo> selectByExample(SysRoleMenuPo sysRoleMenuPo) {
        // 拼接删除条件
        Example example = new Example(SysRoleMenuPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", sysRoleMenuPo.getRoleCode());
        List<SysRoleMenuPo> sysRoleMenuPoList = sysRoleMenuMapper.selectByExample(example);
        return sysRoleMenuPoList;
    }
}

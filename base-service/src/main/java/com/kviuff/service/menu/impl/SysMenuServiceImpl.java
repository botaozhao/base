package com.kviuff.service.menu.impl;

import com.kviuff.common.IdGen;
import com.kviuff.entity.SysMenuPo;
import com.kviuff.mapper.menu.SysMenuMapper;
import com.kviuff.service.menu.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单实现类
 * @author kanglan
 * @date 2018/07/23
 */
@Service
@Slf4j
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 保存菜单
     * @param sysMenuPo
     */
    @Override
    public void insertMenu(SysMenuPo sysMenuPo) {
        sysMenuPo.setMenuCode(IdGen.uuid());
        sysMenuMapper.insert(sysMenuPo);
    }

    /**
     * 删除菜单
     * @param menuCode
     */
    @Override
    public void deleteMenu(String menuCode) {
        sysMenuMapper.deleteByPrimaryKey(menuCode);
    }

    /**
     * 更新菜单
     * @param sysMenuPo
     */
    @Override
    public void updateMenuByMenuCode(SysMenuPo sysMenuPo) {
        sysMenuMapper.updateByPrimaryKey(sysMenuPo);
    }

    /**
     * 根据code获取菜单信息
     * @param menuCode
     * @return
     */
    @Override
    public SysMenuPo selectMenuByCode(String menuCode) {
        return sysMenuMapper.selectByPrimaryKey(menuCode);
    }

    /**
     * 获取所有菜单数据
     * @return
     */
    @Override
    public List<SysMenuPo> selectMenuList() {
        return sysMenuMapper.selectAll();
    }

    /**
     * 根据条件查询菜单列表
     * @param sysMenuPo
     * @return
     */
    @Override
    public List<SysMenuPo> selectMenuListByParams(SysMenuPo sysMenuPo) {
        return sysMenuMapper.getMenuListByParams(sysMenuPo);
    }

    /**
     * 根据角色编码获取角色关联的菜单并去重
     * @param roleCodes
     * @return
     */
    @Override
    public List<SysMenuPo> selectMenuListByRoleCodes(List<String> roleCodes) {
        return sysMenuMapper.selectMenuListByRoleCodes(roleCodes);
    }
}

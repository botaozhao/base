package com.kviuff.mapper.menu;

import com.kviuff.common.mapper.BaseMapper;
import com.kviuff.entity.SysMenuPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单mapper
 * @author kanglan
 * @date 2018/07/23
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenuPo> {

    /**
     * 根据条件查询菜单列表
     * @param sysMenuPo
     * @return
     */
    List<SysMenuPo> getMenuListByParams(SysMenuPo sysMenuPo);

    /**
     * 根据角色编码获取角色关联的菜单并去重
     * @param roleCodes
     * @return
     */
    List<SysMenuPo> selectMenuListByRoleCodes (List<String> roleCodes);

}

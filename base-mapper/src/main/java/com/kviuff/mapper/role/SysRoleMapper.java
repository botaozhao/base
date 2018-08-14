package com.kviuff.mapper.role;

import com.kviuff.common.mapper.BaseMapper;
import com.kviuff.entity.SysRolePo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统角色mapper
 * @author kanglan
 * @date 2018/08/02
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRolePo> {

    /**
     * 多条件查询角色列表
     * @param sysRolePo
     * @return
     */
    List<SysRolePo> selectSysRoleByCondition(SysRolePo sysRolePo);

}
package com.kviuff.mapper.user;

import com.kviuff.common.mapper.BaseMapper;
import com.kviuff.entity.SysUserPo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户mapper
 * @author kanglan
 * @date 2018/08/02
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUserPo> {

    /**
     * 多条件查询用户列表
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectSysUserByCondition(SysUserPo sysUserPo);
}
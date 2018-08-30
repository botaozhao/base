package com.kviuff.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kviuff.common.IdGen;
import com.kviuff.common.util.EncryptionUtils;
import com.kviuff.common.util.PageUtils;
import com.kviuff.entity.SysUserPo;
import com.kviuff.mapper.user.SysUserMapper;
import com.kviuff.service.user.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 用户实现
 * @author kanglan
 * @Date   2018/06/01
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 保存用户信息
     * @param sysUserPo
     */
    @Override
    public void insertUser(SysUserPo sysUserPo) {
        sysUserPo.setUserCode(IdGen.uuid());
        sysUserPo.setPassword(EncryptionUtils.entryptPassword(sysUserPo.getPassword()));
        sysUserMapper.insert(sysUserPo);
    }

    /**
     * 删除用户信息
     * @param userCode
     */
    @Override
    public void deleteUser(String userCode) {
        sysUserMapper.deleteByPrimaryKey(userCode);
    }

    /**
     * 更新用户信息
     * @param sysUserPo
     */
    @Override
    public void updateUser(SysUserPo sysUserPo) {
        sysUserMapper.updateByPrimaryKeySelective(sysUserPo);
    }

    /**
     * 查询用户信息
     * @param userCode
     * @return
     */
    @Override
    public SysUserPo selectUser(String userCode) {
        return sysUserMapper.selectByPrimaryKey(userCode);
    }

    /**
     * 分页查询
     * @param sysUserPo
     * @return
     */
    @Override
    public PageInfo<SysUserPo> selectPageList(SysUserPo sysUserPo) {
        PageHelper.startPage(sysUserPo.getPageNo(), sysUserPo.getPageSize());
        List<SysUserPo> sysUserPoList = sysUserMapper.selectSysUserByCondition(sysUserPo);
        return PageUtils.pageInstance(sysUserPoList);
    }

    /**
     * 根据条件获取用户单条信息
     * @param sysUserPo
     * @return
     */
    @Override
    public SysUserPo selectOneByExample(SysUserPo sysUserPo) {

        Example example = new Example(SysUserPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginCode", sysUserPo.getLoginCode());
        SysUserPo sysUserPo1 = sysUserMapper.selectOneByExample(example);
        return sysUserPo1;
    }
}

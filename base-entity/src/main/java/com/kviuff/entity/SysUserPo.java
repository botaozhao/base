package com.kviuff.entity;

import com.kviuff.entity.base.BasePo;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 * @author kanglan
 * @date 2018/08/02
 */
@Data
@Table(name = "sys_user")
public class SysUserPo extends BasePo {
    /**
     * 用户ID
     */
    @Id
    private String userCode;

    /**
     * 登录账号
     */
    private String loginCode;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态（0正常 1删除 2停用）
     */
    private String status;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginDate;

    /**
     * 用户类型 1:超级管理员  2:普通管理员
     */
    private String userType;

    /**
     * 备注
     */
    private String remarks;

}
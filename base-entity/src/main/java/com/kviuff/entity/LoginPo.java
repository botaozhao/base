package com.kviuff.entity;

import lombok.Data;

/**
 * 登录的实体
 *
 * @author kanglan
 * @date 2018/08/15
 */
@Data
public class LoginPo {

    /**
     * 登录账号
     */
    private String loginCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String vercode;

    /**
     * 记住登录状态
     */
    private String isRemeber;
}

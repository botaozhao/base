package com.kviuff.shiro;

import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义用户和密码（包含验证码）令牌类
 *
 * @author kanglan
 * @date 2018/08/18
 */
@Data
public class ShiroToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    private String captcha;
    private boolean mobileLogin;
    private String loginType;
    private String appid;

    public ShiroToken() {
        super();
    }

    public ShiroToken(String username, char[] password, String host) {
        super(username, password, false, host);
        this.captcha = "";
        this.mobileLogin = false;
    }

    public ShiroToken(String username, char[] password,
                      boolean rememberMe, String host, String captcha, boolean mobileLogin) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.mobileLogin = mobileLogin;
    }

    public ShiroToken(String username) {
        super.setUsername(username);

    }

    /**
     * 第三方登录
     *
     * @param username  昵称
     * @param appid     第三方id
     * @param loginType 登录类型（qq\weixin\shina）
     */
    public ShiroToken(String username, String appid, String loginType) {
        super(username, appid, false, "");
        this.captcha = captcha;
        this.mobileLogin = true;
        this.loginType = loginType;
        this.appid = appid;
    }
}

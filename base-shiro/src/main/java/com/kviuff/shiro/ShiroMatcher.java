package com.kviuff.shiro;

import com.kviuff.common.utils.EncryptionUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 自定义shiro验证时使用的加密算法
 *
 * @author kanglan
 * @date 2018/08/15
 */
public class ShiroMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        ShiroToken token = (ShiroToken) authcToken;
        Object accountCredentials = getCredentials(info); // 加密后的密码
        boolean isMobileLogin = token.isMobileLogin();    // 是否手机登录
        if(isMobileLogin){ // 微信和第三方登录自动登录
            return true;
        }
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return EncryptionUtil.validatePassword(String.valueOf(token.getPassword()), accountCredentials + "");
    }
}

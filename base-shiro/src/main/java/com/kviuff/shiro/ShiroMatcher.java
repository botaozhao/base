package com.kviuff.shiro;

import com.kviuff.common.util.EncryptionUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义shiro验证
 *
 * @author kanglan
 * @date 2018/08/15
 */
public class ShiroMatcher extends HashedCredentialsMatcher {


    // 集群中可能会导致出现验证多过5次的现象，因为AtomicInteger只能保证单节点并发
    // 解决方案，利用ehcache、redis（记录错误次数）和mysql数据库（锁定）的方式处理：密码输错次数限制； 或两者结合使用
    private Cache<String, AtomicInteger> passwordRetryCache;

    public ShiroMatcher(CacheManager cacheManager) {
        // 读取ehcache中配置的登录限制锁定时间
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    /**
     * 在回调方法doCredentialsMatch(AuthenticationToken token,AuthenticationInfo info)中进行身份认证的密码匹配，
     * 引入了redis用于保存用户登录次数，如果登录失败retryCount变量则会一直累加，如果登录成功，那么这个count就会从缓存中移除，
     * 实现了如果登录次数超出指定的值就锁定。
     * @param authcToken
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {

        // 获取登录用户名
        String username = (String) authcToken.getPrincipal();
        // 从redis中获取密码输错次数
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (null == retryCount) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }

        // 超过5次 锁定
        if (retryCount.incrementAndGet() > 5) {
            throw new ExcessiveAttemptsException("username:"+username+" tried to login more than 5 times in period");
        }

        ShiroToken token = (ShiroToken) authcToken;
        Object accountCredentials = getCredentials(info); // 加密后的密码
        boolean isMobileLogin = token.isMobileLogin();    // 是否手机登录
        if(isMobileLogin){ // 微信和第三方登录自动登录
            return true;
        }
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        boolean matches = EncryptionUtils.validatePassword(String.valueOf(token.getPassword()), accountCredentials + "");
        if (matches) {
            // 清楚redis中的count次数缓存
            passwordRetryCache.remove(username);
        } else {
            retryCount = new AtomicInteger(retryCount.incrementAndGet());
            passwordRetryCache.put(username, retryCount);
        }
        return matches;
    }
}

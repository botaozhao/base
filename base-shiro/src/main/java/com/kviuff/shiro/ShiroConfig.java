package com.kviuff.shiro;

import com.kviuff.common.util.EncryptionUtils;
import com.kviuff.shiro.session.CustomSessionManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro权限配置
 *
 * @author kanglan
 * @date 2018/08/15
 */
@Configuration
public class ShiroConfig {


    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;


    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     * 此处已实现自定义
     */
    @Bean
    public ShiroMatcher customCredentialsMatcher() {
        ShiroMatcher shiroMatcher = new ShiroMatcher(cacheManager());
        // 指定加密方式
        shiroMatcher.setHashAlgorithmName(EncryptionUtils.SHA1);
        // 加密次数
        shiroMatcher.setHashIterations(EncryptionUtils.HASH_INTERATIONS);
        shiroMatcher.setStoredCredentialsHexEncoded(true);
        return shiroMatcher;
    }

    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     *
     * @return
     */
    @Bean
    public SimpleCookie simpleCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(3600);
        return simpleCookie;
    }

    /**
     * cookie管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(simpleCookie());
        return cookieRememberMeManager;
    }


    /**
     * 注入验证方式
     *
     * @return
     */
    @Bean
    public ShiroRealm myShiroRealm(ShiroMatcher matcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(matcher);
        shiroRealm.setAuthorizationCachingEnabled(false);
        return shiroRealm;
    }

    /**
     * 定义安全管理器securityManager,注入自定义的realm
     *
     * @param shiroRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm);
        // 管理记住登录状态
        manager.setRememberMeManager(cookieRememberMeManager());
        // 自定义session管理 使用redis
        manager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        manager.setCacheManager(cacheManager());
        return manager;
    }


    /**
     * 自定义sessionManager
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // 配置缓存过期时间
        redisManager.setExpire(1800);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 定义shiroFilter过滤器并注入securityManager
     *
     * @param manager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置securityManager
        bean.setSecurityManager(manager);
        // 设置登录页面
        // 可以写路由也可以写jsp页面的访问路径
        bean.setLoginUrl("/rest/login/unAuth");
        // 设置登录成功跳转的页面
        bean.setSuccessUrl("/");
        // 设置未授权跳转的页面
        // bean.setUnauthorizedUrl("/pages/unauthorized.jsp");
        //定义过滤器，map必须用：LinkedHashMap，因为它必须保证有序
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/rest/login/doLogin", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/rest/**", "anon");
        // filterChainDefinitionMap.put("/loginUser", "anon");
        // filterChainDefinitionMap.put("/admin", "roles[admin]");
        // filterChainDefinitionMap.put("/edit", "perms[delete]");
        // filterChainDefinitionMap.put("/druid/**", "anon");

        //需要登录访问的资源 , 一般将/**放在最下边
        filterChainDefinitionMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }


}

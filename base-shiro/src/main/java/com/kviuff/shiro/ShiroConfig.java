package com.kviuff.shiro;

import com.kviuff.common.utils.EncryptionUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
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
        ShiroMatcher shiroMatcher = new ShiroMatcher();
        // 指定加密方式
        shiroMatcher.setHashAlgorithmName(EncryptionUtil.SHA1);
        // 加密次数
        shiroMatcher.setHashIterations(EncryptionUtil.HASH_INTERATIONS);
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
        //这个参数是cookie的名称，对应前端的checkbox的name = isRemeber
        SimpleCookie simpleCookie = new SimpleCookie("isRemeber");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
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
        cookieRememberMeManager.setCipherKey(Base64.decodeBase64("2AvVhdsgUs0FSA3SDFAdag=="));
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
        manager.setRememberMeManager(cookieRememberMeManager());
        return manager;
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
        bean.setLoginUrl("/login");
        // 设置登录成功跳转的页面
        bean.setSuccessUrl("/");
        // 设置未授权跳转的页面
        // bean.setUnauthorizedUrl("/pages/unauthorized.jsp");
        //定义过滤器
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/index", "authc");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/login/defaultKaptcha", "anon");
        filterChainDefinitionMap.put("/rest/login/doLogin", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
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

package com.kviuff.shiro;

import com.kviuff.common.utils.EncodesUtil;
import com.kviuff.entity.SysMenuPo;
import com.kviuff.entity.SysUserPo;
import com.kviuff.entity.SysUserRolePo;
import com.kviuff.service.menu.SysMenuService;
import com.kviuff.service.user.SysUserRoleService;
import com.kviuff.service.user.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现AuthorizingRealm接口用户用户认证
 *
 * @author kanglan
 * @date 2018/08/14
 */
public class ShiroRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 获取登录用户名
        String name= (String) principalCollection.getPrimaryPrincipal();
        // 获取登录用户信息
        SysUserPo sysUserPo = getUserInfo(name);
        String userType = sysUserPo.getUserType();
        String userCode = sysUserPo.getUserCode();
        List<SysMenuPo> sysMenuPoList = new ArrayList<>();
        List<SysUserRolePo> sysUserRolePoList = new ArrayList<>();
        List<String> roleCodes = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        // 获取用户的菜单权限和功能权限
        if ("1".equals(userType)) { // 超级管理员，所有菜单的权限
            sysMenuPoList = sysMenuService.selectMenuList();
        } else { // 非超级管理员，根据角色获取菜单权限
            // 获取用户所拥有的角色列表
            SysUserRolePo sysUserRolePo = new SysUserRolePo();
            sysUserRolePo.setUserCode(userCode);
            sysUserRolePoList = sysUserRoleService.selectByExample(sysUserRolePo);
            for (SysUserRolePo sysUserRolePo1 : sysUserRolePoList) {
                roleCodes.add(sysUserRolePo1.getRoleCode());
            }
            sysMenuPoList = sysMenuService.selectMenuListByRoleCodes(roleCodes);
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取所拥有的功能权限
        for (SysMenuPo sysMenuPo : sysMenuPoList) {
            String permission = sysMenuPo.getPermission();
            if (StringUtils.isNotEmpty(permission)) {
                permissions.add(permission);
            }
        }

        simpleAuthorizationInfo.addRoles(roleCodes);
        simpleAuthorizationInfo.addStringPermissions(permissions);

        return null;
    }

    /**
     * 用户认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // ShiroToken token = (ShiroToken) authenticationToken;

        // 加这一步的目的是在Post请求的时候会先进认证，然后再到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }

        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        // 获取登录用户信息
        SysUserPo sysUserPo = getUserInfo(name);

        if (null == sysUserPo) {
            logger.info("用户" + name + "不存在");
            throw new UnknownAccountException();
        } else {
            byte[] salt = EncodesUtil.decodeHex(sysUserPo.getPassword().substring(0,16));
            return new SimpleAuthenticationInfo(new Principal(sysUserPo),
                    sysUserPo.getPassword(), ByteSource.Util.bytes(salt), getName());
        }

    }

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    private SysUserPo getUserInfo (String userName) {
        // 查询用户名称
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setLoginCode(userName);
        // 获取登录用户信息
        SysUserPo sysUserPo1 = sysUserService.selectOneByExample(sysUserPo);
        return sysUserPo1;
    }

    /**
     * 授权用户信息
     */
    public static class Principal implements Serializable {

        private static final long serialVersionUID = 1L;

        private String userCode; // 用户编码
        private String loginCode; // 登录账号
        private String userName; // 用户姓名

        public Principal(SysUserPo sysUserPo) {
            this.userCode = sysUserPo.getUserCode();
            this.loginCode = sysUserPo.getLoginCode();
            this.userName = sysUserPo.getUserName();
        }

        public String getUserCode() {
            return userCode;
        }

        public String getUserName() {
            return userName;
        }

        public String getLoginCode() {
            return  loginCode;
        }

        @Override
        public String toString() {
            return loginCode;
        }

    }

}

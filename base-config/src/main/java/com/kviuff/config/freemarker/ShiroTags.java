//package com.kviuff.config.freemarker;
//
//import freemarker.template.SimpleHash;
//import org.apache.shiro.web.tags.*;
//import org.springframework.stereotype.Component;
//
///**
// * Shortcut for injecting the tags into Freemarker
// *
// * @author kanglan
// * @date 2018/08/17
// */
//@Component
//public class ShiroTags extends SimpleHash {
//    public ShiroTags() {
//        put("authenticated", new AuthenticatedTag());
//        put("guest", new GuestTag());
//        put("hasAnyRoles", new HasAnyRolesTag());
//        put("hasPermission", new HasPermissionTag());
//        put("hasRole", new HasRoleTag());
//        put("lacksPermission", new LacksPermissionTag());
//        put("lacksRole", new LacksRoleTag());
//        put("notAuthenticated", new NotAuthenticatedTag());
//        put("principal", new PrincipalTag());
//        put("user", new UserTag());
//    }
//}

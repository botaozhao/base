//package com.kviuff.config.freemarker;
//
//import freemarker.template.TemplateModelException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import javax.annotation.PostConstruct;
//
///**
// * 配置freemarker使用shiro标签
// *
// * @author kanglan
// * @date 2018/08/17
// */
//@Configuration
//public class FreeMarkerConfig {
//
//    @Autowired
//    private FreeMarkerConfigurer freeMarkerConfigurer;
//
//    /**
//     * 加载属性
//     * @throws Exception
//     */
//    @PostConstruct
//    public void setSharedVariable() throws TemplateModelException {
//        System.out.println("设置freemarker-shiro标签");
//        System.out.println(new ShiroTags());
//        // 加上这句后，可以在页面上使用shiro标签
//        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
//        // 加上这句后，可以在页面上用${context.contextPath}获取contextPath
//        // freeMarkerViewResolver.setRequestContextAttribute("context");
//
//    }
//}

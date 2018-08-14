package com.kviuff.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 首页控制层
 * @author kanglan
 * @Date   2018/06/01
 */

@Controller
@RequestMapping("")
public class IndexController {

    final static String INDEX_PAGE = "index";

    /**
     * 加载列表页
     * @return
     */
    @RequestMapping("index")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView(INDEX_PAGE);
        return mv;
    }

}

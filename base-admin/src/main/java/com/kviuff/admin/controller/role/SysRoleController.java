package com.kviuff.admin.controller.role;

import com.kviuff.entity.SysRolePo;
import com.kviuff.service.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 角色控制层
 * @author kanglan
 * @date 2018/08/09
 */
@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    final static String LIST_PAGE = "/modules/sys/role/list";
    final static String ADD_PAGE = "/modules/sys/role/add";
    final static String EDIT_PAGE = "/modules/sys/role/edit";
    final static String ROLE_MENU_PAGE = "/modules/sys/rolemenu/rolemenu";

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 加载列表页
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView(LIST_PAGE);
        return mv;
    }

    /**
     * 加载新增页
     * @return
     */
    @RequestMapping("add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView(ADD_PAGE);
        return mv;
    }

    /**
     * 加载编辑页
     * @return
     */
    @RequestMapping("edit/{roleCode}")
    public ModelAndView edit(@PathVariable String roleCode) {
        ModelAndView mv = new ModelAndView(EDIT_PAGE);
        SysRolePo sysRolePo = sysRoleService.selectRole(roleCode);
        mv.addObject("sysRolePo", sysRolePo);
        return mv;
    }

    /**
     * 加载角色权限配置页
     * @return
     */
    @RequestMapping("rolemenu/{roleCode}")
    public ModelAndView rolemenu(@PathVariable String roleCode) {
        ModelAndView mv = new ModelAndView(ROLE_MENU_PAGE);
        mv.addObject("roleCode", roleCode);
        return mv;
    }

}

package com.kviuff.api.role;

import com.github.pagehelper.PageInfo;
import com.kviuff.common.R;
import com.kviuff.entity.SysMenuJsonPo;
import com.kviuff.entity.SysMenuPo;
import com.kviuff.entity.SysRoleMenuPo;
import com.kviuff.entity.SysRolePo;
import com.kviuff.service.menu.SysMenuService;
import com.kviuff.service.role.SysRoleMenuService;
import com.kviuff.service.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 角色接口-restful
 *
 * @author kanglan
 * @date 2018/07/20
 */
@RestController
@RequestMapping("/rest/sys/role")
public class SysRoleRestController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取角色分页数据
     *
     * @return
     */
    @RequestMapping("/list")
    public R findAllMenu(HttpServletRequest request) {
        try {
            // 每页条数
            int pageSize = Integer.parseInt(request.getParameter("limit"));
            // 页码
            String pageNo = request.getParameter("page") == null ? "1" : request.getParameter("page");
            // 登录账号
            String roleCode = request.getParameter("roleCode");
            String roleName = request.getParameter("roleName");
            SysRolePo sysRolePo = new SysRolePo();
            sysRolePo.setRoleCode(roleCode);
            sysRolePo.setRoleName(roleName);
            sysRolePo.setPageNo(Integer.parseInt(pageNo));
            sysRolePo.setPageSize(pageSize);
            PageInfo<SysRolePo> sysRolePoPageInfo = sysRoleService.findPageList(sysRolePo);
            Map<String, Object> map = new HashMap<>();
            map.put("count", sysRolePoPageInfo.getTotal());
            map.put("data", sysRolePoPageInfo.getList());
            return R.ok(map);
        } catch (Exception e) {
            e.getMessage();
            return R.error("获取角色列表出错");
        }
    }

    /**
     * 保存角色
     *
     * @param sysRolePo
     */
    @RequestMapping("/save")
    public R saveMenu(@RequestBody SysRolePo sysRolePo) {
        try {
            sysRolePo.setCreateBy("系统");
            sysRolePo.setCreateDate(new Date());
            sysRoleService.insertRole(sysRolePo);
            return R.ok("保存成功");
        } catch (Exception e) {
            e.getMessage();
            return R.error("新增角色出错");
        }
    }


    /**
     * 修改角色
     *
     * @param sysRolePo
     */
    @RequestMapping("/update")
    public R updateMenu(@RequestBody SysRolePo sysRolePo) {
        try {
            sysRoleService.updateRole(sysRolePo);
            return R.ok("修改成功");
        } catch (Exception e) {
            e.getMessage();
            return R.error("修改角色出错");
        }
    }

    /**
     * 删除角色
     *
     * @param roleCode
     */
    @RequestMapping("/delete/{roleCode}")
    public R deleteMenu(@PathVariable("roleCode") String roleCode) {
        try {
            sysRoleService.deleteRole(roleCode);
            return R.ok("删除成功");
        } catch (Exception e) {
            e.getMessage();
            return R.error("删除角色出错");
        }
    }

    /**
     * 保存角色权限
     *
     * @param sysRoleMenuPo
     */
    @RequestMapping("/saveRoleMenu")
    public R saveRoleMenu(@RequestBody SysRoleMenuPo sysRoleMenuPo) {
        try {

            // 将以,分隔的菜单编码转换成数组
            String[] menuCodes = sysRoleMenuPo.getMenuCode().split(",");
            // 获取角色编码
            String roleCode = sysRoleMenuPo.getRoleCode();
            // 删除该角色编码的所有配置
            SysRoleMenuPo sysRoleMenuPo2 = new SysRoleMenuPo();
            sysRoleMenuPo2.setRoleCode(roleCode);
            sysRoleMenuService.deleteByExample(sysRoleMenuPo2);
            // 处理菜单编码的数组，将数据放入list，批量插入数据库
            List<SysRoleMenuPo> sysRoleMenuPoList = new ArrayList<>();
            for (String menuCode: menuCodes) {
                SysRoleMenuPo sysRoleMenuPo1 = new SysRoleMenuPo();
                sysRoleMenuPo1.setRoleCode(roleCode);
                sysRoleMenuPo1.setMenuCode(menuCode);
                sysRoleMenuPoList.add(sysRoleMenuPo1);
            }
            sysRoleMenuService.insertBatch(sysRoleMenuPoList);
            return R.ok("保存成功");
        } catch (Exception e) {
            e.getMessage();
            return R.error("保存角色权限出错");
        }
    }

    /**
     * 获取所有菜单的json数据
     *
     * @return
     */
    @RequestMapping(value = "/json/{roleCode}", method = RequestMethod.GET)
    public List<SysMenuJsonPo> getMenuJson(@PathVariable("roleCode") String roleCode) {

        SysRoleMenuPo sysRoleMenuPo = new SysRoleMenuPo();
        sysRoleMenuPo.setRoleCode(roleCode);
        List<SysRoleMenuPo> sysRoleMenuPoList = sysRoleMenuService.selectByExample(sysRoleMenuPo);

        List<SysMenuPo> sysMenuPoList = sysMenuService.selectMenuList();
        List<SysMenuJsonPo> sysMenuJsonPoList = new ArrayList<>();
        for (SysMenuPo sysMenuPo : sysMenuPoList) {
            SysMenuJsonPo sysMenuJsonPo = new SysMenuJsonPo();
            String menuCode = sysMenuPo.getMenuCode();
            sysMenuJsonPo.setId(menuCode);
            sysMenuJsonPo.setName(sysMenuPo.getMenuName());
            sysMenuJsonPo.setParentCode(sysMenuPo.getParentCode());
            sysMenuJsonPo.setHref(sysMenuPo.getMenuHref());

            for (SysRoleMenuPo sysRoleMenuPo1 : sysRoleMenuPoList) {
                if (menuCode.equals(sysRoleMenuPo1.getMenuCode())) {
                    sysMenuJsonPo.setChecked("true");
                    break;
                }
            }

            sysMenuJsonPoList.add(sysMenuJsonPo);
        }

        List<SysMenuJsonPo> newList = new ArrayList<SysMenuJsonPo>();
        for (SysMenuJsonPo sysMenuJsonPo : sysMenuJsonPoList) {
            if (sysMenuJsonPo.getParentCode().compareTo("0") == 0) { // 一级菜单
                List<SysMenuJsonPo> childrenList = getChildMenuList(sysMenuJsonPo.getId(), sysMenuJsonPoList);
                sysMenuJsonPo.setChildren(childrenList);
                sysMenuJsonPo.setSpread(true);
                newList.add(sysMenuJsonPo);
            }
        }

        return newList;
    }

    /**
     * 获取一级菜单的子菜单
     *
     * @param parentCode 父id
     * @param menulist   菜单列表
     * @return
     */
    private List<SysMenuJsonPo> getChildMenuList(String parentCode, List<SysMenuJsonPo> menulist) {
        List<SysMenuJsonPo> childList = new ArrayList<SysMenuJsonPo>();
        for (SysMenuJsonPo sysMenuJsonPo : menulist) {
            if (sysMenuJsonPo.getParentCode().compareTo(parentCode) == 0) {
                sysMenuJsonPo.setChildren(this.getChildMenuList(sysMenuJsonPo.getId(), menulist));
                childList.add(sysMenuJsonPo);
            }
        }
        return childList;
    }

}

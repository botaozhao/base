package com.kviuff.api.menu;

import com.kviuff.common.R;
import com.kviuff.entity.SysMenuJsonPo;
import com.kviuff.entity.SysMenuPo;
import com.kviuff.service.menu.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单接口-restful
 *
 * @author kanglan
 * @date 2018/07/20
 */
@RestController
@RequestMapping("/rest/sys/menu")
public class SysMenuRestController {

    /**
     * 注入接口
     */
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 根据编码查询菜单
     *
     * @param menuCode
     * @return
     */
    @RequestMapping("/info/{menuCode}")
    public R findMenuByCode(@PathVariable("menuCode") String menuCode) {
        SysMenuPo sysMenuPo = sysMenuService.selectMenuByCode(menuCode);
        return R.ok().put("menu", sysMenuPo);
    }

    /**
     * 获取所有的菜单
     *
     * @return
     */
    @RequestMapping("/list")
    public R findAllMenu() {
        List<SysMenuPo> sysMenuPoList = sysMenuService.selectMenuList();
        return R.ok().put("data", sysMenuPoList);
    }

    /**
     * 保存菜单
     *
     * @param sysMenuPo
     */
    @RequestMapping("/save")
    public R saveMenu(@RequestBody SysMenuPo sysMenuPo) {
        initMenu(sysMenuPo);
        sysMenuService.insertMenu(sysMenuPo);
        return R.ok("保存成功");
    }

    /**
     * 修改菜单
     *
     * @param sysMenuPo
     */
    @RequestMapping("/update")
    public R updateMenu(@RequestBody SysMenuPo sysMenuPo) {
        initMenu(sysMenuPo);
        sysMenuService.updateMenuByMenuCode(sysMenuPo);
        return R.ok("修改成功");
    }

    /**
     * 删除菜单
     *
     * @param menuCode
     */
    @RequestMapping("/delete/{menuCode}")
    public R deleteMenu(@PathVariable("menuCode") String menuCode) {

        // 判断是否有下级菜单，如果有不允许删除
        SysMenuPo sysMenuPo = new SysMenuPo();
        sysMenuPo.setParentCode(menuCode);
        List<SysMenuPo> sysMenuPoList = sysMenuService.selectMenuListByParams(sysMenuPo);
        if (sysMenuPoList.size() > 0) {
            return R.error("请先删除子菜单");
        }

        sysMenuService.deleteMenu(menuCode);
        return R.ok("删除成功");
    }

    /**
     * 获取所有菜单的json数据
     *
     * @return
     */
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public List<SysMenuJsonPo> getMenuJson() {
        List<SysMenuPo> sysMenuPoList = sysMenuService.selectMenuList();
        List<SysMenuJsonPo> sysMenuJsonPoList = new ArrayList<>();
        for (SysMenuPo sysMenuPo : sysMenuPoList) {
            SysMenuJsonPo sysMenuJsonPo = new SysMenuJsonPo();
            sysMenuJsonPo.setId(sysMenuPo.getMenuCode());
            sysMenuJsonPo.setName(sysMenuPo.getMenuName());
            sysMenuJsonPo.setParentCode(sysMenuPo.getParentCode());
            sysMenuJsonPo.setHref(sysMenuPo.getMenuHref());
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

    /**
     * 初始化菜单信息
     * @param sysMenuPo
     */
    private void initMenu (SysMenuPo sysMenuPo) {
        String parentCode = sysMenuPo.getParentCode();
        if (StringUtils.isEmpty(parentCode) || "0".equals(parentCode)) { // 没有父级
            sysMenuPo.setParentCode("0");
            sysMenuPo.setParentCodes("0,");
            sysMenuPo.setTreeLeaf(0);
            sysMenuPo.setTreeLevel(0);
        } else {  // 有父级
            SysMenuPo sysMenuPo1 = sysMenuService.selectMenuByCode(parentCode);
            String parentCodes = sysMenuPo1.getParentCodes();
            sysMenuPo.setParentCodes(parentCodes + parentCode + ",");
            Integer treeLevel = parentCodes.split(",").length;
            sysMenuPo.setTreeLevel(treeLevel);
            Integer treeLeaf = treeLevel == 3 ? 1 : 0;
            sysMenuPo.setTreeLeaf(treeLeaf);
        }
    }
}

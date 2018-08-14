package com.kviuff.admin.controller.user;

import com.github.pagehelper.PageInfo;
import com.kviuff.common.R;
import com.kviuff.entity.SysRolePo;
import com.kviuff.entity.SysUserRolePo;
import com.kviuff.service.role.SysRoleService;
import com.kviuff.service.user.SysUserRoleService;
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
@RequestMapping("/rest/sys/user/role")
public class SysUserRoleRestController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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
            // 用户编码
            String userCode = request.getParameter("userCode");
            SysRolePo sysRolePo = new SysRolePo();
            sysRolePo.setPageNo(Integer.parseInt(pageNo));
            sysRolePo.setPageSize(pageSize);
            PageInfo<SysRolePo> sysRolePoPageInfo = sysRoleService.findPageList(sysRolePo);
            List<SysRolePo> sysRolePoList = sysRolePoPageInfo.getList();

            SysUserRolePo sysUserRolePo = new SysUserRolePo();
            sysUserRolePo.setUserCode(userCode);
            List<SysUserRolePo> sysUserRolePoList = sysUserRoleService.selectByExample(sysUserRolePo);

            for (SysRolePo sysRolePo1 : sysRolePoList) {
                String roleCode = sysRolePo1.getRoleCode();
                for (SysUserRolePo sysUserRolePo1 : sysUserRolePoList) {
                    String roleCode1 = sysUserRolePo1.getRoleCode();
                    if (roleCode1.equals(roleCode)) {
                        sysRolePo1.setLayChecked("true");
                    }
                }
            }

            Map<String, Object> map = new HashMap<>();
            map.put("count", sysRolePoPageInfo.getTotal());
            map.put("data", sysRolePoList);
            return R.ok(map);
        } catch (Exception e) {
            e.getMessage();
            return R.error("获取角色列表出错");
        }
    }

}

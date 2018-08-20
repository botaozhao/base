package com.kviuff.api.user;


import com.github.pagehelper.PageInfo;
import com.kviuff.common.R;
import com.kviuff.entity.SysUserPo;
import com.kviuff.entity.SysUserRolePo;
import com.kviuff.service.user.SysUserRoleService;
import com.kviuff.service.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户接口-restful
 *
 * @author kanglan
 * @date 2018/08/02
 */
@RestController
@RequestMapping("/rest/sys/user")
public class SysUserRestController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 获取用户分页数据
     *
     * @return
     */
    @RequestMapping("/list")
    public R findAllMenu(HttpServletRequest request) {
        // 每页条数
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        // 页码
        String pageNo = request.getParameter("page") == null ? "1" : request.getParameter("page");
        // 登录账号
        String loginCode = request.getParameter("loginCode");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String status = request.getParameter("status");
        SysUserPo sysUserPo = new SysUserPo();
        sysUserPo.setPageNo(Integer.parseInt(pageNo));
        sysUserPo.setPageSize(pageSize);
        sysUserPo.setLoginCode(loginCode);
        sysUserPo.setUserName(userName);
        sysUserPo.setEmail(email);
        sysUserPo.setMobile(mobile);
        sysUserPo.setStatus(status);
        PageInfo<SysUserPo> sysUserPoPageInfo = sysUserService.selectPageList(sysUserPo);
        Map<String, Object> map = new HashMap<>();
        map.put("count", sysUserPoPageInfo.getTotal());
        map.put("data", sysUserPoPageInfo.getList());
        return R.ok(map);
    }

    /**
     * 保存用户
     *
     * @param sysUserPo
     */
    @RequestMapping("/save")
    public R saveMenu(@RequestBody SysUserPo sysUserPo) {
        sysUserPo.setCreateDate(new Date());
        sysUserPo.setCreateBy("系统");
        sysUserPo.setStatus("0");
        sysUserService.insertUser(sysUserPo);
        return R.ok("保存成功");
    }


    /**
     * 修改菜单
     *
     * @param sysUserPo
     */
    @RequestMapping("/update")
    public R updateMenu(@RequestBody SysUserPo sysUserPo) {
        sysUserService.updateUser(sysUserPo);
        return R.ok("修改成功");
    }

    /**
     * 删除菜单
     *
     * @param userCode
     */
    @RequestMapping("/delete/{userCode}")
    public R deleteMenu(@PathVariable("userCode") String userCode) {
        sysUserService.deleteUser(userCode);
        return R.ok("删除成功");
    }


    /**
     * 保存角色权限
     *
     * @param sysUserRolePo
     */
    @RequestMapping("/saveUserRole")
    public R saveRoleMenu(@RequestBody SysUserRolePo sysUserRolePo) {
        try {

            // 将以,分隔的菜单编码转换成数组
            String[] roleCodes = sysUserRolePo.getRoleCode().split(",");
            // 获取用户编码
            String userCode = sysUserRolePo.getUserCode();
            // 删除该角色编码的所有配置
            SysUserRolePo sysUserRolePo2 = new SysUserRolePo();
            sysUserRolePo2.setUserCode(userCode);
            sysUserRoleService.deleteByExample(sysUserRolePo2);
            // 处理角色编码的数组，将数据放入list，批量插入数据库
            List<SysUserRolePo> sysUserRolePoList = new ArrayList<>();
            for (String roleCode: roleCodes) {
                SysUserRolePo sysUserRolePo1 = new SysUserRolePo();
                sysUserRolePo1.setRoleCode(roleCode);
                sysUserRolePo1.setUserCode(userCode);
                sysUserRolePoList.add(sysUserRolePo1);
            }
            sysUserRoleService.insertBatch(sysUserRolePoList);
            return R.ok("保存成功");
        } catch (Exception e) {
            e.getMessage();
            return R.error("保存角色权限出错");
        }
    }

}

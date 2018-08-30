package com.kviuff.api.login;


import com.kviuff.common.R;
import com.kviuff.common.util.CodeUtils;
import com.kviuff.entity.LoginPo;
import com.kviuff.entity.SysUserPo;
import com.kviuff.redis.RedisService;
import com.kviuff.service.user.SysUserService;
import com.kviuff.shiro.ShiroToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录接口
 *
 * @author kanglan
 * @date 2018/08/15
 */
@RestController
@RequestMapping("/rest/login")
@Slf4j
public class LoginRestController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisService redisService;

    /**
     * 登录操作
     *
     * @param loginPo
     */
    @RequestMapping("/doLogin")
    public R doLogin(@RequestBody LoginPo loginPo) {
        // 验证码
        String vercode = loginPo.getVercode();
        // 登录账号
        String loginCode = loginPo.getLoginCode();
        // 密码
        String password = loginPo.getPassword();
        // 记住密码
        String remeberMe = loginPo.getIsRemeber();
        // 存入redis的键
        String tokenCode = loginPo.getTokenCode();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        // 获取session中的验证码
        String sessionCaptcha = (String) redisService.get(tokenCode);
        sessionCaptcha = CodeUtils.decodeVerifyCodes(sessionCaptcha);
        if (StringUtils.isEmpty(vercode)) {
            return R.error("验证码不能为空");
        } else if (!sessionCaptcha.toLowerCase().equals(vercode.toLowerCase())) {
            return R.error("验证码不正确");
        } else if (StringUtils.isEmpty(loginCode)) {
            return R.error("用户名不能为空");
        } else if (StringUtils.isEmpty(password)) {
            return R.error("密码不能为空");
        }

        boolean isRemeber = "on".equals(remeberMe) ? true : false;

        ShiroToken token = new ShiroToken(loginCode, password.toCharArray(), isRemeber, "", vercode, false);
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            log.info("用户不存在：" + loginCode);
            return R.error("用户不存在");
        } catch (IncorrectCredentialsException e) {
            log.info("用户或密码错误：" + loginCode);
            return R.error("用户或密码错误");
        }catch (Exception e) {
            log.info("登入出错：" + e.getMessage());
            return R.error("登入出错");
        }
        if (subject.isAuthenticated()) {

            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setLoginCode(loginCode);
            sysUserPo = sysUserService.selectOneByExample(sysUserPo);
            session.setAttribute("sysUserPo", sysUserPo);

            Map<String, Object> map = new HashMap<>();
            map.put("token", session.getId());
            map.put("msg", "登入成功");
            redisService.delete(tokenCode);
            return R.ok(map);
        } else {
            token.clear();
            return R.error("登入出错");
        }
    }


    /**
     * 登录操作
     *
     * @param loginPo
     */
    @RequestMapping("/unAuth")
    public R unAuth(@RequestBody LoginPo loginPo) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return R.ok(map);

    }

}

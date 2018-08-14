package com.kviuff.admin.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kviuff.common.CommonConstants;
import com.kviuff.common.utils.RandomValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 登录控制层
 *
 * @author kanglan
 * @Date 2018/08/14
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    final static String PAGE_LOGIN = "/login";

    /**
     * 加载登录页
     *
     * @return
     */
    @RequestMapping("")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView(PAGE_LOGIN);
        return mv;
    }

    /**
     * 生成图片验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            httpServletResponse.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            httpServletResponse.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            httpServletResponse.setHeader("Cache-Control", "no-cache");
            httpServletResponse.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(httpServletRequest, httpServletResponse);//输出验证码图片方法
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


    /**
     * 校对验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    private boolean validateCode(HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse) {
        String rightCode = (String) httpServletRequest.getSession().getAttribute(CommonConstants.VERIFICATION_CODE);
        String tryCode = httpServletRequest.getParameter(CommonConstants.VALIDATE_CODE);
        if (!rightCode.equals(tryCode)) {
            return false;
        } else {
            return true;
        }
    }
}

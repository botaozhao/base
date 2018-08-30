package com.kviuff.api.code;

import com.kviuff.common.CommonConstants;
import com.kviuff.common.IdGen;
import com.kviuff.common.R;
import com.kviuff.common.util.CodeUtils;
import com.kviuff.common.util.ImgResult;
import com.kviuff.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码接口
 *
 * @author kanglan
 * @date 2018/08/15
 */
@RestController
@RequestMapping("/rest/code")
@Slf4j
public class CodeRestController {

    @Autowired
    private RedisService redisService;

    /**
     * 获取验证码
     *
     * @param response
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    @ResponseBody
    public R getCode(HttpServletResponse response) {
        String UUID = IdGen.uuid();
        try {
            ImgResult imgResult = CodeUtils.VerifyCode(4);
            String img = imgResult.getImg();
            String code = imgResult.getCode();
            log.info("the captcha is:" + code);
            redisService.set(UUID, code, 60);
            // 将验证码的key及验证码的图片返回
            Map<String, Object> map = new HashMap<>();
            map.put("tokenCode", UUID);
            map.put(CommonConstants.VERIFICATION_CODE, img);
            return R.ok(map);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("获取验证码出错:" + e.toString());
            return R.error("获取验证码出错");
        }
    }

}

package com.kviuff.common.util;

import com.kviuff.common.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Random;

@Slf4j
public class CodeUtils {

    /**
     * 放到session中的key
     */
    public static final String RANDOMCODEKEY = CommonConstants.VERIFICATION_CODE;
    /**
     * 随机产生只有数字的字符串 private String
     */
    private static String randString = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    /**
     * 图片宽
     */
    private static int width = 95;
    /**
     * 图片高
     */
    private static int height = 25;
    /**
     * 干扰线数量
     */
    private static int lineSize = 40;
    /**
     * 随机产生字符数量
     */
    private static int stringNum = 4;

    private static final Logger logger = LoggerFactory.getLogger(CodeUtils.class);

    private static Random random = new Random();

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 获得字体
     */
    private static Font getFont() {
        return new Font("宋体,楷体,微软雅黑", Font.CENTER_BASELINE, 18);
    }

    /**
     * 获得颜色
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 绘制字符串
     */
    private static void drowString(Graphics g, String randomString, int i) {
        log.info(randomString);
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(randomString, 13 * i, 16);
    }

    /**
     * 绘制干扰线
     */
    private static void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     */
    public static String getRandomString(int num) {
        return String.valueOf(randString.charAt(num));
    }


    /**
     * 生成随机图片
     */
    public static void getRandcodeByte(OutputStream os, String code) {
        int codeLength = code.length();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);//图片大小
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//字体大小
        g.setColor(getRandColor(110, 133));//字体颜色
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机字符
        for (int i = 1; i <= codeLength; i++) {
            drowString(g, code.charAt(i - 1) + "", i);
        }
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", os);
        } catch (Exception e) {
            logger.error("将内存中的图片通过流动形式输出到客户端失败>>>>   ", e);
        }

    }

    /**
     * 使用系统默认字符源生成验证码
     *
     * @param verifySize 验证码长度
     * @return
     */
    public static String generateVerifyCode(int verifySize) {
        return generateVerifyCode(verifySize, randString);
    }

    /**
     * 使用指定源生成验证码
     *
     * @param verifySize 验证码长度
     * @param sources    验证码字符源
     * @return
     */
    public static String generateVerifyCode(int verifySize, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = randString;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(verifySize);
        for (int i = 0; i < verifySize; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }
        return verifyCode.toString();
    }

    /**
     * 获取随机验证码及其加密图片
     *
     * @param size
     * @return
     * @throws IOException
     */
    public static ImgResult VerifyCode(int size) throws IOException {
        ImgResult rs = new ImgResult();
        String code = generateVerifyCode(size).toLowerCase();
        log.info(code);
        rs.setCode(encoder.encodeToString(code.getBytes()));
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        getRandcodeByte(data, code);
        rs.setImg(encoder.encodeToString(data.toByteArray()));
        return rs;
    }

    /**
     * base64解密
     * @param verifyCodes
     * @return
     */
    public static String decodeVerifyCodes(String verifyCodes) {

        String code = null;
        try {
            code = new String(decoder.decode(verifyCodes), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void main(String[] args) throws IOException {
//        ImgResult rs = VerifyCode(4);
//        System.out.println(rs.getImg());
//        System.out.println(rs.getCode());

        System.out.println(new String(decoder.decode("cXg5Ng=="), "UTF-8"));

    }
}

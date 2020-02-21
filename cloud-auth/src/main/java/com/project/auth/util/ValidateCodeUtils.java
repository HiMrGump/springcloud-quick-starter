package com.project.auth.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2020/2/2115:31
 * @Version 1.0
 **/
public class ValidateCodeUtils {

    //生成随机数
    public static String createText(){
        DefaultKaptcha defaultKaptcha = ApplicationContextUtil.getBean(DefaultKaptcha.class);
        return defaultKaptcha.createText();
    }

    //写图片
    public static void writeImage(OutputStream output,String text) throws IOException {
        DefaultKaptcha defaultKaptcha = ApplicationContextUtil.getBean(DefaultKaptcha.class);
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ImageIO.write(bufferedImage, "jpg", output);
        output.flush();
    }
}

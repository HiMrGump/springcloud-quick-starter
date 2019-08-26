package com.project.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContext工具类
 * @ClassName: ApplicationContextUtil
 * @Author: WangQingYun
 * @Date: Created in 2019/4/22 16:45
 * @Version: 1.0
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext ac;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac  = applicationContext;
    }

    /**
     * 根据name获取一个bean
     * @param name bean的name
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name) {
        return (T)ac.getBean(name);
    }

    /**
     * 根据类获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return ac.getBean(clazz);
    }

}

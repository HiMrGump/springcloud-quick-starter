package com.project.util;

import java.util.UUID;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: IDUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/6/6 14:07
 * @Version: 1.0
 */
public class IDUtils {

    public static String generate(){
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
}

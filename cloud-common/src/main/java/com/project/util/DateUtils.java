package com.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DateUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/8/26 17:59
 * @Version: 1.0
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String formatDate(Date d, String parsePattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parsePattern);
        return simpleDateFormat.format(d);
    }

}

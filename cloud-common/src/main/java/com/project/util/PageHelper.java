package com.project.util;

import lombok.Data;

import java.util.List;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: PageHelper
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:34
 * @Version: 1.0
 */
@Data
public class PageHelper<E> {
    //当前页,默认1
    private long currentPage = 1;
    //页大小,默认20
    private long pageSize = 20;
    //数据总数
    private long total;
    //数据
    private List<E> results;
}

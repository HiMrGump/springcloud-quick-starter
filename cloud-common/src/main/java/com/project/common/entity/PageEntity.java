package com.project.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.util.PageHelper;
import lombok.Data;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2019/8/2417:15
 * @Version 1.0
 **/
@Data
public abstract class PageEntity extends BaseEntity{
    //当前页,默认1
    @TableField(exist = false)
    @JsonIgnore
    private long currentPage = 1;

    //页大小,默认20
    @TableField(exist = false)
    @JsonIgnore
    private long pageSize = 20;

    public PageHelper buildPageHelper(){
        PageHelper pageHelper = new PageHelper();
        pageHelper.setCurrentPage(currentPage);
        pageHelper.setPageSize(pageSize);
        return pageHelper;
    }
}

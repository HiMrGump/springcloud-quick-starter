package com.project.common.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.util.PageHelper;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;
import tk.mybatis.mapper.annotation.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: BaseEntity
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:53
 * @Version: 1.0
 */
@Data
public abstract class BaseEntity implements Serializable {
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator="JDBC")
    private String id;

    //创建时间
    @Order(value="DESC")
    private Date createDate;

    //最后修改时间
    @Column(name = "last_modify_date")
    private Date lastModifyDate;

    //最后修改人
    @Column(name = "last_modify_by")
    private String lastModifyBy;

    //删除状态,0-正常,1删除
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //不进行序列化
    @LogicDelete
    private Integer deleteFlag;

    //当前页,默认1
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int currentPage = 1;

    //页大小,默认20
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int pageSize = 20;

    public PageHelper buildPageHelper(){
        PageHelper pageHelper = new PageHelper();
        pageHelper.setCurrentPage(currentPage);
        pageHelper.setPageSize(pageSize);
        return pageHelper;
    }
}

package com.project.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    @TableId(type= IdType.UUID)
    private String id;
    //创建时间
    private Date createDate;
    //最后修改时间
    @JsonIgnore
    private Date lastModifyDate;
    //最后修改人
    @JsonIgnore
    private String lastModifyBy;
    //版本号
    @Version
    @JsonIgnore
    private long version;
    //删除状态,0-正常,1删除
    @TableLogic
    @JsonIgnore
    private int deleteFlag;
}

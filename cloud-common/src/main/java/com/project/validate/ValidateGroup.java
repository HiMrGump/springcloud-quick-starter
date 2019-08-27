package com.project.validate;

/**
 * 该接口描述了vaildate数据校验分组功能定义,可以往下添加
 *
 * @ClassName: ValidateGroup
 * @Author: WangQingYun
 * @Date: Created in 2019/5/5 9:35
 * @Version: 1.0
 */
public interface ValidateGroup {
    /**
     * 该接口描述在插入语境下，必须填写某些字段
     */
    interface Insert{}
    /**
     * 该接口描述在更新语境下，必须填写某些字段
     */
    interface Update{}
}
package com.project.common.db;
/**
 * 数据库操作类型枚举
 *
 * @ClassName: DBOperation
 * @Author WangQingYun
 * @Date Created in 2019/8/29 14:06
 * @Version 1.0
 **/
public enum DBOperation {

    EQ,     //等于
    GT,   //大于
    GE,   //大于等于
    LT,   //小于
    LE,   //小于等于
    LIKE,   //模糊
    IN,     //范围内
    BETWEEN,//两个值之间

}

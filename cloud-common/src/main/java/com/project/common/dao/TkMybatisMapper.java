package com.project.common.dao;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: MyBaseMapper
 * @Author: WangQingYun
 * @Date: Created in 2019/8/29 14:06
 * @Version: 1.0
 */
@RegisterMapper
public interface TkMybatisMapper<T> extends Mapper<T>, MySqlMapper<T> {
}

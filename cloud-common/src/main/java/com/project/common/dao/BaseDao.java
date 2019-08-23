package com.project.common.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.common.entity.BaseEntity;

/**
 * 该接口描述了XXXX操作的功能定义
 *
 * @ClassName: BaseDao
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:31
 * @Version: 1.0
 */
public interface BaseDao<E extends BaseEntity> extends BaseMapper<E> {
}

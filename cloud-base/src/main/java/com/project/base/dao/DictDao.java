package com.project.base.dao;

import com.project.base.entity.DictEntity;
import com.project.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 该类定义了字典操作接口
 *
 * @ClassName: DictDao
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 16:52
 * @Version: 1.0
 */
@Mapper
public interface DictDao extends BaseDao<DictEntity> {
}

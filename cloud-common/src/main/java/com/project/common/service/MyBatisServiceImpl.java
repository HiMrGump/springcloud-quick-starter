package com.project.common.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.dao.BaseDao;
import com.project.common.db.DBHelper;
import com.project.common.db.DBOperation;
import com.project.common.entity.BaseEntity;
import com.project.util.IDUtils;
import com.project.util.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2019/8/2415:48
 * @Version 1.0
 **/
public abstract class MyBatisServiceImpl<E extends BaseEntity> implements BaseService<E>{

    public abstract BaseDao getDao();

    /**
     *  根据主键获取一条数据
     * @param id 主键W
     * @return 数据
     */
    public E get(Serializable id){
        return (E)getDao().selectById(id);
    }

    public List<E> list(DBHelper dbSearchHelper){
        return getDao().selectList(buildWrapper(dbSearchHelper));
    }

    public PageHelper<E> listByPage(PageHelper<E> pageHelper, DBHelper dbSearchHelper){
        if(pageHelper == null){
            pageHelper = new PageHelper<E>();
        }

        Page page = new Page(pageHelper.getCurrentPage(),pageHelper.getPageSize());
        IPage iPage = getDao().selectPage(page, buildWrapper(dbSearchHelper));
        pageHelper.setTotal(iPage.getTotal());
        pageHelper.setResults(iPage.getRecords());
        return pageHelper;
    }

    private Wrapper buildWrapper(DBHelper dbSearchHelper){
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        //LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper();
        List<DBHelper.FiledRelation> operationList = dbSearchHelper.getOperationList();
        for (DBHelper.FiledRelation oper: operationList) {
            DBOperation operation = oper.getOperation();
            switch (operation){
                case EQ:
                    if(!isBreak(oper.getValue()[0])){
                        queryWrapper.eq(oper.getFiledName(),oper.getValue()[0]);
                    }
                     break;
                case GT:
                    queryWrapper.gt(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case GE:
                    queryWrapper.ge(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LT:
                    queryWrapper.lt(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LE:
                    queryWrapper.le(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LIKE:
                    if(!isBreak(oper.getValue()[0])){
                        queryWrapper.like(oper.getFiledName(),oper.getValue()[0]);
                    }
                    break;
                case IN:
                    queryWrapper.in(oper.getFiledName(),oper.getValue());
                    break;
                case BETWEEN:
                    Object[] valueArr = oper.getValue();
                    if(valueArr[0] != null && valueArr[1] != null ){
                        queryWrapper.between(oper.getFiledName(),valueArr[0],valueArr[1]);
                    }else if(valueArr[0] != null){
                        queryWrapper.ge(oper.getFiledName(),valueArr[0]);
                    }else if(valueArr[1] != null){
                        queryWrapper.le(oper.getFiledName(),valueArr[1]);
                    }
                    break;
            }
        }
        return queryWrapper;
    }

    private boolean isBreak(Object val1){
        if(val1 == null){
           return true;
        }
        if(val1.getClass().getName() == "java.lang.String"){
            String valStr1 = (String)val1;
            if(StringUtils.isBlank(valStr1) || "null".equalsIgnoreCase(valStr1) || "undefined".equalsIgnoreCase(valStr1)){
                return true;
            }
        }
        return false;
    }

    @Transactional
    public int delete(Serializable id){
        return getDao().deleteById(id);
    }

    @Transactional
    public int deleteByIds(List<Serializable> idList){
        return getDao().deleteBatchIds(idList);
    }

    @Transactional
    public int update(E entity){
        entity.setLastModifyDate(new Date());
        return getDao().updateById(entity);
    }

    @Transactional
    public int save(E entity){
        entity.setId(IDUtils.generate());
        entity.setCreateDate(new Date());
        return getDao().insert(entity);
    }
}

package com.project.common.service;

import com.github.pagehelper.PageInfo;
import com.project.common.dao.BaseDao;
import com.project.common.entity.BaseEntity;
import com.project.exception.ParameterErrorException;
import com.project.util.IDUtils;
import com.project.util.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Override
    public Optional<E> get(String id){
        return Optional.ofNullable((E)getDao().selectByPrimaryKey(id));
    }

    @Override
    public Optional<E> getOne(E entity){
        return Optional.ofNullable((E)getDao().selectOne(entity));
    }

    @Override
    public List<E> list(E e){
        return getDao().select(e);
    }

    @Override
    public PageHelper<E> listByPage(E e){
        com.github.pagehelper.PageHelper.startPage(e.getCurrentPage(),e.getPageSize());
        PageInfo<E> ePageInfo = new PageInfo<E>(getDao().select(e));
        PageHelper pageHelper = e.buildPageHelper();
        pageHelper.setTotal(ePageInfo.getTotal());
        pageHelper.setResults(ePageInfo.getList());
        return pageHelper;
    }

   /* public List<E> list(DBHelper dbSearchHelper){
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
    }*/

    @Override
    @Transactional
    public int delete(String id){
        if(StringUtils.isBlank(id)){
            throw new ParameterErrorException("主键必填");
        }
        return getDao().deleteByPrimaryKey(id);
    }

    @Transactional
    public int update(E entity){
        if(StringUtils.isBlank(entity.getId())){
            throw new ParameterErrorException("主键必填");
        }
        entity.setLastModifyDate(new Date());
        return getDao().updateByPrimaryKey(entity);
    }

    @Transactional
    public int updateBySelective(E entity){
        if(StringUtils.isBlank(entity.getId())){
            throw new ParameterErrorException("主键必填");
        }
        entity.setLastModifyDate(new Date());
        return getDao().updateByPrimaryKeySelective(entity);
    }

    @Transactional
    public int save(E entity){
        entity.setId(IDUtils.generate());
        entity.setCreateDate(new Date());
        entity.setLastModifyDate(new Date());
        entity.setDeleteFlag(0);
        return getDao().insert(entity);
    }
}

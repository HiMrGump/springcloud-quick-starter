package com.project.common.service;

import com.github.pagehelper.IPage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.project.common.dao.BaseDao;
import com.project.common.db.DBHelper;
import com.project.common.db.DBOperation;
import com.project.common.entity.BaseEntity;
import com.project.exception.ParameterErrorException;
import com.project.util.IDUtils;
import com.project.util.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 这个类实现了XXXX相关功能
 *
 * @Author Gump
 * @Date 2019/8/2415:48
 * @Version 1.0
 **/
public abstract class MyBatisServiceImpl<E extends BaseEntity> implements BaseService<E>{

    public abstract BaseDao getDao();

    private Class<E> clazz;

    public MyBatisServiceImpl() {
        //获取泛型参数class文件
       clazz = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

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
    public PageHelper<E> listByPage(E entity){
        com.github.pagehelper.PageHelper.startPage(entity.getCurrentPage(),entity.getPageSize());
        PageInfo<E> pageInfo = new PageInfo<E>(getDao().select(entity));
        PageHelper pageHelper = entity.buildPageHelper();
        pageHelper.setTotal(pageInfo.getTotal());
        pageHelper.setResults(pageInfo.getList());
        return pageHelper;
    }


    public List<E> list(DBHelper dbSearchHelper){
        Example example = buildExample(dbSearchHelper);
        return getDao().selectByExample(example);
    }

    public PageHelper<E> listByPage(PageHelper<E> pageHelper, DBHelper dbSearchHelper){
        if(pageHelper == null){
            pageHelper = new PageHelper<E>();
        }
        com.github.pagehelper.PageHelper.startPage(pageHelper.getCurrentPage(),pageHelper.getPageSize());
        PageInfo<E> pageInfo = new PageInfo<E>(getDao().selectByExample(buildExample(dbSearchHelper)));
        pageHelper.setTotal(pageInfo.getTotal());
        pageHelper.setResults(pageInfo.getList());
        return pageHelper;
    }

    private Example buildExample(DBHelper dbSearchHelper){
        if(dbSearchHelper.getOperationList().isEmpty()){ //无条件
            return new Example(clazz);
        }
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        List<DBHelper.FiledRelation> operationList = dbSearchHelper.getOperationList();
        for (DBHelper.FiledRelation oper: operationList) {
            DBOperation operation = oper.getOperation();
            switch (operation){
                case EQ:
                     break;
                case GT:
                    criteria.andGreaterThan(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case GE:
                        criteria.andGreaterThanOrEqualTo(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LT:
                        criteria.andLessThan(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LE:
                        criteria.andLessThanOrEqualTo(oper.getFiledName(),oper.getValue()[0]);
                    break;
                case LIKE:
                        criteria.andLike(oper.getFiledName(),"%" + (String)oper.getValue()[0] + "%");
                    break;
                case IN:
                        criteria.andIn(oper.getFiledName(), Arrays.stream(oper.getValue()).collect(Collectors.toList()));
                    break;
                case BETWEEN:
                    Object[] valueArr = oper.getValue();
                    if(valueArr[0] != null && valueArr[1] != null ){
                        criteria.andBetween(oper.getFiledName(),valueArr[0],valueArr[1]);
                    }else if(valueArr[0] != null){
                        criteria.andGreaterThanOrEqualTo(oper.getFiledName(),oper.getValue()[0]);
                    }else if(valueArr[1] != null){
                        criteria.andLessThanOrEqualTo(oper.getFiledName(),oper.getValue()[1]);
                    }
                    break;
            }
        }
        return example;
    }


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

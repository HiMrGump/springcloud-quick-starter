package com.project.user.service;

import com.project.common.dao.BaseDao;
import com.project.common.service.BaseService;
import com.project.common.service.MyBatisServiceImpl;
import com.project.user.dao.RoleDao;
import com.project.user.dao.UserDao;
import com.project.user.entity.UserEntity;
import com.project.user.pojo.UserRoleVO;
import com.project.util.BeanUtils;
import com.project.util.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 该类实现了用户相关操作接口的具体功能
 *
 * @ClassName: UserService
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 11:28
 * @Version: 1.0
 */
@Service
public class UserService extends MyBatisServiceImpl<UserEntity> implements BaseService<UserEntity> {

    @Resource
    RoleDao roleDao;

    @Resource
    UserDao userDao;

    /**
     * 根据账户名获取用户和角色信息
     * @param accountName 账户名
     * @return
     */
    public UserRoleVO getByAccountName(String accountName){
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setAccountName(accountName);
        UserEntity userEntity  = userDao.selectOne(searchUserEntity);
        if(userEntity == null){
            return null;
        }
        UserRoleVO userRoleVO = BeanUtils.copyBean(userEntity, UserRoleVO.class);
        userRoleVO.setRoleList(roleDao.listByUserId(userEntity.getId()));
        return userRoleVO;
    }

    /**
     * 根据手机号获取用户和角色信息
     * @param mobile 手机号
     * @return
     */
    public UserRoleVO getByMobile(String mobile){
        UserEntity searchUserEntity = new UserEntity();
        searchUserEntity.setUserMobile(mobile);
        UserEntity userEntity  = userDao.selectOne(searchUserEntity);
        if(userEntity == null){
            return null;
        }

        UserRoleVO userRoleVO = BeanUtils.copyBean(userEntity, UserRoleVO.class);
        userRoleVO.setRoleList(roleDao.listByUserId(userEntity.getId()));
        return userRoleVO;
    }

    /**
     * 从请求头中获取当前登录数据
     * @return
     */
    public Optional<UserEntity> getCurrentLoginUser(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String authentication = request.getHeader("Authorization");
        Optional<JwtUtils.JwtDetail> jwtDetail = JwtUtils.parseJwt(authentication);
        if(jwtDetail.isPresent()){
            return get(jwtDetail.get().getUserId());
        }
        return Optional.empty();
    }

    @Override
    public BaseDao getDao() {
        return userDao;
    }
}

package com.project.user.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.common.dao.BaseDao;
import com.project.common.service.BaseService;
import com.project.common.service.MyBatisServiceImpl;
import com.project.user.dao.RoleDao;
import com.project.user.dao.UserDao;
import com.project.user.entity.UserEntity;
import com.project.user.pojo.UserRoleVO;
import com.project.util.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: UserServiceImpl
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

    public UserRoleVO getByAccountName(String accountName){
        QueryWrapper<UserEntity> userWrapper = new QueryWrapper<>();
        userWrapper.lambda().eq(UserEntity :: getAccountName,accountName);
        // UserEntity userEntity = dao.selectOne(userWrapper);
        UserEntity userEntity = null;
        if(userEntity == null){
            return null;
        }
        UserRoleVO userRoleVO = BeanUtils.copyBean(userEntity, UserRoleVO.class);
        userRoleVO.setRoleList(roleDao.listByUserId(userEntity.getId()));
        return userRoleVO;
    }

    public UserRoleVO getByMobile(String mobile){
        QueryWrapper<UserEntity> userWrapper = new QueryWrapper<>();
        userWrapper.lambda().eq(UserEntity :: getUserMobile,mobile);
        UserEntity userEntity = userDao.selectOne(userWrapper);
        if(userEntity == null){
            return null;
        }
        UserRoleVO userRoleVO = BeanUtils.copyBean(userEntity, UserRoleVO.class);
        userRoleVO.setRoleList(roleDao.listByUserId(userEntity.getId()));
        return userRoleVO;
    }

    public Optional<UserEntity> getCurrentLoginUser(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String authentication = request.getHeader("Authorization");

        if(StringUtils.isBlank(authentication)){
            return Optional.empty();
        }
        authentication = authentication.substring(7);
        Jwt jwt = JwtHelper.decode(authentication);
        String claims = jwt.getClaims();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(claims);
        String userId = jsonObject.getString("userId");
        UserEntity userEntity = get(userId);
        return Optional.ofNullable(userEntity);
    }

    @Override
    public BaseDao getDao() {
        return userDao;
    }
}

package com.project.util;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.io.Serializable;
import java.util.Optional;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: JwtUtils
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 14:02
 * @Version: 1.0
 */
public class JwtUtils {

    /**
     * 从JWT中用户ID、头像、呢称基本信息
     * @param authentication 认证头
     * @return
     */
    public static Optional<JwtDetail> parseJwt(String authentication){
        if(StringUtils.isBlank(authentication)){
            return Optional.empty();
        }
        authentication = authentication.substring(7); //替换掉bearer
        Jwt jwt = JwtHelper.decode(authentication);
        String claims = jwt.getClaims();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(claims);
        return Optional.ofNullable(BeanUtils.copyBean(jsonObject,JwtDetail.class));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtDetail implements Serializable {
        //用户ID
        private String userId;
        //用户别名
        private String userAlias;
        //用户头像
        private String userAvatar;
    }
}

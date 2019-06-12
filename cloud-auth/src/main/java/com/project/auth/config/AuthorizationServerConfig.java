package com.project.auth.config;

import com.google.common.collect.Maps;
import com.project.auth.security.MyUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: AuthorizationServerConfiguration
 * @Author: WangQingYun
 * @Date: Created in 2019/5/30 15:45
 * @Version: 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIEN_ID_ONE = "client_1";  //客户端1 用来标识客户的Id
    private static final String CLIEN_ID_TWO = "client_2";  //客户端2
    private static final String CLIENT_SECRET = "123456";   //secret客户端安全码
    private static final String GRANT_TYPE_PASSWORD = "password";   // 密码模式授权模式
    private static final String REFRESH_TOKEN = "refresh_token";    //刷新token
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";  //授权码模式
    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";  //客户端模式
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;          //单位：秒,1个小时
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;        //单位：秒,6个小时
    private static final String RESOURCE_ID = "*";    //指定哪些资源是需要授权验证的

    @Resource
    private AuthenticationManager authenticationManager;   //认证方式

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        String secret = passwordEncoder.encode(CLIENT_SECRET);// 用 BCrypt 对密码编码
        //配置3个个客户端,一个用于password认证、一个用于client认证、一个用于authorization_code认证
        configurer.inMemory()  // 使用in-memory存储
                .withClient(CLIEN_ID_ONE)    //client_id用来标识客户的Id  客户端1
               // .resourceIds(RESOURCE_ID)  //允许访问的resourceId列表,默认全部
                .authorizedGrantTypes(GRANT_TYPE_CLIENT_CREDENTIALS, REFRESH_TOKEN)  //允许授权类型   授权码模式
                .scopes(SCOPE_READ,SCOPE_WRITE)  //允许授权范围
                .authorities("SIMPLE_USER")  //客户端可以使用的权限
                .secret(secret)  //secret客户端安全码
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)    //token 时间秒
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS) //刷新token 时间 秒
                .and()
                .withClient(CLIEN_ID_TWO) //client_id用来标识客户的Id  客户端 2
             //   .resourceIds(RESOURCE_ID)  //允许访问的resourceId列表,默认全部
                .authorizedGrantTypes(GRANT_TYPE_AUTHORIZATION_CODE,GRANT_TYPE_PASSWORD, REFRESH_TOKEN)   //允许授权类型  密码授权模式
                .scopes(SCOPE_READ,SCOPE_WRITE) //允许授权范围
                .authorities("SIMPLE_USER") //客户端可以使用的权限
                .secret(secret)  //secret客户端安全码
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)    //token 时间秒
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS); //刷新token 时间 秒

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)  //支持GET  POST  请求获取token
                .tokenEnhancer(tokenEnhancerChain()) //拓展token
                .userDetailsService(userDetailsService) //必须注入userDetailsService否则根据refresh_token无法加载用户信息
                .reuseRefreshTokens(true);  //开启刷新token
    }


    /**
     * 认证服务器的安全配置
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //isAuthenticated():排除anonymous   isFullyAuthenticated():排除anonymous以及remember-me
        security
                .tokenKeyAccess("permitAll()") //访问/oauth/token不需要认证访问
                .checkTokenAccess("isAuthenticated()") //访问/oauth/check_token需要认证访问
                .allowFormAuthenticationForClients();  //允许表单认证
    }
    //对token进行非对称加密
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("UnitedNations"));
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
        //基于jwt实现令牌（Access Token）
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 自定义token
     *
     * @return TokenEnhancerChain
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        return tokenEnhancerChain;
    }
    /*
        扩展jwt token
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken,authentication) -> {
            Map<String, Object> additionalInfo = Maps.newHashMap();
            // 自定义token内容，加入组织机构信息
            additionalInfo.put("license", "made by United Nations");
            MyUserDetails userDetails = (MyUserDetails)authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put("userId", userDetails.getId());
            additionalInfo.put("nickName", userDetails.getNickName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}

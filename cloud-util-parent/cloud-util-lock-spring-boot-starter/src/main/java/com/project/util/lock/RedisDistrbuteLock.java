package com.project.util.lock;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 该类用redis实现了分布式锁的基本功能
 * set(key,value,"NX","EX",expire)指令：
 * 　　　参数：NX  等同SETNX指令,当key不存在则设置成功
 *      参数：EX  等同SETEX指令,设置过期时间
 *      返回值：ok-成功　null-失败
 *
 * eval(script,keyNum,k...,v...)指令：
 * 　　　参数：script 一个lua脚本字符串描述
 * 　　　参数：keyNum key的数量
 * 　　　参数: k...   可变长的key数组,长度由keyNum决定
 * 　　　参数: v...   可变长的value数组,与k...对应
 * @ClassName: RedisDistrbuteLock
 * @Author: WangQingYun
 * @Date: Created in 2019/4/17 10:11
 * @Version: 1.0
 */

public class RedisDistrbuteLock implements DistrbuteLock {

    //分布式锁前缀
    private final static String LOCK_PREFIX = "LOCK:";
    //锁过期时间数,redis默认单位是秒
    private final static int EXPIRE_IN_SECOND = 5;
    //删除key的lua脚本
    public final static String DEL_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]) return 'success' else return 'fail' end";


    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public boolean lock(String lockKey, String requestId) {
       /* while(true){
            if("OK".equals(jedisCluster.set(getRedisKey(lockKey,requestId),requestId,"NX","EX",EXPIRE_IN_SECOND))){
                return true; //加锁成功
            }else{
                continue;    //加锁失败，重新加锁
            }
        }*/

        while(true){
            if( redisTemplate.opsForValue().setIfAbsent(getRedisKey(lockKey,requestId),requestId,EXPIRE_IN_SECOND, TimeUnit.SECONDS)){
                return true; //加锁成功
            }else{
                continue;    //加锁失败，重新加锁
            }
        }
    }

    @Override
    public boolean tryLock(String lockKey, String requestId,int timeout) {
        if(timeout <= 0){
            throw new RuntimeException("请给定一个大于0的超时时间");
        }
        Date afterCurrentDate = DateUtil.offsetSecond(new Date(), timeout).toJdkDate();//计算超时日期

        while(true){
            if(redisTemplate.opsForValue().setIfAbsent(getRedisKey(lockKey,requestId),requestId,EXPIRE_IN_SECOND, TimeUnit.SECONDS)){
                return true; //加锁成功
            }else{
                if(new Date().before(afterCurrentDate)){
                    continue;      //加锁失败,未过超时时间,继续获取
                }else{
                    return false; //加锁失败,超时
                }

            }
        }
    }

    @Override
    public boolean unlock(String lockKey, String requestId) {
        //使用LUA脚本,保证原子性,：实现只有自己才能解自己加的锁
        //"1".equals(jedisCluster.eval(DEL_SCRIPT,1,getRedisKey(lockKey,requestId),requestId))
        DefaultRedisScript<String> redisScript =  new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptText(DEL_SCRIPT);
        if("success".equals(redisTemplate.execute(redisScript, Collections.singletonList(getRedisKey(lockKey,requestId)),requestId))){
            return true;
        }
        return false;
    }


    /**
     * 组装获取唯一key
     * @param lockKey
     * @param requestId
     * @return
     */

    private String getRedisKey(String lockKey, String requestId){
        return   LOCK_PREFIX + lockKey + ":" + requestId;

    }
}


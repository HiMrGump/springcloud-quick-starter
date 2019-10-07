package com.project.util.lock;

/**
 * 该接口定义了分布式锁的基本功能
 *
 * @ClassName: DistrbuteLock
 * @Author: WangQingYun
 * @Date: Created in 2019/4/17 10:08
 * @Version: 1.0
 */
public interface DistrbuteLock {
    /**
     * <pre>
     *  获取锁,如果获取不到锁则重试，直到获取锁
     * @param lockKey 锁标识，为uri
     * @param requestId 标识唯一业务的字段
     * @return true-加锁成功,false-加锁失败
     * </pre>
     */
     boolean lock(String lockKey, String requestId);

    /**
     * <pre>
     * 尝试获取锁，在指定时间内未获取到锁，则失败
     * @param lockKey 锁标识，为uri
     * @param requestId 标识唯一业务的字段
     * @param timeout 获取锁的超时时间,秒
     * @return true-加锁成功,false-加锁失败
     * </pre>
     */
     boolean tryLock(String lockKey, String requestId, int timeout);

    /**
     * <pre>
     * 释放锁,必须由持有锁的客户端才能解锁
     * @param lockKey 锁标识，为uri + requestId组合
     * @param requestId 标识唯一业务的字段，针对一条记录只有拿到锁的请求才能解锁
     * @return true-解锁成功  false-解锁失败
     * </pre>
     */
     boolean unlock(String lockKey, String requestId);
}

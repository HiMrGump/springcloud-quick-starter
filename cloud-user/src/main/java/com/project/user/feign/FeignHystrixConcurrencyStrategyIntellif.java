package com.project.user.feign;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.netflix.hystrix.strategy.HystrixPlugins.*;

/**
 * 自定义Feign的隔离策略;
 * 在转发Feign的请求头的时候，如果开启了Hystrix，Hystrix的默认隔离策略是Thread(线程隔离策略)，因此转发拦截器内是无法获取到请求的请求头信息的
 * 可以修改默认隔离策略为信号量模式：hystrix.command.default.execution.isolation.strategy=SEMAPHORE，这样的话转发线程和请求线程实际上是一个线程，这并不是最好的解决方法.信号量模式也不是官方最为推荐的隔离策略
 * 另一个解决方法就是自定义Hystrix的隔离策略，思路是将现有的并发策略作为新并发策略的成员变量,在新并发策略中，返回现有并发策略的线程池、Queue；将策略加到Spring容器即可；
 *
 * @ClassName: FeignHystrixConcurrencyStrategyIntellif
 * @Author: WangQingYun
 * @Date: Created in 2019/9/2 10:29
 * @Version: 1.0
 */
@Component
@Slf4j
public class FeignHystrixConcurrencyStrategyIntellif extends HystrixConcurrencyStrategy {

    private HystrixConcurrencyStrategy delegate = getInstance().getConcurrencyStrategy();

    public FeignHystrixConcurrencyStrategyIntellif() {
        try {
            if (this.delegate instanceof FeignHystrixConcurrencyStrategyIntellif) {
                // Welcome to singleton hell...
                return;
            }
            HystrixCommandExecutionHook commandExecutionHook =
                    getInstance().getCommandExecutionHook();
            HystrixEventNotifier eventNotifier = getInstance().getEventNotifier();
            HystrixMetricsPublisher metricsPublisher = getInstance().getMetricsPublisher();
            HystrixPropertiesStrategy propertiesStrategy =
                    getInstance().getPropertiesStrategy();
            this.logCurrentStateOfHystrixPlugins(eventNotifier, metricsPublisher, propertiesStrategy);
            reset();
            getInstance().registerConcurrencyStrategy(this);
            getInstance().registerCommandExecutionHook(commandExecutionHook);
            getInstance().registerEventNotifier(eventNotifier);
            getInstance().registerMetricsPublisher(metricsPublisher);
            getInstance().registerPropertiesStrategy(propertiesStrategy);
        } catch (Exception e) {
            log.error("Failed to register Sleuth Hystrix Concurrency Strategy", e);
        }
    }

    private void logCurrentStateOfHystrixPlugins(HystrixEventNotifier eventNotifier,
                                                 HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesStrategy) {
        if (log.isDebugEnabled()) {
            log.debug("Current Hystrix plugins configuration is [" + "concurrencyStrategy ["
                    + this.delegate + "]," + "eventNotifier [" + eventNotifier + "]," + "metricPublisher ["
                    + metricsPublisher + "]," + "propertiesStrategy [" + propertiesStrategy + "]," + "]");
            log.debug("Registering Sleuth Hystrix Concurrency Strategy.");
        }
    }

    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return new WrappedCallable<>(callable, requestAttributes);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixProperty<Integer> corePoolSize, HystrixProperty<Integer> maximumPoolSize,
                                            HystrixProperty<Integer> keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        return this.delegate.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime,
                unit, workQueue);
    }

    @Override
    public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey,
                                            HystrixThreadPoolProperties threadPoolProperties) {
        return this.delegate.getThreadPool(threadPoolKey, threadPoolProperties);
    }

    @Override
    public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
        return this.delegate.getBlockingQueue(maxQueueSize);
    }

    @Override
    public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
        return this.delegate.getRequestVariable(rv);
    }

    static class WrappedCallable<T> implements Callable<T> {
        private final Callable<T> target;
        private final RequestAttributes requestAttributes;

        public WrappedCallable(Callable<T> target, RequestAttributes requestAttributes) {
            this.target = target;
            this.requestAttributes = requestAttributes;
        }

        @Override
        public T call() throws Exception {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                return target.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}



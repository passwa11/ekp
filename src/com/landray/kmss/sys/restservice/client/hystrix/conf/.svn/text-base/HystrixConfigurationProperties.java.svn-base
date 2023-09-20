package com.landray.kmss.sys.restservice.client.hystrix.conf;

import com.netflix.config.ConfigurationManager;
import org.slf4j.Logger;

import java.util.Properties;

/**
 * hystrix配置属性，该类定义了hystrix比较场景的属性和其默认值{@link DefaultConfigurationProperties}<br/>
 * 可以在spring.xml中配置该bean类，可以定义多个使用于不同客户端，默认只有一个id=configurationProperties<br/>
 * 在spring bean初始化后，init-method=initConfig中会将properties加载到ConfigurationManager
 *
 * @author 苏运彬
 * @date 2021/7/28
 */
public class HystrixConfigurationProperties {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HystrixConfigurationProperties.class);

    private static final String SEPARATOR = ".";
    private static final String COMMAND_PREFIX = "hystrix.command";
    private static final String THREAD_POOL_PREFIX = "hystrix.threadpool";
    private static final String EXECUTION_ISOLATION_STRATEGY  = "execution.isolation.strategy";
    private static final String EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS = "execution.isolation.thread.timeoutInMilliseconds";
    private static final String EXECUTION_TIMEOUT_ENABLED = "execution.timeout.enabled";
    private static final String EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT = "execution.isolation.thread.interruptOnTimeout";
    private static final String EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_CANCEL = "execution.isolation.thread.interruptOnCancel";
    private static final String EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = "execution.isolation.semaphore.maxConcurrentRequests";

    private static final String FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = "fallback.isolation.semaphore.maxConcurrentRequests";
    private static final String FALLBACK_ENABLED = "fallback.enabled";

    private static final String CIRCUIT_BREAKER_ENABLED = "circuitBreaker.enabled";
    private static final String CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD = "circuitBreaker.requestVolumeThreshold";
    private static final String CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS = "circuitBreaker.sleepWindowInMilliseconds";
    private static final String CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE = "circuitBreaker.errorThresholdPercentage";
    private static final String CIRCUIT_BREAKER_FORCE_OPEN = "circuitBreaker.forceOpen";
    private static final String CIRCUIT_BREAKER_FORCE_CLOSED = "circuitBreaker.forceClosed";

    private static final String METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS = "metrics.rollingStats.timeInMilliseconds";
    private static final String METRICS_ROLLING_STATS_NUM_BUCKETS = "metrics.rollingStats.numBuckets";
    private static final String METRICS_ROLLING_PERCENTILE_ENABLED = "metrics.rollingPercentile.enabled";
    private static final String METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS = "metrics.rollingPercentile.timeInMilliseconds";
    private static final String METRICS_ROLLING_PERCENTILE_NUM_BUCKETS = "metrics.rollingPercentile.numBuckets";
    private static final String METRICS_ROLLING_PERCENTILE_BUCKET_SIZE = "metrics.rollingPercentile.bucketSize";
    private static final String METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS = "metrics.healthSnapshot.intervalInMilliseconds";

    private static final String REQUEST_CACHE_ENABLED = "requestCache.enabled";
    private static final String REQUEST_LOG_ENABLED = "requestLog.enabled";

    private static final String THREAD_POOL_CORE_SIZE = "coreSize";
    private static final String THREAD_POOL_MAXIMUM_SIZE = "maximumSize";
    private static final String THREAD_POOL_MAX_QUEUE_SIZE = "maxQueueSize";
    private static final String THREAD_POOL_QUEUE_SIZE_REJECTION_THRESHOLD = "queueSizeRejectionThreshold";
    private static final String THREAD_POOL_KEEP_ALIVE_TIME_MINUTES = "keepAliveTimeMinutes";
    private static final String THREAD_POOL_ALLOW_MAXIMUM_SIZE_TO_DIVERGE_FROM_CORE_SIZE = "allowMaximumSizeToDivergeFromCoreSize";
    private static final String THREAD_POOL_METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS = "metrics.rollingStats.timeInMilliseconds";
    private static final String THREAD_POOL_METRICS_ROLLING_STATS_NUM_BUCKETS = "metrics.rollingStats.numBuckets";

    /**
     * 命令key，默认为default，表示全局配置，若是针对某个command的配置，可以修改成对应类名（getSimpleName)<br/>
     * 格式：hystrix.command.${commandKey}.coreSize
     */
    private String commandKey = "default";
    /**
     * 执行隔离策略，默认为线程，可选择有THREAD和SEMAPHORE
     */
    private String executionIsolationStrategy = "THREAD";
    /**
     * 执行超时时间，默认为1000毫秒
     */
    private int executionIsolationThreadTimeoutInMilliseconds = 1000;
    /**
     * 是否开启执行超时，默认为true
     */
    private boolean executionTimeoutEnabled = true;
    /**
     * 发生超时时是否应中断执行，默认为true
     */
    private boolean executionIsolationThreadInterruptOnTimeout = true;
    /**
     * 发生取消时是否应中断执行，默认为false
     */
    private boolean executionIsolationThreadInterruptOnCancel = false;
    /**
     * 信号量策略的前提下，允许的最大请求数，默认为10
     */
    private int executionIsolationSemaphoreMaxConcurrentRequests = 10;

    /**
     * 回退允许从调用线程发出的最大请求数，同时支持THREAD和SEMAPHORE，默认为10<br/>
     * 并行进行回退的最大线程数
     */
    private int fallbackIsolationSemaphoreMaxConcurrentRequests = 10;
    /**
     * 在发生失败或拒绝时是否启动回退
     */
    private boolean fallbackEnabled = true;

    /**
     * 是否启用断路器，默认为true
     */
    private boolean circuitBreakerEnabled = true;
    /**
     * 滚动窗口中使电路跳闸的最小请求数，默认为20(10s)
     */
    private int circuitBreakerRequestVolumeThreshold = 20;
    /**
     * 设置在电路跳闸后拒绝请求，然后再次尝试确定电路是否应再次关闭之前拒绝请求的时间量，默认为5000毫秒
     */
    private int circuitBreakerSleepWindowInMilliseconds = 5000;
    /**
     * 设置错误百分比，等于或高于该百分比时，电路应跳闸开路并启动对回退逻辑的短路请求，默认为50
     */
    private int circuitBreakerErrorThresholdPercentage = 50;
    /**
     * 强制断路器进入打开（跳闸）状态，在该状态下它将拒绝所有请求，默认为false，不建议开启
     */
    private boolean circuitBreakerForceOpen = false;
    /**
     * 强制断路器进入关闭状态，在这种状态下，无论错误百分比如何，它都将允许请求，默认为false，不建议开启
     */
    private boolean circuitBreakerForceClosed = false;

    /**
     * 设置统计滚动窗口的持续时间，以毫秒为单位。这是 Hystrix 为断路器使用和发布保留指标的时间<br/>
     * 此属性仅影响初始指标创建，启动后对此属性所做的调整不会生效。这避免了指标数据丢失，并允许优化指标收集。<br/>
     * 默认为10000
     */
    private int metricsRollingStatsTimeInMilliseconds = 10000;
    /**
     * 设置滚动统计窗口划分的桶数，和持续时间相关，10000/10 可以，10000/20 也可以，但 10000/7 不行（整除）<br/>
     * 此属性仅影响初始指标创建，启动后对此属性所做的调整不会生效。这避免了指标数据丢失，并允许优化指标收集。<br/>
     * 默认为10
     */
    private int metricsRollingStatsNumBuckets = 10;
    /**
     * 执行延迟是否应作为百分位数进行跟踪和计算。如果它们被禁用，所有汇总统计信息（平均值、百分位数）将返回为 -1，默认为true
     */
    private boolean metricsRollingPercentileEnabled = true;
    /**
     * 设置滚动窗口的持续时间，其中保留执行时间以允许进行百分位数计算，以毫秒为单位，默认为60000<br/>
     * 此属性仅影响初始指标创建，启动后对此属性所做的调整不会生效。这避免了指标数据丢失，并允许优化指标收集
     */
    private int metricsRollingPercentileTimeInMilliseconds = 60000;
    /**
     * 设置rollingPercentile窗口将被分成的桶数,60000/6 可以，60000/60 也可以，但 10000/7 不行（整除）<br/>
     * 默认为6
     */
    private int metricsRollingPercentileNumBuckets = 6;
    /**
     * 此属性设置每个存储桶保留的最大执行次数。如果在此期间发生更多执行，它们将环绕并在存储桶的开头开始覆盖。<br/>
     * 例如，如果存储桶大小设置为 100 并表示 10 秒的存储桶窗口，但在此期间发生了 500 次执行，则只有最后 100 次执行将保留在该 10 秒存储桶中。<br/>
     * 如果增加此大小，这也会增加存储值所需的内存量，并增加对列表进行排序以进行百分位数计算所需的时间<br/>
     * 此属性仅影响初始指标创建，启动后对此属性所做的调整不会生效。这避免了指标数据丢失，并允许优化指标收集。<br/>
     * 默认值为100
     */
    private int metricsRollingPercentileBucketSize = 100;
    /**
     * 设置等待时间（以毫秒为单位），允许拍摄运行状况快照以计算成功和错误百分比并影响断路器状态。<br/>
     * 在大容量电路上，错误百分比的连续计算可能会占用 CPU，因此此属性允许您控制计算频率，默认为500
     */
    private int metricsHealthSnapshotIntervalInMilliseconds = 500;

    /**
     * 是否HystrixCommand.getCacheKey()应用于HystrixRequestCache通过请求范围缓存提供重复数据删除功能<br/>
     * 默认为true
     */
    private boolean requestCacheEnabled = true;
    /**
     * 是否应将HystrixCommand执行和事件记录到HystrixRequestLog（在请求折叠中可以看到折叠的事件）<br/>
     * 默认为true
     */
    private boolean requestLogEnabled = true;

    /**
     * 线程key，默认为default，表示全局配置，若是针对某个command的配置，可以修改成对应类名（getSimpleName)<br/>
     * 格式：hystrix.threadpool.${threadPoolKey}.coreSize
     */
    private String threadPoolKey = "default";
    /**
     * 设置核心线程池大小，默认为10
     */
    private int threadPoolCoreSize = 10;
    /**
     * 设置最大线程池大小。这是在不开始拒绝HystrixCommands的情况下可以支持的最大并发量。<br/>
     * 此设置仅在您同时设置时才生效allowMaximumSizeToDivergeFromCoreSize<br/>
     * 默认为10
     */
    private int threadPoolMaximumSize = 10;
    /**
     * 设置实现的最大队列大小<br/>
     * 如果将此设置为-1thenSynchronousQueue将被使用，否则将使用正值LinkedBlockingQueue<br/>
     * 此属性仅在初始化时适用，因为如果不重新初始化不受支持的线程执行器，就无法调整队列实现的大小或更改。<br/>
     * 默认为-1
     */
    private int threadPoolMaxQueueSize = -1;
    /**
     * 置队列大小拒绝阈值<br/>
     * 一个人为的最大队列大小，即使maxQueueSize尚未达到，也会发生拒绝<br/>
     * 默认为5
     */
    private int threadPoolQueueSizeRejectionThreshold = 5;
    /**
     * 设置保持活动时间，以分钟为单位<br/>
     * allowMaximumSizeToDivergeFromCoreSize为true允许这 2 个值发散，以便池可以获取/释放线程。如果coreSize < maximumSize，则此属性控制线程在被释放之前将处于未使用状态的时间<br/>
     * 默认为1
     */
    private int threadPoolKeepAliveTimeMinutes = 1;
    /**
     * 该属性允许配置maximumSize生效。然后该值可以等于或高于coreSize。设置coreSize < maximumSize创建了一个线程池，它可以维持maximumSize并发，但会在相对不活动期间将线程返回给系统。（受制于keepAliveTimeInMinutes）<br/>
     * 默认情况coreSize和maximumSize一样，如果想设置不同，可以开启该参数<br/>
     * 默认为false
     */
    private boolean threadPoolAllowMaximumSizeToDivergeFromCoreSize = false;
    /**
     * 设置统计滚动窗口的持续时间，以毫秒为单位。这是为线程池保留指标的时间<br/>
     * 默认为10000
     */
    private int threadPoolMetricsRollingStatsTimeInMilliseconds = 10000;
    /**
     * 设置滚动统计窗口划分的桶数<br/>
     * 10000/10 可以，10000/20 也可以，但 10000/7 不行（整除)<br/>
     * 默认为10
     */
    private int threadPoolMetricsRollingStatsNumBuckets = 10;

    /**
     * 配置初始化使用的properties集
     */
    private final Properties properties = new Properties();

    private <T>T setOrDefault(T value, T defaultValue){
        if(value == null){
            return defaultValue;
        }
        if(value instanceof String && "".equals(value)){
            return defaultValue;
        }
        return value;
    }

    public String getCommandKey() {
        return commandKey;
    }

    public void setCommandKey(String commandKey) {
        this.commandKey = setOrDefault(commandKey,DefaultConfigurationProperties.COMMAND_KEY);
    }

    public String getExecutionIsolationStrategy() {
        return executionIsolationStrategy;
    }

    public void setExecutionIsolationStrategy(String executionIsolationStrategy) {
        this.executionIsolationStrategy = setOrDefault(executionIsolationStrategy,DefaultConfigurationProperties.EXECUTION_ISOLATION_STRATEGY);
    }

    public int getExecutionIsolationThreadTimeoutInMilliseconds() {
        return executionIsolationThreadTimeoutInMilliseconds;
    }

    public void setExecutionIsolationThreadTimeoutInMilliseconds(int executionIsolationThreadTimeoutInMilliseconds) {
        this.executionIsolationThreadTimeoutInMilliseconds = setOrDefault(executionIsolationThreadTimeoutInMilliseconds,DefaultConfigurationProperties.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS);
    }

    public boolean isExecutionTimeoutEnabled() {
        return executionTimeoutEnabled;
    }

    public void setExecutionTimeoutEnabled(boolean executionTimeoutEnabled) {
        this.executionTimeoutEnabled = setOrDefault(executionTimeoutEnabled,DefaultConfigurationProperties.EXECUTION_TIMEOUT_ENABLED);
    }

    public boolean isExecutionIsolationThreadInterruptOnTimeout() {
        return executionIsolationThreadInterruptOnTimeout;
    }

    public void setExecutionIsolationThreadInterruptOnTimeout(boolean executionIsolationThreadInterruptOnTimeout) {
        this.executionIsolationThreadInterruptOnTimeout = setOrDefault(executionIsolationThreadInterruptOnTimeout,DefaultConfigurationProperties.EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT);
    }

    public boolean isExecutionIsolationThreadInterruptOnCancel() {
        return executionIsolationThreadInterruptOnCancel;
    }

    public void setExecutionIsolationThreadInterruptOnCancel(boolean executionIsolationThreadInterruptOnCancel) {
        this.executionIsolationThreadInterruptOnCancel = setOrDefault(executionIsolationThreadInterruptOnCancel,DefaultConfigurationProperties.EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_CANCEL);
    }

    public int getExecutionIsolationSemaphoreMaxConcurrentRequests() {
        return executionIsolationSemaphoreMaxConcurrentRequests;
    }

    public void setExecutionIsolationSemaphoreMaxConcurrentRequests(int executionIsolationSemaphoreMaxConcurrentRequests) {
        this.executionIsolationSemaphoreMaxConcurrentRequests = setOrDefault(executionIsolationSemaphoreMaxConcurrentRequests,DefaultConfigurationProperties.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS);
    }

    public int getFallbackIsolationSemaphoreMaxConcurrentRequests() {
        return fallbackIsolationSemaphoreMaxConcurrentRequests;
    }

    public void setFallbackIsolationSemaphoreMaxConcurrentRequests(int fallbackIsolationSemaphoreMaxConcurrentRequests) {
        this.fallbackIsolationSemaphoreMaxConcurrentRequests = setOrDefault(fallbackIsolationSemaphoreMaxConcurrentRequests,DefaultConfigurationProperties.FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS);
    }

    public boolean isFallbackEnabled() {
        return fallbackEnabled;
    }

    public void setFallbackEnabled(boolean fallbackEnabled) {
        this.fallbackEnabled = setOrDefault(fallbackEnabled,DefaultConfigurationProperties.FALLBACK_ENABLED);
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public void setCircuitBreakerEnabled(boolean circuitBreakerEnabled) {
        this.circuitBreakerEnabled = setOrDefault(circuitBreakerEnabled,DefaultConfigurationProperties.CIRCUIT_BREAKER_ENABLED);
    }

    public int getCircuitBreakerRequestVolumeThreshold() {
        return circuitBreakerRequestVolumeThreshold;
    }

    public void setCircuitBreakerRequestVolumeThreshold(int circuitBreakerRequestVolumeThreshold) {
        this.circuitBreakerRequestVolumeThreshold = setOrDefault(circuitBreakerRequestVolumeThreshold,DefaultConfigurationProperties.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD);
    }

    public int getCircuitBreakerSleepWindowInMilliseconds() {
        return circuitBreakerSleepWindowInMilliseconds;
    }

    public void setCircuitBreakerSleepWindowInMilliseconds(int circuitBreakerSleepWindowInMilliseconds) {
        this.circuitBreakerSleepWindowInMilliseconds = setOrDefault(circuitBreakerSleepWindowInMilliseconds,DefaultConfigurationProperties.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS);
    }

    public int getCircuitBreakerErrorThresholdPercentage() {
        return circuitBreakerErrorThresholdPercentage;
    }

    public void setCircuitBreakerErrorThresholdPercentage(int circuitBreakerErrorThresholdPercentage) {
        this.circuitBreakerErrorThresholdPercentage = setOrDefault(circuitBreakerErrorThresholdPercentage,DefaultConfigurationProperties.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE);
    }

    public boolean isCircuitBreakerForceOpen() {
        return circuitBreakerForceOpen;
    }

    public void setCircuitBreakerForceOpen(boolean circuitBreakerForceOpen) {
        this.circuitBreakerForceOpen = setOrDefault(circuitBreakerForceOpen,DefaultConfigurationProperties.CIRCUIT_BREAKER_FORCE_OPEN);
    }

    public boolean isCircuitBreakerForceClosed() {
        return circuitBreakerForceClosed;
    }

    public void setCircuitBreakerForceClosed(boolean circuitBreakerForceClosed) {
        this.circuitBreakerForceClosed = setOrDefault(circuitBreakerForceClosed,DefaultConfigurationProperties.CIRCUIT_BREAKER_FORCE_CLOSED);
    }

    public int getMetricsRollingStatsTimeInMilliseconds() {
        return metricsRollingStatsTimeInMilliseconds;
    }

    public void setMetricsRollingStatsTimeInMilliseconds(int metricsRollingStatsTimeInMilliseconds) {
        this.metricsRollingStatsTimeInMilliseconds = setOrDefault(metricsRollingStatsTimeInMilliseconds,DefaultConfigurationProperties.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS);
    }

    public int getMetricsRollingStatsNumBuckets() {
        return metricsRollingStatsNumBuckets;
    }

    public void setMetricsRollingStatsNumBuckets(int metricsRollingStatsNumBuckets) {
        this.metricsRollingStatsNumBuckets = setOrDefault(metricsRollingStatsNumBuckets,DefaultConfigurationProperties.METRICS_ROLLING_STATS_NUM_BUCKETS);
    }

    public boolean isMetricsRollingPercentileEnabled() {
        return metricsRollingPercentileEnabled;
    }

    public void setMetricsRollingPercentileEnabled(boolean metricsRollingPercentileEnabled) {
        this.metricsRollingPercentileEnabled = setOrDefault(metricsRollingPercentileEnabled,DefaultConfigurationProperties.METRICS_ROLLING_PERCENTILE_ENABLED);
    }

    public int getMetricsRollingPercentileTimeInMilliseconds() {
        return metricsRollingPercentileTimeInMilliseconds;
    }

    public void setMetricsRollingPercentileTimeInMilliseconds(int metricsRollingPercentileTimeInMilliseconds) {
        this.metricsRollingPercentileTimeInMilliseconds = setOrDefault(metricsRollingPercentileTimeInMilliseconds,DefaultConfigurationProperties.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS);
    }

    public int getMetricsRollingPercentileNumBuckets() {
        return metricsRollingPercentileNumBuckets;
    }

    public void setMetricsRollingPercentileNumBuckets(int metricsRollingPercentileNumBuckets) {
        this.metricsRollingPercentileNumBuckets = setOrDefault(metricsRollingPercentileNumBuckets,DefaultConfigurationProperties.METRICS_ROLLING_PERCENTILE_NUM_BUCKETS);
    }

    public int getMetricsRollingPercentileBucketSize() {
        return metricsRollingPercentileBucketSize;
    }

    public void setMetricsRollingPercentileBucketSize(int metricsRollingPercentileBucketSize) {
        this.metricsRollingPercentileBucketSize = setOrDefault(metricsRollingPercentileBucketSize,DefaultConfigurationProperties.METRICS_ROLLING_PERCENTILE_BUCKET_SIZE);
    }

    public int getMetricsHealthSnapshotIntervalInMilliseconds() {
        return metricsHealthSnapshotIntervalInMilliseconds;
    }

    public void setMetricsHealthSnapshotIntervalInMilliseconds(int metricsHealthSnapshotIntervalInMilliseconds) {
        this.metricsHealthSnapshotIntervalInMilliseconds = setOrDefault(metricsHealthSnapshotIntervalInMilliseconds,DefaultConfigurationProperties.METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS);
    }

    public boolean isRequestCacheEnabled() {
        return requestCacheEnabled;
    }

    public void setRequestCacheEnabled(boolean requestCacheEnabled) {
        this.requestCacheEnabled = setOrDefault(requestCacheEnabled,DefaultConfigurationProperties.REQUEST_CACHE_ENABLED);
    }

    public boolean isRequestLogEnabled() {
        return requestLogEnabled;
    }

    public void setRequestLogEnabled(boolean requestLogEnabled) {
        this.requestLogEnabled = setOrDefault(requestLogEnabled,DefaultConfigurationProperties.REQUEST_LOG_ENABLED);
    }

    public String getThreadPoolKey() {
        return threadPoolKey;
    }

    public void setThreadPoolKey(String threadPoolKey) {
        this.threadPoolKey = setOrDefault(threadPoolKey,DefaultConfigurationProperties.THREAD_POOL_KEY);
    }

    public int getThreadPoolCoreSize() {
        return threadPoolCoreSize;
    }

    public void setThreadPoolCoreSize(int threadPoolCoreSize) {
        this.threadPoolCoreSize = setOrDefault(threadPoolCoreSize,DefaultConfigurationProperties.THREAD_POOL_CORE_SIZE);
    }

    public int getThreadPoolMaximumSize() {
        return threadPoolMaximumSize;
    }

    public void setThreadPoolMaximumSize(int threadPoolMaximumSize) {
        this.threadPoolMaximumSize = setOrDefault(threadPoolMaximumSize,DefaultConfigurationProperties.THREAD_POOL_MAXIMUM_SIZE);
    }

    public int getThreadPoolMaxQueueSize() {
        return threadPoolMaxQueueSize;
    }

    public void setThreadPoolMaxQueueSize(int threadPoolMaxQueueSize) {
        this.threadPoolMaxQueueSize = setOrDefault(threadPoolMaxQueueSize,DefaultConfigurationProperties.THREAD_POOL_MAX_QUEUE_SIZE);
    }

    public int getThreadPoolQueueSizeRejectionThreshold() {
        return threadPoolQueueSizeRejectionThreshold;
    }

    public void setThreadPoolQueueSizeRejectionThreshold(int threadPoolQueueSizeRejectionThreshold) {
        this.threadPoolQueueSizeRejectionThreshold = setOrDefault(threadPoolQueueSizeRejectionThreshold,DefaultConfigurationProperties.THREAD_POOL_QUEUE_SIZE_REJECTION_THRESHOLD);
    }

    public int getThreadPoolKeepAliveTimeMinutes() {
        return threadPoolKeepAliveTimeMinutes;
    }

    public void setThreadPoolKeepAliveTimeMinutes(int threadPoolKeepAliveTimeMinutes) {
        this.threadPoolKeepAliveTimeMinutes = setOrDefault(threadPoolKeepAliveTimeMinutes,DefaultConfigurationProperties.THREAD_POOL_KEEP_ALIVE_TIME_MINUTES);
    }

    public boolean isThreadPoolAllowMaximumSizeToDivergeFromCoreSize() {
        return threadPoolAllowMaximumSizeToDivergeFromCoreSize;
    }

    public void setThreadPoolAllowMaximumSizeToDivergeFromCoreSize(boolean threadPoolAllowMaximumSizeToDivergeFromCoreSize) {
        this.threadPoolAllowMaximumSizeToDivergeFromCoreSize = setOrDefault(threadPoolAllowMaximumSizeToDivergeFromCoreSize,DefaultConfigurationProperties.THREAD_POOL_ALLOW_MAXIMUM_SIZE_TO_DIVERGE_FROM_CORE_SIZE);
    }

    public int getThreadPoolMetricsRollingStatsTimeInMilliseconds() {
        return threadPoolMetricsRollingStatsTimeInMilliseconds;
    }

    public void setThreadPoolMetricsRollingStatsTimeInMilliseconds(int threadPoolMetricsRollingStatsTimeInMilliseconds) {
        this.threadPoolMetricsRollingStatsTimeInMilliseconds = setOrDefault(threadPoolMetricsRollingStatsTimeInMilliseconds,DefaultConfigurationProperties.THREAD_POOL_METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS);
    }

    public int getThreadPoolMetricsRollingStatsNumBuckets() {
        return threadPoolMetricsRollingStatsNumBuckets;
    }

    public void setThreadPoolMetricsRollingStatsNumBuckets(int threadPoolMetricsRollingStatsNumBuckets) {
        this.threadPoolMetricsRollingStatsNumBuckets = setOrDefault(threadPoolMetricsRollingStatsNumBuckets,DefaultConfigurationProperties.THREAD_POOL_METRICS_ROLLING_STATS_NUM_BUCKETS);
    }

   private void initProperties() {
        String cPrefix = COMMAND_PREFIX + SEPARATOR + commandKey + SEPARATOR;
        String tPrefix = THREAD_POOL_PREFIX + SEPARATOR + commandKey + SEPARATOR;
        properties.put(cPrefix + EXECUTION_ISOLATION_STRATEGY,this.executionIsolationStrategy);
        properties.put(cPrefix + EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS,this.executionIsolationThreadTimeoutInMilliseconds);
        properties.put(cPrefix + EXECUTION_TIMEOUT_ENABLED,this.executionTimeoutEnabled);
        properties.put(cPrefix + EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT,this.executionIsolationThreadInterruptOnTimeout);
        properties.put(cPrefix + EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_CANCEL,this.executionIsolationThreadInterruptOnCancel);
        properties.put(cPrefix + EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,this.executionIsolationSemaphoreMaxConcurrentRequests);
        properties.put(cPrefix + FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,this.fallbackIsolationSemaphoreMaxConcurrentRequests);
        properties.put(cPrefix + FALLBACK_ENABLED,this.fallbackEnabled);
        properties.put(cPrefix + CIRCUIT_BREAKER_ENABLED,this.circuitBreakerEnabled);
        properties.put(cPrefix + CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD,this.circuitBreakerRequestVolumeThreshold);
        properties.put(cPrefix + CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS,this.circuitBreakerSleepWindowInMilliseconds);
        properties.put(cPrefix + CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE,this.circuitBreakerErrorThresholdPercentage);
        properties.put(cPrefix + CIRCUIT_BREAKER_FORCE_OPEN,this.circuitBreakerForceOpen);
        properties.put(cPrefix + CIRCUIT_BREAKER_FORCE_CLOSED,this.circuitBreakerForceClosed);
        properties.put(cPrefix + METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS,this.metricsRollingStatsTimeInMilliseconds);
        properties.put(cPrefix + METRICS_ROLLING_STATS_NUM_BUCKETS,this.metricsRollingStatsNumBuckets);
        properties.put(cPrefix + METRICS_ROLLING_PERCENTILE_ENABLED,this.metricsRollingPercentileEnabled);
        properties.put(cPrefix + METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS,this.metricsRollingPercentileTimeInMilliseconds);
        properties.put(cPrefix + METRICS_ROLLING_PERCENTILE_NUM_BUCKETS,this.metricsRollingPercentileNumBuckets);
        properties.put(cPrefix + METRICS_ROLLING_PERCENTILE_BUCKET_SIZE,this.metricsRollingPercentileBucketSize);
        properties.put(cPrefix + METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS,this.metricsHealthSnapshotIntervalInMilliseconds);
        properties.put(cPrefix + REQUEST_CACHE_ENABLED,this.requestCacheEnabled);
        properties.put(cPrefix + REQUEST_LOG_ENABLED,this.requestLogEnabled);
        properties.put(tPrefix + THREAD_POOL_CORE_SIZE,this.threadPoolCoreSize);
        properties.put(tPrefix + THREAD_POOL_MAXIMUM_SIZE,this.threadPoolMaximumSize);
        properties.put(tPrefix + THREAD_POOL_MAX_QUEUE_SIZE,this.threadPoolMaxQueueSize);
        properties.put(tPrefix + THREAD_POOL_QUEUE_SIZE_REJECTION_THRESHOLD,this.threadPoolQueueSizeRejectionThreshold);
        properties.put(tPrefix + THREAD_POOL_KEEP_ALIVE_TIME_MINUTES,this.threadPoolKeepAliveTimeMinutes);
        properties.put(tPrefix + THREAD_POOL_ALLOW_MAXIMUM_SIZE_TO_DIVERGE_FROM_CORE_SIZE,this.threadPoolAllowMaximumSizeToDivergeFromCoreSize);
        properties.put(tPrefix + THREAD_POOL_METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS,this.threadPoolMetricsRollingStatsTimeInMilliseconds);
        properties.put(tPrefix + THREAD_POOL_METRICS_ROLLING_STATS_NUM_BUCKETS,this.threadPoolMetricsRollingStatsNumBuckets);
    }

    /**
     * 将properties设置到配置管理器（动态属性），构建hystrixCommand的时候会自动读取
     */
    private void initConfig(){
        try {
            if(logger.isInfoEnabled()){
                logger.info("begin load ConfigurationProperties (Hystrix Config)");
            }

            initProperties();
            ConfigurationManager.loadProperties(this.properties);

            if(logger.isInfoEnabled()){
                logger.info("end load ConfigurationProperties (Hystrix Config)");
            }
        } catch (Exception e) {
            if(logger.isErrorEnabled()){
                logger.error("init ConfigurationProperties fail", e);
            }
        }
    }

    public class DefaultConfigurationProperties {
        public static final String COMMAND_KEY = "default";
        public static final String EXECUTION_ISOLATION_STRATEGY = "THREAD";
        public static final int EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS = 1000;
        public static final boolean EXECUTION_TIMEOUT_ENABLED = true;
        public static final boolean EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT = true;
        public static final boolean EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_CANCEL = false;
        public static final int EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = 10;

        public static final int FALLBACK_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS = 10;
        public static final boolean FALLBACK_ENABLED = true;

        public static final boolean CIRCUIT_BREAKER_ENABLED = true;
        public static final int CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD = 20;
        public static final int CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS = 5000;
        public static final int CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE = 50;
        public static final boolean CIRCUIT_BREAKER_FORCE_OPEN = false;
        public static final boolean CIRCUIT_BREAKER_FORCE_CLOSED = false;

        public static final int METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS = 10000;
        public static final int METRICS_ROLLING_STATS_NUM_BUCKETS = 10;
        public static final boolean METRICS_ROLLING_PERCENTILE_ENABLED = true;
        public static final int METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS = 60000;
        public static final int METRICS_ROLLING_PERCENTILE_NUM_BUCKETS = 6;
        public static final int METRICS_ROLLING_PERCENTILE_BUCKET_SIZE = 100;
        public static final int METRICS_HEALTH_SNAPSHOT_INTERVAL_IN_MILLISECONDS = 500;

        public static final boolean REQUEST_CACHE_ENABLED = true;
        public static final boolean REQUEST_LOG_ENABLED = true;

        public static final String THREAD_POOL_KEY = "default";
        public static final int THREAD_POOL_CORE_SIZE = 10;
        public static final int THREAD_POOL_MAXIMUM_SIZE = 10;
        public static final int THREAD_POOL_MAX_QUEUE_SIZE = -1;
        public static final int THREAD_POOL_QUEUE_SIZE_REJECTION_THRESHOLD = 5;
        public static final int THREAD_POOL_KEEP_ALIVE_TIME_MINUTES = 1;
        public static final boolean THREAD_POOL_ALLOW_MAXIMUM_SIZE_TO_DIVERGE_FROM_CORE_SIZE = false;
        public static final int THREAD_POOL_METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS = 10000;
        public static final int THREAD_POOL_METRICS_ROLLING_STATS_NUM_BUCKETS = 10;
    }
}

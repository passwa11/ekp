package com.landray.kmss.util.thread;

import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 后台线程的监控线程，用于记录线程在运行期间的调用栈以及时间，与{@link NamedAndMonitoredThreadFactory} 对应，
 * 用于监控线程池中的线程执行情况，会随着线程消亡而消亡<br/>
 * <b>#1 不可用于生产，不可用于生产，不可用于生产</b><br/>
 * <b>#2 默认采样间隔1ms</b> {@link #setFrequency(int)}<br/>
 * <b>#3 默认超过10000ms的线程日志会被输出</b> {@link #setMinDuration(int)}<br/>
 * <b>#4 如果业务直接通过new Thread().start()的方式，那么可以使用以下样例进行监控</b>
 * <code>
 *     <pre>
 *      Thread targetThread = ...;
 *      AutoShutdownThreadMonitor monitor =
 *          new AutoShutdownThreadMonitor(targetThread.getName(),targetThread);
 *      monitor.setXXX();//如果需要设置采样频率等参数的话
 * 		monitor.begin();
 * 	    targetThread.start();
 *     </pre>
 * </code>
 */
public class AutoShutdownThreadMonitor implements Runnable {

    private static final Log logger = LogFactory
            .getLog(AutoShutdownThreadMonitor.class);

    private Thread targetThread;
    private String traceId;
    private boolean running = true;

    //以下属性是一次运行周期内的数据，每次执行完成后会重置
    private StackTraceInfo root = null;
    private long lastTimestamp = 0;
    private long thisTimestamp = 0;
    private long maxInterval = 0;
    private long monitorBegin = 0;

    private int minDuration = 10000;
    private int frequency = 10;

    public AutoShutdownThreadMonitor(String traceId, Thread targetThread) {
        this.targetThread = targetThread;
        this.traceId = traceId;
        String duration = ResourceUtil.getKmssConfigString("kmss.performance.tuning.background.min.duration");
        minDuration = duration == null ? 10000 : Integer.parseInt(duration);
        String frequency = ResourceUtil.getKmssConfigString("kmss.performance.tuning.background.frequency");
        this.frequency = frequency == null ? 1 : Integer.parseInt(frequency);
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * 开始监控
     */
    public void begin() {
        new Thread(this, "ThreadMonitor:" + targetThread.getName()).start();
    }

    /**
     * 监控结束
     */
    public void end() {
        running = false;
    }

    @Override
    public void run() {

        while (running) {
            Thread.State targetThreadState = targetThread.getState();
            try {
                if (Thread.State.NEW.equals(targetThreadState)) {
                    //新建线程还没开始的线程，直接跳过
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(targetThread.getName() + "未开始");
                    }
                    continue;
                }
                //如果线程终结，清理上下文信息，并退出监控
                if (Thread.State.TERMINATED.equals(targetThreadState)) {
                    if (logger.isInfoEnabled()) {
                        logger.info("结束监控:" + targetThread.getName());
                    }
                    if (root != null) {
                        doLog(System.currentTimeMillis() - monitorBegin);
                        reset();
                    }
                    return;
                }
                //WAITING表示线程完成了一轮工作
                if (Thread.State.WAITING.equals(targetThreadState)) {
                    if (root != null) {
                        doLog(System.currentTimeMillis() - monitorBegin);
                        reset();
                    }
                }
                //核心状态，线程运行期间需要搜集运行
                if (Thread.State.RUNNABLE.equals(targetThreadState)
                        || Thread.State.TIMED_WAITING.equals(targetThreadState)
                        || Thread.State.BLOCKED.equals(targetThreadState)) {
                    if (monitorBegin == 0) {
                        monitorBegin = System.currentTimeMillis();
                    }
                    if (root == null) {
                        root = new StackTraceInfo();
                        if (logger.isDebugEnabled()) {
                            logger.debug(targetThread.getName() + "进入运行阶段，开始监控");
                        }
                    }
                    lastTimestamp = thisTimestamp;
                    thisTimestamp = System.currentTimeMillis();
                    if (thisTimestamp == lastTimestamp) {
                        try {
                            Thread.sleep(frequency);
                        } catch (InterruptedException e) {
                        }
                        thisTimestamp = System.currentTimeMillis();
                    }
                    doCollect();
                }

            } catch (Exception e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("监控线程异常：" + e.getMessage() + ", 受控线程状态：" + targetThreadState);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    private void reset() {
        monitorBegin = 0;
        lastTimestamp = 0;
        lastTimestamp = 0;
        root = null;
    }

    /**
     * 采集线程堆栈信息
     */
    private void doCollect() {
        StackTraceElement[] elements = targetThread.getStackTrace();
        Map<String, StackTraceInfo> curInfos = root.nexts;
        long dt = thisTimestamp - lastTimestamp;
        if (lastTimestamp > 0) {
            maxInterval = Math.max(maxInterval, dt);
        }
        for (int i = elements.length - 1; i >= 0; i--) {
            StackTraceElement element = elements[i];
            String name = stringJoin(element.getClassName(), ".",
                    element.getMethodName(), "(", element.getFileName(), ":",
                    element.getLineNumber(), ")");
            StackTraceInfo stackInfo = curInfos.get(name);
            if (stackInfo == null) {
                stackInfo = new StackTraceInfo();
                curInfos.put(name, stackInfo);
            }
            if (this.lastTimestamp > 0 && stackInfo.lastTimestamp == this.lastTimestamp) {
                stackInfo.useTimestamp += dt;
            } else {
                stackInfo.count++;
            }
            stackInfo.lastTimestamp = this.thisTimestamp;
            curInfos = stackInfo.nexts;
        }
    }

    private String stringJoin(Object... ss) {
        StringBuilder sb = new StringBuilder();
        for (Object s : ss) {
            if (s != null) {
                sb.append(s);
            }
        }
        return sb.toString();
    }


    /**
     * 记录日志
     */
    private void doLog(long dt) {
        if (dt < minDuration) {
            if (logger.isInfoEnabled()) {
                logger.info("线程执行时间未超过" + minDuration + "ms，不输出堆栈");
            }
            return;
        }
        long minLogTime = 10;
        StringBuilder message = new StringBuilder();
        message.append("dt：").append(dt)
                .append("，\r\nTRACE-STACK：").append(targetThread.getName())
                .append("，\r\ntraceId：").append(traceId)
                .append("，\r\nmessage：最大采样间隔").append(maxInterval)
                .append("ms，仅显示不小于").append(minLogTime).append("ms的堆栈\r\n");
        buildStackTrace(message, root.nexts, 0, minLogTime);
        if (logger.isWarnEnabled()) {
            logger.warn(message.toString());
        }
    }

    private Comparator<Entry<String, StackTraceInfo>> comparator= new Comparator<Entry<String, StackTraceInfo>>() {
        @Override
        public int compare(Entry<String, StackTraceInfo> o1, Entry<String, StackTraceInfo> o2) {
            long v1 = o1.getValue().useTimestamp;
            long v2 = o2.getValue().useTimestamp;
            return v2 > v1 ? 1 : (v2 == v1 ? 0 : -1);
        }
    };

    /**
     * 输出堆栈信息（递归）
     */
    private void buildStackTrace(StringBuilder message, Map<String, StackTraceInfo> infos, int index, long minLogTime) {
        if (infos.isEmpty()) {
            return;
        }
        List<Entry<String, StackTraceInfo>> list = new ArrayList<Entry<String, StackTraceInfo>>(
                infos.entrySet());
        // 排序
        Collections.sort(list,comparator );
        // 输出
        int nextIndex = index + 1;
        for (Entry<String, StackTraceInfo> entry : list) {
            StackTraceInfo info = entry.getValue();
            if (info.useTimestamp < minLogTime) {
                continue;
            }
            for (int i = 0; i < index; i++) {
                message.append("| ");
            }
            message.append("(").append(info.useTimestamp).append(", ").append(info.count)
                    .append(") ").append(entry.getKey()).append("\r\n");
            buildStackTrace(message, info.nexts, nextIndex, minLogTime);
        }
    }

    /**
     * 堆栈信息
     */
    private static class StackTraceInfo {
        int count = 0;
        long useTimestamp = 0;
        long lastTimestamp = 0;
        Map<String, StackTraceInfo> nexts = new HashMap<String, StackTraceInfo>();
    }

}

package com.landray.kmss.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 单一线程监控
 */
public class SingleThreadMonitor implements Runnable {

    private static final Log logger = LogFactory
            .getLog(SingleThreadMonitor.class);

    private Thread thread;
    private String traceId;
    private boolean running = true;
    private StackTraceInfo root = new StackTraceInfo();
    private long lastTimestamp = 0;
    private long thisTimestamp = 0;
    private long maxInterval = 0;

    public SingleThreadMonitor(String traceId) {
        this.thread = Thread.currentThread();
        this.traceId = traceId;
    }

    /**
     * 开始监控
     */
    public void begin() {
        new Thread(this).start();
    }

    /**
     * 监控结束
     */
    public void end() {
        running = false;
    }

    @Override
    public void run() {
        long monitorBegin = System.currentTimeMillis();
        while (running) {
            lastTimestamp = thisTimestamp;
            thisTimestamp = System.currentTimeMillis();
            if (thisTimestamp == lastTimestamp) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                thisTimestamp = System.currentTimeMillis();
            }
            doCollect();
        }
        doLog(System.currentTimeMillis() - monitorBegin);
    }

    /**
     * 采集线程堆栈信息
     */
    private void doCollect() {
        StackTraceElement[] elements = thread.getStackTrace();
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
        long minLogTime = 5;
        StringBuilder message = new StringBuilder();
        message.append("dt：").append(dt)
                .append("，TRACE-STACK：").append(thread.getName())
                .append("，traceId：").append(traceId)
                .append("，message：最大采样间隔").append(maxInterval)
                .append("ms，仅显示不小于").append(minLogTime).append("ms的堆栈\r\n");
        buildStackTrace(message, root.nexts, 0, minLogTime);
        logger.warn(message.toString());
    }

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
        Collections.sort(list, new Comparator<Entry<String, StackTraceInfo>>() {
            @Override
            public int compare(Entry<String, StackTraceInfo> o1, Entry<String, StackTraceInfo> o2) {
                long v1 = o1.getValue().useTimestamp;
                long v2 = o2.getValue().useTimestamp;
                return v2 > v1 ? 1 : (v2 == v1 ? 0 : -1);
            }
        });
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

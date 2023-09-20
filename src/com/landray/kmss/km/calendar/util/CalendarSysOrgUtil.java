package com.landray.kmss.km.calendar.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalendarSysOrgUtil {

    private static ExecutorService threadPool = null;

    public static ExecutorService getThreadPool(){
        if(threadPool == null){
            threadPool = Executors.newFixedThreadPool(3);
        }
        return threadPool;
    }

    /**
     * 根据层级ID过滤子组织
     *
     * @param hids
     * @return
     */
    public static Set<String> filterSubHierarchy(Set<String> hids) {
        if (CollectionUtils.isEmpty(hids)) {
            return Collections.EMPTY_SET;
        }
        Set<String> resultTemp = new HashSet<String>();
        Set<String> addTemp = new HashSet<String>();
        Set<String> delTemp = new HashSet<String>();
        for (String hid1 : hids) {
            boolean add = true;
            for (String hid2 : resultTemp) {
                if (hid2.startsWith(hid1)) {
                    add = false;
                    delTemp.add(hid2);
                    addTemp.add(hid1);
                    continue;
                } else if (hid1.startsWith(hid2)) {
                    add = false;
                    break;
                }
            }
            if (add) {
                resultTemp.add(hid1);
            } else {
                resultTemp.removeAll(delTemp);
                resultTemp.addAll(addTemp);
                delTemp.clear();
                addTemp.clear();
            }
        }
        return resultTemp;
    }
}

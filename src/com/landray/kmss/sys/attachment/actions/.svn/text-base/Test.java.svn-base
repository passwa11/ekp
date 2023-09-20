package com.landray.kmss.sys.attachment.actions;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Test {
    private static Map<String, String> map = new HashMap<>();

    private static AtomicLong count = new AtomicLong(0);

    public static void main(String[] args) {
        String s = "thread -- ";
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(new MapRunnable(s+i));
            t.start();
        }
    }

    static class MapRunnable implements Runnable {
        private String k;

        public MapRunnable(String k) {
            this.k = k;
        }

        @Override
        public void run() {
            map.put(k, k);
            System.out.println(Thread.currentThread().getName() + "  " + count.incrementAndGet());
        }
    }
}



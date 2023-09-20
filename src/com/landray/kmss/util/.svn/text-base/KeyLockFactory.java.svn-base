package com.landray.kmss.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据某个key进行多线程锁，使用方法：<br>
 *
 * 多线程并发时，等待执行
 *
 * <pre>
 * class X {
 * 	KeyLockFactory factory = new KeyLockFactory();
 *
 * 	void m() {
 * 		KeyLock lock = factory.getKeyLock("myKey").lock();
 * 		try {
 * 			// ...
 * 		} finally {
 * 			lock.unlock();
 * 		}
 * 	}
 * }
 * </pre>
 *
 * 多线程并发时，只允许执行一次
 *
 * <pre>
 * class Y {
 * 	KeyLockFactory factory = new KeyLockFactory();
 *
 * 	void n() {
 *    KeyLock lock = factory.getKeyLock("myKey")；
 *    if(lock.tryLock()) {
 *      try {
 *        // ...
 *      } finally {
 *        lock.unlock();
 *      }
 *    }
 * 	}
 *
 * }
 * </pre>
 *
 * @author 叶中奇
 */
public class KeyLockFactory {
    private ConcurrentHashMap<String, AtomicLock> lockMap = new ConcurrentHashMap<>(16);

    /**
     * 获取一个锁
     */
    public KeyLock getKeyLock(String key) {
        return new KeyLock(key);
    }

    private AtomicLock createLock(String key) {
        AtomicLock newLock = new AtomicLock();
        while (true) {
            AtomicLock oldLock = lockMap.putIfAbsent(key, newLock);
            if (oldLock == null) {
                return newLock;
            } else {
                synchronized (oldLock) {
                    if (oldLock.count > 0) {
                        oldLock.count++;
                        return oldLock;
                    }
                }
            }
        }
    }

    private void removeLock(String key, AtomicLock lock) {
        synchronized (lock) {
            lock.count--;
            if (lock.count <= 0) {
                lockMap.remove(key);
            }
        }
    }

    public class KeyLock {
        private String key;
        private AtomicLock target;

        private KeyLock(String key) {
            this.key = key;
        }

        /**
         * 加锁
         */
        public synchronized KeyLock lock() {
            if (target != null) {
                throw new IllegalStateException("每次使用KeyLock时请重新构造");
            }
            target = createLock(key);
            target.lock.lock();
            return this;
        }

        /**
         * 尝试加锁
         */
        public synchronized boolean tryLock() {
            if (target != null) {
                throw new IllegalStateException("每次使用KeyLock时请重新构造");
            }
            target = createLock(key);
            if (target.lock.tryLock()) {
                return true;
            } else {
                removeLock(key, target);
                target = null;
                return false;
            }
        }

        /**
         * 解锁
         */
        public synchronized void unlock() {
            if (target != null) {
                target.lock.unlock();
                removeLock(key, target);
                target = null;
            }
        }
    }

    private class AtomicLock {
        private int count = 1;
        private ReentrantLock lock = new ReentrantLock();
    }
}

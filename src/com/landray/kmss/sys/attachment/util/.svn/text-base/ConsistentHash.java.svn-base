/**
 * 
 */
package com.landray.kmss.sys.attachment.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性哈希算法，非常著名的负载均衡算法的实现。
 * 
 * @author 龚健
 * @see
 */
public class ConsistentHash<T> {
	private final HashAlgorithm hashAlgorithm = new HashAlgorithm();
	/** 虚拟节点数 */
	private final int numberOfReplicas;
	/** 用来存储虚拟节点hash值到真实node的映射 */
	private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

	public ConsistentHash(Collection<T> nodes) {
		this(160, nodes);
	}

	public ConsistentHash(int numberOfReplicas, Collection<T> nodes) {
		this.numberOfReplicas = numberOfReplicas;

		for (T node : nodes) {
			add(node);
		}
	}

	public void add(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.put(hashAlgorithm.hash(node.toString() + i), node);
		}
	}

	public void remove(T node) {
		for (int i = 0; i < numberOfReplicas; i++) {
			circle.remove(hashAlgorithm.hash(node.toString() + i));
		}
	}

	/**
	 * 获得一个最近的顺时针节点
	 * 
	 * @param key
	 *            为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
	 * @return
	 */
	public T get(Object key) {
		if (circle.isEmpty()) {
			return null;
		}

		long hash = hashAlgorithm.hash((String) key);
		if (!circle.containsKey(hash)) {
			// 返回此映射的部分视图，其键大于等于hash
			SortedMap<Long, T> tailMap = circle.tailMap(hash);
			// 没找到相应的节点，则返回第一个节点
			hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
		}
		return circle.get(hash);
	}

	public long getSize() {
		return circle.size();
	}

	private class HashAlgorithm {
		private MessageDigest md5 = null;

		public long hash(String key) {
			if (md5 == null) {
				try {
					md5 = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalStateException("no md5 algorythm found");
				}
			}

			md5.reset();
			md5.update(key.getBytes());
			byte[] bKey = md5.digest();
			long res = ((long) (bKey[3] & 0xFF) << 24)
					| ((long) (bKey[2] & 0xFF) << 16)
					| ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
			return res & 0xffffffffL;
		}
	}
}

package com.landray.kmss.sys.portal.cache;

import com.landray.kmss.sys.cluster.interfaces.message.IMessage;

/**
 * 缓存配置消息
 *
 * @author 潘永辉
 * @dataTime 2022年2月12日 上午8:59:07
 */
public class CacheConfigMessage implements IMessage {

	private static final long serialVersionUID = 7084451919732701261L;

	// ================缓存类================
	private String[] modelNames;

	// ================EHCache设置================
	private Integer maxElementsInMemory;

	private Boolean overflowToDisk;

	private Boolean eternal;

	private Long timeToLiveSeconds;

	private Long timeToIdleSeconds;

	// ================群集设置================
	private Integer cacheType;

	public CacheConfigMessage(String... modelNames) {
		this.modelNames = modelNames;
	}

	public String[] getModelNames() {
		return modelNames;
	}

	public Integer getMaxElementsInMemory() {
		return maxElementsInMemory;
	}

	public void setMaxElementsInMemory(Integer maxElementsInMemory) {
		this.maxElementsInMemory = maxElementsInMemory;
	}

	public Boolean getOverflowToDisk() {
		return overflowToDisk;
	}

	public void setOverflowToDisk(Boolean overflowToDisk) {
		this.overflowToDisk = overflowToDisk;
	}

	public Boolean getEternal() {
		return eternal;
	}

	public void setEternal(Boolean eternal) {
		this.eternal = eternal;
	}

	public Long getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}

	public void setTimeToLiveSeconds(Long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}

	public Long getTimeToIdleSeconds() {
		return timeToIdleSeconds;
	}

	public void setTimeToIdleSeconds(Long timeToIdleSeconds) {
		this.timeToIdleSeconds = timeToIdleSeconds;
	}

	public Integer getCacheType() {
		return cacheType;
	}

	public void setCacheType(Integer cacheType) {
		this.cacheType = cacheType;
	}

}

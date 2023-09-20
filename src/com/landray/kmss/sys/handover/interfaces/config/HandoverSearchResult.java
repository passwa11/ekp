package com.landray.kmss.sys.handover.interfaces.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作交接查询结果
 * 
 * @author 缪贵荣
 * 
 */
public class HandoverSearchResult {

	private final String module;

	public String getModule() {
		return module;
	}

	/**
	 * 标题
	 */
	private String moduleMessageKey;

	public String getModuleMessageKey() {
		return moduleMessageKey;
	}

	public void setModuleMessageKey(String moduleMessageKey) {
		this.moduleMessageKey = moduleMessageKey;
	}

	private final String item;

	public String getItem() {
		return item;
	}

	private String itemMessageKey;

	public String getItemMessageKey() {
		return itemMessageKey;
	}

	public void setItemMessageKey(String itemMessageKey) {
		this.itemMessageKey = itemMessageKey;
	}

	/**
	 * 数量
	 */
	private Long total = 0L;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	long incrementAndGet() {
		if (total == null) {
			total = 0L;
		}
		return ++total;
	}

	/**
	 * 工作交接记录集
	 */
	private List<HandoverRecord> handoverRecords;

	public List<HandoverRecord> getHandoverRecords() {
		if (handoverRecords == null) {
			handoverRecords = new ArrayList<HandoverRecord>();
		}
		return handoverRecords;
	}

	public void addHandoverRecord(String id, String url, String[] datas) {
		getHandoverRecords().add(new HandoverRecord(id, url, datas));
	}

	public void addHandoverRecord(String id, String[] datas) {
		getHandoverRecords().add(new HandoverRecord(id, datas));
	}

	public HandoverSearchResult(String module, String item) {
		super();
		this.module = module;
		this.item = item;
	}

}

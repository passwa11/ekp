package com.landray.kmss.sys.handover.interfaces.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作交接执行结果
 * 
 * @author 缪贵荣
 * 
 */
public class HandoverExecuteResult {

	private final String logId;

	public String getLogId() {
		return logId;
	}

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
	 * 成功数量
	 */
	private Long succTotal = 0L;

	public Long getSuccTotal() {
		return succTotal;
	}

	public void setSuccTotal(Long succTotal) {
		this.succTotal = succTotal;
	}

	long succIncrementAndGet() {
		if (succTotal == null) {
			succTotal = 0L;
		}
		return ++succTotal;
	}

	/**
	 * 失败数量
	 */
	private Long failTotal = 0L;

	public Long getFailTotal() {
		return failTotal;
	}

	public void setFailTotal(Long failTotal) {
		this.failTotal = failTotal;
	}

	long failIncrementAndGet() {
		if (failTotal == null) {
			failTotal = 0L;
		}
		return ++failTotal;
	}
	
	/**
	 * 忽略数量
	 */
	private Long ignoreTotal = 0L;

	public Long getIgnoreTotal() {
		return ignoreTotal;
	}

	public void setIgnoreTotal(Long ignoreTotal) {
		this.ignoreTotal = ignoreTotal;
	}

	long ignoreIncrementAndGet() {
		if (ignoreTotal == null) {
			ignoreTotal = 0L;
		}
		return ++ignoreTotal;
	}

	private final Map<String, String> info = new HashMap<String, String>();

	public Map<String, String> getInfo() {
		return info;
	}

	private final Map<String, String> error = new HashMap<String, String>();

	public Map<String, String> getError() {
		return error;
	}

	private Exception exception;

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public HandoverExecuteResult(String module, String item, String logId) {
		super();
		this.module = module;
		this.item = item;
		this.logId = logId;
	}

}

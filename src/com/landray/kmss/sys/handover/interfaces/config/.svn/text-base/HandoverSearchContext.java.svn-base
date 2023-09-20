package com.landray.kmss.sys.handover.interfaces.config;

import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import net.sf.json.JSONObject;

/**
 * 工作交接查询上下文
 * 
 * @author 缪贵荣
 * 
 */
public class HandoverSearchContext {

	/**
	 * 工作交接人
	 */
	private final SysOrgElement handoverOrg;

	public SysOrgElement getHandoverOrg() {
		return handoverOrg;
	}

	/**
	 * 工作接收人
	 */
	private final SysOrgElement recipientOrg;

	public SysOrgElement getRecipientOrg() {
		return recipientOrg;
	}

	/**
	 * 工作交接查询结果
	 */
	private final HandoverSearchResult handoverSearchResult;

	/**
	 * 设置工作交接查询结果数量
	 * 
	 * @param total
	 *            数量
	 */
	public void setTotal(long total) {
		handoverSearchResult.setTotal(total);
	}

	/**
	 * 添加工作交接记录集
	 * 
	 * @param id
	 *            唯一标识
	 * @param url
	 *            链接
	 * @param datas
	 *            数据
	 */
	public void addHandoverRecord(String id, String url, String[] datas) {
		handoverSearchResult.addHandoverRecord(id, url, datas);
		handoverSearchResult.incrementAndGet();
	}

	/**
	 * 添加工作交接记录集
	 * 
	 * @param id
	 *            唯一标识
	 * @param datas
	 *            数据
	 */
	public void addHandoverRecord(String id, String[] datas) {
		handoverSearchResult.addHandoverRecord(id, datas);
		handoverSearchResult.incrementAndGet();
	}

	public String getModule() {
		return handoverSearchResult.getModule();
	}

	public String getItem() {
		return handoverSearchResult.getItem();
	}
	
	public String getItemMessageKey(){
		return handoverSearchResult.getItemMessageKey();
	}

	public HandoverSearchResult getHandoverSearchResult() {
		return handoverSearchResult;
	}

	public HandoverSearchContext(SysOrgElement handoverOrg,
			SysOrgElement recipientOrg, HandoverConfig config) {
		this.handoverOrg = handoverOrg;
		this.recipientOrg = recipientOrg;
		this.handoverSearchResult = new HandoverSearchResult(
				config.getModule(), null);
		this.handoverSearchResult.setModuleMessageKey(config.getMessageKey());
	}

	public HandoverSearchContext(SysOrgElement handoverOrg,
			SysOrgElement recipientOrg, HandoverConfig config,
			HandoverItem item) {
		this.handoverOrg = handoverOrg;
		this.recipientOrg = recipientOrg;
		this.handoverSearchResult = new HandoverSearchResult(
				config.getModule(), item.getItem());
		this.handoverSearchResult.setModuleMessageKey(config.getMessageKey());
		this.handoverSearchResult.setItemMessageKey(item.getItemMessageKey());
	}

	/**
	 * 矩阵组织构造的json
	 */
	private JSONObject sysMatrixJson;

	public JSONObject getSysMatrixJson() {
		return sysMatrixJson;
	}

	public void setSysMatrixJson(JSONObject sysMatrixJson) {
		this.sysMatrixJson = sysMatrixJson;
	}

}

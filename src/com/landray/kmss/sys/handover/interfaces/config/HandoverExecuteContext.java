package com.landray.kmss.sys.handover.interfaces.config;

import java.util.Collections;
import java.util.List;

import com.landray.kmss.sys.handover.interfaces.HandoverContext;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogDetailService;
import com.landray.kmss.sys.handover.service.spring.HandoverPluginUtils.HandoverConfig;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 工作交接执行上下文
 * 
 * @author 缪贵荣
 * 
 */
public class HandoverExecuteContext extends HandoverContext {

	/**
	 * 选择的交接记录集
	 */
	private final List<String> selectedRecordIds;

	public List<String> getSelectedRecordIds() {
		if (selectedRecordIds != null) {
			return Collections.unmodifiableList(selectedRecordIds);
		} else {
			return null;
		}
	}
	
	/**
	 * 选择的交接流程集
	 */
	private List<String> selectedProcessIds = null;

	public List<String> getSelectedProcessIds() {
		if (selectedProcessIds != null) {
			return Collections.unmodifiableList(selectedProcessIds);
		} else {
			return null;
		}
	}

	public void setSelectedProcessIds(List<String> selectedProcessIds) {
		this.selectedProcessIds = selectedProcessIds;
	}

	/**
	 * 选择的交接节点编号集
	 */
	private List<String> selectedFactIds = null;

	public List<String> getSelectedFactIds() {
		if (selectedFactIds != null) {
			return Collections.unmodifiableList(selectedFactIds);
		} else {
			return null;
		}
	}

	public void setSelectedFactIds(List<String> selectedFactIds) {
		this.selectedFactIds = selectedFactIds;
	}

	private final HandoverExecuteResult handoverExecuteResult;

	private ISysHandoverConfigLogDetailService sysHandoverConfigLogDetailService;

	/**
	 * 设置工作交接执行结果数量
	 * 
	 * @param total
	 *            数量
	 */
	public void setSuccTotal(long total) {
		handoverExecuteResult.setSuccTotal(total);
	}
	
	public long getSuccTotal(){
		return handoverExecuteResult.getSuccTotal();
	}
	
	/**
	 * 设置忽略的数量
	 * @param total
	 */
	public void setIgnoreTotal(long total) {
		handoverExecuteResult.setIgnoreTotal(total);
	}
	
	public long getIgnoreTotal(){
		return handoverExecuteResult.getIgnoreTotal();
	}

	public void log(String modelId, String modelName, String desc, String fdUrl, String factId, Integer fdState) {
		if (SysHandoverConfigLogDetail.STATE_SUCC.equals(fdState)) {
			// 成功总数++
			handoverExecuteResult.succIncrementAndGet();
		} else {
			handoverExecuteResult.ignoreIncrementAndGet();
		}
		// 添加明细日志
		getSysHandoverConfigLogDetailService().add(handoverExecuteResult.getLogId(), modelId, modelName, desc, fdUrl, factId, fdState);
	}

	public String getModule() {
		return handoverExecuteResult.getModule();
	}

	public String getItem() {
		return handoverExecuteResult.getItem();
	}

	public String getItemMessageKey(){
		return handoverExecuteResult.getItemMessageKey();
	}
	
	public void info(String id, String desc) {
		handoverExecuteResult.getInfo().put(id, desc);
	}

	public void error(String id, String desc) {
		handoverExecuteResult.getError().put(id, desc);
	}

	public HandoverExecuteResult getHandoverExecuteResult() {
		return handoverExecuteResult;
	}

	public HandoverExecuteContext(SysOrgElement from, SysOrgElement to,
			List<String> selectedRecordIds, HandoverConfig config, String logId) {
		super(from, to);
		this.selectedRecordIds = selectedRecordIds;
		this.handoverExecuteResult = new HandoverExecuteResult(
				config.getModule(), null, logId);
		this.handoverExecuteResult.setModuleMessageKey(config.getMessageKey());
	}

	public HandoverExecuteContext(SysOrgElement from, SysOrgElement to,
			List<String> selectedRecordIds, HandoverConfig config,
			HandoverItem item, String logId) {
		super(from, to);
		this.selectedRecordIds = selectedRecordIds;
		this.handoverExecuteResult = new HandoverExecuteResult(
				config.getModule(), item.getItem(), logId);
		this.handoverExecuteResult.setModuleMessageKey(config.getMessageKey());
		this.handoverExecuteResult.setItemMessageKey(item.getItemMessageKey());
	}

	private ISysHandoverConfigLogDetailService getSysHandoverConfigLogDetailService() {
		if (sysHandoverConfigLogDetailService == null) {
			sysHandoverConfigLogDetailService = (ISysHandoverConfigLogDetailService) SpringBeanUtil
					.getBean("sysHandoverConfigLogDetailService");
		}
		return sysHandoverConfigLogDetailService;
	}

	/**
	 * 事项交接模式：替换交接人(0), 追加交接人(1)
	 */
	private Integer execMode;

	public Integer getExecMode() {
		return execMode;
	}

	public void setExecMode(Integer execMode) {
		this.execMode = execMode;
	}

}

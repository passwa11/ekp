package com.landray.kmss.sys.handover.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.handover.forms.SysHandoverConfigLogForm;

/**
 * 配置类工作交接日志
 * 
 * @author 缪贵荣
 * 
 */
@SuppressWarnings("serial")
public class SysHandoverConfigLog extends BaseModel {

	/**
	 * 工作交接模块（例如：组织架构、系统权限）
	 */
	private String fdModule;

	public String getFdModule() {
		return fdModule;
	}

	public void setFdModule(String fdModule) {
		this.fdModule = fdModule;
	}

	/**
	 * 工作交接模块（例如：组织架构、系统权限）
	 */
	private String fdModuleName;

	public String getFdModuleName() {
		return fdModuleName;
	}

	public void setFdModuleName(String fdModuleName) {
		this.fdModuleName = fdModuleName;
	}

	/**
	 * 工作交接项
	 */
	private String fdItem;

	public String getFdItem() {
		return fdItem;
	}

	public void setFdItem(String fdItem) {
		this.fdItem = fdItem;
	}

	/**
	 * 工作交接项名称
	 */
	private String fdItemName;

	public String getFdItemName() {
		return fdItemName;
	}

	public void setFdItemName(String fdItemName) {
		this.fdItemName = fdItemName;
	}

	/**
	 * 状态（待执行/执行中/暂停/已完成）
	 * 
	 * @see STATUS_WAITTING STATUS_RUNNING STATUS_SUSPENDED STATUS_ENDED
	 */
	private Integer fdStatus = 0;

	public Integer getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 数量
	 */
	private Long fdCount;

	public Long getFdCount() {
		return fdCount;
	}

	public void setFdCount(Long fdCount) {
		this.fdCount = fdCount;
	}
	
	/**
	 * 忽略的数量
	 */
	private Long fdIgnoreCount;

	public Long getFdIgnoreCount() {
		return fdIgnoreCount;
	}

	public void setFdIgnoreCount(Long fdIgnoreCount) {
		this.fdIgnoreCount = fdIgnoreCount;
	}

	/**
	 * 是否成功
	 */
	private Boolean fdIsSucc = new Boolean(true);

	public Boolean getFdIsSucc() {
		return fdIsSucc;
	}

	public void setFdIsSucc(Boolean fdIsSucc) {
		this.fdIsSucc = fdIsSucc;
	}

	/**
	 * 日志
	 */
	private SysHandoverConfigMain fdMain;

	public SysHandoverConfigMain getFdMain() {
		return fdMain;
	}

	public void setFdMain(SysHandoverConfigMain fdMain) {
		this.fdMain = fdMain;
	}

	/**
	 * 开始时间
	 */
	private Date fdStartTime = new Date();

	public Date getFdStartTime() {
		return fdStartTime;
	}

	public void setFdStartTime(Date fdStartTime) {
		this.fdStartTime = fdStartTime;
	}

	/**
	 * 结束时间
	 */
	private Date fdEndedTime;

	public Date getFdEndedTime() {
		return fdEndedTime;
	}

	public void setFdEndedTime(Date fdEndedTime) {
		this.fdEndedTime = fdEndedTime;
	}

	@Override
    public Class<?> getFormClass() {
		return SysHandoverConfigLogForm.class;
	}

	/**
	 * 明细日志
	 */
	private List<SysHandoverConfigLogDetail> conLogDetails;

	public List<SysHandoverConfigLogDetail> getConLogDetails() {
		return conLogDetails;
	}

	public void setConLogDetails(List<SysHandoverConfigLogDetail> conLogDetails) {
		this.conLogDetails = conLogDetails;
	}
}

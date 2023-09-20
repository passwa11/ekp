package com.landray.kmss.sys.handover.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.handover.forms.SysHandoverConfigMainForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzModel;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 配置类工作交接
 * 
 * @author 缪贵荣
 * 
 */
@SuppressWarnings("serial")
public class SysHandoverConfigMain extends BaseModel implements
		ISysQuartzModel, InterceptFieldEnabled {

	/**
	 * 工作交接人Id
	 */
	private String fdFromId;

	public String getFdFromId() {
		return fdFromId;
	}

	public void setFdFromId(String fdFromId) {
		this.fdFromId = fdFromId;
	}

	/**
	 * 工作交接人Name
	 */
	private String fdFromName;

	public String getFdFromName() {
		return fdFromName;
	}

	public void setFdFromName(String fdFromName) {
		this.fdFromName = fdFromName;
	}

	/**
	 * 工作接收人Id
	 */
	private String fdToId;

	public String getFdToId() {
		return fdToId;
	}

	public void setFdToId(String fdToId) {
		this.fdToId = fdToId;
	}

	/**
	 * 工作接收人Name
	 */
	private String fdToName;

	public String getFdToName() {
		return fdToName;
	}

	public void setFdToName(String fdToName) {
		this.fdToName = fdToName;
	}

	/**
	 * 创建人
	 */
	private SysOrgElement docCreator;

	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 创建时间
	 */
	private Date docCreateTime = new Date();

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 类型：模块配置(1), 文档流程实例(2), 文档权限(3), 事项交接(4). 兼容老数据，null为配置类
	 */
	private Integer handoverType;

	public Integer getHandoverType() {
		return handoverType;
	}

	public void setHandoverType(Integer handoverType) {
		this.handoverType = handoverType;
	}

	/*
	 * 文档类交接内容
	 */
	private String fdContent;

	public String getFdContent() {
		return (String) readLazyField("fdContent", fdContent);
	}

	public void setFdContent(String fdContent) {
		this.fdContent = (String) writeLazyField("fdContent", this.fdContent,
				fdContent);
	}

	/*
	 * 执行状态【0：等待执行，1：执行成功，2：执行失败，3：执行中】
	 */
	private Integer fdState;

	public Integer getFdState() {
		return fdState;
	}

	public void setFdState(Integer fdState) {
		this.fdState = fdState;
	}

	/*
	 * 文档处理成功条数
	 */
	private Long total;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Override
    public Class<?> getFormClass() {
		return SysHandoverConfigMainForm.class;
	}

	/**
	 * 明细日志
	 */
	private List<SysHandoverConfigLog> configLogs;

	public List<SysHandoverConfigLog> getConfigLogs() {
		return configLogs;
	}

	public void setConfigLogs(List<SysHandoverConfigLog> configLogs) {
		this.configLogs = configLogs;
	}

	/**
	 * 文档权限明细日志
	 */
	private List<SysHandoverConfigAuthLogDetail> configAuthLogs;

	public List<SysHandoverConfigAuthLogDetail> getConfigAuthLogs() {
		return configAuthLogs;
	}

	public void
			setConfigAuthLogs(
					List<SysHandoverConfigAuthLogDetail> configAuthLogs) {
		this.configAuthLogs = configAuthLogs;
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

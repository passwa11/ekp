package com.landray.kmss.sys.handover.model;

import java.util.Date;

import com.landray.kmss.common.model.BaseModel;

/**
 * 文档权限交接明细
 * 
 * @author 潘永辉 2017年7月12日
 *
 */
public class SysHandoverConfigAuthLogDetail extends BaseModel {
	private static final long serialVersionUID = -6784819845290765275L;

	// 文档ID
	private String fdModelId;
	// 模块全类名
	private String fdModelName;
	// 模块名称
	private String fdModelMessageKey;
	// 文档标题
	private String fdModelSubject;
	// 文档URL
	public String fdModelUrl;

	// 交接记录
	private SysHandoverConfigMain fdMain;
	// 创建时间
	private Date docCreateTime = new Date();
	// 交接权限
	private String fdAuthType;
	// 节点名称（限：流程意见阅读权限）
	private String fdNodeName;

	@Override
	public Class<?> getFormClass() {
		return null;
	}

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdModelMessageKey() {
		return fdModelMessageKey;
	}

	public void setFdModelMessageKey(String fdModelMessageKey) {
		this.fdModelMessageKey = fdModelMessageKey;
	}

	public String getFdModelSubject() {
		return fdModelSubject;
	}

	public void setFdModelSubject(String fdModelSubject) {
		this.fdModelSubject = fdModelSubject;
	}

	public String getFdModelUrl() {
		return fdModelUrl;
	}

	public void setFdModelUrl(String fdModelUrl) {
		this.fdModelUrl = fdModelUrl;
	}

	public SysHandoverConfigMain getFdMain() {
		return fdMain;
	}

	public void setFdMain(SysHandoverConfigMain fdMain) {
		this.fdMain = fdMain;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getFdAuthType() {
		return fdAuthType;
	}

	public void setFdAuthType(String fdAuthType) {
		this.fdAuthType = fdAuthType;
	}

	public String getFdNodeName() {
		return fdNodeName;
	}

	public void setFdNodeName(String fdNodeName) {
		this.fdNodeName = fdNodeName;
	}

}

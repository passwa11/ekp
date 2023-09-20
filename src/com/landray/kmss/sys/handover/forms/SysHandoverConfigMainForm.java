package com.landray.kmss.sys.handover.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;

/**
 * 日志主文档 Form
 * 
 * @author
 * @version 1.0 2014-07-22
 */
@SuppressWarnings("serial")
public class SysHandoverConfigMainForm extends ExtendForm {

	/**
	 * 交接人ID
	 */
	protected String fdFromId = null;

	/**
	 * @return 交接人ID
	 */
	public String getFdFromId() {
		return fdFromId;
	}

	/**
	 * @param fdFromId
	 *            交接人ID
	 */
	public void setFdFromId(String fdFromId) {
		this.fdFromId = fdFromId;
	}

	/**
	 * 交接人
	 */
	protected String fdFromName = null;

	/**
	 * @return 交接人
	 */
	public String getFdFromName() {
		return fdFromName;
	}

	/**
	 * @param fdFromName
	 *            交接人
	 */
	public void setFdFromName(String fdFromName) {
		this.fdFromName = fdFromName;
	}

	/**
	 * 接收人ID
	 */
	protected String fdToId = null;

	/**
	 * @return 接收人ID
	 */
	public String getFdToId() {
		return fdToId;
	}

	/**
	 * @param fdToId
	 *            接收人ID
	 */
	public void setFdToId(String fdToId) {
		this.fdToId = fdToId;
	}

	/**
	 * 接收人
	 */
	protected String fdToName = null;

	/**
	 * @return 接收人
	 */
	public String getFdToName() {
		return fdToName;
	}

	/**
	 * @param fdToName
	 *            接收人
	 */
	public void setFdToName(String fdToName) {
		this.fdToName = fdToName;
	}

	/**
	 * 创建者
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	// 类型 1表示文档类，0表示配置类，默认为0，兼容老数据，null为配置类
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
		return fdContent;
	}

	public void setFdContent(String fdContent) {
		this.fdContent = fdContent;
	}
	
	/*
	 * 执行状态
	 */
	private Integer fdState;
	
	public Integer getFdState() {
		return fdState;
	}

	public void setFdState(Integer fdState) {
		this.fdState = fdState;
	}

	/*
	 * 文档已经处理总数
	 */
	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdFromId = null;
		fdFromName = null;
		fdToId = null;
		fdToName = null;
		docCreatorId = null;
		docCreateTime = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<?> getModelClass() {
		return SysHandoverConfigMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
		}
		return toModelPropertyMap;
	}
}

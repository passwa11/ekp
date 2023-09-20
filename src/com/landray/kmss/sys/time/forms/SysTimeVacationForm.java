package com.landray.kmss.sys.time.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽
 */
public class SysTimeVacationForm extends ExtendForm {
	/*
	 * 名称
	 */
	private String fdName = null;

	/*
	 * 所属区域组
	 */
	private String sysTimeAreaId = null;

	/*
	 * 开始日期
	 */
	private String fdStartDate = null;

	/*
	 * 结束日期
	 */
	private String fdEndDate = null;

	/*
	 * 开始时间
	 */
	private String fdStartTime = null;

	/*
	 * 结束时间
	 */
	private String fdEndTime = null;

	/*
	 * 创建人ID
	 */
	private String docCreatorId = null;

	/*
	 * 创建人姓名
	 */
	private String docCreatorName = null;

	/*
	 * 创建时间
	 */
	private String docCreateTime = null;

	/**
	 * @return 返回 名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            要设置的 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * @return 返回 所属区域组
	 */
	public String getSysTimeAreaId() {
		return sysTimeAreaId;
	}

	/**
	 * @param fdAreaId
	 *            要设置的 所属区域组
	 */
	public void setSysTimeAreaId(String fdAreaId) {
		this.sysTimeAreaId = fdAreaId;
	}

	public String getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(String fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdStartDate() {
		return fdStartDate;
	}

	public void setFdStartDate(String fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * @return 返回 开始时间
	 */
	public String getFdStartTime() {
		return fdStartTime;
	}

	/**
	 * @param fdStartTime
	 *            要设置的 开始时间
	 */
	public void setFdStartTime(String fdStartTime) {
		if (!StringUtil.isNull(fdStartTime)) {
			this.fdStartTime = fdStartTime;
		} else {
			this.fdStartTime = "00:00:00";
		}
	}

	/**
	 * @return 返回 结束时间
	 */
	public String getFdEndTime() {
		return fdEndTime;
	}

	/**
	 * @param fdEndTime
	 *            要设置的 结束时间
	 */
	public void setFdEndTime(String fdEndTime) {
		if (!StringUtil.isNull(fdEndTime)) {
			this.fdEndTime = fdEndTime;
		} else {
			this.fdEndTime = "23:59:59";
		}
	}

	/**
	 * @return 返回 创建人
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            要设置的 创建人
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * @return 返回 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		sysTimeAreaId = null;
		fdStartDate = null;
		fdEndDate = null;
		fdStartTime = null;
		fdEndTime = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return SysTimeVacation.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("sysTimeAreaId",
					new FormConvertor_IDToModel("sysTimeArea",
							SysTimeArea.class));
			toModelPropertyMap.put("fdStartDate",
					new FormConvertor_Common("fdStartDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdEndDate",
					new FormConvertor_Common("fdEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdStartTime",
					new FormConvertor_Common("fdStartTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
			toModelPropertyMap.put("fdEndTime",
					new FormConvertor_Common("fdEndTime")
							.setDateTimeType(DateUtil.TYPE_TIME));
		}
		return toModelPropertyMap;
	}

}

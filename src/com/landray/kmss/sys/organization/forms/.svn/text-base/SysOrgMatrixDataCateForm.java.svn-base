package com.landray.kmss.sys.organization.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgMatrix;
import com.landray.kmss.sys.organization.model.SysOrgMatrixDataCate;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 矩阵数据分组
 * 
 * @author panyh
 * @date 2020-05-11
 *
 */
public class SysOrgMatrixDataCateForm extends ExtendForm implements Comparable<SysOrgMatrixDataCateForm> {
	private static final long serialVersionUID = 1L;

	/**
	 * 类别名称
	 */
	private String fdName;

	/**
	 * 所属矩阵
	 */
	private String fdMatrixId;
	private String fdMatrixName;

	/**
	 * 创建时间
	 */
	private Date fdCreateTime = new Date();

	/**
	 * 可维护者
	 */
	protected String fdElementId;
	protected String fdElementName;

	/**
	 * 排序号
	 */
	private Integer fdOrder;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdMatrixId() {
		return fdMatrixId;
	}

	public void setFdMatrixId(String fdMatrixId) {
		this.fdMatrixId = fdMatrixId;
	}

	public String getFdMatrixName() {
		return fdMatrixName;
	}

	public void setFdMatrixName(String fdMatrixName) {
		this.fdMatrixName = fdMatrixName;
	}

	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	public String getFdElementId() {
		return fdElementId;
	}

	public void setFdElementId(String fdElementId) {
		this.fdElementId = fdElementId;
	}

	public String getFdElementName() {
		return fdElementName;
	}

	public void setFdElementName(String fdElementName) {
		this.fdElementName = fdElementName;
	}

	@Override
	public Class<?> getModelClass() {
		return SysOrgMatrixDataCate.class;
	}

	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdMatrixId = null;
		fdMatrixName = null;
		fdOrder = null;
		fdElementId = null;
		fdElementName = null;
		fdCreateTime = new Date();
		super.reset(mapping, request);
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.put("fdMatrixId", new FormConvertor_IDToModel("fdMatrix", SysOrgMatrix.class));
			toModelPropertyMap.put("fdElementId", new FormConvertor_IDToModel("fdElement", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public int compareTo(SysOrgMatrixDataCateForm o) {
		int i1 = this.getFdOrder() == null ? 999 : this.getFdOrder();
		int i2 = o.getFdOrder() == null ? 999 : o.getFdOrder();
		return i1 - i2;
	}

	/**
	 * 分组数量
	 */
	private long count;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}

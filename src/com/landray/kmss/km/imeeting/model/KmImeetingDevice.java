package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingDeviceForm;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 会议辅助服务
 * 
 */
public class KmImeetingDevice extends BaseAuthModel {

	/**
	 * 设备名称
	 */
	protected String fdName;

	/**
	 * @return 设备名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            设备名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 是否有效
	 */
	protected Boolean fdIsAvailable = new Boolean(true);

	/**
	 * @return 是否有效
	 */
	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	/**
	 * @param fdIsAvailable
	 *            是否有效
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}


	@Override
	public Class getFormClass() {
		return KmImeetingDeviceForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

	@Override
	protected void recalculateEditorField() {
		// 重新计算可编辑者
		if (authAllEditors == null) {
            authAllEditors = new ArrayList();
        } else {
            authAllEditors.clear();
        }

		String tmpStatus = getDocStatus();
		authAllEditors.add(getDocCreator());

		List tmpList = getAuthOtherEditors();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllEditors);
		}

		if (StringUtil.isNotNull(tmpStatus) && tmpStatus.charAt(0) >= '3') {
			tmpList = getAuthEditors();
			if (tmpList != null) {
				ArrayUtil.concatTwoList(tmpList, authAllEditors);
			}
		}
	}
	
	@Override
	public String getDocSubject() {
		return null;
	}

	@Override
	public String getDocStatus() {
		return "30";
	}
}

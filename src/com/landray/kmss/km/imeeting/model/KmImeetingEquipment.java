package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingEquipmentForm;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;



/**
 * 会议辅助设备
 * 
 * @author 
 * @version 1.0 2016-01-25
 */
public class KmImeetingEquipment  extends BaseAuthModel {

	/**
	 * 设备名称
	 */
	private String fdName;
	
	/**
	 * @return 设备名称
	 */
	public String getFdName() {
		return this.fdName;
	}
	
	/**
	 * @param fdName 设备名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	
	/**
	 * 排序号
	 */
	private Integer fdOrder;
	
	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return this.fdOrder;
	}
	
	/**
	 * @param fdOrder 排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}	
	
	/**
	 * 是否有效
	 */
	private Boolean fdIsAvailable;
	
	/**
	 * @return 是否有效
	 */
	public Boolean getFdIsAvailable() {
		return this.fdIsAvailable;
	}
	
	/**
	 * @param fdIsAvailable 是否有效
	 */
	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}
		

	//机制开始
	//机制结束

	@Override
	public Class<KmImeetingEquipmentForm> getFormClass() {
		return KmImeetingEquipmentForm.class;
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

package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.attend.model.SysAttendOutPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议签到，记录EKP外部人员的信息
 *
 * @author cuiwj
 * @version 1.0 2018-08-15
 */
public class SysAttendOutPersonForm extends ExtendForm {

	/**
	 * 姓名
	 */
	private String fdName;

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 手机号
	 */
	private String fdPhoneNum;

	public String getFdPhoneNum() {
		return fdPhoneNum;
	}

	public void setFdPhoneNum(String fdPhoneNum) {
		this.fdPhoneNum = fdPhoneNum;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdPhoneNum = null;
		super.reset(mapping, request);
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

	@Override
	public Class getModelClass() {
		return SysAttendOutPerson.class;
	}

}

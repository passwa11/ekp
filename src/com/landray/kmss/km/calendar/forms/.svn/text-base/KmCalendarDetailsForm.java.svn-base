package com.landray.kmss.km.calendar.forms;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.calendar.model.KmCalendarDetails;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * 日程相关人详情表
 * 
 * @author
 * @version 1.0 2021.6.15
 */
@SuppressWarnings("serial")
public class KmCalendarDetailsForm extends ExtendForm {
	private static FormToModelPropertyMap toModelPropertyMap;
	private String fdCalendarId; //所属日程ID
	private String fdCalendarName; //所属日程
	private String fdPersonId;//日程相关人ID
	private String fdPersonName;//日程相关人
	private Boolean fdIsDelete; //是否删除
	private String fdLabelId; //所属标签

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdCalendarId=null;
		fdCalendarName=null;
		fdPersonId=null;
		fdPersonName=null;
		fdIsDelete=null;
		fdLabelId=null;
		super.reset(mapping, request);
	}

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdCalendarId", new FormConvertor_IDToModel("fdCalendar", KmCalendarMain.class));
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	@Override
	public Class getModelClass() {
		return KmCalendarDetails.class;
	}
}

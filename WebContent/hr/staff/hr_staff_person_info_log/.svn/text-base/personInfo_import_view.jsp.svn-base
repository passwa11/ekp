<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:content expand="false" title="${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') }">
	<list:listview channel="hrStaffPersonInfoLog">
		<ui:source type="AjaxJson">
			{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list&personInfoId=${ JsParam.personInfoId }'}
		</ui:source>
		<list:colTable isDefault="false" layout="sys.ui.listview.columntable" channel="hrStaffPersonInfoLog"
			rowHref="/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=view&fdId=!{fdId}" name="columntable">
			<list:col-serial></list:col-serial> 
			<list:col-auto props="fdCreateTime;fdIp;fdBrowser;fdEquipment;fdCreator;fdParaMethod;fdDetails"></list:col-auto>
		</list:colTable>
	</list:listview> 
	<list:paging channel="hrStaffPersonInfoLog" />
</ui:content>

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="lui-personnel-file-staffInfo" id="staffDynamic">
      <div class="lui-personnel-file-header-title">
         <div class="lui-personnel-file-header-title-left">
           <div class="lui-personnel-file-header-title-text">${ lfn:message('hr-staff:porlet.employee.dynamic') }</div>
         </div>
       </div>
      <div class="newTableList">
			<list:listview channel="hrStaffPersonInfoLog">
				<ui:source type="AjaxJson">
					{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=list&personInfoId=${ JsParam.personInfoId }'}
				</ui:source>
				<list:colTable isDefault="false" layout="sys.ui.listview.columntable" channel="hrStaffPersonInfoLog"
					rowHref="/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=view&fdId=!{fdId}" name="columntable">
					<list:col-auto props="fdCreateTime;fdIp;fdBrowser;fdEquipment;fdCreator;fdParaMethod;fdDetails"></list:col-auto>
				</list:colTable>
			</list:listview> 
			<list:paging channel="hrStaffPersonInfoLog" />
	  </div>
	  
	  
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
{$
	<div class="lui_hr_box_2">
		<a class="lui_hr_img" 
		   target="_blank" onclick="Com_OpenNewWindow(this)"
		   data-href="${LUI_ContextPath }/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={%grid['fdId']%}"> 
			<img  src="{% grid['imgUrl']%}" onload="javascript:drawImage(this,this.parentNode)">
		</a>
		<ul>
			<li><input type="checkbox" data-lui-mark="table.content.checkbox" value="{% grid['fdId']%}" name="List_Selected"/>
			<input type="hidden" value="{% grid['fdIsAvailable']%}" name="fdIsAvailable"/>
				<a target="_blank" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath }/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={%grid['fdId']%}"> 
					<span class="com_author">{%grid['fdName']%}</span>
				</a>
			</li>
			<li title="{% grid['fdDeptName']%}" >${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent')}：{% grid['fdDeptName'] %}</li>
$}				
	if("" != grid['fdStaffNo'] && null !=grid['fdStaffNo'] && "" != grid['fdLoginName'] && null !=grid['fdLoginName']){
		{$
			<li title="{% grid['fdLoginName']%}|{% grid['fdStaffNo']%}">${lfn:message('hr-staff:hrStaffPersonInfo.CountOrNo')}：{% grid['fdLoginName'] %}|{% grid['fdStaffNo']%}</li>
		$}
	}else{
		{$
			<li title="{% grid['fdLoginName']%}{% grid['fdStaffNo']%}">${lfn:message('hr-staff:hrStaffPersonInfo.CountOrNo')}：{% grid['fdLoginName'] %}{% grid['fdStaffNo']%}</li>
		$}
	}
			{$  
				<li title="{% grid['fdTimeOfEnterprise']%}">${lfn:message('hr-staff:hrStaffPersonInfo.fdTimeOfEnterprise')}：{% grid['fdTimeOfEnterprise']%}</li>
				<li class="lui_ask_btn">
					<a href="javascript:void(0)" class="lui_hr_askBtn_2  com_bgcolor_n com_fontcolor_n com_bordercolor_n"  onclick="editStaffInfo('{%grid['fdId']%}')">
					<span>${lfn:message('hr-staff:hr.staff.btn.edit')}</span>
					</a>
				</li>
		 	$}
{$
	    </ul>					
	</div>
$}
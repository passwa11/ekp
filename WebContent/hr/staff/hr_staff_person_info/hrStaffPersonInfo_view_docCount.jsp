<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:dataview>
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getDocCount&personInfoId=${JsParam.personInfoId}'}
	</ui:source>
	<ui:render type="Template">
	if(data.length > 0) {
		for(var i=0; i<data.length; i++){
			{$
			<li>
			$}   
			if(data[i].isSelf){
				{$
					<a href="${LUI_ContextPath}{%data[i].link%}" target="_blank"><em>{%data[i].num%}</em><span>{%data[i].title%}</span></a>
				$}
			}else{
				{$
					<div><em>{%data[i].num%}</em><span>{%data[i].title%}</span></div>
				$}
			}
			{$
			</li>
			$}
		}
	}
	</ui:render>
</ui:dataview>

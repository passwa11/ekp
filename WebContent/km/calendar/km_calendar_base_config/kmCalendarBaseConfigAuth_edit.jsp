<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<script type="text/javascript">
	window.onload = function(){
		var _deptCanRead=document.getElementsByName("_deptCanRead")[0];
		var deptCanRead=document.getElementsByName("deptCanRead")[0];
		if(deptCanRead.value=="false"){
			_deptCanRead.checked=false;
		}else{
			_deptCanRead.checked=true;
		}
	};
	function changeValue(thisObj){
		var _deptCanRead=document.getElementsByName("_deptCanRead")[0];
		var deptCanRead=document.getElementsByName("deptCanRead")[0];
		if(_deptCanRead.checked){
			deptCanRead.value="true";
		}else{
			deptCanRead.value="false";
		}
	}
</script>
<html:form action="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do">
<div style="margin-top:25px">
<p class="configtitle">
	<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.auth.set"/>
</p>
<center>
<table class="tb_normal" width=90%>
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.auth.readerSetting"/>
		</td>
		<td colspan="3">
			<html:hidden property="deptCanRead" />
			<input name="_deptCanRead" type="checkbox" onclick="changeValue(this);"/>
				<bean:message bundle="km-calendar" key="kmCalendarBaseConfig.auth.readerSetting.text"/>
		</td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmCalendarBaseConfigForm, 'updateAuthConfig');" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
</template:replace>
</template:include>

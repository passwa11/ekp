<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<script language="JavaScript">
	Com_IncludeFile("calendar.js");
</script>
<script type="text/javascript">
	function checkData(){
		var day = document.getElementsByName("fdKeepDay")[0];
		if(day!=null){
			if(isNaN(day.value)||day.value<=0){
				alert("<bean:message key="kmCalendarBaseConfig.fdKeepDay.number.validate" bundle="km-calendar"/>");
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
</script>
<html:form action="/km/calendar/km_calendar_base_config/kmCalendarBaseConfig.do" onsubmit="return checkData();">
<div style="margin-top:25px">
<p class="configtitle">
<c:if test="${!empty type and type=='day'}">
	<bean:message bundle="km-calendar" key="table.kmCalendarBaseConfigKeepTime"/>
</c:if>
<c:if test="${!empty type and type=='time'}">
	<bean:message bundle="km-calendar" key="table.kmCalendarBaseConfigSetTime"/>
</c:if>
</p>
<center>
<table class="tb_normal" width=90%>
	<input type="hidden" value="${type }" name="type">
	<input type="text" style="display:none"/>
	<c:if test="${!empty type and type=='day'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="km-calendar" key="kmCalendarBaseConfig.fdKeepDay"/>
			 </td>
			 <td colspan=3>
				 <xform:text property="fdKeepDay" validators="required digits min(0)"></xform:text><span class="txtstrong">*</span>
				<span id='dateDescription'>
				<br><font color="red" ><bean:message  bundle="km-calendar" key="kmCalendarType.dateDescription"/></font></span>
			</td>
		</tr>
	</c:if>
	<c:if test="${!empty type and type=='time'}">
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="km-calendar" key="kmCalendarBaseConfig.fdStartTime"/>
			</td>
			<td width=35%>
				<html:text property="fdStartTime" readonly="true" styleClass="inputsgl" style="width:85%"/>
				<a href="#" onclick="selectTime('fdStartTime');"><bean:message key="dialog.selectTime"/></a>
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="km-calendar" key="kmCalendarBaseConfig.fdEndTime"/>
			</td><td width=35%>
				<html:text property="fdEndTime" readonly="true" styleClass="inputsgl" style="width:85%"/>
				<a href="#" onclick="selectTime('fdEndTime');"><bean:message key="dialog.selectTime"/></a>
			</td>
		</tr>
	</c:if>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmCalendarBaseConfigForm, 'update');" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
	<script type="text/javascript">
		$KMSSValidation();
	</script>
</template:replace>
</template:include>

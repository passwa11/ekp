<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date,java.util.Calendar,com.landray.kmss.util.*"%>
<%
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(new Date());
	calendar.add(Calendar.MONTH, -1);//月份减一
	String startDate=DateUtil.convertDateToString(calendar.getTime(),DateUtil.TYPE_DATE,request.getLocale());
%>
<template:include ref="default.edit" sidebar="no" >
	<template:replace name="title">
		<bean:message key="kmCalendarAuth.invite" bundle="km-calendar"/> - <bean:message key="module.km.calendar" bundle="km-calendar"/>
	</template:replace>

	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<c:if test="${isConfirm == 'false' }">
			<%-- 不同意 --%>
			<ui:button text="${lfn:message('km-calendar:kmCalendarRquestAuth.authRequest.no') }" order="1" onclick="confirmRequestNo();"></ui:button>
			<%-- 同意 --%>
			<ui:button text="${lfn:message('km-calendar:kmCalendarRquestAuth.authRequest.yes') }" order="2" onclick="confirmRequest();"></ui:button>
			</c:if>
			<%-- 关闭 --%>
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-calendar:module.km.calendar') }" href="/km/calendar/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-calendar:kmCalendarAuth.invite') }"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<template:replace name="content"> 
		<html:form action="/km/calendar/km_calendar_auth/kmCalendarAuth.do">
			<html:hidden property="fdRequestAuthId"  value="${HtmlParam['fdRequestAuthId'] }"/>
			<input name="authEdit" type="hidden" value="false"/>
			<input name="authModify" type="hidden" value="false"/>
			<input name="authRead" type="hidden" value="false"/>
			<p class="txttitle">
				<bean:message key="kmCalendarAuth.invite" bundle="km-calendar"/>
			</p>
			<div class="lui_form_content_frame" style="padding-top:5px">
			<table class="tb_normal" width="100%;">
				<tr>
					<td class="" colspan="3">
						'<ui:person personId="${ kmCalendarRequestAuth.docCreator.fdId}"></ui:person>'
						<span><bean:message key="kmCalendarRquestAuth.authRequest" bundle="km-calendar"/></span>
					</td>
				</tr>
				<tr>
					<td class="" width=15%>
						<bean:message key="kmCalendarAuth.authScope" bundle="km-calendar"/>
					</td>
					<td width="85%" colspan="2" >
						<input type="hidden" name="fdRequestAuth" value="${ kmCalendarRequestAuth.fdRequestAuth}"/>
    					<input id="authRead" type="checkbox" value="authRead" disabled="disabled" checked="checked"/><bean:message key="kmCalendarAuth.authScope.authRead" bundle="km-calendar"/><br/>
    					<input id="authEdit" type="checkbox" onclick="changeFdRequestAuth(this);" value="authEdit"/><bean:message key="kmCalendarAuth.authScope.authEdit" bundle="km-calendar"/><br/>
    					<input id="authModify" type="checkbox" onclick="changeFdRequestAuth(this);" value="authModify"/><bean:message key="kmCalendarAuth.authScope.authModify" bundle="km-calendar"/>
					</td>
				</tr>
				<tr>
		    		<td colspan="3" width="100%">
		    			<input type="checkbox" name="updateCalendar" checked="checked" onclick="clickCheckbox(this)"/>${lfn:message('km-calendar:sysCalendarShareGroup.updateAuth')}
		    			<span id="updateAuthDate">
		    				，${lfn:message('km-calendar:sysCalendarShareGroup.updateAuthDate')}
		    				<xform:datetime property="startDate" required="true" value="<%=startDate %>" subject="${lfn:message('km-calendar:sysCalendarShareGroup.updateAuthDate')}"
		    					dateTimeType="date" showStatus="edit" style="width:15%;vertical-align: middle;"/>
		    			</span>
		    		</td>
		    	</tr>
			</table>
			</div>
		</html:form>
	</template:replace>	
</template:include>
<script>
	$KMSSValidation();
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
		$(document).ready(function(){
			var fdRequestAuth = $("input[name='fdRequestAuth']").val();
			if(fdRequestAuth.indexOf('authEdit') > -1){
				$('#authEdit').prop("checked", true);
			}
			if(fdRequestAuth.indexOf('authModify') > -1){
				$('#authModify').prop("checked", true);
			}
			if(fdRequestAuth.indexOf('authRead') > -1){
				$('#authRead').prop("checked", true);
			}
		});
		window.changeFdRequestAuth = function(checkbox){
			var fdRequestAuth = $("input[name='fdRequestAuth']");
			var values = fdRequestAuth.val().split(";");
			var arr = new Array();
			$.each(values,function(i,item){
				arr.push(item);
			});
			var value = checkbox.value;
			var check = checkbox.checked;
			var index = arr.indexOf(value);
			if(check && index == -1)
				arr.push(value);
			if(!check && index > -1)
				arr.splice(index,1);
			fdRequestAuth.val(arr.join(";"));
		};
		window.confirmRequestNo = function(){
			$('[name="authEdit"],[name="authModify"],[name="authRead"]').val('false');
			$('[name="updateCalendar"]').val(false);
			var formObj = document.kmCalendarAuthForm;
			Com_Submit(formObj, 'confirmRequestNo');
		};
		window.confirmRequest = function(){
			var fdRequestAuth = $('[name="fdRequestAuth"]').val() || '';
			$('[name="authEdit"],[name="authModify"],[name="authRead"]').val('false');
			if(fdRequestAuth.indexOf('authEdit') > -1){
				$('[name="authEdit"]').val('true');
			}
			if(fdRequestAuth.indexOf('authModify') > -1){
				$('[name="authModify"]').val('true');
			}
			if(fdRequestAuth.indexOf('authRead') > -1){
				$('[name="authRead"]').val('true');
			}
			var updateCalendar = $('[name="updateCalendar"]');
			if(updateCalendar.is(":checked")){
				$('[name="updateCalendar"]').val(true);
			}
			var formObj = document.kmCalendarAuthForm;
			Com_Submit(formObj, 'confirmRequest');
		};
	});
</script>
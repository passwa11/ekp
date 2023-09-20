<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
	
	<template:replace name="title">
		日程授权 - 时间管理
	</template:replace>
	
	<template:replace name="content">
		<html:form action="/km/calendar/km_calendar_auth/kmCalendarAuth.do">
			<html:hidden property="fdRequestAuthId"  value="${HtmlParam['fdRequestAuthId'] }"/>
			<input name="authEdit" type="hidden" value="false"/>
			<input name="authModify" type="hidden" value="false"/>
			<input name="authRead" type="hidden" value="false"/>
			<div data-dojo-type="mui/view/DocScrollableView" id="scrollView" class="gray" data-dojo-mixins="mui/form/_ValidateMixin">
				<div style="padding: 0 1.5rem;">
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<tr>
						<td style="font-size: 1.8rem;border-color: #000;">
							<c:out value="${ kmCalendarRequestAuth.docCreator.fdName}"></c:out><bean:message key="kmCalendarRquestAuth.authRequest" bundle="km-calendar"/>
							<br/>
						</td>
					</tr>
					<tr>
						<td>
							授权内容如下:<br/><br/>
							<input type="hidden" name="fdRequestAuth" value="${ kmCalendarRequestAuth.fdRequestAuth}"/>
	    					<input style="-webkit-appearance:checkbox" id="authRead" type="checkbox" value="authRead" disabled="disabled" checked="checked"/><bean:message key="kmCalendarAuth.authScope.authRead" bundle="km-calendar"/><br/>
	    					<input style="-webkit-appearance:checkbox" id="authEdit" type="checkbox" onclick="changeFdRequestAuth(this);" value="authEdit"/><bean:message key="kmCalendarAuth.authScope.authEdit" bundle="km-calendar"/><br/>
	    					<input style="-webkit-appearance:checkbox" id="authModify" type="checkbox" onclick="changeFdRequestAuth(this);" value="authModify"/><bean:message key="kmCalendarAuth.authScope.authModify" bundle="km-calendar"/>
						</td>
					</tr>
				</table>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<c:if test="${isConfirm == 'false' }">
					  	<li data-dojo-type="mui/tabbar/TabBarButton"
					  		data-dojo-props='colSize:2,href:"javascript:confirmRequestNo();",transition:"slide"'>
					  		<bean:message bundle="km-calendar" key="kmCalendarRquestAuth.authRequest.no" />
					  	</li>
					  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
					  		data-dojo-props='colSize:2,href:"javascript:confirmRequest();",transition:"slide"'>
					  		<bean:message bundle="km-calendar" key="kmCalendarRquestAuth.authRequest.yes" />
					  	</li>
				  	</c:if>
				</ul>
			</div>
		</html:form>
		<script type="text/javascript">
			require(["mui/form/ajax-form!kmCalendarAuthForm"]);
			require(['dojo/query',"dojo/ready",'dojo/dom-attr'],function(query,ready,domAttr){
				ready(function(){
					var fdRequestAuth = query('[name="fdRequestAuth"]')[0].value;
					if(fdRequestAuth.indexOf('authEdit') > -1){
						domAttr.set(query('#authEdit')[0],'checked','checked');
					}
					if(fdRequestAuth.indexOf('authModify') > -1){
						domAttr.set(query('#authModify')[0],'checked','checked');
					}
					if(fdRequestAuth.indexOf('authRead') > -1){
						domAttr.set(query('#authRead')[0],'checked','checked');
					}
				});
				window.changeFdRequestAuth = function(checkbox){
					var fdRequestAuth = query('[name="fdRequestAuth"]')[0];
					var values = fdRequestAuth.value.split(";");
					var value = checkbox.value;
					if(values.indexOf(value) == -1){
						values.push(value);
					}else{
						values.splice(values.indexOf(value),1);
					}
					// var arr = new Array();
					// $.each(values,function(i,item){
					// 	arr.push(item);
					// });
					// console.log(arr);
					// var value = checkbox.value;
					// var check = checkbox.checked;
					// console.log(check)
					// var index = arr.indexOf(value);
					// console.log(index);
					// if(check && index == -1)
					// 	arr.push(value);
					// if(!check && index > -1)
					// 	arr.splice(index,1);
					fdRequestAuth.value = values.join(";");
				};
				window.confirmRequest = function(){
					var fdRequestAuth = query('[name="fdRequestAuth"]')[0],
					fdRequestAuthValue = fdRequestAuth.value;
					console.log(fdRequestAuthValue);
					if(fdRequestAuthValue.indexOf('authEdit') > -1){
						query('[name="authEdit"]')[0].value = 'true';
					}
					if(fdRequestAuthValue.indexOf('authModify') > -1){
						query('[name="authModify"]')[0].value = 'true';
					}
					if(fdRequestAuthValue.indexOf('authRead') > -1){
						query('[name="authRead"]')[0].value = 'true';
					}
					var formObj = document.kmCalendarAuthForm;
					Com_Submit(formObj, 'confirmRequest');
				};
				window.confirmRequestNo = function(){
					query('[name="authEdit"]')[0].value = 'false';
					query('[name="authModify"]')[0].value = 'false';
					query('[name="authRead"]')[0].value = 'false';
					var formObj = document.kmCalendarAuthForm;
					Com_Submit(formObj, 'confirmRequestNo');
				};
			});
		</script>
	</template:replace>
</template:include>
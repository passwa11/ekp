<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">
	
	<template:replace name="title">
		日程授权 - 时间管理
	</template:replace>
	
	<template:replace name="content">
		<html:form action="/km/calendar/km_calendar_auth/kmCalendarAuth.do">
			<html:hidden property="fdGroupId"  value="${HtmlParam['fdGroupId'] }"/>
			<input name="authEdit" type="hidden" value="false"/>
			<input name="authModify" type="hidden" value="false"/>
			<input name="authRead" type="hidden" value="false"/>
			<div data-dojo-type="mui/view/DocScrollableView" id="scrollView" class="gray" data-dojo-mixins="mui/form/_ValidateMixin">
				<div style="padding: 0 1.5rem;">
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<tr>
						<td style="font-size: 1.8rem;border-color: #000;">
							<c:out value="${ kmCalendarShareGroup.docCreator.fdName}"></c:out>请求日程授权
							<br/>
						</td>
					</tr>
					<tr>
						<td>
							授权内容如下:<br/><br/>
							<xform:checkbox property="authScope" value="${authStatus}" showStatus="edit" mobile="true">
								<xform:simpleDataSource value="authEdit">允许他创建我的日程安排</xform:simpleDataSource>
								<xform:simpleDataSource value="authModify">允许他维护我的日程安排</xform:simpleDataSource>
								<xform:simpleDataSource value="authRead">允许他阅读我的日程安排</xform:simpleDataSource>
							</xform:checkbox>
						</td>
					</tr>
				</table>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<li data-dojo-type="mui/back/BackButton"
				  		data-dojo-props="doBack:window.doback"></li>
				  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
				  		data-dojo-props='colSize:2,href:"javascript:confirmInvite();",transition:"slide"'>
				  			<bean:message key="button.update"/>
				  		</li>
				   	<li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
				    	<div data-dojo-type="mui/back/HomeButton"></div>
				   	</li>
				</ul>
			</div>
		</html:form>
		<script type="text/javascript">
			require(["mui/form/ajax-form!kmCalendarAuthForm"]);
			require(['dojo/query'],function(query){
				window.confirmInvite = function(){
					var authScope = query('[name="authScope"]')[0],
						authScopeValue = authScope.value;
					if(authScopeValue.indexOf('authEdit') > -1){
						query('[name="authEdit"]')[0].value = 'true';
					}
					if(authScopeValue.indexOf('authModify') > -1){
						query('[name="authModify"]')[0].value = 'true';
					}
					if(authScopeValue.indexOf('authRead') > -1){
						query('[name="authRead"]')[0].value = 'true';
					}
					var formObj = document.kmCalendarAuthForm;
					Com_Submit(formObj, 'confirmInvite');
				};
			});
		</script>
	</template:replace>
</template:include>
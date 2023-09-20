<%@page
	import="com.landray.kmss.km.calendar.forms.KmCalendarRequestAuthForm"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%
	String currentUserId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("currentUserId", currentUserId);
%>
<template:include ref="mobile.edit" compatibleMode="true">

	<template:replace name="title">
		<bean:message bundle="km-calendar" key="kmCalendarRequestAuth.persons" />
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-calendar-edit.css" />
		<mui:min-file name="mui-calendar.js" />
	</template:replace>

	<template:replace name="content">
		<xform:config orient="vertical">
			<html:form
				action="/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do">
				<html:hidden property="docCreateId" />

				<div class="gray" data-dojo-type="mui/view/DocScrollableView"
					id="scrollView" data-dojo-mixins="mui/form/_ValidateMixin">
					<div class="muiImportBox">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td class="event_auth"><xform:address showStatus="edit"
										propertyId="fdRequestPersonIds"
										propertyName="fdRequestPersonNames" orgType="ORG_TYPE_PERSON"
										mulSelect="true" textarea="true" mobile="true"
										subject="${lfn:message('km-calendar:kmCalendarRequestAuth.fdRequestPerson')}"
										required="true"></xform:address></td>
							</tr>
							<tr>
								<td>
									<div data-dojo-type='mui/form/CheckBoxGroup'
										data-dojo-props="'name':'fdRequestAuth',
										'mul':true,'concentrate':false,
										'showStatus':'edit',
										'alignment':'V',
										'orient':'vertical',
										'subject':'<bean:message key="kmCalendarRequestAuth.fdRequestAuth" bundle="km-calendar"/>',
										'store':[{'text':'<bean:message key="kmCalendarRquestAuth.fdRequestAuth.authRead" bundle="km-calendar"/>','value':'authRead','checked':true,'disabled':'disabled'},
										{'text':'<bean:message key="kmCalendarRquestAuth.fdRequestAuth.authEdit" bundle="km-calendar"/>','value':'authEdit','checked':false},
										{'text':'<bean:message key="kmCalendarRquestAuth.fdRequestAuth.authModify" bundle="km-calendar"/>','value':'authModify','checked':false}]">
									</div> 
								</td>
							</tr>
						</table>
					</div>

					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom"
						data-dojo-props='fill:"grid"'>
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit "
							data-dojo-props='colSize:2,href:"javascript:saveRequestAuth(document.kmCalendarRequestAuthForm, \"update\");",transition:"slide"'>
							<bean:message key="button.submit" />
						</li>
					</ul>
				</div>
			</html:form>
		</xform:config>
	</template:replace>


</template:include>
<script type="text/javascript">
	require([ "mui/form/ajax-form!kmCalendarRequestAuthForm" ]);
	require([ 'dojo/query', "dojo/ready", 'dojo/dom-attr', 'dijit/registry','mui/dialog/Tip',"mui/form/ajax-form" ],
			function(query, ready, domAttr, registry,Tip,ajaxForm) {
				//校验对象
				var validorObj = null;
				ready(function() {
					validorObj = registry.byId('scrollView');
					$("input[type=checkbox][value=authRead]").prop("disabled",
							"disabled");
				});
				window.saveRequestAuth = function(formObj, method) {
					if (validorObj.validate()) {
						ajaxForm.ajaxForm("[name='kmCalendarRequestAuthForm']", {
							success:function(result){
								Tip.success({
									text: "${lfn:message('km-calendar:kmCalendarRequestAuth.modify.sendnotify') }", 
									callback: function(){
										if(localStorage){
											localStorage.setItem("calendar:kmCalendarRequestAuth", "share");
										}
										window.location='${LUI_ContextPath}/km/calendar/mobile/index.jsp';
									}, 
									cover: true
								});
							},
							error:function(result){
							}
						});
						Com_Submit(formObj, 'save');
					}
				};
				
			});
</script>

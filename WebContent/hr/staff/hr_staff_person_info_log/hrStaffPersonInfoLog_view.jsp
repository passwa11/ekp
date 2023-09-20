<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') } - ${ lfn:message('hr-staff:module.hr.staff') }
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/staff/resource/css/hr_staff.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<kmss:authShow roles="ROLE_HRSTAFF_LOG_DELETE">
			<ui:button text="${lfn:message('button.delete')}" order="1" onclick="deleteLog('hrStaffPersonInfoLog.do?method=delete&fdId=${param.fdId}');">
			</ui:button>
			</kmss:authShow>
			<ui:button text="${lfn:message('button.close')}" order="2" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') }" href="/hr/staff/hr_staff_person_info_log/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content"> 
		<div class="lui_form_content_frame">
		<div class='lui_form_subject'>
			${ lfn:message('hr-staff:table.HrStaffPersonInfoLog') }
		</div>
				
		<table class="tb_normal" width=100%>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreator') }
				</td>
				<td width=35%>
					<c:choose>
						<c:when test="${'true' eq hrStaffPersonInfoLogForm.isAnonymous}">
							${ lfn:message('hr-staff:hrStaffPersonInfoLog.sync.creator') }
						</c:when>
						<c:otherwise>
							${hrStaffPersonInfoLogForm.fdCreatorName}
						</c:otherwise>
					</c:choose>
				</td>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdCreateTime') }
				</td>
				<td width=35%>
					${hrStaffPersonInfoLogForm.fdCreateTime}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdParaMethod') }
				</td>
				<td width=35%>
					<sunbor:enumsShow value="${ hrStaffPersonInfoLogForm.fdParaMethod }" enumsType="hrStaffPersonInfoLog_fdParaMethod" />
				</td>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdIp') }
				</td>
				<td width=35%>
					${hrStaffPersonInfoLogForm.fdIp}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdBrowser') }
				</td>
				<td width=35%>
					${hrStaffPersonInfoLogForm.fdBrowser}
				</td>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdEquipment') }
				</td>
				<td width=35%>
					${hrStaffPersonInfoLogForm.fdEquipment}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdDetails') }
				</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${'true' eq hrStaffPersonInfoLogForm.isAnonymous}">
							${ lfn:message('hr-staff:hrStaffPersonInfoLog.sync.creator') }
						</c:when>
						<c:otherwise>
							${hrStaffPersonInfoLogForm.fdCreatorName}
						</c:otherwise>
					</c:choose>
					${hrStaffPersonInfoLogForm.fdDetails}
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('hr-staff:hrStaffPersonInfoLog.fdTargets') }
				</td>
				<td colspan="3">
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:'/hr/staff/hr_staff_person_info_log/hrStaffPersonInfoLog.do?method=findTargets&logId=${JsParam.fdId}'}
						</ui:source>
						<ui:render type="Template">
							{$
							<ul class="hr_staff_person_log_targets">
							$}
							for(var i=0; i<data.length; i++) {
								{$
									<li><a href="${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId={% data[i].fdId %}" target="_blank"><span>{% data[i].fdName %}</span></a></li>
								$}
							}
							{$
							</ul>
							$}
						</ui:render>
					</ui:dataview>
				</td>
			</tr>
		</table>
		</div>
		
		<script type="text/javascript">
			seajs.use(['lui/dialog'],function(dialog){
				window.deleteLog = function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(isOk) {
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};
			});
		</script>
	</template:replace>
</template:include>
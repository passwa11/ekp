<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<%-- 标签页标题--%>
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	
	<%--导航路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffPayrollIssuance')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<script type="text/javascript">
			Com_IncludeFile("calendar.js",null,"js");
			seajs.use(['lui/jquery','sys/ui/js/dialog'],function($,dialog){
				//删除
				window.deleteDoc=function(delUrl){
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
						if(isOk){
							Com_OpenWindow(delUrl,'_self');
						}	
					});
					return;
				};

				window.sendEmailChoose=function(val){
					if(val && val.indexOf('email') > -1){
						$("#sendEmailChoose").show();
					}else{
						$("#sendEmailChoose").hide();
					}
				}
				LUI.ready(function(){
					if("${hrStaffPayrollIssuanceForm.fdNotifyType}"){
						sendEmailChoose("${hrStaffPayrollIssuanceForm.fdNotifyType}");
					}
				});
			});
		</script>  
		
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--删除--%>
			<ui:button text="${lfn:message('button.delete') }" onclick="deleteDoc('hrStaffPayrollIssuance.do?method=delete&fdId=${param.fdId}');" order="4">
			</ui:button>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<%--资源借用单信息--%>
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do">		
			<html:hidden property="fdId" />
			<div class="lui_form_content_frame">
				<p class="lui_form_subject">
					<bean:message bundle="hr-staff" key="table.hrStaffPayrollIssuance" />
				</p>
				<table class="tb_normal" width=100%>
				<tr>
					<%--主题--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdmessageName" />
					</td>
					<td width=35% colspan="3">
						<bean:write name="hrStaffPayrollIssuanceForm" property="fdMessageName" />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdNotifyType" />
					</td>
					<td width="35%">
						<xform:checkbox property="fdNotifyType" showStatus="readOnly">
							<xform:simpleDataSource value="todo">待办</xform:simpleDataSource>
							<xform:simpleDataSource value="email">邮件</xform:simpleDataSource>
						</xform:checkbox>

						<span id="sendEmailChoose" style="display:none">
							<bean:message bundle="sys-profile" key="sys.email.info.emailSender" />
							<xform:select property="fdSendEmail" showStatus="view"  htmlElementProperties="id='fdSendEmail'" subject="${lfn:message('sys-profile:sys.email.info.send.name') }">
								<xform:customizeDataSource className="com.landray.kmss.sys.profile.service.spring.SysSenderEmailInfoDataSource" ></xform:customizeDataSource>
							</xform:select>
						</span>
					</td>
				</tr>				
				<tr>
					<%--发放人--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdCreator" />
					</td>
					<td width=35%>
						<bean:write name="hrStaffPayrollIssuanceForm" property="fdCreatorName" />
					</td>
				</tr>
				<tr>
					<%--发放时间--%>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdCreateTime" />
					</td>
					<td width=35% colspan = "3">
						<bean:write name="hrStaffPayrollIssuanceForm" property="fdCreateTime" />
					</td>
				</tr>
				<tr>
					<%--发放消息--%>
					<td class="td_normal_title" width=15%>	
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.resultMseeage" />
					</td>
					<td width=35%>
						<bean:write name="hrStaffPayrollIssuanceForm" property="fdResultMseeage" />
					</td>
				</tr>
				<tr>
					<%--发放消息详情--%>
					<td class="td_normal_title" width=15%>	
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.resultDetailMseeage" />
					</td>
					<td width=35% colspan="3">
						<kmss:showText value="${hrStaffPayrollIssuanceForm.fdResultDetailMseeage}"/> 
					</td>
				</tr>
			</table>
			</div>
			</html:form>
	</template:replace>
</template:include>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%> 
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="head">
		<style>
			#btnSubmit {
				width: 200px;
				height: 38px;
				line-height: 38px;
				background: #4285F4;
				border: 1px solid #4285F4;
				border-radius: 4px;
				font-family: PingFangSC-Regular;
				font-size: 14px;
				color: #FFFFFF;
				letter-spacing: -0.34px;
				text-align: center;
				display: inline-block;
				cursor: pointer;
			}
			
			#btnSubmit.invalid {
				background-color: #E5E5E5 !important;
				border-color: #E5E5E5 !important;
				color: #666666 !important;
			}
			
			#btnSubmit:active {
				background: #3174e3;
				border-color: #3174e3;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffPayrollIssuanceForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffPayrollIssuanceForm.fdCreatorName } - ${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffPayrollIssuanceForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" onclick="_save()" order="1">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }" href="/hr/staff/hr_staff_payroll_issuance/" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<div class="lui_form_content_frame">
		<html:form action="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do" enctype="multipart/form-data" onsubmit="return checkFile();" >
			<html:hidden property="fdId" />
			<html:hidden property="fdCreatorId" />
			<html:hidden property="fdCreatorName" />
			<div class='lui_form_title_frame' align="center">
				<div class='lui_form_subject'>
					<c:choose>
						<c:when test="${ hrStaffPayrollIssuanceForm.method_GET == 'add' }">
							<c:out value="${ lfn:message('button.add') } - ${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }"></c:out>	
						</c:when>
					</c:choose>
				</div>
			</div>
			
			<table class="tb_normal" width=98%>
				<tr>
					<!-- 推送消息名称 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdmessageName" />
					</td>
					<td width="35%">
						<xform:text property="fdMessageName" style="width:95%;" className="inputsgl" required="true" subject="${lfn:message('hr-staff:hrStaffPayrollIssuance.fdmessageName') }"  />
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdNotifyType" />
					</td>
					<td width="35%"> 
						<%
							//开启了邮件功能
							if ("true".equals(ResourceUtil.getKmssConfigString("kmss.notify.type.email.enabled"))) {
						%>
						<xform:checkbox onValueChange="sendEmailChoose" property="fdNotifyType" showStatus="edit" required="true" subject="${lfn:message('hr-staff:hrStaffPayrollIssuance.fdNotifyType') }">
							<xform:simpleDataSource value="todo"><bean:message bundle="sys-notify" key="sysNotify.type.todo"/></xform:simpleDataSource>
							<xform:simpleDataSource value="email"><bean:message bundle="sys-notify" key="sysNotify.type.email"/></xform:simpleDataSource>
						</xform:checkbox>
						<span id="sendEmailChoose" style="display:none">

							<bean:message bundle="sys-profile" key="sys.email.info.send.tip" />
							<xform:select property="fdSendEmail" showStatus="edit"  htmlElementProperties="id='fdSendEmail'" subject="${lfn:message('sys-profile:sys.email.info.send.name') }">
<%--								<xform:customizeDataSource className="com.landray.kmss.hr.recruit.service.spring.HrRecruitEmailInfoDataSource" ></xform:customizeDataSource>--%>
								<xform:customizeDataSource className="com.landray.kmss.sys.profile.service.spring.SysSenderEmailInfoDataSource" ></xform:customizeDataSource>
							</xform:select>

						</span>
						<%}else {%> 
							<xform:checkbox property="fdNotifyType" showStatus="edit" required="true" subject="${lfn:message('hr-staff:hrStaffPayrollIssuance.fdNotifyType') }">
								<xform:simpleDataSource value="todo"><bean:message bundle="sys-notify" key="sysNotify.type.todo"/></xform:simpleDataSource>
							</xform:checkbox> 
						<%} %>
					</td>
				</tr>
				<tr>
					<!-- 工资明细文件 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdPayroolDetail" />
					</td>
					<td>
					 	<input class="input_file" type="file" name="file" accept=".xls,.xlsx"/>${messages}
					</td>
				</tr>
				<tr>
					<!-- 注意事项 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdWarning" />
					</td>
					<td>
					<p><bean:message key="hrStaff.payroll.issuance.one" bundle="hr-staff"/></p>
					<p><bean:message key="hrStaff.payroll.issuance.two" bundle="hr-staff"/></p>
					<p><bean:message key="hrStaff.payroll.issuance.three" bundle="hr-staff"/></p>
					<%-- <a href="javascript:void(0);" onclick="downloadTemplet();" style="color:blue;text-decoration:underline"><bean:message key="hrStaff.payroll.issuance.four" bundle="hr-staff"/></a>
					&nbsp;<input type="hidden" name="fdExportDeptId" />
					<xform:text property="fdExportDeptName" showStatus="readOnly"></xform:text>
					<a href="javascript:void(0);" onclick="Dialog_Address(false,'fdExportDeptId','fdExportDeptName',';',ORG_TYPE_ORG|ORG_TYPE_DEPT);">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdExportDept" />
					</a> --%>
					</td>
				</tr>
				<tr>
					<!-- 模板下载 -->
					<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.download.template" />
					</td>
					<td>
						<input name="downType" type="radio" value="all" checked="checked"/><bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.download.template.all" />
						&nbsp;
						<input name="downType" type="radio" value="dept" /><bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.download.template.dept" />
						&nbsp;
						<span id="selectSpan" style="display: none;">
							<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdExportDept.tip" />
							<input type="hidden" name="fdExportDeptId" />
							<xform:text property="fdExportDeptName" showStatus="readOnly"></xform:text>
							<a href="javascript:void(0);" onclick="Dialog_Address(false,'fdExportDeptId','fdExportDeptName',';',ORG_TYPE_ORG|ORG_TYPE_DEPT);">
								<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdExportDept.select" />
							</a>
						</span>
						<br/>
						<div id="btnSubmit">${lfn:message('hr-staff:hrStaff.payroll.issuance.four')}</div>
					</td>
				</tr>
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			Com_IncludeFile("dialog.js");
			$KMSSValidation(document.forms['hrStaffhrStaffPayrollIssuanceForm']);
			//模板下载
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
				$(document).ready(function(){
					$("[name='downType']").click(function(){
						var val = $(this).val();
						if(val == 'dept'){
							$("#selectSpan").show();
						}else{
							$("#selectSpan").hide();
							$("[name='fdExportDeptId']").val('');
							$("[name='fdExportDeptName']").val('');
						}
					});
				});
				function ajax(callback){
					$.ajax({
						type :'POST',
						url : '${LUI_ContextPath}/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=downloadAjax',
						success : function(res){
							if(res == 'true'){
								callback();
							}else{
								dialog.alert("<bean:message bundle='hr-staff' key='hrStaffPayrollIssuance.downloadTemplet.tip' />");
							}
						}
					});
				};
				function downTemp(){
					var fdExportDeptId = $("[name='fdExportDeptId']").val();
					var form=$("<form>");//定义一个form表单  
		            form.attr("style","display:none");  
	                form.attr("target","");  
	                form.attr("method","post");  
	                form.attr("action","${LUI_ContextPath}/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=downloadTemplet");  
	                var input1=$("<input>");  
	                input1.attr("type","hidden");  
	                input1.attr("name","fdExportDeptId");  
	                input1.attr("value",fdExportDeptId);  
	                $("body").append(form);//将表单放置在web中  
	                form.append(input1);  
	                form.submit();//表单提交
				};
				$("#btnSubmit").click(function(){
					ajax(downTemp);
				});
				window._save = function(){
					var canSave = true;
					var fdNotifyType = document.getElementsByName("fdNotifyType")[0];
					
					if(fdNotifyType.value.indexOf("todo")>-1){
						<%
							if (!"true".equals(ResourceUtil.getKmssConfigString("kmss.notify.type.todo.enabled"))) {
						%>
						canSave = false;
						dialog.alert("<bean:message bundle='hr-staff' key='hrStaffPayrollNotify.todo.notify.null'/>");
						<%	
							}
						%>
					}
					
					if(canSave&&fdNotifyType.value.indexOf("email")>-1){
						<%
						if (!"true".equals(ResourceUtil.getKmssConfigString("kmss.notify.type.email.enabled"))) {
						%>
						canSave = false;
						dialog.alert("<bean:message bundle='hr-staff' key='hrStaffPayrollNotify.email.notify.null'/>");
						<%	
							}
						%>		
					}
					
					if(canSave && checkFile()){
						dialog.confirm("<bean:message bundle='hr-staff' key='hrStaffPayrollNotify.notify.async.confirm'/>",function(rs){
							if(rs){
								Com_Submit(document.hrStaffPayrollIssuanceForm, 'save');
							}
						});
					}

				}
			});
		
			//检查是否有文件上传
			function checkFile(){
				var file = document.getElementsByName("file");
				if(file[0].value==null || file[0].value.length==0){
					alert("<bean:message bundle='hr-staff' key='hrStaffPayrollIssuance.import.file.required'/>");
					return false;
				}
				return true ;
			};
			
			function sendEmailChoose(val){
				if(val && val.indexOf('email') > -1){ 
					$("#sendEmailChoose").show();
					$("#fdSendEmail").attr("validate","required");
				}else{
					$("#sendEmailChoose").hide();
					$("#fdSendEmail").removeAttr("validate");
				} 
			}
		</script>
	</template:replace>
</template:include>
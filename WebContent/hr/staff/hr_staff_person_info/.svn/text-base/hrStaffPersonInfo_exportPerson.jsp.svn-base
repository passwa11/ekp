<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style>
			#btnSubmit {
				width: 100px;
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
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js", null, "js");</script>
		<div style="margin:10px auto;text-align: center;">
			<br/>
			<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do">
			<table width="95%" style="text-align: left;" align="center">	
				<tr>
					<td colspan="2">
						<font color="red">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.export.tip"/>
						</font>
					</td>
				</tr>
				<tr>
					<td>
						<input name="downType" type="radio" value="all" checked="checked"/><bean:message bundle="hr-staff" key="hrStaffPersonInfo.export.all" />
						&nbsp;
						<input name="downType" type="radio" value="dept" /><bean:message bundle="hr-staff" key="hrStaffPersonInfo.export.dept" />
					</td>
					<td>
						<span id="selectSpan" style="display: none;">
							<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdExportDept.tip" />
							<input type="hidden" name="fdExportDeptId" />
							<xform:text property="fdExportDeptName" showStatus="readOnly"></xform:text>
							<a href="javascript:void(0);" onclick="Dialog_Address(false,'fdExportDeptId','fdExportDeptName',';',ORG_TYPE_ORG|ORG_TYPE_DEPT);">
								<bean:message bundle="hr-staff" key="hrStaffPayrollIssuance.fdExportDept.select" />
							</a>
						</span>
					</td>
				</tr>
			</table>
			<div style="width:95px;margin: 30px auto;">
				<div id="btnSubmit" style="margin-bottom: 20px;">${lfn:message('hr-staff:hrStaffPersonInfo.export.button') }</div>
			</div>
			</html:form>
		</div>
		<div style="height: 60px;"></div>
		<script>
			seajs.use(['lui/dialog', 'lui/jquery'],function(dialog,$) {
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
						url : '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=downloadAjax',
						success : function(res){
							if(res == 'true'){
								callback();
							}else{
								dialog.alert("<bean:message bundle='hr-staff' key='hrStaffPersonInfo.export.dialog.tip' />");
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
	                form.attr("action","${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=exportPerson");  
	                var input1=$("<input>");  
	                input1.attr("type","hidden");  
	                input1.attr("name","fdDeptId");  
	                input1.attr("value",fdExportDeptId);
	                var input2=$("<input>");
	                input2.attr("type","hidden");
	                input2.attr("name","personStatus");
	                input2.attr("value","${param.personStatus}");
	                $("body").append(form);//将表单放置在web中  
	                form.append(input1);  
	                form.append(input2);
	                form.submit();//表单提交
				}
				//确认
				$("#btnSubmit").click(function(){
					ajax(downTemp);
				});
			});
		</script>
	</template:replace>
</template:include>
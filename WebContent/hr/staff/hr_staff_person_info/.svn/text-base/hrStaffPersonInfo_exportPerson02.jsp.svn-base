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
					<td class="td_normal_title" width="150px">
						${ lfn:message('sys-attend:sysAttendReport.showColumn') }
						<xform:checkbox property="fdAllShowCols" showStatus="edit" isArrayValue="false" onValueChange="selectAll(this)">
							<xform:simpleDataSource value="">${ lfn:message("sys-ui:ui.listview.selectall") }</xform:simpleDataSource>
						</xform:checkbox>
					</td>
					<td>
						<xform:checkbox property="fdShowCols" showStatus="edit" isArrayValue="false" value="fdFirstLevelDepartment.fdName;fdSecondLevelDepartment.fdName;fdThirdLevelDepartment.fdName;fdStaffNo;fdName;fdStaffType;fdAffiliatedCompany;fdOrgPosts.fdName;fdStaffingLevel.fdName;fdCategory;fdOrgRank.fdName;fdReportLeader.fdName;fdDepartmentHead.fdName;fdEntryTime;fdProposedEmploymentConfirmationDate;fdPositiveTime;fdWorkingYears;fdHighestEducation;fdBeginDate;fdEndDate;fdContractPeriod;fdIdCard;fdDateOfBirth;fdAge;fdSex;fdReasonForResignation;fdResignationDate;fdResignationType;fdStaffingLevel;fdOrgRank;fdContPeriod;fdStatus;fdMaritalStatus;fdLoginName">
							<xform:customizeDataSource className="com.landray.kmss.hr.staff.service.spring.HrStaffPersonInfoCustomizeDataSource"></xform:customizeDataSource>
						</xform:checkbox>
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
				window.selectAll = function(){
					var fdShowCols="";
					var allcols=$('input[name="_fdAllShowCols"]').is(':checked');
					$('input[name="_fdShowCols"]').each(function(){
						if(allcols){
							this.checked=true;
							if(fdShowCols){
								fdShowCols+=";"+this.value;
							}else{
								fdShowCols+=this.value;
							}
						}
						else{
							this.checked=false;
						}
					});
					$('input[name="fdShowCols"]').val(fdShowCols);
				}

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
					var fdShowCols = $("[name='fdShowCols']").val();
					var form = $("<form>");//定义一个form表单
					form.attr("style", "display:none");
					form.attr("target", "");
					form.attr("method", "post");
					form.attr("action", "${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=exportPersonInfo");
					var input1 = $("<input>");
					input1.attr("type", "hidden");
					input1.attr("name", "fdShowCols");
					input1.attr("value", fdShowCols);
					$("body").append(form);//将表单放置在web中
					form.append(input1);
					form.submit();//表单提交
				}
				//确认
				$("#btnSubmit").click(function(){
					ajax(downTemp);
				});
				
			});
			function selectAll(_this){
				var hobby = document.getElementsByName("_fdShowCols");
            // 点击按钮进行全选和全不选
                  for (var i = 0; i < hobby.length; i++) {
                        /* 根据全选按钮属性为true/false时
                           同时将属性值赋值给每一个"hobby"按钮 */
                           hobby[i].checked = _this.checked;
                  }

//                   __cbClick("fdShowCols",'null',false,null);
//					if($input[name='_fdShowCols'].prop())
			}
		</script>
	</template:replace>
</template:include>
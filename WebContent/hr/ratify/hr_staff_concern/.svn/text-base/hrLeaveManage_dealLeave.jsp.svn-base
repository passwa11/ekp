<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
    	<link rel="stylesheet" href="../resource/style/lib/form.css">
    	<link rel="stylesheet" href="../resource/style/hr.css">
	</template:replace>
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js", null, "js");</script>
			<html:form action="/hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do" styleId="leaveDRForm" styleClass="hr_leave_deal_form">
				<div class="hr_leave_deal_wrap">
			        <!--提示文字Starts-->
			        <!-- alert_info 蓝色底 -->
			        <c:if test="${empty hrRatifyLeaveDRForm.fdRatifyLeaveId }">
				        <div class="lui_hr_tip_alert alert_info">
				            <div class="lui_hr_tip_alert_L">
				                <div class="lui_hr_validation_msg">
				                    <i class="lui_hr_validation_icon lui_hr_icon_info_warning">
				                    </i>
				                   	 需要走审批流程？
				                </div>
				            </div>
				            <div class="lui_hr_tip_alert_R lui_hr_tip_opt">
				                <a class="lui_text_primary" onclick="addRatifyLeave('${hrRatifyLeaveDRForm.fdUserId }');" target="_blank">发起离职审批</a>
				            </div>
				
				        </div>
			        </c:if>
			        <!--提示文字Ends-->
			
			        <!--离职人员信息Starts-->
			        <div class="lui_hr_personInfo_wrap">
			            <!--左边图片Starts-->
			            <div class="lui_hr_person_pic">
							<img src="${LUI_ContextPath}/sys/person/resource/images/head${not empty hrRatifyLeaveDRForm.fdUserSex?(hrRatifyLeaveDRForm.fdUserSex eq 'M'?"_man":"_lady"):""}.png"
								 width="48" height="48"/>
			            </div>
			            <!--左边图片Ends-->
			            <!--右边详细Starts-->
			            <div class="lui_hr_person_info">
			                <div class="lui_hr_person_info_content">
			                    <span class="lui_hr_person_name">${hrRatifyLeaveDRForm.fdUserName }</span>
								<span class="lui_hr_sex lui_hr_${not empty hrRatifyLeaveDRForm.fdUserSex?(hrRatifyLeaveDRForm.fdUserSex eq 'M'?"man":"female"):""}"></span>
			                    <!--说明：lui_hr_female是女性的类名，男性的类名把lui_hr_female换成lui_hr_man -->
			                </div>
			                <div class="lui_hr_department">
			                    <span class="lui_hr_depart lui_hr_secondary_txt">${hrRatifyLeaveDRForm.fdUserParentName }</span>&nbsp;|
			                    <span class="lui_hr_status">
			                    	<sunbor:enumsShow enumsType="hrStaffPersonInfo_fdStatus" value="${hrRatifyLeaveDRForm.fdUserStatus }"></sunbor:enumsShow>
			                    </span>
			                </div>
			            </div>
			            <!--右边详细Ends-->
			        </div>
			        <!--离职人员信息Ends-->
					<html:hidden property="fdUserId"/>
					<html:hidden property="fdUserName"/>
					<html:hidden property="fdUserSex"/>
					<html:hidden property="fdUserParentName"/>
					<html:hidden property="fdUserStatus"/>
					<html:hidden property="fdRatifyLeaveId"/>
			        <!--离职表格信息Starts-->
			        <div class="lui_hr_tb_simple_wrap">
			            <table class="tb_simple lui_hr_tb_simple">
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveApplyDate"/>	
			                    </td>
			                    <td>
			                        <xform:datetime subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveApplyDate') }" property="fdLeaveApplyDate" dateTimeType="date" required="true" showStatus="edit" />
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeavePlanDate"/>
			                    </td>
			                    <td>
			                        <xform:datetime subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeavePlanDate') }" property="fdLeavePlanDate" dateTimeType="date" required="true" showStatus="edit" />
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime"/>
			                    </td>
			                    <td>
			                        <xform:datetime subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveTime') }" property="fdLeaveRealDate" required="true" dateTimeType="date" showStatus="edit" />
			                    </td>
			
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveSalaryEndDate"/>
			                    </td>
			                    <td>
			                        <xform:datetime subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveSalaryEndDate') }" property="fdLeaveSalaryEndDate" dateTimeType="date" showStatus="edit" />
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveReason"/>
			                    </td>
			                    <td>
			                        <xform:select subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdLeaveReason') }" property="fdLeaveReason" showPleaseSelect="true" required="true" showStatus="edit">
			                        	<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'" orderBy="fdOrder"></xform:beanDataSource>
			                        </xform:select>
			                    </td>
			                </tr>
			
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveRemark"/>
			                    </td>
			                    <td>
			                        <xform:text property="fdLeaveRemark" showStatus="edit" />
			                    </td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNextCompany"/>
			                    </td>
			                    <td>
			                        <xform:text property="fdNextCompany" showStatus="edit" />
			                    </td>
			                </tr>
			            </table>
			        </div>
			        <!--离职表格信息Ends-->
			
			        <!--弹框底部按钮 产品标准组件 Starts-->
			       <%--  <div class="lui_hr_footer_btnGroup">
			            <ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
			            <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
			        </div> --%>
			        <!--弹框底部按钮 产品标准组件 Ends-->
			    </div>
			</html:form>
		<script>
			var validation=$KMSSValidation();//校验框架
			
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				window.addRatifyLeave = function(userId){
					if(userId){
						dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
							if(rtn != false&&rtn != null){
								var tempId = rtn.id;
								var tempName = rtn.name;
								if(tempId !=null && tempId != ''){
									$dialog.hide('null');
									var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_leave/hrRatifyLeave.do?method=add&i.docTemplate="+tempId+"&fdStaffId="+userId;
									Com_OpenWindow(url, '_blank');
								}
							}
						},null,null,null,null,null,'HrRatifyLeaveDoc');
					}
				};
				//确定
				var __isSubmit = false;
				window.clickOk=function(){
					if(__isSubmit){
						return;
					}
					if(validation.validate()){
						__isSubmit = true;
						submit();
					}
				};
				function submit(){
					var url = '<c:url value="/hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do?method=saveLeave"/>';
					$.ajax({
						url : url,
						type : 'POST',
						data : $("#leaveDRForm").serialize(),
						dataType : 'json',
						error : function(data) {
							if(window.del_load != null) {
								window.del_load.hide(); 
							}
							dialog.failure('<bean:message key="return.optFailure" />');
						},
						success: function(data) {
							if(window.del_load != null){
								window.del_load.hide(); 
							}
							dialog.success('<bean:message key="return.optSuccess" />');
							setTimeout(function (){
								window.$dialog.hide("success");
							}, 1500);
						}
				   });
				}
			});
		</script>
	</template:replace>
</template:include>
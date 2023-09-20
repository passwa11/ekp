<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
		   prefix="person"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			  href="${LUI_ContextPath}/hr/ratify/resource/style/lib/common.css">
		<link rel="stylesheet"
			  href="${LUI_ContextPath}/hr/ratify/resource/style/lib/form.css">
		<link rel="stylesheet"
			  href="${LUI_ContextPath}/hr/ratify/resource/style/hr.css">
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
			Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
		</script>
		<div class="hr_leave_deal_wrap">
			<!-- alert_info 蓝色底 -->
	        <div class="lui_hr_tip_alert alert_info">
	            <div class="lui_hr_tip_alert_L">
	                <div class="lui_hr_validation_msg">
	                    <i class="lui_hr_validation_icon lui_hr_icon_info_warning">
	                    </i>
	                   	 ${ lfn:message('hr-staff:hrStaffEntry.isNeedApprovalProcess') }
	                </div>
	            </div>
	            <div class="lui_hr_tip_alert_R lui_hr_tip_opt">
	                <a class="lui_text_primary" onclick="addRatifyPositive('${hrStaffPersonInfo.fdId }');" target="_blank">${ lfn:message('hr-staff:hrStaffEntry.employment.confirm.init') }</a>
	            </div>
	
	        </div>
	        <!--提示文字Ends-->
			        
			<div class="lui_hr_personInfo_wrap">
				<!--左边图片Starts-->
				<div class="lui_hr_person_pic">
					<img src="${LUI_ContextPath}/sys/person/resource/images/head${not empty hrStaffPersonInfo.fdSex?(hrStaffPersonInfo.fdSex eq 'M'?"_man":"_lady"):""}.png"
						 width="48" height="48"/>
				</div>
				<!--左边图片Ends-->
				<!--右边详细Starts-->
				<div class="lui_hr_person_info">
					<div class="lui_hr_person_info_content">
						<span class="lui_hr_person_name">${hrStaffPersonInfo.fdName}</span>
						<span class="lui_hr_sex lui_hr_${not empty hrStaffPersonInfo.fdSex?(hrStaffPersonInfo.fdSex eq 'M'?"man":"female"):""}"></span>
						<!--说明：lui_hr_female是女性的类名，男性的类名把lui_hr_female换成lui_hr_man -->
					</div>
					<div class="lui_hr_department">
						<span class="lui_hr_depart lui_hr_secondary_txt">${hrStaffPersonInfo.fdOrgParentsName}-${hrStaffPersonInfo.fdOrgPosts[0].fdName}</span>
					</div>
					<span class="lui_hr_status"><sunbor:enumsShow value="${ hrStaffPersonInfo.fdStatus }" enumsType="hrStaffPersonInfo_fdStatus" /></span>
				</div>
				<!--右边详细Ends-->
			</div>
			<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do">
				<div class="lui_hr_tb_simple_wrap">
					<table class="tb_simple lui_hr_tb_simple">
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.plan.fdPositiveTime') }
							<td>
								<xform:datetime property="fdPositiveTime"  dateTimeType="date" validators="compareDate" required="true" showStatus="view" value="${hrStaffPersonInfo.fdPositiveTime }"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdActualPositiveTime') }
							</td>
							<td>
								<xform:datetime property="fdActualPositiveTime" dateTimeType="date" validators="compareDate" required="true" showStatus="edit" subject="${ lfn:message('hr-staff:hrStaffPersonInfo.fdActualPositiveTime') }"></xform:datetime>
							</td>
						</tr>
						<tr>
							<td class="tr_normal_title">
								${ lfn:message('hr-staff:hrStaffPersonInfo.fdPositiveRemark') }
							</td>
							<td>
								<xform:textarea property="fdPositiveRemark" style="width:58%;height:50px;" showStatus="edit"/>
							</td>
						</tr>
					</table>
				</div>
				<html:hidden property="fdId" value="${hrStaffPersonInfo.fdId}" />
			</html:form>
		</div>
		<%-- <div class="lui_hr_footer_btnGroup" style="padding: 20px 50px;">
			<ui:button text="确认办理" onclick="_submit();"></ui:button>
			<ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
		</div> --%>
	</template:replace>
</template:include>
<script type="text/javascript">
	// 表单校验
	var _validation = $KMSSValidation();

	seajs.use( [ 'lui/jquery', 'lui/dialog', 'hr/staff/resource/js/dateUtil' ], function($, dialog, dateUtil) {
		window.addRatifyPositive = function(userId){
			if(userId){
				dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn){
					if(rtn != false&&rtn != null){
						var tempId = rtn.id;
						var tempName = rtn.name;
						if(tempId !=null && tempId != ''){
							$dialog.hide('null');
							var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=add&i.docTemplate="+tempId+"&fdStaffId="+userId;
							Com_OpenWindow(url, '_blank');
						}
					}
				},null,null,null,null,null,'HrRatifyPositiveDoc');
			}
		};
		
		// 表单序列化成JSON对象
		$.fn.serializeObject = function() {
			var o = {};
			var a = this.serializeArray();
			$.each(a, function() {
				if (o[this.name] !== undefined) {
					if (!o[this.name].push) {
						o[this.name] = [ o[this.name] ];
					}
					o[this.name].push(this.value || '');
				} else {
					o[this.name] = this.value || '';
				}
			});
			return o;
		};

		// 确认提交
		window._submit = function() {
			var personId = '${hrStaffPersonInfo.fdId }';
			var fdActualPositiveTime = $('input[name=fdActualPositiveTime]').val();
			var fdPositiveRemark = $('textarea[name=fdPositiveRemark]').val();
			var data = {
				personId :personId,
				fdActualPositiveTime: fdActualPositiveTime,
				fdPositiveRemark :fdPositiveRemark
			};
			if ($KMSSValidation().validate()) {
				$.post("${LUI_ContextPath}/hr/ratify/hr_ratify_positive/hrRatifyPositive.do?method=saveTransactionPositive",data,function(data){
					if(data!=""){
						dialog.success('<bean:message key="return.optSuccess" />');
						setTimeout(function (){
							window.$dialog.hide("success");
						}, 1500);
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				})
			}
		};

		// 取消
		window._cancel = function() {
			window.$dialog.hide();
		};

		var compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error1") }';
		switch('${JsParam.type}'){
			case 'contract':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error2") }';
				break;
			}
			case 'education':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error3") }';
				break;
			}
			case 'qualification':{
				compareDateMsg = '${ lfn:message("hr-staff:hrStaffPersonExperience.compareDate.error4") }';
				break;
			}
		}

		// 日期区间校验
		_validation.addValidator('compareDate', compareDateMsg, function(v, e, o) {
			var fdBeginDate = $('[name="fdBeginDate"]');
			var fdEndDate = $('[name="fdEndDate"]');
			var result = true;
			if (fdBeginDate.val() && fdEndDate.val()) {
				var start = dateUtil.parseDate(fdBeginDate.val());
				var end = dateUtil.parseDate(fdEndDate.val());
				if (start.getTime() > end.getTime()) {
					result = false;
				}
			}
			return result;
		});

	});
</script>
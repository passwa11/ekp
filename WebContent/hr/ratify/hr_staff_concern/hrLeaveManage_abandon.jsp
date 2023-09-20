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
			<html:form action="/hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do" styleClass="hr_leave_abandon_form">
				<div class="hr_leave_abandon_wrap">
					<div class="lui_hr_tb_simple_wrap">
			            <table class="tb_simple lui_hr_tb_simple">
			                <tr>
			                	<td colspan="2">
			                		<div class="lui_hr_validation_msg">
			                			<i class="lui_hr_validation_icon lui_hr_icon_warning"></i>
									 	放弃离职后员工状态将恢复为在职员工
									</div>
			                	</td>
			                </tr>
			                <tr>
			                    <td class="tr_normal_title">
			                        	员工状态	
			                    </td>
			                    <td>
			                        <xform:select property="fdStatus" showStatus="edit" value="trial">
			                        	<xform:simpleDataSource value="trial">
			                        		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.trial" />
			                        	</xform:simpleDataSource>
			                        	<xform:simpleDataSource value="practice">
			                        		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.practice" />
			                        	</xform:simpleDataSource>
			                        	<xform:simpleDataSource value="official">
			                        		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.official" />
			                        	</xform:simpleDataSource>
			                        	<xform:simpleDataSource value="temporary">
			                        		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.temporary" />
			                        	</xform:simpleDataSource>
			                        	<xform:simpleDataSource value="trialDelay">
			                        		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.trialDelay" />
			                        	</xform:simpleDataSource>
			                        </xform:select>
			                    </td>
			                </tr>
			             </table>
			        </div>
			        <%-- <div class="lui_hr_footer_btnGroup">
			            <ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
			            <ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
			        </div> --%>
				</div>
			</html:form>
		<script>
			var validation=$KMSSValidation();//校验框架
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				//确定
				var __isSubmit = false;
				window.clickOk=function(){
					if(__isSubmit){
						return;
					}
					if(validation.validate()){
						__isSubmit = true;
						var url = '<c:url value="/hr/ratify/hr_ratify_leave_dr/hrRatifyLeaveDR.do?method=abandonLeave"/>';
						var fdUserId = '${JsParam.fdUserId}';
						var fdStatus = $("[name='fdStatus']").val();
						var _data = {'fdUserId':fdUserId,'fdStatus':fdStatus};
						$.ajax({
							url : url,
							type : 'POST',
							data : $.param(_data, true),
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
				};
			});
		</script>
	</template:replace>
</template:include>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content" >
		<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");</script>
		<div style="margin:10px auto;text-align: center;">
			<div class="txttitle">
				<bean:message bundle="km-imeeting" key="kmImeeting.btn.cancelMeeting" />
			</div>
			<br/>
			<html:form action="/km/imeeting/km_imeeting_main_cancel/kmImeetingMainCancel.do">
			<html:hidden property="fdCancelType" />
			<table id="Table_Main" class="tb_normal"width="95%"align="center">
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.cancelMeetingReason" />
					</td>
					<td width="85%" colspan="3">
						<xform:textarea property="cancelReason" validators="maxLength(1500) emptyString" isLoadDataDict="false"
							style="width:95%;" showStatus="edit" subject="${lfn:message('km-imeeting:kmImeetingMain.cancelMeetingReason') }">
						</xform:textarea>
						<span class="txtstrong">*</span>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.notifyWay" />
					</td>
					<td width="85%" colspan="3">
						 <kmss:editNotifyType property="fdNotifyType"  />
					</td>
				</tr>
			</table>
			<div style="width:95px;margin: 10px auto;">
			<ui:button text="${lfn:message('km-imeeting:kmImeetingMain.cancelNotify.tip') }" onclick="clickOk();" >	</ui:button>
			</div>
			</html:form>
		</div>
		<div style="height: 60px;"></div>
		<script>
			var validation=$KMSSValidation();//校验框架
			function trimStr(str) { 
				return str.replace(/^\s+|\s+$/g, "");
			}
			validation.addValidator("emptyString","${lfn:message('km-imeeting:kmImeetingMain.cancelMeetingReason.tip')}",function(v, e, o){
				if(trimStr(v) == ''){
				   	return false;
				}
				return true;
			});
			
			seajs.use(['lui/dialog', 'lui/jquery'],function(dialog,$) {
				//确认
				window.clickOk=function(){
					if(validation.validate()){
						var formObj = document.kmImeetingMainCancelForm;
						Com_Submit(formObj,"cancelMeeting");
					}
				};
			});
		</script>
		
		<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
			<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
	</template:replace>
</template:include>
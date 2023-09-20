<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="title"><bean:message bundle="sys-ui" key="sys.ui.config" /></template:replace>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0;color: #35a1d0;">
			<bean:message bundle="sys-portal" key="table.sysPortalNotice"/>
		</h2>
		
		<html:form action="/sys/portal/sys_portal_notice/sysPortalNotice.do?method=edit">
			<center>
				<table  class="luiPortalNotice tb_normal" width=80%>
					<tr id="tr_fdState">
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-portal" key="sysPortalNotice.fdState"/>
						</td>
						<td>
							<ui:switch property="fdState" onValueChange="notice_display_change();" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"
								disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
						</td>
					</tr>
					<tr style="display: none;">
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-portal" key="sysPortalNotice.docContent"/>
						</td>
						<td>
							<xform:textarea style="width:98%" property="docContent" required="true"/>				
						</td>
					</tr>
					<tr style="display: none;">
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-portal" key="sysPortalNotice.docStartTime"/>
						</td>
						<td>
							<xform:datetime required="true" property="docStartTime" dateTimeType="datetime" showStatus="edit" />
						
						</td>
					</tr>
					<tr style="display: none;">
						<td class="td_normal_title" width="20%">
							<bean:message bundle="sys-portal" key="sysPortalNotice.docEndTime"/>
						</td>
						<td>
							<xform:datetime required="true" property="docEndTime" dateTimeType="datetime" validators="compareTime compareNowTime" showStatus="edit" />
						</td>
					</tr>
					
				</table>
			</center>
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<center style="margin-top: 10px;">
			<!-- 提交 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();"></ui:button>
			</center>
		</html:form>
		
	 	<script type="text/javascript">
	 		var _validation = $KMSSValidation();
	 		seajs.use(['lui/dialog','lui/jquery','sys/portal/sys_portal_notice/dateUtil'], function(dialog,$,dateUtil) {
	 			_validation.addValidator('compareTime','<bean:message bundle="sys-portal" key="sysPortalNotice.config.compareTime.error" />',function(v, e, o){
					var result = true;
					var fdTime=$('[name="docStartTime"]');
					var fdEndTime=$('[name="docEndTime"]');

					if( fdTime.val() && fdEndTime.val() ){
						var start=dateUtil.parseDate(fdTime.val());
						var end=dateUtil.parseDate(fdEndTime.val());
						if(end.getTime() <= start.getTime()){
							result = false;
						}
					}
					return result;
				});
	 			//比较结束时间和当前时间，结束时间早于当前时间没有意义
	 			_validation.addValidator('compareNowTime','<bean:message bundle="sys-portal" key="sysPortalNotice.config.compareTime.error1" />',function(v, e, o){
					var result = true;
					var fdEndTime=$('[name="docEndTime"]');
					if(fdEndTime.val() ){
						var end=dateUtil.parseDate(fdEndTime.val());
						var nowTime = new Date();
						if(end.getTime() <= nowTime.getTime()){
							result = false;
						}
						
					}
					return result;
				});
	 			window.commitMethod = function (){
			 		if(!_validation.validate()){
			 			return;
			 		}
			 		Com_Submit(document.sysPortalNoticeForm, "update");
			 	};
			 	window.notice_display_change = function(v){
			 		var value = $('[name="fdState"]').val() || v;
			 		var docContentEle = $("[name='docContent']")[0];
			 		var docStartTimeEle = $("[name='docStartTime']")[0];
			 		var docEndTimeEle = $("[name='docEndTime']")[0];
					if(value == 'true'){
						$('.luiPortalNotice tr[id!="tr_fdState"]').show();
						_validation.addElements(docContentEle, "required");
						_validation.addElements(docStartTimeEle, "required");
						_validation.resetElementsValidate(docEndTimeEle);
					}else{
						$('.luiPortalNotice tr[id!="tr_fdState"]').hide();
						_validation.removeElements(docContentEle, "required");
						_validation.removeElements(docStartTimeEle, "required");
						_validation.removeElements(docEndTimeEle);
					}
			 	};
			 	$(function(){
			 		var fdState = "${sysPortalNoticeForm.fdState}";
			 		setTimeout(function(){
			 			notice_display_change(fdState);
					},1);
			 	});
	 		});
	 		
		 	
	 	</script>
	</template:replace>
</template:include>

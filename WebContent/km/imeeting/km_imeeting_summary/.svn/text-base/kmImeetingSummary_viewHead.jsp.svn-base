<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="head">
	<script>
		function yqq(isSignBefore){
			 seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
				 var ajaxUrl = Com_Parameter.ContextPath+"km/imeeting/km_imeeting_outsign/kmImeetingOutsign.do?method=validateOnce&signId=${param.fdId}";
					$.ajax({
						url : ajaxUrl,
						type : 'post',
						data : {},
						dataType : 'text',
						async : true,     
						error : function(){
							dialog.alert('请求出错');
						} ,   
						success : function(data) {
							if(data == "true"){
								//window.location = data;
								dialog.alert("当前会议纪要已经发送过易企签签署，不能重复发送!!!");
							}else{
								var infoUrl = "/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=openYqqExtendInfo&signId=${param.fdId}";
								var extendIframe=dialog.iframe(infoUrl,"签署平台签署",null,{width:1200, height:600, topWin : window, close: true});
							}
						}
					}); 
			 });
		}
		
		<c:if test="${kmImeetingSummaryForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true'&&yqqFlag=='true'&&kmImeetingSummaryForm.fdSignEnable=='true'}">
		 Com_Parameter.event["submit"].push(function(){
			 var canStartProcess = document.getElementById("sysWfBusinessForm.canStartProcess");
			//操作类型为通过类型 ，才判断是否已经签署
			if($(canStartProcess).val() != "false" && lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
				var flag = true;
			 	var url = Com_Parameter.ContextPath+"km/imeeting/km_imeeting_outsign/kmImeetingOutsign.do?method=queryFinish&signId=${param.fdId}";
			 	$.ajax({
					url : url,
					type : 'post',
					data : {},
					dataType : 'text',
					async : false,     
					error : function(){
						dialog.alert('请求出错');
					} ,   
					success:function(data){
						if(data == "true"){
							flag = true;
						}else{
							dialog.alert("当前签署任务未完成，无法提交！！");
							flag = false;
						}
					}
				});
			 	return flag;
			}else{
				return true;
			} 
		 });
		 </c:if>
	</script>
</template:replace>
<%-- 页签名--%>
<template:replace name="title">
	<c:out value="${kmImeetingSummaryForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
</template:replace>
<%-- 按钮栏--%>
<template:replace name="toolbar">
  <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6">
   <c:if test="${not empty kmImeetingSummaryForm.fdMeetingId}">
		<kmss:auth requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingSummaryForm.fdMeetingId}" requestMethod="GET">
			<ui:button order="2" text="${lfn:message('km-imeeting:kmImeeting.btn.viewMeeting') }" 
					onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingSummaryForm.fdMeetingId}','_blank');">
			</ui:button>
		</kmss:auth>
	</c:if>
	<c:if test="${kmImeetingSummaryForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true'&&yqqFlag=='true'&&kmImeetingSummaryForm.fdSignEnable=='true'}">
     	 <%-- 集成了易企签、勾选了附件选项、印章类型为电子签章且未发送过签署的才出现发送签署按钮 --%>
      	 <ui:button text="${lfn:message('km-imeeting:kmImeeting.sign.zhixingqianshu')}" onclick="yqq()" order="1" />
    </c:if>
	<%-- 编辑 --%> 
	<kmss:auth requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=edit&fdId=${JsParam.fdId}" requestMethod="GET">
		<c:if test="${kmImeetingSummaryForm.docStatus!='00'}">
			<ui:button order="2" text="${ lfn:message('button.edit') }"  onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=edit&fdId=${JsParam.fdId}','_self');">
			</ui:button>
		</c:if>
	</kmss:auth> 
	<%-- 删除 --%> 
	<kmss:auth requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=delete&fdId=${JsParam.fdId}" requestMethod="GET">
		<ui:button order="4" text="${ lfn:message('button.delete') }"  onclick="docDel();">
		</ui:button>
	</kmss:auth>
	<!--打印 -->
	<kmss:auth requestURL="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=print&fdId=${JsParam.fdId}" requestMethod="GET">
	    <ui:button text="${ lfn:message('button.print') }"  onclick="Com_OpenWindow('${LUI_ContextPath}/km/imeeting/km_imeeting_summary/kmImeetingSummary.do?method=print&fdId=${JsParam.fdId}','_blank');">
		</ui:button>
	</kmss:auth>
	<c:if test="${kmImeetingSummaryForm.docStatus eq '30' or kmImeetingSummaryForm.docStatus eq'31'}">
		<!-- 沉淀-->
		<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&fdId=${param.fdId}" requestMethod="GET">
			<c:import url="/kms/multidoc/kms_multidoc_subside/subsideButton.jsp?fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&fdId=${ param.fdId}&serviceName=kmImeetingSummaryService" charEncoding="UTF-8">
			</c:import>
		</kmss:auth>
	</c:if>
	<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
	</ui:button>
	</ui:toolbar>
</template:replace>
<%-- 路径导航 --%> 
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"  href="/" target="_self">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }"  href="/km/imeeting/" target="_self">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:table.kmImeetingSummary') }" href="/km/imeeting/#j_path=/myHandleSummary&except=docStatus:00" target="_self">
			</ui:menu-item>
			<ui:menu-source autoFetch="false"   target="_self"  href="/km/imeeting/#j_path=/myHandleSummary&except=docStatus:00&cri.q=fdTemplate:${kmImeetingSummaryForm.fdTemplateId}">
				<ui:source type="AjaxJson">
					{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=path&modelName=com.landray.kmss.km.imeeting.model.KmImeetingTemplate&categoryId=${kmImeetingSummaryForm.fdTemplateId}"} 
				</ui:source>
			</ui:menu-source>
	</ui:menu>
</template:replace>

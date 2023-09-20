<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
		<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="hrRatifySignForm"></c:param>
	</c:import>
	<!-- 流程状态标识 -->
	<c:import url="/hr/ratify/hr_ratify_main/hrRatifyMain_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="hrRatifySignForm" />
		<c:param name="approveType" value="${param.approveModel}" />
	</c:import>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="hrRatifySignForm" method="post" action="<c:url value="/hr/ratify/hr_ratify_sign/hrRatifySign.do"/>">
	</c:if>
	<p class="lui_form_subject">
		<c:out value="${hrRatifySignForm.docSubject}" />
	</p>
	<script>
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
			 window.yqq=function(){
				 var ajaxUrl = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_sign/hrRatifyOutSign.do?method=queryStatus&signId=${param.fdId}";
				 var ajaxUrl2 = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_sign/hrRatifyOutSign.do?method=validateOnce&signId=${param.fdId}";
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
							if(data == "false"){
								$.ajax({
									url : ajaxUrl2,
									type : 'post',
									data : {},
									dataType : 'text',
									async : true,     
									error : function(){
										dialog.alert('请求出错');
									} ,   
									success:function(data){
										if(data == "true"){
											dialog.alert("当前合同签订已经发送过易企签签署，签署事件未完成，请完成后在发起签署！！");
										}else{
											var infoUrl = "/hr/ratify/hr_ratify_sign/hrRatifySign.do?method=openYqqExtendInfo&signId=${param.fdId}";
											var extendIframe=dialog.iframe(infoUrl,"签署平台签署",null,{width:1200, height:600, topWin : window, close: true});
										}
									}
								});
							}else{
								var extendIframe=dialog.iframe(data,"签署平台签署",null,{width:1200, height:600, topWin : window, close: true});
							}
						}
					}); 
				
			 }
			 <c:if test="${hrRatifySignForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true' && hrRatifySignForm.fdSignEnable=='true'}">
			 Com_Parameter.event["submit"].push(function(){
				//操作类型为通过类型 ，才判断是否已经签署
				if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
					var flag = true;
					 var url = Com_Parameter.ContextPath+"hr/ratify/hr_ratify_sign/hrRatifyOutSign.do?method=queryFinish&signId=${param.fdId}";
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
		 });
	</script>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpage collapsed="true" id="reviewTabPage">
				<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					});
				</script>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
			</ui:tabpage>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-extend="true" var-average='false' var-useMaxWidth='true'>
				<%--流程--%>
				<c:choose>
					<c:when test="${hrRatifySignForm.docStatus>='30' || hrRatifySignForm.docStatus=='00'}">
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifySignForm.docUseXform == 'true' || empty hrRatifySignForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifySignForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifySignForm.docUseXform == 'true' || empty hrRatifySignForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifySignForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
	<c:if test="${param.approveModel ne 'right'}">
		</form>
	</c:if>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${hrRatifySignForm.docStatus>='30' || hrRatifySignForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联机制(与原有机制有差异) -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifySignForm" /> 
						</c:import> 
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<%-- 流程 --%>
						<c:choose>
							<c:when test="${hrRatifySignForm.docUseXform == 'true' || empty hrRatifySignForm.docUseXform}">
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.hrRatifySignForm, 'update');" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:when>
							<c:otherwise>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="hrRatifySignForm" />
									<c:param name="fdKey" value="${fdTempKey }" />
									<c:param name="isExpand" value="true" />
									<c:param name="approveType" value="right" />
									<c:param name="approvePosition" value="right" />
								</c:import>
							</c:otherwise>
						</c:choose>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifySignForm" />
							<c:param name="fdModelId" value="${hrRatifySignForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.hr.ratify.model.HrRatifySign" />
						</c:import>
						<!-- 关联机制(与原有机制有差异) -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="hrRatifySignForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
						</c:import> 
						<!-- 基本信息-->
						<c:import url="/hr/ratify/hr_ratify_sign/hrRatifySign_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<%--关联机制(与原有机制有差异)--%>
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="hrRatifySignForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
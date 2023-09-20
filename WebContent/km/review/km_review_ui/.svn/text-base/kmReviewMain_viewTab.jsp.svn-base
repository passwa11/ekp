<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.review.forms.KmReviewMainForm"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmReviewMainForm"></c:param>
	</c:import>
	<!-- 流程状态标识 -->
	<c:import url="/km/review/km_review_ui/kmReviewMain_banner.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="kmReviewMainForm" />
		<c:param name="approveType" value="${param.approveModel}" />
	</c:import>
	<c:if test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.relationConfig =='true'}">
		<% request.setAttribute("relationConfig", "true"); %>
	</c:if>
	<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|calendar.js|dialog.js|jquery.js", null, "js");
	</script>


	<script language="JavaScript">
	
	$(document).ready(function(){
		var obj = document.getElementById("JGWebOffice_mainContent")||document.getElementById("JGWebOffice_${kmReviewMainForm.fdModelId}");
		if(obj){
			obj.setAttribute("height", "580px");
		}
	});
	
	seajs.use(['lui/dialog'],function(dialog){
		window.dialog = dialog;
	});
	function appointFeedback(){
		var path = "/km/review/km_review_main/kmReviewChangeFeedback.jsp?fdId=${param.fdId}"
		dialog.iframe(path,' ',null,{width:750,height:500});

	}
	function feedback(){
		var path ="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=add&fdMainId=${param.fdId}&fdCreatorId=${kmReviewMainForm.docCreatorId}";
		dialog.iframe(path,' ',null,{width:750,height:500});

	}
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
		var eqbSigntUrl = Com_Parameter.ContextPath
		+ "km/review/km_review_main/kmReviewMain.do?method=eqbSign&signId=${param.fdId}";
		window.eqbSign = function() {
			$.ajax({
				type : "POST",
				url : eqbSigntUrl,
				contentType : false,
				processData : false,
				success : function(rtn) {
					if (rtn.success) {
						if(rtn.isSigner){
							Com_OpenWindow(rtn.signUrl);
						}else{
							dialog.alert("不是当前签署人或文档已被签署，请刷新重试");
						}
					} else {
						seajs.use([ 'lui/dialog' ], function(dialog) {
							dialog.alert(rtn.error);
						});
					}
				}
			})
		}
		
		 window.yqq=function(){
			 var ajaxUrl = Com_Parameter.ContextPath+"km/review/km_review_main/kmReviewOutSign.do?method=queryStatus&signId=${param.fdId}";
			 var ajaxUrl2 = Com_Parameter.ContextPath+"km/review/km_review_main/kmReviewOutSign.do?method=validateOnce&signId=${param.fdId}";
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
										dialog.alert("当前流程已经发送过易企签签署，签署事件未完成，请完成后再查看签署！！");
									}else{
										var infoUrl = "/km/review/km_review_main/kmReviewMain.do?method=openYqqExtendInfo&signId=${param.fdId}";
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
		 <c:if test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true'  && kmReviewMainForm.fdSignEnable=='true'}">
		 Com_Parameter.event["submit"].push(function(){
			//操作类型为通过类型 ，才判断是否已经签署
			if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
				 var flag = true;
				 var url = Com_Parameter.ContextPath+"km/review/km_review_main/kmReviewOutSign.do?method=queryFinish&signId=${param.fdId}";
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
	<c:if test="${param.approveModel ne 'right'}">
		<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
			<form name="kmReviewMainForm" method="post" action="<c:url value="/km/review/km_review_main/kmReviewMain.do"/>">
		</c:if>	
	</c:if>				
		<p class="lui_form_subject" style="position: relative">
			<c:if test="${empty kmReviewMainForm.docSubject}">
				<bean:message bundle="km-review" key="table.kmReviewMain" />
			</c:if>
			<c:if test="${not empty kmReviewMainForm.docSubject}">
				<c:out value="${kmReviewMainForm.docSubject}" />
			</c:if>
		</p>
		<xform:text property="fdId" showStatus="noShow"/>	
		<xform:text property="docSubject" showStatus="noShow"/>
		<xform:text property="docStatus" showStatus="noShow"/>
		<xform:text property="fdNumber" showStatus="noShow"/>
		<xform:text property="method_GET" showStatus="noShow"/>
		<ui:render type="Template">
				<c:import url="/km/review/resource/tmpl/treemenu2.tmpl"></c:import>
			</ui:render>
		<div class="lui_form_content_frame">
			<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
				<c:choose>
					<c:when test="${kmReviewMainForm.fdUseWord == 'true'}">
						<table class="tb_normal" width=100%>
							<tr>
								<td colspan="4">
									<div id="reviewButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
				   					</div>
									<%
										Boolean _isWpsWebOffice = false;
										boolean existViewPath = JgWebOffice.isExistViewPath(request);
										String type = SysAttConfigUtil.getOnlineToolType();
										String readOLConfig = SysAttConfigUtil.getReadOLConfig();
										if (StringUtils.isEmpty(readOLConfig) || "-1".equals(readOLConfig)) {
											if ("3".equals(type)) {//加载项
												_isWpsWebOffice = true;
											}
										}else{
											if ("3".equals(type) && "1".equals(readOLConfig) && !existViewPath) {
												//wps加载项+aspose，文件没有转换完成时，使用加载项
												_isWpsWebOffice = true;
											}
										}
										pageContext.setAttribute("_isWpsWebOffice", _isWpsWebOffice);
									%>
									<c:choose>
										<c:when test="${pageScope._isWpsWebOffice == 'true'}">
											<%
												//以下代码用于附件不通过form读取的方式
												List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
												if(sysAttMains==null || sysAttMains.isEmpty()){
													try{
														KmReviewMainForm kmReviewMainForm=(KmReviewMainForm)pageContext.getRequest().getAttribute("kmReviewMainForm");
														String _modelId  = kmReviewMainForm.getFdId();
														if(StringUtil.isNotNull(_modelId)){
															ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
															sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.km.review.model.KmReviewMain",_modelId,"mainContent");
														}
														if(sysAttMains!=null && !sysAttMains.isEmpty()){
															pageContext.setAttribute("sysAttMains",sysAttMains);
														}
													}catch(Exception e){
														e.printStackTrace();
													}
												}
											%>
											<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
												<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
												<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
											</c:forEach>
											<!-- 判断附件文件是否已经转换 -->
											<%
												if(com.landray.kmss.sys.attachment.util.JgWebOffice.isExistViewPath(request))
												{
											%>
											<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="mainContent" />
												<c:param name="fdAttType" value="office" />
												<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
												<c:param name="formBeanName" value="kmReviewMainForm" />
												<c:param name="buttonDiv" value="missiveButtonDiv" />
												<c:param name="isExpand" value="true" />
												<c:param name="showToolBar" value="false" />
											</c:import>
											<%} else{%>
											<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="mainContent" />
												<c:param name="fdMulti" value="false" />
												<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
												<c:param name="formBeanName" value="kmReviewMainForm" />
												<c:param name="fdTemplateModelName" value="com.landray.kmss.km.review.model.KmReviewTemplate" />
												<c:param name="fdTemplateKey" value="mainContent" />
												<c:param name="templateBeanName" value="kmReviewTemplateForm" />
												<c:param name="showDelete" value="false" />
												<c:param name="wpsExtAppModel" value="kmReviewMain" />
												<c:param name="canRead" value="true" />
												<c:param name="addToPreview" value="false" />
												<c:param  name="hideTips"  value="true"/>
												<c:param  name="hideReplace"  value="true"/>
												<c:param  name="canEdit"  value="false"/>
												<c:param name="canPrint" value="false" />
												<c:param  name="canChangeName"  value="false"/>
												<c:param name="load" value="true" />
											</c:import>
											<%} %>
										</c:when>
										<c:otherwise>
											<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="mainContent" />
												<c:param name="fdAttType" value="office" />
												<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
												<c:param name="formBeanName" value="kmReviewMainForm" />
												<c:param name="buttonDiv" value="missiveButtonDiv" />
												<c:param name="isExpand" value="true" />
												<c:param name="showToolBar" value="false" />
												<c:param name="isAtt" value="false" />
												<c:param name="showChangeView" value="true" />
											</c:import>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<table class="tb_normal" width=100%>
							<tr>
								<td colspan="4">
									<xform:rtf property="docContent" />
								</td>
							</tr>
							<!-- 相关附件 -->
							<tr KMSS_RowType="documentNews">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-review" key="kmReviewMain.attachment" />
								</td>
								<td colspan=3>
									<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdMulti" value="true" />
										<c:param name="formBeanName" value="kmReviewMainForm" />
										<c:param name="fdKey" value="fdAttachment" />
									</c:import>
								</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>		
			</c:if>
			<%--如果需要嵌入签署页面，则与自定义表单构成两个页签，没有则只有自定义表单，无页签 Start--%>
			<c:choose>
				<c:when test="${kmReviewMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true' && eqbSignButtonFlag eq 'true'}">
					<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
								 var-count="5" var-average='false' var-useMaxWidth='true'>
						<%--自定义表单--%>
						<ui:content title="${ lfn:message('km-review:kmReviewMain.xform.ShenPiNeiRong.title') }" expand="false">
							<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
								<%-- 表单 --%>
								<c:import url="/sys/xform/include/sysForm_view.jsp"	charEncoding="UTF-8">
									<c:param name="formName" value="kmReviewMainForm" />
									<c:param name="fdKey" value="reviewMainDoc" />
									<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
									<c:param name="useTab" value="false"/>
								</c:import>
							</c:if>
						</ui:content>
						<%-- E签宝嵌入签署页面 --%>
						<c:import url="/km/review/km_review_main/kmReviewMain.do?method=getEqbSignPage" charEncoding="UTF-8">
							<c:param name="signId" value="${kmReviewMainForm.fdId }" />
							<c:param name="enable" value="true"></c:param>
						</c:import>
					</ui:tabpanel>
				</c:when>
				<c:otherwise>
					<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
						<%-- 表单 --%>
						<c:import url="/sys/xform/include/sysForm_view.jsp"	charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdKey" value="reviewMainDoc" />
							<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
							<c:param name="useTab" value="false"/>
						</c:import>
					</c:if>
				</c:otherwise>
			</c:choose>
		 	<%--如果需要嵌入签署页面，则与自定义表单构成两个页签，没有则只有自定义表单，无页签 End--%>
		</div>
		
		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true"  var-useMaxWidth='true'>
					<c:import url="/km/review/km_review_ui/kmReviewMain_viewContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false" var-navwidth="90%">
					<c:import url="/km/review/km_review_ui/kmReviewMain_viewContent.jsp" charEncoding="UTF-8">
		 		 	</c:import>
				</ui:tabpage>
				<kmss:ifModuleExist path="/sys/iassister">
					<ui:drawerpanel id="drawerPanel" width="500">
						<c:import
							url="/sys/iassister/sys_iassister_template/import/check_items.jsp" charEncoding="UTF-8">
							<c:param name="panelId" value="drawerPanel"></c:param>
							<c:param name="idx" value="0"></c:param>
							<c:param name="templateId"
								value="${kmReviewMainForm.fdTemplateId }"></c:param>
							<c:param name="templateModelName"
								value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
							<c:param name="mainModelId"
								value="${kmReviewMainForm.fdId }"></c:param>
							<c:param name="mainModelName"
								value="com.landray.kmss.km.review.model.KmReviewMain"></c:param>
							<c:param name="fdKey" value="reviewMainDoc"></c:param>
						</c:import>
					</ui:drawerpanel>
				</kmss:ifModuleExist>
				<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
					</form>
				</c:if>	
			</c:otherwise>
		</c:choose>	

	<script language="JavaScript">
		xform_validation = $KMSSValidation(document.forms['kmReviewMainForm']);
		
		seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
			window.deleteDoc = function(delUrl){
				Com_Delete_Get(delUrl, 'com.landray.kmss.km.review.model.KmReviewMain');
				return;
			};

			window.copyDoc = function(copyUrl) {
				// 检查模板表单是否有更新
				$.ajax({
					url : '<c:url value="/km/review/km_review_main/kmReviewMain.do" />?method=checkTemplate&fdReviewId=${param.fdId}&fdTemplateId=${kmReviewMainForm.fdTemplateId}',
					// 这里的请求如果是异步，在打开新窗口时会被浏览器阻止，所以设置为同步，缺点：在点击“复制流程”时，会有一点点卡顿
					async : false,
					dataType : 'json',
					success : function(json) {
						if (json == false) {
							Com_OpenWindow(copyUrl, '_blank');
						} else {
							dialog.confirm('<bean:message bundle="km-review" key="kmReviewMain.copy.comfirm"/>', function(isOk) {
								if (isOk) {
									Com_OpenWindow(copyUrl, '_blank');
								}
							});
						}
					}
				});
				return;
			};

			window.deleteFeedbackInfo = function(fdId) {
				dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(isOk) {
					if (isOk) {
						var loading = dialog.loading();
						var url = '<c:url value="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do" />?method=delete&fdId=' + fdId;
						$.getJSON(url, function(json) {
							loading.hide();
							if (json.status) {
								dialog.success('<bean:message key="return.optSuccess" />');
								topic.channel('feedbackch1').publish('list.refresh');
							} else {
								dialog.failure('<bean:message key="return.optFailure" />');
							}
						});
					}
				});
				return;
			};
			
			window.printDoc = function(){
				var printMode = "${fdPrintMode}";
				if(typeof subform_print_BySubformId != "undefined" && printMode!='2'){
					subform_print_BySubformId('${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=print&fdId=${param.fdId}');
				}else{
					Com_OpenWindow('${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=print&fdId=${param.fdId}');
				}
				return
			};
			
			window.printLattice = function(){
				Com_OpenWindow('${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=printLattice&fdId=${param.fdId}');
				return
			};
			
			window.editDoc = function(){
				if(lbpm && lbpm.isSubForm && typeof subform_edit_BySubformId != "undefined"){
					subform_edit_BySubformId('${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId}','_self');
				}else{
					Com_OpenWindow('${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId}','_self');
				}
				return
			};
		});
		
		// 发生版本冲突时，强制提交
		window.versionOverwrite = function() {
			// 点击提交按钮
			if($("#process_review_button").length>0){
				$("#process_review_button").trigger("click");
			}else{
				alert("<bean:message bundle='km-review' key='kmReviewMain.unableSubmit' />");
				versionCancel();
			}
		}
		// 发生版本冲突时，刷新页面
		window.versionCancel = function() {
			var href = window.location.href;
			var method = Com_GetUrlParameter(href, "method");
			if (method == "publishDraft") {
				// 如果是流程审批请求，刷新后跳到view页面
				href = href.replace('method=' + method, 'method=view');
			}
			window.location.href = href;
		}
	</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${kmReviewMainForm.docStatus>='30' || kmReviewMainForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 传阅意见-->
						<c:if test="${existOpinion}">
							<ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" >
								<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${kmReviewMainForm.fdId}&fdModelName=com.landray.kmss.km.review.model.KmReviewMain">
								</ui:iframe>
							</ui:content>
						</c:if>
						<!-- 基本信息-->
						<c:import url="/km/review/km_review_ui/kmReviewMain_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="approveType" value="right" />
							<c:param name="enable" value="${enableModule.enableSysRelation eq 'false' ? 'false' : 'true'}"></c:param>
						</c:import>
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<c:choose>
							<c:when test="${existOpinion && existOpinionAndNoIdentity}">
								<!-- 传阅意见-->
								<c:if test="${existOpinion}">
									<ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" titleicon="lui-fm-icon-6">
										<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${kmReviewMainForm.fdId}&fdModelName=com.landray.kmss.km.review.model.KmReviewMain">
										</ui:iframe>
									</ui:content>
								</c:if>
								<%-- 流程 --%>
								<c:choose>
									<c:when test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
										<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmReviewMainForm" />
											<c:param name="fdKey" value="reviewMainDoc" />
											<c:param name="showHistoryOpers" value="true" />
											<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
											<c:param name="isExpand" value="true" />
											<c:param name="approveType" value="right" />
											<c:param name="approvePosition" value="right" />
										</c:import>
									</c:when>
									<c:otherwise>
										<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmReviewMainForm" />
											<c:param name="fdKey" value="reviewMainDoc" />
											<c:param name="showHistoryOpers" value="true" />
											<c:param name="isExpand" value="true" />
											<c:param name="approveType" value="right" />
											<c:param name="approvePosition" value="right" />
										</c:import>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<%-- 流程 --%>
								<c:choose>
									<c:when test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
										<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmReviewMainForm" />
											<c:param name="fdKey" value="reviewMainDoc" />
											<c:param name="showHistoryOpers" value="true" />
											<c:param name="onClickSubmitButton" value="Com_Submit(document.kmReviewMainForm, 'publishDraft');" />
											<c:param name="isExpand" value="true" />
											<c:param name="approveType" value="right" />
											<c:param name="approvePosition" value="right" />
										</c:import>
									</c:when>
									<c:otherwise>
										<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="kmReviewMainForm" />
											<c:param name="fdKey" value="reviewMainDoc" />
											<c:param name="showHistoryOpers" value="true" />
											<c:param name="isExpand" value="true" />
											<c:param name="approveType" value="right" />
											<c:param name="approvePosition" value="right" />
										</c:import>
									</c:otherwise>
								</c:choose>
								<!-- 传阅意见-->
								<c:if test="${existOpinion}">
									<ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" titleicon="lui-fm-icon-6">
										<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${kmReviewMainForm.fdId}&fdModelName=com.landray.kmss.km.review.model.KmReviewMain">
										</ui:iframe>
									</ui:content>
								</c:if>
							</c:otherwise>
						</c:choose>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmReviewMainForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
							<c:param name="enable" value="${enableModule.enableSysRelation eq 'false' ? 'false' : 'true'}"></c:param>
						</c:import>
						<!-- 基本信息-->
						<c:import url="/km/review/km_review_ui/kmReviewMain_viewBaseInfoContent.jsp" charEncoding="UTF-8">
						</c:import>
						<kmss:ifModuleExist path="/sys/iassister">
							<c:import
								url="/sys/iassister/sys_iassister_template/import/check_items.jsp" charEncoding="UTF-8">
								<c:param name="panelId" value="barRightPanel"></c:param>
								<c:param name="idx" value="4"></c:param>
								<c:param name="templateId"
									value="${kmReviewMainForm.fdTemplateId }"></c:param>
								<c:param name="templateModelName"
									value="com.landray.kmss.km.review.model.KmReviewTemplate"></c:param>
								<c:param name="mainModelId"
									value="${kmReviewMainForm.fdId }"></c:param>
								<c:param name="mainModelName"
									value="com.landray.kmss.km.review.model.KmReviewMain"></c:param>
								<c:param name="fdKey" value="reviewMainDoc"></c:param>
							</c:import>
						</kmss:ifModuleExist>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<!-- 传阅意见-->
			<c:if test="${existOpinion}">
				<ui:accordionpanel style="min-width:200px;min-height:100px;" layout="sys.ui.accordionpanel.simpletitle"> 
					<ui:content title="${ lfn:message('km-review:kmReviewMain.circulation.option') }" id="circulation" >
						<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=edit&fdModelId=${kmReviewMainForm.fdId}&fdModelName=com.landray.kmss.km.review.model.KmReviewMain">
						</ui:iframe>
					</ui:content>
				</ui:accordionpanel>
			</c:if>
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmReviewMainForm" />
				<c:param name="enable" value="${enableModule.enableSysRelation eq 'false' ? 'false' : 'true'}"></c:param>
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>
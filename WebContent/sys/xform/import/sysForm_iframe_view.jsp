<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="auto">
<template:replace name="content">
	<style>
		.lui_form_path_frame{
			display: none !important;
		}
		.com_qrcode {
			display: none !important;
		}
		.tempTB {
			width: 100% !important;
			max-width:100% !important;
		}
	</style>

	<c:set var="mainDocForm" value="${requestScope[param.formName]}" />
	<script type="text/javascript">
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|calendar.js|dialog.js|jquery.js", null, "js");
	</script>

	<!-- wps加载项的引入 -->
	<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
	<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
	<script>Com_IncludeFile("wps_cloud_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/cloud/js/","js",true);</script>
	<script>Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);</script>


	<script language="JavaScript">
	$(document).ready(function(){
		var obj = document.getElementById("JGWebOffice_mainContent")||document.getElementById("JGWebOffice_${mainDocForm.fdModelId}");
		if(obj){
			obj.setAttribute("height", "580px");
		}
	});

	seajs.use(['lui/dialog'],function(dialog){
		window.dialog = dialog;
	});
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
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
										dialog.alert("当前流程已经发送过易企签签署，签署事件未完成，请完成后在查看签署！！");
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
		 <c:if test="${mainDocForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true'  && mainDocForm.fdSignEnable=='true'}">
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
	<form name="sysForm" id="sysForm" method="post">
		<div class="lui_form_content_frame">
			<c:if test="${mainDocForm.fdUseForm == 'false'}">
				<c:choose>
					<c:when test="${mainDocForm.fdUseWord == 'true'}">
						<table class="tb_normal" width=100%>
							<tr>
								<td colspan="4">
									<div id="reviewButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
				   					</div>
										<%
				   							//加载项 暂时屏蔽
							     			pageContext.setAttribute("_isWpsAddonsEnable", false);
										%>
										<c:choose>
											<c:when test="${pageScope._isWpsAddonsEnable == 'true'}">
												<a href="javascript:;" class="com_btn_link" id="startWps" style="margin: 0 10px;" onclick="wpsAddonsEnable()">点击查看正文</a>
												<script>
														function wpsAddonsEnable(){
															var url ="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=findAttMains";
															  $.ajax({
																  type:"post",
																  url:url,
																  data:{fdModelName:"${mainDocForm.modelClass}",fdKey:"editonline",fdModelId:"${mainDocForm.fdId}"},
																  dataType:"json",
																  async:false,
																  success:function(data){
																	  console.log(data);
																	var wpsParam = {};
																		openWpsOAAssit(data.attMainId,wpsParam);

																  }
															 });
														}
												</script>
											</c:when>
											<c:otherwise>
												<%
													if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()) {
												%>
														<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
															<c:param name="fdKey" value="mainContent" />
															<c:param name="fdAttType" value="office" />
															<c:param name="fdModelId" value="${mainDocForm.fdId}" />
															<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
															<c:param name="formBeanName" value="${param.formName}" />
															<c:param name="buttonDiv" value="missiveButtonDiv" />
															<c:param name="isExpand" value="true" />
															<c:param name="showToolBar" value="false" />
														</c:import>
												<%
													}
												%>
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
										<c:param name="formBeanName" value="${param.formName}" />
										<c:param name="fdKey" value="fdAttachment" />
									</c:import>
								</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${mainDocForm.fdUseForm == 'true' || empty mainDocForm.fdUseForm}">
				<%-- 表单 --%>
				<c:import url="/sys/xform/include/sysForm_view.jsp"	charEncoding="UTF-8">
					<c:param name="formName" value="${param.formName}" />
					<c:param name="fdKey" value="${param.fdKey}" />
					<c:param name="useTab" value="false"/>
				</c:import>
			</c:if>
		</div>
	</form>
	<script language="JavaScript">
		var isDebug = 'true' === '${param.isDebug}';
		var _valdate = $KMSSValidation(document.forms['${param.formName}']);

		//表单提交前通知当前页面
		window.addEventListener("message", receiveMessage, false);
		function receiveMessage(event)
		{
			if(isDebug)
				console.log("iframe ${mainDocForm.method} receiveMessage:" + event.data.event);
			if(event.data.event === 'beforeFormSubmit') {
				let isDraft = false;
				if(event.data.isDraft === true) {
					isDraft = true;
				}
				//校验是否通过
				let validateResult = validate(isDraft);
				//向父窗口返回表单的内容
				let sysForm = getSysForm();
				parent.postMessage({"event": "sysForm", "validate": validateResult, "form": sysForm}, "*");
			}
		}

		//获取表单的内容
		function getSysForm() {
			var formSerial = {};
			if(isDebug)
				console.log($("#sysForm"));
			$($("#sysForm").serializeArray()).each(function() {
				formSerial[this.name] = this.value;
			});
			return formSerial;
		}

		function validate(isDraft) {
			return _valdate.validate();
		}

		// 发生版本冲突时，强制提交
		window.versionOverwrite = function() {
			// 点击提交按钮
			$("#process_review_button").trigger("click");
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
</template:include>
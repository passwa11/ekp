<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%
   String fdId = request.getParameter("fdId");
   String link = request.getParameter("showWindow");
   boolean showWindow = true;
   if("false".equals(link) || MobileUtil.DING_PC != MobileUtil.getClientType(request)){
	   showWindow=false;
   }
   request.setAttribute("showWindow", showWindow);
   String dingUrl="/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fdId+"&showWindow=false";
   dingUrl = StringUtil.formatUrl(dingUrl);

%>
<template:replace name="content">
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
	<script type="text/javascript" src="//g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>
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
	<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
		<form name="kmReviewMainForm" method="post" action="<c:url value="/km/review/km_review_main/kmReviewMain.do"/>">
	</c:if>
		<xform:text property="fdId" showStatus="noShow"/>
		<xform:text property="docSubject" showStatus="noShow"/>
		<xform:text property="docStatus" showStatus="noShow"/>
		<xform:text property="fdNumber" showStatus="noShow"/>
		<xform:text property="method_GET" showStatus="noShow"/>
		<ui:render type="Template">
			<c:import url="/km/review/resource/tmpl/treemenu2.tmpl"></c:import>
		</ui:render>
		<div class="lui_form_content_frame">
			<div class="lui-head">
				<div class="lui-head-content">
					<div class="lui-doc-subject">
						<xform:text property="docSubject"  style="width:97%;" className="inputsgl"/>
					</div>
					<div>
						<span class="lui-doc-status">
							<c:if test="${kmReviewMainForm.docStatus=='00'}">
								<c:out value="${ lfn:message('km-review:status.discard')}"></c:out>
							</c:if>
							<c:if test="${kmReviewMainForm.docStatus=='10'}">
								<c:out value="${ lfn:message('km-review:status.draft') } "></c:out>
							</c:if>
							<c:if test="${kmReviewMainForm.docStatus=='11'}">
								<c:out value="${ lfn:message('km-review:status.refuse')}"></c:out>
							</c:if>
							<c:if test="${kmReviewMainForm.docStatus=='20'}">
								<span id="handlerName_old" style="display: none"><kmss:showWfPropertyValues idValue="${kmReviewMainForm.fdId}" propertyName="handlerName" extend="hidePersonal" /></span>
								<span id="handlerName_dingSuit">等待<kmss:showWfPropertyValues idValue="${kmReviewMainForm.fdId}" propertyName="handlerName" extend="hidePersonal" />处理</span>
								<span id="nodeName_dingSuit">
									<kmss:showWfPropertyValues var="nodevalue" idValue="${kmReviewMainForm.fdId}" propertyName="nodeName" />
									<div title="${nodevalue}">
										等待<c:out value="${nodevalue}"></c:out>处理
									</div>
								</span>
							</c:if>
							<c:if test="${kmReviewMainForm.docStatus=='30'}">
								<c:out value="${ lfn:message('km-review:status.publish') }"></c:out>
							</c:if>
							<c:if test="${kmReviewMainForm.docStatus=='31'}">
								<c:out value="${ lfn:message('km-review:status.feedback') }"></c:out>
							</c:if>
						</span>
						<span class="lui-doc-number">
							编号：<xform:text property="fdNumber" className="inputsgl" showStatus="view"/>
						</span>
						
					</div>
					<c:if test="${kmReviewMainForm.method_GET=='view' }">
						<c:if test="${kmReviewMainForm.docStatus=='10' || kmReviewMainForm.docStatus=='11'|| kmReviewMainForm.docStatus=='20'}">
							<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
								<div class="lui_normal_hearder_div">
									<span class="lui_normal_hearder_title">
										
									</span>
								</div>
								<div class="lui_normal_hearder_btnWrap">
									<span class="lui_normal_hearder_btn" onclick="editDoc();" style="cursor: pointer;">
										<img  src="../resource/style/images/update.png" style="margin-right: 8px;vertical-align: middle;height: 12px;margin-top: -2px;">编辑
									</span>
								</div>
							</kmss:auth>
						</c:if>
						<!-- 打印 -->
						<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=print&fdId=${param.fdId}&s_xform=${kmReviewMainForm.sysWfBusinessForm.fdSubFormId}" requestMethod="GET">
							<div class="lui_normal_hearder_btnWrap">
								<span class="lui_normal_hearder_btn" onclick="printDoc();" style="cursor: pointer;">
									<img  src="../resource/style/images/icon-print.png" style="margin-right: 8px;vertical-align: middle;height: 12px;margin-top: -2px;">${ lfn:message('km-review:button.print') }
								</span>
							</div>
						</kmss:auth>
						<%-- <kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
							<c:if test="${showWindow}">
								<div class="lui_normal_hearder_btnWrap">
									<span class="lui_normal_hearder_btn" onclick="openNewWindown();" style="cursor: pointer;">
										<img  src="../resource/style/images/openwindow_line.png" style="margin-right: 8px;vertical-align: middle;height: 12px;margin-top: -2px;">独立窗口
									</span>
								</div>
							</c:if>
						</kmss:auth> --%>
						<kmss:auth requestURL="/km/review/km_review_main/kmReviewMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
								<div class="lui_normal_hearder_btnWrap">
									<span class="lui_normal_hearder_btn" onclick="deleteDoc('${LUI_ContextPath}/km/review/km_review_main/kmReviewMain.do?method=delete&fdId=${param.fdId}');" style="cursor: pointer;">
										<img  src="../resource/style/images/delete.png" style="margin-right: 8px;vertical-align: middle;height: 12px;margin-top: -2px;">${ lfn:message('button.delete') }
									</span>
								</div>
						</kmss:auth>
						<%--切换表单--%>
						<c:if test="${kmReviewMainForm.docStatus!='10' && kmReviewMainForm.docStatus!='11' && kmReviewMainForm.method_GET=='view' && kmReviewMainForm.sysWfBusinessForm.showSubBut}">
							<%--<ui:button id="switchForm" parentId="toolbar" text="${ lfn:message('sys-lbpmservice:lbpmNode.subForm.switchForm') }" order="1" onclick="Subform_switchForm();"></ui:button>--%>
							<div class="lui_normal_hearder_btnWrap">
									<span class="lui_normal_hearder_btn" onclick="Subform_switchForm();" style="cursor: pointer;">
										<img  src="../resource/style/images/qiehuan.png" style="margin-right: 8px;vertical-align: middle;height: 12px;margin-top: -2px;">${ lfn:message('sys-lbpmservice:lbpmNode.subForm.switchForm') }
									</span>
							</div>
						</c:if>
					</c:if>
				</div>
			</div>

			<ui:tabpage id="review_content" expand="false" var-navwidth="90%" collapsed="true">
				<ui:content title="${ lfn:message('km-review:kmReviewDocumentLableName.reviewContent') }" toggle="false" >
				<div class="lui_normal_title_d">
					<span class="lui_normal_title_s">审批内容</span>
				</div>
				<%-- 	<table class="tb_normal" width=100%>
						<!--主题-->
						<tr>
							<td align="right" style="border-right: 0px;width: 99.6px;" class="td_normal_title">
								<label><bean:message bundle="km-review" key="kmReviewMain.docSubject" /></label>
							</td>
							<td style="border-left: 0px !important;">
								<div style="width:80%;display: inline-block;">
									<xform:text property="docSubject"  style="width:97%;" className="inputsgl"/>
								</div>
							</td>
						</tr>
					</table> --%>
					<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
						<c:choose>
							<c:when test="${kmReviewMainForm.fdUseWord == 'true'}">
								<table class="tb_normal" width=100%>
									<tr>
										<td colspan="4">
											<div id="reviewButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
											</div>
												<%
														if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()) {
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
												<%
													}
												%>
		
										</td>
									</tr>
								</table>
							</c:when>
							<c:otherwise>
								<table class="tb_normal" width=100%>
									<tr>
										<td align="right" style="border-right: 0px;width: 99.6px;" class="td_normal_title"></td>
										<td>
											<xform:rtf property="docContent" />
										</td>
									</tr>
									<!-- 相关附件 -->
									<tr KMSS_RowType="documentNews">
										<td class="td_normal_title" width="99.6">
											<bean:message bundle="km-review" key="kmReviewMain.attachment" />
										</td>
										<td>
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
			</ui:tabpage>
		</div>
		
		<div>
			<ui:tabpanel expand="false" var-navwidth="90%" collapsed="true">
				<c:import url="/km/review/km_review_ui/dingSuit/viewContent.jsp" charEncoding="UTF-8">
				</c:import>
			</ui:tabpanel>
		</div>
		<div class="btn_div btn_div_add" style="display: none">
			
			
			<c:if test="${(kmReviewMainForm.docStatus>='20' && kmReviewMainForm.docStatus<'30') || kmReviewMainForm.docStatus == '11'}">
				<%--提交按钮--%>
				<div class="submit_btn_div btn_div_item">
					<ui:button styleClass="lui_mix_submit_btn" text="${ lfn:message('button.submit') }" order="2" onclick="_dingSubmitDoc();">
					</ui:button>
				</div>
			</c:if>
		</div>
		<c:if test="${kmReviewMainForm.fdUseForm == 'true' || empty kmReviewMainForm.fdUseForm}">
			</form>
		</c:if>
		

	<script language="JavaScript">
		//加载页面后改变提交按钮的位置到流程tab下方
		$(function () {
			changeSubmitBtnPosition();
		});

		function changeSubmitBtnPosition() {
			var btn = $(".btn_div");
			var target = $(".lui-lbpm-foldOrUnfold");
			if (target) {
				target.parent().append(btn);
			}
			//#122636 处理钉钉端结束状态的流程查看页删除按钮在流程tab下方 
			var docStatus = '${kmReviewMainForm.docStatus}';
			if(docStatus === '30'){
				console.log("状态是："+docStatus);
				$(".btn_div_add").css("margin-top","-55px");
			} 
			btn.show();

			//153268 如果当前节点无处理人时，显示等待<无>处理，当前节点无处理人时，显示 节点名称，如果有处理人，显示处理人的姓名。
			var handleName = $("#handlerName_old").text();
			console.log(handleName);
			if(handleName.includes('<无>')){
				$("#handlerName_dingSuit").css("display","none");
				$("#nodeName_dingSuit").css("display","");
			}else{
				$("#handlerName_dingSuit").css("display","");
				$("#nodeName_dingSuit").css("display","none");
			}
		}

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
					Com_OpenWindow('${KMSS_Parameter_ContextPath}km/review/km_review_main/kmReviewMain.do?method=print&fdId=${param.fdId}', '_self');
				}
				return;
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
			
			window.openNewWindown = function(){
				var openUrl = "<%=dingUrl%>";
				openUrl = encodeURIComponent(openUrl);
				window.location.href='dingtalk://dingtalkclient/page/link?url='+openUrl+'&web_wnd=general&width=1200&height=905';
                //关闭当前窗口
				close();
			};
			
			function close(){
			    Com_Parameter.CloseInfo = null;
			    Com_CloseWindow();
			    //工作通知、待办等关闭
			    DingTalkPC.biz.navigation.quit();
			}
			
		});
		
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
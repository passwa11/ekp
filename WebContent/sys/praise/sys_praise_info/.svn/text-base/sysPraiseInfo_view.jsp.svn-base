<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%
	boolean isAdmin = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_SYSPRAISEINFO_MAINTAINER");
	request.setAttribute("isAdmin", isAdmin);
	request.setAttribute("fdUserId", UserUtil.getUser().getFdId());
%>
<template:include ref="default.view" sidebar = "no">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-praise:module.sys.praiseInfo') }"></c:out>
	</template:replace>
	<template:replace name="head">
		<!-- 赞赏回复 Ends -->
		<link href="${LUI_ContextPath}/sys/praise/style/info_view.css" rel="stylesheet">
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="4">
			<kmss:authShow roles="ROLE_SYSPRAISEINFO_MAINTAINER">
			<c:import url="/sys/simplecategory/import/doc_cate_change_view.jsp"
					charEncoding="UTF-8">
					<c:param name="fdMmodelName"
						value="com.landray.kmss.sys.praise.model.SysPraiseInfo" />
					<c:param name="docFkName" value="docCategory" />
					<c:param name="fdCateModelName"
						value="com.landray.kmss.sys.praise.model.SysPraiseInfoCategory" />
					<c:param name="fdMmodelId" value="${sysPraiseInfoForm.fdId }" />
					<c:param name="fdCategoryId"
						value="${sysPraiseInfoForm.docCategoryId }" />
			</c:import>
			</kmss:authShow>
			<ui:button text="${lfn:message('button.close')}" onclick="Com_CloseWindow(); " order="5">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:combin ref="menu.path.simplecategory">
			<ui:varParams
				moduleTitle="${ lfn:message('sys-praise:module.sys.praiseInfo.manager') }" 
				modulePath="/sys/praise/" 
				modelName="com.landray.kmss.sys.praise.model.SysPraiseInfoCategory" 
				autoFetch="false"
				categoryId="${sysPraiseInfoForm.docCategoryId}" />
		</ui:combin>
	</template:replace>
	<template:replace name="content"> 
				<div class="lui_form_title_frame lui_admire_cardWrap">
					<div class="lui_form_subject">
						<bean:message key="sys-praise:sysPraiseInfo.infoTitle"/>
					</div>
					<div class="lui_form_baseinfo">
						<span class="lui_form_date">
							<bean:message key="sys-praise:sysPraiseInfo.docCreateTime"/>: ${sysPraiseInfoForm.docCreateTime }
						</span>
						<bean:message key="sys-praise:sysPraiseInfo.fdType"/>:
						<c:choose>
						 	<c:when test="${sysPraiseInfoForm.fdType eq '1'}">
						 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.praise') }"/>
						 	</c:when>
						 	<c:when test="${sysPraiseInfoForm.fdType eq '2'}">
						 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.rich') }"/>
						 	</c:when>
						 	<c:when test="${sysPraiseInfoForm.fdType eq '3'}">
						 		<c:out value="${lfn:message('sys-praise:sysPraiseInfo.fdType.unPraise') }"/>
						 	</c:when>
						 </c:choose>
					</div>
					<div class="lui_form_content_frame" style="min-height: inherit">
						<!-- 赞赏卡片 Starts -->
						<div class="lui_admire_card">
							<div class="admire_card_header">
								<div class="admire_card_portrait">
									<c:choose>
										<c:when test="${ !isAdmin &&  (not empty sysPraiseInfoForm.fdIsHideName && sysPraiseInfoForm.fdIsHideName eq 'true') && fdUserId ne sysPraiseInfoForm.fdPraisePersonId}">
										</c:when>
										<c:otherwise>
											<img src="${LUI_ContextPath}/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=${sysPraiseInfoForm.fdPraisePersonId}"/>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="admire_card_content">
									<p class="admire_info">
										<c:choose>
											<c:when test="${ !isAdmin &&  (not empty sysPraiseInfoForm.fdIsHideName && sysPraiseInfoForm.fdIsHideName eq 'true') && fdUserId ne sysPraiseInfoForm.fdPraisePersonId}">
												<c:out value="${lfn:message('sys-praise:sysPraiseInfo.name.hide') }"/>
											</c:when>
											<c:otherwise>
												<ui:person personId="${sysPraiseInfoForm.fdPraisePersonId}"/>
											</c:otherwise>
										</c:choose>
										<c:choose>
										 	<c:when test="${sysPraiseInfoForm.fdType eq '1'}">
												<bean:message key="sys-praise:sysPraiseInfo.fdType.praise"/>
										 	</c:when>
										 	<c:when test="${sysPraiseInfoForm.fdType eq '2'}">
												<bean:message key="sys-praise:sysPraiseInfo.fdType.rich"/>
										 	</c:when>
										 	<c:when test="${sysPraiseInfoForm.fdType eq '3'}">
												<bean:message key="sys-praise:sysPraiseInfo.fdType.unPraise"/>
										 	</c:when>
										</c:choose>
										${sysPraiseInfoForm.fdTargetPersonName}
										<c:if test="${sysPraiseInfoForm.fdType eq '2'}">
											<c:out value="${sysPraiseInfoForm.fdRich}"/> 
											<bean:message key="sys-praise:sysPraiseInfo.present2"/>
										</c:if>
									</p>
									<p class="admire_cate">
										<span>
											<bean:message key="sys-praise:sysPraiseInfoCategory.fdName"/>
										</span>
										<em>
											<c:choose>
												<c:when test="${empty sysPraiseInfoForm.docCategoryName }">
													<bean:message key="sys-praise:sysPraiseInfoCategory.nullInfo"/>
												</c:when>
												<c:otherwise>
													<c:out value="${sysPraiseInfoForm.docCategoryName}"/>
												</c:otherwise>
											</c:choose>
										</em>
									</p>
								</div>
							</div>
							<div class="admire_card_type">
								<c:choose>
								 	<c:when test="${sysPraiseInfoForm.fdType eq '1'}">
										<span class="type_praise">
											<bean:message key="sys-praise:sysPraiseInfo.fdType.praise"/>
										</span>
								 	</c:when>
								 	<c:when test="${sysPraiseInfoForm.fdType eq '2'}">
										<span class="type_money">
											<bean:message key="sys-praise:sysPraiseInfo.fdType.rich"/>
										</span>
								 	</c:when>
								 	<c:when test="${sysPraiseInfoForm.fdType eq '3'}">
										<span class="type_trample">
											<bean:message key="sys-praise:sysPraiseInfo.fdType.unPraise"/>
										</span>
								 	</c:when>
								 </c:choose>
							</div>
							<div class="admire_card_detail">
								<xform:textarea property="fdReason"/>
							</div>
						</div>
						<!-- 赞赏卡片 Ends -->
						
						<c:if test="${isShowReplyContainer}">
							<!-- 回执信息框 Starts -->
							<div class="lui_admire_receipt">
								<div class="evel_editor_wrap">
									<div class="evel_editor">
										<xform:rtf property="replyContent" showStatus="edit" toolbarSet="Praise" 
												   width="100%" height="70px"></xform:rtf>
									</div>
								</div>
								<div class="evel_opt">
									<div class="evel_opt_item" onClick="replyTextBox()">
										<i class="lui_icon_s icon_reply"></i>
										<span>
											<bean:message key="sys-praise:sysPraiseInfo.common.language"/>
										</span>
										<!-- 常用语弹出框 Starts -->
										<div class="evel_pop" id="replyTextBox">
											<ul>
												<c:if test="${fn:length(replyTextList)==0}">
													<li>
														<span title="<bean:message key="sys-praise:sysPraiseInfo.common.language.null"/>">
															<bean:message key="sys-praise:sysPraiseInfo.common.language.null"/>
														</span>
													</li>
												</c:if>
												<c:if test="${fn:length(replyTextList)>0}">
													<c:forEach var="relyText" items="${replyTextList}">
														<li>
															<span title="${relyText}" onclick="changeRtfValue(this)">${relyText}</span>
														</li>
													</c:forEach>
												</c:if>
											</ul>
										</div>
										<!-- 常用语弹出框 Ends -->
									</div>
									<ui:button onclick="saveReply()" styleClass="evel_btn">
										<bean:message key="sys-praise:sysPraiseInfo.reply"/>
									</ui:button>
								</div>
							</div>
							<!-- 回执信息框 Ends -->
						</c:if>

						<c:if test="${!isShowReplyContainer&& not empty sysPraiseInfoForm.replyContent}">
							<!-- 回执信息展示 Starts -->
							<div class="lui_admire_evel_info">
								<h3 class="evel_title">
									<bean:message key="sys-praise:sysPraiseInfo.reply.message"/>
								</h3>
								<div class="lui_admire_evel_item">
									<div class="admire_portrait">
										<img src="${LUI_ContextPath}/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=${sysPraiseInfoForm.fdTargetPersonId}"/>
									</div>
									<div class="evel_detail">
										<p class="info">
											<ui:person personId="${sysPraiseInfoForm.fdTargetPersonId}"/>
											<span class="admire_date">${sysPraiseInfoForm.replyTime}</span></p>
										<p class="content">${sysPraiseInfoForm.replyContent}</p>
									</div>
								</div>
							</div>
							<!-- 回执信息展示 Ends -->
						</c:if>
						
						<%-- <c:if test="${sysPraiseInfoForm.isReply == 'true' && sysPraiseInfoForm.isReturnCoin == 'true'}">
							<!-- 回赠信息展示 Starts -->
							<div class="lui_admire_evel_info">
								<h3 class="evel_title">回赠信息</h3>
								<div class="lui_admire_evel_item">
									<div class="admire_portrait"><img src="images/portrait.png" alt="" /></div>
									<div class="evel_detail">
										<p class="info">
											<ui:person personId="${sysPraiseInfoForm.fdTargetPersonId}"/>
											<span class="admire_date">${sysPraiseInfoForm.returnTime}</span></p>
										<p class="content">回赠k币</p>
									</div>
								</div>
							</div>
							<!-- 回赠信息展示 Ends -->
						</c:if> --%>

					</div>
				</div>
		<script>
			function replyTextBox(){
				if($('#replyTextBox').is(':hidden')){//如果当前隐藏
					$('#replyTextBox').show();//那么就显示div
				}else{//否则
					$('#replyTextBox').hide();//就隐藏div
				}
			}
			
			function changeRtfValue(target){
				var value = target.innerText;
				CKEDITOR.instances.replyContent.setData(value);
			}
			
			function saveReply(){
				seajs.use(['lui/dialog'], function(dialog){
					var replyContent = CKEDITOR.instances.replyContent.getData();
					var valateText  = replyContent.substring(replyContent.indexOf('>')+1,replyContent.lastIndexOf('<'))
					valateText=valateText.replace(/&nbsp;/ig, "");
					valateText=valateText.replace(/\s/g,"");
					if(!valateText){
						dialog.alert("${lfn:message('sys-praise:sysPraiseInfo.reply.message.pl') }");
						return;
					}
					$.ajax({
						url: '${LUI_ContextPath}/sys/praise/sys_praise_info/sysPraiseInfo.do?method=saveReply',
						method: 'post',
						data: {
							'modelId' : '${sysPraiseInfoForm.fdId}',
							'replyContent' : replyContent
						},
						success: function(data){
							dialog.result(data);
							setTimeout(function(){
								window.location.reload();
							}, 1000);
						},
						error : function(data) {
							dialog.failure(text);
						},
						dataType : "json"
					});
				});
			}
		</script>
	</template:replace>
</template:include>
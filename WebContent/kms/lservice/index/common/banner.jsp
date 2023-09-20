<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.kms.lservice.util.UrlsUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ LUI_ContextPath}/kms/lservice/index/common/style/switch.css?s_cache=${ LUI_Cache }" />
<div class="lui_studyCenter_user_headInfo">
	<!-- 
	<div class="lui_studyCenter_bannerBg">
		<img src="images/banner@2x.png" alt="">
	</div> -->
	
	<%
		String type = request.getParameter("type");
		String imgUrl = "", roleTxt  = "";
		if(StringUtil.isNotNull(type)) {
			imgUrl = "/kms/lservice/index/common/style/img/portrait-" + type + ".png";
			roleTxt = ResourceUtil.getString("kms-lservice:lservice.role." + type);
		}
	%>
	<div class="lui_mainContent">
		<div class="lui_userInfo_basic">
			<div class="lui_userInfo_pic">
				<img src="${LUI_ContextPath}<%=imgUrl%>" alt="<%=roleTxt%>">
			</div>
			<div class="lui_userInfo_txt">
				<h3 class="lui_userInfo_name">
					<%=roleTxt%>：<em title="<%=UserUtil.getUserName(request)%>"><%=UserUtil.getUserName(request)%></em>
				</h3>
				<p onclick="switchRole()">
					<span class="lui_btn  lui_btn_switch"><i></i>${lfn:message('kms-lservice:lservice.index.role.switch') }</span>
				</p>
			</div>
		</div>
		<div class="lui_userInfo_right">
			<div class="lui_userInfo_list">
				<ul>
					<kmss:ifModuleExist path="/kms/credit">
						<kmss:authShow roles="ROLE_KMSCREDIT_DEFAULT">
							<ui:dataview>
								<ui:source type="AjaxJson">
								{url : "/kms/credit/kms_credit_sum_personal/kmsCreditSumPersonal.do?method=getUserSum"}
							</ui:source>
								<ui:render type="Template">
								var sum = 0;
								if(data && data.fdCreditSum >= 0) {
									sum = data.fdCreditSum;
								}
								{$
										<li><a href="${LUI_ContextPath}/kms/credit/student/"
										target="_blank"> {%sum%}</a>
										<p>${lfn:message('kms-lservice:lservice.index.credit') }</p></li>
								$}
							</ui:render>
							</ui:dataview>
						</kmss:authShow>
					</kmss:ifModuleExist>
					<kmss:ifModuleExist path="/kms/loperation">
					<kmss:authShow roles="ROLE_KMSLOPERATION_DEFAULT">
					<ui:dataview>
							<ui:source type="AjaxJson" cfg-timeout="3000">
								{
								url : "/kms/loperation/kms_loperation_stu_total/kmsLoperationStuTotal.do?method=getUserLearnTime"}
							</ui:source>
							<ui:render type="Template">
								var dnum = 0
								if(data && data.time >= 0) {
									dnum = data.time;
								}
								{$
										<li><a  href="${LUI_ContextPath}/kms/loperation/student/" target="_blank">
												{%dnum%}</a>
											<p>${lfn:message('kms-lservice:lservice.index.time') }</p>
										</li>
								$}
							</ui:render>
						</ui:dataview>
					</kmss:authShow>
						
					</kmss:ifModuleExist> 
					<kmss:ifModuleExist path="/kms/medal">
					<kmss:authShow roles="ROLE_KMSMEDAL_DEFAULT">
					<ui:dataview>
							<ui:source type="AjaxJson">
								{url : "/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=getUserMedalNum"}
							</ui:source>
							<ui:render type="Template">
								var dnum = 0
								if(data && data.num >= 0) {
									dnum = data.num;
								}
								{$
										<li><a  href="${LUI_ContextPath}/kms/medal/?type=stu" target="_blank">
												{%dnum%}</a>
											<p>${lfn:message('kms-lservice:lservice.index.medal') }</p>
										</li>
								$}
							</ui:render>
						</ui:dataview>
					</kmss:authShow>
					</kmss:ifModuleExist> 
					<kmss:ifModuleExist path="/kms/diploma">
					<kmss:authShow roles="ROLE_KMSDIPLOMA_DEFAULT">
					<ui:dataview>
							<ui:source type="AjaxJson">
								{url : "/kms/diploma/kms_diploma_ui/kmsDiplomaPerson.do?method=getUserDiplomaNum"}
							</ui:source>
							<ui:render type="Template">
								var dnum = 0
								if(data && data.num >= 0) {
									dnum = data.num;
								}
								{$
										<li><a href="${LUI_ContextPath}/kms/diploma/main/student/" target="_blank">
												{%dnum%}</a>
											<p>${lfn:message('kms-lservice:lservice.index.diploma') }</p>
										</li>
								$}
							</ui:render>
						</ui:dataview>
					</kmss:authShow>
					</kmss:ifModuleExist>
				</ul>
			</div>
			<div class="lui_userInfo_btnGroup">
				<kmss:ifModuleExist path="/kms/learn">
					<a href="${LUI_ContextPath}/kms/learn/main/index.jsp?type=<%=type%>" target="_blank" class="lui_btn">
						${lfn:message('kms-lservice:lservice.index.learnmain') }</a> 
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/train">
					<a href="${LUI_ContextPath}/kms/train/kms_train_plan/index.jsp?type=<%=type%>" target="_blank" class="lui_btn">
						${lfn:message('kms-lservice:lservice.index.train') }</a> 
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/kms/lecturer">
					<a href="${LUI_ContextPath}/kms/lecturer"
					   target="_blank" 
					   class="lui_btn">${lfn:message('kms-lservice:lservice.index.lecturer') }</a>
				</kmss:ifModuleExist>
			</div>
		</div>
		
		<script>
		function switchRole() {
			seajs.use( ['lui/dialog'], function(dialog) {
				dialog.iframe('/kms/lservice/index/common/switch_dialog.jsp?modelName=${JsParam.modelName}&type=${JsParam.type}', 
						"${lfn:message('kms-lservice:lservice.index.role.switch') }",
						 null, 
						 {	
							width:720,
							height:300
						});
			});
		}
		</script>
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.km.review.forms.KmReviewFeedbackInfoForm"%>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.view" canHash="true">
	<template:replace name="title">
		<bean:message bundle="km-review" key="kmReviewFeedbackInfo.detail" />
	</template:replace>
	<template:replace name="head">

		<script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
		<mui:cache-file name="mui-feedbackInfo.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">

	<div style="height: 46px;width: 100%;background: #F5F6FB;">
		<img style="margin-left:20px;border-radius: 50%;width: 30px;height: 30px;vertical-align: middle;" src="${KMSS_Parameter_ContextPath}${feedbackCreatorHeadUrl}" />
		<span style="font-size: 13px;font-weight:400;color: #2A304A;margin-left: 10px;height:46px; line-height:46px;"><c:out 	value="${kmReviewFeedbackInfoForm.docCreatorName}" /></span>
		<span style="float: right;margin-right: 10px;height:46px; line-height:46px;color: #9497A4;font-size: 13px;">
			<%
				KmReviewFeedbackInfoForm  kmReviewFeedbackInfoForm = (KmReviewFeedbackInfoForm) request.getAttribute("kmReviewFeedbackInfoForm");
				String docCreatorTimeStr = kmReviewFeedbackInfoForm.getDocCreatorTime();
				System.out.println(docCreatorTimeStr);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//注意月份是MM
				Date date = simpleDateFormat.parse(docCreatorTimeStr);
				request.setAttribute("kmReviewFeedbackInfoFormDocCreatorTime",date);
			%>
			<kmss:showDate isInterval="true" value="${kmReviewFeedbackInfoFormDocCreatorTime}" type="datetime"></kmss:showDate>


		</span>
	</div>
		<script type="text/javascript">
			function tz(fdId){
				var myhref = "${LUI_ContextPath }/km/review/km_review_main/kmReviewMain.do?method=view&fdId="+fdId
				window.location.href = myhref;
			}
		</script>
	<div id="mainContent" style="overflow: auto;margin-top: 20px;margin-bottom: 20px;">
		<div onclick="tz('${kmReviewFdId}')" style="margin:0 20px;">
			<div style="overflow: auto;">  <%--全部内容--%>
				<div id="xglc" style="width: 100%;background: #F5F6FB;">
					<div style="margin:0 10px;height:100%;">
						<div style="height:30px;line-height:30px;font-size: 13px;color: #AAACB7;letter-spacing: 0;">相关流程</div>
						<div style="margin-top: 10px;height: 0.1px;width: 100%;background: #D4D6DB; "></div>
						<div style="margin-top: 10px;color: #2A304A;font-weight: 400;font-size: 16px;">${kmReviewDocSubject}</div>
						<div style="height:40px;line-height:40px;margin-top: 0px;">
							<img style="float:right;margin-left:1px;margin-top:0px;border-radius: 50%;width: 13px;height: 13px;vertical-align: middle;" src="../mobile/km_review_feedback_info/images/jt.png" />
							<img style="margin-left:1px;border-radius: 50%;width: 20px;height: 20px;vertical-align: middle;" src="${KMSS_Parameter_ContextPath}${kmReviewCreatorHeadUrl}" />
						    <span><c:out 	value="${kmReviewCreatorFdName}" /></span>
						</div>
					</div>
				</div>
				<div style="font-size: 16px;color: #2A304A;margin-top: 10px;font-weight: 400">
					<c:out 	value="${kmReviewFeedbackInfoForm.fdSummary}" />

				</div>
				<div class="automatically" style="margin-top:10px;font-family: PingFangSC-Regular;font-size: 16px;color: #9497A4;letter-spacing: 2.13px;">
					<xform:textarea isLoadDataDict="true" htmlElementProperties="data-actor-expand='true'"  property="docContent" mobile="true" style="height:100%;width:96%" showStatus="view" required="true" subject="反馈内容"></xform:textarea>
				</div>
			</div>
		</div>
	</div>
	<div style="margin-top: 10px;height: 10px;width: 100%;background: #F5F6FB; "></div>
	<div id="content2" style="overflow: auto;margin-top: 20px;margin-bottom: 20px;">
		<div style="margin:0 20px;">
			<div style="font-size: 13px;">
				通知人员
				<c:if test="${ empty  feedbackFdNotifyPeopleList}">
					&nbsp;无
				</c:if>
			</div>
			<div style="margin-top: 10px;">
				<c:forEach items="${feedbackFdNotifyPeopleList }" var="feedbackFdNotify" varStatus="status">
					<div style="text-align:center;word-wrap: break-word;word-break: break-all;overflow: hidden;margin-top:10px;display: inline-block;min-width:70px;height:34px;line-height:34px;background: #F4F5F5;border-radius: 30px;">
						<%--<img style="margin-left:6px;vertical-align: middle;border-radius: 50%;width: 18px;height: 18px;" src="../mobile/km_review_feedback_info/images/txx1.jpeg" />--%>
						<span >${feedbackFdNotify}</span>
					</div>
				</c:forEach>
			</div>
			<div style="margin-top: 10px;height: 1px; width: 100%;border-bottom: 1px solid rgba(231,231,231,0.3); "></div>
			<div style="width:100%;height: 30px;margin-top: 10px;font-size: 13px;line-height: 30px;">
				<div style="float: left;font-weight: 400;font-size: 13px;">通知方式</div>
				<div style="float: right;color: #9497A4;letter-spacing: 0;text-align: right;">
					<kmss:showNotifyType value="${kmReviewFeedbackInfoForm.fdNotifyType}"/>
				</div>
			</div>
		</div>
	</div>

		<div>
			<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formBeanName" value="KmReviewFeedbackInfoForm"/>
				<c:param name="fdKey" value="feedBackAttachment"/>
				<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewFeedbackInfo" />
				<c:param name="fdModelId" value="${kmReviewFeedbackInfoForm.fdId}" />
				<c:param name="fdViewType" value="simple"></c:param>
			</c:import>
		</div>
	</template:replace>
</template:include>



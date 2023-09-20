<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<template:include ref="default.archive" sidebar="no">
<template:replace name="head">
</template:replace>
<template:replace name="title">
	<c:out value="${kmReviewMainForm.docSubject }"></c:out>
</template:replace>
<template:replace name="toolbar">
</template:replace>
<template:replace name="content">
<c:set var="p_defconfig" value="${p_defconfig}" scope="request"/>
<form name="kmReviewMainForm" method="post" action="<c:url value="/km/review/km_review_main/kmReviewMain.do"/>">
<center>
<div class="print_title_header">
<p id="title" class="print_txttitle"><bean:write name="kmReviewMainForm" property="docSubject" /></p>
<div class="printDate">
  <bean:message bundle="km-review" key="kmReviewMain.transferDate" />:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
</div>
</div>
<div id="printTable" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div>
    <div class="tr_label_title"> 
       <div class="title">
      	 <bean:message bundle="km-review" key="kmReviewDocumentLableName.baseInfo" />
       </div>
    </div>
	<table class="tb_normal" width=100%>
		<!--主题-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docSubject" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="docSubject" /></td>
		</tr>
		<!--模板名称-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewTemplate.fdName" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="fdTemplateName" /></td>
		</tr>
		<!--申请人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docCreatorName" /></td>
			<td width=35%><html:hidden name="kmReviewMainForm"
				property="docCreatorId" /> <bean:write name="kmReviewMainForm"
				property="docCreatorName" /></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.fdNumber" /></td>
			<td width=35%><bean:write name="kmReviewMainForm"
				property="fdNumber" /></td>
		</tr>
		<!--部门-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.department" /></td>
			<td><bean:write name="kmReviewMainForm" property="fdDepartmentName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewMain.docCreateTime" /></td>
			<td width=35%><bean:write name="kmReviewMainForm"
				property="docCreateTime" /></td>
		</tr>
		<!--实施反馈人-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="table.kmReviewFeedback" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="fdFeedbackNames" /></td>
	
		</tr>
		<!--关键字-->
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="km-review" key="kmReviewKeyword.fdKeyword" /></td>
			<td colspan=3><bean:write name="kmReviewMainForm"
				property="fdKeywordNames" /></td>
		</tr>
	</table>
</div>

<%-- 审批内容 --%>
<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="km-review" key="kmReviewDocumentLableName.reviewContent" />
	    </div>
    </div>
	
	<c:if test="${kmReviewMainForm.fdUseForm == 'false'}">
		<c:choose>
			<c:when test="${kmReviewMainForm.fdUseWord == 'true'}">
				<table class="tb_normal" width=100%>
					<tr>
						<td colspan="4">
							<div id="reviewButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
		   					</div>
							   <%
									// 金格启用模式
									if (com.landray.kmss.sys.attachment.util.JgWebOffice
											.isJGEnabled()) {
										pageContext.setAttribute("sysAttMainUrl","/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp");
									} else {
										pageContext.setAttribute("sysAttMainUrl","/resource/html_locate/sysAttMain_view.jsp");
							 		}
								%>
								<c:import url="${sysAttMainUrl}" charEncoding="UTF-8">
									<c:param name="fdKey" value="mainContent" />
									<c:param name="fdAttType" value="office" />
									<c:param name="fdModelId" value="${kmReviewMainForm.fdId}" />
									<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewMain" />
									<c:param name="formBeanName" value="kmReviewMainForm" />
									<c:param name="isReadOnly" value="true"/>
									<c:param name="buttonDiv" value="reviewButtonDiv" />
									<c:param name="isToImg" value="false"/>
									<c:param  name="attHeight" value="580"/>
								</c:import>
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<table id="info_content" class="tb_normal" width=100% >
					<tr>
						<td colspan="4">
							${kmReviewMainForm.docContent}
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="km-review" key="kmReviewMain.attachment" />
						</td>
						<td colspan=3>
							<c:import url="/resource/html_locate/sysAttMain_view.jsp" charEncoding="UTF-8">
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
		<table id="info_content" width=100% >
			<tr>
				<td id="_xform_detail">
					<%-- 表单 --%>
					<c:import url="/resource/html_locate/sysForm.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="kmReviewMainForm" />
						<c:param name="fdKey" value="reviewMainDoc" />
						<c:param name="messageKey" value="km-review:kmReviewDocumentLableName.reviewContent" />
						<c:param name="useTab" value="false" />
						<c:param name="isPrint" value="true" />
					</c:import>
				</td>
			</tr>
		</table>
	 </c:if>
</div>
<%-- 审批记录 --%>
<c:if test="${saveApproval }">
<div>
    <div class="tr_label_title">
	    <div class="title">
	       <bean:message bundle="km-review" key="kmReviewMain.flow.trail" />
	    </div>
    </div>
	<table width=100%>
		<!-- 审批记录 -->
		<tr>
			<td colspan=4>
				<c:import url="/resource/html_locate/lbpmAuditNote.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmReviewMainForm" />
				</c:import>
			</td>
		</tr>
	</table>
</div>
</c:if>
</div>
</div>


</center>
</form>
</template:replace>
		
</template:include>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<ui:ajaxtext>
	<%-- 此处为浏览器窗口标题 --%>
	<div data-dojo-block="title">
	   <c:out value="${sysNewsMainForm.docSubject}"></c:out>
	</div>
	<%--此处为内容 --%>
	<div data-dojo-block="content">
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin" class="muiFlowBack">
			<c:if test="${sysNewsMainForm.docStatus < '30' }">
				<c:set var="newsPublishUnit" value=""></c:set>
				<c:if test="${sysNewsMainForm.fdIsWriter eq false}">
					<c:set var="newsPublishUnit" value="${sysNewsMainForm.fdDepartmentName}"></c:set>
				</c:if>
				<script type="text/javascript"> 
					window.docSubject = "${lfn:escapeHtml(sysNewsMainForm.docSubject)}"; <%-- 标题  --%>
					window.fdIsWriter = "${lfn:escapeJs(sysNewsMainForm.fdIsWriter)}";
					window.authorName = "${lfn:escapeJs(sysNewsMainForm.fdAuthorName)}";
					window.writerName = "${lfn:escapeJs(sysNewsMainForm.fdWriter)}";
					window.headerItemDatas = [
						{name: '<bean:message bundle="sys-news" key="sysNewsMain.publisher" />', value: window.fdIsWriter=="true"?window.writerName:window.authorName}, <%-- 作者  --%>
						{name: '<bean:message bundle="sys-news" key="sysNewsMain.publishUnit" />', value: '${newsPublishUnit}'}, <%-- 所属部门  --%>
						{name: '<bean:message bundle="sys-news" key="sysNewsMain.docCreateTime" />', value: '${sysNewsMainForm.docCreateTime}'} <%-- 创建时间  --%>
					];
				</script>
				<div data-dojo-type="mui/header/DocViewHeader" data-dojo-props='subject:window.docSubject,
					 userId:"${sysNewsMainForm.fdCreatorId}",
					 userName:"${sysNewsMainForm.fdCreatorName}",
					 docStatus:"${sysNewsMainForm.docStatus}",
					 itemDatas:window.headerItemDatas'>
				</div>
			</c:if>
			<c:if test="${sysNewsMainForm.docStatus == '30' }">
				<div class="muiDocSubjectMain">
					<div class="muiDocSubject">
						<c:out value="${sysNewsMainForm.docSubject}" />
					</div>
					<div class="muiDocInfo">
						<span> 
						     <c:if test="${sysNewsMainForm.fdIsWriter==true}">
								<c:out value="${sysNewsMainForm.fdWriter}" />
							 </c:if> 
							 <c:if test="${sysNewsMainForm.fdIsWriter==false}">
								<c:out value="${sysNewsMainForm.fdAuthorName}" />
								 <c:out value="${sysNewsMainForm.fdDepartmentName}"></c:out>
							</c:if>
						</span>
						<c:if test="${sysNewsMainForm.docStatus >= '30' }">
							<span class="muiTimeView"> 
								${sysNewsMainForm.docPublishTime}
							</span>
						</c:if>
						<c:if test="${sysNewsMainForm.docStatus >= '30' }">
							<i class="fontmuis muis-views muiViewsNum"></i>
							<span><c:out value="${sysNewsMainForm.docReadCount}" /></span>
						</c:if>
					</div>
				</div>
			</c:if>
			
			<c:if test="${sysNewsMainForm.docStatus < '30' }">
			<div data-dojo-type="mui/panel/NavPanel">
			</c:if>
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-mobile" key="mui.mobile.info" />'">
					<div>
						<%-- <div class="muiDocSubject">
							<bean:write	name="sysNewsMainForm" property="docSubject" />
						</div> --%>
						<c:if test="${sysNewsMainForm.fdDescription!=null && sysNewsMainForm.fdDescription!='' }">
							<div class="muiDocSummary">
								<div class="muiDocSummarySign">
									<bean:message bundle="sys-news" key="sysNewsMain.docDesc" />
								</div>
								<xform:textarea property="fdDescription"></xform:textarea>
							</div>	
						</c:if>
						<div class="muiDocContent" id="contentDiv">
							<c:choose>
								<c:when test="${not empty sysNewsMainForm.fdIsLink}">
								   <!--发布机制链接-->
									<bean:message bundle="sys-news" key="SysNewsMain.linkNews" />
									<a href='<c:url value="${sysNewsMainForm.fdLinkUrl}"/>' class="muiLink"/>
								        <c:out value="${sysNewsMainForm.docContent}"/>
									</a>
								</c:when>
								<c:otherwise>
									<c:if test="${sysNewsMainForm.fdContentType=='rtf'}">
										<xform:rtf property="docContent" mobile="true"></xform:rtf>
									</c:if>
										<!-- 附件上传模式 -->
									<c:if test="${sysNewsMainForm.fdContentType=='att'  && fn:length(sysNewsMainForm.attachmentForms['newsMain'].attachments)>0}">
										<c:forEach items="${sysNewsMainForm.attachmentForms['newsMain'].attachments}" var="sysAttMain"	varStatus="vstatus">
											<c:set var="attId" value="${sysAttMain.fdId}"/>
											<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
										</c:forEach>
									 <%
									   //取fdAttMainId的值判断附件是否已经转换
									   if(JgWebOffice.isExistViewPath(request)){ 
									  %>
									 <c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
										<c:param name="formName" value="sysNewsMainForm"></c:param>
										<c:param name="fdKey" value="newsMain"></c:param>
										<c:param name="contentFlag"  value="true"/>
										<c:param name="editContent" value="false"></c:param>
									</c:import>
										<%
							 		  }else{
							  			%>
						  			<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
											<c:param name="formName" value="sysNewsMainForm"/>
											<c:param name="fdKey" value="newsMain"></c:param>
											<c:param name="fdMulti" value="false"></c:param>
									</c:import> 
						  			<%} %>
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>	
					</div>
					<div>
					   <c:if test="${not empty sysNewsMainForm.fdLinkUrl and not empty sysNewsMainForm.fdModelId}">
						   <c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="${sysNewsMainForm.fdModelId}" />
							</c:import>
						</c:if>
					</div>
					<c:if test="${sysNewsMainForm.docStatus eq '20' }">
						<c:if test="${hasImage eq true}">
							<div class="muiTabStyle">${lfn:message('sys-news:sysNewsMain.fdMainPicture')}</div>
						    <c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="Attachment" />
								<c:param name="fdAttType" value="byte" />
								<c:param name="fdViewType" value="simple" />
								<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
						</c:if>
					</c:if>
					<%-- 附件 --%>
					<div>
						<c:set var="_attForms" value="${sysNewsMainForm.attachmentForms['fdAttachment']}" />
					 	<c:if test="${_attForms!=null && fn:length(_attForms.attachments)>0}">
					 		<c:set var="hasAttachments" value="true" />
					 	</c:if>
						<c:if test="${empty sysNewsMainForm.fdIsLink && sysNewsMainForm.fdContentType=='word'}">
							<c:set var="attForms" value="${sysNewsMainForm.attachmentForms['editonline']}" />
						 	<c:if test="${attForms!=null && fn:length(attForms.attachments)>0}">
						 		<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
						 			<c:set var="attMainId" value="${sysAttMain.fdId }"></c:set>
						 			<%
										SysAttMain sysAttMain = (SysAttMain) pageContext
														.getAttribute("sysAttMain");
										String path = SysAttViewerUtil.getViewerPath(
												sysAttMain, request);
										if (StringUtil.isNotNull(path)){
											pageContext.setAttribute("hasThumbnail", "true");
											pageContext.setAttribute("hasViewer", "true");
										}
										pageContext.setAttribute("_sysAttMain", sysAttMain);
									%>
						 		</c:forEach>
						 	</c:if>
							<c:if test="${not empty _sysAttMain.fdId }">
								<c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="sysNewsMainForm"></c:param>
									<c:param name="fdKey" value="editonline"></c:param>
								</c:import> 
							</c:if>
						</c:if>
						<c:if test="${hasAttachments}">
							<div class="muiTabStyle">附件</div>
							<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="sysNewsMainForm"></c:param>
								<c:param name="fdKey" value="fdAttachment"></c:param>
								<c:param name="fdViewType" value="simple"></c:param>
							</c:import>
						</c:if>
					</div>
				</div>
				<c:if test="${sysNewsMainForm.docStatus < '30' }">
					<%--参考流程管理 --%>
					<div data-dojo-type="mui/panel/DelayContent" data-dojo-props="title:'<bean:message bundle="sys-mobile" key="mui.mobile.review.record" />'">
						<div class="muiFormContent">
							<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${sysNewsMainForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain"/>
								<c:param name="formBeanName" value="sysNewsMainForm"/>
							</c:import>
						</div>
					</div>
				</c:if>
			<c:if test="${sysNewsMainForm.docStatus < '30' }">
			</div>
			</c:if>
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
	            docStatus="${sysNewsMainForm.docStatus}" 
				formName="sysNewsMainForm"
				viewName="lbpmView"
				allowReview="true">
				<template:replace name="flowArea">
					<c:if test="${sysNewsMainForm.fdContentType!='word'}">
						<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
							<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="colSize:4,href:'/sys/news/mobile/edit.jsp?fdId=${param.fdId}'">
								<bean:message key="button.edit"/>
							</li>
						</kmss:auth>
					</c:if>
					<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelName" value="${sysNewsMainForm.modelClass.name}"></c:param>
						<c:param name="fdModelId" value="${sysNewsMainForm.fdId}"></c:param>
						<c:param name="fdSubject" value="${sysNewsMainForm.docSubject}"></c:param>
						<c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark')}"></c:param>
						<c:param name="showOption" value="label"></c:param>
					</c:import>
					<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="sysNewsMainForm"></c:param>
			    		<c:param name="showOption" value="label"></c:param>
					</c:import>
				</template:replace>
				<template:replace name="publishArea">
					<c:if test="${sysNewsMainForm.fdContentType!='word'}">
						<kmss:auth requestURL="/sys/news/sys_news_main/sysNewsMain.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
							<li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="colSize:4,href:'/sys/news/mobile/edit.jsp?fdId=${param.fdId}'">
								<bean:message key="button.edit"/>
							</li>
						</kmss:auth>
					</c:if>
					<c:if test="${sysNewsMainForm.fdCanComment ne 'false'}">
						<c:import url="/sys/evaluation/mobile/import/view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm"></c:param>
							<c:param name="showOption" value="label"></c:param>
						 </c:import>
					</c:if>
					<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelName" value="${sysNewsMainForm.modelClass.name}"></c:param>
						<c:param name="fdModelId" value="${sysNewsMainForm.fdId}"></c:param>
						<c:param name="fdSubject" value="${sysNewsMainForm.docSubject}"></c:param>
						<c:param name="label" value="${lfn:message('sys-bookmark:button.bookmark')}"></c:param>
						<c:param name="showOption" value="label"></c:param>
					</c:import>
					<c:import url="/sys/relation/mobile/import/view.jsp" charEncoding="UTF-8">
			    		<c:param name="formName" value="sysNewsMainForm"></c:param>
			    		<c:param name="showOption" value="label"></c:param>
					</c:import>
				</template:replace>
			</template:include>
		</div>

		<%-- 钉钉图标 --%>
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysNewsMainForm" />
			</c:import>
		</kmss:ifModuleExist>
		<%-- 钉钉图标 end --%>
		<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="sysNewsMainForm" />
			<c:param name="fdKey" value="newsMainDoc" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
		</c:import>
		<%-- 分享机制  --%>
		<kmss:ifModuleExist path="/third/ywork/">
			 <c:import url="/third/ywork/ywork_share/yworkDoc_mobile_share.jsp"
				charEncoding="UTF-8">
				<c:param name="modelId" value="${sysNewsMainForm.fdId}" />
				<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
				<c:param name="templateId" value="${ sysNewsMainForm.fdTemplateId}" />
				<c:param name="allPath" value="${ sysNewsMainForm.fdTemplateName}" />
			</c:import>
		</kmss:ifModuleExist>
	</div>
</ui:ajaxtext>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>

<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<%-- <ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" toggle="false"> --%>
		<!--标题-->
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<c:if test="${sysNewsMainForm.fdIsTop==true}">
					<i class="lui_article_status_top_border lui_article_status_top"><bean:message key="news.fdIsTop.true" bundle="sys-news"/></i>
				</c:if>
				<bean:write name="sysNewsMainForm" property="docSubject" />
			</div>
			<div class='lui_form_baseinfo'>
				<!--发布日期-->
				<c:if test="${ not empty sysNewsMainForm.docPublishTime }">
					<span><bean:write name="sysNewsMainForm"
							property="docPublishTime" /></span>
				</c:if>
				<!--作者-->
				<bean:message bundle="sys-news" key="sysNewsMain.fdAuthorId" />
				：
				<c:if test="${sysNewsMainForm.fdIsWriter==false}">
					<span><ui:person personId="${sysNewsMainForm.fdAuthorId}"
							personName="${sysNewsMainForm.fdAuthorName}" layer="true"></ui:person></span>
				</c:if>
				<c:if test="${sysNewsMainForm.fdIsWriter==true}">
					<c:out value="${sysNewsMainForm.fdWriter}"></c:out>
				</c:if>
				<%--新闻来源 --%>
				<c:if test="${not empty sysNewsMainForm.fdNewsSource}">
					<bean:message bundle="sys-news" key="sysNewsMain.fdNewsSourceOnly" />：<bean:write
						name="sysNewsMainForm" property="fdNewsSource" />
				</c:if>
			</div>
		</div>
		<!--内容-->
		<!-- 摘要 -->
		<c:if test="${ not empty sysNewsMainForm.fdDescription }">
			<div class="lui_form_summary_frame" style="text-indent: 0;">
				<font color="#003048"> 
					<c:out value="${sysNewsMainForm.fdDescription}"></c:out>
				</font>
			</div>
		</c:if>
		<div class="lui_form_content_frame clearfloat"
			id="lui_form_content_frame">
			<div style="min-height: 150px;" id="contentDiv">
				<c:choose>
					<c:when test="${not empty sysNewsMainForm.fdIsLink}">
						<!--发布机制链接-->
						<bean:message bundle="sys-news" key="SysNewsMain.linkNews" />
						<a href='<c:url value="${sysNewsMainForm.fdLinkUrl}"/>'
							class="com_subject" />
						<c:out value="${sysNewsMainForm.docContent}" />
						</a>
					</c:when>
					<c:otherwise>
						<c:if test="${sysNewsMainForm.fdContentType=='rtf'}">
							<xform:rtf property="docContent"></xform:rtf>
						</c:if>
						<c:if test="${sysNewsMainForm.fdContentType=='word'}">
							<%
								if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()) {
							%>
							<div id="missiveButtonDiv"
								style="text-align: right; padding-bottom: 5px">&nbsp;</div>
							<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="editonline" />
								<c:param name="fdAttType" value="office" />
								<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="buttonDiv" value="missiveButtonDiv" />
								<c:param name="isExpand" value="true" />
								<c:param name="showToolBar" value="false" />
							</c:import>
							<%
								} else {
							%>
								${sysNewsMainForm.fdHtmlContent}
							<%
								}
							%>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<!--附件-->
			<c:if
				test="${not empty sysNewsMainForm.autoHashMap['fdAttachment'].attachments}">
				<div class="lui_form_spacing"></div>
				<div>
					<div class="lui_form_subhead">
						<img
							src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
						${ lfn:message('sys-news:tip.news.download.attachment') }(${fn:length(sysNewsMainForm.attachmentForms['fdAttachment'].attachments)})
					</div>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="sysNewsMainForm" />
						<c:param name="fdKey" value="fdAttachment" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
					</c:import>
				</div>
			</c:if>
			<br>
			<!-- 盖章文件 -->
			 	<c:if test="${sysNewsMainForm.fdSignEnable}">
				 	<c:if test="${not empty sysNewsMainForm.autoHashMap['yqqSigned'].attachments}">
				 		<div class="lui_form_spacing"></div>
					 	<div>
					 		<div class="lui_form_subhead">
								<img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
								${ lfn:message('sys-news:sysNewsSummary.yqqSignFile') }(${fn:length(sysNewsMainForm.attachmentForms['yqqSigned'].attachments)})
							</div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="yqqSigned" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
						</div>
				 	</c:if>
			 		<c:if test="${not empty sysNewsMainForm.autoHashMap['fdSignFile'].attachments}">
			 			<div class="lui_form_spacing"></div>
					 	<div>
					 		<div class="lui_form_subhead">
								<img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
								${ lfn:message('sys-news:sysNewsSummary.fdSignFile') }(${fn:length(sysNewsMainForm.attachmentForms['fdSignFile'].attachments)})
							</div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
		
						</div>
			 		</c:if>
			 	</c:if>
			
			<c:if test="${param.approveModel ne 'right'}">
				<div style="padding-left: 5px">
					<!-- 标签机制 -->
					<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="utf-8">
						<c:param name="formName" value="sysNewsMainForm" />
						<c:param name="useTab" value="false"></c:param>
					</c:import>
				</div>
			</c:if>
		</div>
		<%-- </ui:content> --%>
	</c:when>
	<c:when test="${ param.contentType eq 'share' }">
		<!-- 分享机制 -->
		<kmss:ifModuleExist path="/third/ywork/">
			<c:import url="/third/ywork/ywork_share/yworkDoc_share.jsp"
				charEncoding="UTF-8">
				<c:param name="modelId" value="${sysNewsMainForm.fdId}" />
				<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
				<c:param name="templateId" value="${ sysNewsMainForm.fdTemplateId}" />
				<c:param name="allPath" value="${ sysNewsMainForm.fdTemplateName}" />
				<c:param name="readRecord" value="true" />
				<c:param name="shareRecord" value="true" />
			</c:import>
		</kmss:ifModuleExist>
	</c:when>
	<c:when test="${ param.contentType eq 'info' }">
		<ui:content title="${lfn:message('sys-news:sysNewsMain.baseInfo')}" toggle="false">
			<ul class='lui_form_info'>
				<li><bean:message bundle="sys-news" key="sysNewsMain.fdAuthorId" />：
				<ui:person personId="${sysNewsMainForm.fdCreatorId}" personName="${sysNewsMainForm.fdCreatorName}" layer="true"></ui:person></li>
				<li><bean:message bundle="sys-news" key="sysNewsMain.fdDepartmentId" />：<bean:write	 name="sysDocBaseInfoForm" property="fdDepartmentName" /></li>
				<%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
					<li><bean:message bundle="sys-authorization" key="sysAuthArea.authArea" />：${ sysNewsMainForm.authAreaName }</li>
				<%} %>
				<li><bean:message bundle="sys-news" key="sysNewsPublishMain.fdImportance" />：<sunbor:enumsShow	value="${sysNewsMainForm.fdImportance}"	enumsType="sysNewsMain_fdImportance" /></li>
				<li><bean:message bundle="sys-news" key="sysNewsPublishMain.docStatus" />：<sunbor:enumsShow	value="${sysNewsMainForm.docStatus}"	enumsType="news_status" /></li>
			  	<li>${lfn:message("sys-news:sysNewsMain.fdMainPicture") }：</li>
			  <c:if test="${hasImage eq true}">  
			    <li>
				    <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="Attachment" />
						<c:param name="fdMulti" value="false" />
						<c:param name="fdAttType" value="pic" />
						<c:param name="fdImgHtmlProperty" value="width=120" />
						<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
						<%-- 图片设定大小 --%>
						<c:param name="picWidth" value="258" />
						<c:param name="picHeight" value="192" />
						<c:param name="proportion" value="false" />
						<c:param name="fdLayoutType" value="pic"/>
						<c:param name="fdPicContentWidth" value="258"/>
						<c:param name="fdPicContentHeight" value="192"/>
						<c:param name="fdViewType" value="pic_single"/>
						<c:param name="fdShowMsg">true</c:param>
					</c:import>
			    </li>
			</c:if>
			</ul>
		</ui:content>
	</c:when>
</c:choose>
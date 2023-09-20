<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page	import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeConstantUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page	import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page	import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc"%>
<%@page	import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeMainAuthor"%>
<%@page	import="com.landray.kmss.sys.doc.model.SysDocAuthor"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page
	import="com.landray.kmss.kms.category.model.KmsCategoryConfig"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>	
<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory') }"></c:set>	
<%
	KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
	String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
	if ("true".equals(kmsCategoryEnabled)) {
%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>	
	<c:set var="kmsKnowledgeBaseDocListDocCategory" value="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docCategory.categoryTrue') }"></c:set>
<%
	}
%>
<%
	ISysAttMainCoreInnerService sysAttMainCoreInnerService = (ISysAttMainCoreInnerService) SpringBeanUtil
			.getBean("sysAttMainService");

	ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
			.getBean("sysOrgCoreService");
%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }" mobile="true">
		<list:data-column property="fdId">
		</list:data-column>
		<!-- 文档类型 -->
		<list:data-column property="fdKnowledgeType" />
		<list:data-column col="label"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}" escape="false">
			<c:if test="${item.docIsIndexTop==true}">
				<span class="muiTitleStatusTop">${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.setTop')}</span>
			</c:if>
			<c:out value="${item.docSubject}"></c:out> 
		</list:data-column>	
		<!-- label图标 -->		
		<list:data-column col="labelIcon" title="置顶" escape="false">
			<c:if test="${item.docIsIndexTop==true}">
				<span class="muiTitleStatusTop">${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.setTop')}</span>
			</c:if>
		</list:data-column>	
		<!-- label文本 -->
		<list:data-column col="labelText"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docSubjects')}" >		
			<c:out value="${item.docSubject}"></c:out> 
		</list:data-column>
		<!-- 描述 -->
		<list:data-column col="summary" property="fdDescription"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.fdDescription')}">
		</list:data-column>
		<!--缩略图-->
		<list:data-column col="icon" title="icon" escape="false">
			<c:if test="${loadImg == true}">
				<%
					Object basedocObj = pageContext.getAttribute("item");
					if(basedocObj != null) {
						KmsKnowledgeBaseDoc basedoc = (KmsKnowledgeBaseDoc)basedocObj;
						out.print(KmsKnowledgeUtil.getImgUrl(basedoc));
					}
				%>
			</c:if>
		</list:data-column>
		<!-- 创建者 -->
		<list:data-column col="creator"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }"
			escape="false">
			<c:if test="${not empty item.fdDocAuthorList }">
			     <%
					KmsKnowledgeBaseDoc basedocObj = (KmsKnowledgeBaseDoc) pageContext
											.getAttribute("item");
			        if(basedocObj.getFdDocAuthorList().size()>0){
                        for(SysDocAuthor entity:basedocObj.getFdDocAuthorList()){
                        	SysOrgElement element = sysOrgCoreService
									.findByPrimaryKey(entity.getFdSysOrgElement().getFdId());
							out.print(element.getFdName()+" ");
                        }
			        }			
				  %>
			</c:if>
			
			<c:if test="${empty item.fdDocAuthorList }">
				<c:out value="${item.outerAuthor}"/>
			</c:if>
		</list:data-column>

		<!-- 创建时间 -->
		<list:data-column col="created"
			title="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }">
			<kmss:showDate value="${item.docPublishTime}" type="date" />
		</list:data-column>

		<!-- 阅读量 -->
		<list:data-column col="count"
			title="${lfn:message('kms-knowledge:kmsKnowledge.read') }"
			escape="false">
			<c:if test="${item.docStatus=='30' ||item.docStatus=='40'}">
				${not empty item.fdTotalCount ? item.fdTotalCount : 0}
			</c:if>
		</list:data-column>
		<%--链接，这里在某些浏览器下点击跳转会莫名其妙弹出下载框，所以不做跳转 --%>
		<list:data-column col="href" escape="false">
			<c:if test="${item.fdKnowledgeType eq '1' }">
				/kms/multidoc/mobile/view.jsp?fdId=${item.fdId}
			</c:if>
			<c:if test="${item.fdKnowledgeType eq '2' }">
				/kms/wiki/mobile/view.jsp?fdId=${item.fdId}
			</c:if>
		</list:data-column>
		<!--是否精华-->
		<list:data-column col="status" escape="false">
			<c:if test="${item.docIsIntroduced==true}">
				<span class="muiTitleStatusPrime">${lfn:message('kms-knowledge:kms.knowledge.4m.introduce') }</span>
			</c:if>
		</list:data-column>
		<!--评分-->
		<list:data-column col="score"
			title="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
			escape="false">
			<c:if test="${ not empty item.docScore }">
			   <%-- <c:out value="${item.docScore}"/>分 --%>
			   [${item.docScore}分]
			</c:if>
			<c:if test="${ empty item.docScore }">
			  [--分]
			</c:if>
		</list:data-column>
		<!--分类-->
		<list:data-column col="category"
			title="${kmsKnowledgeBaseDocListDocCategory}"
			escape="false">
			<c:out value="${item.docCategory.fdName}"/>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>

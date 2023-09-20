<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page	import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@page	import="com.landray.kmss.kms.category.model.KmsCategoryKnowledgeRelInfo"%>
<%@ page import="com.landray.kmss.kms.category.model.KmsCategoryConfig" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<c:set var="kmsCategoryEnabled" value="false"></c:set>
<c:if test="${kms_professional}">
	<%
		KmsCategoryConfig kmsCategoryConfig = new KmsCategoryConfig();
		String kmsCategoryEnabled = (String) kmsCategoryConfig.getDataMap().get("kmsCategoryEnabled");
		if ("true".equals(kmsCategoryEnabled)) {
	%>
	<c:set var="kmsCategoryEnabled" value="true"></c:set>
	<%
		}
	%>
</c:if>
<list:data>
    <list:data-columns var="item" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdMainId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdId">
            ${item.fdMainId}
        </list:data-column>
		<list:data-column property="docSubject" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docSubject')}" />
<%--         <list:data-column property="docTemplate" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.docTemplate')}" style="style: 20%"/> --%>
		
		<c:if test="${kmsCategoryEnabled == true}">
        <list:data-column col="docTemplate" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory') }">
			<%
				Object obj = pageContext.getAttribute("item");
				if (obj != null) {
					KmsCategoryKnowledgeRelInfo baseModel = (KmsCategoryKnowledgeRelInfo) obj;
					out.print(KmsKnowledgeUtil.getCategoryTreeString(baseModel.getFdMainId()));
				}
			%>
		</list:data-column>
		</c:if>
		<c:if test="${kmsCategoryEnabled == false}">
        <list:data-column col="docTemplate" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.docCategory.categoryTrue') }">
			<%
				Object obj = pageContext.getAttribute("item");
				if (obj != null) {
					KmsCategoryKnowledgeRelInfo baseModel = (KmsCategoryKnowledgeRelInfo) obj;
					out.print(KmsKnowledgeUtil.getCategoryTreeString(baseModel.getFdMainId()));
				}
			%>
		</list:data-column>
		</c:if>
		<list:data-column col="icon" escape="false" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}">
		  	 <c:if test="${docMessageMap != null && not empty docMessageMap && not empty docMessageMap[item.fdMainId]['docIsIntroduced'] && docMessageMap[item.fdMainId]['docIsIntroduced'] ==true}">
		  	 	<span class="lui_icon_s lui_icon_s_icon_essence" title="${lfn:message('kms-knowledge:kmsKnowledge.introduced')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='20'}">
			 	<span class="lui_icon_s lui_icon_s_icon_examine" title="${lfn:message('status.examine')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='10'}">
			 	<span class="lui_icon_s lui_icon_s_icon_draft" title="${lfn:message('status.draft')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='00'}">
			 	<span class="lui_icon_s lui_icon_s_icon_discard" title="${lfn:message('status.discard')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='11'}">
			 	<span class="lui_icon_s lui_icon_s_icon_refuse" title="${lfn:message('status.refuse')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='40'}">
			 	<span class="lui_icon_s lui_icon_s_icon_expire" title="${lfn:message('status.expire')}"></span>
			 </c:if>
			 <c:if test="${item.docStatus=='25'}">
			 	<span class="lui_icon_s lui_icon_s_icon_waitpublish" title="${ lfn:message('kms-common:kmsDocStatus.waitpublish') }"></span>
			 </c:if>
		</list:data-column>
        <list:data-column property="fdSourceType" title="${lfn:message('kms-category:kmsCategoryKnowledgeRel.fdSourceType')}" />
    
        <list:data-column col="linkStr" escape="false">
			${item.linkStr}
		</list:data-column>
        <list:data-column col="docReadTime" title="${lfn:message('kms-category:kmsCategoryKnowledge.docReadTime')}">
			<kmss:showDate value="${item.docCreateTime}" type="date" />
		</list:data-column>
		
		<list:data-column col="docPublishTime"
			title="${lfn:message('kms-knowledge:kmsKnowledge.docPublishTime') }">
			<kmss:showDate value="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docPublishTime']  ?  docMessageMap[item.fdMainId]['docPublishTime'] : ''}" type="date" />
		</list:data-column>
		
		<list:data-column col="fdDescription"
			title="${ lfn:message('kms-knowledge:kmsKnowledgeCategory.fdDescription')}" escape="false">
			<c:out value="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['fdDescription']  ?  docMessageMap[item.fdMainId]['fdDescription'] : ''}"></c:out>
		</list:data-column>
		<list:data-column col="docIntrCount"
			title="${lfn:message('kms-knowledge:kmsKnowledge.intrCount') }" escape="false">
			<span class="com_number" title="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docIntrCount'] ?  docMessageMap[item.fdMainId]['docIntrCount'] : 0}">
				${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docIntrCount']  ?  docMessageMap[item.fdMainId]['docIntrCount'] : 0}
			</span>
		</list:data-column>
		
		<list:data-column col="docEvalCount"
			title="${lfn:message('kms-knowledge:kmsKnowledge.evalCount') }" escape="false">
			<span class="com_number" title="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docEvalCount'] ?  docMessageMap[item.fdMainId]['docEvalCount'] : 0}">
				${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docEvalCount']  ?  docMessageMap[item.fdMainId]['docEvalCount'] : 0}
			</span>
		</list:data-column>
		
		<list:data-column col="fdTotalCount"
			title="${lfn:message('kms-knowledge:kmsKnowledge.read') }" escape="false">
			<span class="com_number" title="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['fdTotalCount'] ?  docMessageMap[item.fdMainId]['fdTotalCount'] : 0}">
				${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['fdTotalCount']  ?  docMessageMap[item.fdMainId]['fdTotalCount'] : 0}
			</span>
		</list:data-column>
		
		<list:data-column
			title="${lfn:message('kms-knowledge:kmsKnowledge.score') }"
			col="docScore" escape="false">
			<span class="com_number" title="${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docScore'] ?  docMessageMap[item.fdMainId]['docScore'] : '0.0'}">
				${docMessageMap != null && not empty docMessageMap && not empty  docMessageMap[item.fdMainId]['docScore']  ?  docMessageMap[item.fdMainId]['docScore'] : '0.0'}
			</span>
		</list:data-column>
		
		<list:data-column col="docAuthor.fdName"
			title="${lfn:message('kms-knowledge:kmsKnowledgeBaseDoc.list.docAuthor') }" escape="false">
			
			<c:choose>
				<c:when test="${docMessageMap != null && docMessageMap[item.fdMainId]  != null && docMessageMap[item.fdMainId]['fdDocAuthorList'].size()>0 }">
				   <c:forEach var="obj"  items="${ docMessageMap[item.fdMainId]['fdDocAuthorList'] }" varStatus="status">
				        <c:choose>
				           <c:when test="${ status.count == docMessageMap[item.fdMainId]['fdDocAuthorList'].size() }">
				              <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person>
				           </c:when>
				           <c:otherwise>
				              <ui:person personId="${obj.fdSysOrgElement.fdId}" personName="${obj.fdSysOrgElement.fdName}"></ui:person> / 
				           </c:otherwise>
				        </c:choose>
				   </c:forEach>
				</c:when>
				<c:otherwise>
					 <a class="com_outer_author" href="javascript:;">
						 <c:out value="${docMessageMap != null && docMessageMap[item.fdMainId] != null && not empty docMessageMap[item.fdMainId]['outerAuthor']?  docMessageMap[item.fdMainId]['outerAuthor'] : ''  }"/>
					 </a>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		
		<%--多作者头像--%>
		<list:data-column col="docAuthor.fdAuthorImageUrl">
		     <c:choose>
		        <c:when test="${docMessageMap != null && docMessageMap[item.fdMainId]  != null && docMessageMap[item.fdMainId]['fdDocAuthorList'] != null}">
			        <c:if test="${docMessageMap[item.fdMainId]['fdDocAuthorList'].size()>1}">
		                 /sys/person/resource/images/head_image.png
		            </c:if>
		            <c:if test="${docMessageMap[item.fdMainId]['fdDocAuthorList'].size() == 1}">
		            	<person:headimageUrl personId="${docMessageMap[item.fdMainId]['fdDocAuthorList'][0].fdSysOrgElement.fdId}" size="m" />
		            </c:if>
		        </c:when>
		        <c:otherwise>
		            /sys/person/resource/images/head_image.png
		        </c:otherwise>
		     </c:choose>
		</list:data-column>

		<list:data-column col="docAuthorId">
		   <c:if test="${docMessageMap != null && docMessageMap[item.fdMainId] != null && not empty docMessageMap[item.fdMainId]['fdDocAuthorList'] }">
		      <c:forEach var="obj1"  items="${ docMessageMap[item.fdMainId]['fdDocAuthorList'] }">
		        <c:out value="${obj1.fdSysOrgElement.fdId}"/>
		      </c:forEach>
		  </c:if>
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

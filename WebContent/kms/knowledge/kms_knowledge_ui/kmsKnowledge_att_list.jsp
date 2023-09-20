<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.sunbor.web.tag.Page"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@page import="com.landray.kmss.kms.knowledge.service.IKmsKnowledgeBaseDocService"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.kms.knowledge.util.KmsKnowledgeUtil"%>
<%@page import="com.landray.kmss.kms.knowledge.borrow.util.KmsKnowledgeBorrowUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>

<%
	Page attPage = (Page) request.getAttribute("attPage");
	JSONObject attCreatorJson = new JSONObject();
	JSONObject attFileSize = new JSONObject();
	ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
			.getBean("sysOrgElementService");
	SysOrgElement deptOrg = null;
	IKmsKnowledgeBaseDocService kmsKnowledgeBaseDocService = (IKmsKnowledgeBaseDocService) SpringBeanUtil
			.getBean("kmsKnowledgeBaseDocService");
	String attCreator = "";
	String fileSize = "";
	if (attPage != null) {
		List<Object[]> obj = attPage.getList();
		for (Object[] attList : obj) {
			if (StringUtil.isNotNull((String) attList[1])) {
				deptOrg = (SysOrgElement) sysOrgElementService
						.findByPrimaryKey((String) attList[1]);
				if (deptOrg instanceof SysOrgPerson) {
					attCreator = deptOrg.getFdName ();
				}
			}
			attCreatorJson.element((String) attList[0], attCreator);
			if(attList[2] != null){
				fileSize = KmsKnowledgeUtil.format(attList[2].toString());
			}
			attFileSize.element((String) attList[0], fileSize);
		}
	}
	request.setAttribute("attCreatorJson", attCreatorJson);
	request.setAttribute("attFileSize", attFileSize);

    boolean isBorrowOpen = KmsKnowledgeBorrowUtil.checkBorrowOpen(request);
    request.setAttribute("isBorrowOpen", isBorrowOpen);
%>
<list:data>
	<list:data-columns var="item" list="${attPage.list }">
		<list:data-column col="fdId">
			${item[5]}
		</list:data-column>
		<list:data-column col="fdKnowledgeType">
			${item[7]} 
		</list:data-column>
		<list:data-column col="attId">
			${item[0]}
		</list:data-column>
		<list:data-column col="attName" title="${lfn:message('kms-knowledge:kmsKnowledge.attName')}">
			${item[4]}
		</list:data-column>
		<list:data-column col="attCreator" title="${lfn:message('kms-knowledge:kmsKnowledge.uploader')}" escape="false">
			<ui:person personId="${item[1]}" personName="${attCreatorJson[item[0]]}"></ui:person>
		</list:data-column>
		<list:data-column col="attSize" title="${lfn:message('kms-knowledge:kmsKnowledge.attSize')}">
			${attFileSize[item[0]]}
		</list:data-column>
		<list:data-column col="uploadTime" title="${lfn:message('kms-knowledge:kmsKnowledge.uploadTime')}">
			<kmss:showDate value="${item[3]}" type="date" />
		</list:data-column>
		<list:data-column col="docSubject" title="${lfn:message('kms-knowledge:kmsKnowledge.docSubject')}">
			${item[6]}
		</list:data-column>
		<list:data-column col="handle" title="${ lfn:message('kms-knowledge:kmsKnowledge.index.btn.handle')}" escape="false">
			<kmss:auth
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${item[0]}"
					requestMethod="GET">
				<a class="lui_knowledge_download"
				   href="javascript:downloadAttAndLog('${item[0]}');" />
			</kmss:auth>
		</list:data-column>
        <c:if test="${isBorrowOpen}">
			<list:data-column col="docBorrowFlagName" title="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.readStatus')}">
				 <c:choose>
                    <c:when test="${1 == item[8]}">
                        <c:out value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.canBorrow') }" />
                    </c:when>
                    <c:otherwise>
                        <c:out value="${lfn:message('kms-knowledge-borrow:kmsKnowledgeBorrow.canRead') }" />
                    </c:otherwise>
                </c:choose>
			</list:data-column>
		</c:if>
        <list:data-column col="borrowOpenFlag">
            ${borrowOpenFlag}
        </list:data-column>
	</list:data-columns>

	<list:data-paging page="${attPage }">
	</list:data-paging>
</list:data>

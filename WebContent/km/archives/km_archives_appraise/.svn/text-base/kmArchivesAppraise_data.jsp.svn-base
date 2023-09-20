<%@page import="com.landray.kmss.km.archives.service.IKmArchivesAppraiseService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesAppraise"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesAppraise" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <!-- 鉴定名称 -->
        <list:data-column col="docSubject" escape="false" headerStyle="width:400px;" title="${lfn:message('km-archives:kmArchivesAppraise.docSubject')}">
        	<!-- 修复档案记录中鉴定记录页签列表中鉴定名称内容未显示 -->
        	<%
        		KmArchivesAppraise kmArchivesAppraise = (KmArchivesAppraise) pageContext.getAttribute("kmArchivesAppraise");
        		if(kmArchivesAppraise!=null){
        			IKmArchivesAppraiseService kmArchivesAppraiseService=(IKmArchivesAppraiseService)SpringBeanUtil.getBean("kmArchivesAppraiseService");
            		KmArchivesAppraise kmArchivesAppraiseOrigin=(KmArchivesAppraise)kmArchivesAppraiseService.findByPrimaryKey(kmArchivesAppraise.getFdOriginId());
            		if(kmArchivesAppraiseOrigin!=null){
            			request.setAttribute("fdDetailsName", kmArchivesAppraiseOrigin.getDocSubject());
            		}
            		else{
            			request.setAttribute("fdDetailsName", kmArchivesAppraise.getDocSubject());
            		}
        		}
        		
        	%>
        	<span class="com_subject"><c:out value="${fdDetailsName }"/></span>
        </list:data-column>
		<!-- 档案名称 -->
		<list:data-column property="fdArchivesName" title="${lfn:message('km-archives:kmArchivesAppraise.fdArchivesName')}" />
        <list:data-column property="fdArchivesNumber" title="${lfn:message('km-archives:kmArchivesAppraise.fdArchivesNumber')}" />
        <list:data-column col="fdAfterAppraiseDate" title="${lfn:message('km-archives:kmArchivesAppraise.fdAfterAppraiseDate')}">
        	<kmss:showDate value="${kmArchivesAppraise.fdAfterAppraiseDate }" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesAppraise.docCreator')}" escape="false">
            <c:out value="${kmArchivesAppraise.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.fdId" escape="false">
            <c:out value="${kmArchivesAppraise.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesAppraise.docCreateTime')}">
            <kmss:showDate value="${kmArchivesAppraise.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 原档案有效期 -->
        <list:data-column col="fdOriginalDate" title="${lfn:message('km-archives:kmArchivesAppraise.fdOriginalDate')}">
            <kmss:showDate value="${kmArchivesAppraise.fdOriginalDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column escape="false" style="max-width:120px;" col="fdAppraiseIdea" title="${lfn:message('km-archives:kmArchivesAppraise.fdAppraiseIdea')}">
        	<div title="<c:out value="${kmArchivesAppraise.fdAppraiseIdea}"/>" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
        		<c:out value="${kmArchivesAppraise.fdAppraiseIdea}"/>
        	</div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('km-archives:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${kmArchivesAppraise.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('km-archives:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${kmArchivesAppraise.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column property="docStatus" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

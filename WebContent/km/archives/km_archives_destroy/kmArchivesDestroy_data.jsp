<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesDestroyService"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDestroy"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesDestroy" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <!-- 销毁名称 -->
        <list:data-column col="docSubject" escape="false" headerStyle="width:400px;" title="${lfn:message('km-archives:kmArchivesDestroy.docSubject')}">
        	<!-- 修复档案记录中销毁记录页签列表中销毁名称内容未显示 -->
        	<%
        		KmArchivesDestroy kmArchivesDestroy = (KmArchivesDestroy) pageContext.getAttribute("kmArchivesDestroy");
        		if(kmArchivesDestroy!=null){
        			IKmArchivesDestroyService kmArchivesDestroyService=(IKmArchivesDestroyService)SpringBeanUtil.getBean("kmArchivesDestroyService");
        			KmArchivesDestroy kmArchivesDestroyOrigin=(KmArchivesDestroy)kmArchivesDestroyService.findByPrimaryKey(kmArchivesDestroy.getFdOriginId());
            		if(kmArchivesDestroyOrigin!=null){
        				request.setAttribute("fdDetailsName", kmArchivesDestroyOrigin.getDocSubject());
        			}
            		else{
            			request.setAttribute("fdDetailsName", kmArchivesDestroy.getDocSubject());
            		}
        		}
        		
        	%>
        	<span class="com_subject"><c:out value="${fdDetailsName }"/></span>
        </list:data-column>
		<!-- 档案名称 -->
		<list:data-column property="fdArchivesName" title="${lfn:message('km-archives:kmArchivesDestroy.fdArchivesName')}" />
        <list:data-column property="fdArchivesNumber" title="${lfn:message('km-archives:kmArchivesDestroy.fdArchivesNumber')}" />
        <list:data-column col="docCreator.fdName" title="${lfn:message('km-archives:kmArchivesDestroy.docCreator')}" escape="false">
            <c:out value="${kmArchivesDestroy.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.fdId" escape="false">
            <c:out value="${kmArchivesDestroy.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('km-archives:kmArchivesDestroy.docCreateTime')}">
            <kmss:showDate value="${kmArchivesDestroy.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <!-- 归档日期 -->
        <list:data-column col="fdReturnDate" title="${lfn:message('km-archives:kmArchivesDestroy.fdReturnDate')}">
            <kmss:showDate value="${kmArchivesDestroy.fdReturnDate}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column escape="false" style="max-width:120px;" col="fdDestroyIdea" title="${lfn:message('km-archives:kmArchivesDestroy.fdDestroyIdea')}">
        	<div title='<c:out value="${kmArchivesDestroy.fdDestroyIdea}"></c:out>' style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">
        		<c:out value="${kmArchivesDestroy.fdDestroyIdea}"/>
        	</div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_node" title="${lfn:message('km-archives:lbpm.currentNode') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${kmArchivesDestroy.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('km-archives:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${kmArchivesDestroy.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <list:data-column property="docStatus" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

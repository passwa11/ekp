<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.landray.kmss.tic.core.common.service.ITicCoreFuncBaseService"%>
<%@page import="com.landray.kmss.tic.core.common.model.TicCoreFuncBase"%>
<%@page import="com.landray.kmss.tic.core.common.model.TicCoreTransSett"%>

<list:data>
    <list:data-columns var="ticCoreTransSett" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdFunction.id" escape="false">
            <c:out value="${ticCoreTransSett.fdFuncBaseId}" />
        </list:data-column>
        <list:data-column property="fdName" escape="false" title="${lfn:message('tic-core-common:ticCoreCommon.transFuncName')}">
        </list:data-column>
          <list:data-column  col="fdFuncBaseName" escape="false" title="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}">
          <%
          TicCoreTransSett ticCoreTransSett = (TicCoreTransSett)pageContext.getAttribute("ticCoreTransSett");
      	  ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession()
		   .getServletContext());
          ITicCoreFuncBaseService service = (ITicCoreFuncBaseService) ctx.getBean("ticCoreFuncBaseService");
          TicCoreFuncBase ticCoreFuncBase=(TicCoreFuncBase) service.findFunc(ticCoreTransSett.getFdFuncBaseId());
          request.setAttribute("fdFuncBaseName", ticCoreFuncBase.getFdName());
          %>
           <c:out value="${fdFuncBaseName}" />
        </list:data-column>
         <list:data-column col="fdCategoryName" escape="false" title="${lfn:message('tic-core-common:ticCoreTransSett.transFuncCategory')}">
          <c:out value="${ticCoreTransSett.fdCategory.fdName}" />
        </list:data-column>
        
        
        <list:data-column col="fdTransDesc" escape="false" title="${lfn:message('tic-core-common:ticCoreTransSett.fdTransDesc')}">
        	<c:out value="${ticCoreTransSett.fdTransDesc}" />
        </list:data-column>
		
		
        <list:data-column col="fdIsDefault" escape="false" title="${lfn:message('tic-core-common:ticCoreTransSett.fdIsDefault')}">
          <sunbor:enumsShow value="${ticCoreTransSett.fdIsDefault}" enumsType="common_yesno"/>
        </list:data-column> 
         <list:data-column col="fdIsAvailable" escape="false" title="${lfn:message('tic-core-common:ticCoreBusiCate.isEnable')}">
          <sunbor:enumsShow value="${ticCoreTransSett.fdIsAvailable}" enumsType="common_yesno"/>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

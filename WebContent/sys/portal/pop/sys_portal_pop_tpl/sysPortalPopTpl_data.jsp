<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.portal.model.SysPortalPopTpl"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	ISysAttMainCoreInnerService sysAttMainCoreInnerService = 
		(ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
%>

<list:data>
    <list:data-columns var="sysPortalPopTpl" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="docSubject" title="${lfn:message('sys-portal:sysPortalPopTpl.docSubject')}" />
        <list:data-column col="fdCategory.name" title="${lfn:message('sys-portal:sysPortalPopTpl.fdCategory')}" escape="false">
            <c:out value="${sysPortalPopTpl.fdCategory.fdName}" />
        </list:data-column>
        <list:data-column col="fdCategory.id" escape="false">
            <c:out value="${sysPortalPopTpl.fdCategory.fdId}" />
        </list:data-column>
        <list:data-column col="fdIsAvailable.name" title="${lfn:message('sys-portal:sysPortalPopTpl.fdIsAvailable')}">
            <sunbor:enumsShow value="${sysPortalPopTpl.fdIsAvailable}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsAvailable">
            <c:out value="${sysPortalPopTpl.fdIsAvailable}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('sys-portal:sysPortalPopTpl.docCreator')}" escape="false">
            <c:out value="${sysPortalPopTpl.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysPortalPopTpl.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-portal:sysPortalPopTpl.docCreateTime')}">
            <kmss:showDate value="${sysPortalPopTpl.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        
		<list:data-column col="preview" title="预览" escape="false">
			<%
				SysPortalPopTpl sysPortalPopTpl=(SysPortalPopTpl)pageContext.getAttribute("sysPortalPopTpl");
				List list = sysAttMainCoreInnerService.findByModelKey(ModelUtil.getModelClassName(sysPortalPopTpl),
								sysPortalPopTpl.getFdId(), "attPortalPopTpl");
				if(list!=null && list.size()>0){
					SysAttMain att=(SysAttMain)list.get(0);
					String imgUrl = "sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=" + att.getFdId();
					out.print(imgUrl);
				}
			%>
		</list:data-column>
        
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>

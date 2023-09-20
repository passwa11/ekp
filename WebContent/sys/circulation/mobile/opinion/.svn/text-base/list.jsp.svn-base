<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.sys.circulation.model.SysCirculationOpinion"%>
<%@page import="com.landray.kmss.sys.circulation.util.CirculationUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }" mobile="true">
		<list:data-column col="label" title="" escape="false">
			<%
				if(pageContext.getAttribute("item")!=null){
					SysCirculationOpinion sco = (SysCirculationOpinion)pageContext.getAttribute("item");
					String _modelId = sco.getFdId();
					String _modelName = SysCirculationOpinion.class.getName();
					try{
						ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
						List sysAttMains =  ((ISysAttMainCoreInnerDao) sysAttMainService.getBaseDao()).findByModelKey(_modelName,_modelId,"attachment");
						request.setAttribute("sysAttMains",sysAttMains);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			%>
			<c:out value="${item.docContent}" />
			<c:if test="${not empty  sysAttMains}">
				<img src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/attachment.png">
			</c:if>
		</list:data-column>
		 <%-- 创建者--%>
		<list:data-column col="creator" title="${ lfn:message('sys-news:sysNewsMain.docCreatorId') }" >
		        <c:out value="${item.fdBelongPerson.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			    <person:headimageUrl personId="${item.fdBelongPerson.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="created" title="">
			<kmss:showDate value="${item.fdWriteTime}" isInterval="true"></kmss:showDate>
		</list:data-column>
		<list:data-column col="summary" title="" escape="false">
	         <c:out value="${item.fdBelongPerson.fdParent.fdName}"/>
      	</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>

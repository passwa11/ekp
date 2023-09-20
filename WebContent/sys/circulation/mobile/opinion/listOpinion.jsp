<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.sys.circulation.model.SysCirculationOpinion"%>
<%@page import="com.landray.kmss.sys.circulation.util.CirculationUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<list:data>
	<list:data-columns var="sysCirculationOpinion" list="${queryPage.list }" varIndex="status">
		<%--ID--%> 
		<list:data-column property="fdId"></list:data-column>
		<list:data-column col="fdSeries" escape="false">
			<c:out value="${status+1}" />
		</list:data-column>
		<list:data-column col="fdCirculationTime" title="传阅时间" >
			<kmss:showDate value="${sysCirculationOpinion.sysCirculationMain.fdCirculationTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="fdOrder" title="序号" >
			<c:out value="${sysCirculationOpinion.fdOrder}" />
		</list:data-column>
		<list:data-column col="fdBelongPerson" title="接收人" escape="false">
			<c:out value="${sysCirculationOpinion.fdBelongPerson.fdName}" />
		</list:data-column>
		<list:data-column col="fdBelongPersonDept" title="接收人部门" escape="false" >
			<c:out value="${sysCirculationOpinion.fdBelongPerson.fdParent.fdName}" />
		</list:data-column>
		<list:data-column col="docStatusVal" title="状态" escape="false">
			<c:out value="${sysCirculationOpinion.docStatus}" />
		</list:data-column>
		<list:data-column col="docStatus" title="状态" escape="false">
			<sunbor:enumsShow value="${sysCirculationOpinion.docStatus}" enumsType="sysCirculationOpinion_docStatus" />
		</list:data-column>
		<list:data-column col="fdReadTime" title="阅读时间" >
			<kmss:showDate value="${sysCirculationOpinion.fdReadTime}" type="datetime" />
		</list:data-column>
		<list:data-column  col="fdWriteTime" title="填写时间" >
			<kmss:showDate value="${sysCirculationOpinion.fdWriteTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="fdRemindCount" title="提醒次数" >
			<c:out value="${sysCirculationOpinion.fdRemindCount}" />
		</list:data-column>
		<list:data-column col="fdRecallTime" title="撤回时间" >
			<kmss:showDate value="${sysCirculationOpinion.fdRecallTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="docContent" title="意见" escape="false">
			<%
				if(pageContext.getAttribute("sysCirculationOpinion")!=null){
					SysCirculationOpinion sco = (SysCirculationOpinion)pageContext.getAttribute("sysCirculationOpinion");
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
			<c:out value="${sysCirculationOpinion.docContent}" />
			<c:if test="${not empty  sysAttMains}">
				<img src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/attachment.png">
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
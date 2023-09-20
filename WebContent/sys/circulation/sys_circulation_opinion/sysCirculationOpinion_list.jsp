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
	<list:data-columns var="sysCirculationOpinion" list="${queryPage.list }">
		<%--ID--%> 
		<list:data-column property="fdId"></list:data-column>
		<list:data-column style="width:125px;" property="sysCirculationMain.fdCirculationTime" title="${ lfn:message('sys-circulation:sysCirculationMain.fdCirculationTime') }" >
		</list:data-column>
		<list:data-column style="width:100px;" property="fdOrder" title="${ lfn:message('page.serial') }" >
		</list:data-column>
		<list:data-column style="width:100px;" property="fdBelongPerson.fdName" title="${ lfn:message('sys-circulation:sysCirculationMain.receiver') }" >
		</list:data-column>
		<list:data-column style="width:120px;" col="fdBelongPerson.fdParent.fdName" title="${ lfn:message('sys-circulation:sysCirculationMain.receiverDept') }" >
			<c:out value="${sysCirculationOpinion.fdBelongPerson.fdParent.deptLevelNames}"></c:out>
		</list:data-column>
		<list:data-column style="width:80px;" col="docStatus" title="${ lfn:message('page.state') }" escape="false">
			<sunbor:enumsShow value="${sysCirculationOpinion.docStatus}" enumsType="sysCirculationOpinion_docStatus" bundle="sys-circulation" />
		</list:data-column>
		<list:data-column style="width:120px;" property="fdReadTime" title="${ lfn:message('sys-circulation:sysCirculationMain.readTime') }" >
			<kmss:showDate value="${sysCirculationOpinion.fdReadTime}" type="datetime" />
		</list:data-column>
		<list:data-column  style="width:125px;" property="fdWriteTime" title="${ lfn:message('sys-circulation:sysCirculationMain.fillTime') }" >
			<kmss:showDate value="${sysCirculationOpinion.fdWriteTime}" type="datetime" />
		</list:data-column>
		<list:data-column style="width:60px;" property="fdRemindCount" title="${ lfn:message('sys-circulation:sysCirculationMain.remindTimes') }" >
		</list:data-column>
		<list:data-column style="width:120px;" property="fdRecallTime" title="${ lfn:message('sys-circulation:sysCirculationMain.recallTime') }" >
			<kmss:showDate value="${sysCirculationOpinion.fdRecallTime}" type="datetime" />
		</list:data-column>
		<list:data-column col="docContent" title="${ lfn:message('sys-circulation:sysCirculationMain.opinion') }" escape="false" >
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
			<c:out value="${sysCirculationOpinion.docContent }"></c:out>
			<c:if test="${not empty  sysAttMains}">
				<img src="${KMSS_Parameter_ContextPath}sys/circulation/resource/images/attachment.png">
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
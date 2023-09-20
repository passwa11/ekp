<%@page import="com.landray.kmss.sys.lbpmperson.forms.LbpmProcessPersonMobileForm"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.third.pda.service.spring.PdaModuleSelectDialog"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.notify.util.SysNotifyConfigUtil"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.third.pda.util.PdaFlagUtil"%>
<%@page import="com.landray.kmss.third.pda.service.IPdaDataShowService"%>
<%@page import="com.landray.kmss.third.pda.model.PdaRowsPerPageConfig"%>
<%
String prefix = PdaFlagUtil.getUrlPrefix(request);
%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status" mobile="true">
	    <%-- 主题--%>	
		<list:data-column col="label" escape="false" title="${ lfn:message('sys-notify:sysNotifyTodo.subject4View') }">
			<c:out value="${model.subject}"/>   
		</list:data-column>
		<list:data-column col="creator" title="${ lfn:message('sys-notify:sysNotifyTodo.docCreator.fdName') }" >
		         <c:out value="${model.fdCreator.fdName}"/>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
				<c:if test="${not empty model.fdCreator}">
					<person:headimageUrl personId="${model.fdCreator.fdId}" size="m" />
				</c:if>
			    <c:if test="${empty model.fdCreator}">
					/sys/notify/resource/images/gear.png
				</c:if>
		</list:data-column>
		 <%-- 创建时间--%>
	 	<list:data-column col="created" title="${ lfn:message('sys-notify:sysNotifyTodo.fdCreateDate') }">
	        	<kmss:showDate value="${model.fdCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
      	<list:data-column col="op" title="">
			<c:if test="${model.isFastApprove eq 'true'}">${model.isFastApprove}</c:if>
	        <kmss:showDate value="${model.fdCreateTime}" type="date"></kmss:showDate>
      	</list:data-column>
      	<list:data-column col="href" escape="false">
      		<%
      			LbpmProcessPersonMobileForm mobileForm=(LbpmProcessPersonMobileForm)pageContext.getAttribute("model");
      		 	String url="";
      			if(mobileForm.getViewURL().startsWith("/")){
					url = prefix + mobileForm.getViewURL().substring(1);
      			}
      			pageContext.setAttribute("url",url);
      		%>
      	 	<c:out value="${url}" escapeXml="false"/>
		</list:data-column>
		<list:data-column col="modelNameText" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName')}" escape="false">
			<c:out value="${model.modelText}"/>
		</list:data-column>
		<list:data-column col="lbpmCurrNodeValue" title="${ lfn:message('sys-lbpmservice:lbpmSupport.STATUS_RUNNING')}" escape="false">
			<c:out value="${model.currentNodeNames}"/>
		</list:data-column>
		<list:data-column col="uuid"  escape="false">
			<c:out value="${model.uuid}"/>
		</list:data-column>
		<list:data-column col="modelId"  escape="false">
			<c:out value="${model.fdModelId}"/>
		</list:data-column>
		<list:data-column col="reviewOp" title="${ lfn:message('sys-notify:sysNotifyTodo.moduleName')}" escape="false">
			<c:if test="${model.isFastReject=='true'}">
				<a title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastreject')}" class="muiProcessBtn muiProcessBtnGray" href="javascript:;"  onclick="mobile_fastApprove('${model.fdId}','refuse','${model.uuid}');">${lfn:message('sys-lbpmperson:lbpmperson.op.fastreject')}</a>
			</c:if>
			<c:if test="${model.isFastApprove=='true'}">
				<a title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastapprove')}"  class="muiProcessBtn" href="javascript:;"  onclick="mobile_fastApprove('${model.fdId}','pass','${model.uuid}');">${lfn:message('sys-lbpmperson:lbpmperson.op.fastapprove')}</a>
			</c:if>
			<c:if test="${model.isAssignBack=='true'&&model.isSignedPerson=='true'}">
					<a title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignback')}" class="muiProcessBtn muiProcessBtnGray" href="javascript:;"  onclick="mobile_fastApprove('${model.fdId}','assignRefuse','${model.uuid}');">${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignback')}</a>
			</c:if>
			<c:if test="${model.isAssignPass=='true'&&model.isSignedPerson=='true'}">
					<a title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignpass')}"  class="muiProcessBtn" href="javascript:;"  onclick="mobile_fastApprove('${model.fdId}','assignPass','${model.uuid}');">${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignpass')}</a>
			</c:if>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
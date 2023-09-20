<%@ page language="java" contentType="text/json; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingTopic"%>
<%@page import="com.landray.kmss.sys.unit.model.KmImissiveUnit"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement,java.util.*,com.landray.kmss.util.*"%>
<list:data>
	<list:data-columns var="kmImeetingTopic" list="${queryPage.list }">
		<%--ID--%> 
		<list:data-column property="fdId"></list:data-column>
		<%--名称--%>
		<list:data-column  headerClass="width200" col="docSubject" title="${ lfn:message('km-imeeting:kmImeetingTopic.docSubject')}" style="text-align:left" escape="false">
			<span class="com_subject"><c:out value="${kmImeetingTopic.docSubject}" /></span>
		</list:data-column>
		<list:data-column  headerClass="width120" property="fdTopicCategory.fdName" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdTopicCategory')}">
		</list:data-column>
		<list:data-column  headerClass="width100" property="fdChargeUnit.fdName" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdChargeUnit')}">
		</list:data-column>
		<list:data-column  headerClass="width80" col="fdReporter.fdName" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdReporter')}" escape="false">
			<ui:person personId="${kmImeetingTopic.fdReporter.fdId}" personName="${kmImeetingTopic.fdReporter.fdName}"></ui:person>
		</list:data-column>
		<list:data-column  col="fdAttendUnit" escape="false" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdAttendUnit')}">
			<%
			if(pageContext.getAttribute("kmImeetingTopic")!=null){
		    List fdAttendUnit=((KmImeetingTopic)pageContext.getAttribute("kmImeetingTopic")).getFdAttendUnit();
			String unitNames="";
				for(int i=0;i<fdAttendUnit.size();i++){
					if(i==fdAttendUnit.size()-1){
						unitNames+=((KmImissiveUnit)fdAttendUnit.get(i)).getFdName();	
					}else{
						unitNames+=((KmImissiveUnit)fdAttendUnit.get(i)).getFdName()+";";
					}
				 }
				request.setAttribute("unitNames",unitNames);
			}
			%>
			<p title="${unitNames}">
				<c:forEach items="${kmImeetingTopic.fdAttendUnit}" var="fdUnit" varStatus="vstatus">
					${fdUnit.fdName}
				</c:forEach>
			</p>
		</list:data-column>
		<list:data-column  col="fdListenUnit" escape="false" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdListenUnit')}">
			<%
			if(pageContext.getAttribute("kmImeetingTopic")!=null){
		    List fdListenUnit=((KmImeetingTopic)pageContext.getAttribute("kmImeetingTopic")).getFdListenUnit();
			String unitNames="";
				for(int i=0;i<fdListenUnit.size();i++){
					if(i==fdListenUnit.size()-1){
						unitNames+=((KmImissiveUnit)fdListenUnit.get(i)).getFdName();	
					}else{
						unitNames+=((KmImissiveUnit)fdListenUnit.get(i)).getFdName()+";";
					}
				 }
				request.setAttribute("unitNames",unitNames);
			}
			%>
			<p title="${unitNames}">
				<c:forEach items="${kmImeetingTopic.fdListenUnit}" var="fdUnit" varStatus="vstatus">
					${fdUnit.fdName}
				</c:forEach>
			</p>
		</list:data-column>
		<list:data-column  headerClass="width80" property="docCreator.fdName" title="${ lfn:message('km-imeeting:kmImeetingTopic.docCreator')}">
		</list:data-column>
		<list:data-column headerClass="width140"  col="docCreateTime" title="${ lfn:message('km-imeeting:kmImeetingTopic.docCreateTime')}">
		    <kmss:showDate value="${kmImeetingTopic.docCreateTime}" type="date" />
		</list:data-column>
		<list:data-column headerClass="width140"  col="docPublishTime" title="${ lfn:message('km-imeeting:kmImeetingTopic.docPublishTime')}">
		    <kmss:showDate value="${kmImeetingTopic.docPublishTime}" type="date" />
		</list:data-column>
		<list:data-column headerClass="width140"  col="docAlterTime" title="${ lfn:message('km-imeeting:kmImeetingTopic.docAlterTime')}">
		    <kmss:showDate value="${kmImeetingTopic.docAlterTime}" type="date" />
		</list:data-column>
		<!--文档状态-->
		<list:data-column headerStyle="width:60px" col="docStatus" title="${ lfn:message('km-imeeting:kmImeetingTopic.docStatus') }">
			<sunbor:enumsShow value="${kmImeetingTopic.docStatus}" enumsType="common_status" />
		</list:data-column> 
		<list:data-column headerStyle="width:60px" col="fdIsAccept" title="${ lfn:message('km-imeeting:kmImeetingTopic.fdIsAccept') }">
			<sunbor:enumsShow value="${kmImeetingTopic.fdIsAccept}" enumsType="common_yesno" />
		</list:data-column> 
		<!-- 当前环节和当前处理人-->	
		<list:data-column headerClass="width80" col="nodeName" title="${ lfn:message('km-imeeting:sysWfNode.processingNode.currentProcess') }" escape="false">
			<kmss:showWfPropertyValues var="nodevalue" idValue="${kmImeetingTopic.fdId}" propertyName="nodeName" />
				<div class="textEllipsis width100" title="${nodevalue}">
			        <c:out value="${nodevalue}"></c:out>
			    </div>
		</list:data-column>
		<list:data-column headerClass="width80" col="handlerName" title="${ lfn:message('km-imeeting:sysWfNode.processingNode.currentProcessor') }" escape="false">
		    <kmss:showWfPropertyValues var="handlerValue" idValue="${kmImeetingTopic.fdId}" propertyName="handlerName" />
		    	<div class="textEllipsis width80" style="font-weight:bold;" title="${handlerValue}">
			        <c:out value="${handlerValue}"></c:out>
			    </div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
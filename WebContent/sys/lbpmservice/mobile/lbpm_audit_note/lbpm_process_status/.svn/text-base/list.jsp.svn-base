<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="lbpmHistoryWorkitem" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<!-- 节点名称 -->
		<list:data-column col="nodeName" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdFactNodeName') }" escape="false">
		         <c:out value="${lbpmHistoryWorkitem.fdNode.fdFactNodeName}"/>
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column col="fdHandler" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdHandler') }" escape="false">
		         <c:out value="${lbpmHistoryWorkitem.fdExpecter.fdName}"/>
		</list:data-column>
		<!-- 接收时间 -->
		<list:data-column col="fdStartDate" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdStartDate') }">
			<kmss:showDate value="${lbpmHistoryWorkitem.fdStartDate}" type="datetime"/>
		</list:data-column>
		<!-- 操作时间 -->
		<list:data-column col="operationDate" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdFinishDate') }" escape="false">
			<!--工作项结束时间不为空时，显示工作项结束时间-->
			<c:if test="${not empty lbpmHistoryWorkitem.fdFinishDate}">
				<kmss:showDate value="${lbpmHistoryWorkitem.fdFinishDate}" type="datetime"/>
			</c:if>
			<c:if test="${empty lbpmHistoryWorkitem.fdFinishDate}">
				<!--已查看-->
				<c:if test="${lbpmHistoryWorkitem.fdIsLook==true}">
					<!--查看时间为空则显示工作项的结束时间-->
					<c:if test="${not empty lbpmHistoryWorkitem.fdViewDate}">
						<kmss:showDate value="${lbpmHistoryWorkitem.fdViewDate}" type="datetime"/>
					</c:if>	
				</c:if>
			</c:if>
		</list:data-column>
		<!-- 操作状态 -->
		<list:data-column col="operationType" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.operationType') }" escape="false">
			<c:if test="${not empty lbpmHistoryWorkitem.fdFinishDate}">
				<span>${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isFinish') }</span>
			</c:if>
			<c:if test="${empty lbpmHistoryWorkitem.fdFinishDate}">
				<c:if test="${lbpmHistoryWorkitem.fdIsLook==true}">
					<span class="color_success">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isLook') }</span>
				</c:if>
				<c:if test="${lbpmHistoryWorkitem.fdIsLook!=true}">
					<span class="color_warning">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notLook') }</span>
				</c:if>
			</c:if>
		</list:data-column>
		 <%-- 创建者头像--%>
		<list:data-column col="icon" escape="false">
			    <person:headimageUrl personId="${lbpmHistoryWorkitem.fdExpecter.fdId}" size="m" />
		</list:data-column>
		<!-- 操作 -->
		<list:data-column col="operations" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.operations') }" escape="false">		
			<c:if test="${displayOperations[lbpmHistoryWorkitem.fdId] == true }">
				<a class="mui_flowstate_data_btn" onclick="press('${lbpmHistoryWorkitem.fdId}');" href="javascript:void(0)" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.press') }">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.press') }</a>
			</c:if>	
		</list:data-column>	
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
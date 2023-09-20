
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	
<list:data>
	<list:data-columns var="lbpmHistoryWorkitem" list="${queryPage.list }" varIndex="">
		<!-- 节点名称 -->
		<list:data-column headerStyle="width:20%" property="fdNode.fdFactNodeName" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdFactNodeName') }">
		</list:data-column>
		<!-- 操作者 -->
		<list:data-column headerStyle="width:20%" col="fdHandler" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdHandler') }" escape="false">
			<%-- <c:if test="${empty lbpmHistoryWorkitem.fdHandler}">
				<c:out value="${lbpmHistoryWorkitem.fdExpecter.fdName}" />
			</c:if>
			<c:if test="${not empty lbpmHistoryWorkitem.fdHandler}">
				<c:out value="${lbpmHistoryWorkitem.fdHandler.fdName}" />
			</c:if>	 --%>
			<c:out value="${lbpmHistoryWorkitem.fdExpecter.fdName}" />
		</list:data-column>
		<!-- 接收时间 -->
		<list:data-column headerStyle="width:20%" col="fdStartDate" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdStartDate') }">
			<%-- <c:if test="${lbpmHistoryWorkitem.fdActivityType!='draftWorkitem'}">
				<kmss:showDate value="${lbpmHistoryWorkitem.fdStartDate}" type="datetime"/>
			</c:if> --%>
			<kmss:showDate value="${lbpmHistoryWorkitem.fdStartDate}" type="datetime"/>
		</list:data-column>
		<!-- 操作时间 -->
		<list:data-column headerStyle="width:20%" col="operationDate" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.fdFinishDate') }" escape="false">
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
		<list:data-column headerStyle="width:10%" col="operationType" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.operationType') }" escape="false">
			<c:if test="${not empty lbpmHistoryWorkitem.fdFinishDate}">
				<span>${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isFinish') }</span>
			</c:if>
			<c:if test="${empty lbpmHistoryWorkitem.fdFinishDate}">
				<c:if test="${lbpmHistoryWorkitem.fdIsLook==true}">
					<span class="lui_flowstate_text_success">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isLook') }</span>
				</c:if>
				<c:if test="${lbpmHistoryWorkitem.fdIsLook!=true}">
					<span class="lui_flowstate_text_danger">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notLook') }</span>
				</c:if>
			</c:if>
		</list:data-column>
		<!-- 操作 -->
		<list:data-column style="width:10%"  col="operations" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.operations') }" escape="false">		
			<c:if test="${displayOperations[lbpmHistoryWorkitem.fdId] == true }">
				<a class="lui_flowstate_btn" style="position:relative;" onmouseover="mouseoverCountDown('${lbpmHistoryWorkitem.fdId}',this)" onmouseout="mouseoutCountDown('${lbpmHistoryWorkitem.fdId}',this)" href="javascript:press('${lbpmHistoryWorkitem.fdId}',this);" title="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.press') }">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.press') }
					<div class="content" id="inform${lbpmHistoryWorkitem.fdId}"></div>
				</a>
			</c:if>	
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
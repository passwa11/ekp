<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmProcess" list="${queryPage.list }" varIndex="status">
	    <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="url" escape="false">
		    <c:if test="${not empty urltMap[lbpmProcess.fdId]}">
	             ${urltMap[lbpmProcess.fdId]}
	        </c:if>
		</list:data-column >
		<list:data-column col="index">
		     ${status+1}
		</list:data-column >
	    <!--标题-->
	    <list:data-column col="subject" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.docSubject') }" escape="false" style="text-align:left">
		     <c:if test="${not empty subjectMap[lbpmProcess.fdId]}">
			          	<a class="com_subject textEllipsis" title="${subjectMap[lbpmProcess.fdId]}" onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}${urltMap[lbpmProcess.fdId]}" target="_blank">
			       			 ${subjectMap[lbpmProcess.fdId]}
			       	    </a>
	        </c:if>
		</list:data-column>
  <c:if test="${type ne 'create'}">		
	    <!--创建人-->
	    <list:data-column col="fdCreator.fdName" headerClass="width60" styleClass="width60" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.creator') }" escape="false">
	    	 <ui:person personId="${lbpmProcess.fdCreator.fdId}" personName="${lbpmProcess.fdCreator.fdName}"></ui:person>
		</list:data-column>
  </c:if>		
		<!--创建时间-->
		<list:data-column col="fdCreateTime" headerClass="width100" styleClass="width100" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.creatorTime') }">
				<kmss:showDate value="${lbpmProcess.fdCreateTime}" type="date"/>
		</list:data-column>
		<!--状态-->
		<list:data-column headerClass="width60" styleClass="width60" col="fdStatus" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.status') }" escape="false">
					<c:if test="${lbpmProcess.fdStatus=='10'}">
						${ lfn:message('sys-lbpmmonitor:status.created') }
					</c:if>
					<c:if test="${lbpmProcess.fdStatus=='20'}">
						${ lfn:message('sys-lbpmmonitor:status.activated') }
					</c:if>
					<c:if test="${lbpmProcess.fdStatus=='21'}">
						${ lfn:message('sys-lbpmmonitor:status.error') }
					</c:if>
					<c:if test="${lbpmProcess.fdStatus=='30'}">
						${ lfn:message('sys-lbpmmonitor:status.completed') }
					</c:if>
					<c:if test="${lbpmProcess.fdStatus=='00'}">
						${ lfn:message('sys-lbpmmonitor:status.discard') }
					</c:if>
					<c:if test="${lbpmProcess.fdStatus=='40'}">
						${ lfn:message('sys-lbpmmonitor:status.suspend') }
					</c:if>
		</list:data-column>
		<!--当前处理人-->
		<list:data-column headerClass="width80" styleClass="width80" col="handlerName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.handlerName') }" escape="false">
		    <kmss:showWfPropertyValues var="handlerName" idValue="${lbpmProcess.fdId}" propertyName="handlerName" />
		      	<div class="textEllipsis width80" style="font-weight: bold;" title="${handlerName}">
			        <c:out value="${handlerName}"></c:out>
			    </div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
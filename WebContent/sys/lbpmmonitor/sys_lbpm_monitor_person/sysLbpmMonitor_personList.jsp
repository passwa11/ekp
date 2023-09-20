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
		          <span class="com_subject">${subjectMap[lbpmProcess.fdId]}</span>
	        </c:if>
		</list:data-column>
		<!--所属模块-->
		<c:if test="${showModule==true}">
		    <list:data-column headerStyle="width:8%" col="subject" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.module') }" escape="false">
		         ${moduleMap[lbpmProcess.fdModelName]}
			</list:data-column>
		</c:if>
		<!--申请单编号-->
		<list:data-column headerStyle="width:100px" col="fdNumber" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.fdNumber') }" escape="false" >
			<c:if test="${not empty numberMap[lbpmProcess.fdId]}">
				${numberMap[lbpmProcess.fdId]}
			</c:if>
		</list:data-column>
	    <!--创建人-->
	    <list:data-column headerStyle="width:8%" property="fdCreator.fdName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.creator') }"> 
		</list:data-column>
		<!--创建时间-->
		<list:data-column col="fdCreateTime" headerClass="width100" styleClass="width100" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.creatorTime') }">
				<kmss:showDate value="${lbpmProcess.fdCreateTime}" type="date"/>
		</list:data-column>
		<!--状态-->
		<list:data-column headerStyle="width:6%" col="fdStatus" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.status') }" escape="false">
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
		<!--当前处理（节点）-->
		<list:data-column  col="nodeName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.nodeName') }" escape="false">
			<kmss:showWfPropertyValues var="nodevalue" idValue="${lbpmProcess.fdId}" propertyName="nodeName" />
			    <div title="${nodevalue}">
			        <c:out value="${nodevalue}"></c:out>
			    </div>
		</list:data-column>
		<!--当前处理人-->
		<list:data-column  col="handlerName" title="${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.person.handlerName') }" escape="false">
		    <kmss:showWfPropertyValues var="handlerName" idValue="${lbpmProcess.fdId}" propertyName="handlerName" />
		     <div title="${handlerName}">
		       <c:out value="${handlerName}"></c:out>
		    </div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
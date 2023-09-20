<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmProcessPersonForm" list="${queryPage.list }" varIndex="status">
	    <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="url" escape="false">
		    <c:if test="${not empty urltMap[lbpmProcessPersonForm.fdId]}">
	             ${urltMap[lbpmProcessPersonForm.fdId]}
	        </c:if>
		</list:data-column >
		<list:data-column col="index">
		     ${status+1}
		</list:data-column >
	    <!--标题-->
	    <list:data-column col="subject" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.docSubject') }" escape="false" style="text-align:left">
		     <c:if test="${not empty subjectMap[lbpmProcessPersonForm.fdId]}">
		          <span class="com_subject">${subjectMap[lbpmProcessPersonForm.fdId]}</span>
	        </c:if>
		</list:data-column>
		<!--所属模块-->
		<c:if test="${showModule==true}">
		    <list:data-column headerStyle="width:10%" col="subject" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.module') }" escape="false">
		         ${moduleMap[lbpmProcessPersonForm.fdModelName]}
			</list:data-column>
		</c:if>
		<!--申请单编号-->
		<list:data-column headerStyle="width:100px" col="fdNumber" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.fdNumber') }" escape="false" >
			<c:if test="${not empty numberMap[lbpmProcessPersonForm.fdId]}">
				${numberMap[lbpmProcessPersonForm.fdId]}
			</c:if>
		</list:data-column>
	    <!--创建人-->
		<c:if test="${fn:containsIgnoreCase(lbpmProcessPersonForm, 'fdCreator')}">
			<list:data-column headerStyle="width:8%" col="fdCreator" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.creator') }">
				${lbpmProcessPersonForm.fdCreator.fdName}
			</list:data-column>
		</c:if>
		<c:if test="${!fn:containsIgnoreCase(lbpmProcessPersonForm, 'creator')}">
			<list:data-column headerStyle="width:8%" property="fdCreator.fdName" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.creator') }">
			</list:data-column>
		</c:if>
		<!--创建时间-->
		<list:data-column col="fdCreateTime" headerClass="width100" styleClass="width100" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.creatorTime') }">
				<kmss:showDate value="${lbpmProcessPersonForm.fdCreateTime}" type="date"/>
		</list:data-column>
		<!--当前处理（节点）-->
		<list:data-column headerStyle="width:8%" col="nodeName" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.nodeName') }" escape="false">
			<kmss:showWfPropertyValues var="nodeNameVar" idValue="${lbpmProcessPersonForm.fdId}" propertyName="nodeName" />
			<c:out value="${nodeNameVar}"/>
		</list:data-column>
		<!--当前处理人-->
		<list:data-column headerClass="width100" col="handlerName" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.handlerName') }" escape="false">
			<kmss:showWfPropertyValues var="handlerValue" idValue="${lbpmProcessPersonForm.fdId}" propertyName="handlerName" />
				<div class="textEllipsis width100" style="font-weight:bold;" title="${handlerValue}">
				<c:out value="${handlerValue}"></c:out>
			</div>
		</list:data-column>
		<!-- 送审时间-->
		<list:data-column headerStyle="width:10%" col="arrivalTime" title="${ lfn:message('sys-lbpmperson:lbpmperson.person.arrivalTime') }" escape="false">
			<kmss:showWfPropertyValues idValue="${lbpmProcessPersonForm.fdId}" propertyName="arrivalTime" />
		</list:data-column>
		<list:data-column headerStyle="width:90px;" style="text-align:right;width:90px;" col="fdNumber" title="${ lfn:message('sys-lbpmperson:lbpmperson.op.fastreview') }" escape="false" >
			<span id="reviewOp_${lbpmProcessPersonForm.uuid}" class="btn_group_processhandle">
			<c:if test="${lbpmProcessPersonForm.isFastReject=='true'}">
				<input title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastreject')}" class="lui_form_button" type="button" value="${lfn:message('sys-lbpmperson:lbpmperson.op.fastreject')}" onclick="fastApprove('${lbpmProcessPersonForm.fdId}','refuse','${lbpmProcessPersonForm.uuid}');">
			</c:if>
			<c:if test="${lbpmProcessPersonForm.isFastApprove=='true'}">
					<input title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastapprove')}" class="lui_form_button" type="button" value="${lfn:message('sys-lbpmperson:lbpmperson.op.fastapprove')}" onclick="fastApprove('${lbpmProcessPersonForm.fdId}','pass','${lbpmProcessPersonForm.uuid}');">
			</c:if>
			<!-- 被加签人快速审批按钮显示 -->
			<c:if test="${lbpmProcessPersonForm.isAssignBack=='true'&&lbpmProcessPersonForm.isSignedPerson=='true'}">
				<input title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignback')}" class="lui_form_button" type="button" value="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignback')}" onclick="fastApprove('${lbpmProcessPersonForm.fdId}','assignRefuse','${lbpmProcessPersonForm.uuid}');">
			</c:if>
			<c:if test="${lbpmProcessPersonForm.isAssignPass=='true'&&lbpmProcessPersonForm.isSignedPerson=='true'}">
				<input title="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignpass')}" class="lui_form_button" type="button" value="${lfn:message('sys-lbpmperson:lbpmperson.op.fastassignpass')}" onclick="fastApprove('${lbpmProcessPersonForm.fdId}','assignPass','${lbpmProcessPersonForm.uuid}');">
			</c:if>
			</span>
			<!-- 只有快速审批的才显示复选框 -->
			<c:if test="${(lbpmProcessPersonForm.isFastReject!='true'&&lbpmProcessPersonForm.isFastApprove!='true')&&(lbpmProcessPersonForm.isAssignBack!='true'&&lbpmProcessPersonForm.isAssignPass!='true')}">
			<script>
				$("#reviewOp_${lbpmProcessPersonForm.uuid}").parents("tr").find("input[name='List_Selected']").remove();
			</script>
			</c:if>

		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
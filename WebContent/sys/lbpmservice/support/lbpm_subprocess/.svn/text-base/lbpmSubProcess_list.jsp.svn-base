<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<style>
.tag_subProcess{
	margin: 0px 0px 0.5rem;
	font-size: 12px;
	font-weight: normal;
	line-height: 16px;
	word-wrap: break-word;
	word-break: break-all;
	text-decoration: none;
}

.tag_subProcess:ACTIVE{
	text-decoration: none;
}

.tag_status {
	display: inline-block;
	font-size: 12px;
	height: 16px;
	line-height: 16px;
	padding: 0rem 3px;
	border-radius: 2px;
	border-width: 1px;
	border-style: solid;
	border-color: #4cbe45;
	color: #4cbe45;
}
.tag_discard{
	border-color: #ff9c00;
	color: #ff9c00;
}
.tag_draft{
	border-color: #aaa;
	color: #aaa;
}
.tag_refuse{
	border-color: #ff0000;
	color: #ff0000;
}
.tag_examine{
	border-color: #18b4ed;
	color: #18b4ed;
}
	
.tag_refuse{
	border-color: #ff0000;
	color: #ff0000;
}
.tag_publish{
	border-color: #4cbe45;
	color: #4cbe45;
}
.tag_expire{
	border-color: #4cbe45;
	color: #4cbe45;
}

.tag_summary{
	display:block;
	color: #989898 !important;
}
</style>
	<c:if test="${empty requestScope.mainModels}" >
		<center><bean:message key="return.noRecord"/></center>
	</c:if>
	<c:if test="${not empty requestScope.mainModels}" >
	<table style="width:95%">
		<c:forEach items="${requestScope.mainModels}" var="mainModelMap" varStatus="vstatus">
			<c:set var="mainModel" value="${mainModelMap.model}" />
			<tr>
				<td 
					class="normal_td">
					<a class="tag_subProcess" href="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_process/lbpmProcess.do?method=viewSub&fdId=${mainModel.fdId}" target="_blank">
						<c:if test="${mainModel.docStatus < '10'}">
							<span class="tag_status tag_discard">
								<kmss:message key="status.discard"/>
							</span>
						</c:if>
						<c:if test="${mainModel.docStatus >= '10' and mainModel.docStatus < '11'}">
							<span class="tag_status tag_draft">
								<kmss:message key="status.draft"/>
							</span>
						</c:if>
						<c:if test="${mainModel.docStatus >= '11' and mainModel.docStatus < '20'}">
							<span class="tag_status tag_refuse">
								<kmss:message key="status.refuse"/>
							</span>
						</c:if>
						<c:if test="${mainModel.docStatus >= '20' and mainModel.docStatus < '30'}">
							<span class="tag_status tag_examine">
								<kmss:message key="status.examine"/>
							</span>
						</c:if>
						<c:if test="${mainModel.docStatus >= '30' and mainModel.docStatus < '40'}">
							<span class="tag_status tag_publish">
								<kmss:message key="status.publish"/>
							</span>
						</c:if>
						<c:if test="${mainModel.docStatus >= '40'}">
							<span class="tag_status tag_expire">
								<kmss:message key="status.expire"/>
							</span>
						</c:if>
						<c:out value="${mainModel.docSubject}" />
						(<c:out value="${mainModel.docCreator.fdName}" />)
						<c:if test="${mainModel.docStatus >= '20' and mainModel.docStatus < '30'}">
							<span class="tag_summary"">
							<kmss:message key="sys-lbpmservice-support:lbpmAuditNote.list.curInfo"/>:
							<kmss:showWfPropertyValues idValue="${mainModel.fdId}" propertyName="summary" />
							</span>
						</c:if>
					</a>
					
				</td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>
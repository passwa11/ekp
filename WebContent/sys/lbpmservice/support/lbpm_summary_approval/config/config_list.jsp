<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmSummaryApprovalConfig" varIndex="index" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdTemplateName" headerClass="width200" title="${ lfn:message('sys-lbpmservice-support:lbpmSummaryApproval.title.lbpmTemplate') }" style="text-align:center;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${lbpmSummaryApprovalConfig.fdTemplateName}" /></span>
		</list:data-column>
		<list:data-column headerClass="width200" col="fdNodeFactNames" title="${ lfn:message('sys-lbpmservice-support:lbpmSummaryApproval.title.nodes') }" escape="false">
			<c:out value="${lbpmSummaryApprovalConfig.fdNodeFactNames }"></c:out>
		</list:data-column>
		<list:data-column headerClass="width80" col="fdNoticeTime" title="${ lfn:message('sys-lbpmservice-support:lbpmSummaryApproval.title.noticeTime') }">
		    <c:out value="${fdNoticeTimes[index]}"></c:out>
		</list:data-column>
		<list:data-column headerClass="width80" col="docCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmSummaryApproval.title.docCreator') }" escape="false">
		  <ui:person personId="${lbpmSummaryApprovalConfig.docCreator.fdId}" personName="${lbpmSummaryApprovalConfig.docCreator.fdName}"></ui:person>
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width200" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=edit&fdId=${lbpmSummaryApprovalConfig.fdId}" requestMethod="GET">
					<a class="btn_txt" href="javascript:edit('${lbpmSummaryApprovalConfig.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=delete&fdId=${lbpmSummaryApprovalConfig.fdId}" requestMethod="GET">
					<a class="btn_txt" href="javascript:deleteAll('${lbpmSummaryApprovalConfig.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
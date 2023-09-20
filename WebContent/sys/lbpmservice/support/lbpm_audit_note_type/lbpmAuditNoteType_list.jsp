<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="lbpmAuditNoteType" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNoteType.fdName') }" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width30" property="fdOrder" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNoteType.fdOrder') }">
		</list:data-column>
		<list:data-column headerClass="width80" col="fdIsAvailable" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNoteType.fdIsAvailable') }">
		   <sunbor:enumsShow value="${lbpmAuditNoteType.fdIsAvailable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width100" property="docCreator.fdName" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNoteType.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" property="docCreateTime" title="${ lfn:message('sys-lbpmservice-support:lbpmAuditNoteType.docCreateTime') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=edit&fdId=${kmImissiveType.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmAuditNoteType.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=delete&fdId=${lbpmAuditNoteType.fdId}" requestMethod="POST">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${lbpmAuditNoteType.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>

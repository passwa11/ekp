<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmVoteWeight" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdCreateTime" title="${ lfn:message('sys-lbpmext-voteweight:lbpmVoteWeight.fdCreateTime') }"  escape="false">
			<kmss:showDate value="${lbpmVoteWeight.fdCreateTime}" type="datetime" />
		</list:data-column>
	
		<list:data-column property="fdVoter.fdName" title="${ lfn:message('sys-lbpmext-voteweight:lbpmVoteWeight.fdVoter') }">
		</list:data-column>
		
		<list:data-column property="fdVoteWeight" title="${ lfn:message('sys-lbpmext-voteweight:lbpmVoteWeight.fdVoteWeight') }">
		</list:data-column>

		<!-- 其它操作 -->
		<list:data-column headerClass="width100" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=edit&fdId=${lbpmVoteWeight.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${lbpmVoteWeight.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=delete&fdId=${lbpmVoteWeight.fdId}" requestMethod="GET">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:del('${lbpmVoteWeight.fdId}')">${lfn:message('button.delete')}</a>
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
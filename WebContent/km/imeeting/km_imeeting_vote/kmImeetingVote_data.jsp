<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingVote" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->	
		<list:data-column  property="docSubject" title="${ lfn:message('km-imeeting:kmImeetingVote.docSubject') }" />
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${kmImeetingVote.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteAll('${kmImeetingVote.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
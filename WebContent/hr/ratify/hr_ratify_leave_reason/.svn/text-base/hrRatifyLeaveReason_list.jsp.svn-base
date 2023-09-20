<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
<list:data-columns var="hrRatifyLeaveReason" list="${queryPage.list}">
		<list:data-column property="fdId"/>
		<list:data-column property="fdOrder" title="${ lfn:message('hr-ratify:hrRatifyLeaveReason.fdOrder') }">
		</list:data-column>
		<list:data-column property="fdName" title="${hrRatifyLeaveReason.fdType eq 'entry' ? lfn:message('hr-ratify:hrRatifyLeaveReason.fdName.entry') : lfn:message('hr-ratify:hrRatifyLeaveReason.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${hrRatifyLeaveReason.fdId}')">${lfn:message('button.edit')}</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:deleteAll('${hrRatifyLeaveReason.fdId}')">
						<bean:message key="button.delete"/>						
					</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>					
	</list:data-columns>	
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>
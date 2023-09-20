<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingSeatTemplate" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 名称 -->	
		<list:data-column  property="fdName" title="${ lfn:message('km-imeeting:kmImeetingSeatTemplate.fdName') }" />
		<!-- 座位数 -->
		<list:data-column col="fdSeatCount" title="${ lfn:message('km-imeeting:kmImeetingSeatTemplate.fdSeatCount') }" >
			<c:out value="${kmImeetingSeatTemplate.fdSeatCount}"></c:out>
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate.do?method=edit&fdId=${kmImeetingSeatTemplate.fdId}" requestMethod="GET">
						<!-- 编辑 -->
						<a class="btn_txt" href="javascript:edit('${kmImeetingSeatTemplate.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate.do?method=delete&fdId=${kmImeetingSeatTemplate.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kmImeetingSeatTemplate.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
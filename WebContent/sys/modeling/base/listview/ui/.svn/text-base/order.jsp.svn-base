<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	/*#135585 排序字段太长时，期望显示“...”*/
	.lui_widget_btn .lui_widget_btn_txt {
		overflow: hidden;
		text-overflow:ellipsis;
		white-space: nowrap;
		max-width: 150px;
		vertical-align: top;
	}
</style>
<div class="lui_list_operation_order_text">
	${ lfn:message('list.orderType') }：
</div>
<div class="lui_list_operation_sort_toolbar">
	<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5" id="sortToolbar">
		<list:sortgroup>
			<c:forEach items="${viewOrderInfo.columns}" var="order">
				<list:sort property="${order.property.name }" text="${order.messageKey }" group="sort.list" value="${order.orderType == 'desc' ? 'down' : 'up'}"></list:sort>
			</c:forEach>
		</list:sortgroup>
	</ui:toolbar>
</div>
<c:choose>
	<c:when test="${showConfig.pagingSetting eq '2' }">
		<list:paging layout="sys.ui.paging.top.simple" ></list:paging>
	</c:when>
	<c:when test="${showConfig.pagingSetting eq '3' }">
			<list:paging layout="sys.ui.paging.top.can.change" ></list:paging>
	</c:when>
	<c:otherwise>
		<list:paging layout="sys.ui.paging.top"></list:paging>
	</c:otherwise>
</c:choose>
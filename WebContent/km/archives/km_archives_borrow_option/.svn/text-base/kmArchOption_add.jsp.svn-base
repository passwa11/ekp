<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<frameset cols="20%,80%"> 
	<frame name="category" src="<%=request.getContextPath() %>/km/archives/km_archives_borrow_option/kmArchMain_tree.jsp?fdTemplatId=${HtmlParam.fdTemplateId }" > 
	<frameset rows="60%,40%">
		<frame name="chacked" 
		src="<%=request.getContextPath()%>/km/archives/km_archives_borrow/kmArchBorrowOption.do?method=checkArchList&fdTemplatId=${HtmlParam.fdTemplateId }"
		selectId="${HtmlParam.selected }" 
		> 
		<frame name="oitems"
		
		src="<%=request.getContextPath() %>/km/archives/km_archives_borrow_option/kmArchCheck_list.jsp">
		 <%-- src="<%=request.getContextPath()%>/km/archives/km_archives_borrow/kmArchBorrowOption.do?method=redirectCheckList"> --%> 
		
			 
	</frameset>
</frameset> 

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<frameset cols="20%,80%"> 
	<frame name="category" src="<%=request.getContextPath() %>/km/archives/km_archives_destroy_option/kmArchMain_tree.jsp" > 
	<frameset rows="60%,40%">
		<frame name="chacked" src="<%=request.getContextPath()%>/km/archives/km_archives_destroy/kmArchDestroyOption.do?method=checkArchList&selected=${HtmlParam.selected }&fdCurDestroyId=${HtmlParam.fdId}"> 
		<frame name="oitems" src="<%=request.getContextPath()%>/km/archives/km_archives_destroy/kmArchDestroyOption.do?method=checkList&rowsize=50" > 	 
	</frameset>
</frameset>

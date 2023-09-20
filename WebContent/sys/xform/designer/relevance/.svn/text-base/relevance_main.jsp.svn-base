<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.relevance.ISysFormRelevanceService" %>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.relevance.spring.SysFormRelevanceServiceImp" %>
<script>
	Com_IncludeFile('relevance_main.css',Com_Parameter.ContextPath+'sys/xform/designer/relevance/css/','css',true);
	Com_IncludeFile('relevanceObj.js',Com_Parameter.ContextPath+'sys/xform/designer/relevance/','js',true);
</script>
<%
	String isDetail = "false";
	//if(request.getParameter("controlId").indexOf("!{index}") > -1){
	//	isDetail = "true";
	//}
	pageContext.setAttribute("_isDetail", isDetail);
%>
<div>
	<!-- 编辑页面可以操控关联控件 -->
	<xform:editShow>
		<!-- 判断当前用户是否是起草人，如果是起草人，则可编辑，不然显示页面跟view 页面一样，多了一个提示 -->
		<% 
			ISysFormRelevanceService sysFormRelevanceService = new SysFormRelevanceServiceImp();
			Boolean isDraft = true;
			String fdId = request.getParameter("fdId");
			if(StringUtil.isNotNull(fdId) && !sysFormRelevanceService.isDraft(fdId)){
				isDraft = false;
			}
			if(isDraft){
		%>
			<script>
				Xform_ObjectInfo.isDraft = true;
			</script>
			<script>
				// 初始化展示编辑文档	
			</script>
		<%}else{ %>
			<script>
			Xform_ObjectInfo.isDraft = false;
				// 初始化展示查看文档，警告信息提示	
			</script>
		<%} %>
	</xform:editShow>
	
</div>
<!-- 查看页面只需展示展示文档即可 -->
<xform:viewShow>
	<script>
		// 展示查看文档
	</script>
</xform:viewShow>

<script>
	new RelevanceDocObj({'controlId':'${param.fdControlId}'});
</script>



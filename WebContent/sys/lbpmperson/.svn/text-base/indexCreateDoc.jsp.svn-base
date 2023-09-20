<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
<template:replace name="body">
	<script type="text/javascript">
		Com_IncludeFile("data.js|jquery.js");
		seajs.use(['theme!list']);	
	</script>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/lbpmperson/style/css/docreater.css" />
	<div style="width:100%;">
	  <ui:tabpanel layout="sys.ui.tabpanel.list">
		 <ui:content title="${ lfn:message('sys-lbpmperson:lbpmperson.create') }" style="margin-top:10px;" >
		 				<jsp:include page="/sys/lbpmperson/person_efficiency/summary_count2.jsp"></jsp:include>
			<%--
			     简单分类的：
			    /sys/lbpmperson/import/createDoc.jsp?cateType=simpleCategory&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}"
			    全局分类的
			    /sys/lbpmperson/import/createDoc.jsp?cateType=globalCategory&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}"
			 --%>
			<c:import url="/sys/lbpmperson/import/createDoc_ui.jsp" charEncoding="UTF-8"></c:import>
		</ui:content>
		</ui:tabpanel>
	  </div> 
	</template:replace>
		
</template:include>
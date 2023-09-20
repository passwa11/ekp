<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<script type="text/javascript">
		Com_IncludeFile("data.js|jquery.js");
		seajs.use(['theme!list','sys/lbpmperson/style/css/docreater.css#']);	
	</script>
			<%--
			     简单分类的：
			    /sys/lbpmperson/import/createDoc.jsp?cateType=simpleCategory&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}"
			    全局分类的
			    /sys/lbpmperson/import/createDoc.jsp?cateType=globalCategory&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}"
			 --%>
			<c:import url="/sys/lbpmperson/import/createDoc.jsp?cateType=${JsParam.cateType}&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}&customTemplateKey=${JsParam.customTemplateKey}" charEncoding="UTF-8"></c:import>
		
	</template:replace> 
</template:include>
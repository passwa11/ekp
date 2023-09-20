<%@ page language="java" pageEncoding="UTF-8" %>
<!-- 项目路径 -->
<%@ include file="/sys/ui/jsp/common.jsp" %>
<!-- 引入seajs，dialog等 -->
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!-- 保留配置页面样式 -->
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script>
<!-- 保留配置页面字体大小 -->
<style>
	body, input, textarea, div, a, table, tr, td, th {
		font-size: 12px;
	}
</style>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	Com_IncludeFile("placeholderNewInstance.js",Com_Parameter.ContextPath + "sys/modeling/main/xform/controls/placeholder/",null,true);
</script>

<div class="modelingPlaceholder" style="display:none;">
	<script type="text/config">
		{
			"controlId" : "${JsParam.controlId}",
			"status" : "${JsParam.status}",
			"width" : "${JsParam.width}",
			"required" : "${JsParam.required}",
			"label" : "${JsParam.label}"
		}
	</script>
</div>

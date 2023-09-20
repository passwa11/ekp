<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	Com_IncludeFile("fillingNewInstance.js",Com_Parameter.ContextPath + "sys/modeling/main/xform/controls/filling/",null,true);
</script>

<div class="modelingPlaceholder" style="display:none;">
	<script type="text/config">
		{
			"controlId" : "${JsParam.controlId}",
			"bindDom" : "${JsParam.bindDom}",
			"bindCount" : "${JsParam.bindCount}",
			"status" : "${JsParam.status}",
			"label" : "${JsParam.label}"
		}

	</script>
</div>

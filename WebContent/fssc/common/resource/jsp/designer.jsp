<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
//修复自定义表单编辑无法重新选择表单引用模式
Com_AddEventListener(window, "load", function(){
	var $fdMode = $("[name='sysFormTemplateForms."+"${HtmlParam.fdKey}"+".fdMode']",window.top.document);
    var modelVal = $fdMode.val();
    if (modelVal === "3") {
        $fdMode.removeAttr("disabled");
        $fdMode.removeClass("removeSelectAppearance");
    }
});
</script>
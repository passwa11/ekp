<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%-- 日志导出按钮 --%>
<kmss:auth requestURL="/sys/log/sys_log_export/sysLogExport.do?method=export" requestMethod="POST">
    <!-- 日志导出 -->
    <ui:button text="${lfn:message('sys-log:sysLogExport.export')}" onclick="exportAll('${param.listMethod }', '${param.eventType }')" order="2" ></ui:button>
</kmss:auth>
<%-- 日志下载按钮 --%>
<kmss:auth requestURL="/sys/log/sys_log_export/index.jsp" requestMethod="POST">
    <!-- 日志导出 -->
    <ui:button text="${lfn:message('sys-log:sysLogExport.download')}" onclick="downloadPage()" order="2" ></ui:button>
</kmss:auth>

<script>
	var listOption = {
	    contextPath: '${LUI_ContextPath}',
		lang : {
			exportConfirmSelecte : "${lfn:message('sys-log:page.export.confirm.selecte')}",
			exportConfirmAll : "${lfn:message('sys-log:page.export.confirm.all')}",
			exportTitle : "${lfn:message('sys-log:sysLogExport.export')}",
			noRecord : "${lfn:message('return.noRecord')}"
		}
	}
 	Com_IncludeFile("logExport.js", "${LUI_ContextPath}/sys/log/sys_log_export/ui/", 'js', true);
</script>

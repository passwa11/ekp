<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*,com.landray.kmss.util.*,java.io.*,org.apache.commons.io.IOUtils,
	com.landray.kmss.sys.modeling.log.ModelingLogTool
	" %>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" />
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
<title>
	低代码平台后台运行日志
</title>
<body>
		<%
		String logpath = request.getParameter("logpath");
		String str = "";
		String num = request.getParameter("num");
		if(StringUtil.isNotNull(num)){
			int count = Integer.parseInt(num);
			str = ModelingLogTool.readFileContent(logpath,"\r\n",count);
		}else{
			str = ModelingLogTool.readFileContent(logpath,"\r\n");
		}
		%>
	<textarea style="width:99%; height:780px;"><%=str%></textarea>
</body>
</html>
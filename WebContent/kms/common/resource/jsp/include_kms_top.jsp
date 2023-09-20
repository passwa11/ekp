<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=5" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@ include file="/kms/common/resource/jsp/tags.jsp" %>
<%@ include file="/kms/common/resource/jsp/include_ekp.jsp" %>
<%@ include file="/kms/common/resource/jsp/include_kms.jsp" %>
<% response.addHeader("X-UA-Compatible", "IE=5"); %>
<link href="${kmsResourcePath }/favicon.ico" rel="shortcut icon" >
<link href="${kmsThemePath }/public.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/common.js"></script>
<script>
Com_IncludeFile("jquery.js|docutil.js|optbar.js|validator.jsp|validation.jsp|doclist.js|dialog.js|data.js|kms_tmpl.js|json2.js", null, "js");
</script>	
<script>
// kmsJS
Com_IncludeFile('kms.js',"${kmsResourcePath }/js/lib/","js",true);
Com_IncludeFile('template.js|kms_portlet.js|kms_common.js|kms_utils.js',"${kmsResourcePath }/js/","js",true);
</script>

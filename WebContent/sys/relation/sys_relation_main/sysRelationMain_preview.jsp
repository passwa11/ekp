<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
    var param_frameName ="${JsParam.frameName}";
    var sysRelationMain_do_url = '<c:url value="/sys/relation/sys_relation_main/sysRelationMain.do" />';
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	Com_IncludeFile("data.js|jquery.js", null, "js");
	Com_IncludeFile("sysRelationMain_preview.js", Com_Parameter.ContextPath+"sys/relation/sys_relation_main/js/", null, true);
</script>
</head>
<body style="background-color: transparent">
<p class="txttitle"><br><bean:message bundle="sys-relation" key="sysRelationMain.previewResult" /></p>
<table id="tb_normal" width=95% height="80%">
<tr>
	<td width="20%" valign="top" nowrap="nowrap" style="text-align: left;padding-left: 5px;">
		<span id="count" style="line-height: 18px;"></span>
	</td>
	<td style="position: relative;border-left: 1px solid rgb(225, 225, 225);" valign="top">
		<iframe id="IF_sysRelation_content" name="relation_form_iframe" allowTransparency="true" width="100%" height="100%" marginheight="0" marginwidth="0" scrolling=no frameborder=0 onload="dyniFrameSize(this);"></iframe>
	</td>
</tr>
</table>
</body>
</html>
		

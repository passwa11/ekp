<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
	Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script>
</head>
<body>
	<table class="tb_normal" style="width:95%">
		<tr>
			<td  class="td_normal_title" align="center">
				<span style="color:red;font-weight: bold;"><bean:message bundle="sys-xform-base" key="Designer_Lang.fieldLayout_noParameters" /></span>
			</td>
			
		</tr>
		<tr>
		<td  class="td_normal_title" align="center">
				<input type="button" class="btnopt"
		value="${lfn:message('button.close')}" onClick="window.close();">
			</td>
		</tr>
	</table>
</body>
</html>
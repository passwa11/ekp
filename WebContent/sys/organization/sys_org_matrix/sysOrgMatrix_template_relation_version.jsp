<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String matrixId = request.getParameter("matrixId");
	String curVer = request.getParameter("curVer");
	pageContext.setAttribute("matrixId", matrixId);
	pageContext.setAttribute("curVer", curVer);
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
	</template:replace>
	<template:replace name="content">
		<ul class="lui_maxtrix_relation_version"></ul>
		
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog', 'lui/topic'], function($, dialog, topic) {
				$.post("${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions", {"fdId": "${matrixId}"}, function(res) {
					$(".lui_maxtrix_relation_version").empty();
					if(res) {
						for(var i in res) {
							var ver = res[i].fdName;
							var enabled = res[i].fdIsEnable;
							var verName = ver;
							var disabled = "";
							if(ver == "${curVer}") {
								disabled = " checked disabled='disabled'";
								verName += "<bean:message bundle='sys-organization' key='sysOrgMatrix.relation.curVer'/>";
							} else if(!enabled) {
								disabled = " disabled='disabled'";
								verName += " (<span style='color:red;'><bean:message bundle='sys-organization' key='sysOrgMatrix.version.nonactivated'/></span>)";
							}
							$(".lui_maxtrix_relation_version").append("<li><label><input type='radio' name='version' value='" + ver + "'" + disabled + ">" + verName + "</label></li>");
						}
					}
				}, "json");
			});
		</script>
	</template:replace>
</template:include>
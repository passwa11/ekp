<%@ page language="java" 
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%
	IntellConfig intellConfigViewInclude = new IntellConfig();
	String intellConfigViewInclude_aipUrl = intellConfigViewInclude.getItDomain();
	if (intellConfigViewInclude_aipUrl!=null && intellConfigViewInclude_aipUrl.endsWith("/")){
		intellConfigViewInclude_aipUrl = intellConfigViewInclude_aipUrl.substring(0, intellConfigViewInclude_aipUrl.length()-1);
	}
	String intellConfigViewInclude_systemId = intellConfigViewInclude.getSystemName();
%>
<!-- 
	参数解释：
	formName： 表单名称
	useTab：是否以表格的形式展现
	showEditButton：是否支持阅读页面调整标签
	fdQueryCondition：联想查询，需要传递具体的值，跟sysTagMain_edit.jsp的fdQueryCondition对应：
	例如知识仓库：
		sysTagMain_edit.jsp的fdQueryCondition为docCategoryId;docDeptId
		sysTagMain_view.jsp的fdQueryCondition为${kmsMultidocKnowledgeForm.docCategoryId };${kmsMultidocKnowledgeForm.docDeptId }
 -->
<c:if test="${useTab!=true}">
	<c:choose>
		<c:when test="${param.field=='true'}">
			${sysTagMainForm.fdTagNames}
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				var tag_params = {
					"model" : "view",
					"fdTagNames" : "${fn:escapeXml(sysTagMainForm.fdTagNames)}",
					"render" : "${JsParam.render}"
				};
				Com_IncludeFile("jquery.js");
				Com_IncludeFile("tag.js",
						"${KMSS_Parameter_ContextPath}third/intell/resource/js/", "js", true);
			</script>
			<script type="text/javascript">
				if (window.Intell_Opt == null) {
					window.Intell_Opt = new IntellOpt('${modelName}', '${mainForm.fdId}', '<%=intellConfigViewInclude_aipUrl%>','<%=intellConfigViewInclude_systemId%>',
							tag_params);
				}
				Com_AddEventListener(window, 'load', function() {
					window.Intell_Opt.onload();
				});
			</script>
			
			<div class="intell_tag_area" display = "none">
				<div id="intellTagView" class="intell_tag_view" style="padding-left: 7px;"></div>
			</div>
	</c:otherwise>
	</c:choose>
</c:if>
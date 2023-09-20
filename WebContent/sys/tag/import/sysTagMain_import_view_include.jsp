<%@ page language="java" 
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
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
<div style="display:none;">
<input type="hidden" id="systag_fdTagNames_hidden" value="<c:out value='${sysTagMainForm.fdTagNames }' />" />
</div>



<%-- 有表格的情况 --%>
<c:if test="${useTab==true && not empty sysTagMainForm.fdTagNames}">
<script type="text/javascript">
		var tag_params = {
			"model" : "view",
			"fdTagNames" : document.getElementById("systag_fdTagNames_hidden").value,
			"render" : "${JsParam.render}"
		};
		Com_IncludeFile("jquery.js");
		Com_IncludeFile("tag.js",
				"${KMSS_Parameter_ContextPath}sys/tag/resource/js/", "js", true);
	</script>
	<script type="text/javascript">
		if (window.tag_opt == null) {
			window.tag_opt = new TagOpt('${modelName}', '${mainForm.fdId}', '',
					tag_params);
		}
		Com_AddEventListener(window, 'load', function() {
			window.tag_opt.onload();
		});
	</script>
	<tr>
		<td class="td_normal_title" width=15% nowrap><bean:message
				bundle="sys-tag" key="table.sysTagTags" /></td>
		<td colspan="3"><%
		SysTagMainForm sysTagMainForm= (SysTagMainForm)pageContext.getAttribute("sysTagMainForm");
		String[] tags=sysTagMainForm.getFdTagNames().split("\\s+");   
		request.setAttribute("tags",tags);
		          %>
		<div class="tag_content"></div>
		</td>
	</tr>
</c:if>
<%-- 无表格的情况 --%>
<c:if test="${useTab!=true}">
	<c:choose>
		<c:when test="${param.field=='true'}">
			${sysTagMainForm.fdTagNames}
		</c:when>
		<c:otherwise>
			<script type="text/javascript">
				var tag_params = {
					"model" : "view",
					"fdTagNames" : document.getElementById("systag_fdTagNames_hidden").value,
					"render" : "${JsParam.render}"
				};
				Com_IncludeFile("jquery.js");
				Com_IncludeFile("tag.js",
						"${KMSS_Parameter_ContextPath}sys/tag/resource/js/", "js", true);
			</script>
			<script type="text/javascript">
				if (window.tag_opt == null) {
					window.tag_opt = new TagOpt('${modelName}', '${mainForm.fdId}', '',
							tag_params);
				}
				Com_AddEventListener(window, 'load', function() {
					window.tag_opt.onload();
				});
			</script>
			
			<div class="tag_area" style="display: none">
				<c:if test="${not empty param.showAwikiNavType && param.showAwikiNavType == 'true' }">
			    <div class="com_bgcolor_d" style="width: 80%; height: 1px; margin-top: 20px; margin-bottom: 10px;"></div>
				<div class="" style="font-size: 16px">
				<span style="width:5px;height:12px;display:inline-block; margin-right: 5px;" 
				 class="com_bgcolor_d"></span><span><bean:message
						bundle="sys-tag" key="sysTagTags.title" /></span>
				</div>
				<div class="tag_content" style="padding-left: 7px;"></div>
				</c:if>
				<c:if test="${empty param.showAwikiNavType || param.showAwikiNavType == 'false' }">
					<c:if test="${empty param.showTitle}">
						<div class="tag_title">
								<image src="${LUI_ContextPath}/sys/tag/images/tag.png">&nbsp;<bean:message
									bundle="sys-tag" key="sysTagTags.title" />
						</div>
					</c:if>
					<div class="tag_content"></div>
				</c:if>
			</div>
	</c:otherwise>
	</c:choose>
</c:if>
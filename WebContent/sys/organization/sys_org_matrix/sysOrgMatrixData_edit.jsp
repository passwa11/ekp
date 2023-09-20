<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ sysOrgMatrixForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="addData();" />
				</c:when>
				<c:when test="${ sysOrgMatrixForm.method_GET == 'edit' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="updateData();" />
				</c:when>
			</c:choose>
	    	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style>
			.field_title{
				text-align: center;
				font-weight: bold;
			}
		</style>
		<script>
			Com_IncludeFile("dialog.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 95%; margin: 10px auto;">
		<p class="txttitle">
			<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <bean:message key="button.edit"/>
		</p>
		
		<html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do">
			<table class="tb_normal" width=100%>
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgMatrix.fdName"/>
					</td>
					<td width=35%>
					    ${sysOrgMatrixForm.fdName}
					</td>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-organization" key="sysOrgMatrix.fdCategory"/>
					</td>
					<td width=35%>
						${sysOrgMatrixForm.fdCategoryName}
					</td>
				</tr>
				
				<!-- 条件选项字段 -->
				<tr>
					<td class="td_normal_title field_title" colspan="4">
						<bean:message bundle="sys-organization" key="sysOrgMatrix.conditional.field"/>
					</td>
				</tr>
				<tr>
				<c:set var="fieldCount" value="${fn:length(sysOrgMatrixForm.fdRelationConditionals)}"/>
				<c:forEach items="${sysOrgMatrixForm.fdRelationConditionals}" var="conditional" varStatus="vstatus">
					<c:set var="orgType" value="${conditional.fdType == 'org' ? 'ORG_TYPE_ORG' : conditional.fdType == 'dept' ? 'ORG_TYPE_DEPT' : conditional.fdType == 'post' ? 'ORG_TYPE_POST' : conditional.fdType == 'person' ? 'ORG_TYPE_PERSON' : conditional.fdType == 'group' ? 'ORG_TYPE_GROUP' : ''}"/>
					<c:if test="${vstatus.index > 0 && vstatus.index % 2 == 0}">
					</tr>
					<tr>
					</c:if>
					<td width=15% class="td_normal_title">
						${conditional.fdName}
					</td>
					<c:choose>
						<c:when test="${vstatus.index == fieldCount - 1}">
						<td width=85% colspan="3">
						</c:when>
						<c:otherwise>
						<td width=35%>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${orgType != ''}">
							<input type="hidden" data-type="fieldId" name="${conditional.fdId}" value="${conditional.fdConditionalId}">
							<input type="text" name="${conditional.fdFieldName}" value="${conditional.fdConditionalValue}" style="width:80%" readonly="true" class="inputsgl">
							<a href="#" onclick="Dialog_Address(false, '${conditional.fdId}', '${conditional.fdFieldName}', null, ${orgType});">
								<bean:message key="dialog.selectOrg" />
							</a>
						</c:when>
						<c:when test="${conditional.fdMainDataType == 'sys'}">
							<input type="hidden" data-type="fieldId" name="${conditional.fdId}" value="${conditional.fdConditionalId}">
							<input type="text" name="${conditional.fdFieldName}" value="${conditional.fdConditionalValue}" style="width:80%" readonly="true" class="inputsgl">
							<a href="#" onclick="Dialog_MainData('${conditional.fdId}', '${conditional.fdFieldName}', '${conditional.fdName}');">
								<bean:message key="dialog.selectOrg" />
							</a>
						</c:when>
						<c:when test="${conditional.fdMainDataType == 'cust'}">
							<input type="hidden" data-type="fieldId" name="${conditional.fdId}" value="${conditional.fdConditionalId}">
							<input type="text" name="${conditional.fdFieldName}" value="${conditional.fdConditionalValue}" style="width:80%" readonly="true" class="inputsgl">
							<a href="#" onclick="Dialog_Tree(false, '${conditional.fdId}', '${conditional.fdFieldName}', null, 'sysOrgMatrixMainDataService&id=${conditional.fdType}', '${conditional.fdName}');">
								<bean:message key="dialog.selectOrg" />
							</a>
						</c:when>
						<c:otherwise>
							<input type="text" data-type="fieldId" name="${conditional.fdId}" value="${conditional.fdConditionalValue}" subject="${conditional.fdName}" style="width:90%" class="inputsgl" validate="maxLength(200)">
						</c:otherwise>
					</c:choose>
						
					</td>
				</c:forEach>
				</tr>
								
				<!-- 结果选项字段 -->
				<tr>
					<td class="td_normal_title field_title" colspan="4">
						<bean:message bundle="sys-organization" key="sysOrgMatrix.result.field"/>
					</td>
				</tr>
				<tr>
				<c:set var="fieldCount" value="${fn:length(sysOrgMatrixForm.fdRelationResults)}"/>
				<c:forEach items="${sysOrgMatrixForm.fdRelationResults}" var="result" varStatus="vstatus">
					<c:set var="orgType" value="${result.fdType == 'post' ? 'ORG_TYPE_POST' : 'ORG_TYPE_PERSON'}"/>
					<c:if test="${vstatus.index > 0 && vstatus.index % 2 == 0}">
					</tr>
					<tr>
					</c:if>
					<td width=15% class="td_normal_title">
						${result.fdName}
					</td>
					<c:choose>
						<c:when test="${vstatus.index == fieldCount - 1}">
							<td width=85% colspan="3">
						</c:when>
						<c:otherwise>
							<td width=35%>
						</c:otherwise>
					</c:choose>
						<input type="hidden" data-type="fieldId" name="${result.fdId}" value="${result.fdResultValueIds}">
						<input type="text" name="${result.fdFieldName}" value="${result.fdResultValueNames}" style="width:80%" readonly="true" class="inputsgl">
						<a href="#" onclick="Dialog_Address(true, '${result.fdId}', '${result.fdFieldName}', null, ${orgType});">
							<bean:message key="dialog.selectOrg" />
						</a>
					</td>
				</c:forEach>
				</tr>
				<input type="hidden" name="matrixData">
				<input type="hidden" name="fdMatrixId" value="${sysOrgMatrixForm.fdId}">
				<input type="hidden" name="dataId" value="${dataId}">
			</table>
		</html:form>
		</div>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysOrgMatrixForm']);
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				// 新增数据
				window.addData = function() {
					if(buildForm()) {
						Com_Submit(document.sysOrgMatrixForm, 'addData');
					}
				}
				
				// 更新数据
				window.updateData = function() {
					if(buildForm()) {
						Com_Submit(document.sysOrgMatrixForm, 'updateData');
					}
				}
				
				// 系统主数据
				window.Dialog_MainData = function(fieldId, fieldName, title) {
					var selected = $("input[name='" + fieldId + "']").val();
					dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId=${sysOrgMatrixForm.fdId}&fieldName=" + fieldName + "&selected=" + selected,
							title, function(data) {
						if(data) {
							if(data == "clear") {
								$("input[name='" + fieldId + "']").val("");
								$("input[name='" + fieldName + "']").val("");
							} else {
								$("input[name='" + fieldId + "']").val(data.id);
								$("input[name='" + fieldName + "']").val(data.name);
							}
						}
					}, {
						width : 1200,
						height : 600,
						buttons : [{
							name : '<bean:message key="button.ok" />',
							focus : true,
							fn : function(value, dialog) {
								if(dialog.frame && dialog.frame.length > 0) {
									var frame = dialog.frame[0];
									var contentDoc = $(frame).find("iframe")[0].contentDocument;
									$(contentDoc).find("input[name='List_Selected']:checked").each(function(i, n) {
										value = {};
										value.id = $(n).val();
										value.name = $(n).parent().parent().find("td.mainData_title").text();
										return true;
									});
								}
								setTimeout(function() {
									dialog.hide(value);
								}, 200);
							}
						}, {
							name : '<bean:message key="button.cancel" />',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide();
							}
						}, {
							name : '<bean:message key="button.clear" />',
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide("clear");
							}
						}]
					});
				}
				
				buildForm = function() {
					var data = {};
					$("input[data-type=fieldId]").each(function(i, n) {
						var val = $(n).val().replace(/(^\s*)|(\s*$)/g, "");
						if(val.length > 0) {
							data[$(n).attr("name")] = val;
						}
					});
					if($.isEmptyObject(data)) {
						dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.data.empty"/>');
						return false;
					}
					$("input[name=matrixData]").val(JSON.stringify(data));
					return true;
				}
			});
		</script>
	</template:replace>
</template:include>

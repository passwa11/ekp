<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="content">
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${modelingAppCategoryForm.method_GET=='edit'}">
				<ui:button text="${ lfn:message('button.update') }" order="1"  onclick="submitForm('update');"></ui:button>
			</c:if>
			<c:if test="${modelingAppCategoryForm.method_GET=='add'}">
				<ui:button text="${ lfn:message('button.save') }" order="1"  onclick="submitForm('save');"></ui:button>
			</c:if>
		</ui:toolbar>
		<html:form action="/sys/modeling/base/modelingAppCategory.do">
			<div style="padding:15px 0px">
				<table class="tb_normal" width="95%">
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('sys-modeling-base:modeling.category.name')}
						</td>
						<td>
							<xform:xtext property="fdName" required="true" subject="${lfn:message('sys-modeling-base:modeling.category.name')}"></xform:xtext>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('sys-modeling-base:modelingAppListview.fdOrder')}
						</td>
						<td>
							<xform:xtext property="fdOrder" subject="${lfn:message('sys-modeling-base:modelingAppListview.fdOrder')}" validators="digits"  style="width:95%;" ></xform:xtext>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('sys-modeling-base:modelingBusiness.fdDesc')}
						</td>
						<td>
							<xform:textarea property="fdDesc" style="width:85%" />
						</td>
					</tr>
				</table>
			</div>
		</html:form>
		<script>
			var validation = $KMSSValidation();
			function submitForm(method){
				if (validation.validate()) {
					Com_Submit(document.modelingAppCategoryForm,method);
				}
			}
		</script>
	</template:replace>
</template:include>
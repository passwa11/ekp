<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<ui:content title="${ lfn:message('sys-modeling-main:modelingAppBaseModel.reviewContent') }" toggle="false" >
			<%-- 表单 --%>
			<div id="sysModelingXform">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent" />
					<c:param name="useTab" value="false" />
				</c:import>
			</div>
			<script>
				Com_IncludeFile('view_common.js','${KMSS_Parameter_ContextPath}sys/modeling/main/resources/js/','js',true);
			</script>
		</ui:content>
	</c:when>
	<c:when test="${param.contentType eq 'baseInfo'}">
	</c:when>
	<c:when test="${param.contentType eq 'other'}">
		<c:if test="${empty tabList }">
			<c:if test="${param.approveModel ne 'right'}">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
				</c:import>
			</c:if>
			 <%--权限机制 --%>
			<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="modelingAppModelMainForm" />
				<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
				<c:param name="isModeling" value="true"></c:param>
			</c:import>
		</c:if>
		<%-- 查看视图定义标签 --%>
		<c:import url="/sys/modeling/base/view/ui/viewtabs.jsp" charEncoding="UTF-8">
			<c:param name="expand" value="false" />
			<c:param name="approveModel" value="${param.approveModel}"></c:param>
		</c:import>
	</c:when>
</c:choose>
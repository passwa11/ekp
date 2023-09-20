<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
	</template:replace>
	<template:replace name="toolbar">
	</template:replace>
	<template:replace name="content">
		<html:hidden property="fdModelId"></html:hidden>
		<%-- 表单 --%>
		<c:choose>
			<%-- E签宝特殊处理 --%>
			<c:when test="${modelingAppModelMainForm.sysWfBusinessForm.fdIsHander == 'true' && modelingAppModelMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.eqbSign =='true'}">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop"
							 var-count="5" var-average='false' var-useMaxWidth='true'>
					<ui:content title="${ lfn:message('sys-modeling-main:modelingAppBaseModel.reviewContent') }" toggle="false">
						<div id="sysModelingXform">
							<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="modelingAppModelMainForm" />
								<c:param name="fdKey" value="modelingApp" />
								<c:param name="useTab" value="false" />
							</c:import>
						</div>
						<script>
							Com_IncludeFile("iframe.js",Com_Parameter.ContextPath+'sys/modeling/main/resources/js/view/','js',true);
						</script>
					</ui:content>
					<c:import url="/elec/eqb/elec_eqb_common_default/elecEqbCommonDefaultSign.do?method=getEqbSignPage" charEncoding="UTF-8">
						<c:param name="signId" value="${modelingAppModelMainForm.fdId }" />
						<c:param name="enable" value="true"></c:param>
					</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<div id="sysModelingXform">
					<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppModelMainForm" />
						<c:param name="fdKey" value="modelingApp" />
						<c:param name="useTab" value="false" />
					</c:import>
				</div>
				<script>
					Com_IncludeFile("iframe.js",Com_Parameter.ContextPath+'sys/modeling/main/resources/js/view/','js',true);
				</script>
			</c:otherwise>
		</c:choose>
	</template:replace>
</template:include>
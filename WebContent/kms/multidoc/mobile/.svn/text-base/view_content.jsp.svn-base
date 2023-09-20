<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<c:out value="${ kmsMultidocKnowledgeForm.docSubject }" />
	</template:replace>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/multidoc/mobile/css/view.css" />
	</template:replace>
	
	<template:replace name="content">

		<div data-dojo-type="mui/view/DocScrollableView">
			<div class="muiDocFrame">
				<c:import url="/kms/multidoc/mobile/content.jsp"></c:import>
			</div>
		</div>
	</template:replace>
</template:include>

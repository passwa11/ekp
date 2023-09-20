<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">

		<script>
			seajs.use([ 'kms/knowledge/kms_knowledge_ui/js/createForPortlet' ], function(
					create) {

				var id = '${param.categoryId}';

				if (id) {
					create.checkTemplateType({
						id : id,
						target : '_self'
					});
				} else {
					create.addDoc();
				}

			});
		</script>
	</template:replace>
</template:include>
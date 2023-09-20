<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list" canHash="true" tiny="true">
	<template:replace name="title">
		${lfn:message('kms-common:module.kms.allNotes') }
	</template:replace>
	<template:replace name="head">
		<script>
		</script>
		<style>	
			#content {
				
			}
			.header {
				display: flex;
				align-items: center;
				flex-direction: row;
				margin: 2rem 1.5rem 1rem 1.5rem;
				
			}
			.header_text {
				margin-left: 1rem;
				font-size: 1.6rem;
				color: #2A304A;
				letter-spacing: 0;
				line-height: 1.8rem;
			}
		</style>
		<mui:cache-file name="mui-kms-common-myNote.js" cacheType="md5" />
		<mui:cache-file name="mui-kms-common-myNote.css" cacheType="md5" />
	</template:replace>
	<template:replace name="content">	
		<div class="header">
			<img style="width:1.4rem;height:1.7rem;" src="${ LUI_ContextPath}/kms/common/mobile/myNote/image/noteTitle.svg">
			<div class="header_text">
				${param.courseName}
			</div>
		</div>
		<div  id="scroll" data-dojo-type="mui/list/StoreScrollableView" >
			<ul 
				data-dojo-type="mui/list/JsonStoreList"
				data-dojo-mixins="kms/common/mobile/myNote/js/MyNoteCourseNoteListMixin"
				data-dojo-props="url:'/kms/common/kms_notes/kmsCourseNotes.do?method=dataIndex&orderby=docCreateTime&ordertype=down&modelId=${param.fdModelId}&modelName=${param.fdModelName}&listType=my_notes'"						
				>
			</ul>
		</div>
	</template:replace>
</template:include>

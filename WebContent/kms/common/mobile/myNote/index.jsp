<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list" canHash="true" tiny="true">
	<template:replace name="title">
		${lfn:message('kms-common:module.kms.allNotes') }
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-kms-common-myNote.js" cacheType="md5" />
		<mui:cache-file name="mui-kms-common-myNote.css" cacheType="md5" />
	</template:replace>
	<template:replace name="content">		
		<div  id="scroll" data-dojo-type="mui/list/StoreScrollableView" >
			<ul 
				data-dojo-type="mui/list/JsonStoreList"
				data-dojo-mixins="kms/common/mobile/myNote/js/MyNoteCourseListMixin" >
			</ul>
		</div>
	</template:replace>
</template:include>

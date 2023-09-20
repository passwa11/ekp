<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" >
	<template:replace name="title">
		<c:out value="${lfn:message('sys-authorization:sysAuthTemplate.name')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/authorization/mobile/resource/css/role.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView" data-dojo-type="mui/list/StoreScrollableView">
		    <ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList muiAuthTemplateCardList"
		    	data-dojo-mixins="mui/list/TemplateItemListMixin" 
		    	data-dojo-props="url:'/sys/authorization/sys_auth_role/sysAuthRole.do?method=list&fdTemplate=1&categoryId=&orderby=docCreateTime&ordertype=down',lazy:false">
			</ul>
		</div>
	</template:replace>
</template:include>

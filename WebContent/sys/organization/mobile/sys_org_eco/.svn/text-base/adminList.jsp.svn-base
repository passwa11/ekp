<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.list" tiny="true">
	<template:replace name="title">
		${ lfn:message('sys-organization:sysOrgEco.name') }
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/eco/list.css">
    </template:replace>
	<template:replace name="content">
		<div class="sysOrgEcoContainer">
	        <div data-dojo-type="sys/organization/mobile/js/eco/muiOrgAdminListSearch"></div>
			<ul class="sysOrgEcoSearchListItem"
				data-dojo-type="mui/list/JsonStoreList"
	          	data-dojo-mixins="sys/organization/mobile/js/eco/muiOrgAdminListMixin"
	          	data-dojo-props="url:'/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=listMaintainableDept',
								lazy:false">
	       	</ul>
		</div>
			
	</template:replace>
</template:include>

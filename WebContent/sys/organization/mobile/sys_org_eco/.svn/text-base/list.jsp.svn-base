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
		<div class="muiOrgEcoContainer">
			<p class="muiOrgEcoTitle">${ lfn:message('sys-organization:sysOrgEco.name') }</p>

			<ul class="muiOrgEcoFlexList"
				data-dojo-type="sys/organization/mobile/js/eco/muiOrgListHeader">
			</ul>
			
			<ul class="muiOrgEcoPopulationList"
				data-dojo-type="mui/list/JsonStoreList"
	          	data-dojo-mixins="sys/organization/mobile/js/eco/muiOrgListMixin"
	          	data-dojo-props="url:'/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=list&parent=&orderby=fdElement.fdOrder,fdElement.fdNamePinYin&ordertype=up&rowsize=100',
								lazy:false">
	       	</ul>
		</div>
	</template:replace>
</template:include>

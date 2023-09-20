<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<bean:message key="module.km.archives" bundle="km-archives"/>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/km/archives/mobile/resource/css/archmulcate.css?s_cache=${MUI_Cache}"></link>
	<style>
#detailsView .muiListItem .muiComplexrTop .muiComplexrTitle {
	margin-left: 4.5rem;
}
</style>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
			<div
				data-dojo-type="mui/nav/MobileCfgNavBar" 
				data-dojo-props=" defaultUrl:'/km/archives/mobile/details/nav.jsp'">
			</div>
			
			<div
				data-dojo-type="mui/search/SearchButtonBar" 
				data-dojo-props="modelName:'com.landray.kmss.km.archives.model.KmArchivesMain' ">
			</div>
		</div>
		<div data-dojo-type="mui/list/NavSwapScrollableView" id="detailsView">
			 <ul
		    	data-dojo-type="mui/list/JsonStoreList"
		    	data-dojo-mixins="mui/list/TextItemListMixin,km/archives/mobile/details/kmArchivesStatusMixin">
			</ul>
		</div>
	</template:replace>
</template:include>

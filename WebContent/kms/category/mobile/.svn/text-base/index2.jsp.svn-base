<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<template:include ref="mobile.list" tiny="true" canHash="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			${lfn:message('kms-category:module.kms.category')}
		</c:if>
	</template:replace>
	<template:replace name="head">
		<style>
			.muiComplexlItem {
				padding-left: .8rem;
				padding-right: .8rem;
			}
			.muiListItem {
				padding-top: .8rem;
			}
			.muiComplexlCreated {
				padding-left: 1rem;
			}
		</style>
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
		<mui:cache-file name="mui-kms-category.js" cacheType="md5" />
	</template:replace>
	<template:replace name="content">
	
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
			<div data-dojo-type="mui/nav/MobileCfgNavBar"
				 data-dojo-props="modelName:'com.landray.kmss.kms.category.model.KmsCategoryMain'">
			</div>
			<div data-dojo-type="mui/search/SearchButtonBar"
		         data-dojo-props="modelName:'com.landray.kmss.kms.wiki.model.KmsWikiMain;com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge,com.landray.kmss.kms.kem.model.KmsKemMain'" >
		    </div>
		</div>
		
		<div data-dojo-type="mui/header/NavHeader">
		</div>
		
		<div data-dojo-type="mui/list/NavView">
	       <ul data-dojo-type="mui/list/HashJsonStoreList" 
	           data-dojo-mixins="mui/list/ComplexRItemListMixin">
	       </ul>
		</div>
		
	
	</template:replace>
</template:include>





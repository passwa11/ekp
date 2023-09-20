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
.noStatus .muiListItem .muiComplexrTop .muiComplexrTitle {
	margin-left: 0;
}
</style>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
			<div
				data-dojo-type="mui/nav/MobileCfgNavBar" 
				data-dojo-props=" defaultUrl:'/km/archives/mobile/approval/nav.jsp?type=${JsParam.type}'">
			</div>
			<div
				data-dojo-type="mui/search/SearchButtonBar" 
				data-dojo-props="modelName:'' " id="barButtonSearch">
			</div>
			<script type="text/javascript">
				require(['dojo/dom-style', 'dojo/dom', 'dojo/topic', 'dijit/registry', 'dojo/ready'], function(domStyle, dom, topic, registry, ready){
					function handleNavItemChange(tabIndex){
						switch(tabIndex){
							case 0:
								registry.byId('barButtonSearch').modelName = 'com.landray.kmss.km.archives.model.KmArchivesMain';
								if(dom.byId('btnBorrow'))
									domStyle.set(dom.byId('btnBorrow'),'display','none');
								break;
							case 1:
								registry.byId('barButtonSearch').modelName = 'com.landray.kmss.km.archives.model.KmArchivesBorrow';
								if(dom.byId('btnBorrow'))
									domStyle.set(dom.byId('btnBorrow'),'display','block');
								break;	
						}
					}
					topic.subscribe('/mui/navitem/_selected', function(tab, data){
						handleNavItemChange(tab.tabIndex);
					});
					ready(function(){
						handleNavItemChange(0);
					});
				});
			</script>
		</div>
		<div data-dojo-type="mui/list/NavSwapScrollableView" data-dojo-props="canStore:false">
			 <ul
		    	data-dojo-type="mui/list/JsonStoreList"
		    	data-dojo-mixins="mui/list/TextItemListMixin">
			</ul>
		</div>
		<kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add">
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			<li data-dojo-type="mui/tabbar/CreateButton"
		   		data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
		   		data-dojo-props="icon1:'',
		   			templURL:'/km/archives/mobile/resource/tmpl/simplecategory.jsp',
		   			url:'/km/archives/km_archives_template/kmArchivesTemplate.do?method=getTemplete',
		   			redirectUrl: '/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add4m&docTemplateId={fdId}&docTemplateName={fdName}'" id="btnBorrow" style="display: none;">
				<bean:message key="mui.borrow.add" bundle="km-archives"/>
			</li>
		</ul>
		</kmss:auth>
	</template:replace>
</template:include>

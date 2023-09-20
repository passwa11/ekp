<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4rem'">
	<div data-dojo-type="mui/header/HeaderItem" 
		data-dojo-mixins="mui/folder/_Folder,mui/back/HrefBackMixin"
		data-dojo-props="href:'/km/smissive/mobile/index.jsp'">
	</div>
	<div data-dojo-type="mui/header/HeaderItem" 
		data-dojo-props="label:'${JsParam.moduleName}',referListId:'_filterDataList'">
	</div>
	<div data-dojo-type="mui/header/HeaderItem" 
		data-dojo-mixins="mui/folder/Folder" 
		data-dojo-props="tmplURL:'/km/smissive/mobile/query.jsp'">
	</div>
</div>
<div id="scroll" data-dojo-type="mui/list/StoreScrollableView" class="gray">
	<div
		data-dojo-type="mui/search/SearchBar" 
		data-dojo-props="modelName:'com.landray.kmss.km.smissive.model.KmSmissiveMain',needPrompt:false,height:'4rem'">
	</div>
    <ul id="_filterDataList"
    	data-dojo-type="mui/list/JsonStoreList" 
    	data-dojo-mixins="mui/list/CardItemListMixin"
    	data-dojo-props="url:'${JsParam.queryStr}', lazy:false">
	</ul>
</div>

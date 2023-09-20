<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div data-dojo-type="mui/header/Header"
	data-dojo-props="height:'3.8rem'">
	<div data-dojo-type="mui/header/HeaderItem"
		data-dojo-props="label:'${param.moduleName}',referListId:'_filterDataList'">
	</div>
	<div data-dojo-type="mui/header/HeaderItem"
		data-dojo-mixins="mui/folder/_Folder,mui/simplecategory/SimpleCategoryDialogMixin"
		data-dojo-props="icon:'mui mui-ul',
			modelName:'com.landray.kmss.sys.bookmark.model.SysBookmarkCategory',
			redirectURL:'/sys/bookmark/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=list&cateid=!{curIds}&mydoc=true'">
	</div>
</div>

      <div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
	              <ul id="_filterDataList" data-dojo-type="mui/list/JsonStoreList"
		             data-dojo-mixins="sys/bookmark/mobile/js/SysBookListMixin"
		             data-dojo-props="url:'${param.queryStr}', lazy:false">
	             </ul>
      </div>


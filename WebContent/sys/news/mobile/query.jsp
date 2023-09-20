<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/query/QueryList" data-dojo-props="topHeight:!{topHeight}">
	<div data-dojo-type="mui/query/QueryListItem" 
		data-dojo-mixins="mui/simplecategory/SimpleCategoryDialogMixin" 
		data-dojo-props="label:'<bean:message key="portlet.cate" />',icon:'mui mui-Csort',
			modelName:'com.landray.kmss.sys.news.model.SysNewsTemplate',
			redirectURL:'/sys/news/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=!{curIds}&q.docStatus=30&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'">
	</div>
	<div data-dojo-type="mui/query/QueryListItem"
		data-dojo-mixins="mui/search/SearchBarDialogMixin" 
		data-dojo-props="label:'<bean:message key="button.search" />',icon:'mui mui-search', modelName:'com.landray.kmss.sys.news.model.SysNewsMain'">
	</div>
	<div data-dojo-type="mui/query/QueryListItem"
		data-dojo-mixins="mui/query/CommonQueryDialogMixin" 
		data-dojo-props="label:'<bean:message key="list.search" />',icon:'mui mui-query',
			redirectURL:'/sys/news/mobile/index.jsp?moduleName=!{text}&filter=1',
			store:[{'text':'<bean:message key="sysNewsMain.allNews" bundle="sys-news" />','dataURL':'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=&q.docStatus=30&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'},
			{'text':'<bean:message key="list.create" />','dataURL':'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=&q.mydoc=create&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'},
			{'text':'<bean:message key="list.approval" />','dataURL':'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=&q.mydoc=approval&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'},
			{'text':'<bean:message key="list.approved" />','dataURL':'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=&q.mydoc=approved&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'},
			{'text':'<bean:message key="sysNewsMain.fdIsTopNews" bundle="sys-news" />','dataURL':'/sys/news/sys_news_main/sysNewsMainIndex.do?method=listChildren&categoryId=&q.fdIsTop=1&orderby=fdIsTop%3BfdTopTime%3BdocAlterTime&ordertype=down'}
			]">
	</div>
</div>

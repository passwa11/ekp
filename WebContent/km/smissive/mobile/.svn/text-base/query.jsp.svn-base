<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/query/QueryList" data-dojo-props="topHeight:!{topHeight}">
    <div data-dojo-type="mui/query/QueryListItem" 
		data-dojo-mixins="mui/simplecategory/SimpleCategoryDialogMixin" 
		data-dojo-props="label:'<bean:message key="portlet.cate" />',icon:'mui mui-Csort',
			modelName:'com.landray.kmss.km.smissive.model.KmSmissiveTemplate',
			redirectURL:'/km/smissive/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren&categoryId=!{curIds}'">
	</div>
	<div data-dojo-type="mui/query/QueryListItem"
		data-dojo-mixins="mui/search/SearchBarDialogMixin" 
		data-dojo-props="label:'<bean:message key="button.search" />',icon:'mui mui-search', modelName:'com.landray.kmss.km.smissive.model.KmSmissiveMain'">
	</div>
	<div data-dojo-type="mui/query/QueryListItem"
		data-dojo-mixins="mui/query/CommonQueryDialogMixin" 
		data-dojo-props="label:'<bean:message key="list.search" />',icon:'mui mui-query',
			redirectURL:'/km/smissive/mobile/index.jsp?moduleName=!{text}&filter=1',
			store:[{'text':'<bean:message bundle="km-smissive" key="smissive.tree.myJob.alldoc" />','dataURL':'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren'},
			{'text':'<bean:message bundle="km-smissive" key="smissive.create.my" />','dataURL':'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren&q.mydoc=create'},
			{'text':'<bean:message bundle="km-smissive" key="smissive.approval.my" />','dataURL':'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren&q.mydoc=approval'},
			{'text':'<bean:message bundle="km-smissive" key="smissive.approved.my" />','dataURL':'/km/smissive/km_smissive_main/kmSmissiveMainIndex.do?method=listChildren&q.mydoc=approved'}
			]">
	</div>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="mui/query/QueryList" data-dojo-props="topHeight:!{topHeight}">
    <div data-dojo-type="mui/query/QueryListItem" 
		data-dojo-mixins="mui/syscategory/SysCategoryDialogMixin"
		data-dojo-props="label:'<bean:message key="portlet.cate" />',icon:'mui mui-Csort',
		    getTemplate:1,
		    selType: 0|1,
			modelName:'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
			redirectURL:'/km/review/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/km/review/km_review_index/kmReviewIndex.do?method=list&q.fdTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
	</div>
	<div data-dojo-type="mui/query/QueryListItem"
		data-dojo-mixins="mui/search/SearchBarDialogMixin" 
		data-dojo-props="label:'<bean:message key="button.search" />',icon:'mui mui-search', modelName:'com.landray.kmss.km.review.model.KmReviewMain'">
	</div>
</div>

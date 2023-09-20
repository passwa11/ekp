<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="sys/tag/mobile/import/js/select/DialogHeader"
	 class="tag_DialogHeader"
	 data-dojo-props="
	 	detailUrl:'{categroy.detailUrl}',
	 	key:'{categroy.key}',
	 	title:'{categroy.title}',
	 	height:'3.8rem'">
</div>
<div data-dojo-type="mui/category/CategoryNavInfo"
	 data-dojo-props="key:'{categroy.key}'">
</div>
<div class="tag_DialogCategoryHeader">
	<%--	id='tag_DialogCategorySelect{categroy.key} 该版不考虑多引入问题--%>
	<div id='tag_DialogCategorySelect'
		 class="tag_DialogCategorySelect"
		 style="width: 50%;float: left;"
		 data-dojo-type="sys/tag/mobile/import/js/select/DialogCategoryBar"
		 data-dojo-props="">
	</div>
	<div id='tag_DialogSearchBar'
		 class="tag_DialogSearchBar"
		 style="width: 50%;float: right;"
		 data-dojo-type="sys/tag/mobile/import/js/select/DialogSearchBar"
		 data-dojo-props="
		  orgType:{categroy.type},
		  searchUrl:'{categroy.searchDataUrl}',
		  key:'{categroy.key}',
		  exceptValue:'{categroy.exceptValue}',
		  height:'5rem'">
	</div>
</div>
<div data-dojo-type="mui/list/StoreElementScrollableView"
	 data-dojo-mixins="sys/tag/mobile/import/js/select/DialogTagScrollViewMixin"
	 data-dojo-props="isMul:{categroy.isMul}"
	 id="tag_ScrollableView"
	 class="tag_ScrollableView">
	<ul data-dojo-type="sys/tag/mobile/import/js/select/DialogList"
		id="tag_DialogItemListMixin"
		data-dojo-mixins="sys/tag/mobile/import/js/select/DialogItemListMixin"
		data-dojo-props="
			isMul:{categroy.isMul},
			key:'{categroy.key}',
			primaryKey:'{categroy.primaryKey}',
			dataUrl:'{categroy.listDataUrl}',
			searchUrl:'{categroy.searchDataUrl}',
			modelName:'{categroy.modelName}',
			modelId:'{categroy.modelId}',
			queryCondition:'{categroy.queryCondition}',
			fieldParam:'{categroy.fieldParam}',
			curIds:'{categroy.curIds}',
			curNames:'{categroy.curNames}',
			showTagInfo: {categroy.showTagInfo},
			selType:2,
			exceptValue:'{categroy.exceptValue}'">
	</ul>
</div>
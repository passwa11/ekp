<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div  class="muiCateSearchArea">
	<div data-dojo-type="sys/xform/mobile/controls/relevance/RelevanceSearchBar" 
		 data-dojo-props="modelName:'{argu.fdMainModelName}',fdControlId:'{argu.fdControlId}',extendXmlPath:'{argu.extendXmlPath}',height:'4rem',key:'{argu.key}'">
	</div>
</div>
<br>
<div data-dojo-type="sys/xform/mobile/controls/relevance/RelevancePath"
	data-dojo-props="key:'{argu.key}',fdControlId:'{argu.fdControlId}',extendXmlPath:'{argu.extendXmlPath}',height:'4rem',fdKey:'{argu.fdKey}',
	fdId:'{argu.fdId}',isBase:{argu.isBase},fdTemplateModelName: '{argu.fdTemplateModelName}',isSimpleCategory:{argu.isSimpleCategory},modelName:'{argu.fdMainModelName}'">
</div>
<div id="relevance_cate_{argu.key}"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="sys/xform/mobile/controls/relevance/RelevanceNextPageViewMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{argu.key}'">
	
	<ul data-dojo-type="sys/xform/mobile/controls/relevance/RelevanceList"
		data-dojo-mixins="sys/xform/mobile/controls/relevance/RelevanceItemListMixin"
		data-dojo-props="key:'{argu.key}',fdControlId:'{argu.fdControlId}',extendXmlPath:'{argu.extendXmlPath}',modelName:'{argu.fdMainModelName}',isUseNew:'{argu.isUseNew}',isMul:{argu.isMul},
		inputParams:'{argu.inputParams}',fdKey:'{argu.fdKey}'">
	</ul>
</div>
<div data-dojo-type="sys/xform/mobile/controls/relevance/RelevanceSelection"
	 data-dojo-props="key:'{argu.key}',curIds:'{argu.curIds}',curSubjects:'{argu.curSubjects}',curFdModelNames:'{argu.curFdModelNames}',curIsCreators:'{argu.curIsCreators}'" fixed="bottom">
</div>
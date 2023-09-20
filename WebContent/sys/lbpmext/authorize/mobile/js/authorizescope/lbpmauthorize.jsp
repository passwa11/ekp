<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div  class="muiCateSearchArea">
	<div data-dojo-type="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeSearchBar" 
		 data-dojo-props="height:'4rem',key:'{argu.key}'">
	</div>
</div>
<div data-dojo-type="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizePath"
	data-dojo-props="key:'{argu.key}',fdControlId:'{argu.fdControlId}',extendXmlPath:'{argu.extendXmlPath}',height:'4rem'">
</div>
<div id="lbpmAuthorize_cate_{argu.key}"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeNextPageViewMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{argu.key}'">
	
	<ul data-dojo-type="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeList"
		data-dojo-mixins="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeItemListMixin"
		data-dojo-props="key:'{argu.key}',fdControlId:'{argu.fdControlId}',extendXmlPath:'{argu.extendXmlPath}',modelName:'{argu.fdMainModelName}',isUseNew:'{argu.isUseNew}'">
	</ul>
</div>
<div data-dojo-type="sys/lbpmext/authorize/mobile/js/authorizescope/LbpmAuthorizeSelection" 
	 data-dojo-props="key:'{argu.key}',curIds:'{argu.curIds}',curNames:'{argu.curNames}'" fixed="bottom">
</div>
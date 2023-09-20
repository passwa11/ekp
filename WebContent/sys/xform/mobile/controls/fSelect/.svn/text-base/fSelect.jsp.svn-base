<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div id='_fSelect_sgl_view_{argu.key}'
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="sys/xform/mobile/controls/event/_NextPageViewMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{argu.key}'">
	<div id='_fSelect_sgl_search_list_{argu.key}' data-dojo-type="sys/xform/mobile/controls/fSelect/FSelectSearchBar" 
		data-dojo-props="key:'{argu.key}',data:'{argu.data}'" class="muiFSelectSearchBarList">
	</div>
	<ul id='_fSelect_sgl_list_{argu.key}' 
		data-dojo-type="sys/xform/mobile/controls/fSelect/FSelectList"
		data-dojo-props="isMul:{argu.isMul},pageAble:{argu.pageAble},key:'{argu.key}',required:{argu.required},leastNItem:{argu.leastNItem},subject:&quot;{argu.subject}&quot;,data:'{argu.data}'">
	</ul>
	<br/>
	<div data-dojo-type="sys/xform/mobile/controls/fSelect/FSelectSelection" 
		data-dojo-props="isMul:{argu.isMul},key:'{argu.key}'" fixed="bottom">
	</div>
</div>
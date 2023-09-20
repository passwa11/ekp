<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div data-dojo-type="km/imeeting/mobile/resource/js/PlaceHeader"
		data-dojo-props="key:'{categroy.key}',height:'3.8rem'">
</div>
<div data-dojo-type="dojox/mobile/ScrollableView">
	<ul data-dojo-type="mui/category/CategoryList"
		data-dojo-mixins="km/imeeting/mobile/resource/js/list/PlaceItemListMixin"
		data-dojo-props="'lazy':false,'selType':'1',key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'">
	</ul>
</div>
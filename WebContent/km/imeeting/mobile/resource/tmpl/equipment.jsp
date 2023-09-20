<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="muiCateHeader" style="height: 4rem; line-height: 4rem;">
	<div class="muiCateHeaderContent">
		<div class="muiCateHeaderTitle" style="position: relative;">${ lfn:message('km-imeeting:kmImeetingEquipment.btn.select') }</div>
	</div>
</div>
<div data-dojo-type="dojox/mobile/ScrollableView">
	<ul data-dojo-type="mui/category/CategoryList"
		data-dojo-mixins="km/imeeting/mobile/resource/js/list/EquipmentItemListMixin"
		data-dojo-props="'lazy':false,'selType':'1',key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',isMul:'{categroy.isMul}'=='true'">
	</ul>
</div>

<div data-dojo-type="km/imeeting/mobile/resource/js/EquipmentSelection" 
	data-dojo-props="
		isMul:'{categroy.isMul}'=='true',
		key:'{categroy.key}',
		curIds:'{categroy.curIds}',
		curNames:'{categroy.curNames}'
	" fixed="bottom">
</div>
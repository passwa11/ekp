<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="archSelector_toolbar">
	<div class="archSelector_toolbar_btn" style="left: 1rem; display: none;" id="archSelectorPreBtn">
		<i class="mui mui-back"></i>
		<span><bean:message bundle="km-archives" key="button.lever.up"/></span>
	</div>
	<div class="archSelector_toolbar_btn" style="right: 1rem;" id="archSelectorCalBtn">
		<span><bean:message bundle="km-archives" key="button.cancel"/></span>
	</div>
	<span id="archSelectorTitle"><bean:message bundle="km-archives" key="button.select"/></span>
</div>

<div id="archSelectorView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
	<ul data-dojo-type="km/archives/mobile/resource/js/ArchSelector" 
		data-dojo-props="isMul:true,key:'{categroy.key}',fdTemplatId:'${param.fdTemplatId}'"
		style="outline: none;">
	</ul>
	
</div>

<div data-dojo-type="km/archives/mobile/resource/js/ArchSelection" 
	data-dojo-props="key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'" fixed="bottom">
</div>
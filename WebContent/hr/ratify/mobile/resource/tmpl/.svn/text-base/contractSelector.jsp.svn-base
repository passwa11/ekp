<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="certSelector_toolbar">
	<div class="certSelector_toolbar_btn" style="left: 1rem; display: none;" id="certSelectorPreBtn">
		<i class="mui mui-back"></i>
		<span><bean:message key="list.lever.up"/></span>
	</div>
	<div class="certSelector_toolbar_btn" style="right: 1rem;" id="certSelectorCalBtn">
		<span><bean:message key="button.cancel"/></span>
	</div>
	<span id="certSelectorTitle">请选择</span>	
</div>

<div id="certSelectorView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',operateType:'{categroy.operateType}'">
	
	<ul data-dojo-type="hr/ratify/mobile/resource/js/contractSelector"
		data-dojo-props="operateType:'{categroy.operateType}'"
		style="outline: none;"></ul>
	
</div>

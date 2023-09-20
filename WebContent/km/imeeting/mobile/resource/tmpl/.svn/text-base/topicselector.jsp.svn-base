<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="topicSelector_toolbar">
	<div class="topicSelector_toolbar_btn" style="left: 1rem; display: none;" id="topicSelectorPreBtn">
		<i class="mui mui-back"></i>
		<span><bean:message key="list.lever.up"/></span>
	</div>
	<div class="topicSelector_toolbar_btn" style="right: 1rem;" id="topicSelectorCalBtn">
		<span><bean:message key="button.cancel"/></span>
	</div>
	<span id="topicSelectorTitle">请选择</span>	
</div>

<div id="topicSelectorView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
	<ul data-dojo-type="km/imeeting/mobile/resource/js/TopicSelector" style="outline: none;"></ul>
	
</div>

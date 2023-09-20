<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="certSelector_toolbar">
	<div class="certSelector_toolbar_btn" style="left: 1rem; display: none;" id="certSelectorPreBtn">
		<i class="mui mui-back"></i>
		<span>上一级</span>
	</div>
	<div class="certSelector_toolbar_btn" style="right: 1rem;" id="certSelectorCalBtn">
		<span>取消</span>
	</div>
	<span id="certSelectorTitle">请选择</span>	
</div>

<div id="certSelectorView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
	<ul data-dojo-type="km/certificate/mobile/resource/js/CertSelector" 
		data-dojo-props="isMul:true,key:'{categroy.key}'"
		style="outline: none;">
	</ul>
	
</div>

<div data-dojo-type="km/certificate/mobile/resource/js/CertSelection" 
	data-dojo-props="key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'" fixed="bottom">
</div>
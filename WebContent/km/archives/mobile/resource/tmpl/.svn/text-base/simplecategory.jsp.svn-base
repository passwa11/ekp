<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div id="allCateView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
    <ul
    	data-dojo-type="km/archives/mobile/resource/js/SimpleItemList"
    	data-dojo-props="
    		url:'<%=request.getContextPath()%>{categroy.url}',
    		redirectUrl: '<%=request.getContextPath()%>{categroy.redirectUrl}'
    	">
	</ul>
	
</div>

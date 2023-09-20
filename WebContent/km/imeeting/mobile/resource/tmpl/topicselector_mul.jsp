<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="topicSelector_toolbar">
	<div class="topicSelector_toolbar_btn" style="left: 1rem; display: none;" id="topicSelectorPreBtn">
		<i class="mui mui-back"></i>
		<span><bean:message key="list.lever.up"/></span>
	</div>
	<div class="topicSelector_toolbar_btn" style="left: 1rem; display: none;" id="topicSelectorBackBtn">
		<i class="mui mui-back"></i>
		<span><bean:message key="button.back"/></span>
	</div>
	<div class="topicSelector_toolbar_btn" style="right: 1rem;" id="topicSelectorCalBtn">
		<span><bean:message key="button.cancel"/></span>
	</div>
	<span id="topicSelectorTitle"><bean:message key="mobile.imeeting.select" bundle="km-imeeting"/></span>
	<span id="topicSearchTitle" style="display: none;"><bean:message key="mobile.imeeting.select" bundle="km-imeeting"/></span>
</div>

<div class="muiSearchParent">
	<div class="muiSearchType">
		<div data-dojo-type="km/imeeting/mobile/resource/js/Select"
			data-dojo-mixins="km/imeeting/mobile/resource/js/TopicSelectMixin"
			data-dojo-props="key:'{categroy.key}',subject:'下拉框',mul:false,name:'select1',store:window.store,searchUrl:'${LUI_ContextPath}/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&isDialog=0',searchName:'q.fdIsAccept'" class="toipicSearchType">
		</div>
	</div>
	
	<div
		data-dojo-type="mui/commondialog/template/DialogSearchBar" 
		data-dojo-props="needPrompt:false,height:'3.8rem',searchUrl:'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&isDialog=0',
		key:'{categroy.key}'" class="muiSearchContent">
	</div>
</div>

<div id="topicSelectorView_{categroy.key}" 
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/category/_ViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}'">
	
	<ul data-dojo-type="km/imeeting/mobile/resource/js/TopicSelector" 
		data-dojo-props="isMul:true,key:'{categroy.key}'"
		style="outline: none;" id="topicSelectorUl">
	</ul>
	
	<ul data-dojo-type="mui/commondialog/template/DialogList"
		data-dojo-mixins="mui/commondialog/template/DialogItemListMixin"
		data-dojo-props="isMul:{categroy.isMul},key:'{categroy.key}',dataUrl:'/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=list&isDialog=0',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',
			displayProp:'docSubject'" style="display: none;" id="topicSearchUl">
	</ul>
	
</div>

<div data-dojo-type="km/imeeting/mobile/resource/js/TopicSelection" 
	data-dojo-props="key:'{categroy.key}',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'" fixed="bottom">
</div>
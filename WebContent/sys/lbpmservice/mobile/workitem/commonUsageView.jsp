<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<!-- 添加审批语视图 -->
<div data-dojo-type="sys/lbpmservice/mobile/workitem/CommonUsageCreateView"
	data-dojo-mixins="sys/lbpmservice/mobile/workitem/CommonUsageViewMixin" 
	class="commonUsageView commonUsageCreateView" id="commonUsageCreateView" style="display: none">
	<xform:text htmlElementProperties="id='commonUsageId'" property="commUsageId" showStatus="noShow" mobile="true"></xform:text>
	<!-- 输入框 -->
	<div id='commonUsageContent' class='commonUsageContent' data-dojo-type="sys/lbpmservice/mobile/workitem/CommonusageTextarea" 
		data-dojo-props="placeholder:'${lfn:message('fsys-lbpmservice:mui.commonUsage.placeholder') }',name:'commonUsageContent'"></div>
	<!-- 按钮区 -->
	<ul id='commonUsageAddTabBar' data-dojo-type="mui/tabbar/TabBar" class="commonUsageAddTabBar">
		<li id='saveUsageButton' data-dojo-type="sys/lbpmservice/mobile/workitem/CommonUsageSaveButton" data-dojo-props='' class='mainTabBarButton'>
			<bean:message  key="button.submit" /> 
		</li>
	</ul>
</div>
<!-- 管理审批语视图 -->
<div data-dojo-type="sys/lbpmservice/mobile/workitem/CommonUsageManageView" 
	data-dojo-mixins="sys/lbpmservice/mobile/workitem/CommonUsageViewMixin" 
	class="commonUsageView commonUsageManageView" id="commonUsageManageView" style="display: none">
<ul id="commonUsageList" data-dojo-type='sys/lbpmservice/mobile/workitem/CommonUsageList'
 	data-dojo-mixins="sys/lbpmservice/mobile/workitem/CommonUsageItemListMixin"></ul>
<!-- 按钮区 -->
	<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiViewBottom">
		<li data-dojo-type="sys/lbpmservice/mobile/workitem/CommonUsageAddButton" data-dojo-props='' class='mainTabBarButton'>
			<bean:message bundle="sys-lbpmservice" key="mui.commonUsage.add"/>
		</li>
	</ul>
</div>

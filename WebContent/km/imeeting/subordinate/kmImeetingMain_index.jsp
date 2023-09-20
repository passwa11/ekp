<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.List"%>

<%-- 筛选器 --%>
<list:criteria id="imeetingCriteria${propertyItem.item}" channel="kmImeetingMain${propertyItem.item}">
	<list:cri-ref key="fdName" ref="criterion.sys.docSubject"> </list:cri-ref>
	<%-- 分类导航 --%>
	<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" title="${lfn:message('sys-category:menu.sysCategory.index') }" expand="false">
	  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"/>
	</list:cri-ref>
	<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingMain.docStatus') }" key="meetingStatus" channel="kmImeetingMain${propertyItem.item}">
		<list:box-select>
			<list:item-select id="status_for_myCreate${propertyItem.item}" cfg-enable="true" cfg-defaultValue ="${JsParam.defaultValue==null?30:''}">
				<ui:source type="Static">
					[{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.unHold') }', value:'unHold'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.holding') }', value:'holding'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish.hold') }', value:'hold'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.cancel') }', value:'41'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.draft') }', value:'10'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.append') }', value:'20'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.reject') }', value:'11'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.abandom') }', value:'00'}
					,{text:'${ lfn:message('km-imeeting:kmImeeting.status.publish') }', value:'30'}
					
					]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<%-- 主持人、会议发起人、组织部门、召开时间 --%>
	<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingMain" property="fdHoldDate" />
</list:criteria>

<%-- 操作栏 --%>
<div class="lui_list_operation">
	<!-- 分割线 -->
	<div class="lui_list_operation_line"></div>
	<!-- 排序 -->
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="kmImeetingMain${propertyItem.item}">
				<list:sortgroup>
					<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
					<list:sort property="docPublishTime" text="${lfn:message('km-imeeting:kmImeetingMain.docPublishTime') }" group="sort.list"></list:sort>
					<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDate') }" group="sort.list"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">	
		<list:paging layout="sys.ui.paging.top" channel="kmImeetingMain${propertyItem.item}"> 		
		</list:paging>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>

<%-- 列表视图 --%>
<list:listview id="imeetingListview${propertyItem.item}" channel="kmImeetingMain${propertyItem.item}">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&item=${propertyItem.item}&orgId=${JsParam.orgId}'}
	</ui:source>
	<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain"
		 isDefault="true" layout="sys.ui.listview.columntable" channel="kmImeetingMain${propertyItem.item}"
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.km.imeeting.model.KmImeetingMain&orgId=${JsParam.orgId}" name="columntable">
		<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
		<list:col-auto></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging channel="kmImeetingMain${propertyItem.item}"></list:paging>

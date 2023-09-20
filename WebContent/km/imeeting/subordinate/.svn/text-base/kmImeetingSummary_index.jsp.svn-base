<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
	seajs.use(['theme!list']);	
</script>
<%-- 筛选器 --%>
<list:criteria id="summaryCriteria" channel="kmImeetingSummary">
	<%-- 状态 --%>
	<list:tab-criterion title="" key="docStatus" channel="kmImeetingSummary">
		<list:box-select>
			<list:item-select cfg-enable="true" type="lui/criteria/select_panel!TabCriterionSelectDatas">
				<ui:source type="Static">
						[{text:'${ lfn:message('status.discard')}', value:'00'},
						{text:'${ lfn:message('status.draft')}', value:'10'},
						{text:'${ lfn:message('status.examine')}',value:'20'},
						{text:'${ lfn:message('status.refuse')}',value:'11'},
						{text:'${ lfn:message('status.publish')}',value:'30'}]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:tab-criterion>
	<list:cri-ref key="fdName" ref="criterion.sys.docSubject"> </list:cri-ref>
	<%-- 分类导航 --%>
	<list:cri-ref ref="criterion.sys.category" key="fdTemplate" multi="false" title="${lfn:message('sys-category:menu.sysCategory.index') }" expand="false">
	  <list:varParams modelName="com.landray.kmss.km.imeeting.model.KmImeetingTemplate"/>
	</list:cri-ref>
	<list:cri-auto modelName="com.landray.kmss.km.imeeting.model.KmImeetingSummary" property="docCreateTime" />
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
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6" channel="kmImeetingSummary">
				<list:sortgroup>
					<list:sort property="docCreateTime" text="${lfn:message('km-imeeting:kmImeetingSummary.docCreateTime') }" group="sort.list" value="down"></list:sort>
					<list:sort property="docPublishTime" text="${lfn:message('km-imeeting:kmImeetingSummary.docPublishTime') }" group="sort.list"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">	
		<list:paging layout="sys.ui.paging.top" channel="kmImeetingSummary">
		</list:paging>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<%-- 列表视图 --%>
<list:listview id="summaryListview" channel="kmImeetingSummary">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&orgId=${JsParam.orgId}'}
	</ui:source>
	<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary"
		isDefault="true" layout="sys.ui.listview.columntable" channel="kmImeetingSummary"
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary&orgId=${JsParam.orgId}" name="columntable">
		<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>
		<list:col-auto></list:col-auto>
	</list:colTable>
</list:listview>
<list:paging channel="kmImeetingSummary"></list:paging>

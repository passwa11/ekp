<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 筛选器 -->
<list:criteria id="bookCriteria" channel="kmImeetingBook">
	<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-imeeting:kmImeetingBook.fdName') }"></list:cri-ref>
	<list:cri-criterion title="${lfn:message('km-imeeting:table.kmImeetingBook') }" key="fdPlace" channel="kmImeetingBook">
		<list:box-title>
			<div style="line-height: 30px">${lfn:message('km-imeeting:table.kmImeetingBook') }</div>
			<div class="person">
				<list:item-search width="50px" height="22px">
					<ui:event event="search.changed" args="evt">
						var se = this.parent.parent.selectBox.criterionSelectElement;
						var source = se.source;
						if(evt.searchText){
							evt.searchText = encodeURIComponent(evt.searchText);
						}
						source.resolveUrl(evt);
						source.get();
					</ui:event>
				</list:item-search>
			</div>
		</list:box-title>
		<list:box-select style="min-height:60px">
			<list:item-select type="lui/criteria/select_panel!CriterionSelectDatas">
				<ui:source type="AjaxJson">
					{url: "/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=criteria&fdName=!{searchText}"}
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
	<!-- 占用时间筛选器 -->
	<list:cri-ref key="fdDate" ref="criterion.sys.calendar" title="${lfn:message('km-imeeting:kmImeetingResUse.fdDate') }">
	</list:cri-ref>
	<%-- 审批状态 --%>
	<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingBook.exam.status') }" key="status" multi="false" channel="kmImeetingBook">
		<list:box-select>
			<list:item-select>
				<ui:source type="Static">
					[{text:'${ lfn:message('km-imeeting:kmImeetingCalendar.res.wait') }', value:'wait'}
					,{text:'${ lfn:message('km-imeeting:kmImeetingBook.exam.status.yes') }', value:'yes'}
					,{text:'${ lfn:message('km-imeeting:kmImeetingBook.exam.status.no') }', value:'no'}
					]
				</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>
</list:criteria>
<!-- 操作栏 -->
<div class="lui_list_operation">
	<!-- 排序 -->
	<div style='color: #979797;float: left;padding-top:1px;'>
		${ lfn:message('list.orderType') }：
	</div>
	<div style="float:left">
		<div style="display: inline-block;vertical-align: middle;">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" channel="kmImeetingBook">
				<list:sortgroup>
					<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingBook.fdHoldDate') }" group="sort.list" value="down"></list:sort>
					<list:sort property="fdFinishDate" text="${lfn:message('km-imeeting:kmImeetingBook.fdFinishDate') }" group="sort.list"></list:sort>
				</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<div style="float:left;">	
		<list:paging layout="sys.ui.paging.top" channel="kmImeetingBook">
		</list:paging>
	</div>
</div>

<ui:fixed elem=".lui_list_operation"></ui:fixed>

<!-- 内容列表 -->
<list:listview id="bookListview" channel="kmImeetingBook">
	<ui:source type="AjaxJson">
		{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.imeeting.model.KmImeetingBook&orgId=${JsParam.orgId}'}
	</ui:source>
	<list:colTable isDefault="true" layout="sys.ui.listview.columntable" channel="kmImeetingBook"
		rowHref="/sys/subordinate/sysSubordinate.do?method=view&modelId=!{fdId}&modelName=com.landray.kmss.km.imeeting.model.KmImeetingBook&orgId=${JsParam.orgId}" name="columntable">	
		<list:col-serial></list:col-serial>
		<list:col-auto props=""></list:col-auto>
	</list:colTable>
</list:listview>
<!-- 分页 -->
<list:paging channel="kmImeetingBook"/>

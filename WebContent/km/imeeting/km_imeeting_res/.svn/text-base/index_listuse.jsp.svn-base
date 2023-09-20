<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="head">
		<style>
		 html,body{
		 	height: inherit;/*修复出现双滚动条#106178*/
		 }
		</style>
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" multi="false">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-imeeting:kmImeetingResUse.fdName') }"></list:cri-ref>
			<!-- 会议室筛选器 -->
			<list:cri-criterion title="会议室" key="fdPlace">
				<list:box-title>
					<div style="line-height: 30px">${lfn:message('km-imeeting:kmImeetingRes.fdPlace') }</div>
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
			<list:cri-criterion title="${lfn:message('km-imeeting:kmImeetingUse.btn.title') }" key="dateType">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('km-imeeting:kmImeetingUse.btn.thisweek') }', value:'thisweek'}
							,{text:'${ lfn:message('km-imeeting:kmImeetingUse.btn.nextweek') }', value:'nextweek'}
							,{text:'${ lfn:message('km-imeeting:kmImeetingUse.btn.thismonth') }', value:'thismonth'}
							]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="fdPlace" text="${lfn:message('km-imeeting:kmImeetingResUse.fdPlace') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingResUse.fdHoldDate') }" group="sort.list"></list:sort>
							<list:sort property="fdFinishDate" text="${lfn:message('km-imeeting:kmImeetingResUse.fdFinishDate') }" group="sort.list"></list:sort>
							<list:sort property="docStatus" text="${lfn:message('km-imeeting:kmImeetingResUse.docStatus') }" group="sort.list"></list:sort>
						</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;"></div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=listUse&contentType=json'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				onRowClick="window.openResUse('!{link}')" name="columntable">	
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdPlace,fdName,fdHoldDate,fdFinishDate,personName,docStatus"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				window.openResUse = function(link){
					if(link){
						Com_OpenWindow('${LUI_ContextPath}'+link);
					}else{
						dialog.result({
							status : true,
							title : '会议预约暂不支持查看！'
						});
					}
				};
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
			});
		</script>
	
	</template:replace>
</template:include>
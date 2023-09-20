<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria>
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-imeeting:kmImeetingBook.fdName') }"></list:cri-ref>
			<list:cri-criterion title="会议室" key="fdPlace">
				<list:box-title>
					<div style="line-height: 30px">会议室</div>
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
			<list:cri-criterion title="${ lfn:message('km-imeeting:kmImeetingBook.exam.status') }" key="status" multi="false">
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
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sortgroup>
							<list:sort property="fdPlace" text="${lfn:message('km-imeeting:kmImeetingBook.fdPlace') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdHoldDate" text="${lfn:message('km-imeeting:kmImeetingBook.fdHoldDate') }" group="sort.list"></list:sort>
							<list:sort property="fdFinishDate" text="${lfn:message('km-imeeting:kmImeetingBook.fdFinishDate') }" group="sort.list"></list:sort>
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
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="BtntoolBar">
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=deleteall"
							requestMethod="GET">
					    	<ui:button id="del" text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()"></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=view&fdId=!{fdId}" name="columntable">	
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				//删除
		 		window.delDoc = function(){
		 			var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),function(data){
								if(window.del_load!=null)
									window.del_load.hide();
								if(data!=null && data.status==true){
									topic.publish("list.refresh");
									dialog.success('<bean:message key="return.optSuccess" />');
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
						}
					});
				};
			});
		</script>
	
	</template:replace>
</template:include>
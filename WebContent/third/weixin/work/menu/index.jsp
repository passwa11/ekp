<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="content">
		<div style="padding: 20px;">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref style="width:145px;" key="fdAgentName" ref="criterion.sys.docSubject" title="${lfn:message('third-weixin-work:third.wx.menu.agentName') }"></list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="fdAgentId" text="${lfn:message('third-weixin-work:third.wx.menu.agentId') }" group="sort.list"></list:sort>
						<list:sort property="fdAgentName" text="${lfn:message('third-weixin-work:third.wx.menu.agentName') }" group="sort.list"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="6">
						<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
						 	<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							 <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/third/wxwork/menu/wxworkMenuDefine.do?method=data'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/third/wxwork/menu/wxworkMenuDefine.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdAgentId,fdAgentName,fdPublished"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
		</div>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('<c:url value="/third/wxwork/menu/wxworkMenuDefine.do" />?method=add');
				};
				//删除
				window.deleteAll = function(){
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
							$.post('<c:url value="/third/wxwork/menu/wxworkMenuDefine.do?method=deleteall"/>',
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

<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<div class="lui_list_noCreate_frame">
			<ui:combin ref="menu.nav.create">
				<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
				<ui:varParam name="button">
					[
						{
							"text": "${ lfn:message('sys-attend:module.sys.attend') }",
							"href": "javascript:void(0)",
							"icon": "lui_icon_l_icon_89"
						}
					]
				</ui:varParam>				
			</ui:combin>
		</div>
		<div class="lui_list_nav_frame">
			 <ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendMainExc"></c:param>
				   <c:param name="criteria" value="sysAttendExcCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('list.approval') }">
				<!-- 查询条件  -->
				<list:criteria id="sysAttendExcCriteria">
					<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendMainExc.docStatus.handleException') }" key="mydoc"> 
						<list:box-select>
							<list:item-select cfg-defaultValue="approval">
								<ui:source type="Static">
									[{text:"${ lfn:message('list.approval') }", value:'approval'},
									{text:"${ lfn:message('list.approved') }",value:'approved'},]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMainExc" 
						property="docCreator"  />
					<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMain" property="docCreateTime"/>
				</list:criteria>
				 
				<!-- 列表工具栏 -->
				<div class="lui_list_operation">
					<div style='color: #979797;float: left;padding-top:1px;'>
						${ lfn:message('list.orderType') }：
					</div>
					<div style="float:left">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							   <list:sort property="sysAttendMainExc.fdAttendMain.docCreateTime" text="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<div style="float:left;">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3">
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=list'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false"
						rowHref="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						 
						<list:col-auto props="fdAttendMain.docCreatorName;fdAttendMain.docCreateTime;fdAttendMain.fdLocation;fdAttendMain.categoryName;fdAttendMain.fdStatus;docStatus"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>
			</ui:content>
		</ui:tabpanel>
			 
	 	
	 	<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				window.setStatus = function(status){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					window._callback_loading = dialog.loading();
					$.post('<c:url value="/sys/attend/sys_attend_main_exc/sysAttendMainExc.do?method=setStatus"/>',
								$.param({"List_Selected":values ,"fdStatus":status},true),_callback,'json');
				};
				window._callback = function(data){
					if(window._callback_loading!=null)
						window._callback_loading.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};
			});
		</script>
	</template:replace>
</template:include>

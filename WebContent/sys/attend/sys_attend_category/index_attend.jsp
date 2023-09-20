<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<!-- 新建按钮 -->
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
			<ui:varParam name="button">
				<c:set var="_create_href" value="javascript:void(0)"></c:set>
				<kmss:authShow roles="ROLE_SYSATTEND_CREATE">
					<c:set var="_create_href" value="javascript:addDoc();"></c:set>
				</kmss:authShow>
				[
					{
						"text": "${ lfn:message('sys-attend:module.sys.attend') }",
						"href": "${_create_href }",
						"icon": "lui_icon_l_icon_89"
					}
				]
			</ui:varParam>				
		</ui:combin>
		<div class="lui_list_nav_frame">
			 <ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendCategory"></c:param>
				   <c:param name="criteria" value="cateAttendCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:sysAttendCategory.attend') }">
				<!-- 查询条件  -->
				<list:criteria id="cateAttendCriteria" expand="false">
					<list:cri-ref title="${lfn:message('sys-attend:sysAttendCategory.attend') }" key="docSubject" ref="criterion.sys.docSubject"/>
					<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendCategory.fdStatus') }" key="fdStatus"> 
						<list:box-select>
							<list:item-select cfg-defaultValue="">
								<ui:source type="Static">
									[
									{text:"${ lfn:message('sys-attend:sysAttendCategory.fdStatus.doing') }",value:'1'},
									{text:"${ lfn:message('sys-attend:sysAttendCategory.fdStatus.finish') }",value:'2'},
									{text:"${ lfn:message('sys-attend:sysAttendCategory.fdStatus.unStart') }",value:'0'}]
								</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>
					<list:cri-ref ref="criterion.sys.simpleCategory" key="fdATemplate" multi="false" title="${ lfn:message('sys-attend:table.sysAttendCategoryATemplate')}" expand="false">
					 	<list:varParams modelName="com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate"/>
					</list:cri-ref>
				</list:criteria>
				 
				<!-- 列表工具栏 -->
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
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							   <list:sort property="sysAttendCategory.docCreateTime" text="${ lfn:message('sys-attend:sysAttendCategory.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3">
								<kmss:authShow roles="ROLE_SYSATTEND_CREATE">
									<ui:button text="${ lfn:message('sys-attend:sysAttendCategory.newAttend') }" onclick="addDoc()" order="2"></ui:button>
								</kmss:authShow>
								<kmss:authShow roles="ROLE_SYSATTEND_CREATE">
									<ui:button text="${ lfn:message('sys-attend:sysAttendHisCategory.name') }" onclick="showHis()" order="3"></ui:button>
								</kmss:authShow>

								<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=deleteall&fdType=1">
									<ui:button text="${lfn:message('button.delete')}" onclick="delDoc()" order="4"></ui:button>
								</kmss:auth>			 
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_category/sysAttendCategory.do?method=list&type=attend'}
					</ui:source>
					<!-- 列表视图 -->	
					<list:colTable isDefault="false"
						rowHref="/sys/attend/sys_attend_category/sysAttendCategory.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-html style="width:0px;">{$ <input type="hidden" id="_{%row['fdId']%}" value="{%row['_fdStatus']%}"> $}</list:col-html>
						<list:col-html title="${lfn:message('sys-attend:sysAttendCategory.attend.fdName')}">{$ <span class="com_subject"> {%row['fdName']%} </span>$}</list:col-html>
						<list:col-auto props="fdStatus;docCreator.fdName;docCreateTime;"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>	
			</ui:content>
		</ui:tabpanel>
		 
	 	
	 	<script>
		seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});			
			
			window.addDoc = function(){
				dialog.simpleCategoryForNewFile(
						'com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate',
						'/sys/attend/sys_attend_category/sysAttendCategory.do?method=add&type=attend&fdATemplateId=!{id}');
			}
			window.showHis =function(){
				//历史考勤组的配置
				dialog.iframe('/sys/attend/sys_attend_his_category/index.jsp','<bean:message key="sys-attend:table.sysAttendHisCategory"/>',function(value) {
				}, {
					"width" : 1024,
					"height" : 680
				});
			}
			window.delDoc = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
				});
				
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				var doingArr = [];
				var excDoingArr = [];
				for(var i = 0;i < values.length;i++){
					var fdStatus = $('#_' + values[i]).val();
					if(fdStatus=='1'){
						doingArr.push(values[i]);
					} else {
						excDoingArr.push(values[i]);
					}
				}
				if(doingArr.length == values.length){
					dialog.alert("${ lfn:message('sys-attend:sysAttendCategory.doing.warn') }");
					return;
				}
				var warning = '<bean:message key="page.comfirmDelete"/>';
				if (doingArr.length > 0) {
					warning = '<bean:message bundle="sys-attend" key="sysAttendCategory.comfirmDelete"/>';
				}
				dialog.confirm(warning, function(value){
					if(value==true){
						var del_load = dialog.loading();
						$.post('${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=deleteall&fdType=1',$.param({"List_Selected":excDoingArr},true),function(data){
							if(del_load!=null){
								del_load.hide();
								topic.publish("list.refresh");
							}
							dialog.result(data);
						},'json');
					}
				});
			};
			window.switchAttendPage = function(url,hash){
				url = Com_SetUrlParameter(url,'j_iframe','true');
				url = Com_SetUrlParameter(url,'j_aside','false');
				if(hash){
					url = url + hash;
				}
				LUI.pageOpen(url,'_rIframe');
			}			
		});
		</script>
	</template:replace>
</template:include>

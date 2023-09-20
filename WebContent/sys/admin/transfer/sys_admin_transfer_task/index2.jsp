<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-admin-transfer:table.sysAdminTransferTask') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-criterion title="${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus')}" key="fdStatus">
				<list:box-select>
					<list:item-select  cfg-defaultValue="10">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus.todo')}', value:'10'},
							{text:'${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus.done')}', value:'20'},
							{text:'${ lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus.deleted')}', value:'9'}]
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5">
					<list:sortgroup>
						<list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdStatus" text="${lfn:message('sys-admin-transfer:sysAdminTransferTask.fdStatus') }" group="sort.list"></list:sort>
						<list:sort property="fdResult" text="${lfn:message('sys-admin-transfer:sysAdminTransferTask.fdResult') }" group="sort.list"></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
			</div>
			<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div id="transferButton" style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<!-- 处理 -->
						<ui:button text="${lfn:message('sys-admin-transfer:button.transfer')}" onclick="transferAll();"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/admin/transfer/sys_admin_transfer_task/sysAdminTransferTask.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/admin/transfer/sys_admin_transfer_task/sysAdminTransferTask.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdType,fdName,fdDescription,fdStatus,fdResult,docCreateTime,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
				initTabelCheckbox();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script>
		Com_IncludeFile("jquery.js");
		</script>
		<script type="text/javascript" src="<c:url value="/sys/admin/resource/js/ajaxSyncComponent.js"/>"></script>
	 	<script type="text/javascript">
		 	Lang = {
		 			todo: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.todo" />',
		 			done: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.done" />',
		 			waitting: '<bean:message bundle="sys-admin-transfer" key ="label.waitting" />',
		 			running: '<bean:message bundle="sys-admin-transfer" key ="label.running" />',
		 			notruned: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.not.runed" />',
		 			runed: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.runed" />',
		 			notread: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.not.read" />',
		 			read: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.read" />',	
		 			deleted: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdStatus.deleted" />',
		 			info: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdResult.info" />',
		 			warn: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdResult.warn" />',
		 			error: '<bean:message bundle="sys-admin-transfer" key ="sysAdminTransferTask.fdResult.error" />'
		 		};
		 	// 禁止链接
		 	function display(display) {
		 		var btns = document.getElementsByTagName("INPUT");
		 		for(var i = 0; i < btns.length; i++) {
		 			if(btns[i].type == "button" || btns[i].type == "image" || btns[i].type == "radio") {
		 				btns[i].style.display = display;
		 			}
		 		}
		 		btns = document.getElementsByTagName("A");
		 		for(var j = 0; j < btns.length; j++){
		 			btns[j].style.display = display;
		 		}
		 	}
		 	// 初始化表格多选框（已经处理的记录禁用多选框）
		 	function initTabelCheckbox() {
		 		var show=false;
		 		var hide=false;
		 		$("input[name='List_Selected']").each(function() {
					if($(this).parent().parent().children().last().text().length == 0){
						$(this).css('display','none'); 
						hide=true;
					} else {
						$(this).attr("checked", true);
						show=true;
					}
					if(hide==true && show==false){
						$("#transferButton").css("display", 'none');
						$("input[name='List_Tongle']").css("display", 'none');
					}else if(hide==false && show==true){
						$("#transferButton").css("display", "");
						$("input[name='List_Tongle']").css("display", "");
						$("input[name='List_Tongle']").attr("checked", true);
					}else if(hide==true && show==true){
						$("#transferButton").css("display", "");
						$("input[name='List_Tongle']").css("display", 'none');
					}
					
				});
			 }
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
		 		// 处理
		 		window.transferAll = function(id) {
			 		var temp = [];
			 		if(id) {
			 			temp.push(id);
			 		} else if ($("input[name='List_Selected']:checked").length > 0) {
			 			temp.push(1);
			 		}
			 		if(temp.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
				 		
		 			dialog.confirm('<bean:message key="sys-admin-transfer:page.comfirmTransferAll"/>', function(value) {
						if(value == true) {
							display("none");
				 			var url = '<c:url value="/sys/admin/resource/jsp/jsonp.jsp?s_bean=sysAdminTransferTaskService"/>';
				 			var component = ajaxSyncComponent(url); // ajax异步顺序执行
				 			if(id) {
				 				component.addData({fdId: id});
				 				$("input[value='" + id + "']").css('display','none'); 
				 				$("input[value='" + id + "']").attr("checked", false);
					 		} else {
					 			var obj = document.getElementsByName("List_Selected");
					 			for(var i = 0; i < obj.length; i++) {
					 				if(obj[i].checked && !(obj[i].style.display=='none')) {
					 					component.addData({fdId: obj[i].value});
					 					$("#status_"+obj[i].value).html("<b><font color='red'>"+Lang.waitting+"</font></b>");
					 					obj[i].style.display = "none";
					 					obj[i].checked = false;
					 				}
					 			}
					 		}
				 			component.beforeRequest = function(comp) {
				 				$("#status_"+comp.datas[comp.index].fdId).html("<b><font color='red'>"+Lang.running+"</font></b>");
				 			};
				 			component.afterResponse = function(json, comp) {
				 				if(json) {
				 					if(json.status=='0') { // 未执行
				 						$("#status_"+comp.datas[comp.index-1].fdId).html("<b><font color='black'>"+Lang.notruned+"</font></b>");
				 					} else if(json.status=='1') { // 已执行
				 						$("#status_"+comp.datas[comp.index-1].fdId).html("<b><font color='black'>"+Lang.runed+"</font></b>");
				 					} else if(json.status=='2') { // 待查阅
				 						$("#status_"+comp.datas[comp.index-1].fdId).html("<b><font color='black'>"+Lang.notread+"</font></b>");
				 					} else if(json.status=='3') { // 已查阅
				 						$("#status_"+comp.datas[comp.index-1].fdId).html("<b><font color='black'>"+Lang.read+"</font></b>");
				 					} else if(json.status=='9') { // 已删除
				 						$("#status_"+comp.datas[comp.index-1].fdId).html("<b><font color='black'>"+Lang.deleted+"</font></b>");
				 					}
				 					if(json.result == '0') { // 成功
				 						$("#result_"+comp.datas[comp.index-1].fdId).html("<b><font color='green'>"+Lang.info+"</font></b>");
				 					} else if(json.result == '1') { // 警告
				 						$("#result_"+comp.datas[comp.index-1].fdId).html("<b><font color='yellow'>"+Lang.warn+"</font></b>");
				 					} else if(json.result == '2') { // 错误
				 						$("#result_"+comp.datas[comp.index-1].fdId).html("<b><font color='red'>"+Lang.error+"</font></b>");
				 					}
				 				}
				 			};
				 			component.onComplate = function(comp) {
				 				display("");
				 			};
				 			component.traverse();
						}
					});
				};
				
		 	});
	 	</script>
	</template:replace>
</template:include>

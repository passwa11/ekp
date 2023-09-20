<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.list">
	<%--标签页标题--%>
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-sms:module.sys.sms') }"></c:out>
	</template:replace>
	<%--导航路径--%>
	<template:replace name="path">
			<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
				<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self">
				</ui:menu-item>
				<ui:menu-item text="${ lfn:message('sys-sms:module.sys.sms') }" href="/sys/sms/" target="_self">
				</ui:menu-item>
			</ui:menu>
	</template:replace>
	<%--左侧--%>
	<template:replace name="nav">
		<%--新建按钮--%>
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-sms:module.sys.sms') }" />
			<ui:varParam name="button">
				[
					<kmss:authShow roles="ROLE_SYSSMS_CREATE">
					{
						"text": "${ lfn:message('sys-sms:sysSmsMain.btn.add.sms') }",
						"href": "/sys/sms/sys_sms_main/sysSmsMain.do?method=add",
						"icon": "lui_icon_l_icon_14"
					}
					</kmss:authShow>
				]
			</ui:varParam>
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
					<%--状态筛选--%>
					<ui:content title="${ lfn:message('sys-sms:tree.status') }">
					<ul class='lui_list_nav_list'>
						<li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').setValue('fdFlag', '2');">${ lfn:message('sys-sms:tree.sucess')}</a></li>
						<li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').setValue('fdFlag', '3');">${ lfn:message('sys-sms:tree.fauile')}</a></li>
						<li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').setValue('fdFlag','1');">${lfn:message('sys-sms:tree.unsend')}</a></li>
					</ul>
				</ui:content>
				
				<kmss:authShow roles="ROLE_SYSSMS_BACKSTAGE_MANAGER">
					<%--后台管理--%>
					<ui:content title="${ lfn:message('list.otherOpt') }">
						<ul class='lui_list_nav_list'>
							<li><a href="${LUI_ContextPath }/sys/profile/index.jsp#notify/sms/" target="_blank">${ lfn:message('list.manager') }</a></li>
						</ul>
					</ui:content>
				</kmss:authShow>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<%--右侧--%>
	<template:replace name="content"> 
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdReceiverNumber" ref="criterion.sys.docSubject" title="${lfn:message('sys-sms:sysSmsMain.fdReceiverNumber') }"></list:cri-ref>
			<list:cri-ref ref="criterion.sys.person" key="receiver" multi="false" title="${ lfn:message('sys-sms:sysSmsMain.fdReceiver') }" />
			<list:cri-auto modelName="com.landray.kmss.sys.notify.model.SysNotifyShortMessageSend" property="fdCreateTime;fdFlag"/>
			<list:cri-criterion title="${lfn:message('sys-notify:sysNotifyTodo.moduleName') }" key="fdModelName" multi="true">
				<list:box-select>
					<list:item-select type="lui/criteria!CriterionSelectDatas"  id="moduleNames">
						<ui:source type="AjaxJson" >
							{url: "/sys/sms/sys_sms_main/sysSmsMain.do?method=getModules"}
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="5">
					<list:sortgroup>
						<list:sort property="fdCreateTime" text="${lfn:message('sys-sms:sysSmsMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="fdReceiver" text="${lfn:message('sys-sms:sysSmsMain.fdReceiver') }" group="sort.list"></list:sort>
						<list:sort property="fdReceiverNumber" text="${lfn:message('sys-sms:sysSmsMain.fdReceiverNumber') }" group="sort.list"></list:sort>
						<list:sort property="fdFlag" text="${lfn:message('sys-sms:sysSmsMain.docStatus') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar" count="4">
						<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall" requestMethod="GET">
								<ui:button text="${lfn:message('button.deleteall')}" onclick="del();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/sms/sys_sms_main/sysSmsMain.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/sms/sys_sms_main/sysSmsMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdReceiver,fdReceiverNumber,fdSender,fdCreateTime,fdFlag,operations"></list:col-auto>
			</list:colTable>
		
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	  <script type="text/javascript">
	 	Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				// 增加
		 		window.add = function() {
		 			Dialog_PopupWindow(Com_Parameter.ContextPath + 'resource/jsp/frame.jsp?url='+encodeURIComponent('<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do" />?method=add'),'650','600');
		 		};
		 		// 删除
		 		window.del = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/sms/sys_sms_main/sysSmsMain.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
		 	});
	 	</script>

	</template:replace>
	
</template:include>
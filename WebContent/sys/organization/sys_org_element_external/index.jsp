<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.org') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgElement.search1') }" style="width: 400px;"></list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-property:custom.field.status') }" key="isAvailable"> 
				<list:box-select>
					<list:item-select cfg-defaultValue="true">
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-property:custom.field.status.false') }',value:'false'},
							{text:'${ lfn:message('sys-property:custom.field.status.true') }',value:'true'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 组织选择 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				<%@ include file="/sys/organization/sys_org_element_external/sysOrgExternal_common_select.jsp"%>
					${ lfn:message('list.orderType') }：
			</div>
			<!-- 排序 -->
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdElement.fdOrder" text="${lfn:message('sys-organization:sysOrgOrg.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdElement.fdNamePinYin" text="${lfn:message('sys-organization:sysOrgOrg.fdName') }" group="sort.list"></list:sort>
						<list:sort property="fdElement.fdNo" text="${lfn:message('sys-organization:sysOrgOrg.fdNo') }" group="sort.list"></list:sort>
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
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=invalidatedAll" requestMethod="POST">
							<!-- 置为无效 -->
							<ui:button text="${lfn:message('sys-organization:organization.invalidated')}" onclick="invalidated()" order="3" ></ui:button>
							<!-- 置为有效 -->
							<ui:button text="${lfn:message('sys-organization:sysOrgElementExternal.enabled')}" onclick="changeElemStatus('true')" order="2" ></ui:button>
						</kmss:auth>
						
						<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=invalidatedAll" requestMethod="POST">
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgOrg"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
						<%-- 
						<kmss:auth requestURL="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=edit&fdId=${external.fdId}" requestMethod="GET">
							<!-- 批量修复扩展属性字段 -->
							<ui:button text="${lfn:message('sys-organization:sysOrgElementExternal.ext.prop.repairAll')}" onclick="repair()" order="5" ></ui:button>
						</kmss:auth>
						 --%>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview id="externalListLoad" cfg-needMinHeight="false">
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=list&parent=${JsParam.parent}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="view('!{fdId}', '!{auth}');">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
				topic.subscribe('list.loaded', function(event) {
					if(event.table && event.table.kvData) {
						for(var i in event.table.kvData) {
							var data = event.table.kvData[i];
							if('false' == data.auth) {
								// 删除多选按钮，移除可点击样式
								$(":checkbox[value='" + data.fdId + "']").remove();
								$("tr[kmss_fdid='" + data.fdId + "']").css("cursor", "");
							}
						}
					}
				});
				// 查看
		 		window.view = function(id, auth) {
					if('false' != auth)
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do" />?method=view&fdId=' + id);
		 		};
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do" />?method=add');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do" />?method=edit&fdId=' + id);
		 		};
		 		// 查看日志
		 		window.viewLog = function(id, fdName) {
		 			if(id) {
						var url = '<c:url value="/sys/organization/sys_log_organization/index.jsp" />?fdId=' + id;
						url = Com_SetUrlParameter(url, "fdName", encodeURI("${lfn:message('sys-organization:sysOrgElement.org')}-" + fdName));
		 				Com_OpenWindow(url);
			 		}
			 	};
			 	window.changeElemStatus = function(status, id) {
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
					
			 		var url  = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=changeElemStatus&fdId=' + id + '"/>';
			 		var messege = "您确定要将所选的记录设置为无效吗？";
			 		
			 		if (status && status == "true") {
			 			messege = "您确定要将所选的记录设置为有效吗？";
			 		}
			 		dialog.confirm(messege, function(value) {
			 			if(value == true) {
			 				window.del_load = dialog.loading();
			 				$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"ids" : values, "status" : status}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									if(data.responseJSON.message && data.responseJSON.message.length > 0)
										dialog.alert(data.responseJSON.message[0].msg);
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
			 	
		 		// 置为无效
		 		window.invalidated = function(id) {
		 			var values = [];
		 			var msg;
		 			var url;
		 			if(id) {
		 				values.push(id);
		 				msg = '<bean:message key="organization.invalidated.comfirm" bundle="sys-organization"/>';
		 				url  = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=invalidated"/>';
			 		} else {
			 			msg = '<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>';
			 			url  = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=invalidatedAll"/>';
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm(msg, function(value) {
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
									if(data.responseJSON.message && data.responseJSON.message.length > 0)
										dialog.alert(data.responseJSON.message[0].msg);
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
				// 修复扩展属性
		 		window.repair = function(id) {
		 			var values = [];
		 			var msg;
		 			if(id) {
		 				values.push(id);
		 				msg = '<bean:message key="sysOrgElementExternal.ext.prop.repair.comfirm" bundle="sys-organization"/>';
			 		} else {
			 			msg = '<bean:message key="sysOrgElementExternal.ext.prop.repairAll.comfirm" bundle="sys-organization"/>';
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=repair"/>';
					dialog.confirm(msg, function(value) {
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
									if(data.responseJSON.message && data.responseJSON.message.length > 0)
										dialog.alert(data.responseJSON.message[0].msg);
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
				// 快捷调换上级
				window.changeParent = function() {
					
					var values = "";
					var selected;
					var select = document.getElementsByName("List_Selected");
					var fdIds = "";
					for (var i = 0; i < select.length; i++) {
						if (select[i].checked) {
							selected = true;
							fdIds = fdIds+select[i].value +";";
							//break;
						}
					}
					
					fdIds = fdIds.substring(0,fdIds.length-1);
					
					dialog.iframe("/sys/organization/sys_org_org/sysOrgOrg.do?method=changeDeptEdit&orgType=ORG_TYPE_ORG&fdIds="+fdIds, 
							'<bean:message bundle="sys-organization" key="sysOrgDept.quickChangeDept"/>', null, {
							width:800,
							height:350,
							buttons:[{
									name : '<bean:message key="button.ok"/>',
									value : true,
									focus : true,
									fn : function(value, _dialog) {
										//解决列表选择数据后填充到iframe待选择框后fdIds会提示未被选择的问题
										var iframe = _dialog.element.find('iframe').get(0);
										var fdIds = iframe.contentWindow.document.getElementsByName("fdIds")[0].value;
										_dialog.fdIds = fdIds;
										
										if(!_dialog.fdIds || _dialog.fdIds == "") {
											dialog.alert('<bean:message bundle="sys-organization" key="sysOrgDept.quickChangeDept.from.null"/>');
											return false;
										}
										if(!_dialog.parentId || _dialog.parentId == "") {
											dialog.alert('<bean:message bundle="sys-organization" key="sysOrgDept.quickChangeDept.toDept.null"/>');
											return false;
										}
										
										$.ajax({
											url : '<c:url value="/sys/organization/sys_org_org/sysOrgOrg.do"/>?method=changeDept',
											type : 'POST',
											data : $.param({"orgIds":_dialog.fdIds,"parentId":_dialog.parentId}, true),
											dataType : 'json',
											error : function(data) {
												if(data.responseJSON.message && data.responseJSON.message.length > 0)
													dialog.alert(data.responseJSON.message[0].msg);
											},
											success: function(data) {
												if(data.status) {
													topic.publish("list.refresh");
													_dialog.hide();
													dialog.success('<bean:message key="return.optSuccess"/>');
												} else {
													dialog.failure(data.message);
												}
											}
									   });
									}
								}, {
									name : '<bean:message key="button.cancel"/>',
									styleClass:"lui_toolbar_btn_gray",
									value : false,
									fn : function(value, _dialog) {
										_dialog.hide();
									}
								}]
						});
				}
		 	});
	 	</script>
	</template:replace>
</template:include>

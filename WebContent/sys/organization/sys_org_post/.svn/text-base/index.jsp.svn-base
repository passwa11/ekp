<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	String url = request.getParameter("parent");
	pageContext.setAttribute("parent", url);
	url = "/sys/organization/sys_org_post/sysOrgPost.do?"+(url==null?"":"parent="+url+"&");
	pageContext.setAttribute("actionUrl", url);
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.post') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-ref key="fdSearchName" ref="criterion.sys.docSubject" title="${lfn:message('sys-organization:sysOrgElement.search1') }" style="width: 400px;"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.organization.model.SysOrgPost" property="fdIsBusiness" />
		</list:criteria>
		
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				<%@ include file="/sys/organization/org_common_select.jsp"%>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdOrder" text="${lfn:message('sys-organization:sysOrgPost.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-organization:sysOrgPost.fdName') }" group="sort.list"></list:sort>
						<list:sort property="fdNo" text="${lfn:message('sys-organization:sysOrgPost.fdNo') }" group="sort.list"></list:sort>
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
						<c:if test="${param.available != '0'}">
						<kmss:auth requestURL="${actionUrl}method=add">
							<!-- 增加 -->
							<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="${actionUrl}method=invalidatedAll" requestMethod="POST">
							<!-- 置为无效 -->
							<ui:button text="${lfn:message('sys-organization:organization.invalidated')}" onclick="invalidated()" order="3" ></ui:button>
							<c:if test="${param.fdFlagDeleted != null}">
								<!-- 所有置为无效 -->
								<ui:button text="${lfn:message('sys-organization:organization.invalidatedAll')}" onclick="invalidatedAll()" order="3" ></ui:button>
							</c:if>
							<!-- 快捷调换部门 -->
							<ui:button text="${lfn:message('sys-organization:sysOrgPerson.quickChangeDept')}" onclick="changeDept()" order="4" ></ui:button>
						</kmss:auth>
						</c:if>
						<kmss:auth requestURL="/sys/organization/sys_org_group/sysOrgGroup.do?method=invalidatedAll" requestMethod="POST">
							<!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.organization.model.SysOrgPost"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview cfg-needMinHeight="false">
			<ui:source type="AjaxJson">
				{url:'/sys/organization/sys_org_post/sysOrgPost.do?method=list&fdIsExternal=false&available=${JsParam.available}&all=${JsParam.all}&parent=${JsParam.parent}&fdFlagDeleted=${JsParam.fdFlagDeleted}&fdImportInfo=${JsParam.fdImportInfo}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/organization/sys_org_post/sysOrgPost.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdParent,fdNo,fdName,fdThisLeader,fdPersons,operations"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
			Com_IncludeFile("dialog.js");
			window.openDialogTree =function(id){
				// 选择外部组织
				Dialog_Tree(false, null, null, null, "sysOrgElementExternalService&type=cate&parent=!{value}", "${ lfn:message('sys-organization:sysOrgEco.name') }", null, function(result) {
					if(result && result.data && result.data.length > 0) {
						window.doAction(id,result.data[0].id);
					}
				}, null, null, null, "${ lfn:message('sys-organization:sysOrgElementExternal.selectOrg') }");
			};
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/organization/sys_org_post/sysOrgPost.do" />?method=add&parent=${JsParam.parent}');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id){
		 				Com_OpenWindow('<c:url value="/sys/organization/sys_org_post/sysOrgPost.do" />?method=edit&fdId=' + id);
					}
		 		};		 		// 查看日志
		 		window.viewLog = function(id, fdName) {
		 			if(id) {
						var url = '<c:url value="/sys/organization/sys_log_organization/index.jsp" />?fdId=' + id;
						url = Com_SetUrlParameter(url, "fdName", encodeURI("${lfn:message('sys-organization:sysOrgElement.post')}-" + fdName));
		 				Com_OpenWindow(url);
			 		}
			 	};
		 		// 置为无效
		 		window.invalidated = function(id) {
		 			var confirmStr = '<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>';
		 			var values = [];
		 			if(id) {
		 				values.push(id);
		 				confirmStr += '<br><bean:message key="sys.org.available.handover.post.info" bundle="sys-organization"/>'
 							+ '<br><a href="<c:url value="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=add&type=auth"/>&fdFromId=' + id + '" target="_blank"><bean:message key="sys.org.available.handover.info1" bundle="sys-organization"/></a>'
 							+ '<br><a href="<c:url value="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=add&type=doc"/>&fdFromId=' + id + '" target="_blank"><bean:message key="sys.org.available.handover.info2" bundle="sys-organization"/></a>'
 							+ '<br><a href="<c:url value="/sys/handover/sys_handover_config_main/sysHandoverConfigMain.do?method=add&type=config"/>&fdFromId=' + id + '" target="_blank"><bean:message key="sys.org.available.handover.info3" bundle="sys-organization"/></a>';
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/organization/sys_org_post/sysOrgPost.do?method=invalidatedAll&parent=${parent}"/>';
					dialog.confirm(confirmStr, function(value) {
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
									dialog.result(data);
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
				// 所有置为无效
		 		window.invalidatedAll = function() {
					dialog.confirm('<bean:message key="organization.invalidatedAll.comfirm" bundle="sys-organization"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : '<c:url value="/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=invalidatedAllOmsDeleted"/>',
								type : 'POST',
								data : $.param({"providerKey" : "${JsParam.fdImportInfo}"}, true),
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
				window.doAction =function(id,parentId){
					var url  = '<c:url value="/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=transformOut"/>';
					window.del_load = dialog.loading();
					$.ajax({
						url : url,
						type : 'POST',
						data : {'fdId':id,'parentId':parentId},
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
				};
				//转外部岗位
				window.transformOut = function (id, fdName){
					dialog.confirm(fdName+'<bean:message bundle="sys-organization" key="sys.org.operations.toOutPost.tip"/>', function(value){
						if(value == true) {
							window.openDialogTree(id);
						}
					});
				}
				// 快捷调换部门
				window.changeDept = function() {

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
					
					dialog.iframe("/sys/organization/sys_org_post/sysOrgPost_chgDept.jsp?orgType=ORG_TYPE_POST", 
							'<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept"/>', null, {
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
											dialog.alert('<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.fromPerson.null"/>');
											return false;
										}
										if(!_dialog.deptId || _dialog.deptId == "") {
											dialog.alert('<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.toDept.null"/>');
											return false;
										}
										
										$.ajax({
											url : '<c:url value="/sys/organization/sys_org_post/sysOrgPost.do"/>?method=changeDept',
											type : 'POST',
											data : $.param({"postIds":_dialog.fdIds,"deptId":_dialog.deptId}, true),
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

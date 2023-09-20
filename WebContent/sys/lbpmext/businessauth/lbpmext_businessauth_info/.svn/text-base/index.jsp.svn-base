<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-lbpmservice-support:table.lbpmEmbeddedSubFlow') }</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
		 	<!-- 请输入授权编码 -->
			<list:cri-ref key="fdNumber" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.code') }">
			</list:cri-ref>
			<list:cri-ref key="fdAuthorizedPerson" ref="criterion.sys.person" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPerson') }">
			</list:cri-ref>
			<list:cri-ref key="fdAuthorizedPost" ref="criterion.sys.postperson" title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdAuthorizedPost') }">
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status')}" key="docStatus"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status.draft')}', value:'10'},
							{text:'${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status.valid')}', value:'20'},
							{text:'${ lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status.unvalid')}',value:'30'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<!-- 新增时间筛选维度开始 -->
			<list:cri-ref ref="criterion.sys.calendar" key="fdStartTime"
				title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdStartTime') }" />
			<list:cri-ref ref="criterion.sys.calendar" key="fdEndTime"
				title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdEndTime') }" />
			<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
				title="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdCreateTime') }" />
			<!-- 新增时间筛选维度结束 -->
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
						    <list:sort property="fdStartTime" text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdStartTime') }" group="sort.list"></list:sort>
							<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.fdCreateTime') }" group="sort.list"  value="down"></list:sort>
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
					<ui:toolbar>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=add&category=${JsParam.category}">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="2"  ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=add&category=${JsParam.category}">
							<ui:button text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status.valid')}" onclick="onValidChange('20');" order="2"  ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=add&category=${JsParam.category}">
							<ui:button text="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthInfo.status.unvalid')}" onclick="onValidChange('30');" order="2"  ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=deleteall">
							<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc()"></ui:button>
						</kmss:auth> 
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=list&category=${JsParam.category}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript" charset="UTF-8">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			window.add = function(){
				var fdMainModelName = '';
				Com_OpenWindow('<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do" />?method=add&category=${JsParam.category}');
			};
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish("list.refresh");
			});
			// 编辑
	 		window.edit = function(id) {
		 		if(id){
		 			var fdMainModelName = '';
		 			Com_OpenWindow('<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do" />?method=edit&fdId=' + id);
		 		}
	 				
	 		};
	 		
	 		window.onStatusCheck = function(values){
				if (!values) {
					return false;
				}
				var msg = '';
				var url  = '<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=checkUpdateStatus"/>';
				var skipIds = [];
				$.ajax({
					async : false, 
					url :url,
					type : 'post',
					data : {ids:values.join(";")},
					dataType : 'json',
					error : function(data) {
						dialog.result(data.responseJSON);
						msg = "error";
					},
					success: function(data) {
						if(data.length > 0){
							var alertIndex = 1;
							
							msg += '<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.docUpdateWarning"/></br>';
							msg+= '<table><tr><th></th>' + '<th class="width60">' + '<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.docUpdateWarning.head1"/>' + '</th>' + '<th class="width60">' + '<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.docUpdateWarning.head2"/>' + '</th>' + '<th class="width120">' + '<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.docUpdateWarning.head3"/>' + '</th>' + '<th class="width120">' + '<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.docUpdateWarning.head4"/>' + '</th></tr>';
							for(var i = 0; i < data.length; i++) {
								var rowdata = data[i];
								console.log(rowdata);
								if(rowdata.docStatus == '30') {
									msg+= '<tr><td>' + (alertIndex++) + '.</td>' + '<td class="width60">'+rowdata.fdAuthorizedPersonName + '</td>' + '<td class="width60">'+rowdata.fdAuthorizedPostName + '</td>' +'<td class="width120">' + rowdata.fdStartTime + '</td>' + '</td>' +'<td class="width120">' + rowdata.fdEndTime + '</td></tr>';
								} else {
									skipIds.push(rowdata.fdId)
								}
								for (var ii = 0; ii < values.length; ii++) {
									if(values[ii] == rowdata.fdId) {
										values.splice(ii, 1);
										break;
									}
								}
							}
							msg+= '</table>';
							// 勾选的所有数据都为有效数据情况
							if(skipIds.length == data.length){
								msg = '';
							}
						}
					}
				});
				console.log(skipIds);
				if(msg == '') {
					return false;
				}
				return msg;
			};
			
	 		window.onValidChange = function(value){
	 			var values = [];
	 			$("input[name='List_Selected']:checked").each(function() {
					values.push($(this).val());
				});
	 			
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				};
				
				if (value == "20" && msg!='error') {
					var msg = onStatusCheck(values);	
					if(msg) {
						dialog.confirm(msg, function(flag) {
							if(flag){
								ajaxUpdateState(value, values)
							}
						})
					} else if(msg=='error') {
						return;
					}else {
						ajaxUpdateState(value, values)
					} 
					
				} else {
					ajaxUpdateState(value, values);
				}
	 		};
	 		
	 		window.ajaxUpdateState = function(_state, _ids) {
	 			if(_ids.length==0){
					return;
				}
				var url  = '<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method=updateStatus"/>';
				window.update_load = dialog.loading();
				$.ajax({
					async : false,
					url :url,
					type : 'post',
					data : {status:_state,ids:_ids.join(";")},
					dataType : 'json',
					error : function(data) {
						if(window.update_load != null) {
							window.update_load.hide(); 
						}
						dialog.result(data.responseJSON);
					},
					success: function(data) {
						if(window.update_load != null){
							window.update_load.hide(); 
							topic.publish("list.refresh");
						}
						dialog.result(data);
					}
			   });
			};
	 		
		 	//删除
	 		window.delDoc = function(id){
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
				var url  = '<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do?method="/>';
				if(id){
					url+='delete';
				}else{
					url+='deleteall';
				}
				var data = id?$.param({'fdId' : values}, true):$.param({'List_Selected' : values}, true);
				var type = id?'GET':'POST';
				dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
					if(value == true) {
						window.del_load = dialog.loading();
						$.ajax({
							url : url,
							type : type,
							data : data,
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
			
			// 复制
			window.clone = function(id){
				if(id){
					Com_OpenWindow('<c:url value="/sys/lbpmext/businessauth/lbpmBusinessAuthInfo.do" />?method=clone&cloneModelId=' + id);
				}
			};
	 	});
		</script>
		<br>
	 	<list:paging></list:paging>
	</template:replace>
</template:include>
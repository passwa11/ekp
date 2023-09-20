<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<style>
			.conf_btn_edit .btn_txt {
				margin: 0px 2px;
				color:#2574ad;
				border-bottom:1px solid transparent;
			}
			.conf_btn_edit .btn_txt:hover{
				color:#123a6b;
				border-bottom-color:#123a6b; 
			}
		</style>
		<div style="margin: 0 auto; width: 95%">
			<list:listview cfg-needMinHeight="false">
				<ui:source type="AjaxJson">
					{url:'/sys/filestore/sys_filestore/sysFileConvertClient.do?method=data'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="goClientQueueList('!{fdId}')">
					<list:col-serial></list:col-serial>
					<list:col-auto
						props="operations,fdConverterType,version,identity,clientPort,converterFullKey,taskConvertingNum,converterVersion"></list:col-auto>
				</list:colTable>
			</list:listview>
			<list:paging></list:paging>
			<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
			<script src="${ LUI_ContextPath }/sys/filestore/resource/listAutoResizeCommon.js?s_cache=${LUI_Cache}"></script>
			<script type="text/javascript">
				seajs.use([ 'theme!list', 'theme!portal' ]);
				getData(); // 先获取数据看下多少条记录
				startAutoResize();
				seajs.use('lui/topic', function(topic) {
					topic.subscribe("list.changed", function(rtnData) {
						domain.call(parent, "filestoreClientEvent", [ {
							type : "event",
							name : "removeClientQueueInfo"
						} ]);
					}, null);
				});
				
				function goClientQueueList(clientId) {
					domain.call(parent, "filestoreClientEvent", [ {
						type : "event",
						name : "loadClientQueueInfo",
						clientId : clientId
					} ]);
				}
				
				function reboot(clientId){
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						var confirmHintInfo='<bean:message key="convertClient.comfirmReboot" bundle="sys-filestore"/>';
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertClient.do?method=operations"/>',$.param({"cmd":"reboot","clientId":clientId},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
							);
					});
				}
				
				function close(clientId){
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						var confirmHintInfo='<bean:message key="convertClient.comfirmClose" bundle="sys-filestore"/>';
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertClient.do?method=operations"/>',$.param({"cmd":"close","clientId":clientId},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
							);
					});
				}

				function able(clientId){
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						var confirmHintInfo='<bean:message key="convertClient.comfirmAble" bundle="sys-filestore"/>';
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertClient.do?method=operations"/>',$.param({"cmd":"able","clientId":clientId},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
							);
					});
				}
				
				function config(clientId){
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						var confirmHintInfo='<bean:message key="convertClient.comfirmConfig" bundle="sys-filestore"/>';
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										Com_OpenWindow("${LUI_ContextPath}/sys/filestore/sys_filestore/sysFileConvertClient.do?method=config&fdId="+clientId,"_blank");
									}
									parent.dialoging=false;
								}
							);
					});
				}
				
				function disable(clientId){
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						var confirmHintInfo='<bean:message key="convertClient.comfirmDisable" bundle="sys-filestore"/>';
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertClient.do?method=operations"/>',$.param({"cmd":"disable","clientId":clientId},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
							);
					});
				}
				
				var dialogCallBack =function(data){
					seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
						if (window.innerLoading != null)
							window.innerLoading.hide();
						if (data != null && data.status == true) {
							topic.publish("list.refresh");
							dialog.success('<bean:message key="return.optSuccess" />');
						} else {
							dialog.failure('<bean:message key="return.optFailure" />');
						}}
					);
				};

				function getData() {
					seajs.use(['lui/jquery','lui/dialog'], function($, dialog ) {
						$.ajax({
							url : "${LUI_ContextPath }/sys/filestore/sys_filestore/sysFileConvertClient.do?method=data",
							data : {
							},
							type : 'get',
							dataType: 'json',
							async: false,
							success: function(data) {
                                var dataSum = data.datas.length;
								if(data.datas.length > 0){
									parent.clientSize = dataSum;

								} else {
									parent.clientSize = -999;
								}

							},
							error : function() {
								parent.clientSize = -999;
							}
						});
					});
				}
			</script>
		</div>
	</template:replace>
</template:include>
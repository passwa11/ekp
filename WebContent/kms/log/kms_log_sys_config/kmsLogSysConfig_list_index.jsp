<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('kms-log:kmsLogApp.search.org') }</template:replace>
	<template:replace name="content">
	<style type="text/css">
		input[type="checkbox"][name="List_Tongle"] {display: none;}
	</style>
		<script type="text/javascript">
			//打开新页面显示详细内容
			function openDetailInfo(id) {
				window.open(Com_Parameter.ContextPath + 'kms/log/kms_log_sys_config/kmsLogSysConfig.do?method=view&fdId=' + id,'_blank');
			}
			
			//打开新页面显示详细内容
			function openCompareInfo(id) {
				window.open(Com_Parameter.ContextPath + 'kms/log/kms_log_sys_config/kmsLogSysConfig.do?method=compare&fdIdArr=' + id,'_blank');
			}
			
			
			//比较两条日志记录
			function diffLog(){
				var selCnt = $("input[name='List_Selected']:checked").length;
				if (selCnt != 2) {
					seajs.use(['lui/dialog'], function(dialog) {
						dialog.alert('<bean:message bundle="kms-log" key="kmsLogSysConfig.select"/>');
					});
					return false;
				}
				var fdKeyArr = [];
				$("input[type='checkbox'][name='List_Selected']:checked").each(function(i) {
					fdKeyArr[i] = $(this).val();
				});
				
			    var idArr = fdKeyArr[0] + ";" + fdKeyArr[1];
			    openCompareInfo(idArr);
				
			}
		</script>
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-criterion title="${ lfn:message('kms-log:kmsLogSysConfig.fdModuleName')}" key="fdModuleKey" >
				<list:box-select>
					<list:item-select >
						<ui:source type="Static">
							[{text:'${ lfn:message('kms-integral:module.kms.integral')}', value:'kmsIntegral'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
		
			<list:cri-auto modelName="com.landray.kmss.kms.log.model.KmsLogSysConfig"
			property="fdCreateTime" />
			<list:cri-ref key="fdCreator" ref="criterion.sys.person"  multi="false"
				title="${lfn:message('kms-log:kmsLogAppHistory.fdOperator') }">
			</list:cri-ref>
		</list:criteria>
		
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 刷新按钮 -->
			<div  class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" ></list:paging>
            </div> 
			
			<!-- 操作按钮 -->
 			<div class="lui_list_operation_toolbar">
				<div class="lui_table_toolbar_inner">
					<ui:toolbar id="Btntoolbar" >
						<ui:button text="${lfn:message('kms-log:kmsLogSysConfig.button.compare')}" onclick="diffLog();"></ui:button> 
			 		</ui:toolbar>
			 	</div>
			 </div>
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview >
			<ui:source type="AjaxJson">
				{url:'/kms/log/kms_log_sys_config/kmsLogSysConfig.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/kms/log/kms_log_sys_config/kmsLogSysConfig.do?method=view&fdId=!{fdId}&isBak=true" >
				<list:col-checkbox ></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdCreateTime,fdCreatorName,fdOprateMethod,fdModuleName"></list:col-auto>
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
		 	});
	 	</script>
	</template:replace>
</template:include>

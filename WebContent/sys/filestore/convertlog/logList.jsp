<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<div style="margin: 0 auto; width: 95%">
			<div class="lui_list_operation">
				<table width="100%">
					<tr>
						<td align="right"><ui:toolbar count="2" id="btnToolBar">
								<kmss:authShow roles="SYSROLE_ADMIN">
									<ui:button
										text="${lfn:message('button.deleteall')}"
										onclick="delLogs()" order="1"></ui:button>
								</kmss:authShow>
							</ui:toolbar></td>
					</tr>
				</table>
			</div>
			<list:listview cfg-needMinHeight="false">
				<ui:source type="AjaxJson">
				{url:'/sys/filestore/sys_filestore/sysFileConvertLog.do?method=data&queueId=${param.queueId }'}
			</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" onRowClick="viewStatusInfo('!{fdConvertStatus}','!{fdId}')">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto
						props="fdQueueId,fdConvertKey,statusMessageInfo,fdStatusTime,fdStatusInfo"></list:col-auto>
				</list:colTable>
			</list:listview>
			<list:paging></list:paging>
			<script
				src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
			<script
				src="${ LUI_ContextPath }/sys/filestore/resource/listAutoResizeCommon.js?s_cache=${LUI_Cache}"></script>
			<script type="text/javascript">
				seajs.use([ 'theme!list', 'theme!portal' ]);
				startAutoResize();
				<kmss:authShow roles="SYSROLE_ADMIN">
				var delLogs=function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){values.push($(this).val());});
					var confirmHintInfo='<bean:message key="convertLog.comfirmDelete.selection" bundle="sys-filestore"/>';
					var delType="selection";
					if(values.length == 0){
						confirmHintInfo="<bean:message key='convertLog.comfirmDelete.all' bundle='sys-filestore'/>";
						delType = "all";
					}
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertLog.do?method=delLogs&delType='+delType+'"/>',$.param({"selected":values,"queueId":"${param.queueId}"},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
						);}
					);
				};
				</kmss:authShow>
				
				var dialogCallBack = function(data) {
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
				
				var viewStatusInfo=function(status,logId){
					if(status=='3'||status=='5'||status=='6'||status=='9'||status=='99'||status=='999'){
						seajs.use('lui/dialog',function(dialog){
							dialog.iframe('/sys/filestore/convertlog/result.jsp?logId='+logId,"${lfn:message('sys-filestore:convertlog.statusinfo')}",null,{width:600,height:400});
						});
					}
				};
			</script>
		</div>
	</template:replace>
</template:include>
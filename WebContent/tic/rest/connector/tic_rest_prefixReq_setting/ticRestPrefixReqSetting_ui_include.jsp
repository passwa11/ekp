<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/tic/core/tic_ui_list.jsp">
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<%-- 筛选器 --%>
		<list:criteria>
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject">
			</list:cri-ref>
			
		</list:criteria>
		
		<%-- 显示列表按钮行 --%>
		<div class="lui_list_operation">
			<table width="100%">
				<tr>
					<td align="right">
						<ui:toolbar>
							<kmss:auth requestURL="/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=deleteall">
								<ui:button text="${lfn:message('button.deleteall')}" order="4" onclick="delDoc('${LUI_ContextPath}/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=deleteall')"></ui:button>
							</kmss:auth>
						</ui:toolbar>						
					</td>
				</tr>
			</table>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=list&fdAppType=${JsParam.fdAppType}&fdEnviromentId=${JsParam.fdEnviromentId}'}
			</ui:source>
			<%--列表形式--%>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props=""></list:col-auto>
			</list:colTable>   
		</list:listview> 
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) {
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
						topic.publish('list.refresh');
				}, 100);
			});
			window.setPrefixReqSetting = function(fdId){
				window.$dialog.hide(fdId);
			};
			window.editPrefixReqSetting = function(fdId){
				window.open(Com_Parameter.ContextPath+"tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=edit&fdId="+fdId);
			};
		 	//删除
	 		window.delDoc = function(url){
	 			var values = [];
				$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
				if(values.length==0){
					dialog.alert('<bean:message key="page.noSelect"/>');
					return;
				}
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
					if(value==true){
						window.del_load = dialog.loading();
						$.post('<c:url value="'+ url +'"/>',
								$.param({"List_Selected":values},true),delCallback,'json');
					}
				});
			};
			window.delCallback = function(data){
				if(window.del_load!=null) {
					window.del_load.hide();
				}
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success('<bean:message key="return.optSuccess" />');
				}else{
					dialog.failure('<bean:message key="return.optFailure" />');
				}
			};
	 	});
		</script>
		<br>
	 	<list:paging></list:paging>
	</template:replace>
</template:include>
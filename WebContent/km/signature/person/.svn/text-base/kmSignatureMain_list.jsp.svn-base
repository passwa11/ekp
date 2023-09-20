<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.cfg">
	<template:replace name="title">
		<template:super/> - ${lfn:message('km-signature:tree.myDoc') }
	</template:replace>
	<template:replace name="content">
	<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${lfn:message('km-signature:tree.myDoc')}">
				<div style="width:100%;overflow: hidden;">
				<ui:toolbar style="float:right;">
				    <%-- 视图选择 
						<ui:togglegroup order="0" >
							<ui:toggle icon="lui_icon_s_liebiao" title="${ lfn:message('list.columnTable') }" 
								value="columntable"	group="tg_1" text="${ lfn:message('list.columnTable') }" 
								onclick="LUI('listview').switchType(this.value);">
							</ui:toggle>
							<ui:toggle icon="lui_icon_s_tuwen"
								title="${lfn:message('list.gridTable') }" group="tg_1"
								value="gridtable" text="${lfn:message('list.gridTable') }"
								onclick="LUI('listview').switchType(this.value);">
							</ui:toggle>
						</ui:togglegroup>
					--%>
					<%-- 新建 --%>
					<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=add">
						<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>	
					</kmss:auth>
					<%-- 删除 --%>		
					<kmss:auth requestURL="/km/signature/km_signature_main/kmSignatureMain.do?method=deleteall">
						<ui:button text="${lfn:message('button.deleteall')}" onclick="delDoc()" order="2"></ui:button>
					</kmss:auth>
				</ui:toolbar>
				</div>
				<list:listview id="listview">
					<%--列表视图--%>
					<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
						rowHref="/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=!{fdId}"  name="columntable">
						<ui:source type="AjaxJson" >
							{url:'/km/signature/km_signature_main/kmSignatureMain.do?method=list&mydoc=authorize&forward=listUi'}
						</ui:source>
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-html title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left">
						{$ <span class="com_subject" >{%row['fdMarkName']%}</span> $}
						</list:col-html>
						<list:col-auto props="fdMarkDate;fdDocType" ></list:col-auto>
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>	
			</ui:content>
	</ui:tabpanel>
		<script type="text/javascript">
		    seajs.use(['theme!module']);
	 		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.signature.model.KmSignatureMain";
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar'], function($, dialog , topic,toolbar) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

				//新建
				window.addDoc = function() {
					Com_OpenWindow('<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=add&docType=${JsParam.docType}" />');
				};
				
				//删除
				window.delDoc = function(){
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
							$.post('<c:url value="/km/signature/km_signature_main/kmSignatureMain.do?method=deleteall"/>&categoryId=${JsParam.categoryId}',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
				//删除回调函数
				window.delCallback = function(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};

			});
		</script>
	</template:replace>
</template:include>
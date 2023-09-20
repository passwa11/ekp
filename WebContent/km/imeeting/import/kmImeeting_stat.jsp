<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<template:include  ref="default.simple" spa="true">
	<template:replace name="body">
	<div style="width:100%">
		 <div class="lui_list_operation">
		<table width="100%">
			<tr>
				<td align="right">
					<ui:toolbar count="3" id="listToolbar"> 
						<kmss:authShow roles="ROLE_KMIMEETING_STAT_MAINTAINER">
							<ui:button text="${lfn:message('button.add')}" 
								onclick="addDoc('/km/imeeting/km_imeeting_stat/kmImeetingStat.do?method=add&fdType=${JsParam.stat_key}');">
							</ui:button>
							<ui:button text="${lfn:message('button.deleteall')}" 
								onclick="delDoc('/km/imeeting/km_imeeting_stat/kmImeetingStat.do?method=deleteall');">
							</ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</td>
			</tr>
		</table>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_stat/kmImeetingStat.do?method=list&fdType=${JsParam.stat_key}&model=com.landray.kmss.km.imeeting.model.KmImeetingStat'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="!{fdUrl}" name="columntable">
				<list:col-checkbox name="List_Selected" headerStyle="width:5%"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}" headerStyle="width:5%"></list:col-serial>
				<list:col-html  title="${ lfn:message('km-imeeting:kmImeetingStat.fdName') }" style="text-align:left">
				 {$ <span class="com_subject" >{%row['fdName']%}</span> $}
				</list:col-html>
				<list:col-auto props="docCreator.fdName;docCreateTime"></list:col-auto>
			</list:colTable>
		</list:listview>
	 	<list:paging></list:paging>
		<script type="text/javascript">
		var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
		
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/data/source','lui/menu','lui/toolbar'], 
				function($, dialog , topic, source , menu , toolbar) {

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.addDoc = function(addUrl) {
					Com_OpenWindow('${LUI_ContextPath}' + addUrl,'_blank');	
				};
				
				//删除
				window.delDoc = function(delUrl){
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
							var del_load = dialog.loading();
							$.post('${LUI_ContextPath}' + delUrl,
									$.param({"List_Selected":values},true),function(data){
										if(del_load!=null)
											del_load.hide();
										if(data!=null && data.status==true){
											topic.publish("list.refresh");
											dialog.success('<bean:message key="return.optSuccess" />');
										}else{
											dialog.failure('<bean:message key="return.optFailure" />');
										}
									},'json');
						}
					});
				};
			});
		</script>
	  <c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
		<c:if test="${frameShowTop=='yes' }">
			<ui:top id="top"></ui:top>
			<kmss:ifModuleExist path="/sys/help">
				<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
			</kmss:ifModuleExist>
		</c:if>
 </div> 
	</template:replace>
</template:include>

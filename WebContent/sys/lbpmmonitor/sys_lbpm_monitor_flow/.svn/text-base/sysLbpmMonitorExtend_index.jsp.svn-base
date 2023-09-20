<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title"></template:replace>
	<template:replace name="content">
		<div id="tbody-view" style="padding: 10px">
			<table style="width: 100%">
				<tr>
					<td valign="top">
								<list:criteria channel="channel_common" id="criteria1" expand="false" multi="false">
								<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"/>
									<%--分类导航--%>
									<c:if test="${not empty param.modelName }">
										<%
											String modelName = request.getParameter("modelName");
											String templateModelName = SysConfigs.getInstance().getFlowDefByMain(modelName).getTemplateModelName();
											Class clazz = Class.forName(templateModelName);
											boolean isSimpleCategory = ISysSimpleCategoryModel.class.isAssignableFrom(clazz);
											pageContext.setAttribute("templateModelName", templateModelName);
											pageContext.setAttribute("isSimpleCategory", isSimpleCategory);
										%>
										<c:if test="${isSimpleCategory}">
											<list:cri-ref ref="criterion.sys.simpleCategory"
												key="simpleCategory" multi="false"
												title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
												expand="true" channel="channel_common">
												<list:varParams modelName="${templateModelName}" />
											</list:cri-ref>
										</c:if>
										<c:if test="${!isSimpleCategory}">
											<list:cri-ref ref="criterion.sys.category" key="docCategory"
												multi="false"
												title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
												expand="true" channel="channel_common">
												<list:varParams modelName="${templateModelName}" />
											</list:cri-ref>
										</c:if>
									</c:if>
									<%--当前处理人--%>
									<list:cri-ref ref="criterion.sys.postperson.availableAll"
										key="fdCurrentHandler" multi="false"
										title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" />
									<%--创建者--%>
									<list:cri-ref ref="criterion.sys.person" key="fdCreator"
										multi="false"
										title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }" />
									<%--创建时间--%>
									<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
										title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
									<%--模块--%>
									<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.order.module')}" expand="true" key="fdModelName" multi="false"> 
										<list:box-select>
											<list:item-select cfg-required="true" cfg-defaultValue="com.landray.kmss.km.review.model.KmReviewMain">
												<ui:source type="Static">
													<% 
													List<Map.Entry<String, String>> moduleList = com.landray.kmss.sys.lbpmmonitor.service.spring.SysLbpmMonitorFlowServiceImp.getDocStatusModuleMap();
													JSONArray jsonArr = new JSONArray();
													for (Map.Entry<String, String> entry : moduleList) {
														JSONObject jsonObj = new JSONObject();
														jsonObj.put("text", entry.getValue());
														jsonObj.put("value", entry.getKey());
														jsonArr.add(jsonObj);
													}
													out.print(jsonArr.toString()); 
													%>
												</ui:source>
											</list:item-select>
										</list:box-select>
									</list:cri-criterion>
									<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.statusErrorType')}" expand="true" key="statusErrorType" multi="false">
										<list:box-select>
											<list:item-select cfg-defaultValue="flow" cfg-required="true">
												<ui:source type="Static">
												    [{text:'${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.statusError')}', value:'flow'},
													 {text:'${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docStatusError')}', value:'doc'}]
												</ui:source>
											</list:item-select>
										</list:box-select>
									</list:cri-criterion>
								</list:criteria>
								<!-- 排序 -->
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
											<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6">
												<c:if test="${param.method=='getStatusError'}">
													<list:sort property="fdCreateTime"
														text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
														group="sort.list" value="down"></list:sort>
												</c:if>
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
											<c:if test="${param.method=='getStatusError'}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="privilBtn"
														text="${lfn:message('sys-lbpmmonitor:button.correctStatus')}"
														onclick="batchCorrectStatus('${group.fdUrl}')" order="1"></ui:button>
												</ui:toolbar>
											</c:if>
										</div>
									</div>
								</div>
								<ui:fixed elem=".lui_list_operation"></ui:fixed>
								<list:listview id="listview">
									<ui:source type="AjaxJson">
										{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=${JsParam.method}&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}'}
									</ui:source>
									<list:colTable isDefault="false"
										layout="sys.ui.listview.columntable"
										rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId=!{fdId}&fdModelName=!{fdModelName}"
										name="columntable">
										<list:col-checkbox></list:col-checkbox>
										<list:col-serial></list:col-serial>
										<list:col-auto props=""></list:col-auto>
									</list:colTable>
								</list:listview>
								<list:paging></list:paging>
								<script>
								//修改异常状态
								function batchCorrectStatus(fdUrl) {
									var values = [];
									$("input[name='List_Selected']:checked").each(function() {
										values.push($(this).val());
									});
									if(values.length==0){
										seajs.use([ 'lui/dialog' ], function(dialog) {
											dialog.alert("${lfn:message('page.noSelect')}");
										});
										return;
									}
									
									var url = '/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=correctStatus';
									if (fdUrl && fdUrl!="") {
										url = fdUrl + url;
									} else {
										url = '<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=correctStatus"/>';
									}
									
									if(LUI("criteria1").criteriaValues){
										var criteriaValues = LUI("criteria1").criteriaValues;
										for (var i=0; i<criteriaValues.length; i++) {
											if("statusErrorType"==criteriaValues[i].key && criteriaValues[i].values.length != 0){
												url = url + '&statusErrorType=' + criteriaValues[i]["values"][0].value;
											} else if ("fdModelName"==criteriaValues[i].key && criteriaValues[i].values.length != 0) {
												url = url + '&fdModelName=' + criteriaValues[i]["values"][0].value;
											}
										}
									}
									
									seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
										dialog.confirm("${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.correctStatus.confirm')}",function(value){
											if(value==true){
												window.del_load = dialog.loading();
												$.ajax({
													url: url,
													type: 'POST',
													data:$.param({"List_Selected":values},true),
													dataType: 'json',
													error: function(data){
														if(window.del_load!=null){
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
									});
								}
							 	
								domain.autoResize();
								seajs.use(['lui/topic'], function(topic) {
									// 监听新建更新等成功后刷新
									topic.subscribe('successReloadPage', function() {
										window.setTimeout(function() {
											topic.publish('list.refresh');
										}, 2000);
									});
									//筛选器变化		
									topic.channel("channel_common").subscribe("criteria.changed", function(evt) {
										
										   if(evt.criterions){
											   var keyArray=new Array();
											   for(var i=0; i<evt.criterions.length;i++){
												   keyArray[i] = evt.criterions[i].key;
												}
											   if(keyArray.contains("docSubject")){
													if(!keyArray.contains("fdModelName")){
														//alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.selectModuleFirst"/>');												
														//return;
													}
												}
											  }
										   topic.publish("criteria.changed", evt);
									});
								});
								Array.prototype.contains = function (arr){
									for(var i=0;i<this.length;i++){
										if(this[i] == arr){
											return true;
										}
									}
									return false;
								};
								</script>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>
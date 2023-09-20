<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_flow/css/common.css"/>"/>
	</template:replace>
	<template:replace name="title"></template:replace>
	<template:replace name="content">
		<div id="tbody-view" style="padding: 10px">
			
			<table style="width: 100%">
				<tr>
					<td valign="top">
								<list:criteria channel="channel_interfaceLog" expand="false" multi="false" >
											
										<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"/>
										
										<%--创建时间--%>
										<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
											title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
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
												expand="true" channel="channel_interfaceLog">
												<list:varParams modelName="${templateModelName}" />
											</list:cri-ref>
										</c:if>
										<c:if test="${!isSimpleCategory}">
											<list:cri-ref ref="criterion.sys.category" key="docCategory"
												multi="false"
												title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
												expand="true" channel="channel_interfaceLog">
												<list:varParams modelName="${templateModelName}" />
											</list:cri-ref>
										</c:if>
									</c:if>
									<c:if test="${empty param.modelName}">
									<%--模块--%>
									<list:cri-criterion title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.order.module')}" key="fdModelName" multi="false"> 
										<list:box-select>
											<list:item-select>
												<ui:source type="AjaxJson">
													{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getModule'}
												</ui:source>
											</list:item-select>
										</list:box-select>
									</list:cri-criterion>
									</c:if>
									<%--对接系统--%>
										<list:cri-criterion
										        title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.interfacelog.transferSystem') }"
										        key="transferSystem" expand="true" multi="false" >
										        <list:box-select>
										          <list:item-select cfg-required="false" cfg-defaultValue ="">
										            <ui:source type="Static" >
										              [
										                {text:'SAP',value:'1'},
										                {text:'K3',value:'2'},
										                {text:'EAS',value:'3'},
										                {text:'U8',value:'4'},
										                {text:'业务集成',value:'5'}
										              ]
										            </ui:source>
										          </list:item-select>
										        </list:box-select>
										      </list:cri-criterion>
										      
									<%--执行情况--%>
										<list:cri-criterion
										        title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.interfacelog.transferResult') }"
										        key="transferResult" expand="true" multi="false" >
										        <list:box-select>
										          <list:item-select cfg-required="false" cfg-defaultValue ="1">
										            <ui:source type="Static" >
										              [
										                {text:'${lfn:message("sys-lbpmmonitor:sysLbpmMonitor.interface.sucess")}',value:'0'},
										                {text:'${lfn:message("sys-lbpmmonitor:sysLbpmMonitor.interface.error")}',value:'1'},
										                {text:'${lfn:message("sys-lbpmmonitor:sysLbpmMonitor.interface.busError")}',value:'2'}
										              ]
										            </ui:source>
										          </list:item-select>
										        </list:box-select>
										      </list:cri-criterion>
										           
								</list:criteria>
								<!-- 排序 -->
								<div class="lui_list_operation">
								
								
										<!-- 全选
										<div class="lui_list_operation_order_btn">
											<list:selectall></list:selectall>
										</div> -->
										
										<!-- 分割线 -->
										<div class="lui_list_operation_line"></div>

									
									
									<!-- 排序 -->
									<div class="lui_list_operation_sort_btn">
										<div class="lui_list_operation_order_text">
											${ lfn:message('list.orderType') }:
										</div>
										<div class="lui_list_operation_sort_toolbar">
											<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
												<list:sortgroup >
														<list:sort channel="channel_interfaceLog" property="fdCreateTime"
															text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}" group="sort.list" value="down"></list:sort>
												</list:sortgroup>
											</ui:toolbar>
										</div>
									</div>
									
									
									<!-- 分页 -->
									<div class="lui_list_operation_page_top">	
										<list:paging layout="sys.ui.paging.top" > 		
										</list:paging>
									</div>
									
									<!-- 操作按钮 
									<div style="float:right">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="privilBtn"
														text="重试"
														onclick="batchModifyHandler('')" order="2"></ui:button>
												</ui:toolbar>
										</div>
									</div>-->
									
								</div>
								<c:if test="${not empty param.modelName}">
									<c:set var="paramModelName" value="&q.fdModelName=${param.modelName}" ></c:set>
								</c:if>
								<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>
								<list:listview channel="channel_interfaceLog">
									<ui:source type="AjaxJson">
										{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/interfacelogAction.do?method=queryInterfaceLogInfo${not empty paramModelName ? paramModelName : ""}'}
									</ui:source>
									<list:colTable isDefault="true"
										layout="sys.ui.listview.columntable"
										rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/interfacelogAction.do?method=view&fdId=!{fdId}">
										<list:col-serial></list:col-serial>
										<list:col-auto props=""></list:col-auto>
									</list:colTable>
									<ui:event topic="list.loaded"></ui:event>
								</list:listview>
								<list:paging channel="channel_interfaceLog"></list:paging>
								<script>
								
								//解决多子系统页面显示不全问题
								domain.autoResize();
								
									seajs
											.use(
													[ 'lui/topic' ],
													function(topic) {
														//筛选器变化	
														topic
																.channel("channel_interfaceLog")
																.subscribe(
																		"criteria.spa.changed",
																		function(evt) {
																			if (evt.criterions) {
																				var keyArray = new Array();
																				for (var i = 0; i < evt.criterions.length; i++) {
																					keyArray[i] = evt.criterions[i].key;
																				}
																				var flag = '${empty param.modelName }';
																				 /*if (keyArray
																						.contains("docSubject")) {
																					if (!keyArray.contains("fdModelName")&& flag==="true") {
																						alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.selectModuleFirst"/>');
																						return;
																					}
																				} */
																				
																			}
																		});
													});
									Array.prototype.contains = function(arr) {
										for (var i = 0; i < this.length; i++) {
											if (this[i] == arr) {
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
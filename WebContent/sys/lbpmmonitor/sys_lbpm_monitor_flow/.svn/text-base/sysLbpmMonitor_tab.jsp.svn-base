<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs,com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel"%>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<ui:tabpanel>
	<ui:content title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.tree.expiredFlow.child01') }" id="limit">
		<table style="width: 100%">
			<tr>
				<td valign="top">
					<list:criteria channel="channel_limit" id="criteria1" expand="false" multi="false">
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
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
							<c:if test="${!isSimpleCategory}">
								<list:cri-ref ref="criterion.sys.category" key="docCategory"
									multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
						</c:if>
						<c:if test="${param.method!='getProcessRestart'}">
							<c:if test="${param.fdType!='finish'}">
								<%--当前处理人--%>
								<list:cri-ref ref="criterion.sys.postperson.availableAll"
									key="fdCurrentHandler" multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" />
							</c:if>
							<%--创建者--%>
							<list:cri-ref ref="criterion.sys.person" key="fdCreator"
								multi="false"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }" />
							<%--创建时间--%>
							<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
						</c:if>
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
					</list:criteria>
					<!-- 排序 -->
					<div class="lui_list_operation">
						<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<!-- 全选 -->
							<div class="lui_list_operation_order_btn">
								<list:selectall channel="channel_limit"></list:selectall>
							</div>
							<!-- 分割线 -->
							<div class="lui_list_operation_line"></div>
						</c:if>
						<!-- 排序 -->
						<div class="lui_list_operation_sort_btn">
							<div class="lui_list_operation_order_text">
								${ lfn:message('list.orderType') }：
							</div>
							<div class="lui_list_operation_sort_toolbar">
								<ui:toolbar channel="channel_limit" layout="sys.ui.toolbar.sort" style="float:left" count="6">
									<list:sortgroup>
										<c:if test="${param.fdType!='finish' && param.method!='getProcessRestart'}">
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list" value="down"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='finish'}">
											<list:sort property="fdEndedTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.endTime')}"
												group="sort.list" value="down"></list:sort>
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType!='error' && param.method!='getProcessRestart'}">
											<list:sort property="fdStatus"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.status')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='error' || param.method=='getInvalidHandler'}">
											<list:sort property="fdCurrentHandler"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.currentHandler.short')}"
												group="sort.list"></list:sort>
										</c:if>
									</list:sortgroup>
								</ui:toolbar>
							</div>
						</div>
						<!-- 分页 -->
						<div class="lui_list_operation_page_top">	
							<list:paging layout="sys.ui.paging.top" channel="channel_limit"> 		
							</list:paging>
						</div>
						<!-- 操作按钮 -->
						<div style="float:right">
							<div style="display: inline-block;vertical-align: middle;">
								<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired'}">
									<ui:toolbar id="Btntoolbar1" channel="channel_limit">
										<ui:button id="batchModifyHandler1"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyHandler.short')}"
											onclick="batchModifyHandler('${group.fdUrl}','limit')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired' || 
								param.method=='getPause'}">
									<ui:toolbar id="Btntoolbar1" channel="channel_limit">
										<ui:button id="batchModifyPrivileger1"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger.short')}"
											onclick="batchModifyPrivileger('${group.fdUrl}','limit')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<!-- #54516 流程重启 2018年5月8日 -->
								<c:if test="${param.fdType=='finish'}">
									<%
										LbpmSetting lbpmSetting = new LbpmSetting();
										String isProcessRestart=lbpmSetting.getIsProcessRestart();
										pageContext.setAttribute("isProcessRestart", isProcessRestart);
									%>
									<c:if test="${isProcessRestart=='true'}">
										<ui:toolbar id="Btntoolbar1" channel="channel_limit">
											<ui:button id="restart1"
												text="${lfn:message('sys-lbpmmonitor:button.process.restart')}"
												onclick="restartProcess()" order="1"></ui:button>
										</ui:toolbar>
									</c:if> 
								</c:if>   
								<c:if test="${(param.fdType != null && param.fdType!='' && param.fdType!='finish') || (param.method!='listChildren' && param.method!='getRecentHandle' && param.method!='getProcessRestart' && param.method!='getPause')}">
									<ui:toolbar id="Btntoolbar1" channel="channel_limit">
										<ui:button id="batchPrivi1"
											text="${lfn:message('sys-lbpmmonitor:button.batchPrivi.short')}"
											onclick="batchPrivil('getLimitExpired','limit')" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.method=='getExpired' || param.method=='getLimitExpired'}">
									<ui:toolbar id="Btntoolbar1" channel="channel_limit">
										<ui:button id="onekeyPress1"
											text="${lfn:message('sys-lbpmmonitor:button.onekeyPress')}"
											onclick="onekeyPress(null,'getLimitExpired','limit')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.fdType=='error'}">
									<ui:toolbar id="Btntoolbar1" channel="channel_limit">
										<ui:button id="handleUnavailableProcess1"
											text="${lfn:message('sys-lbpmmonitor:button.handleUnavailableProcess')}"
											onclick="handleUnavailableProcess()" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
							</div>
						</div>
					</div>
					<ui:fixed elem=".lui_list_operation"></ui:fixed>
					<list:listview id="listview1" channel="channel_limit">
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getLimitExpired&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}'}
						</ui:source>
						<list:colTable isDefault="false"
							layout="sys.ui.listview.columntable"
							rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId=!{fdId}&fdModelName=!{fdModelName}"
							name="columntable">
							<!-- #54516 流程重启 2018年5月8日 -->
							<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<list:col-checkbox></list:col-checkbox>
							</c:if>
							<list:col-serial></list:col-serial>
							<list:col-auto props=""></list:col-auto>
						</list:colTable>
					</list:listview>
					<list:paging channel="channel_limit"/>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/topic'], function(topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					window.setTimeout(function() {
						topic.channel("channel_limit").publish('list.refresh');
					}, 2000);
				});
				//筛选器变化		
				topic.channel("channel_limit").subscribe("criteria.changed", function(evt) {
					
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
		</script>
	</ui:content>
	<ui:content title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.tree.expiredFlow.child02') }" id="press">
		<table style="width: 100%">
			<tr>
				<td valign="top">
					<list:criteria channel="channel_press" id="criteria2" expand="false" multi="false">
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
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
							<c:if test="${!isSimpleCategory}">
								<list:cri-ref ref="criterion.sys.category" key="docCategory"
									multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
						</c:if>
						<c:if test="${param.method!='getProcessRestart'}">
							<c:if test="${param.fdType!='finish'}">
								<%--当前处理人--%>
								<list:cri-ref ref="criterion.sys.postperson.availableAll"
									key="fdCurrentHandler" multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" />
							</c:if>
							<%--创建者--%>
							<list:cri-ref ref="criterion.sys.person" key="fdCreator"
								multi="false"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }" />
							<%--创建时间--%>
							<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
						</c:if>
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
					</list:criteria>
					<!-- 排序 -->
					<div class="lui_list_operation">
						<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<!-- 全选 -->
							<div class="lui_list_operation_order_btn">
								<list:selectall channel="channel_press"></list:selectall>
							</div>
							<!-- 分割线 -->
							<div class="lui_list_operation_line"></div>
						</c:if>
						<!-- 排序 -->
						<div class="lui_list_operation_sort_btn">
							<div class="lui_list_operation_order_text">
								${ lfn:message('list.orderType') }：
							</div>
							<div class="lui_list_operation_sort_toolbar">
								<ui:toolbar channel="channel_press" layout="sys.ui.toolbar.sort" style="float:left" count="6">
									<list:sortgroup>
										<c:if test="${param.fdType!='finish' && param.method!='getProcessRestart'}">
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list" value="down"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='finish'}">
											<list:sort property="fdEndedTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.endTime')}"
												group="sort.list" value="down"></list:sort>
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType!='error' && param.method!='getProcessRestart'}">
											<list:sort property="fdStatus"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.status')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='error' || param.method=='getInvalidHandler'}">
											<list:sort property="fdCurrentHandler"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.currentHandler.short')}"
												group="sort.list"></list:sort>
										</c:if>
									</list:sortgroup>
								</ui:toolbar>
							</div>
						</div>
						<!-- 分页 -->
						<div class="lui_list_operation_page_top">	
							<list:paging layout="sys.ui.paging.top" channel="channel_press"> 		
							</list:paging>
						</div>
						<!-- 操作按钮 -->
						<div style="float:right">
							<div style="display: inline-block;vertical-align: middle;">
								<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired'}">
									<ui:toolbar id="Btntoolbar2" channel="channel_press">
										<ui:button id="batchModifyHandler2"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyHandler.short')}"
											onclick="batchModifyHandler('${group.fdUrl}','press')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired' || 
								param.method=='getPause'}">
									<ui:toolbar id="Btntoolbar2" channel="channel_press">
										<ui:button id="batchModifyPrivileger2"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger.short')}"
											onclick="batchModifyPrivileger('${group.fdUrl}','press')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<!-- #54516 流程重启 2018年5月8日 -->
								<c:if test="${param.fdType=='finish'}">
									<%
										LbpmSetting lbpmSetting = new LbpmSetting();
										String isProcessRestart=lbpmSetting.getIsProcessRestart();
										pageContext.setAttribute("isProcessRestart", isProcessRestart);
									%>
									<c:if test="${isProcessRestart=='true'}">
										<ui:toolbar id="Btntoolbar2" channel="channel_press">
											<ui:button id="restart2"
												text="${lfn:message('sys-lbpmmonitor:button.process.restart')}"
												onclick="restartProcess()" order="1"></ui:button>
										</ui:toolbar>
									</c:if> 
								</c:if>   
								<c:if test="${(param.fdType != null && param.fdType!='' && param.fdType!='finish') || (param.method!='listChildren' && param.method!='getRecentHandle' && param.method!='getProcessRestart' && param.method!='getPause')}">
									<ui:toolbar id="Btntoolbar2" channel="channel_press">
										<ui:button id="batchPrivi2"
											text="${lfn:message('sys-lbpmmonitor:button.batchPrivi.short')}"
											onclick="batchPrivil('getExpired','press')" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.method=='getExpired' || param.method=='getLimitExpired'}">
									<ui:toolbar id="Btntoolbar2" channel="channel_press">
										<ui:button id="onekeyPress2"
											text="${lfn:message('sys-lbpmmonitor:button.onekeyPress')}"
											onclick="onekeyPress(null,'getExpired','press')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.fdType=='error'}">
									<ui:toolbar id="Btntoolbar2" channel="channel_press">
										<ui:button id="handleUnavailableProcess2"
											text="${lfn:message('sys-lbpmmonitor:button.handleUnavailableProcess')}"
											onclick="handleUnavailableProcess()" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
							</div>
						</div>
					</div>
					<ui:fixed elem=".lui_list_operation"></ui:fixed>
					<list:listview id="listview2" channel="channel_press">
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getExpired&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}'}
						</ui:source>
						<list:colTable isDefault="false"
							layout="sys.ui.listview.columntable"
							rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId=!{fdId}&fdModelName=!{fdModelName}"
							name="columntable">
							<!-- #54516 流程重启 2018年5月8日 -->
							<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<list:col-checkbox></list:col-checkbox>
							</c:if>
							<list:col-serial></list:col-serial>
							<list:col-auto props=""></list:col-auto>
						</list:colTable>
					</list:listview>
					<list:paging channel="channel_press"/>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/topic'], function(topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					window.setTimeout(function() {
						topic.channel("channel_press").publish('list.refresh');
					}, 2000);
				});
				//筛选器变化		
				topic.channel("channel_press").subscribe("criteria.changed", function(evt) {
					
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
		</script>
	</ui:content>
	<ui:content title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.tree.expiredFlow.child03') }" id="draft">
		<table style="width: 100%">
			<tr>
				<td valign="top">
					<list:criteria channel="channel_draft" id="criteria3" expand="false" multi="false">
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
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
							<c:if test="${!isSimpleCategory}">
								<list:cri-ref ref="criterion.sys.category" key="docCategory"
									multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
									expand="true">
									<list:varParams modelName="${templateModelName}" />
								</list:cri-ref>
							</c:if>
						</c:if>
						<c:if test="${param.method!='getProcessRestart'}">
							<c:if test="${param.fdType!='finish'}">
								<%--当前处理人--%>
								<list:cri-ref ref="criterion.sys.postperson.availableAll"
									key="fdCurrentHandler" multi="false"
									title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.currentHandler') }" />
							</c:if>
							<%--创建者--%>
							<list:cri-ref ref="criterion.sys.person" key="fdCreator"
								multi="false"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthor') }" />
							<%--创建时间--%>
							<list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
								title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.docAuthorTime') }" />
						</c:if>
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
					</list:criteria>
					<!-- 排序 -->
					<div class="lui_list_operation">
						<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<!-- 全选 -->
							<div class="lui_list_operation_order_btn">
								<list:selectall channel="channel_draft"></list:selectall>
							</div>
							<!-- 分割线 -->
							<div class="lui_list_operation_line"></div>
						</c:if>
						<!-- 排序 -->
						<div class="lui_list_operation_sort_btn">
							<div class="lui_list_operation_order_text">
								${ lfn:message('list.orderType') }：
							</div>
							<div class="lui_list_operation_sort_toolbar">
								<ui:toolbar channel="channel_draft" layout="sys.ui.toolbar.sort" style="float:left" count="6">
									<list:sortgroup>
										<c:if test="${param.fdType!='finish' && param.method!='getProcessRestart'}">
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list" value="down"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='finish'}">
											<list:sort property="fdEndedTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.endTime')}"
												group="sort.list" value="down"></list:sort>
											<list:sort property="fdCreateTime"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.creatorTime')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType!='error' && param.method!='getProcessRestart'}">
											<list:sort property="fdStatus"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.status')}"
												group="sort.list"></list:sort>
										</c:if>
										<c:if test="${param.fdType=='error' || param.method=='getInvalidHandler'}">
											<list:sort property="fdCurrentHandler"
												text="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.currentHandler.short')}"
												group="sort.list"></list:sort>
										</c:if>
									</list:sortgroup>
								</ui:toolbar>
							</div>
						</div>
						<!-- 分页 -->
						<div class="lui_list_operation_page_top">	
							<list:paging layout="sys.ui.paging.top" channel="channel_draft"> 		
							</list:paging>
						</div>
						<!-- 操作按钮 -->
						<div style="float:right">
							<div style="display: inline-block;vertical-align: middle;">
								<%-- <c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired'}">
									<ui:toolbar id="Btntoolbar3" channel="channel_draft">
										<ui:button id="privilBtn3"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyHandler.short')}"
											onclick="batchModifyHandler('${group.fdUrl}','draft')" order="1"></ui:button>
									</ui:toolbar>
								</c:if> --%>
								<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired' || 
								param.method=='getPause'}">
									<ui:toolbar id="Btntoolbar3" channel="channel_draft">
										<ui:button id="batchModifyPrivileger3"
											text="${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger.short')}"
											onclick="batchModifyPrivileger('${group.fdUrl}','draft')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<!-- #54516 流程重启 2018年5月8日 -->
								<c:if test="${param.fdType=='finish'}">
									<%
										LbpmSetting lbpmSetting = new LbpmSetting();
										String isProcessRestart=lbpmSetting.getIsProcessRestart();
										pageContext.setAttribute("isProcessRestart", isProcessRestart);
									%>
									<c:if test="${isProcessRestart=='true'}">
										<ui:toolbar id="Btntoolbar3" channel="channel_draft">
											<ui:button id="restart3"
												text="${lfn:message('sys-lbpmmonitor:button.process.restart')}"
												onclick="restartProcess()" order="1"></ui:button>
										</ui:toolbar>
									</c:if> 
								</c:if>   
								<c:if test="${(param.fdType != null && param.fdType!='' && param.fdType!='finish') || (param.method!='listChildren' && param.method!='getRecentHandle' && param.method!='getProcessRestart' && param.method!='getPause')}">
									<ui:toolbar id="Btntoolbar3" channel="channel_draft">
										<ui:button id="batchPrivi3"
											text="${lfn:message('sys-lbpmmonitor:button.batchPrivi.short')}"
											onclick="batchPrivil('getDraftExpired','draft')" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.method=='getExpired' || param.method=='getLimitExpired' || param.method=='getDraftExpired'}">
									<ui:toolbar id="Btntoolbar3" channel="channel_draft">
										<ui:button id="onekeyPress3"
											text="${lfn:message('sys-lbpmmonitor:button.onekeyPress')}"
											onclick="onekeyPress(null,'getDraftExpired','draft')" order="1"></ui:button>
									</ui:toolbar>
								</c:if>
								<c:if test="${param.fdType=='error'}">
									<ui:toolbar id="Btntoolbar3" channel="channel_draft">
										<ui:button id="handleUnavailableProcess3"
											text="${lfn:message('sys-lbpmmonitor:button.handleUnavailableProcess')}"
											onclick="handleUnavailableProcess()" order="2"></ui:button>
									</ui:toolbar>
								</c:if>
							</div>
						</div>
					</div>
					<ui:fixed elem=".lui_list_operation"></ui:fixed>
					<list:listview id="listview3" channel="channel_draft">
						<ui:source type="AjaxJson">
							{url:'/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getDraftExpired&modelName=${JsParam.modelName }&fdStatus=${JsParam.fdStatus}&fdType=${JsParam.fdType}'}
						</ui:source>
						<list:colTable isDefault="false"
							layout="sys.ui.listview.columntable"
							rowHref="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=view&fdId=!{fdId}&fdModelName=!{fdModelName}"
							name="columntable">
							<!-- #54516 流程重启 2018年5月8日 -->
							<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
							<list:col-checkbox></list:col-checkbox>
							</c:if>
							<list:col-serial></list:col-serial>
							<list:col-auto props=""></list:col-auto>
						</list:colTable>
					</list:listview>
					<list:paging channel="channel_draft"/>
				</td>
			</tr>
		</table>
		<script>
			seajs.use(['lui/topic'], function(topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					window.setTimeout(function() {
						topic.channel("channel_draft").publish('list.refresh');
					}, 2000);
				});
				//筛选器变化		
				topic.channel("channel_draft").subscribe("criteria.changed", function(evt) {
					
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
		</script>
	</ui:content>
</ui:tabpanel>
<script>
	//批量特权操作
	function batchPrivil(method,id) {
		var selected;
		$("#"+id+" input[name='List_Selected']:checked").each(function() {
			selected = true;
			return false;
		});
		if (selected) {
			var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method='+method+'&fdType=${JsParam.fdType}&contentId='+id;
			seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
				dialog.iframe(url,
						"${lfn:message('sys-lbpmmonitor:button.batchPrivi')}",
						function(value) {
						}, {
							"width" : 600,
							"height" : 400,
							topWin : window
						});
			});
			return;
		} else {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}
	//批量修改处理人
	function batchModifyHandler(fdUrl,id) {
		var selected;
		$("#"+id+" input[name='List_Selected']:checked").each(function() {
			selected = true;
			return false;
		});
		if (selected) {
			var url =fdUrl+'/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchModifyHandler.jsp?contentId='+id;
			seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
				dialog.iframe(url,
						"${lfn:message('sys-lbpmmonitor:button.batchModifyHandler')}",
						function(value) {
							topic.publish("list.refresh");
						}, {
							"width" : 600,
							"height" : 400,
							topWin : window
						});
			});
			return;
		} else {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}

	//批量修改流程特权人
	function batchModifyPrivileger(fdUrl,id) {
		var selected;
		$("#"+id+" input[name='List_Selected']:checked").each(function() {
			selected = true;
			return false;
		});
		if (selected) {
			var url =fdUrl+'/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchModifyPrivileger.jsp?contentId='+id;
			seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
				dialog.iframe(url,
						"${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger')}",
						function(value) {
						}, {
							"width" : 600,
							"height" : 400,
							topWin : window
						});
			});
			return;
		} else {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}
	//#54516 流程重启 2018年5月8日
	function restartProcess(){
		//获取选中的ID
		var ids = new Array();
		$("input[name='List_Selected']:checked").each(function() {
			ids.push($(this).val());
		});
		if(ids.length==0){
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.alert("${lfn:message('page.noSelect')}");
			});
			return;
		}
		var url = '<c:url value="/sys/lbpmservice/support/lbpmProcessRestartAction.do" />?method=restartProcess';
		seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
			dialog.confirm("${lfn:message('sys-lbpmmonitor:process.restart.message')}",function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.ajax({
						url: url,
						type: 'POST',
						data:$.param({"List_Selected":ids},true),
						dataType: 'json',
						error: function(data){
							if(window.del_load!=null){
								window.del_load.hide(); 
							}
							dialog.result(data.responseJSON);
						},
						success: restartCallback
				   });
				}
			});
			window.restartCallback = function(data){
				if(window.del_load!=null){
					window.del_load.hide();
					topic.publish("list.refresh");
				}
				dialog.result(data);
			};
		});
	}
	
	//一键催办操作
	function onekeyPress(docIds,method,id) {
		var selected;
		if(docIds){
			selected = true;
		} else {
			$("#"+id+" input[name='List_Selected']:checked").each(function() {
				selected = true;
				return false;
			});
		}
		
		if (selected) {
			if(docIds){
				var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method='+method+'&fdType=${JsParam.fdType}&oneKey=1&docIds=' + docIds;
				var dialogTitle = "${lfn:message('sys-lbpmservice:lbpm.operation.admin_press')}";
			} else {
				var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method='+method+'&fdType=${JsParam.fdType}&oneKey=1&contentId='+id;
				var dialogTitle = "${lfn:message('sys-lbpmmonitor:button.onekeyPress')}";
			}
			
			seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
				dialog.iframe(url,
						dialogTitle,
						function(value) {
						}, {
							"width" : 600,
							"height" : 400,
							topWin : window
						});
			});
			return;
		} else {
			seajs.use([ 'lui/dialog' ], function(dialog) {
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}

	// 处理无效流程【文档不存在】
	function handleUnavailableProcess() {
		var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/unavailableProcess_remove.jsp';
		seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
			dialog.iframe(url,
					"${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.handleUnavailableProcess.title')}",
					function(value) {
					}, {
						"width" : 600,
						"height" : 400,
						topWin : window
					});
		});
	}
 	
	domain.autoResize();
	Array.prototype.contains = function (arr){
		for(var i=0;i<this.length;i++){
			if(this[i] == arr){
				return true;
			}
		}
		return false;
	};
	</script>

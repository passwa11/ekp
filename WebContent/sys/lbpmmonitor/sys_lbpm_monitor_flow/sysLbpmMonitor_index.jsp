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
			<c:if test="${param.method!='getExpired' && param.method!='getLimitExpired' }">
			<table style="width: 100%">
				<tr>
					<td valign="top">
								<list:criteria channel="channel_common" id="criteria1" expand="false" multi="false">
								<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"/>
								
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
												channel="channel_common">
												<list:varParams modelName="${templateModelName}" />
											</list:cri-ref>
										</c:if>
										<c:if test="${!isSimpleCategory}">
											<list:cri-ref ref="criterion.sys.category" key="docCategory"
												multi="false"
												title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.flow.categoryNav') }"
											  	channel="channel_common">
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
 								</list:criteria>
								<!-- 排序 -->
								<div class="lui_list_operation">
									<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
										<!-- 全选 -->
										<div class="lui_list_operation_order_btn">
											<list:selectall></list:selectall>
										</div>
										<!-- 分割线 -->
										<div class="lui_list_operation_line"></div>
									</c:if>
									<c:if test="${param.method!='getProcessRestart'}">
									<!-- 排序 -->
									<div class="lui_list_operation_sort_btn">
										<div class="lui_list_operation_order_text">
											${ lfn:message('list.orderType') }：
										</div>
										<div class="lui_list_operation_sort_toolbar">
											<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6">
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
													<c:if test="${param.fdType!='error' && param.method!='getProcessRestart' && param.fdType!='running'}">
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
									</c:if>
									<c:if test="${param.method!='getProcessRestart'}">
										<!-- 分页 -->
										<div class="lui_list_operation_page_top">
											<list:paging layout="sys.ui.paging.top" >
											</list:paging>
										</div>
									</c:if>
									<c:if test="${param.method =='getProcessRestart'}">
										<!-- 分页 -->
										<div class="lui_list_operation_page_top">
											<list:paging layout="sys.ui.paging.top2" >
											</list:paging>
										</div>
									</c:if>
									<!-- 操作按钮 -->
									<div style="float:right">
										<div style="display: inline-block;vertical-align: middle;">
											<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired'}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="batchModifyHandler"
														text="${lfn:message('sys-lbpmmonitor:button.batchModifyHandler.short')}"
														onclick="batchModifyHandler('${group.fdUrl}')" order="1"></ui:button>
												</ui:toolbar>
											</c:if>
											<c:if test="${param.fdType=='running' || param.fdType=='error' || param.method=='getInvalidHandler' || param.method=='getExpired' || param.method=='getLimitExpired' ||
											param.method=='getPause'}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="batchModifyPrivileger"
														text="${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger.short')}"
														onclick="batchModifyPrivileger('${group.fdUrl}')" order="1"></ui:button>
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
													<ui:toolbar id="Btntoolbar">
														<ui:button id="restart"
															text="${lfn:message('sys-lbpmmonitor:button.process.restart')}"
															onclick="restartProcess()" order="1"></ui:button>
													</ui:toolbar>
												</c:if> 
											</c:if>   
											<c:if test="${(param.fdType != null && param.fdType!='' && param.fdType!='finish') || (param.method!='listChildren' && param.method!='getRecentHandle' && param.method!='getProcessRestart' && param.method!='getPause')}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="batchPrivi"
														text="${lfn:message('sys-lbpmmonitor:button.batchPrivi.short')}"
														onclick="batchPrivil()" order="2"></ui:button>
												</ui:toolbar>
											</c:if>
											<c:if test="${param.method=='getExpired' || param.method=='getLimitExpired'}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="onekeyPress"
														text="${lfn:message('sys-lbpmmonitor:button.onekeyPress')}"
														onclick="onekeyPress()" order="1"></ui:button>
												</ui:toolbar>
											</c:if>
											<c:if test="${param.fdType=='error'}">
												<ui:toolbar id="Btntoolbar">
													<ui:button id="handleUnavailableProcess"
														text="${lfn:message('sys-lbpmmonitor:button.handleUnavailableProcess')}"
														onclick="handleUnavailableProcess()" order="2"></ui:button>
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
										<!-- #54516 流程重启 2018年5月8日 -->
										<c:if test="${param.method != 'getRecentHandle' && param.method != 'getProcessRestart' && !(param.method == 'listChildren' && param.fdStatus == 'all')}">
										<list:col-checkbox></list:col-checkbox>
										</c:if>
										<list:col-serial></list:col-serial>
										<list:col-auto props=""></list:col-auto>
									</list:colTable>
								</list:listview>
								<list:paging></list:paging>
								<script>
								//批量特权操作
								function batchPrivil() {
									var selected;
									var docIds = [];
									var select = document.getElementsByName("List_Selected");
									for ( var i = 0; i < select.length; i++) {
										if (select[i].checked) {
											docIds.push(select[i].value);
											selected = true;
										}
									}
									if (selected) {
										var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method=${JsParam.method}&fdType=${JsParam.fdType}';
										seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
											//弹窗遮罩全覆盖后为了兼容原有的获取值的模式
											top.selectDocIds = docIds;
											
											dialog.iframe(url,
													"${lfn:message('sys-lbpmmonitor:button.batchPrivi')}",
													function(value) {
														//topic.publish('list.refresh');
													}, {
														"width" : 600,
														"height" : 400,
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
								function batchModifyHandler(fdUrl) {
									var selected;
									var docIds = [];
									var select = document.getElementsByName("List_Selected");
									for ( var i = 0; i < select.length; i++) {
										if (select[i].checked) {
											docIds.push(select[i].value);
											selected = true;
										}
									}
									if (selected) {
										var url =fdUrl+'/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchModifyHandler.jsp';
										seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
											//弹窗遮罩全覆盖后为了兼容原有的获取值的模式
											top.selectDocIds = docIds;
											
											dialog.iframe(url,
													"${lfn:message('sys-lbpmmonitor:button.batchModifyHandler')}",
													function(value) {
														//topic.publish('list.refresh');
													}, {
														"width" : 600,
														"height" : 400,
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
								function batchModifyPrivileger(fdUrl) {
									var selected;
									var select = document.getElementsByName("List_Selected");
									var docIds = [];
									for ( var i = 0; i < select.length; i++) {
										if (select[i].checked) {
											docIds.push(select[i].value)
											selected = true;
										}
									}
									if (selected) {
										var url =fdUrl+'/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchModifyPrivileger.jsp';
										seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
											//弹窗遮罩全覆盖后为了兼容原有的获取值的模式
											top.selectDocIds = docIds;

											dialog.iframe(url,
													"${lfn:message('sys-lbpmmonitor:button.batchModifyPrivileger')}",
													function(value) {
														//topic.publish('list.refresh');
													}, {
														"width" : 600,
														"height" : 400,
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
									if(ids.length==1){
										var msg = [];
										msg.push("<div id='restartProcessDiv' style='width:200px;text-align:left;'><span style='height:25px;line-height:25px;'>${lfn:message('sys-lbpmmonitor:process.restart.message')}</span>");
										msg.push('<br>');
										msg.push('<span name="restartProcessSpan" style="height:25px;line-height:25px;">${lfn:message("sys-lbpmmonitor:process.restart.nodeId")}</span>');
										msg.push('<select name="restartProcessSelect" style="width:190px;height:25px;line-height:25px;"></select>');
										msg.push('</div>');
										seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic'], function($, Dialog, topic) {
											var dialog = Dialog.build({
												config : {
													width : 400,
													cahce : false,
													title : "${lfn:message('sys-lbpmmonitor:button.process.restart')}",
													content : {
														type : "common",
														html : msg.join(''),
														iconType : 'question',
														buttons : [ {
															name : "${lfn:message('button.ok')}",
															value : true,
															focus : true,
															fn : function(value, dialog) {
																var url = '<c:url value="/sys/lbpmservice/support/lbpmProcessRestartAction.do" />?method=restartProcess';
																window.del_load = Dialog.loading();
																$.ajax({
																	url: url,
																	type: 'POST',
																	data:$.param({"List_Selected":ids,"restartNodeId":dialog.element.find("select[name='restartProcessSelect']").val()},true),
																	dataType: 'json',
																	error: function(data){
																		if(window.del_load!=null){
																			window.del_load.hide(); 
																		}
																		dialog.result(data.responseJSON);
																	},
																	success: function(data){
																		if(window.del_load!=null){
																			window.del_load.hide();
																			topic.publish("list.refresh");
																		}
																		Dialog.result(data);
																	}
															    });
																dialog.hide(value);
															}
														}, {
															name : "${lfn:message('button.cancel')}",
															value : false,
															styleClass : 'lui_toolbar_btn_gray',
															fn : function(value, dialog) {
																dialog.hide(value);
															}
														} ]
													}
												}
											}).on('show', function() {
												var self = this;
												var $select = self.element.find("select[name='restartProcessSelect']");
												var url = '<c:url value="/sys/lbpmservice/support/lbpmProcessRestartAction.do" />?method=getNodesByProcessId';
												$.ajax({
													url: url,
													type: 'POST',
													data:$.param({"fdProcessId":ids[0]},true),
													dataType: 'json',
													error: function(data){
														dialog.result(data.responseJSON);
													},
													success: function(data){
														if(data && data.length>0){
															for(var i=0;i<data.length;i++){
																$select.append("<option value='"+data[i].id+"'>"+data[i].id+"."+data[i].name+"</option>");
															}
														}else{
															$select.hide();
															self.element.find("span[name='restartProcessSpan']").hide();
														}
													}
											   });
											}).show();
										});
									}else{
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
								}
								
								//一键催办操作
								function onekeyPress(docIds) {
									var selected;
									var isMutil = false;
									if(docIds){
										selected = true;
									} else {
										var select = document.getElementsByName("List_Selected");
										for ( var i = 0; i < select.length; i++) {
											if (select[i].checked) {
												docIds.push(select[i].value);
												selected = true;
												isMutil = true;
											}
										}
									}
									
									if (selected) {
										if(docIds && !isMutil){
											var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method=${JsParam.method}&fdType=${JsParam.fdType}&oneKey=1&docIds=' + docIds;
											var dialogTitle = "${lfn:message('sys-lbpmservice:lbpm.operation.admin_press')}";
										} else {
											var url = '/sys/lbpmmonitor/sys_lbpm_monitor_flow/batchPirvil_flow.jsp?method=${JsParam.method}&fdType=${JsParam.fdType}&oneKey=1';
											var dialogTitle = "${lfn:message('sys-lbpmmonitor:button.onekeyPress')}";
										}
										
										seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
											//弹窗遮罩全覆盖后为了兼容原有的获取值的模式
											top.selectDocIds = docIds;
											
											dialog.iframe(url,
													dialogTitle,
													function(value) {
														if(isMutil){
															topic.publish('list.refresh');
														}
													}, {
														"width" : 600,
														"height" : 400,
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
											   var flag = '${empty param.modelName }';
												/* if (keyArray
														.contains("docSubject")) {
													if (!keyArray.contains("fdModelName")&& flag==="true") {
														alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.flow.selectModuleFirst"/>');												
														return;
													}
												} */
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
			</c:if>
			<c:if test="${param.method=='getExpired' || param.method=='getLimitExpired' }">
				<c:import url="/sys/lbpmmonitor/sys_lbpm_monitor_flow/sysLbpmMonitor_tab.jsp" charEncoding="UTF-8">
				</c:import>
			</c:if>
		</div>
		<%
			//提供流程概况的饼状图跳转，根据时间展示对应的内容
			//时间范围
			String timeFrame = request.getParameter("timeFrame");
			if(StringUtil.isNotNull(timeFrame)){
				timeFrame = StringEscapeUtils.escapeJavaScript(timeFrame);
				request.setAttribute("timeFrame", timeFrame);
				//存在时间参数,导入script脚本
		%>
		<script type="text/javascript">
			var timeFrame = '${timeFrame}';
			var num = setInterval(() => {
				var criteria = $(".criteria")[0];
				if(criteria){
					var uid = $(criteria).attr("data-lui-cid");
					//打开筛选
					var criteriaObj = LUI(uid);
					if(criteriaObj.isDrawed && criteriaObj.moreAction){
						clearInterval(num);
						criteriaObj.expandCriterions(!criteriaObj.expand);
						//初始化筛选器的值
						setTimeout(function(){
							var calendar = $(".criterion-calendar")[0];
							if(calendar){
								uid = $(calendar).attr("data-lui-cid");
								var calendarObj = LUI(uid);
								
								//获取对应得类型值
								var type = calendarObj.TYPE_ALL;
								if(timeFrame == "year"){
									//近一年
									type = calendarObj.TYPE_YEAR;
								}else if(timeFrame == "month"){
									//近一个月
									type = calendarObj.TYPE_MONTH;
								}else if(timeFrame == "week"){
									//近一周
									type = calendarObj.TYPE_WEEK;
								}
								var onum = setInterval(() => {
									var len = $(".criterion-clear").length;
									if(len > 0){
										clearInterval(onum);
										var allActionArea = calendarObj.allActionArea;
										for(var i=0; i<allActionArea.length; i++){
											var actionArea = allActionArea[i];
											var child = $(actionArea).find("a")[0];
											if(child && $(child).attr("data-lui-val") == type){
												child.click();//对应得action点击
											}
										}
									}
								}, 200);
							}
						},500);
					}
				}
			}, 200);
		</script>
		<%
			}
		%>
	</template:replace>
</template:include>
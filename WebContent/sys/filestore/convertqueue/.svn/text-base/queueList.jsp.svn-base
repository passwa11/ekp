<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="wpsoaassist" value="<%=SysAttWpsoaassistUtil.isEnable()%>"/>
<template:include ref="default.simple">
	<template:replace name="body">
		<div style="margin: 0 auto; width: 95%">
			<%-- 筛选器 --%>
			<list:criteria id="criteria1">
				<list:cri-auto
					modelName="com.landray.kmss.sys.filestore.model.SysFileConvertQueue"
					property="fdAttModelId;fdAttMainId;fdFileName;fdConverterKey;fdConvertNumber" expand="false" />
				<list:cri-criterion
					title="${lfn:message('sys-filestore:convertQueue.status')}"
					key="fdConvertStatus" expand="true">
					<list:box-select>
						<c:choose>
							<c:when test="${param.clientId==null||param.clientId=='' }">
								<list:item-select cfg-defaultValue="0">
									<ui:source type="Static">
										[
										{text:'${ lfn:message('sys-filestore:convertStatus.0') }', value:'0'},
										{text:'${ lfn:message('sys-filestore:convertStatus.1') }', value:'1'},
										{text:'${ lfn:message('sys-filestore:convertStatus.5') }',value:'5'},
										{text:'${ lfn:message('sys-filestore:convertStatus.6') }',value:'6'},
										{text:'${ lfn:message('sys-filestore:convertStatus.9') }',value:'9'},
										{text:'${ lfn:message('sys-filestore:convertStatus.3') }',value:'3'},
										{text:'${ lfn:message('sys-filestore:convertStatus.99') }',value:'99'},
										{text:'${ lfn:message('sys-filestore:convertStatus.4') }',value:'4'}
										]
									</ui:source>
								</list:item-select>
							</c:when>
							<c:otherwise>
								<list:item-select cfg-defaultValue="1">
									<ui:source type="Static">
										[
										{text:'${ lfn:message('sys-filestore:convertStatus.1') }', value:'1'},
										{text:'${ lfn:message('sys-filestore:convertStatus.other') }',value:'other'}
										]
									</ui:source>
								</list:item-select>
							</c:otherwise>
						</c:choose>
					</list:box-select>
				</list:cri-criterion>
			</list:criteria>
			<!-- 排序 -->
			<div class="lui_list_operation">
				<!-- 全选 -->
				<div class="lui_list_operation_order_btn">
					<list:selectall></list:selectall>
				</div>
				<!-- 分页 -->
				<div class="lui_list_operation_page_top">	
					<list:paging layout="sys.ui.paging.top" > 		
					</list:paging>
				</div>
				<div style="float:right;">
					<div style="display: inline-block;vertical-align: middle;">
						<ui:toolbar count="3" id="btnToolBar">
							<kmss:authShow roles="SYSROLE_ADMIN;SYSROLE_SYSADMIN">
								<ui:button
									text="${lfn:message('button.deleteall')}"
									onclick="delQueues();" order="1"></ui:button>
								<ui:button
									text="${lfn:message('sys-filestore:queue.setting')}"
									onclick="setQueueParam();" order="2"></ui:button>
								<ui:button
									text="${lfn:message('sys-filestore:button.reDistribute')}"
									onclick="reDistribute();" order="3"></ui:button>
								<ui:button
									text="${lfn:message('sys-filestore:button.reAllDistribute')}"
									onclick="reAllDistribute();" order="5"></ui:button>
							</kmss:authShow>
						</ui:toolbar>
					</div>
				</div>
			</div>
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			<list:listview cfg-needMinHeight="false">
				<ui:source type="AjaxJson">
					{url:'/sys/filestore/sys_filestore/sysFileConvertQueue.do?method=data&clientId=${param.clientId }'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
					onRowClick="goQueueLogList('!{fdId}','!{fdFileName}')">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto
						props="fdAttMainId;fdModule;linkFileName;fdConverterType;fdConverterKey;fdConvertNumber;fdConvertStatus;fdStatusTime;fdCreateTime"></list:col-auto>
				</list:colTable>
			</list:listview>
			<list:paging></list:paging>
			<c:if test="${wpsoaassist}">
				<script
					src="${ LUI_ContextPath }/sys/attachment/sys_att_main/wps/oaassist/js/wps_utils.js?s_cache=${LUI_Cache}"></script>
			</c:if>
			<script
				src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
			<script
				src="${ LUI_ContextPath }/sys/filestore/resource/listAutoResizeCommon.js?s_cache=${LUI_Cache}"></script>
			<script type="text/javascript">
				seajs.use([ 'theme!list', 'theme!portal' ]);
				startAutoResize();
				seajs.use('lui/topic', function(topic) {
					topic.subscribe("list.changed", function(rtnData) {
						domain.call(parent, "filestoreQueueEvent", [ {
							type : "event",
							name : "removeQueueLogInfo"
						} ]);
					}, null);
				});
				
				var otherFailure = true;
				var toolFailure = false;
				var timeoutFailure = false;

				function getChoosedFailureType() {
					var choosedFailureType = "";
					if (otherFailure) {
						choosedFailureType += "3;";
					}
					if (toolFailure) {
						choosedFailureType += "5;";
					}
					if (timeoutFailure) {
						choosedFailureType += "6;";
					}
					if (choosedFailureType.length > 0) {
						choosedFailureType = choosedFailureType.substring(0,
								choosedFailureType.length - 1);
					}
					if(choosedFailureType==""){
						return [];
					}
					return choosedFailureType.split(";");
				}

				function failureClick(obj) {
					if (obj.checked) {
						if (obj.value == 3) {
							otherFailure = true;
						}
						if (obj.value == 5) {
							toolFailure = true;
						}
						if (obj.value == 6) {
							timeoutFailure = true;
						}
					} else {
						if (obj.value == 3) {
							otherFailure = false;
						}
						if (obj.value == 5) {
							toolFailure = false;
						}
						if (obj.value == 6) {
							timeoutFailure = false;
						}
					}
				}

				function goQueueLogList(queueId,queueFileName) {
					domain.call(parent, "filestoreQueueEvent", [ {
						type : "event",
						name : "loadQueueLogInfo",
						queueId : queueId,
						queueFileName : queueFileName
					} ]);
				}
				
				var reAllDistribute=function(){
					var reDistributeType = [];
					var confirmHintInfo = '<input type="checkbox" name="convert_failure" value="3" checked="true" onclick="failureClick(this);"/>${ lfn:message("sys-filestore:qitashibai") } <input type="checkbox" name="convert_failure" value="5" onclick="failureClick(this);" />${ lfn:message("sys-filestore:convertertoolshibai") } <input type="checkbox" name="convert_failure" value="6" onclick="failureClick(this);" />${ lfn:message("sys-filestore:chaoshishibai") }<br/>';
					seajs.use('lui/dialog',function(dialog){
						parent.dialoging=true;
						dialog.confirm(confirmHintInfo,
								function(value) {
									if (value == true) {
										reDistributeType = getChoosedFailureType();
										window.innerLoading = dialog.loading();
										$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertQueue.do?method=reDistribute"/>',$.param({"Redistribute_All":true,"Convert_Failure" : reDistributeType},true),dialogCallBack,'json');
									}
									parent.dialoging=false;
								}
						);}
					);
				};
				
				var reDistribute=function(){
					seajs.use('lui/dialog',function(dialog){
						var values=[];
						$("input[name='List_Selected']:checked").each(function(){values.push($(this).val());});
						var confirmHintInfo='<bean:message key="confirm.redistribute" bundle="sys-filestore"/>';
						parent.dialoging=true;
						if(values.length == 0){
							dialog.alert('<bean:message key="page.noSelect"/>',function(value){
								parent.dialoging=false;
							});
							return;
						}
						dialog.confirm(confirmHintInfo,
							function(value) {
								if (value == true) {
									window.innerLoading = dialog.loading();
									$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertQueue.do?method=reDistribute"/>',$.param({"Redistribute_All":false,"List_Selected":values},true),dialogCallBack,'json');
								}
								parent.dialoging=false;
							}
						);}
					);
				};
				
				var setQueueParam=function(fdId){
					seajs.use('lui/dialog',function(dialog){
						var values = [];
						var fdIds="";
						if (fdId != null) {
							values.push(fdId);
						} else {
							$("input[name='List_Selected']:checked").each(function() {values.push($(this).val());});
							if (values.length == 0) {
								dialog.alert('<bean:message key="page.noSelect"/>');
								return;
							}else {
								for(var i=0;i<values.length;i++){
									fdIds+=values[i];
									if(i<values.length-1){
										fdIds+=";";
									}
								}
							}
						}
						dialog.iframe("/sys/filestore/sys_filestore/sysFileConvertQueueParam.do?method=setting&queueIds="+ fdIds,
							"${ lfn:message('sys-filestore:queue.setting') }",
							function() {
								seajs.use(['lui/base','lui/data/source'],function(base,source){
									var criteria = base.byId('criteria1');
									criteria.setValue('fdConvertStatus','0');
								});
							}, 
							{width : 600,height : 400});
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
				
				var delQueues=function(){
					seajs.use('lui/dialog',function(dialog){
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){values.push($(this).val());});
						var confirmHintInfo='<bean:message key="convertQueue.comfirmDelete.selection" bundle="sys-filestore"/>';
						var delType="selection";
						parent.dialoging=true;
						if(values.length == 0){
							dialog.alert('<bean:message key="page.noSelect"/>',function(value){
								parent.dialoging=false;
							});
							return;
						}
						dialog.confirm(confirmHintInfo,function(value) {
								if (value == true) {
									window.innerLoading = dialog.loading();
									$.post('<c:url value="/sys/filestore/sys_filestore/sysFileConvertQueue.do?method=delQueues"/>',$.param({"selected":values},true),dialogCallBack,'json');
								}
								parent.dialoging=false;
							}
						);
					});
				};
			</script>
	</template:replace>
</template:include>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<div class="mechanism_div">
		<html:form action="/sys/modeling/base/modelingAppModel.do">
			<html:hidden property="fdMechanismConfig"/>
			<div class="switch_submit_div" align="left">
				<div class="switch_div">
					<ui:switch property="isImportEnable" checked="${mechanism['import'] }" onValueChange="changeEnable('import', true);" id="onImportEnable"
							   enabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.import.set')}" disabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.import.set')}"/>
				</div>
			</div>

			<div class="config_div">
				<div id="visible_import_setting" style="margin-top: 20px;">
					<input itype="text" style="display:none" />
					<list:criteria id="criteria1">
						<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingImportConfig.fdTemplateName')}"></list:cri-ref>
					</list:criteria>
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
								<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
									<list:sortgroup>
										<list:sort property="fdCreateTime" text="${lfn:message('sys-modeling-base:modelingImportConfig.createTime')}" group="sort.list" value="down"></list:sort>
									</list:sortgroup>
								</ui:toolbar>
							</div>
						</div>
						<!-- 分页 -->
						<div class="lui_list_operation_page_top">
							<%@ include file="/sys/profile/showconfig/showConfig_paging_top.jsp" %>
						</div>
						<div style="float:right">
							<div style="display: inline-block;vertical-align: middle;">
								<ui:toolbar id="Btntoolbar">
									<ui:button text="${lfn:message('button.add')}" id="add" onclick="window.addDoc('${modelingAppModelForm.fdId }');"></ui:button>
									<ui:button id="del" text="${lfn:message('button.deleteall')}" order="3" onclick="window.delDoc()"></ui:button>
								</ui:toolbar>
							</div>
						</div>
					</div>
					<ui:fixed elem=".lui_list_operation" id="hack_fix"></ui:fixed>

					<list:listview id="listview">
						<ui:source type="AjaxJson">
							{url:'/sys/modeling/main/modelingImportConfig.do?method=list&fdModelId=${modelingAppModelForm.fdId }'}
						</ui:source>
						<list:colTable url="${LUI_ContextPath }/sys/profile/listShow/sys_listShow/sysListShow.do?method=getSort&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel" isDefault="true" layout="sys.ui.listview.columntable">
							<list:col-checkbox></list:col-checkbox>
							<list:col-serial></list:col-serial>
							<list:col-auto></list:col-auto>
						</list:colTable>
					</list:listview>
					<!-- 分页 -->
					<list:paging/>
				</div>
			</div>
			<script>
				seajs.use([ 'sys/ui/js/dialog' ,'lui/topic','lui/jquery'],function(dialog,topic,$) {
					window.addDoc=function(fdModelId){
						var url='/sys/modeling/main/modelingImportConfig.do?method=add&fdModelId='+fdModelId;
						dialog.iframe(url,'${lfn:message("sys-modeling-base:table.modelingImportConfig")}',function() {
							topic.publish("list.refresh");
						},{
							width : 720,
							height : 600
						});
					}
					window.editDoc=function(fdId){
						var url='/sys/modeling/main/modelingImportConfig.do?method=edit&fdId='+fdId;
						dialog.iframe(url,'${lfn:message("sys-modeling-base:table.modelingImportConfig")}',function() {
							topic.publish("list.refresh");
						},{
							width : 720,
							height : 600
						});
					}

					window.delOne = function(fdId) {
						var values = [fdId];
						dialog.confirm('${lfn:message("page.comfirmDelete.newVersion")}',function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.post('${LUI_ContextPath }/sys/modeling/main/modelingImportConfig.do?method=deleteall',
										$.param({"List_Selected":values},true),delCallback,'json');
							}
						});
					};
					//删除文档
					window.delDoc = function(){
						var values = [];
						$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
						if(values.length==0){
							dialog.alert('${lfn:message("page.noSelect")}');
							return;
						}

						dialog.confirm('${lfn:message("page.comfirmDelete")}',function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.post('${LUI_ContextPath }/sys/modeling/main/modelingImportConfig.do?method=deleteall',
										$.param({"List_Selected":values},true),delCallback,'json');
							}
						});
					};

					function delCallback(data){
						if(window.del_load!=null)
							window.del_load.hide();
						if(data!=null && data.status==true){
							topic.publish("list.refresh");
							dialog.success('${lfn:message("return.optSuccess")}');
						}else{
							dialog.failure('${lfn:message("return.optFailure")}');
						}
					};
				});
				function importSave(cfg) {
					if($("input[name='isImportEnable']").val() == 'true'){
						cfg["import"]="true";
					}else{
						cfg["import"]="false";
					}
				}
				LUI.ready(function() {
					changeEnable('import');
					beforeSubmitFuncArr.push(importSave);
				});

			</script>
		</html:form>
		</div>
		<script>
			var _numberSet = false;
			var beforeSubmitFuncArr = new Array();
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
				window.ajaxUpdate = function(method,successFlag){
					var cfg = {};
					var cfgStr = document.getElementsByName("fdMechanismConfig")[0].value;
					if(cfgStr!=""){
						cfg = LUI.toJSON(cfgStr);
					}
					console.log(beforeSubmitFuncArr);
					for (var i = 0; i < beforeSubmitFuncArr.length; i++) {
						beforeSubmitFuncArr[i].call(this,cfg);
					}
					let fdMechanismConfig = LUI.stringify(cfg);
					let fdId = "${param.fdId}";
					let url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=" + method;
					$.ajax({
						url: url,
						data: {"fdId":fdId, "fdMechanismConfig":fdMechanismConfig},
						dataType: 'json',
						type: 'POST',
						success: function(data) {
							//确认提交，且返回成功，提示信息
							if(successFlag && data.status){
								dialog.success('<bean:message key="return.optSuccess" />');
							}
						},
						error: function(req) {
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					});
				}
				//确认是否有提示信息
				window.checkSubmitImportForm = function (name) {
					//是否开已开启导入机制,true-是
					var importEnable ='${mechanism['import']}';
					//如果页面加载时导入机制已经开启，此时要关闭，则提示确认信息
					if($("input[name='isImportEnable']").val()=='false' && importEnable === 'true') {
						var content = {
							"html": "${lfn:message('sys-modeling-base:modeling.model.set.check.content')}",
							"title": "${lfn:message('sys-modeling-base:modeling.model.set.check.title.pre')}"
									+" ["+"${lfn:message('sys-modeling-base:table.mechanismImport')}"+"] "
									+"${lfn:message('sys-modeling-base:modeling.model.set.check.title.suf')}",
							"width": "500px", "height": "200px"
						};
						content.callback = function (isOk) {
							if (isOk) {
								//确认-提交并提示成功信息
								ajaxUpdate('updateImport',true);
							}else{
								//取消-则重新打开样式
								$("#onImportEnable .weui_switch").click();
								$("#visible_" + name + "_setting").show();
							}
						}
						dialog.confirm(content);
					}else {
						//默认提交-不提示成功信息
						ajaxUpdate('updateImport',false);
					}
				}

			});

			function changeEnable(name, change) {
				var upperName = name[0].toUpperCase() + name.substring(1);
				if ($("input[name='is" + upperName + "Enable']").val() == 'true') {
					$("#visible_" + name + "_setting").show();
				} else {
					$("#visible_" + name + "_setting").hide();
				}
				if (change) {
					checkSubmitImportForm(name);
				}
			}

			function XForm_Util_UnitArray(array, sysArray, extArray) {
				array = array.concat(sysArray);
				if (extArray != null) {
					array = array.concat(extArray);
				}
				return array;
			}

			function XForm_getXFormDesignerObj_modelingApp(){
				var obj = [];
				var xformId ="${xformId}";
				<%-- // 1 加载主文档的字典 --%>
				var sysObj = _XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
				if (sysObj != null) {
					//通过xformId获取appmodel 从而获取有无流程标识
					var getIsFlowUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingBehavior.do?method=findModelIsFlowByXformId&xformId=" + xformId;
					$.ajax({
						url: getIsFlowUrl,
						type: "get",
						async: false,
						success: function (data) {
							if(!data){
								for (var i = 0; i <sysObj.length ; i++) {
									var sysItem  = sysObj[i];
									if(sysItem && sysItem.name=="fdProcessEndTime"){
										//无流程表单去掉流程结束时间
										sysObj.splice(i,1);
									}
								}
							}else{
								//#166825
								var fdHandlerObj = [];
								fdHandlerObj.name="LbpmExpecterLog_fdHandler";
								fdHandlerObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog_fdHandler')}";
								fdHandlerObj.type="com.landray.kmss.sys.organization.model.SysOrgPerson";
								sysObj.push(fdHandlerObj);
								var fdNodeObj = [];
								fdNodeObj.name="LbpmExpecterLog_fdNode";
								fdNodeObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog.fdNode')}";
								fdNodeObj.type="String";
								sysObj.push(fdNodeObj);
							}
						}
					});
				}
				var extObj = null;

				<%-- // 2加载扩展表单的字典 --%>
				extObj = _XForm_GetTempExtDictObj("${xformId}");

				return XForm_Util_UnitArray(obj, sysObj, extObj);
			}

			// 查询modelName的属性信息
			function _XForm_GetSysDictObj(modelName){
				return Formula_GetVarInfoByModelName(modelName);
			}

			// 查找自定义表单的数据字典
			function _XForm_GetTempExtDictObj(tempId) {
				return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
			}

		</script>
	</template:replace>
</template:include>
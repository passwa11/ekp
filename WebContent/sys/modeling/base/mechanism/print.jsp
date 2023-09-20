<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
	if("false".equals(request.getAttribute("enableFlow"))){
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
	}else{
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
	}
%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css?s_cache=${LUI_Cache}"/>
		<style>
			body{
				overflow: hidden;	
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<div class="mechanism_div">
		<html:form action="/sys/modeling/base/modelingAppModel.do">
			<html:hidden property="fdMechanismConfig"/>
			<div class="switch_submit_div" align="left">
				<div class="switch_div">
					<ui:switch property="isPrintEnable" checked="${mechanism.print.enable}" onValueChange="changePrintEnable();"
							   enabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.print.set')}"
							   disabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.print.set')}"
					           id = "isPrintEnableId"/>
				</div>
				<div class="submit_div">
					<ui:button text="${ lfn:message('button.save') }" order="1" onclick="checkSubmit('updatePrint');"/>
				</div>
			</div>
			<div class="config_div">
				<div id="visible_print_setting">
				<div class='title'>${lfn:message('sys-modeling-base:print.Choose.mode')}</div>
				<div class='content'>
					<div>
						<label>
							<input name="print_modes" value='default' type="radio" onchange="changeMode(this)" <c:if test="${'true' eq mechanism.print.defaultMode || empty mechanism.print.defaultMode}">checked="checked"</c:if>/>
							${lfn:message('sys-modeling-base:print.default.template')}
						</label>
						<label>
							<input name="print_modes" value='custom' type="radio" onchange="changeMode(this)" <c:if test="${'true' eq mechanism.print.customMode}">checked="checked"</c:if>/>
								${lfn:message('sys-modeling-base:print.custom.template')}
						</label>
					</div>
					<div class="default_print_setting_outer">
						<div id="default_print_setting" style="display: ${'true' eq mechanism.print.customMode ? 'none' : "block"}">
							<span>	${lfn:message('sys-modeling-base:print.default.options')}</span>
							<label>
								<input name="print_items" id="subject" type="checkbox" <c:if test="${'true' eq mechanism.print.subject}">checked="checked"</c:if>/>
								<bean:message bundle="sys-modeling-main" key="main.config.subject" />
							</label>
							<label>
								<input name="print_items" id="info" type="checkbox" <c:if test="${'true' eq mechanism.print.info}">checked="checked"</c:if>/>
								<bean:message bundle="sys-modeling-main" key="main.config.info" />
							</label>
							<!-- 开启流程才有此配置 -->
							<c:if test="${modelingAppModelForm.fdEnableFlow }">
								<label>
									<input name="print_items" id="note" type="checkbox" <c:if test="${'true' eq mechanism.print.note}">checked="checked"</c:if>/>
									<bean:message bundle="sys-modeling-main" key="main.config.note" />
								</label>
							</c:if>
							<label>
								<input name="print_items" id="qrcodex" type="checkbox" <c:if test="${'true' eq mechanism.print.qrcodex}">checked="checked"</c:if>/>
								<bean:message bundle="sys-modeling-main" key="main.config.qrcode" />
							</label>
						</div>
					</div>
						<%--自定义模板--%>
					<div id="custom_print_setting" style="display: ${'true' eq mechanism.print.customMode ? 'block' : 'none'}">
						<c:if test="${modelingAppModelForm.sysFormTemplateForms['modelingApp'].fdMode eq '4'}"><!-- 多表单 -->
							<c:set var="_isHide" value="true" scope="request"></c:set>
							<c:set var="_isHidePrintDesc" value="true" scope="request"></c:set>
							<c:set var="_isHideDefaultSetting" value="true" scope="request"></c:set>
							<c:set var="_isShowName" value="false" scope="request"></c:set>
							<c:set var="_isHidePrintMode" value="true" scope="request"></c:set>
							<jsp:include page="/sys/modeling/base/print/index.jsp"/>
						</c:if>
						<c:if test="${modelingAppModelForm.sysFormTemplateForms['modelingApp'].fdMode ne '4'}">
							<input type='hidden' name='submitOper' value='updateConfig'/>
							<div id='printCreateBtn' style='display:none;<c:if test="${modelingAppModelForm.sysPrintTemplateForm == null || empty modelingAppModelForm.sysPrintTemplateForm.fdPrintMode }">display:block</c:if>'>
								<div class="custom_print_create_btn" onclick="openPrintEditPage()">
									<i></i>
									<span><bean:message key="multiPrint.new" bundle="sys-print"/></span>
								</div>
								<script type="text/javascript">
									seajs.use(['lui/jquery', 'lui/topic'], function($, topic) {
								 		// 监听新建更新等成功后刷新
										topic.subscribe('successReloadPage', function() {
											//window.location.reload();
											$("#printListDiv").show();
											$("#printCreateBtn").hide();
										});
									});
								</script>
							</div>
							<div id="printListDiv" style='display:none;<c:if test="${modelingAppModelForm.sysPrintTemplateForm != null && not empty modelingAppModelForm.sysPrintTemplateForm.fdPrintMode}">display:block</c:if>'>
								<c:import url="/sys/print/include/multi_template/index.jsp"  charEncoding="UTF-8">
									<c:param name="fdModelId" value="${modelingAppModelForm.fdId }"></c:param>
									<c:param name="addUrl" value="/sys/modeling/base/modelingAppModel.do?method=createPrint"></c:param>
									<c:param name="viewUrl" value="/sys/modeling/base/modelingAppModel.do?method=editPrint"></c:param>
									<c:param name="editUrl" value="/sys/modeling/base/modelingAppModel.do?method=editPrint"></c:param>
									<c:param name="listUrl" value="/sys/modeling/base/modelingAppModel.do?method=listPrint"></c:param>
								</c:import>
								<script type="text/javascript">
									seajs.use(['lui/jquery', 'lui/topic'], function($, topic) {
								 		// 监听新建更新等成功后刷新
										topic.subscribe('delSuccessReloadPage', function() {
											//window.location.reload();
											$("#printListDiv").hide();
											$("#printCreateBtn").show();
										});
									});
								</script>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			</div>
			<script>
				function openPrintEditPage(){
					event.preventDefault();
				    event.stopPropagation();
					var url = '<c:url value="/sys/modeling/base/modelingAppModel.do"/>?method=createPrint&fdModelId=${modelingAppModelForm.fdId }';
					Com_OpenWindow(url,"_blank");
					return false;
				}
			
				function printSave(cfg) {
					cfg["print"] = {};
					if($("input[name='isPrintEnable']").val() == 'true'){
						cfg["print"]["enable"]="true";
					}else{
						cfg["print"]["enable"]="false";
					}
					var mode = $("[name='print_modes']:checked").val();
					if(mode == "default"){
						cfg["print"]["defaultMode"] = "true";
						cfg["print"]["customMode"] = "false";
					}else{
						cfg["print"]["defaultMode"] = "false";
						cfg["print"]["customMode"] = "true";
					}
					var items = document.getElementsByName("print_items");
					for(var i=0;i<items.length;i++) {
						if(items[i].checked) {
							cfg["print"][items[i].id]="true";
						} else {
							cfg["print"][items[i].id]="false";
						}
					}
				}
				function changeMode(obj){
					var value = obj.value;
					if(value == "default"){
						$("#default_print_setting").show();
						$("#custom_print_setting").hide();
						$("#subject").prop("checked",true);
						$("#info").prop("checked",true);
					}else{
						$("#default_print_setting").hide();
						$("#custom_print_setting").show();
						$("input[name='print_items']").prop("checked",false);
						resizeIframe();
					}

					seajs.use(['lui/jquery','lui/topic'], function($, topic) {
						topic.publish("modeling.print.changeMode", obj);
					});
				}
				function changePrintEnable() {
					changeEnable('print');
					// 再次开启，清空选项
					if($("input[name='isPrintEnable']").val() == 'true') {
						$("input[name='print_items']").prop("checked",false);
						$("input[name='print_modes'][value='default']").prop("checked",true);
						$("input[name='print_modes'][value='custom']").prop("checked",false);
						$("#default_print_setting").show();
						changeMode($('input[name="print_modes"]:checked')[0]);
					}
				}
				LUI.ready(function() {
					changeEnable('print');
					beforeSubmitFuncArr.push(printSave);
				});
				
				function resizeIframe(){
					var enable = $("[name='isPrintEnable']").val();
					if(!enable){
						enable = '${mechanism.print.enable}';
					}
					var mode = $("[name='print_modes']:checked").val();
					if(enable == 'true' && mode == 'custom'){
						var height = $(".lui_config_form").outerHeight() + "px";
						$(window.parent.document).find("#cfg_iframe").attr("height",height).css("height",height);
						$(window.parent.document).find("#cfg_iframe").css("min-height","800px");
					}
				}
			</script>
		</html:form>
		</div>
		<script>
			var _validation = new $KMSSValidation();
			var _numberSet = false;
			var beforeSubmitFuncArr = new Array();

			function changeEnable(name){
				var upperName = name[0].toUpperCase()+name.substring(1);
				if($("input[name='is"+upperName+"Enable']").val() == 'true'){
					$("#visible_"+name+"_setting").show();
				}else{
					$("#visible_"+name+"_setting").hide();
				}
			}
/*
			function XForm_Util_UnitArray(array, sysArray, extArray) {
				array = array.concat(sysArray);
				if (extArray != null) {
					array = array.concat(extArray);
				}
				return array;
			}

			function XForm_getXFormDesignerObj_modelingApp(){
				var obj = [];

				<%-- // 1 加载主文档的字典 --%>
				var sysObj = _XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
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
			}*/

		</script>
		<script>
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
				//提交前确认是否提示
				window.checkSubmit = function (method) {
					//是否已开启打印机制，true-是
					var printEnable = '${mechanism.print.enable}';
					//如果页面加载时打印机制已经开启，此时要关闭，则提示确认信息
					if($("input[name='isPrintEnable']").val()=='false' && printEnable === 'true') {
						var content = {
							"html": "${lfn:message('sys-modeling-base:modeling.model.set.check.content')}",
							"title": "${lfn:message('sys-modeling-base:modeling.model.set.check.title.pre')}"
									+" ["+"${lfn:message('sys-modeling-base:table.mechanismPrint')}"+"] "
									+"${lfn:message('sys-modeling-base:modeling.model.set.check.title.suf')}",
							"width": "500px", "height": "200px"
						};
						//确认项的回调函数
						content.callback = function (isOk) {
							if (isOk) {
								//确认-提交并提示成功信息
								submitForm(method);
							}else{
								//取消-则重新打开样式
								$("input[name='isPrintEnable']").val(true);
								$("#isPrintEnableId .weui_switch").click();
								$("#visible_print_setting").show();
							}
						}
						dialog.confirm(content);
					}else {
						//默认提交-无需确认
						submitForm(method);
					}
				}
			});

			window.submitForm = function(method){
				var cfg = {};
				var cfgStr = document.getElementsByName("fdMechanismConfig")[0].value;
				if(cfgStr!=""){
					cfg = LUI.toJSON(cfgStr);
				}
				for (var i = 0; i < beforeSubmitFuncArr.length; i++) {
					beforeSubmitFuncArr[i].call(this,cfg);
				}
				document.getElementsByName("fdMechanismConfig")[0].value = LUI.stringify(cfg);
		
				if(typeof Sys_Print_Base64Encodex !== "undefined" && Sys_Print_Base64Encodex){
					var fdDesignerHtml = $("input[name='sysFormTemplateForms.modelingApp.fdDesignerHtml']");
					fdDesignerHtml.val(Sys_Print_Base64Encodex(fdDesignerHtml.val() ? fdDesignerHtml.val() : ""));
					//兼容单表单的多表单表现
					//#171310 【服务问题单】业务建模“多表单模式”下使用前端值计算控件后启用打印机制保存报错
					$("#TABLE_DocList_SubForm").find("tr").each(function(){
						var subHtml = $(this).find("input[name$='fdDesignerHtml']");
						subHtml.val(Sys_Print_Base64Encodex(subHtml.val()));
						var fdPrintDesignerHtml = $(this).find("input[name$='fdPrintDesignerHtml']");
						fdPrintDesignerHtml.val(Sys_Print_Base64Encodex(fdPrintDesignerHtml.val()));
					});
				}
				//如果是更新配置操作，也就是单表单自定义模板时，修改action
				var operObj = document.getElementsByName("submitOper")[0];
				if(operObj && operObj.value == 'updateConfig'){
					var formObj = document.modelingAppModelForm;
					var url = Com_CopyParameter(formObj.action);
					url = Com_SetUrlParameter(url, "operType", "config");
					formObj.action = url;
				}
				Com_Submit(document.modelingAppModelForm, method);
			}
		</script>
	</template:replace>
</template:include>
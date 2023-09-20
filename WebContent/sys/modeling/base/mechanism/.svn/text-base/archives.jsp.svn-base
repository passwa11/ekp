<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
	if("false".equals(request.getAttribute("enableFlow"))){
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
		pageContext.setAttribute("templateService","modelingAppSimpleMainService");
	}else{
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
		pageContext.setAttribute("templateService","modelingAppModelMainService");
	}
%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/archives.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/modelingAppModel.do">
		<div class="mechanism_div">
			<html:hidden property="fdMechanismConfig"/>
			<div class="switch_submit_div" align="left">
				<div class="switch_div">
						${lfn:message('sys-modeling-base:modeling.model.mechanism.archives.set')}
					<ui:switch property="isArchivesEnable" checked="${mechanism['archives']['enable'] }"
							   onValueChange="changeArchivesEnable();"
							   id = "isArchivesEnableId" />
				</div>
				<div class="submit_div">
					<ui:button text="${ lfn:message('button.save') }" order="1" onclick="checkSubmit('updateArchives');"/>
				</div>
			</div>
			<div class="config_div">
				<div id="visible_archives_setting" style="display: none">
					<div class="archives_type_div">
							${lfn:message('sys-modeling-base:modeling.archives.chioce.mode')}
						<input type="radio" name="archives_type" value="0" onclick="changeArchivesType(this)" <c:if test="${mechanism['archives']['archivesType'] eq '0' || empty mechanism['archives']['archivesType']}"> checked="true"</c:if>>${lfn:message('sys-modeling-base:modeling.archives.common.path')}
						<input type="radio" name="archives_type" value="1" onclick="changeArchivesType(this)" <c:if test="${mechanism['archives']['archivesType'] eq '1'}"> checked="true"</c:if>>${lfn:message('sys-modeling-base:modeling.archives.user.path')}
					</div>
					<div class="archives_type_1">
						<i></i>
						<div class="archives_type_1_tips">${lfn:message('sys-modeling-base:modeling.archives.user.tips')}</div>
					</div>
					<div class="archives_type_0">
						<div class="archives_auto_div">
								${lfn:message('sys-modeling-base:modeling.archives.auto')}
							<ui:switch property="isArchivesAuto" checked="${mechanism['archives']['isArchivesAuto']}" />
						</div>
						<div class="archives_type_0_info">
							<i></i>
							<div class="archives_type_0_tips">${lfn:message('sys-modeling-base:modeling.archives.common.tips')}</div>
						</div>
					</div>
				</div>
			</div>
			<script>
				function changeArchivesEnable() {
					changeEnable('archives');
					// 再次开启，清空选项
					if($("input[name='isArchivesEnable']").val() == 'true') {

					}
				}

				LUI.ready(function() {
					changeEnable('archives');
					beforeSubmitFuncArr.push(archivesSave);
				});
			</script>
		</div>
		<div class="archives_template_setting" style="display: none">
			<kmss:ifModuleExist path="/km/archives/">
				<!-- 归档设置 -->
				<c:import url="/sys/modeling/base/mechanism/archives/archivesFileSetting_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelForm" />
					<c:param name="fdId" value="${modelingAppModelForm.fdId}" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="modelName" value="${modelName}" />
					<c:param name="templateService" value="${templateService}" />
					<c:param name="moduleUrl" value="sys/modeling" />
					<c:param name="mechanismMap" value="true" />
					<c:param name="enable" value="true"></c:param>
					<c:param name="modelId" value="${modelId}"></c:param>
					<c:param name="enableFlow" value="${modelingAppModelForm.fdEnableFlow}"></c:param>
				</c:import>
			</kmss:ifModuleExist>
		</div>
		</html:form>
		<script>
			var _validation = new $KMSSValidation();
			var _numberSet = false;
			var beforeSubmitFuncArr = new Array();

			/**
			 显示和隐藏机制配置
			 */
			function changeEnable(name){
				$("#visible_archives_setting").css("display","block");
				$(".visible_archives_setting").css("display","block");
				var upperName = name[0].toUpperCase()+name.substring(1);
				if($("input[name='is"+upperName+"Enable']").val() == 'true'){
					$("#visible_"+name+"_setting").show();
					$("input[name='archives_type']:checked").trigger("click");
				}else{
					$("#visible_"+name+"_setting").hide();
					$(".archives_template_setting").hide();
				}
			}

			function changeArchivesType(e){
				if($(e).val() == "0"){
					$(".archives_type_0").show();
					$(".archives_type_1").hide();
					$(".archives_template_setting").show();
				}else if($(e).val() == "1"){
					//自定义归档路径
					$(".archives_type_1").show();
					$(".archives_type_0").hide();
					$(".archives_template_setting").hide();
					//切换为自定义归档路径需要把归档模板的分类id及其他配置重置
					$("input[name*='categoryId']").val("");
					$("input[name*='categoryName']").val("");
					$("input[name*='fdFilePersonId']").val("");
					$("input[name*='fdFilePersonName']").val("");
					$("input[name*='fdFilePersonName']").closest("div").find(".mf_remove").trigger("click");
					$("input[name*='fdFilePersonFormula']").val("");
					$("input[name*='fdFilePersonFormulaName']").val("");
					if($("input[name*='fdSaveApproval']").val() != "false"){
						$("input[name*='fdSaveApproval']").closest("div").find("input[type='checkbox']").trigger("click");
					}
					if($("input[name*='fdPreFile']").val() != "false"){
						$("input[name*='fdPreFile']").closest("div").find("input[type='checkbox']").trigger("click");
					}
					if($("input[name*='fdSaveOldFile']").val() != "false"){
						$("input[name*='fdSaveOldFile']").closest("div").find("input[type='checkbox']").trigger("click");
					}
					$("select[name*='docSubjectMapping']").val("");
					$("select[name*='fdLibraryMapping']").val("");
					$("select[name*='fdVolumeYearMapping']").val("");
					$("select[name*='fdPeriodMapping']").val("");
					$("select[name*='fdUnitMapping']").val("");
					$("select[name*='fdKeeperMapping']").val("");
					$("select[name*='fdValidityDateMapping']").val("");
					$("select[name*='fdFileDateMapping']").val("");
					$("#file_extendFieldTB").html("");
				}
			}

			function archivesSave(cfg) {
				cfg["archives"] = {};
				if($("input[name='isArchivesEnable']").val() == 'true'){
					cfg["archives"]["enable"]="true";
				}else{
					cfg["archives"]["enable"]="false";
				}
				cfg["archives"]["archivesType"] = $("input[name='archives_type']:checked").val();
				if($("input[name='isArchivesAuto']").val() == 'true'){
					cfg["archives"]["isArchivesAuto"]="true";
				}else{
					cfg["archives"]["isArchivesAuto"]="false";
				}
			}

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

				Com_Submit(document.modelingAppModelForm, method);
			}

			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
				//提交前确认是否提示
				window.checkSubmit = function (method) {
					//是否开已开启归档机制,true-是
					var archivesEnable = '${mechanism['archives']['enable']}';
					//如果页面加载时归档机制已经开启，此时要关闭，则提示确认信息
					if($("input[name='isArchivesEnable']").val()=='false' && archivesEnable === 'true') {
						var content = {
							"html": "${lfn:message('sys-modeling-base:modeling.model.set.check.content')}",
							"title": "${lfn:message('sys-modeling-base:modeling.model.set.check.title.pre')}"
									+" ["+"${lfn:message('sys-modeling-base:table.mechanismArchives')}"+"] "
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
								$("input[name='isArchivesEnable']").val(true);
								$("#isArchivesEnableId .weui_switch").click();
								$("#visible_archives_setting").show();
								$("input[name='archives_type']:checked").trigger("click");
							}
						}
						dialog.confirm(content);
					}else {
						//默认提交-无需确认
						submitForm(method);
					}
				}
			});


		</script>
	</template:replace>
</template:include>
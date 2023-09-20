<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-rule" key="table.sysRuleSetDoc"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.save')}" order="1" onclick="save()" />
	    	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style>
			.main_data_div {
				padding-top: 10px;
			}
		</style>
		<script>Com_IncludeFile('dialog.js|formula.js|doclist.js|jquery.js|plugin.js|data.js');</script>
		<script type="text/javascript" src="<c:url value="/sys/rule/resources/js/common.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/sys/rule/resources/js/control.js"/>"></script>
		<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/ruleset_rules.js"/>'></script>
		<script type="text/javascript" src='<c:url value="/sys/rule/resources/js/ruleset_params.js"/>'></script>
	</template:replace>
	<template:replace name="content">
		<div style="width: 100%; margin: 10px auto;">
		<p class="txttitle">
			<bean:message bundle="sys-rule" key="table.sysRuleSetDoc"/>
		</p>
			<html:form action="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do" onsubmit="return checkForm();">
				<table class="tb_normal" width=95%>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdName"/>
						</td><td width=85% colspan="3">
							<html:hidden property="fdId"/>
						    <xform:text property="fdName" showStatus="edit" style="width:90%" subject="${lfn:message('sys-rule:sysRuleSetDoc.fdName') }"></xform:text>
						    <input type="hidden" name="fdVersion"/>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.sysRuleSetCate"/>
						</td><td width=85% colspan="3">
							<html:hidden property="sysRuleSetCateId"/>
							<xform:text property="sysRuleSetCateName"  style="width:80%;"></xform:text>
							<a href="#" onclick="Dialog_Tree(
								false,
								'sysRuleSetCateId',
								'sysRuleSetCateName',
								null,
								'sysRuleSetCateService&parentId=!{value}&item=fdName:fdId',
								'<bean:message bundle="sys-rule" key="table.sysRuleSetCate"/>');">
								<bean:message key="dialog.selectOrg"/>
							</a>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdOrder"/>
						</td><td width=85% colspan="3">
							<xform:text property="fdOrder1" showStatus="edit" style="width:90%" validators="digits"></xform:text>
<%--						    <input type="text" style="width:90%" name="fdOrder" class="inputsgl" oninput="value=value.replace(/[^\d]/g,'')" value="${sysRuleSetDocForm.fdOrder }"/>--%>
						</td>
					</tr>
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdDesc"/>
						</td>
						<td width=85% colspan="3">
							<xform:textarea showStatus="edit" property="fdDesc" style="width:90%"></xform:textarea>
						</td>
					</tr>
					<!-- 参数设置 -->
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-rule" key="sysRuleSetParam.setting"/>
						</td>
						<td width=85%>
							<c:import url="/sys/rule/sys_ruleset_param/simple_edit.jsp" charEncoding="UTF-8">
							</c:import>
						</td>
					</tr>
					<!-- 规则设置 -->
					<tr>
						<td width=15% class="td_normal_title">
						    <bean:message bundle="sys-rule" key="sysRuleSetRule.setting"/>
						</td>
						<td width=85%>
							<c:import url="/sys/rule/sys_ruleset_rule/simple_edit.jsp" charEncoding="UTF-8">
							</c:import>
						</td>
					</tr>
					<!-- 结果取向 -->
					<tr>
						<td width=15% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdMode"/>
						</td><td width=85% colspan="3">
						    <sunbor:enums property="fdMode" enumsType="sys_ruleset_mode" elementType="radio"/>
						</td>
					</tr>
					<!-- 异常或者不匹配时默认值设置 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting"/>
						</td>
					    <td width=30% colspan="1">
							<xform:select property="defaultValType" showStatus="edit" style="width:20%;" onValueChange="switchDefaultValType">
								<xform:enumsDataSource enumsType="sys_rule_return_type" />
							</xform:select>&nbsp;&nbsp;
							<span id="defaultValueArea">
							
							</span>
							<div style="color: #9e9e9e;"><bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting.describe"/></div>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="color: #FF0000"><bean:message key="sysRuleSetDoc.tip.1" bundle="sys-rule"/></td>
					</tr>
				</table>
			</html:form>
		</div>
		
		<script language="JavaScript">
			Com_IncludeFile("data.js");
			$KMSSValidation(document.forms['sysRuleSetDocForm']);
			if(window.showModalDialog){
				dialogObject = window.dialogArguments;
			}else{
				dialogObject = opener.Com_Parameter.Dialog;
			}
			var maps=[];
			var params = [];
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				//初始化
				Com_AddEventListener(window,"load",function(){
					var unid = Data_GetRadomId();
					$("input[name='fdId']").val(unid);
					$("table.tempTB").css("width","100%");
					$("table.tempTB").css("min-width","");
					$('input[name="sysRuleSetCateName"]').attr("readonly","readonly");/*设置只读*/
				})
				//保存
				window.save = function(){
					//提交
					Com_Submit(document.sysRuleSetDocForm, 'save');
					if(Com_Parameter.isSubmit){
						//处理返回值
						var rtnVal = {};
						rtnVal.id = $("input[name='fdId']").val();//规则集id
						rtnVal.name = $("input[name='fdName']").val();//规则集名称
						var trObjs = $("#paramSetting").find("tr");
						for(var i=1; i<trObjs.length; i++){
							var trObj = trObjs[i];
							var paramId = $(trObj).find("[name^='sysRuleSetParams'][name$='fdId']").eq(0).val();
							var paramName = $(trObj).find("[name^='sysRuleSetParams'][name$='fdName']").eq(0).val();
							var paramType = $(trObj).find("[name^='sysRuleSetParams'][name$='fdType']").eq(0).val();
							var isMulti = $(trObj).find("[name^='sysRuleSetParams'][name$='isMulti']").eq(0).val();
							var fieldId = $(trObj).find("[name^='fields']").eq(0).val();
							var mapObj = {
									paramId:paramId,
									fieldId:fieldId
							}
							var paramObj = {
									paramId:paramId,
									paramName:paramName,
									paramType:paramType,
									isMulti:isMulti
							}
							maps.push(mapObj);
							params.push(paramObj);
						}
						rtnVal.maps = maps;//规则集参数映射
						rtnVal.params = params;//规则集参数
						if(dialogObject){
							dialogObject.rtnData = [rtnVal];
							dialogObject.AfterShow();
						}
					}
				}
				// 表单检查
				window.checkForm = function(){
					//规则检查
					var paramsLen = $("#ruleSetting").find("tr").length - 1;
					if(paramsLen < 1){
						dialog.alert('<bean:message bundle="sys-rule" key="sysRuleSetRule.not.empty" />');
						return false;
					}
					
					//检查规则条件和结果为必填
					var fdConditionObjs = $("[name^='sysRuleSetRules'][name$='fdCondition']");
					var fdResultObjs = $("[name^='sysRuleSetRules'][name$='fdResult']");
					for(var j=0; j<fdConditionObjs.length; j++){
						var fdCondition = $(fdConditionObjs[j]).val();
						var fdResult = $(fdResultObjs[j]).val();
						if(!fdCondition || fdCondition == ""){
							dialog.alert('<bean:message bundle="sys-rule" key="sysRuleSetRule.condition.not.empty" />');
							return false;
						}
						if(!fdResult || fdResult == ""){
							dialog.alert('<bean:message bundle="sys-rule" key="sysRuleSetRule.result.not.empty" />');
							return false;
						}
					}
					
					//校验规则是否有新参数增加
					var fdVersion = $("[name='fdVersion']").val();
					if(!fdVersion || fdVersion == ""){
						fdVersion = 1;
						$("[name='fdVersion']").val(1);
					}
					var newParamIds = ruleSetParam.newParamIds;
					for(var i=0; i<newParamIds.length; i++){
						var paramId = newParamIds[i];
						var fdContentObjs = $("[name^='sysRuleSetRules'][name$='fdContent']");
						for(var j=0; j<fdContentObjs.length; j++){
							var fdContent = $(fdContentObjs[j]).val();
							if(fdContent.indexOf(paramId) != -1){
								var msg = Data_GetResourceString("sys-rule:sysRuleSetDoc.validate.1");
								var result = window.confirm(msg);
								if(result){
									//更新版本号
									fdVersion = parseInt(fdVersion) + 1;
									$("[name='fdVersion']").val(fdVersion);
								}else{
									return false;
								}
							}
						}
						var fdResultObjs = $("[name^='sysRuleSetRules'][name$='fdResult']");
						for(var j=0; j<fdResultObjs.length; j++){
							var fdResult = $(fdResultObjs[j]).val();
							if(fdResult.indexOf(paramId) != -1){
								var msg = Data_GetResourceString("sys-rule:sysRuleSetDoc.validate.1");
								var result = window.confirm(msg);
								if(result){
									//更新版本号
									fdVersion = parseInt(fdVersion) + 1;
									$("[name='fdVersion']").val(fdVersion);
								}else{
									return false;
								}
							}
						}
					}
					
					//更新规则对应的序号和关联的参数
					var params = ruleSetParam.getAllParams();
					var len = $("table#ruleSetting").find("tr").length - 1;
					for(var i=0; i<len; i++){
						$("input[name='sysRuleSetRules["+i+"].fdOrder']").val(i+1);
						var fdCondition = $("input[name='sysRuleSetRules["+i+"].fdCondition']").val();
						var fdResult = $("input[name='sysRuleSetRules["+i+"].fdResult']").val();
						var paramIds = "";
						var paramNames = "";
						for(var j=0; j<params.length; j++){
							var param = params[j];
							if(fdCondition.indexOf(param.paramId) != -1 || fdResult.indexOf(param.paramId) != -1){
								if(paramIds.indexOf(param.paramId) == -1){
									paramIds += param.paramId+";";
									paramNames += param.paramName+";";
								}
							}
						}
						$("input[name='sysRuleSetRules["+i+"].sysRuleSetParamIds']").val(paramIds);
						$("input[name='sysRuleSetRules["+i+"].sysRuleSetParamNames']").val(paramNames);
					}
					
					return true;
				}
				
				//切换默认值类型
				window.switchDefaultValType = function(value, obj){
					//删除必填的内容
					$("#defaultValueArea").parents("td").eq(0).find("div.validation-advice").remove();
					if(value == ""){
						$("#defaultValueArea").empty();
						//默认插入一个为空的默认值input
						$("#defaultValueArea").append("<input type='hidden' name='defaultDisVal' value=''/>");
						return;
					}
					//构造源对象
					var srcObj = {
						'mode':null,
						'rtnType':value,
						'isMulti':"1",
						'value':null,
						'idField':"defaultVal",
						'nameField':"defaultDisVal"
					}
					var parent = document.getElementById("defaultValueArea");
					createControl(srcObj,parent);
				}
			});
		</script>
	</template:replace>
</template:include>

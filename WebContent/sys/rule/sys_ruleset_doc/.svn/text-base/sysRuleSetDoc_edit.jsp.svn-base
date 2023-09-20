<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="sys-rule" key="table.sysRuleSetDoc"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ sysRuleSetDocForm.method_GET == 'add' || sysRuleSetDocForm.method == 'add' }">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="Com_Submit(document.sysRuleSetDocForm, 'save');" />
					<ui:button text="${lfn:message('button.saveadd')}" order="2" onclick="Com_Submit(document.sysRuleSetDocForm, 'saveadd');" />
				</c:when>
				<c:when test="${ sysRuleSetDocForm.method_GET == 'edit' || sysRuleSetDocForm.method == 'edit'}">
					<ui:button text="${lfn:message('button.save')}" order="1" onclick="update();" />
				</c:when>
			</c:choose>
	    	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
	</template:replace>
	<template:replace name="head">
		<style>
			.main_data_div {
				padding-top: 10px;
			}
			.rel_btn{
				color: #2574ad;
				border-bottom: 1px solid;
    			border-bottom: 1px solid transparent;
			}
			.rel_btn:hover{
				color: #123a6b;
    			border-bottom-color: #123a6b;
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
		<c:if test="${ sysRuleSetDocForm.method_GET == 'add' || sysRuleSetDocForm.method == 'add'}">
			<html:form action="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do" onsubmit="return checkFormAndUpdateData();">
				<table class="tb_normal" width=95%>
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdName"/>
						</td><td width=90% colspan="3">
						    <xform:text property="fdName" style="width:90%" subject="${lfn:message('sys-rule:sysRuleSetDoc.fdName') }"></xform:text>
						    <input type="hidden" name="fdVersion"/>
						</td>
					</tr>
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.sysRuleSetCate"/>
						</td><td width=90% colspan="3">
							<html:hidden property="sysRuleSetCateId"/>
							<xform:text property="sysRuleSetCateName"  style="width:80%;" required="true"></xform:text>
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
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdOrder"/>
						</td><td width=90% colspan="3">
						<xform:text property="fdOrder" style="width:90%" validators="digits"></xform:text>
<%--						    <input type="text" style="width:90%" name="fdOrder" class="inputsgl" oninput="value=value.replace(/[^\d]/g,'')" value="${sysRuleSetDocForm.fdOrder }"/>--%>
						</td>
					</tr>
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdIsAvailable"/>
						</td><td width=90% colspan="3">
						    <sunbor:enums property="fdIsAvailable" enumsType="sys_rule_available" elementType="radio"/>
						</td>
					</tr>
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdDesc"/>
						</td>
						<td width=90% colspan="3">
							<xform:textarea property="fdDesc" style="width:90%"></xform:textarea>
						</td>
					</tr>

					<!-- 参数设置 -->
					<tr>
						<td width=10% class="td_normal_title">
						    <bean:message bundle="sys-rule" key="sysRuleSetParam.setting"/>
						</td>
						<td width=90%>
							<c:import url="/sys/rule/sys_ruleset_param/edit.jsp" charEncoding="UTF-8">
							</c:import>
						</td>
					</tr>
					<!-- 规则设置 -->
					<tr>
						<td width=10% class="td_normal_title">
						    <bean:message bundle="sys-rule" key="sysRuleSetRule.setting"/>
						</td>
						<td width=90%>
							<c:import url="/sys/rule/sys_ruleset_rule/edit.jsp" charEncoding="UTF-8">
							</c:import>
							<div style="color: #9e9e9e;">${lfn:message("sys-rule:rule.tip.for.iassister") }</div>
						</td>
					</tr>
					<!-- 结果取向 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdMode"/>
						</td><td width=90% colspan="3">
						    <sunbor:enums property="fdMode" enumsType="sys_ruleset_mode" elementType="radio"/>
						</td>
					</tr>
					<!-- 异常或者不匹配时默认值设置 -->
					<tr>
						<td width=10% class="td_normal_title">
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting"/>
						</td>
					    <td width=30% colspan="1">
							<xform:select property="defaultValType" style="width:20%;" onValueChange="switchDefaultValType">
								<xform:enumsDataSource enumsType="sys_rule_return_type" />
							</xform:select>&nbsp;&nbsp;
							<span id="defaultValueArea">

							</span>
							<div style="color: #9e9e9e;"><bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting.describe"/></div>
						</td>
					</tr>
					<!-- 可使用者 -->
					<tr>
						<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authReaders"/></td>
						<td  width=90% colspan="3"><html:hidden property="authReaderIds" /> <html:textarea
							property="authReaderNames" style="width:90%" readonly="true" /> <a
							href="#"
							onclick="Dialog_Address(true, 'authReaderIds','authReaderNames', ';',null);"><bean:message
							key="dialog.selectOther" /></a><br>
						<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
						<c:set var="formName" value="sysRuleSetDocForm" scope="request"/>
						    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
						        <!-- （为空则本组织人员可使用） -->
						        <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.outter"  arg0="${ecoName}"  />
						    <% } else { %>
						        <!-- （为空则所有内部人员可使用） -->
						        <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.inner" />
						    <% } %>
						<% } else { %>
						    <!-- （为空则所有人可使用） -->
						    <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.all" />
						<% } %>
					   </td>
					</tr>
					<!-- 可维护者 -->
					<tr>
						<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authEditors"/></td>
						<td width=90% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
							property="authEditorNames" style="width:90%" readonly="true" /> <a
							href="#"
							onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
							key="dialog.selectOther" /></a><br>
							<bean:message bundle="sys-rule" key="sysRuleSetDoc.authEditors.describe"/>
						</td>
					</tr>
				</table>
			</html:form>
		</c:if>
		<c:if test="${sysRuleSetDocForm.method_GET == 'edit' || sysRuleSetDocForm.method == 'edit'}">
		<ui:tabpanel>
			<ui:content title="${lfn:message('sys-rule:sysRuleSetDoc.tab.title01') }">
				<html:form action="/sys/rule/sys_ruleset_doc/sysRuleSetDoc.do" onsubmit="return checkFormAndUpdateData();">
					<table class="tb_normal" width=95%>
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdName"/>
							</td><td width=90% colspan="3">
							    <xform:text property="fdName" style="width:90%"></xform:text>
							    <input type="hidden" name="fdVersion" value="${sysRuleSetDocForm.fdVersion }"/>
							</td>
						</tr>
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.sysRuleSetCate"/>
							</td><td width=90% colspan="3">
								<html:hidden property="sysRuleSetCateId"/>
								<xform:text property="sysRuleSetCateName"  style="width:80%;" required="true" ></xform:text>
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
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdOrder"/>
							</td><td width=90% colspan="3">
								<xform:text property="fdOrder" style="width:90%" validators="digits"></xform:text>
<%--							    <input type="text" style="width:90%" name="fdOrder" class="inputsgl" oninput="value=value.replace(/[^\d]/g,'')" value="${sysRuleSetDocForm.fdOrder }"/>--%>
							</td>
						</tr>
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdIsAvailable"/>
							</td><td width=90% colspan="3">
							    <sunbor:enums property="fdIsAvailable" value="${sysRuleSetDocForm.fdIsAvailable }"  enumsType="sys_rule_available" elementType="radio"/>
							</td>
						</tr>
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdDesc"/>
							</td>
							<td width=90% colspan="3">
								<xform:textarea property="fdDesc" style="width:90%"></xform:textarea>
							</td>
						</tr>

						<!-- 参数设置 -->
						<tr>
							<td width=10% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetParam.setting"/>
							</td>
							<td width=90%>
								<c:import url="/sys/rule/sys_ruleset_param/edit.jsp" charEncoding="UTF-8">
								</c:import>
							</td>
						</tr>
						<!-- 规则设置 -->
						<tr>
							<td width=10% class="td_normal_title">
							    <bean:message bundle="sys-rule" key="sysRuleSetRule.setting"/>
							</td>
							<td width=90%>
								<c:import url="/sys/rule/sys_ruleset_rule/edit.jsp" charEncoding="UTF-8">
								</c:import>
							</td>
						</tr>
						<!-- 结果取向 -->
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.fdMode"/>
							</td><td width=90% colspan="3">
							    <sunbor:enums property="fdMode" value="${sysRuleSetDocForm.fdMode}" enumsType="sys_ruleset_mode" elementType="radio"/>
							</td>
						</tr>
						<!-- 异常或者不匹配时默认值设置 -->
						<tr>
							<td width=10% class="td_normal_title">
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting"/>
							</td>
						    <td width=30% colspan="1">
								<xform:select property="defaultValType" style="width:20%;" onValueChange="switchDefaultValType">
									<xform:enumsDataSource enumsType="sys_rule_return_type" />
								</xform:select>&nbsp;&nbsp;
								<span id="defaultValueArea">

								</span>
								<div style="color: #9e9e9e;"><bean:message bundle="sys-rule" key="sysRuleSetDoc.defaultValSetting.describe"/></div>
							</td>
						</tr>
						<!-- 可使用者 -->
						<tr>
							<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authReaders"/></td>
							<td  width=90% colspan="3"><html:hidden property="authReaderIds" /> <html:textarea
								property="authReaderNames" style="width:90%" readonly="true" /> <a
								href="#"
								onclick="Dialog_Address(true, 'authReaderIds','authReaderNames', ';',null);"><bean:message
								key="dialog.selectOther" /></a><br>
								<%-- <bean:message bundle="sys-rule" key="sysRuleSetDoc.authReaders.describe"/> --%>
								<% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
						<c:set var="formName" value="sysRuleSetDocForm" scope="request"/>
						    <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
						        <!-- （为空则本组织人员可使用） -->
						        <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.outter"  arg0="${ecoName}"  />
						    <% } else { %>
						        <!-- （为空则所有内部人员可使用） -->
						        <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.inner" />
						    <% } %>
						<% } else { %>
						    <!-- （为空则所有人可使用） -->
						    <bean:message  bundle="sys-rule" key="sysRuleSetDoc.authReaders.all" />
						<% } %>
						   </td>
						</tr>
						<!-- 可维护者 -->
						<tr>
							<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetDoc.authEditors"/></td>
							<td width=90% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
								property="authEditorNames" style="width:90%" readonly="true" /> <a
								href="#"
								onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
								key="dialog.selectOther" /></a><br>
								<bean:message bundle="sys-rule" key="sysRuleSetDoc.authEditors.describe"/>
							</td>
						</tr>
					</table>
				</html:form>
			</ui:content>
			<ui:content title="${lfn:message('sys-rule:sysRuleSetDoc.tab.title02') }">
					<table class="tb_normal" width=95%>
						<tr>
							<td width=100% colspan="2">
								<list:listview id="listview">
									<ui:source type="AjaxJson">
										{url:'/sys/rule/sys_ruleset_temp/sysRuleTemplate.do?method=getRelationData&sysRuleSetDocId=${sysRuleSetDocForm.fdId }'}
									</ui:source>
									<list:colTable isDefault="false"
										layout="sys.ui.listview.columntable"
										name="columntable">
										<list:col-serial></list:col-serial>
										<list:col-auto props=""></list:col-auto>
									</list:colTable>
								</list:listview>
								<list:paging></list:paging>
							</td>
						</tr>
					</table>
				</ui:content>
		</ui:tabpanel>
		</c:if>
		</div>

		<script language="JavaScript">
			$KMSSValidation(document.forms['sysRuleSetDocForm']);
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				//初始化
				Com_AddEventListener(window,"load",function(){
					$("#ruleSetting").on("table-move",function(evt,rowIndex,tagIndex){
						$("[name='sysRuleSetRules["+(rowIndex-1)+"].fdOrder']").val(rowIndex);
						$("[name='sysRuleSetRules["+(tagIndex-1)+"].fdOrder']").val(tagIndex);
					})
					var method = '${sysRuleSetDocForm.method_GET}';
					if(!method || method == 'null' || method=='undefined'){
						method = '${sysRuleSetDocForm.method}'
					}
					$('input[name="sysRuleSetCateName"]').attr("readonly","readonly");/*设置只读*/
					var fdId = '${sysRuleSetDocForm.fdId}';
					var defaultValType = '${sysRuleSetDocForm.defaultValType}';
					var defaultVal = '${sysRuleSetDocForm.defaultVal}';
					var defaultDisVal = '${sysRuleSetDocForm.defaultDisVal}';
					if(method == "edit"){
						if(defaultValType && defaultValType != ""){
							//初始化默认值
							var defaultValObj = {};
							defaultValObj["defaultVal"] = defaultVal;
							defaultValObj["defaultDisVal"] = defaultDisVal;
							//构造源对象
							var srcObj = {
								'mode':null,
								'rtnType':defaultValType,
								'isMulti':"1",
								'value':defaultValObj,
								'idField':"defaultVal",
								'nameField':"defaultDisVal"
							}
							var parent = document.getElementById("defaultValueArea");
							createControl(srcObj,parent);
						}
						//规则初始化
						ruleSetRule.init(fdId);
						//参数初始化
						ruleSetParam.init(fdId);
					}
				});

				// 表单检查
				window.checkFormAndUpdateData = function(){
					//类别校验
					var sysRuleSetCateName = $("input[name='sysRuleSetCateName']").val();
					if (!sysRuleSetCateName) {
						dialog.alert('<bean:message bundle="sys-rule" key="sysRuleSetDoc.category.validate" />');
						return false;
					}

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
						if(typeof fdCondition == "undefined" || !fdCondition || fdCondition == ""){
							dialog.alert('<bean:message bundle="sys-rule" key="sysRuleSetRule.condition.not.empty" />');
							return false;
						}
						var returnType = $(fdResultObjs[j]).parent().parent().find("[name$='returnType']").val();
						if(returnType != "None" && (typeof fdResult == "undefined" || !fdResult || fdResult == "")){
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
						var fdConditionObjs = $("[name^='sysRuleSetRules'][name$='fdCondition']");
						for(var j=0; j<fdConditionObjs.length; j++){
							var fdCondition = $(fdConditionObjs[j]).val();
							if(fdCondition.indexOf(paramId) != -1){
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

				//更新
				window.update = function(){

					Com_Submit(document.sysRuleSetDocForm, 'update');
				}

				//查看引用
				window.openRefDialog = function(modelId){
					var dialog = new KMSSDialog();
					dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_temp/sysRuleTemplate.do?method=getReferenceInfo&modelId="+modelId;
					dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
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

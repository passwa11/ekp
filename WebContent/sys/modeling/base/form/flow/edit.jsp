<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="content">
		<kmss:windowTitle moduleKey="sys-modeling-base:module.model.name" subjectKey="sys-modeling-base:modeling.flow.create" subject="${modelingAppFlowForm.fdName }" />
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${modelingAppFlowForm.method_GET=='edit'}">
				<ui:button text="${ lfn:message('button.update') }" order="1"  onclick="submitForm('update');"></ui:button>
			</c:if>
			<c:if test="${modelingAppFlowForm.method_GET=='add'}">
				<ui:button text="${ lfn:message('button.save') }" order="1"  onclick="submitForm('save');"></ui:button>
			</c:if>
		</ui:toolbar>
		<html:form action="/sys/modeling/base/modelingAppFlow.do">
			<p class="txttitle">
				${lfn:message('sys-modeling-base:modeling.flow.design')}
			</p>
			<table class="tb_normal" width="100%">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-modeling-base:modeling.flow.fdName')}
					</td>
					<td colspan="3">
						<xform:xtext property="fdName" required="true"></xform:xtext>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('sys-modeling-base:modeling.flow.fdValid')}
					</td>
					<td width="35%">
						<ui:switch property="fdValid" checked="true" enabledText="${lfn:message('sys-modeling-base:modeling.flow.fdValid.true')}" disabledText="${lfn:message('sys-modeling-base:modeling.flow.fdValid.false')}"></ui:switch>
					</td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-modeling-base:modeling.flow.fdIsMobileApprove')}
                    </td>
                    <td width="35%">
                        <sunbor:enums property="fdIsMobileApprove" enumsType="common_yesno" elementType="radio" />
                    </td>
                </tr>
            </table>
            <table class="tb_normal" width="100%">
				<!-- 流程 -->
				<c:import url="/sys/modeling/base/form/flow/lbpmTemplate_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppFlowForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="fdAppModelId" value="${modelingAppFlowForm.fdModelId}" />
					<c:param name="fdAppId" value="${appId}" />
				</c:import>
			</table>
			<html:hidden property="fdModelId"/>
		</html:form>
		<script>
			Com_IncludeFile("formula.js");
			
			$KMSSValidation();
			
			function submitForm(method){
				Com_Submit(document.modelingAppFlowForm,method);
			}
			
			function XForm_Util_UnitArray(array, sysArray, extArray) {
				<%-- // 合并 --%>
				array = array.concat(sysArray);
				if (extArray != null) {
					array = array.concat(extArray);
				}
				<%-- // 结果 --%>
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
				//console.log("${xformId}");
				extObj = extObj || [];
				for(var i = 0; i < extObj.length; i++){
					var tempObj = extObj[i];
					if("relevance" == tempObj.controlType && tempObj.name && tempObj.name.endsWith('_config')){
						tempObj.label = tempObj.label +'(配置)';
					}
				}
				return XForm_Util_UnitArray(obj, sysObj, extObj);
			}
			
			// 查询modelName的属性信息
			function _XForm_GetSysDictObj(modelName){
				return Formula_GetVarInfoByModelName(modelName);
			}
			
			// 查找自定义表单的数据字典
			function _XForm_GetTempExtDictObj(tempId) {
				return new KMSSData().AddBeanData("modelingFormDictTreeVarService&tempType=template&tempId="+tempId).GetHashMapArray();
			}
			
			//查找多表单
			function XForm_getSubFormInfo_modelingApp() {
				var formData = new KMSSData().AddBeanData("sysSubFormDataBean&method=getForms&fdModelId=${modelingAppFlowForm.fdModelId}").GetHashMapArray();
				formData = formData || [];
				var defaultId;
				for (var i = 0; i < formData.length; i++) {
					var formObj = formData[i];
					if ("true" === formObj["default"]){
						defaultId = formObj.id;
						if (formObj.name == "") {
							formObj.name = "${lfn:message('sys-modeling-base:modeling.form.DefaultTable')}";
						}
						//#170193  默认表单的id修改为default,与流程表单保持一致
						formObj.id='default';
					}
				}
				for (var i = 0; i < formData.length; i++) {
					var formObj = formData[i];
					if (defaultId && defaultId === formObj.pcFormId){
						formObj.defaultWebForm = true;
					}
				}
				return formData;
			}
			
			//获取表单模式
			function Form_getModeValue(){
				var modeValue = new KMSSData().AddBeanData("sysSubFormDataBean&method=getModeValue&formId=${xformId}").GetHashMapArray()[0].value;
				return modeValue;
			}
			
			//获取多表单打印模板
			function Print_getSubPrintInfo_modelingApp() {
				var subObj = [];
				//查询多表单打印模板的数据
				var datas = new KMSSData().AddBeanData("sysPrintTemplateService&modelId=${modelingAppFlowForm.fdModelId}&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel").GetHashMapArray();
				if(datas && datas.length > 0){
					var subformObj = {};
					subformObj.id='default'
					subformObj.name='<kmss:message key="sys-print:sysPrint.default_print" />';
					subObj.push(subformObj);
					for(var i=0; i<datas.length; i++){
						if("true" == datas[i].isDefault){//跳过默认的
							continue;
						}
						subformObj = {};
						subformObj.id=datas[i].fdId;
						subformObj.name=datas[i].fdName;
						subObj.push(subformObj);
					}
					
				}
				return subObj;
			}
	
			function Print_getSubPrintViewInfo_modelingApp() {
				var subObj = [];
				var datas = new KMSSData().AddBeanData("sysPrintTemplateService&modelId=${modelingAppFlowForm.fdModelId}&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel").GetHashMapArray();
				if(datas && datas.length > 0){
					var subformObj = {};
					subformObj.id='default'
					subformObj.name='<kmss:message key="sys-print:sysPrint.default_print" />';
					subObj.push(subformObj);
					for(var i=0; i<datas.length; i++){
						subformObj = {};
						subformObj.id=datas[i].fdId;
						subformObj.name=datas[i].fdName;
						subObj.push(subformObj);
					}
					
				}
				return subObj;
			}
			//获取打印模板的id
			function Print_getSubPrintIds(){
				var ids = [];
				var subObj = Print_getSubPrintInfo_modelingApp();
				for(var i=0; i<subObj.length; i++){
					ids.push(subObj[i].id);
				}
				return ids;
			}
		</script>
	</template:replace>
</template:include>
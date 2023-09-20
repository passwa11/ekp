<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script>
	Com_IncludeFile("dialog.js|doclist.js|formula.js");
	var dialogObject = null;
	if(window.showModalDialog) {
		dialogObject = window.dialogArguments;
		if (dialogObject == null) {
			dialogObject = parent.Com_Parameter.Dialog;
		}
	} else if(opener) {
		dialogObject = opener.Com_Parameter.Dialog;
	} else {
		if (dialogObject == null){
			dialogObject = parent.Com_Parameter.Dialog;
		}
	}
</script>
<template:include ref="default.dialog">
	<template:replace name="content">
		<html:form action="/sys/lbpmservice/support/lbpmDynamicSubFlow.do">
		<center>
			<textarea name="fdContent" style="display: none">
					${fdContent}
			</textarea>
			<input type="hidden" name="fdNodeGroupId" value="<c:out value='${fdNodeGroupId}'/>"/>
			<input type="hidden" name="fdCopyId" value="<c:out value='${fdCopyId}'/>"/>
			<input type="hidden" name="fdParamContent" value="<c:out value='${fdParamContent}'/>"/>
		<table class="tb_normal" width=95% id="TABLE_DocList_Params" align="center" style="table-layout:fixed;margin-bottom:50px;" frame=void>
			<tr>
				<td width="25%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.nodeGroup.param" /></td>
				<td width="25%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramType" /></td>
				<td width="80%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramValue" /></td>
			</tr>
			<tr KMSS_IsReferRow="1" style="display:none" class="content">
				<td align="center">
					<input type="hidden" name="fdParamData">
					<input type="hidden" name="fdParamValue">
					<input type="text" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdParamName">
				</td>
				<td align="center">
					<input type="hidden" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdIsMuti"/>
					<input type="hidden" name="fdType"/>
					<input type="text" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdParamType"/>
				</td>
				<td>
					<label><input type="radio" name="wf_startBranchHandler[!{index}]" value="org" checked onclick="switchStartBranchHandlerSelectType(this);"><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
					<label><input type="radio" name="wf_startBranchHandler[!{index}]" value="formula" onclick="switchStartBranchHandlerSelectType(this);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
					<input type="hidden" name="wf_startBranchHandlerId" orgattr="startBranchHandlerId:startBranchHandlerName">
					<input name="wf_startBranchHandlerName" class="inputsgl" readonly style="width:80% ">
					<span name="SPAN_SelectType1">
						<a href="#" onclick="clickAddress(this)"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
					<span name="SPAN_SelectType2" style="display:none ">
						<a href="#" onclick="selectByFormula(this);"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
						</span>
				</td>
			</tr>
		</table>
		<div class="DIV_EditButtons" style="height:30px;">
			<ui:button text="${lfn:message('button.save') }" order="1" onclick="save();"  style="width:77px;">
			</ui:button>
			<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;padding-left:10px">
			</ui:button>
		</div>
		</center>
		</html:form>
		<%@ include file="/resource/jsp/edit_down.jsp"%>
			<script>
				//标识位置的全局变量
				var position = "";
				DocList_Info.push("TABLE_DocList_Params");
				Com_AddEventListener(window, "load", function() {
					var contents = $("textarea[name='fdContent']").val();
					var content = WorkFlow_LoadXMLData(contents);
					var otherParams = "";
					if(content.otherParams != null && content.otherParams != ""){
						otherParams = JSON.parse(content.otherParams);
					}
					var fdCopyId = $("input[name='fdCopyId']").val();
					if(otherParams){

					}
					//处理未提交页面直接进行的复制操作然后打开显示配置好的参数内容
					var dialogContent = dialogObject.paramCon;
					if(dialogContent !=""){
						$("input[name='fdParamContent']").val(dialogContent);
					}
					//编辑状态下获取后台传来的参数配置的具体信息，然后循环加载到动态列表中的对应列
					var fdParamContent = $("input[name='fdParamContent']").val();
					if("undefined" != fdParamContent){
						fdParamContent = JSON.parse(fdParamContent);
						for(var i=0;i<fdParamContent.length;i++){
							var param = fdParamContent[i];
							var fieldValues = new Object();
							//防止复制的时候id重复
							if(undefined != fdCopyId && "undefined" != fdCopyId){
								var paramValue = param.fdParamValue.split("_");
								fieldValues["fdParamValue"]="nodeGroup_"+fdCopyId+"_"+paramValue[2];
							}else{
								fieldValues["fdParamValue"]=param.fdParamValue;
							}
							fieldValues["fdParamData"]=param.fdParamData;
							fieldValues["fdParamName"]=param.fdParamName;
							fieldValues["fdParamType"]=param.fdParamType;
							fieldValues["fdIsMuti"]=param.fdIsMuti;
							fieldValues["wf_startBranchHandlerId"]=param.fdFactParamValue;
							fieldValues["wf_startBranchHandlerName"]=param.fdFactParamName;
							var tr = DocList_AddRow("TABLE_DocList_Params",null,fieldValues);
							//编辑状态下让单选选中对应的选项
							var obj = $("input[name^='wf_startBranchHandler["+i+"]']");
							if(param.orgOrFormula == "org" && param.fdType.indexOf("com.") != -1){
								$(obj).eq(0).prop("checked","checked");
								//根据单选条件控制地址本和公式定义器弹窗显示和隐藏
								$(obj).eq(0).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = '';
								$(obj).eq(0).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = 'none';
							}else if(param.orgOrFormula == "formula" && param.fdType.indexOf("com.") != -1){
								$(obj).eq(1).prop("checked","checked");
								//根据单选条件控制地址本和公式定义器弹窗显示和隐藏
								$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = 'none';
								$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = '';
							}else{
								if(param.fdParamName == '人员' || param.fdParamName == '岗位' || param.fdParamName == '群组' || param.fdParamName == '部门' || param.fdParamName == '机构' || param.fdParamName == '所有组织架构'){
									//根据单选条件控制地址本和公式定义器弹窗显示和隐藏
									if(param.orgOrFormula == "formula"){
										$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = 'none';
										$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = '';
										$(obj).eq(1).prop("checked","checked");
									}else{
										$(obj).eq(0).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = '';
										$(obj).eq(0).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = 'none';
										$(obj).eq(0).prop("checked","checked");
									}

								}else{
									//控制非组织架构参数类型选项显示，只显示公式定义器
									$(obj).eq(0).parent()[0].style.display='none';
									//根据单选条件控制地址本和公式定义器弹窗显示和隐藏
									$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = 'none';
									$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = '';
									$(obj).eq(1).prop("checked","checked");
								}
							}
						}
					}else{
						//节点集参数配置为空时走这里
						var fdNodeGroupId = $("input[name='fdNodeGroupId']").val();
						for(var i=0;i<otherParams.length;i++){
							var param = otherParams[i];
							var fieldValues = new Object();
							fieldValues["fdParamData"]=param.fdParamValue;
							//构造节点集配置参数,方便流程模板选择片段组取数
							// fdNodeGroupId:片段组节点集fdId,param.fdParamValue 原生节点集fdRefId
							fieldValues["fdParamValue"]="nodeGroup_"+fdNodeGroupId+"_"+param.fdParamValue;
							fieldValues["fdParamName"]=param.fdParamName;
							fieldValues["fdIsMuti"]=param.fdIsMulti;
							if(param.fdParamType == "ORG_TYPE_PERSON"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgPerson[]";
									fieldValues["fdParamType"]="人员(多值)";
								}else{
									fieldValues["fdParamType"]="人员";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgPerson";
								}

							}
							if(param.fdParamType == "Boolean"){
								fieldValues["fdParamType"]="布尔";
								fieldValues["fdType"]="Boolean";
							}
							if(param.fdParamType == "Double"){
								fieldValues["fdParamType"]="数字";
								fieldValues["fdType"]="Double";
							}
							if(param.fdParamType == "BigDecimal"){
								fieldValues["fdParamType"]="数字(Big Decimal)";
								fieldValues["fdType"]="BigDecimal";
							}
							if(param.fdParamType == "BigDecimal_Money"){
								fieldValues["fdParamType"]="金额";
								fieldValues["fdType"]="BigDecimal";
							}
							if(param.fdParamType == "String"){
								fieldValues["fdParamType"]="字符";
								fieldValues["fdType"]="String";
							}
							if(param.fdParamType == "Date"){
								fieldValues["fdParamType"]="日期";
								fieldValues["fdType"]="Date";
							}
							if(param.fdParamType == "Time"){
								fieldValues["fdParamType"]="时间";
								fieldValues["fdType"]="Time";
							}
							if(param.fdParamType == "DateTime"){
								fieldValues["fdParamType"]="日期时间";
								fieldValues["fdType"]="DateTime";
							}
							if(param.fdParamType == "ORG_TYPE_ORG"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdParamType"]="机构(多值)";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement[]";
								}else{
									fieldValues["fdParamType"]="机构";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement";
								}
							}
							if(param.fdParamType == "ORG_TYPE_DEPT"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdParamType"]="部门(多值)";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement[]";
								}else{
									fieldValues["fdParamType"]="部门";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement";
								}
							}
							if(param.fdParamType == "ORG_TYPE_POST"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdParamType"]="岗位(多值)";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement[]";
								}else{
									fieldValues["fdParamType"]="岗位";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement";
								}
							}
							if(param.fdParamType == "ORG_TYPE_GROUP"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdParamType"]="群组(多值)";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement[]";
								}else{
									fieldValues["fdParamType"]="群组";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement";
								}
							}
							if(param.fdParamType == "ORG_TYPE_ALL"){
								if(param.fdIsMulti == "true"){
									fieldValues["fdParamType"]="所有组织架构(多值)";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement[]";
								}else{
									fieldValues["fdParamType"]="所有组织架构";
									fieldValues["fdType"]="com.landray.kmss.sys.organization.model.SysOrgElement";
								}
							}
							var tr = DocList_AddRow("TABLE_DocList_Params",null,fieldValues);
							var obj = $("input[name^='wf_startBranchHandler["+i+"]']");
							if(param.fdParamType.indexOf("ORG_TYPE_") == -1){
								$(obj).eq(0).parent()[0].style.display='none';
								$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = 'none';
								$(obj).eq(1).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = '';
								$(obj).eq(1).prop("checked","checked");
							}
						}
					}
				});

				//参数值选择方式
				function switchStartBranchHandlerSelectType(obj){
					var result = $(obj).val();
					var startBranchHandlerId = $(obj).parents("tr").eq(0).find("input[name='wf_startBranchHandlerId']")[0];
					var startBranchHandlerName = $(obj).parents("tr").eq(0).find("input[name='wf_startBranchHandlerName']")[0];
					if(result == "org"){
						$(obj).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = '';
						$(obj).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = 'none';
						$(obj).prop("checked", "checked");
					}else{
						$(obj).parents("tr").eq(0).find("span[name='SPAN_SelectType1']")[0].style.display = 'none';
						$(obj).parents("tr").eq(0).find("span[name='SPAN_SelectType2']")[0].style.display = '';
						$(obj).prop("checked", "checked");
					}
					$(startBranchHandlerId).val("");
					$(startBranchHandlerName).val("");
				}
				//公式定义器方式
				function selectByFormula(res){
					position = res;
					var idField = $(res).parents("tr").eq(0).find("input[name='wf_startBranchHandlerId']")[0]; //节点集选择后的id
					var nameField = $(res).parents("tr").eq(0).find("input[name='wf_startBranchHandlerName']")[0]; //节点集选择后的name
					var fdType = $(res).parents("tr").eq(0).find("input[name='fdType']")[0]; //当前行的参数类型
					var paramList = $dialog.___params.paramContent; //片段组参数页面传过来的的变量值

					Formula_Dialog(idField,
							nameField,
							paramList,
							$(fdType).val(),
							setAfterShow,
							"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
							null);
				}
				function clickAddress(res){
					position = res;
					var idField = $(res).parents("tr").eq(0).find("input[name='wf_startBranchHandlerId']")[0]; //节点集选择后的id
					var nameField = $(res).parents("tr").eq(0).find("input[name='wf_startBranchHandlerName']")[0]; //节点集选择后的name
					var paramType = $(res).parents("tr").eq(0).find("input[name='fdParamType']")[0];
					paramType = $(paramType).val();
					var flag = false;
					var orgType = ORG_TYPE_PERSON;
					if(paramType == "人员(多值)"){
						flag = true;
					}
					if(paramType == "机构(多值)"){
						flag = true;
						orgType = ORG_TYPE_ORG;
					}
					if(paramType == "部门(多值)"){
						flag = true;
						orgType = ORG_TYPE_DEPT;
					}
					if(paramType == "岗位(多值)"){
						flag = true;
						orgType = ORG_TYPE_POST;
					}
					if(paramType == "群组(多值)"){
						flag = true;
						orgType = ORG_TYPE_GROUP;
					}
					if(paramType == "所有组织架构(多值)"){
						flag = true;
						orgType = ORG_TYPE_ALL;
					}

					if(paramType == "机构"){
						orgType = ORG_TYPE_ORG;
					}
					if(paramType == "部门"){
						orgType = ORG_TYPE_DEPT;
					}
					if(paramType == "岗位"){
						orgType = ORG_TYPE_POST;
					}
					if(paramType == "群组"){
						orgType = ORG_TYPE_GROUP;
					}
					if(paramType == "所有组织架构"){
						orgType = ORG_TYPE_ALL;
					}
					Dialog_Address(flag, idField, nameField, null, orgType,setAfterShow);
				}
				//#156477 重新聚焦方法，防止多数据出现滚动条，选择后回到顶部
				function setAfterShow(rtnVal){
					$(position).parents("tr").eq(0).find("input[name='wf_startBranchHandlerName']")[0].focus();
				}
				//节点集参数配置弹窗确定方法
				function save(){
					var content = [];
					$("#TABLE_DocList_Params .content").each(function(index,item){
						var fdParamData = $(this).find("[name='fdParamData']").val();
						var fdParamValue = $(this).find("[name='fdParamValue']").val();
						var fdParamName = $(this).find("[name='fdParamName']").val();
						var fdParamType = $(this).find("[name='fdParamType']").val();
						var fdType = $(this).find("[name='fdType']").val();
						var fdIsMuti = $(this).find("[name='fdIsMuti']").val();
						var orgOrFormula = $(this).find("[name='wf_startBranchHandler["+index+"]']:checked").val();
						var fdFactParamValue = $(this).find("[name='wf_startBranchHandlerId']").val();
						var fdFactParamName = $(this).find("[name='wf_startBranchHandlerName']").val();
						content.push({"fdParamData":fdParamData,"fdParamValue":fdParamValue,"fdParamName":fdParamName,"fdType":fdType,"fdParamType":fdParamType,"fdIsMuti":fdIsMuti,"fdFactParamName":fdFactParamName,"fdFactParamValue":fdFactParamValue,"orgOrFormula":orgOrFormula});
					});
					var paramsConfig = JSON.stringify(content);
					$dialog.hide({
						paramContents : paramsConfig
					});
				}
				function closeDialog(){
					if(!confirm('<bean:message key="message.closeWindow"/>')){
						return;
					}
					Com_CloseWindow();
				}
			</script>
	</template:replace>
</template:include>
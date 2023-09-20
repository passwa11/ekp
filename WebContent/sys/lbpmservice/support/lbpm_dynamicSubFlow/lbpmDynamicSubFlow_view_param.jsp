<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script src="<c:url value="/sys/lbpm/flowchart/js/workflow.js"/>"></script>
<script>
	Com_IncludeFile("dialog.js|doclist.js|formula.js");
</script>
<template:include ref="default.dialog">
	<template:replace name="content">
	<center>
		<textarea name="fdContent" style="display: none">
				${fdContent}
		</textarea>
		<input type="hidden" name="fdParamContent" value="<c:out value='${fdParamContent}'/>"/>
	<table class="tb_normal" width=95% id="TABLE_DocList_Params" align="center" style="table-layout:fixed;margin-bottom:50px;" frame=void>
		<tr>
			<td width="25%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.nodeGroup.param" /></td>
			<td width="25%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramType" /></td>
			<td width="80%" align="center"><bean:message bundle="sys-lbpmservice-support" key="lbpmDynamicSubFlow.part.paramValue" /></td>
		</tr>
		<tr KMSS_IsReferRow="1" style="display:none" class="content">
			<td align="center">
				<input type="hidden" name="fdParamValue">
				<input type="text" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdParamName">
			</td>
			<td align="center">
				<input type="hidden" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdIsMuti"/>
				<input type="text" class="inputsgl" style="border: 0px;color: #0C0C0C;width: 80%;text-align: center" name="fdParamType"/>
			</td>
			<td>
				<label><input type="radio" name="wf_startBranchHandler[!{index}]" value="org" disabled><kmss:message key="FlowChartObject.Lang.Node.selectAddress" bundle="sys-lbpmservice" /></label>
				<label><input type="radio" name="wf_startBranchHandler[!{index}]" value="formula" disabled><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
				<input type="hidden" name="wf_startBranchHandlerId" orgattr="startBranchHandlerId:startBranchHandlerName">
				<input name="wf_startBranchHandlerName" class="inputsgl" readonly style="width:80% ">
				<span name="SPAN_SelectType1">
					<a readonly="true"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</span>
				<span name="SPAN_SelectType2" style="display:none ">
					<a readonly="true"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
					</span>
			</td>
		</tr>
	</table>
	<div class="DIV_ViewButtons" style="height:30px;">
		<ui:button text="${lfn:message('button.cancel') }" order="1" onclick="closeDialog();" style="width:77px">
		</ui:button>
	</div>
	</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
	<script>
		DocList_Info.push("TABLE_DocList_Params");
		Com_AddEventListener(window, "load", function() {
			var contents = $("textarea[name='fdContent']").val();
			var content = WorkFlow_LoadXMLData(contents);
			var otherParams = JSON.parse(content.otherParams);
			//编辑状态下获取后台传来的参数配置的具体信息，然后循环加载到动态列表中的对应列
			var fdParamContent = $("input[name='fdParamContent']").val();
			if("undefined" != fdParamContent){
				fdParamContent = JSON.parse(fdParamContent);
				for(var i=0;i<fdParamContent.length;i++){
					var param = fdParamContent[i];
					var fieldValues = new Object();
					fieldValues["fdParamValue"]=param.fdParamValue;
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
				for(var i=0;i<otherParams.length;i++){
					var param = otherParams[i];
					var fieldValues = new Object();
					fieldValues["fdParamValue"]=param.fdParamValue;
					fieldValues["fdParamName"]=param.fdParamName;
					fieldValues["fdIsMuti"]=param.fdIsMulti;
					if(param.fdParamType == "ORG_TYPE_PERSON"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="人员(多值)";
						}else{
							fieldValues["fdParamType"]="人员";
						}

					}
					if(param.fdParamType == "Boolean"){
						fieldValues["fdParamType"]="布尔";
					}
					if(param.fdParamType == "Double"){
						fieldValues["fdParamType"]="数字";
					}
					if(param.fdParamType == "BigDecimal"){
						fieldValues["fdParamType"]="数字(Big Decimal)";
					}
					if(param.fdParamType == "BigDecimal_Money"){
						fieldValues["fdParamType"]="金额";
					}
					if(param.fdParamType == "String"){
						fieldValues["fdParamType"]="字符";
					}
					if(param.fdParamType == "Date"){
						fieldValues["fdParamType"]="日期";
					}
					if(param.fdParamType == "Time"){
						fieldValues["fdParamType"]="时间";
					}
					if(param.fdParamType == "DateTime"){
						fieldValues["fdParamType"]="日期时间";
					}
					if(param.fdParamType == "ORG_TYPE_ORG"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="机构(多值)";
						}else{
							fieldValues["fdParamType"]="机构";
						}
					}
					if(param.fdParamType == "ORG_TYPE_DEPT"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="部门(多值)";
						}else{
							fieldValues["fdParamType"]="部门";
						}
					}
					if(param.fdParamType == "ORG_TYPE_POST"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="岗位(多值)";
						}else{
							fieldValues["fdParamType"]="岗位";
						}
					}
					if(param.fdParamType == "ORG_TYPE_GROUP"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="群组(多值)";
						}else{
							fieldValues["fdParamType"]="群组";
						}
					}
					if(param.fdParamType == "ORG_TYPE_ALL"){
						if(param.fdIsMulti == "true"){
							fieldValues["fdParamType"]="所有组织架构(多值)";
						}else{
							fieldValues["fdParamType"]="所有组织架构";
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
		function closeDialog(){
			Com_CloseWindow();
		}
	</script>
	</template:replace>
</template:include>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ dbEchartsJobForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('dbcenter-echarts:zhixing') }" onclick="executeRun();" />
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsJobForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/dbcenter/echarts/db_echarts_job/dbEchartsJob.do">
<script>
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
</script>
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="table.dbEchartsJob"/></p>

<center>
<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdName"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdEnable"/>
		</td><td width="35%">
			<xform:radio property="fdEnable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdExpression"/>
		</td><td width="35%">
			<xform:text property="fdExpression" style="width:85%" required="true" />
			<br>${ lfn:message('dbcenter-echarts:job_edit_hint_1') }
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdStartTime"/>
		</td><td width="35%">
			<xform:datetime property="fdStartTime" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdEndTime"/>
		</td><td width="35%">
			<xform:datetime property="fdEndTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdOperationList"/>
		</td><td width="85%" colspan="3">
			<xform:dialog propertyId="fdOperationListIds" propertyName="fdOperationListNames"
				dialogJs="Dialog_List(true, 'fdOperationListIds', 'fdOperationListNames', ';', 'dbEchartsOperationService');" style="width:98%" htmlElementProperties="a='123'"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('dbcenter-echarts:moshiqiehuan') }
		</td><td width="85%" colspan="3">
			<label><input name="editMode" onclick="changeMode();" value="configMode" type="radio" checked>${ lfn:message('dbcenter-echarts:peizhimoshi') }</label>
			<label><input name="editMode" onclick="changeMode();" value="codeMode" type="radio">${ lfn:message('dbcenter-echarts:daimamoshi') }</label>
		</td>
	</tr>
	<tbody id="configMode">
	<tr class="tr_normal_title">
		<td colspan="4" class="config_title">
			${ lfn:message('dbcenter-echarts:chuandicanshu') }
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/transfer.jsp">
				<c:param name="field" value="fdCode" />
			</c:import>
		</td>
	</tr>
	</tbody>
	<tbody id="codeMode" style="display:none;">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.fdCode"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdCode" style="width:98%; height:200px;" />
		</td>
	</tr>
	</tbody>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsJob.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
	</tr>
</table>
<br>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	var g_validator = $KMSSValidation();
	function changeMode(){
		var fields = document.getElementsByName("editMode");
		for(var i=0; i<fields.length; i++){
			if(fields[i].checked){
				LUI.$('#'+fields[i].value).show();
			}else{
				LUI.$('#'+fields[i].value).hide();
			}
		}
		if(fields[1].checked){
			updateCodeField();
		}else{
			updateFormField();
		}
	}
	function updateCodeField(){
		var data = {};
		dbecharts.read("fdCode", data);
		LUI.$('[name="fdCode"]').val(LUI.stringify(data));
	}
	function updateFormField(){
		var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
		var data = value==''?{}:LUI.toJSON(value);
		dbecharts.write("fdCode", data);
	}
	function submitForm(method){
		if(!g_validator.validate()){
			return false;
		}
		var fields = document.getElementsByName("editMode");
		if(fields[0].checked){
			updateCodeField();
		}
		dbecharts.disable(true);
		if(!Com_Submit(document.dbEchartsJobForm, method)){
			dbecharts.disable(false);
		}
	}
	function executeRun(){
		if(confirm("${ lfn:message('dbcenter-echarts:job_edit_hint_2') }")){
			Com_OpenWindow('dbEchartsJob.do?method=run&fdId=${JsParam.fdId}','_blank');
		}
	}
	dbecharts.init(updateFormField);
</script>
</html:form>

	</template:replace>
</template:include>
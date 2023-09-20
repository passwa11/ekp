<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ dbEchartsChartSetForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsChartSetForm.method_GET == 'add'  || dbEchartsChartSetForm.method_GET == 'clone' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do">
<script>
	Com_IncludeFile("doclist.js|dialog.js");
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	Com_IncludeFile("jquery.js|dialog.js");
</script>
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="table.dbEchartsChartSet"/></p>

<center>
<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.docSubject"/>
		</td><td width="85%" colspan="3">
			<xform:text property="docSubject" style="width:98%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('dbcenter-echarts:dbEcharts.echart.table.caregory') }
		</td>
		<td width="85%" colspan="3">
			<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory')}" propertyId="fdDbEchartsTemplateId" style="width:50%" propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog();">
			</xform:dialog>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.fdTheme"/>
		</td><td width="85%" colspan="3">
			<xform:select property="fdTheme" showPleaseSelect="false">
				<xform:simpleDataSource value="default"><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></xform:simpleDataSource>
				<c:forEach items="${themes}" var="theme">
					<xform:simpleDataSource value="${theme}">${theme}</xform:simpleDataSource>
				</c:forEach>
			</xform:select>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.fdChartList"/>
		</td><td width="85%" colspan="3">
			<xform:dialog propertyId="fdChartListIds" propertyName="fdChartListNames"
				dialogJs="Dialog_List(true, 'fdChartListIds', 'fdChartListNames', ';', 'dbEchartsChartService&fdKey=${dbEchartsChartSetForm.fdKey}&fdModelName=${dbEchartsChartSetForm.fdModelName}');" style="width:98%" htmlElementProperties="a='123'"/>
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
			${ lfn:message('dbcenter-echarts:xianshixuanxiang') }
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<%@ include file="config_chartset.jsp"%>
		</td>
	</tr>
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
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.fdCode"/>
		</td><td width="85%" colspan="3">
			<xform:textarea property="fdCode" style="width:98%;height:200px;" />
		</td>
	</tr>
	</tbody>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.docCreateTime"/>
		</td><td width="35%">
			<xform:datetime property="docCreateTime" showStatus="view" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="dbcenter-echarts" key="dbEchartsChartSet.docCreator"/>
		</td><td width="35%">
			<xform:address propertyId="docCreatorId" propertyName="docCreatorName" orgType="ORG_TYPE_PERSON" showStatus="view" />
		</td>
	</tr>
</table>
<br>
</center>
<ui:tabpage expand="false" var-navwidth="90%">
	<!--权限机制 -->
	<c:import url="/sys/right/import/right_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="dbEchartsChartSetForm" />
		<c:param name="moduleModelName"
			value="com.landray.kmss.dbcenter.echarts.model.DbEchartsChartSet" />
	</c:import>
</ui:tabpage>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<html:hidden property="fdDbEchartsTemplateId"/>
<html:hidden property="fdDbEchartsTemplateName"/>
<html:hidden property="fdKey" />
<html:hidden property="fdModelName" />
<script>
	var g_validator = $KMSSValidation();
	seajs.use(['lui/dialog'],function(dialog) {
		// 选择分类
		window.dbEcharts_treeDialog = function() {
		   dialog.simpleCategory('com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate','fdDbEchartsTemplateId','fdDbEchartsTemplateName',false,returnFun,null,true);
		}
		
		
		function returnFun(data){
			
			if(typeof(data.id)=='undefined'){
				return false;
			}
			
			//清空地址本的值
			$('.mf_container ol li').remove();
			$('input[name=authReaderIds]').val("");
			
			$.ajax({
			url:'<c:url value="/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=Readable&fdTemplateId="/>'+data.id,
			type: "GET",
			dataType: "json",
			success:function(res){
				
				var arryAuthReaderIds=[];
				var arryAuthReaderNames=[];
				$.each(res.reader,function(index,item){
					arryAuthReaderIds.push(item.id)
					arryAuthReaderNames.push(item.name)
			      
				});
				arryAuthReaderIds=arryAuthReaderIds.join(';');
				arryAuthReaderNames=arryAuthReaderNames.join(';');
				$("input[name='authReaderIds']").val(arryAuthReaderIds);
				$("textarea[name='authReaderNames']").val(arryAuthReaderNames);
				//
				var arryAutheditorIds=[];
				var arryAutheditorNames=[];
				$.each(res.editor,function(index,item){
					arryAutheditorIds.push(item.id)
					arryAutheditorNames.push(item.name)
			      
				});
				arryAutheditorIds=arryAutheditorIds.join(';');
				arryAutheditorNames=arryAutheditorNames.join(';');
				$("input[name='authEditorIds']").val(arryAutheditorIds);
				$("textarea[name='authEditorNames']").val(arryAutheditorNames);
				Address_QuickSelection("authReaderIds","authReaderNames",";",ORG_TYPE_ALL,true,res.reader,null,null,"");
				Address_QuickSelection("authEditorIds","authEditorNames",";",ORG_TYPE_ALL,true,res.editor,null,null,"");
				
			},error:function(res){
			   alert("${ lfn:message('dbcenter-meterview:meterviewIndicator.sys.FailedtoGetData') }");
			 }
		}) 
			
		}
	});
	
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
		try {
			var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
			var data = value==''?{}:LUI.toJSON(value);
			if(Com_GetUrlParameter(location.href,"method") == "add"){
				data.chartSet = {};
				data.chartSet.tbWidth = '800';
			}
			dbecharts.write("fdCode", data);
		} catch (e) {
			alert("格式异常");
			document.getElementsByName("editMode")[1].checked=true;
			LUI.$('#configMode').hide();
			LUI.$('#codeMode').show();
		}
	}
	function submitForm(method){
		if(!g_validator.validate()){
			return false;
		}
		var fields = document.getElementsByName("editMode");
		if(fields[0].checked){
			updateCodeField();
		}else{
			//检查代码是否规范
			try {
				LUI.toJSON(LUI.$.trim(LUI.$('[name="fdCode"]').val()));
			} catch (e) {
				alert("执行代码格式不正确");
				return false;
			}
		}

		dbecharts.disable(true);
		
		// 添加分类id到url，用于权限过滤
		var url = document.dbEchartsChartSetForm.action;
		if($("input[name='fdModelName']").val() === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
			url = Com_Parameter.ContextPath + "sys/modeling/base/dbEchartsChartSet.do";
		}
		document.dbEchartsChartSetForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
		
		if(!Com_Submit(document.dbEchartsChartSetForm, method)){
			dbecharts.disable(false);
		}
	}
	dbecharts.init(updateFormField);
</script>
</html:form>

	</template:replace>
</template:include>
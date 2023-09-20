<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${lfn:message('dbcenter-echarts:table_edit_01')}" onclick="Com_OpenWindow('dbEchartsTable_help.jsp','echarts_table_help');">
			</ui:button>
			<c:choose>
				<c:when test="${ dbEchartsTableForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsTableForm.method_GET == 'add' || dbEchartsTableForm.method_GET == 'clone'}">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>	
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do">

		<c:import charEncoding="UTF-8" url="/dbcenter/echarts/echart_help.jsp">
			<c:param name="helpPage" value="/dbcenter/echarts/db_echarts_table/dbEchartsTable_help.jsp" />
		</c:import>

<script>
	Com_IncludeFile("doclist.js|security.js");
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	
	Com_IncludeFile('data.js|formula.js|dialog.js|address.js|treeview.js');

</script>
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="table.mode.programming.list.title"/></p>

	<ui:step id="__step" style="background-color:#f2f2f2" >
		
		<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.base') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_table/dbEchartsTable_edit_base.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>

		<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.dataset') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_table/dbEchartsTable_edit_dataset.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>

		<ui:content title="${ lfn:message('dbcenter-echarts:chart.mode.programming.lisetview.setting') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_table/dbEchartsTable_edit_listview.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>

	</ui:step>
<br>

<ui:tabpage expand="false" var-navwidth="90%">
	<!--权限机制 -->
	<c:import url="/sys/right/import/right_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="dbEchartsTableForm" />
		<c:param name="moduleModelName"
			value="com.landray.kmss.dbcenter.echarts.model.DbEchartsTable" />
	</c:import>
</ui:tabpage>
<html:hidden property="fdId" />
<html:hidden property="fdKey" />
<html:hidden property="fdModelName" />

<html:hidden property="method_GET" />
<script>
	var g_validator = $KMSSValidation();
	var code_model = false;
	seajs.use(['lui/dialog'],function(dialog) {
		Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
			//判断模式，如果是代码模式，则直接提交
			var fields = document.getElementsByName("editMode");
			if(code_model){
				return true;
			}
			
			if($("#querys_DocList tr:not(:first)").length<1){
				dialog.alert("SQL语句不能为空");
				return false;
			}
			if($("#columns_DocList tr:not(:first)").length<1){
				dialog.alert("视图列不能为空");
				return false;
			}
			return true;
		}
		
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
			url:  '<c:url value="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=Readable&fdTemplateId="/>'+data.id,
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
			   alert("加载分类可阅读者错误");
			 }
		}) 
			
		}
	});
	
	function changeMode(){
		var fields = document.getElementsByName("editMode");
		for(var i=0; i<fields.length; i++){
			if(fields[i].checked){
				if(fields[i].value=="configMode"){
					LUI.$('#codeMode').hide();
					showStep(true);
					code_model = false;
				}
				if(fields[i].value=="codeMode"){
					showStep(false);
					LUI.$('#codeMode').show();
					code_model = true;
				}
			}
			/*
			if(fields[i].checked){
				LUI.$('#'+fields[i].value).show();
			}else{
				LUI.$('#'+fields[i].value).hide();
			}
			*/
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
		if(_fields){
			data["fields"] = _fields;
		}

		//自动设置允许翻页
		var hasPage = false;
		for(var i=0; i<data["querys"].length; i++){
			if(data["querys"][i]["sql"].indexOf("startIndex")!=-1 && data["querys"][i]["sql"].indexOf("endIndex") !=-1){
				hasPage = true;
			}
		}
		$("[name='listview.page']").prop("checked", hasPage);

		//LUI.$('[name="fdCode"]').val(LUI.stringify(data));
		//console.log(JSON.stringify(data, null, 4))
		LUI.$('[name="fdCode"]').val(JSON.stringify(data, null, 4));
	}

	function updateFormField(){
		try {
			var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
			var data = value==''?{}:LUI.toJSON(value);
			if($.isEmptyObject(data)){
				// 视图项
				if(data.listview){
					data.listview.listSummary = "0";   //默认列表汇总
				}
			}
			dbecharts.write("fdCode", data);
		} catch (e) {
			alert("格式异常");
			document.getElementsByName("editMode")[1].checked=true;
			showStep(false);
			LUI.$('#codeMode').show();
			code_model = true;
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
		var url = document.dbEchartsTableForm.action;
		if($("input[name='fdModelName']").val() === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
			url = Com_Parameter.ContextPath + "sys/modeling/base/dbEchartsTable.do";
		}
		document.dbEchartsTableForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
	
		if(!Com_Submit(document.dbEchartsTableForm, method)){
			dbecharts.disable(false);
		}
		
	}
	dbecharts.init(updateFormField);
	seajs.use(['lui/topic'], function(topic) {
		topic.subscribe('btn_disabled', function(){
			var field = LUI.$('[name="fdCode"]');
			field.val(base64Encode(field.val()));
		});
	});

	function showStep(flag){
		var steps = LUI("__step");
		var count = 0;
		for(var i=0;i<steps.children.length;i++){
			if(steps.children[i].element){
				if(count>0){
					if(flag){
						$(steps.children[i].element).show();
					}else{
						$(steps.children[i].element).hide();
					}
				}
				count++;
			}
		}
	}

<c:if test="${ dbEchartsTableForm.method_GET == 'edit' }">
	LUI.ready(function() {
		setTimeout(function(){
				var steps = LUI("__step");
				for(var i=0;i<steps.children.length;i++){
					if(steps.children[i].element){
						steps.next();
					}
				}
				steps.fireJump(0);
		},500);
	});
</c:if>
	
	function _validatorElements(elIds){
		var arr = elIds.split(";");
		var ret = true;
		for(var i=0;i<arr.length;i++){
			var el= document.getElementsByName(arr[i])[0];
			if(!g_validator.validateElement(el)){
				ret = false;
			}
		}
		return ret;
	}

	seajs.use( [ 'lui/topic' ], function(topic) {
		topic.subscribe('JUMP.STEP', function(evt) {
			//验证基本信息
			if(evt.last==0 ) {
				if(!_validatorElements("docSubject;fdDbEchartsTemplateName")) {
						evt.cancel = true;
						return false;
				}
				return true;
			}

		});

	});

	function getFieldList4Formual(index){
		var fieldList=[];
		if(_fields!=null){
			for(var i=0;i<_fields.length;i++){
				if(_fields[i]["type"]=="Number" || _fields[i]["type"]=="Date" ){
						var fo = {};
						fo["label"] = _fields[i]["name"];
						fo["name"] = _fields[i]["key"];
						fo["type"] = _fields[i]["type"];
						if(_fields[i]["type"]=="Number"){
							fo["type"] = "Double";
						}
						fieldList.push(fo);
					}
			}
		}
		var vals = document.getElementsByName("fieldName");
		var texts = document.getElementsByName("fieldLabel");
		$.each($("#rowstats_DocList tr:gt(0)"),function(i){
			if(index!=i){
				 var key="rowstats["+i+"].key";
				 var label="rowstats["+i+"].label";
				 var fo={};
				 fo["name"] = document.getElementsByName(key)[0].value;
				 fo["label"] = document.getElementsByName(label)[0].value;
				 fo["type"] = "Double";
				 fieldList.push(fo);
			}
		});

		return fieldList;
	}
	
	var flag;
	function selectFormulas(index){
		var fieldList = getFieldList4Formual(index);
		var expressionText = "inputs["+index+"].expressionText1" ;
		var expressionValue = "inputs["+index+"].expressionValue1" ;
		var ide = document.getElementsByName(expressionValue);
		var texte = document.getElementsByName(expressionText);
		Formula_Dialog(ide, texte, fieldList, "Object", formulaReturn, null ,null,null, null);	
		flag=index;
	}
	
	function formulaReturn(index){
		$.each(index.data,function(index,value){
			if(value.id!=null && value.id!=''){
				$("#initValue"+flag).parents('td').css("cursor", "not-allowed")
				 $("#initValue"+flag).css("cursor", "not-allowed");	
				 $("#initValue"+flag).css("pointer-events", "none");										
				 $("#initValue"+flag).css('color', '#cccccc');
			}else{
				$("#initValue"+flag).parents('td').removeAttr("style", " ")
				$("#initValue"+flag).css('color', '#000');			
				$("#initValue"+flag).css("pointer-events", "auto");	
				$("#initValue"+flag).css("cursor", "default");	
			}
		}) 
		
	}
	
	function checkValue(thisValue,index){
		if(thisValue=='' || thisValue=="undefind"){
			$("#ddd"+index).parent().parent('center').css("display", "block")
			$("#ddd"+index).css('color', '#000');
			$("#ddd"+index).css("cursor", "default");	
			$("#ddd"+index).css("pointer-events", "auto");			
		}else{
			$("#ddd"+index).parent().parent('center').css("display", "none")
			$("#ddd"+index).css("pointer-events", "none");				
			$("#ddd"+index).css('color', '#cccccc');
		}
	}

	function selectFormula(index){
		var fieldList = getFieldList4Formual(index);
		var expressionText = "rowstats["+index+"].expressionText" ;
		var expressionValue = "rowstats["+index+"].expressionValue" ;
		var ide = document.getElementsByName(expressionValue);
		var texte = document.getElementsByName(expressionText);
		Formula_Dialog(ide, texte, fieldList, "Object", null, null ,null,null, null);	
	}
	
	
	

	
</script>
</html:form>
	</template:replace>
</template:include>
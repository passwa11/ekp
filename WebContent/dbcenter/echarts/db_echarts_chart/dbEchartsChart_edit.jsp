<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('dbcenter-echarts:button.help') }" onclick="Com_OpenWindow('dbEchartsChart_help.jsp','echarts_chart_help');">
			</ui:button>
			<c:choose>
				<c:when test="${ dbEchartsChartForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsChartForm.method_GET == 'add' || dbEchartsChartForm.method_GET == 'clone' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
		<c:import charEncoding="UTF-8" url="/dbcenter/echarts/echart_help.jsp">
			<c:param name="helpPage" value="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_help.jsp" />
		</c:import>

<html:form action="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do">
<script>
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("config.css", "${LUI_ContextPath}/dbcenter/echarts/common/", "css", true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	Com_IncludeFile("jquery.js|dialog.js|formula.js");
</script>
<c:if test="${dbEchartsChartForm.fdType=='0'}">
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="chart.mode.programming.simple.title"/></p>
</c:if>
<c:if test="${dbEchartsChartForm.fdType=='1'}">
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="chart.mode.programming.higeLevel.title"/></p>
</c:if>

	<ui:step id="__step" style="background-color:#f2f2f2" >
		
		<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.base') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_edit_base.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>

		<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.dataset') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_edit_dataset.jsp" charEncoding="UTF-8">
				<c:param name="fdKey" value="${dbEchartsChartForm.fdKey}" />
				<c:param name="fdModelName" value="${dbEchartsChartForm.fdModelName}" />
			</c:import>
		</ui:content>

	<c:if test="${dbEchartsChartForm.fdType=='0'}">
		<ui:content title="${ lfn:message('dbcenter-echarts:chart.mode.programming.chartset.data') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_edit_chartset_data.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>

		<ui:content title="${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.options') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_edit_chartset_options.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>
	</c:if>

	<c:if test="${dbEchartsChartForm.fdType=='1'}">
		<ui:content title="${ lfn:message('dbcenter-echarts:chart.mode.programming.dataset.options') }" toggle="true">
			<c:import url="/dbcenter/echarts/db_echarts_chart/dbEchartsChart_edit_adv.jsp" charEncoding="UTF-8">
			</c:import>
		</ui:content>
	</c:if>

	</ui:step>

<br>
<ui:tabpage expand="false" var-navwidth="90%">
	<!--权限机制 -->
	<c:import url="/sys/right/import/right_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="dbEchartsChartForm" />
		<c:param name="moduleModelName"
			value="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart" />
	</c:import>
</ui:tabpage>
<html:hidden property="fdId" />
<html:hidden property="fdType" />
<html:hidden property="fdKey" />
<html:hidden property="fdModelName" />
<html:hidden property="method_GET" />
<html:hidden property="fdDbEchartsTemplateId"/>
<html:hidden property="fdDbEchartsTemplateName"/>
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
			url:'<c:url value="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=Readable&fdTemplateId="/>'+data.id,
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
	
	g_validator.addValidator('typeUnit','{name}请输入有效值（数值+单位）。例如：“600px”。',function(v,e,o){
		if(v == null || v == '') return true;
		var v1 = parseInt(v);
		if(isNaN(v1))
			return false;
		if(v1 == v) {
			return false;
		}
		return true;
	});
	function changeMode(){
		var fields = document.getElementsByName("editMode");
		for(var i=0; i<fields.length; i++){
			if(fields[i].checked){
				if(fields[i].value=="configMode"){
					LUI.$('#codeMode').hide();
					showStep(true);
					$("select[name='xAxis.key']").attr("disabled",false);
					$("[name='chart.option']").attr("disabled",false);
				}
				if(fields[i].value=="codeMode"){
					showStep(false);
					LUI.$('#codeMode').show();
					$("select[name='xAxis.key']").attr("disabled","disabled");
					$("[name='chart.option']").attr("disabled","disabled");
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

	function updateCodeField(){
		var data = {};
		dbecharts.read("fdCode", data);

		if(_fields){
			data["fields"] = _fields;
		}

		//LUI.$('[name="fdCode"]').val(LUI.stringify(data));
		LUI.$('[name="fdCode"]').val(JSON.stringify(data, null, 4));

		data = {};
		dbecharts.read("fdConfig", data);
		//LUI.$('[name="fdConfig"]').val(LUI.stringify(data));
		LUI.$('[name="fdConfig"]').val(JSON.stringify(data, null, 4));

	}
	function updateFormField(){
		var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
		var data = {};
		var flag = false;
		try{
			data = value==''?{}:LUI.toJSON(value);
			dbecharts.write("fdCode", data);
		}catch(err){
			alert("执行代码 格式异常，不符合JSON规范，请检查配置");
			flag = true;
		}

		value = LUI.$.trim(LUI.$('[name="fdConfig"]').val());
		try{
			data = value==''?{}:LUI.toJSON(value);
			dbecharts.write("fdConfig", data);
		}catch(err){
			alert("配置 格式异常，不符合JSON规范，请检查配置")
			flag = true;
		}

		//异常时，切换成代码模式，方便修改
		if(flag){
			document.getElementsByName("editMode")[1].checked=true;
			showStep(false);
			LUI.$('#codeMode').show();
			$("select[name='xAxis.key']").attr("disabled","disabled");
			$("[name='chart.option']").attr("disabled","disabled");
		}
	}
	function submitForm(method){
		/*
		var fields = document.getElementsByName("editMode");
		if(fields[1].checked){
			//代码模式
			$("select[name='xAxis.key']").attr("validate","");
		}
		if(fields[0].checked){
			//配置模式
			$("select[name='xAxis.key']").attr("validate","required");
		}
		*/

		if(!g_validator.validate()){
			return false;
		}
		if(window.changeLoadMapJs){
			var loadMapJs = $("#loadMapJs");
	        loadMapJs.val(loadMapJs.attr("json"));
		}
		var fields = document.getElementsByName("editMode");
		if(fields[0].checked){
			updateCodeField();
		}

		if(fields[1].checked){
			try{
				LUI.toJSON(LUI.$.trim(LUI.$('[name="fdCode"]').val()));
				LUI.toJSON(LUI.$.trim(LUI.$('[name="fdConfig"]').val()));
			}catch(err){
				alert("执行代码/配置 格式不对，请检查数据");
				return false;
			}
		}
		dbecharts.disable(true);
		
		// 添加分类id到url，用于权限过滤
		var url = document.dbEchartsChartForm.action;
		if($("input[name='fdModelName']").val() === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
			url = Com_Parameter.ContextPath + "sys/modeling/base/dbEchartsChart.do";
		}
		document.dbEchartsChartForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
		
		if(!Com_Submit(document.dbEchartsChartForm, method)){
			dbecharts.disable(false);
		}
	}
	dbecharts.init(updateFormField);

<c:if test="${ dbEchartsChartForm.method_GET == 'edit' }">
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
	
	function getFieldList4Formual(index){
		var fieldList=[];
		/* if(_fields!=null){
			for(var i=0;i<_fields.length;i++){
				if(_fields[i]["type"]=="Date" ){
						var fo = {};
						fo["label"] = _fields[i]["name"];
						fo["name"] = _fields[i]["key"];
						fo["type"] = _fields[i]["type"];
						fieldList.push(fo);
					}
			}
		} */
		var vals = document.getElementsByName("fieldName");
		var texts = document.getElementsByName("fieldLabel");
		$.each($("#inputs_DocList tr:gt(0)"),function(i){
			if(index!=i){
				 var key="inputs["+i+"].key";
				 var fo={};
				 fo["name"] = document.getElementsByName(key)[0].value;
				 fo["type"] = "Date";
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

			if(evt.last==2 && evt.cur!='1') {
				if(!_validatorElements("xAxis.key")) {
						evt.cancel = true;
						return false;
				}
				return true;
			}

		});

	});

</script>
</html:form>

	</template:replace>
</template:include>
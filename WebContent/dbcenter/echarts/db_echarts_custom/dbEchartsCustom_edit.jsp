<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil" %>
<%@page import="net.sf.json.JSONObject" %>
<%
	JSONObject relationObj = ConfigureUtil.getRelationDiagram();
	String relation = relationObj.toString();
%>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('dbcenter-echarts:button.help') }" onclick="Com_OpenWindow('../chart_config_help.jsp');">
			</ui:button>
			<c:choose>
				<c:when test="${ dbEchartsCustomForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when>
				<c:when test="${ dbEchartsCustomForm.method_GET == 'add'  || dbEchartsCustomForm.method_GET == 'clone' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<kmss:windowTitle moduleKey="dbcenter-echarts:module.dbcenter.piccenter"  subjectKey="dbcenter-echarts:table.dbEchartsCustom" subject="${dbEchartsCustomForm.docSubject}" />
<html:form action="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do">
<%@ include file="/dbcenter/echarts/common/configure/jsp/SQLCommonJS.jsp"%>
<script>
	Com_IncludeFile('configure.css',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/css/','css',true);
	Com_IncludeFile('dbEchartsCustom.css',Com_Parameter.ContextPath+'dbcenter/echarts/db_echarts_custom/css/','css',true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	Com_IncludeFile("mutext.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", null, true);
	Com_IncludeFile("Source.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_custom/js/", null, true);
	Com_IncludeFile("SourceCollection.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_custom/js/", null, true);
	Com_IncludeFile("TableDraw.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_custom/js/", null, true);
	Com_IncludeFile("configure_js.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_custom/js/", null, true);
	//用于时间选择框
	Com_IncludeFile('calendar.js');
	Com_IncludeFile('doclist.js');
</script>
<p class="txttitle"><bean:message bundle="dbcenter-echarts" key="table.dbEchartsCustom"/></p>

	<center>
		<div class="lui-chartData">
			<div class="lui-chartData-content">
				<div style="display:inline-block;width:65%;float:left;">
					<table class="tb_normal dbEcharts_Custom_Table" width=100%> 
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="dbcenter-echarts" key="dbEchartsCustom.docSubject"/>
							</td><td width="85%">
								<xform:text property="docSubject" style="width:98%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.category') }
							</td>
							<td width="85%" colspan="3">
								<xform:dialog required="true" subject="${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.category') }" propertyId="fdDbEchartsTemplateId" style="width:30%"
										propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog();">
								</xform:dialog>
							</td>
						</tr>
						<c:import charEncoding="UTF-8" url="/dbcenter/echarts/db_echarts_custom/custom_table_template.jsp"></c:import>
					</table>
				</div>
				<div style="display:inline-block;width:33%;margin-left:10px;max-width:386px;">
					<c:import charEncoding="UTF-8" url="/dbcenter/echarts/db_echarts_custom/custom_info.jsp"></c:import>
				</div>
			</div>
		</div>
	</center>
	<ui:tabpage expand="false" var-navwidth="90%">
		<!--权限机制 -->
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="dbEchartsCustomForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.dbcenter.echarts.model.DbEchartsCustom" />
		</c:import>
	</ui:tabpage>	
<html:hidden property="fdId" />
	<html:hidden property="fdKey" />
	<html:hidden property="fdModelName" />
<html:hidden property="method_GET" />
<html:hidden property="fdCode" />
</html:form>
	<script>
	var g_validator = $KMSSValidation();
	var sourceCollection = new SourceCollection($(".dbEcharts_Custom_Table"));
	sourceCollection.fieldBlocksDom = $(".lui-chartData-info-list-box");
	
	var _db_components = {};
	_db_components["where"] = {"work":true ,"required":false,"fun":SQLWhereCondition,"arr":[],"tableClass":".whereConditionTable"};
	_db_components["select"] = {"work":true ,"required":true,"fun":SQLSelectValue,"arr":[],"tableClass":".selectValueTable"};
	
	SQLStructure.prototype.getKeyData = function(){
		var table = {};
		var self = this;
		for(var key in self.components){
			var com = self.components[key];
			if(!com.work){
				continue;
			}
			if(key == "where"){
				var datas = [];
				// 入参抽出来
				var dynamic = [];
				for(var i = 0;i < com["arr"].length;i++){
					var c = com["arr"][i];
					if(c.isValid && c.isValid()){
						var v = c.getKeyData();
						datas.push(v);
						if(v.fieldVal.val == "!{dynamic}"){
							dynamic.push(v);						
						}						
					}
				}
				table[key] = datas;
				table.dynamic = dynamic;
			}else{
				var datas = [];
				for(var i = 0;i < com["arr"].length;i++){
					var c = com["arr"][i];
					if(c.isValid && c.isValid()){
						datas.push(c.getKeyData());						
					}
				}
				table[key] = datas;
			}
		}
		
		table.baseModelData = {};
		table.baseModelData.modelName = self.dataSource.modelName;
		table.baseModelData.isXform = self.dataSource.isXform;
		return table;
	}
	
	// 图表中心的类型关系图
	var DbEcharts_RelationDiagram = eval(<%=relation%>);
	
	JSONComponent.init(DbEcharts_RelationDiagram);
	
	seajs.use(['lui/topic','lui/dialog'], function(topic,dialog) {
		
		tableDraw.init($(".dbEcharts_Custom_Table"));
		
		/********************************更新返回值视图项***************************************/
		$(document).on("SQLStructure_SelectComponent_change",function(event,argu){
			sourceCollection.updateFieldBlock(argu.component.sqlStructure,argu.component,argu.type);
		});
		$(document).on("SQLStructure_Init",function(event,argu){
			var structure = argu.structure;
			sourceCollection.initFieldBlock(structure);
			for(var key in structure.components){
				var component = structure.components[key];
				var paires =component["dom"].find("[data-pair]");
				if(paires.length > 0){
					$(paires[0]).show();
				}
			}
			// 更新排序选项
			dbEchartsCustom_UpdateOrderOptions(structure);
		});
		
		function dbEchartsCustom_UpdateOrderOptions(structure){
			var source = sourceCollection.findSourceByStru(structure);
			var selectName = "listview.sort";
			var $select = $(source.tableDom).find("select[name$='"+selectName+"']");
			var items = structure.dataSource.propertyItems;
			var html = "";
			for(var i = 0;i < items.length;i++){
				var item = items[i];
				// 屏蔽对象类型
				if(item.type.indexOf("com.landray.kmss") > -1){
					continue;
				}
				html += "<option";
				html += " value='"+ item.value +"'";
				if(i == 0){
					html += " selected='selected'";
				}
				html += ">";
				html += item.name;
				html += "</option>";
			}
			$select.html(html);
		}
		
		window.dbEchartsCustom_Preview = function(){
			if(!validateForm()){
				return false;
			}
			var config = dbEchartsCustom_GetCodeData();
			var txt = CKEDITOR.instances.fdCustomText.getData();
			config.customTxt = txt;
			var url = Com_Parameter.ContextPath + "dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=preview";
			$.ajax({
		        url:url,
		        type:"post",
		        dataType : 'text',
		        data:JSON.stringify(config),
		        cache : false,
		        async : false, // 同步
		        contentType: "application/json",
		        success:function(html){
		            if(html){
		            	$("#borad-txt").html(html);
		            }else{
		            	$("#borad-txt").html("");
		            }
		        },
		        error: function(err) {
                	console.log("error:" + err);
                	alert(err);
                }
		    });
		}
		
		// 选择分类
		window.dbEcharts_treeDialog = function() {
			dialog.simpleCategory('com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate','fdDbEchartsTemplateId','fdDbEchartsTemplateName',false,returnFun,null,true);
			
		};
		function returnFun(data){
			
			if(typeof(data.id)=='undefined'){
				return false;
			}
			
			//清空地址本的值
			$('.mf_container ol li').remove();
			$('input[name=authReaderIds]').val("");
			$.ajax({
			url:'<c:url value="/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=Readable&fdTemplateId="/>'+data.id,
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
		
		window.dbEchartsCustom_AddSource = function(){
			sourceCollection.addSource();
		};
		
		window.dbEchartsCustom_DelRow = function(dom){
			var $table = $(dom).closest("table");
			var source = sourceCollection.getSourceByTable($table[0]);
			sourceCollection.delSource($table);// del的时候会更新索引
		};
		
		window.dbEchartsCustom_AddRow = function(dom,type){
			var $table = $(dom).closest("table.SQLStructureTable");
			var source = sourceCollection.getSourceByTable($table[0]);
			if(!source.structure.isInit){
				alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.plzChooseSourceFirst') }");
				var e=window.event || arguments.callee.caller.arguments[0];
				e.preventDefault();
				e.stopPropagation();
				return false;
			}
			var com = source.structure.newComponent(type);
			var funStr = "dbEchartsCustom_AddRow_" + type;
			if(typeof(window[funStr]) === "function"){
				if(!eval(funStr).call(window,source.structure,com)){
					return;
				}
			}
		}
		
		// 返回值添加行的时候，更新右边视图
		window.dbEchartsCustom_AddRow_select = function(structure,component){
			sourceCollection.addFieldBlock(structure,component);
			return true;
		}
		
		window.selectModelNameDialog = function(dom){
			window.focus();
			var $table = $(dom).closest("table");
			var url = "/dbcenter/echarts/common/configure/jsp/model_list.jsp";
			<c:if  test="${param.fdModelName eq 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'}">
			url = "/dbcenter/echarts/common/configure/jsp/modeling_model_list.jsp?fdKey=${param.fdKey}";
			</c:if>
			var height = window.screen.availHeight * 0.68;
			var width = document.documentElement.clientWidth * 0.6;
			dialog.iframe(url,"${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataSource') }",function(rs){
				if(rs){
					if(formatModelData(rs,$table)){
						initSQLStructure(rs,$table);
					}				
				}
			},{width:width,height : height});
		}
		
		function formatModelData(rs,$table){
			if(rs.modelName && rs.modelNameText){
				$table.find("input[name*='modelName']").val(rs.modelName);
				$table.find("input[name*='modelNameText']").val(rs.modelNameText);
				$KMSSValidation().validateElement($table.find("input[name*='modelNameText']")[0]);
				return true;
			}else{
				alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.dataSourceError') }");
				return false;			
			}
		}
		
		// 初始化 选择完数据源的回调函数
		function initSQLStructure(rs,$table){
			var config = {};
			config.modelName = rs.modelName;
			config.modelNameText = rs.modelNameText;
			config.data = $.parseJSON(rs.rs).data;
			config.isXform = rs.isXform;
			sourceCollection.getSourceByTable($table[0]).structure.init(config);
		}
		
		window.dbEchartsCustom_CustomSortChange = function(dom){
			var $table = $(dom).closest("table");
			if($(dom).prop("checked")){
				if(!sourceCollection.getSourceByTable($table[0]).structure.isInit){
					alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.custom.plzChooseSourceFirst') }");
					dom.checked = false;
					return ;
				}
			}
			dbEchartsCustom_CheckBoxChange(dom,$table[0],'listview_orderby','inline-block','none');
		}
		
		/**
		* 复选框勾选之后，控制目标的显示或者隐藏
		* @param dom 当前的复选框DOM
		* @param range 范围
		* @param targetDomClass 目标DOM的class
		* @param ifTrueDisplay 复选框勾选时，目标的display样式
		* @param ifTrueDisplay 复选框不勾选时，目标的display样式
		* @return
		*/
		function dbEchartsCustom_CheckBoxChange(dom,range,targetDomClass,ifTrueDisplay,ifFalseDisplay,callback){
			var checked = $(dom).prop("checked");
			console.log($("."+targetDomClass,range));
			if(checked){
				$("."+targetDomClass,range).css("display",ifTrueDisplay);
			}else{
				$("."+targetDomClass,range).css("display",ifFalseDisplay);
			}
			if(callback){
				callback($(dom),$("."+targetDomClass));
			}
		}
		
		window.getSelectFieldVar = function(){
			var blocks = sourceCollection.getAllFieldBlocks();
			var vars = [];
			for(var i = 0;i < blocks.length;i++){
				var fieldArr = blocks[i].fieldArr;
				var titleVal = blocks[i].source.titleVal;
				for(var j = 0;j < fieldArr.length;j++){
					var field = fieldArr[j];
					vars.push(titleVal + "." + field.component.fieldComponent.val.replace("\|","."));
				}
			}
			return vars;
		}
		
		window.dbEchartsCustom_GetCodeData = function(){
			var data = {};
			dbecharts.read("fdCode", data);
			if(data.tables && data.tables.length && data.tables.length > 0){
				sourceCollection.readData(data.tables);	
			}
			return data;
		}
		
		function updateCodeField(){
			var data = dbEchartsCustom_GetCodeData();
			LUI.$('[name="fdCode"]').val(LUI.stringify(data));
		}
		
		window.submitForm = function(method){
			if(!validateForm()){
				return false;
			}
			updateCodeField();
			dbecharts.disable(true);
			
			// 添加分类id到url，用于权限过滤
			var url = document.dbEchartsCustomForm.action;
			document.dbEchartsCustomForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
			
			if(!Com_Submit(document.dbEchartsCustomForm, method)){
				dbecharts.disable(false);
			}
		}
		
		function dbEchartsChart_InitButton(){
			var group = {};
			var paires = $("[data-pair-id]");
			for(var i = 0;i < paires.length;i++){
				var pair = $(paires[i]).data("pair-id");
				if(group.hasOwnProperty(pair)){
					group[pair].push($(paires[i]));
				}else{
					group[pair] = [];
					group[pair].push($(paires[i]));
				}
			}
			
			for(var key in group){
				if(group[key].length > 1){
					group[key][0].hide();
				}
			}
		}
		
		function updateFormField(){
			var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
			var data = value==''?{}:LUI.toJSON(value);
			if(data.tables){
				for(var i = 0;i < data.tables.length;i++){
					var table = data.tables[i];
					sourceCollection.addSource(table);
				}
				dbecharts.write("fdCode", data);
				dbEchartsCustom_Preview();
			}else{
				// 新建的时候默认添加一个数据源
				dbEchartsCustom_AddSource();
			}
			dbEchartsChart_InitButton();
		}
		
		$(document).ready(function() {
			//#147582 防止页面还没加载完编辑控件前就去获取控件的值
			var interval = setInterval(__vnterval,200)
			var intervalEndCount = 10;
			function __vnterval(){
				if (intervalEndCount == 0) {
					console.error("数据解析超时。。。");
					clearInterval(interval);
				}
				intervalEndCount--;
				if(checkCKEDITORLoad()){
					updateFormField();
					clearInterval(interval);
				}
			}

			function checkCKEDITORLoad(){
				if(!CKEDITOR || !CKEDITOR.instances || !CKEDITOR.instances.fdCustomText){
					return false
				}
				return true;
			}
		});


	});
	</script>
	</template:replace>
</template:include>
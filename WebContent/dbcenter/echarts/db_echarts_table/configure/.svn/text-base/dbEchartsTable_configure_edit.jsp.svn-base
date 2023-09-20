<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.config.model.SysConfigParameters" %>
<template:include ref="default.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
		    <ui:button text="${ lfn:message('dbcenter-echarts:button.help') }" onclick="Com_OpenWindow('../chart_config_help.jsp');">
			</ui:button>
			<c:choose>
				<c:when test="${ dbEchartsTableForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" onclick="submitForm('update');"></ui:button>
				</c:when> 
				<c:when test="${ dbEchartsTableForm.method_GET == 'add'  || dbEchartsTableForm.method_GET == 'clone' }">	
					<ui:button text="${ lfn:message('button.save') }" onclick="submitForm('save');"></ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" onclick="submitForm('saveadd');"></ui:button>
				</c:when>
			</c:choose>	
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	<%@ include file="/dbcenter/echarts/common/configure/jsp/SQLCommonJS.jsp"%>
		<kmss:windowTitle moduleKey="dbcenter-echarts:module.dbcenter.piccenter"  subjectKey="dbcenter-echarts:table.dbEchartsTable" subject="${dbEchartsTableForm.docSubject}" />
		<%
			JSONObject relationObj = ConfigureUtil.getRelationDiagram();
			String relation = relationObj.toString();
		%>
		<html:form action="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do">
			<script>
				Com_IncludeFile("doclist.js");
				Com_IncludeFile('configure.css',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/css/','css',true);
				Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
				Com_IncludeFile("ListView.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_table/configure/js/", 'js', true);
				//用于时间选择框
				Com_IncludeFile('calendar.js');

				Com_IncludeFile('data.js|formula.js|dialog.js|address.js|treeview.js');

			</script>
			<p class="txttitle"><span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.listConfigure') }</span></p>
			
			<center>
			<div>
				<ui:step id="__step" style="background-color:#f2f2f2" >
					
					<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.base') }" toggle="true">
						<c:import url="/dbcenter/echarts/db_echarts_table/configure/dbEchartsTable_configure_edit_base.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:content>

					<ui:content title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.chartConfigure.dataset') }" toggle="true">
						<c:import url="/dbcenter/echarts/db_echarts_table/configure/dbEchartsTable_configure_edit_dataset.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:content>

					<ui:content title="${ lfn:message('dbcenter-echarts:chart.mode.programming.lisetview.setting') }" toggle="true">
						<c:import url="/dbcenter/echarts/db_echarts_table/configure/dbEchartsTable_configure_edit_listview.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:content>

				</ui:step>
			</div>
			<!-- 后台执行hql的方式，默认返回值在hql里面 {01：返回的是model，返回值从model里面取} -->
			<input name="execHqlMode" value="" type="hidden" data-dbecharts-config="fdCode" />
			</center>
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
			<html:hidden property="method_GET" />
			<html:hidden property="fdCode" />
			<html:hidden property="fdType" />
			<html:hidden property="fdKey" />
			<html:hidden property="fdModelName" />

			<script>
				var g_validator = $KMSSValidation();
				
				var _db_components = {};
				_db_components["where"] = {"work":true ,"required":false,"fun":SQLWhereCondition,"arr":[],"dom":$(".whereConditionTable")};
				_db_components["select"] = {"work":true ,"required":true,"fun":SQLSelectValue,"arr":[],"dom":$(".selectValueTable")};
				_db_components["filter"] = {"work":true ,"required":false,"fun":SQLFilterItem,"arr":[],"dom":$(".filterItemTable")};
				_db_components["rowstats"] = {"work":true ,"required":false,"fun":SQLRowStats,"arr":[],"dom":$(".rowstatsTable")};
				_db_components["colstats"] = {"work":true ,"required":false,"fun":SQLColStats,"arr":[],"dom":$(".colstatsTable")};
				
				var SQLStructureInstance = new SQLStructure(_db_components);
			
				// 图表中心的类型关系图
				var DbEcharts_RelationDiagram = eval(<%=relation%>);
				
				JSONComponent.init(DbEcharts_RelationDiagram);
				
				SQLStructureInstance.relationDiagram = JSONComponent; 
				// 选择分类
				function dbEcharts_treeDialog() {
					var fdTemplateId='${dbEchartsCustomForm.fdDbEchartsTemplateId}';
					var method = '${dbEchartsCustomForm.method_GET}';
					var url;
					if(method=='edit'){
						url='/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=edit&fdId='+Com_GetUrlParameter(location.href,"fdId")+"&fdTemplateId=!{id}";
					}else if(method=='add'){
						url='/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=add&fdTemplateId=!{id}';
					}
					seajs.use(['lui/dialog'],function(dialog) {
						dialog.simpleCategory('com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate','fdDbEchartsTemplateId','fdDbEchartsTemplateName',false,returnFun,"${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.chooseCategory') }",true);
						
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
				}
				
				// 复选框勾选之后，控制目标的显示或者隐藏，dom当前的复选框，targetDom：目标dom的class，ifTrueDisplay：复选框勾选时，目标的display样式,ifTrueDisplay：复选框不勾选时，目标的display样式
				function dbEchartsTable_CheckBoxChangeDom(dom,targetDomClass,ifTrueDisplay,ifFalseDisplay){
					var checked = $(dom).prop("checked");
					if(checked){
						$("."+targetDomClass).css("display",ifTrueDisplay);
					}else{
						$("."+targetDomClass).css("display",ifFalseDisplay);
					}
				}
				
				// 自定义排序的字段和列表视图中排序的字段不能重复
				function dbEchartsTable_SortValidate(dom){
					var isCustomSort = $("[name='listview.isCustomSort']");
					if(isCustomSort.prop("checked")){
						var value = $(dom).val();
						var items = SQLStructureInstance.listView.getKeyData();
						for(var i = 0;i < items.length;i++){
							var item = items[i];
							if(item.key == value && item.sort == "true"){
								alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.error1') }");
								return false;
							}
						}
					}
					return true;
				}
				
				// 自定义排序
				function dbEchartsTable_CustomSortChange(dom){
					if($(dom).prop("checked")){
						if(!SQLStructureInstance.isInit){
							alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.plzChooseDataSource') }");
							dom.checked = false;
							return ;
						}
					}
					dbEchartsTable_CheckBoxChangeDom(dom,'listview_orderby','inline-block','none');
				}
				
				// 初始化 选择完数据源的回调函数
				function initSQLStructure(rs){
					var config = {};
					config.modelName = rs.modelName;
					config.modelNameText = rs.modelNameText;
					config.data = $.parseJSON(rs.rs).data;
					config.isXform = rs.isXform;
					SQLStructureInstance.init(config);
				}
				
				function dbEchartsChart_AddRow(dom,type){
					if(!SQLStructureInstance.isInit){
						alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.plzChooseDataSource') }");
						return;
					}
					var table = $(dom).closest("table")[0];
					SQLStructureInstance.newComponent(type,table);
				}
				
				SQLStructureInstance.readData = dbEchartsTable_SQLStructure_Read;
				
				// 保存的时候，需要把对象的数据抽取出来保存
				function dbEchartsTable_SQLStructure_Read(data){
					if(!data || !data.table){
						return;
					}
					var table = data.table;
					
					for(var key in SQLStructureInstance.components){
						var com = SQLStructureInstance.components[key];
						if(key == 'filter'){
							var datas = [];
							// 为兼容配置模式的筛选参数，做兼容（不另外做一套）
							var inputs = [];
							for(var i = 0;i < com["arr"].length;i++){
								var c = com["arr"][i];
								if(c.isValid && c.isValid()){
									var v = c.getKeyData();
									datas.push(v);
									inputs.push(c.transferData(v));
								}
							}
							table[key] = datas;
							data.inputs = inputs;
						}else if(key == "where"){
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
							data.dynamic = dynamic;
						}else if(key == "colstats"){
							var datas = [];
							var colstats = [];
							for(var i = 0;i < com["arr"].length;i++){
								var c = com["arr"][i];
								if(c.isValid && c.isValid()){
									var v = c.getKeyData();
									datas.push(v);
									var type = v["field"]["type"];
									if(type=="Integer" || type=="Long" || type=="Double" || type=="BigDecimal"){
										colstats.push(c.transferData(v));
									}else{
										
									}
								}
							}
							table[key] = datas;
							data.colstats = colstats;
						}else if(key == "rowstats"){
							var datas = [];
							var rowstats = [];
							for(var i = 0;i < com["arr"].length;i++){
								var c = com["arr"][i];
								if(c.isValid && c.isValid()){
									var v = c.getKeyData();
									console.log(v)
									datas.push(v);
									rowstats.push(c.transferData(v));
								}
							}
							table[key] = datas;
							data.rowstats = rowstats;
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
					
					/******************listView*********************************/
					var listView = SQLStructureInstance.listView;
					data.columns = listView.getKeyData();
					
					table.baseModelData = {};
					table.baseModelData.modelName = SQLStructureInstance.dataSource.modelName;
					table.baseModelData.isXform = SQLStructureInstance.dataSource.isXform;
				}
				
				function getCodeData(){
					var data = {};
					dbecharts.read("fdCode", data);
					data.execHqlMode = "01"; 
					SQLStructureInstance.readData(data);
					return data;
				}
				
				function updateCodeField(){
					var data = getCodeData();
					//LUI.$('[name="fdCode"]').val(LUI.stringify(data));
					//console.log(JSON.stringify(data, null, 4))
					LUI.$('[name="fdCode"]').val(JSON.stringify(data, null, 4));
				}
				
				function validateForm(){
					// 返回值不能为空
		    		if(!SQLStructureInstance.isValidByKey("select")){
	    				alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.table.canNotRsNull') }");	
		    			return false;
		    		}
					
					// 自定义排序的字段不能和列表视图的排序项相同
					if(!dbEchartsTable_SortValidate($("[name='listview.sort']")[0])){
						return false;
					}
					return true;
				}
				
				function submitForm(method){
					if(!validateForm()){
						return false;
					}
					updateCodeField();
					
					// 添加分类id到url，用于权限过滤
					var url = document.dbEchartsTableForm.action;
					if($("input[name='fdModelName']").val() === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
						url = Com_Parameter.ContextPath + "sys/modeling/base/dbEchartsTable.do";
					}
					document.dbEchartsTableForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
					Com_Submit(document.dbEchartsTableForm, method);
				}
				
				// 自定义选项，首次初始化的时候，处理合计的动态选项
				function SQLDataSource_CustomPropertyItem(sourceData){
					
					sourceData.whereItems = sourceData.propertyItems;
					sourceData.selectItems = sourceData.propertyItems;
					sourceData.filterItems = sourceData.propertyItems;	
					sourceData.colstatsItems = sourceData.propertyItems;	
					sourceData.rowstatsItems = sourceData.propertyItems;
					
					var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
					var data = value==''?{}:LUI.toJSON(value);
					if(data && data.rowstats){
						var rowstats = data.rowstats;
						for(var i = 0;i < rowstats.length;i++){
							var rowstatsInfo = rowstats[i];
							sourceData.colstatsItems.push(SQLDataSource_CreateDynamicProItem(rowstatsInfo.label,rowstatsInfo.key));
						}
					}
				}
				
				// 创建动态字段定义
				function  SQLDataSource_CreateDynamicProItem(name, value){
					return new SQLPropertyItem({
						fieldText : name,
						fieldPinYinText : name,
						field : value,
						fieldType : "Double",
						isDynamic : true
					});
				}
				
				/******************************** 事件监听 start ***************************************/
				// 更新列表s视图
				$(document).on("SQLStructure_SelectComponent_change",function(event,argu){
					if(argu.component.block=="colstats"){
						return;
					}
					if(argu.type == "delete"){
						SQLStructureInstance.listView.deleteItem(argu.component);
					}else if(argu.type == "change"){
						SQLStructureInstance.listView.changeItem(argu.component);	
					}
				});
				$(document).on("SQLStructure_Init",function(event,argu){
					// 更新视图样式里面的排序选项
					dbEchartsTable_UpdateOrderOptions();
				});
				
				// 更新列统计定义的选项
				$(document).on("SQLStructure_RowStatsRowUpdate",function(event,argu){
					if(SQLStructureInstance.isInit){
						// 每次变更的时候，都把所有列统计定义的下拉选项中关于统计的选项删除，再新增新的
						var newItem = [];
						for(var i = 0;i < SQLStructureInstance.components["rowstats"].arr.length;i++){
							var rowstats = SQLStructureInstance.components["rowstats"].arr[i];
							var item = SQLDataSource_CreateDynamicProItem(rowstats.domNode.find("input[name='fieldLabel']").val(),
									rowstats.domNode.find("input[name='fieldName']").val());
							newItem.push(item);
						}
						// 更新所有列定义
						for(var i = 0;i < SQLStructureInstance.components["colstats"].arr.length;i++){
							var colstats = SQLStructureInstance.components["colstats"].arr[i];
							colstats.fieldComponent.updateDynamicOption(newItem);
						}		
					}
				});
				
				function dbEchartsTable_UpdateOrderOptions(){
					var selectName = "listview.sort";
					var $select = $("select[name='"+selectName+"']");
					var items = SQLStructureInstance.dataSource.propertyItems;
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
				/********************************* 事件监听 end *********************************************/
				
				
				function updateFormField(){
					var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
					var data = value==''?{}:LUI.toJSON(value);
					// 新建的时候，设置某些选项的初始值
					if($.isEmptyObject(data)){
						// 视图项 
						data.listview = {};
						data.listview.page = "true";
						data.listview.rowSize = <%=SysConfigParameters.getRowSize()%>;
						data.listview.isAdapterWidth = "true";
						data.listview.listSummary = "0";   //列表汇总 ，待修改
					}
					
					SQLStructureInstance.listView = new ConfigureListView();
					SQLStructureInstance.listView.domNode = $(".listViewTable");
					if(data.table && data.table.baseModelData){
						var fieldDatas = SQLDataSource_findFieldDict(data.table.baseModelData);
						data.table.data = fieldDatas.data;
						data.table.isXform = data.table.baseModelData.isXform;
						SQLStructureInstance.init(data.table);
						// 必须放在init之后
						SQLStructureInstance.listView.fillData(data.columns);
					}
					
					// 更新样式，目前是更新必填元素的显示隐藏
					SQLStructureInstance.updateDisplayComponent();
					
					dbecharts.write("fdCode", data);
				}
				
				dbecharts.init(updateFormField);
					

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
					},1000);
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
							if(!_validatorElements("docSubject;fdDbEchartsTemplateName;table.modelNameText")) {
									evt.cancel = true;
									return false;
							}
							return true;
						}

					});

				});

			
			function getFieldList4Formual(){
				var items = SQLStructureInstance.dataSource.propertyItems;
				var fieldList=[];
				for(var i=0;i<items.length;i++){
					if(items[i]["type"]=="Long" || items[i]["type"]=="Integer" || items[i]["type"]=="Double"
						|| items[i]["type"]=="Date" || items[i]["type"]=="DateTime" || items[i]["type"]=="Time"){
							var fo = {};
							fo["label"] = items[i]["name"];
							fo["name"] = items[i]["value"];
							fo["type"] = items[i]["type"];
							fieldList.push(fo);
						}
				}

				var vals = document.getElementsByName("fieldName");
				var texts = document.getElementsByName("fieldLabel");
				for(var i=0;i<vals.length-1;i++){
					if(vals[i].value!="" && texts[i].value!=""){
						var fo = {};
						fo["label"] = texts[i].value;
						fo["name"] = vals[i].value;
						fo["type"] = "Double";
						fieldList.push(fo);
					}
				}

				return fieldList;
			}
			</script>
		</html:form>
	</template:replace>
</template:include>
if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}
//兼容Safari无法直接获取dialogArguments
if(!dialogObject && navigator.userAgent.indexOf("Safari") >= 0){
	dialogObject = opener.Com_Parameter.Dialog;
}
//获取参数
var parameter = dialogObject.parameter;
Com_AddEventListener(window,"load",function(){
	//初始化控件
	var currentRule = parameter.currentRule;
	var params = parameter.ruleSetParam.getAllParams();
	//根据返回类型判断是否显示矩阵组织
	if(currentRule.returnType && (currentRule.returnType.indexOf("ORG_TYPE_PERSON") != -1
			|| currentRule.returnType.indexOf("ORG_TYPE_POST") != -1)){
		//返回类型是组织架构类型，显示
		$("input[name='fdResultMode'][value='orgMatrix']").parents("label").eq(0).show();
	}else{
		$("input[name='fdResultMode'][value='orgMatrix']").parents("label").eq(0).hide();
	}
	//初始化结果控件
	var resultObj = {
		mode:$("[name='fdResultMode']:checked").val(),
		rtnType:currentRule.returnType,
		isMulti:currentRule.isMulti,
		value:null,
		idField:"fdResult",
		nameField:"fdDisResult",
		type:"result",
		control:"textarea"
	};
	//设置值
	var resultValueObj = {};
	resultValueObj.fdResult = currentRule.fdResult;
	resultValueObj.fdDisResult = currentRule.fdDisResult;
	resultObj.value = resultValueObj;
	//结果设置
	var fdResultMode = currentRule.fdResultMode;
	if(fdResultMode){
		$("input[name='fdResultMode'][value='"+fdResultMode+"']").attr("checked",true);
		resultObj.mode = fdResultMode;
	}
	//设置控件
	if(resultObj.mode == "orgMatrix"){
		//矩阵组织
		initOrgMatrixInfo(resultObj);
	}else{
		var parent = document.getElementById("resultControlArea");
		createControl(resultObj,parent);
	}
	//初始化扩展行为
	//window.initBehavior();
	
	/*结果模式切换*/
	$("[name='fdResultMode']").change(function(){
		var ruleSetRule = parameter.ruleSetRule;
		var currentRule = parameter.currentRule;
		if(this.value=="orgMatrix"){
			//矩阵组织
			$("#orgMatrixArea").show();
			$("#resultControlArea").hide();
			$("#extendArea").hide();
		}else if(this.value =="extend"){
			$("#orgMatrixArea").hide();
			$("#resultControlArea").hide();
			$("#extendArea").show();
		}else{
			//公式定义和固定值控件
			$("#orgMatrixArea").hide();
			$("#resultControlArea").show();
			$("#extendArea").hide();
			//构造源对象
			var srcObj = {
				mode:this.value,
				rtnType:currentRule.returnType,
				isMulti:currentRule.isMulti,
				value:null,
				idField:"fdResult",
				nameField:"fdDisResult",
				type:"result",
				control:"textarea"
			}
			var parent = document.getElementById("resultControlArea");
			createControl(srcObj,parent);
		}
		
		//清空值
		$("[name='fdResult']").val("");
		$("[name='fdDisResult']").val("");
	});
});

/*初始化扩展行为*/
var Behaviors = [];
window.initBehavior = function(){
	var beanName = "getAllResultBehaviorService&model=";
	var rtnVal = new KMSSData().AddBeanData(beanName).GetHashMapArray();
	var behaviorTypeSelect = $("#behaviorType")[0];
	$(behaviorTypeSelect).html("");
	$(behaviorTypeSelect).append('<option value="">请选择</option>');
	//behaviorTypeSelect.options.length = 0;
	//behaviorTypeSelect.options.add(new Option("请选择",""));
	rtnVal.forEach(function(item,index){
		var type = eval('('+item.type+')');
		var behaviors = eval('('+item.behaviors+')');
		$(behaviorTypeSelect).append('<option value="'+type.value+'">'+type.text+'</option>');
		//behaviorTypeSelect.options.add(new Option(type.text,type.value));
		var obj = {
			key : type.value,
			value : behaviors
		}
		Behaviors.push(obj);
	})
}

/*选择扩展行为类型*/
window.changeBehaviorType = function(obj){
	var value = obj.value;
	var behaviorSelect = $("#behavior")[0];
	$(behaviorSelect).html("");
	$(behaviorSelect).append('<option value="">请选择</option>');
	//behaviorSelect.options.length = 0;
	//behaviorSelect.options.add(new Option("请选择",""));
	Behaviors.forEach(function(item, index){
		if(value == item.key){
			for(var i=0; i<item.value.length; i++){
				var behavior = item.value[i];
				$(behaviorSelect).append('<option value="'+behavior.value+'">'+behavior.text+'</option>');
				//behaviorSelect.options.add(new Option(behavior.text,behavior.value));
			}
		}
	})
}

/*选择扩展行为*/
window.changeBehavior = function(obj){
	var value = obj.value;
	var text = $(obj).find("option[value='"+value+"']").eq(0).text();
	var typeValue = $("#behaviorType").val();
	var fdResultObj = document.createElement("input");
	fdResultObj.type = "hidden";
	fdResultObj.name = "fdResult";
	fdResultObj.value = '{"typeValue":"'+typeValue+'","behaviorValue":"'+value+'"}';
	$("#extendResultArea").append(fdResultObj);
	var fdDisResultObj = document.createElement("input");
	fdDisResultObj.type = "hidden";
	fdDisResultObj.name = "fdDisResult";
	fdDisResultObj.value = text;
	$("#extendResultArea").append(fdDisResultObj);
}

/*检查是否已经设置*/
window.checkContent = function(){
	var ruleSetRule = parameter.ruleSetRule;
	var currentRule = parameter.currentRule;
	var fdResultMode = $("[name='fdResultMode']:checked").val();
	var rtnVal = {};
	var resultObj = {};
	
	//校验并设置结果内容
	if(fdResultMode == "orgMatrix"){
		//矩阵组织
		if(validateMatrix()){
			resultObj = {
				fdDisResult:$("input[name=orgMatrixName]")[0].value + "{" + $("input[name=matrixResultName]")[0].value + "}",
				fdResult:writeMatrixOrgJSON()
			}
		}else{
			return;
		}
	}else{
		var fdDisResultObj = $("[name='fdDisResult']")[0];
		var fdResultObj = $("[name='fdResult']")[0];
		/*#146936-当结果类型为数字的时候，没有进行校验-开始*/
		var attrType = $("input[name = 'fdDisResult']").attr("inType");
		/*#146936-当结果类型为数字的时候，没有进行校验-结束*/
		if(fdDisResultObj && fdResultObj){
			if(!fdDisResultObj.value && !fdResultObj.value){
				alert(Data_GetResourceString('sys-rule:sysRuleSetRule.validate.fdContent.empty'));
				return;
			}
			resultObj = {
				fdDisResult:fdDisResultObj.value,
				fdResult:fdResultObj.value
			};
		}else if(fdDisResultObj){
			if(!fdDisResultObj.value){
				alert(Data_GetResourceString('sys-rule:sysRuleSetRule.validate.fdContent.empty'));
				return;
			}
			resultObj = {
				fdDisResult:fdDisResultObj.value,
				fdResult:fdDisResultObj.value
			};
		}else{
			alert(Data_GetResourceString('sys-rule:sysRuleSetRule.validate.fdContent.empty'));
			return;
		}
		/*#146936-当结果类型为数字的时候，没有进行校验-开始*/
		if (attrType != null && attrType != "" && attrType == "isNumber") {
			if (isNaN(fdDisResultObj.value) && !/[1]+.?[0-9]*$/.test(fdDisResultObj.value)) {
				alert(Data_GetResourceString('sys-rule:sysRuleSetRule.validate.isNumber'));
				return;
			}
		}
		/*#146936-当结果类型为数字的时候，没有进行校验-结束*/
	}
	currentRule.fdResult = resultObj.fdResult;
	currentRule.fdDisResult = resultObj.fdDisResult;
	currentRule.fdResultMode = $("[name='fdResultMode']:checked").val();;
	//设置paramIds和paramNames
	var params = parameter.ruleSetParam.getAllParams();
	var paramIds = currentRule.paramIds || "";
	var paramNames = currentRule.paramNames || "";
	/*for(var i=0; i<params.length; i++){
		var param = params[i];
		if(currentRule.fdResult.indexOf(param.paramId) != -1){
			if(paramIds.indexOf(param.paramId) == -1){
				paramIds += param.paramId+";";
				paramNames += param.paramName+";";
			}
		}
	}*/
	//添加矩阵组织的参数，用于添加参数
	var conditionParams = $("select[name='conditionParam']");
	conditionParams.each(function(index,obj){
		if(paramIds.indexOf(conditionParams[index].value) == -1){
			paramIds += conditionParams[index].value + ";";
			paramNames += $(conditionParams[index]).children("option:checked").text() + ";";
		}
	});
	currentRule.paramIds = paramIds;
	currentRule.paramNames = paramNames;
	rtnVal = {
			id:resultObj.fdResult,
			name:resultObj.fdDisResult
	}
	//设置弹窗返回值
	dialogObject.rtnData = [rtnVal];
	dialogObject.AfterShow();
	//关闭窗口
	close();
}

/*公式定义器*/
window.formulaEdit = function(type){
	var currentRule = parameter.currentRule;
	var params = parameter.ruleSetParam.getAllParams();
	var dialog = new KMSSDialog();
	var varInfo = [];
	//构造变量数组(参数)
	for(var i=0; i<params.length; i++){
		var param = params[i];
		var paramType = getMutilTypeVal(param.paramType,param.isMulti);
		varInfo.push({"name":param.paramId,"label":param.paramName,"type":getRealTypeVal(paramType),"controlType":"address","orgType":formateOrgType(param.paramType)});
	}
	var funcBean = "sysFormulaFuncTree";
	var model = "com.landray.kmss.sys.rule.model.SysRuleSetRule";
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	//结果
	var rtnType = getMutilTypeVal(currentRule.returnType,currentRule.isMulti);
	dialog.BindingField("fdResult", "fdDisResult");
	dialog.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType:getRealTypeVal(rtnType),
			funcs:"",
			model:model==null?"":model};
	
	dialog.SetAfterShow(function(){
		
	});
	dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_rule/dialog_formula.jsp";
	dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
}

//----矩阵组织start----
DocList_Info.push("conditionParamList");
MatrixConditions = null; //矩阵组织条件字段集
ConditionInfo = new Object(); //矩阵组织条件字段信息集

// 选择矩阵组织
window.selectOrgMatrix = function() {
	var dialog = new KMSSDialog(false, false);
	dialog.winTitle = Data_GetResourceString("sys-rule:rule.orgMatrix.select");
	var treeTitle = Data_GetResourceString("sys-rule:rule.orgMatrix");
	var node = dialog.CreateTree(treeTitle);
	node.AppendBeanData("sysOrgMatrixService&parent=!{value}", null, null, true, null);
	dialog.notNull = true;
	dialog.BindingField('orgMatrixId', 'orgMatrixName');
	dialog.SetAfterShow(function(rtnData){
		if(rtnData!=null){
			initMatrixConditionInfo(this.rtnData.GetHashMapArray()[0].id);
			clearOrgMatrixParamInfo();
		}
	});
	dialog.Show();
}

// 加载矩阵组织条件字段信息
window.initMatrixConditionInfo = function(sysOrgMatrixFdId){
	if (!sysOrgMatrixFdId) {
		sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	}
	var dataBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=1';
	MatrixConditions = new KMSSData().AddBeanData(dataBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId)).GetHashMapArray();
	ConditionInfo = new Object();
	MatrixConditions.forEach(function(condition, index){
		ConditionInfo[condition.value] = condition;
	});
}

// 清除矩阵组织相关参数的设置
window.clearOrgMatrixParamInfo = function() {
	$("input[name=matrixResultId]").val("");
	$("input[name=matrixResultName]").val("");
	var rows = $("#conditionParamList")[0].rows;
	for (var i = rows.length - 1; i > 0; i --) {
		DocList_DeleteRow(rows[i]);
	}
}

// 选择结果字段
window.selectMatrixResultField = function() {
	var sysOrgMatrixFdId = $("input[name=orgMatrixId]")[0].value;
	if (!sysOrgMatrixFdId) {
		var msg = Data_GetResourceString("sys-rule:rule.orgMatrix.checkIfEmpty");
		alert(msg);
		return;
	}
	var treeBean = 'sysOrgMatrixService&id={sysOrgMatrixFdId}&rtnType=2';
	Dialog_Tree(true, "matrixResultId", "matrixResultName", null, treeBean.replace("{sysOrgMatrixFdId}",sysOrgMatrixFdId), '选择矩阵结果');
}

// 添加条件字段赋值设置行
window.addMatrixOrgConditionParamRow = function(){
	if (MatrixConditions == null || MatrixConditions.length == 0) {
		var msg = Data_GetResourceString("sys-rule:rule.orgMatrix.checkIfEmpty");
		alert(msg);
		return;
	}
	var fieldValues = new Object();
	var row = DocList_AddRow("conditionParamList",null,fieldValues);
	var conditionParamSelect = $(row).find("select[name='conditionParam']");
	MatrixConditions.forEach(function(condition, index){
		conditionParamSelect.append("<option value='"+condition.value+"'>"+condition.text+"</option>");
		if (index == 0) {
			conditionParamChange(conditionParamSelect[0]);
		}
	});
	return row;
}

//条件字段切换
window.conditionParamChange = function(self,type){
	var conditionTypeSelect = $(self).next("select");
	if (type) {
		conditionTypeSelect.val(type);
	} else {
		//切换条件字段时自动还原ID/Name下拉框的默认值
		conditionTypeSelect.val("fdId");
	}
	if (ConditionInfo[self.value]["type"] == "constant") {
		conditionTypeSelect.val("fdName");
		//常量类型不需要出现ID/Name的下拉选择
		conditionTypeSelect[0].style.display = "none";
		self.style.width = "100%";
	} else {
		//对象类型显示出ID/Name下拉框供选择
		conditionTypeSelect[0].style.display = "";
		self.style.width = "60%";
	}
	//悬浮可查看该条件字段的具体类型
	self.title = ConditionInfo[self.value]["type"];
}

// 保存矩阵组织相关配置信息到节点定义里
window.writeMatrixOrgJSON = function() {
	var fdResultMode = $("[name='fdResultMode']:checked").val();
	if(fdResultMode != "orgMatrix"){
		return;
	}
	var config = [];
	config.push("{\"id\":");
	config.push("\"" + $("input[name=orgMatrixId]")[0].value + "\"");
	config.push(",\"idText\":");
	config.push("\"" + $("input[name=orgMatrixName]")[0].value + "\"");
	config.push(",\"results\":");
	config.push("\"" + $("input[name=matrixResultId]")[0].value + "\"");
	config.push(",\"resultsText\":");
	config.push("\"" + $("input[name=matrixResultName]")[0].value + "\"");
	config.push(",\"option\":");
	config.push("\"" + $('input[type=radio][name=matrixResultOption]:checked').val() + "\"");
	
	config.push(",\"conditionals\":");
	config.push(getConditionsParamJson());
	config.push("}");
	
	return config.join("").replace(/"/ig,"'");
}

window.getConditionsParamJson = function() {
	var rtn = [];
	var paramIds = "";
	var paramNames = "";
	var currentRule = parameter.currentRule;
	
	var conditionParams = $("select[name='conditionParam']");
	var conditionTypes = $("select[name='conditionType']");
	
	conditionParams.each(function(index,obj){
		rtn.push("{\"id\":\"" + conditionParams[index].value + "\""
				+ ",\"type\":\"" + conditionTypes[index].value + "\""
				+ ",\"value\":\"\""
			    + ",\"text\":\"\"}");
		paramIds += conditionParams[index].value + ";";
		paramNames += $(conditionParams[index]).children("option:checked").text() + ";";
	});
	currentRule.matrixConditions= MatrixConditions;
	
	return "[" + rtn.join(",") + "]";
}

window.formatJson = function(value) {
	return value.replace(/"/ig,'\\"').replace(/\r\n/ig,'\\r\\n');
}

// 填充矩阵设置信息
window.initOrgMatrixInfo = function(srcObj) {
	if(!srcObj || !srcObj.value || !srcObj.value.fdResult){
		return;
	}
	var config = srcObj.value.fdResult;
	if (config != null && config != "") {
		$("#orgMatrixArea").show();
		$("#resultControlArea").hide();
		var json = (new Function("return ("+ config + ")"))();
		//矩阵组织
		$("input[name=orgMatrixId]")[0].value = json.id;
		$("input[name=orgMatrixName]")[0].value = json.idText;
		initMatrixConditionInfo(json.id);
		//结果字段
		$("input[name=matrixResultId]")[0].value = json.results;
		$("input[name=matrixResultName]")[0].value = json.resultsText;
		$("input:radio[name=matrixResultOption][value="+json.option+"]").attr('checked','true');
		//条件字段参数表
		json.conditionals.forEach(function(obj,index){
			var row = addMatrixOrgConditionParamRow();
			var conditionParamSelect = $(row).find("select[name='conditionParam']");
			conditionParamSelect.val(obj.id);
			conditionParamChange(conditionParamSelect[0],obj.type);
		});
	}
}

//校验
window.validateMatrix = function(){
	if ($("input[name=orgMatrixId]")[0].value != "") {
		if (!$("input[name=matrixResultId]")[0].value) {
			var msg = Data_GetResourceString("sys-rule:rule.orgMatrix.checkIfResultEmpty");
			alert(msg);
			return false;
		}
		if ($("select[name='conditionParam']").length == 0) {
			var msg = Data_GetResourceString("sys-rule:rule.orgMatrix.checkIfConditionEmpty");
			alert(msg);
			return false;
		}
	}else{
		alert(Data_GetResourceString("sys-rule:sysRuleSetRule.validate.1"));
		return false;
	}
	return true;
}
//----矩阵组织end----
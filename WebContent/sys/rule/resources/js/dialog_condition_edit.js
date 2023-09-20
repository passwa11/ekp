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
var extendFunsDesc = null;
Com_AddEventListener(window,"load",function(){
	//初始化控件
	var currentRule = parameter.currentRule;
	var params = parameter.ruleSetParam.getAllParams();
	var isExitOrgParam = false;
	params.forEach(function(param,index){
		if(param.paramType && param.paramType.indexOf("ORG_TYPE_") != -1 && param.isMulti != "1"){
			isExitOrgParam = true;
		}
	})
	if(isExitOrgParam){
		//若不存在组织架构类型的参数，则不需要提供该选择
		$("[name='fdConditionMode'][value='org']").parents("label").eq(0).show();
	}
	//初始化条件控件
	var cdObj = {
		mode:$("[name='fdConditionMode']:checked").val(),
		rtnType:"Boolean",
		value:null,
		idField:"fdCondition",
		nameField:"fdDisCondition",
		type:"condition",
		control:"textarea"
	};
	//设置值
	var cdValueObj = {};
	cdValueObj.fdCondition = currentRule.fdCondition;
	cdValueObj.fdDisCondition = currentRule.fdDisCondition;
	cdObj.value = cdValueObj;
	//条件设置
	var fdConditionMode = currentRule.fdConditionMode;
	if(fdConditionMode){
		$("input[name='fdConditionMode'][value='"+fdConditionMode+"']").attr("checked",true);
		cdObj.mode = fdConditionMode;
	}
	if(isExitOrgParam && fdConditionMode == "org"){
		//组织架构比较（组织架构元素和固定值比较）
		$("#orgArea").show();
		$("#cdControlArea").hide();
		//解析公式并重新设置值
		var argObj = window.parseFormula(currentRule.fdCondition);
		var paramId = argObj.arg1;
		var orgId = argObj.arg2;
		var fun = argObj.fun;
		$("select[name='orgCompare']").val(fun);
		argObj = window.parseFormula(currentRule.fdDisCondition);
		var orgName = argObj.arg2;
		var paramObj = parameter.ruleSetParam.getParamById(paramId);
		//刷新页面
		var paramSelect = $("select[name='param']")[0];
		$(paramSelect).html();
		//paramSelect.options.length = 0;
		params.forEach(function(param,index){
			//只包含组织架构类型的参数
			if(param.paramType && param.paramType.indexOf("ORG_TYPE_") != -1 && param.isMulti != "1"){
				$(paramSelect).append('<option value="'+param.paramId+'">'+param.paramName+'</option>');
				//paramSelect.options.add(new Option(param.paramName, param.paramId));
				isExitParam = true;
			}
		})
		$(paramSelect).val(paramId);
		//根据默认值创建对应的控件
		cdObj.rtnType = paramObj ? paramObj.paramType : "ORG_TYPE_ALL";
		var parent = document.getElementById("pControlArea");
		createControl(cdObj,parent);
		//更新页面的值
		$("input[name='fdCondition']").val(orgId);
		$("input[name='fdDisCondition']").val(orgName);
	}else if(fdConditionMode == "fixed"){
		var parent = document.getElementById("cdControlArea");
		$(parent).html("");
		$(parent).html("TRUE");
	}else{
		var parent = document.getElementById("cdControlArea");
		createControl(cdObj,parent);
	}
	
	//初始化公式说明
	showExtendFunsDesc();
	
	/*条件模式切换*/
	$("[name='fdConditionMode']").change(function(){
		var ruleSetRule = parameter.ruleSetRule;
		var currentRule = parameter.currentRule;
		var srcObj = {
				mode:this.value,
				rtnType:null,
				value:null,
				idField:"fdCondition",
				nameField:"fdDisCondition",
				type:"condition",
				control:'textarea'
			}
		if(this.value == "org"){
			//组织架构比较（组织架构元素和固定值比较）
			$("#orgArea").show();
			$("#cdControlArea").hide();
			$("#cdControlArea").empty();
			//初始化参数选择框
			var params = parameter.ruleSetParam.getAllParams();
			var paramSelect = $("select[name='param']")[0];
			$(paramSelect).html("");
			//paramSelect.options.length = 0;
			var selectObj;
			params.forEach(function(param,index){
				//只包含组织架构类型的参数
				if(param.paramType && param.paramType.indexOf("ORG_TYPE_") != -1 && param.isMulti != "1"){
					$(paramSelect).append('<option value="'+param.paramId+'">'+param.paramName+'</option>');
					//paramSelect.options.add(new Option(param.paramName, param.paramId));
					if(!selectObj){
						selectObj = param;
					}
				}
			})
			//设置默认值为第一个
			if(selectObj){
				$(paramSelect).val(selectObj.paramId);
			}
			//根据默认值创建对应的控件
			srcObj.rtnType = "ORG_TYPE_ALL";
			var parent = document.getElementById("pControlArea");
			createControl(srcObj,parent);
			//显示对应的函数提示
			showExtendFunsDesc();
		}else if(this.value == "fixed"){//固定值
			$("#cdControlArea").show();
			$("#orgArea").hide();
			$("#pControlArea").empty();
			var parent = document.getElementById("cdControlArea");
			$(parent).html("TRUE");
		}else{
			//公式定义
			$("#cdControlArea").show();
			$("#orgArea").hide();
			$("#pControlArea").empty();
			//构造源对象
			srcObj.rtnType = "Boolean";
			var parent = document.getElementById("cdControlArea");
			createControl(srcObj,parent);
		}
		//清空值
		$("[name='fdCondition']").val("");
		$("[name='fdDisCondition']").val("");
	});
});

/*获取规则扩展的函数描述*/
window.getExtendFunsDesc = function(){
	var model = "com.landray.kmss.sys.rule.model.SysRuleSetRule";
	var beanName = "getExtendFunsDescService&model="+model;
	extendFunsDesc = new KMSSData().AddBeanData(beanName).GetHashMapArray();
	//转换格式
	return extendFunsDesc;
}
/**
 * 根据对应的函数获取描述
 */
window.getExtendDescByFunc = function(extendFunsDesc,currentFunc){
	var obj = {};
	if(extendFunsDesc){
		extendFunsDesc.forEach(function(item,index){
			var func = item.func;
			if(func === currentFunc){
				obj.summary = item.summary;
				obj.text = item.text;
				obj.title = item.title;
				obj.value = item.value;
				obj.key = item.key;
			}
		})
	}
	return obj;
}

/**显示对应函数的描述*/
window.showExtendFunsDesc = function(value){
	/*#146551-规则设置-条件内容选择范围前后发生变化-开始*/
	/* 切换“比较关系”的时候，根据“条件值”和“比较关系符”匹配出新的参数类型，重新传递给点击“选择”按钮弹出的地址本中Dialog_Address的查询参数-开始 */
	var cleanVal = value;
	var term = $("select[name='param']").val();
	var currentRule = parameter.currentRule;
	var cdValueObj = {};
	cdValueObj.fdCondition = currentRule.fdCondition;
	cdValueObj.fdDisCondition = currentRule.fdDisCondition;
	if (term != "" && term != null && !(typeof(term) == "undefined")) {
		var srcObj = {
			mode: "org",
			rtnType: "",
			value: null,
			idField: "fdCondition",
			nameField: "fdDisCondition",
			type: "condition"
		}
		srcObj.value = cdValueObj;
		var params = parameter.ruleSetParam.getAllParams();
		var paramObj = parameter.ruleSetParam.getParamById(term, params);
		// 根据默认值创建对应的控件
		srcObj.rtnType = paramObj.paramType;
		if (!value) {
			value = $("select[name='orgCompare']").val();
		}
		srcObj.rtnType = corresponding(srcObj.rtnType, value);
		var parent = document.getElementById("pControlArea");
		createControl(srcObj, parent);
		var argObj = window.parseFormula(currentRule.fdCondition);
		//如果取不到argObj的情况下，依然执行argObj.arg2会报错，这里进行过滤掉。
		//argObj取不到的情况，是因为没有设置任何参数，直接点击的设置。
		if(argObj){
			var orgId = argObj.arg2;
			argObj = window.parseFormula(currentRule.fdDisCondition);
			var orgName = argObj.arg2;
			//更新页面的值
			if(cleanVal){
				//切换运算符的时清空右侧匹配的值
				$("input[name='fdCondition']").val("");
				$("input[name='fdDisCondition']").val("");
			}else{
				$("input[name='fdCondition']").val(orgId);
				$("input[name='fdDisCondition']").val(orgName);
			}
		}
	}
	/* 切换“比较关系”的时候，根据“条件值”和“比较关系符”匹配出新的参数类型，重新传递给点击“选择”按钮弹出的地址本中Dialog_Address的查询参数-结束 */
	/*#146551-规则设置-条件内容选择范围前后发生变化-结束*/
	var funsDesc;
	if(extendFunsDesc){
		funsDesc = extendFunsDesc;
	}else{
		funsDesc = this.getExtendFunsDesc();
	}
	var currentFunc;
	if(value && value != ""){
		currentFunc = value;
	}else{
		currentFunc = $("select[name='orgCompare']").val();
	}
	if(currentFunc && currentFunc !=""){
		var descObj = getExtendDescByFunc(funsDesc, currentFunc);
		var descHtml = "说明：<br>" + descObj.summary + "<br>" + descObj.value;
		$("#funcDescArea").html(descHtml);
	}else{
		$("#funcDescArea").html("");
	}
}

/*切换选择参数*/
window.selectParam = function(value, obj){
	var currentRule = parameter.currentRule;
	var srcObj = {
		mode:"org",
		rtnType:null,
		value:null,
		idField:"fdCondition",
		nameField:"fdDisCondition",
		type:"condition"
	}
	//初始化参数选择框
	var params = parameter.ruleSetParam.getAllParams();
	var paramObj=parameter.ruleSetParam.getParamById(value, params);
	//根据默认值创建对应的控件
	srcObj.rtnType = paramObj.paramType;

	/*#146551-规则设置-条件内容选择范围前后发生变化-开始*/
	/* 切换“条件值”的时候，根据“条件值”和“比较关系符”匹配出新的参数类型，重新传递给点击“选择”按钮弹出的地址本中Dialog_Address的查询参数-开始 */
	//切换左侧条件值时候，清空右侧的条件值
	$("input[name='fdCondition']").val("");
	$("input[name='fdDisCondition']").val("");
	var mark = $("select[name='orgCompare']").val();
	srcObj.rtnType = corresponding(srcObj.rtnType, mark);
	/* 切换“条件值”的时候，根据“条件值”和“比较关系符”匹配出新的参数类型，重新传递给点击“选择”按钮弹出的地址本中Dialog_Address的查询参数-结束 */
	/*#146551-规则设置-条件内容选择范围前后发生变化-结束*/
	
	var parent = document.getElementById("pControlArea");
	createControl(srcObj,parent);
}

//切换函数
window.switchFun = function(value, obj){
	showExtendFunsDesc(value);
}

/*检查是否已经设置*/
window.checkContent = function(){

	var ruleSetRule = parameter.ruleSetRule;
	var currentRule = parameter.currentRule;
	var rtnVal = {};
	var conditionObj = {};
	
	//设置条件内容
	var fdDisConditionObj = $("[name='fdDisCondition']")[0];
	var fdConditionObj = $("[name='fdCondition']")[0];
	var fdConditionMode = $("[name='fdConditionMode']:checked").val();
	var conditionObj = {};
	if(fdDisConditionObj && fdConditionObj){
		conditionObj = {
			fdDisCondition:fdDisConditionObj.value,
			fdCondition:fdConditionObj.value
		};
	}else if(fdDisConditionObj){
		conditionObj = {
			fdDisCondition:fdDisConditionObj.value,
			fdCondition:fdDisConditionObj.value
		};
	}else{
		if($("#cdControlArea").text() == "TRUE"){
			conditionObj.fdDisCondition = "true";
			conditionObj.fdCondition = "true";
		}
	}
	if(fdDisConditionObj && (!fdDisConditionObj.value || fdDisConditionObj.value == "")){
		//条件为空时默认为true
		//conditionObj.fdDisCondition = "true";
		//conditionObj.fdCondition = "true";
		alert(Data_GetResourceString('sys-rule:sysRuleSetRule.validate.fdContent.empty'));
		return;
	}
	//若条件模式是组织架构比较，重新构造公式
	if(fdConditionMode == "org"){
		var params = parameter.ruleSetParam.getAllParams();
		//获取选择的参数，构造公式
		var paramId = $("select[name='param']").val();
		var fdCondition = conditionObj.fdCondition;
		var fdDisCondition = conditionObj.fdDisCondition;
		var paramObj = parameter.ruleSetParam.getParamById(paramId, params);
		//构造
		var oper = $("[name='orgCompare']").val();
		fdCondition = getFormula(oper,paramId, fdCondition);
		fdDisCondition = fdCondition.replace(paramId,paramObj.paramName).replace(conditionObj.fdCondition,fdDisCondition);
		conditionObj.fdCondition = fdCondition;
		conditionObj.fdDisCondition = fdDisCondition;
	}
	currentRule.fdCondition = conditionObj.fdCondition;
	currentRule.fdDisCondition = conditionObj.fdDisCondition;
	currentRule.fdConditionMode = fdConditionMode;
	/*//设置paramIds和paramNames
	var params = parameter.ruleSetParam.getAllParams();
	var paramIds = currentRule.paramIds || "";
	var paramNames = currentRule.paramNames || "";
	for(var i=0; i<params.length; i++){
		var param = params[i];
		if(currentRule.fdCondition.indexOf(param.paramId) != -1){
			if(paramIds.indexOf(param.paramId) == -1){
				paramIds += param.paramId+";";
				paramNames += param.paramName+";";
			}
		}
	}
	currentRule.paramIds = paramIds;
	currentRule.paramNames = paramNames;*/
	rtnVal = {
			id:conditionObj.fdCondition,
			name:conditionObj.fdDisCondition
	}
	//设置弹窗返回值
	dialogObject.rtnData = [rtnVal];
	dialogObject.AfterShow();
	//关闭窗口
	close();
}

/*若条件模式为org,构造新的公式*/
window.getFormula = function(oper, arg1, arg2){
	var funName;
	if(oper == "equal"){
		funName = "$规则.两个组织架构元素是否相同$";
	}else if(oper == "notEqual"){
		funName = "$规则.两个组织架构元素是否不同$";
	}else if(oper == 'belong'){
		funName = "$规则.某元素属于某部门或者某机构$";
	}else{
		funName = "$规则.某元素不属于某部门门或者某机构$";
	}
	arg1 = "$"+arg1+"$";
	var formula = funName + "(" + arg1 + ",\"" + arg2 + "\")";
	return formula;
}

/*若条件模式为org,解析公式，得到参数id和组织架构id*/
window.parseFormula = function(formula){
	/*#146551-规则设置-条件内容选择范围前后发生变化-开始*/
	if(formula == ""){
		return;
	}
	/*#146551-规则设置-条件内容选择范围前后发生变化-结束*/
	//左括号的位置
	var leftIndex = formula.indexOf("(");
	//右括号的位置
	/*#149994-保存后再打开人员名字被截断了-开始*/
	/*var rightIndex = formula.indexOf(")");*/
	var rightIndex = formula.lastIndexOf(")");
	/*#149994-保存后再打开人员名字被截断了-结束*/
	//函数
	var fun = formula.substring(0, leftIndex);
	formula = formula.substring(leftIndex+1,rightIndex);
	var args = formula.split(",");
	var argObj = {};
	argObj.arg1 = args[0].substring(1,args[0].length-1);
	argObj.arg2 = args[1].substring(1,args[1].length-1);
	if(fun == "$规则.两个组织架构元素是否相同$"){
		argObj.fun = "equal";
	}else if(fun == "$规则.两个组织架构元素是否不同$"){
		argObj.fun = "notEqual";
	}else if(fun == '$规则.某元素属于某部门或者某机构$'){
		argObj.fun = "belong";
	}else{
		argObj.fun = "notBelong";
	}
	return argObj;
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
	//条件
	var rtnType = "Boolean";
	dialog.BindingField("fdCondition", "fdDisCondition");
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

/* #146551-规则设置-条件内容选择范围前后发生变化 */
/* 重新处理规则设置的映射关系-开始 */
/* 映射关系举例：人员-等于/不等于-人员，人员-属于/不属于-人员/部门/机构，... */
function corresponding(condition, relation) { // 传入参数（条件内容值，对比关系）
	// 所有的类型表示变量：人员-ORG_TYPE_PERSON，岗位-ORG_TYPE_POST，部门-ORG_TYPE_DEPT，机构-ORG_TYPE_ORG，群组-ORG_TYPE_GROUP；
	// 所有的比较关系表示：等于-equal，不等于-notEqual，属于-belong，不属于-notBelong；
	var repType = "ORG_TYPE_ALL";
	if (condition == "ORG_TYPE_PERSON") { // 人员
		if (relation == "equal" || relation == "notEqual") { // 等于、不等于
			repType = "ORG_TYPE_PERSON"; // 人员
		} else if (relation == "belong" || relation == "notBelong") { // 属于、不属于
			repType = "ORG_TYPE_PERSON | ORG_TYPE_DEPT | ORG_TYPE_ORG"; // 人员、部门、机构
		}
	} else if (condition == "ORG_TYPE_POST") { // 岗位
		if (relation == "equal" || relation == "notEqual") { // 等于、不等于
			repType = "ORG_TYPE_POST"; // 岗位
		} else if (relation == "belong" || relation == "notBelong") { // 属于、不属于
			repType = "ORG_TYPE_POST | ORG_TYPE_DEPT | ORG_TYPE_ORG"; // 岗位、部门、机构
		}
	} else if (condition == "ORG_TYPE_DEPT") { // 部门
		if (relation == "equal" || relation == "notEqual") { // 等于、不等于
			repType = "ORG_TYPE_DEPT"; // 部门
		} else if (relation == "belong" || relation == "notBelong") { // 属于、不属于
			repType = "ORG_TYPE_DEPT | ORG_TYPE_ORG"; // 部门、机构
		}
	} else if (condition == "ORG_TYPE_ORG") { // 机构
		if (relation == "equal" || relation == "notEqual" || relation == "belong" || relation == "notBelong") { // 等于、不等于、属于、不属于
			repType = "ORG_TYPE_ORG"; // 机构
		}
	} else if (condition == "ORG_TYPE_GROUP") { // 群组
		if (relation == "equal" || relation == "notEqual" || relation == "belong" || relation == "notBelong") { // 等于、不等于、属于、不属于
			repType = "ORG_TYPE_GROUP"; // 群组
		}
	}
	return repType;
}
/* 重新处理规则设置的映射关系-结束 */
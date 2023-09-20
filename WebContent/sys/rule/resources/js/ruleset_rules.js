/*规则对象*/
(function(){
	if(window.ruleSetRule){
		return;
	}
	window.ruleSetRule = new createruleSetRule();
	function createruleSetRule(){
		/*属性定义*/
		this.currentRule = new Object();
		/*函数定义*/
		this.init = init;//编辑页面初始化
		this.switchRtnType = switchRtnType;//切换返回类型
		this.switchMulti = switchMulti;//切换多值
		this.editConditionContent = editConditionContent;//编辑条件内容
		this.editResultContent = editResultContent;//编辑结果内容
		this.initCurrentRule = initCurrentRule;//初始化当前规则
	}
	
	function init(fdId){
		//若模式是矩阵组织，则需要将多值设置为不可编辑
		var $isMultiObjs = $("[name^='_sysRuleSetRules'][name$='isMulti']");
		var $selectObjs = $("select[name^='sysRuleSetRules'][name$='returnType']");
		var $fdModeObjs = $("[name^='sysRuleSetRules'][name$='fdResultMode']");
		var $fdDisResultObjs = $("[name^='sysRuleSetRules'][name$='fdDisResult']");
		for(var i=0; i<$selectObjs.length; i++){
			var isMulti = $($isMultiObjs[i]).val();
			var fdType = $($selectObjs[i]).val();
			if(fdType && fdType.indexOf("ORG_TYPE_") != -1){
				$($isMultiObjs[i]).parents("div.isMulti").eq(0).show();
			}
			if(fdType && fdType == 'None'){
				$($fdDisResultObjs[i]).hide();
				$($fdDisResultObjs[i]).next("a").hide();
			}
		}
		for(var i=0; i<$fdModeObjs.length; i++){
			var fdModeObj = $fdModeObjs[i];
			if($(fdModeObj).val() === "orgMatrix"){
				$($isMultiObjs[i]).attr("disabled","disabled");
			}
		}
		//给参数添加分号
		var $paramIdObjs = $("[name^='sysRuleSetRules'][name$='sysRuleSetParamIds']");
		var $paramNameObjs = $("[name^='sysRuleSetRules'][name$='sysRuleSetParamNames']");
		for(var i=0; i<$paramIdObjs.length; i++){
			var paramIds = $($paramIdObjs[i]).val();
			var paramNames = $($paramNameObjs[i]).val();
			if(paramIds && paramIds != ""){
				paramIds += ";";
				$($paramIdObjs[i]).val(paramIds);
			}
			if(paramNames && paramNames != ""){
				paramNames += ";";
				$($paramNameObjs[i]).val(paramNames);
			}
		}
	}
	
	function switchRtnType(value,obj){
		var index = getIndex() - 1;
		
		var $parent = $(obj).parents("td").eq(0);
		if(value && value.indexOf('ORG_TYPE_') !=-1){
			//组织架构类型，提供多选的选项
			$parent.find("div.isMulti").show();
			$("[name='sysRuleSetRules["+index+"].isMulti']").val("");
			$("[name='_sysRuleSetRules["+index+"].isMulti']").removeAttr("checked");
			$("[name='_sysRuleSetRules["+index+"].isMulti']").removeAttr("disabled");
		}else{
			$("[name='sysRuleSetRules["+index+"].isMulti']").val("");
			$parent.find("div.isMulti").hide();
		}
		//解除参数的isEdit = 0
		var fdResult = $("[name='sysRuleSetRules["+index+"].fdResult']").val();
		var params = window.ruleSetParam.getAllParams();
		var updateParams = [];
		for(var j=0; j<params.length; j++){
			var param = params[j];
			if(fdResult.indexOf(param.paramId) != -1){
				if(param.isEdit == '0'){
					param.isEdit = '';
					updateParams.push(param);
				}
			}
		}
		window.ruleSetParam.updateParams(updateParams);
		
		//清空信息
		$("[name='sysRuleSetRules["+index+"].fdResultMode']").val("");
		$("[name='sysRuleSetRules["+index+"].fdResult']").val("");
		$("[name='sysRuleSetRules["+index+"].fdDisResult']").val("");
		$("[name='sysRuleSetRules["+index+"].sysRuleSetParamIds']").val("");
		$("[name='sysRuleSetRules["+index+"].sysRuleSetParamNames']").val("");
		if(value == "None"){
			$($parent.next().children()[0]).hide();
			$($parent.next().children()[1]).hide();
		} else {
			$($parent.next().children()[0]).show();
			$($parent.next().children()[1]).show();
		}
	}
	
	function switchMulti(value,obj){
		var index = getIndex() - 1;
		var fdResultMode = $("[name='sysRuleSetRules["+index+"].fdResultMode']").val();
		var fdResult = $("[name='sysRuleSetRules["+index+"].fdResult']").val();
		var fdDisResult = $("[name='sysRuleSetRules["+index+"].fdDisResult']").val();
		if(fdResultMode == "fixed"){
			//固定值，默认取第一个选择的元素
			fdResult = fdResult.split(";")[0];
			fdDisResult = fdDisResult.split(";")[0];
			$("[name='sysRuleSetRules["+index+"].fdResult']").val(fdResult);
			$("[name='sysRuleSetRules["+index+"].fdDisResult']").val(fdDisResult);
		}
	}
	
	function editConditionContent(){
		var index = getIndex() - 1;
		
		//初始化当前操作的规则对象内容
		this.initCurrentRule(index);
		
		//弹窗
		var dialog = new KMSSDialog();
		dialog.BindingField("sysRuleSetRules["+index+"].fdCondition", "sysRuleSetRules["+index+"].fdDisCondition");
		dialog.parameter = {
			ruleSetRule:window.ruleSetRule,
			ruleSetParam:window.ruleSetParam,
			currentRule:this.currentRule
		};
		dialog.SetAfterShow(function(rtnData){
			var rtnVal = rtnData.GetHashMapArray()[0];
			var fdConditionMode = dialog.parameter.currentRule.fdConditionMode;
			//设置参数
			//var paramIds = dialog.parameter.currentRule.paramIds;
			//var paramNames = dialog.parameter.currentRule.paramNames;
			//设置关联参数
			//$("[name='sysRuleSetRules["+index+"].sysRuleSetParamIds']").val(paramIds);
			//$("[name='sysRuleSetRules["+index+"].sysRuleSetParamNames']").val(paramNames);
			//设置条件信息
			$("[name='sysRuleSetRules["+index+"].fdConditionMode']").val(fdConditionMode);
		});
		dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_rule/dialog_condition_edit.jsp";
		dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
	}
	
	function editResultContent(){
		var index = getIndex() - 1;
		//获取结果类型
		var returnType = $("[name='sysRuleSetRules["+index+"].returnType']").val();
		if(!returnType){
			alert(Data_GetResourceString("sys-rule:sysRuleSetRule.validate.returnType.select"));
			return;
		}
		
		//初始化当前操作的规则对象内容
		this.initCurrentRule(index);
		//预留之前的结果内容
		var lastFdResult = $("[name='sysRuleSetRules["+index+"].fdResult']").val();
		
		//弹窗
		var dialog = new KMSSDialog();
		dialog.BindingField("sysRuleSetRules["+index+"].fdResult", "sysRuleSetRules["+index+"].fdDisResult");
		dialog.parameter = {
			ruleSetRule:window.ruleSetRule,
			ruleSetParam:window.ruleSetParam,
			currentRule:this.currentRule
		};
		dialog.SetAfterShow(function(rtnData){
			var rtnVal = rtnData.GetHashMapArray()[0];
			var fdResultMode = dialog.parameter.currentRule.fdResultMode;
			//设置参数
			var paramIds = dialog.parameter.currentRule.paramIds;
			var paramNames = dialog.parameter.currentRule.paramNames;
			//解除参数的isEdit = 0
			var params = window.ruleSetParam.getAllParams();
			var updateParams = [];
			for(var j=0; j<params.length; j++){
				var param = params[j];
				if(lastFdResult.indexOf(param.paramId) != -1){
					if(param.isEdit == '0'){
						param.isEdit = '';
						updateParams.push(param);
					}
				}
			}
			window.ruleSetParam.updateParams(updateParams);
			
			//设置关联参数
			//$("[name='sysRuleSetRules["+index+"].sysRuleSetParamIds']").val(paramIds);
			//$("[name='sysRuleSetRules["+index+"].sysRuleSetParamNames']").val(paramNames);
			//设置结果信息
			$("[name='sysRuleSetRules["+index+"].fdResultMode']").val(fdResultMode);
			//如果是矩阵组织，需要把参数添加到参数表中
			var matrixConditions = dialog.parameter.currentRule.matrixConditions;
			if(fdResultMode && fdResultMode == "orgMatrix" && matrixConditions && Object.prototype.toString.call(matrixConditions)=='[object Array]'){
				var params = [];
				matrixConditions.forEach(function(matrixCondition, index){
					if(paramIds.indexOf(matrixCondition.value) != -1){
						//被使用的参数
						var paramType = matrixCondition.mainDataType || matrixCondition.type;
						var param = {
								paramId:matrixCondition.value,
								paramType:getRuleTypeFromMatrixType(paramType),
								paramName:matrixCondition.text,
								isEdit:"0"
						}
						params.push(param);
					}
				});
				window.ruleSetParam.addParams(params);
				//将结果类型默认置为多值，且不可编辑
				if($("[name='sysRuleSetRules["+index+"].isMulti']").val() != "1"){
					$("[name='_sysRuleSetRules["+index+"].isMulti']").click();
				}
				$("[name='sysRuleSetRules["+index+"].isMulti']").val("1");
				$("[name='_sysRuleSetRules["+index+"].isMulti']").attr("disabled","disabled");
			}
		});
		dialog.URL = Com_Parameter.ContextPath + "sys/rule/sys_ruleset_rule/dialog_result_edit.jsp";
		dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
	}
	
	function initCurrentRule(index){
		//规则id
		var fdId = $("[name='sysRuleSetRules["+index+"].fdId']").val();
		//规则名
		var fdName = $("[name='sysRuleSetRules["+index+"].fdName']").val();
		//规则结果类型
		var returnType = $("[name='sysRuleSetRules["+index+"].returnType']").val();
		//是否多值
		var isMulti = $("[name='sysRuleSetRules["+index+"].isMulti']").val();
		//条件类型
		var fdConditionMode = $("[name='sysRuleSetRules["+index+"].fdConditionMode']").val();
		//条件显示内容
		var fdDisCondition = $("[name='sysRuleSetRules["+index+"].fdDisCondition']").val();
		//条件隐藏内容
		var fdCondition = $("[name='sysRuleSetRules["+index+"].fdCondition']").val();
		//结果类型
		var fdResultMode = $("[name='sysRuleSetRules["+index+"].fdResultMode']").val();
		//结果显示内容
		var fdDisResult = $("[name='sysRuleSetRules["+index+"].fdDisResult']").val();
		//结果隐藏内容
		var fdResult = $("[name='sysRuleSetRules["+index+"].fdResult']").val();
		//参数ids
		var paramIds = $("[name='sysRuleSetRules["+index+"].sysRuleSetParamIds']").val();
		//参数names
		var paramNames = $("[name='sysRuleSetRules["+index+"].sysRuleSetParamNames']").val();
		
		this.currentRule = new Object();
		this.currentRule.fdId = fdId;
		this.currentRule.fdName = fdName;
		this.currentRule.returnType = returnType;
		this.currentRule.isMulti = isMulti;
		this.currentRule.fdConditionMode = fdConditionMode;
		this.currentRule.fdDisCondition = fdDisCondition;
		this.currentRule.fdCondition = fdCondition;
		this.currentRule.fdResultMode = fdResultMode;
		this.currentRule.fdDisResult = fdDisResult;
		this.currentRule.fdResult = fdResult;
		this.currentRule.paramIds = paramIds || "";
		this.currentRule.paramNames = paramNames || "";
	}
})();
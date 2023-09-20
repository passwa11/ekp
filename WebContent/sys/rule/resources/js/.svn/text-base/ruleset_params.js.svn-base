/*参数对象*/
(function(){
	if(window.ruleSetParam){
		return;
	}
	window.ruleSetParam = new createRuleSetParam();
	
	function createRuleSetParam(){
		/*属性定义*/
		this.paramTypes = [];
		this.newParamIds = [];
		this.fields = [];
		/*函数定义*/
		this.init = init;//编辑初始化
		this.getAllParams = getAllParams;//获取所有的参数
		this.switchType = switchType;//切换类型
		this.addParams = addParams;//动态添加多个参数到参数列表
		this.updateParams = updateParams;//更新参数的状态
		this.getParamIndexById = getParamIndexById;//根据参数id获取参数在参数列表中对应的角标
		this.getParamById = getParamById;//根据参数id从参数集合中获取参数
		this.getParamTypeByValue = getParamTypeByValue;//从参数类型数组中获取对应值得参数类型
		//========以下方法只在规则机制映射新增规则集文档时使用==========
		this.initFieldsSelect = initFieldsSelect;//初始化字段选择框
		this.initFieldsSelectByParamType = initFieldsSelectByParamType;//根据参数类型初始化字段选择框
		this.getMatchParamType = getMatchParamType;//过滤和当前参数类型集不匹配的表单字段类型
		this.switchField = switchField;//切换字段
		this.getParamTypes = getParamTypes;//获取所有可能的参数类型
	}
	
	function init(fdId){
		var $objs = $("input[name^='sysRuleSetParams'][name$='fdId']");
		var $selectObjs = $("select[name^='sysRuleSetParams'][name$='fdType']");
		var $isMultiObjs = $("input[name^='_sysRuleSetParams'][name$='isMulti']");
		var $delBtnObjs = $(".paramDelBtn");
		//获取当前规则集规则关联的参数
		var params = new KMSSData().AddBeanData("sysRuleSetDocService&fdId="+fdId+"&type=ruleParam").GetHashMapArray();
		for(var i=0; i<$objs.length; i++){
			var fdId = $objs[i].value;
			var isUse = false;
			for(var j=0; j<params.length; j++){
				if(fdId == params[j].paramId){
					isUse = true;
					$($selectObjs[i]).attr("disabled","disabled");
					$($selectObjs[i]).css("color","#b4b4b4");
					$($selectObjs[i]).css("border","0");
					$($selectObjs[i]).css("border-bottom","1px #b4b4b4 solid");
					$($isMultiObjs[i]).attr("disabled","disabled");
					$($delBtnObjs[i]).remove();
					break;
				}
			}
			if(!isUse){
				this.newParamIds.push(fdId);
			}
		}
		
		//根据参数是否可编辑进行初始化(矩阵组织增加的参数）
		var $isEditObjs = $("input[name^='sysRuleSetParams'][name$='isEdit']");
		for(var i=0; i<$isEditObjs.length; i++){
			var isEditObj = $isEditObjs[i];
			if($(isEditObj).val() == "0"){
				//不可编辑
				$($selectObjs[i]).attr("disabled","disabled");
				$($selectObjs[i]).css("color","#b4b4b4");
				$($selectObjs[i]).css("border","0");
				$($selectObjs[i]).css("border-bottom","1px #b4b4b4 solid");
				$($isMultiObjs[i]).attr("disabled","disabled");
				$($delBtnObjs[i]).remove();
			}
		}
		//设置组织架构类型的多值框为显示
		var $selectObjs = $("select[name^='sysRuleSetParams'][name$='fdType']");
		for(var i=0; i<$selectObjs.length; i++){
			var isMulti = $($isMultiObjs[i]).val();
			var fdType = $($selectObjs[i]).val();
			if(fdType && fdType.indexOf("ORG_TYPE_") != -1){
				$($isMultiObjs[i]).parents("div.isMulti").eq(0).show();
			}
		}
	}
	
	function getAllParams(){
		//获取参数表格的行数
		var paramLen = $("#paramSetting").find("tr").length - 1;
		
		//循环构建对象
		var params = [];
		for(var i=0; i<paramLen; i++){
			var param = {};
			//参数名
			var paramName = $("[name='sysRuleSetParams["+i+"].fdName']").val();
			//参数类型
			var paramType = $("[name='sysRuleSetParams["+i+"].fdType']").val();
			//参数id
			var paramId = $("[name='sysRuleSetParams["+i+"].fdId']").val();
			//是否多值
			var isMulti = $("[name='sysRuleSetParams["+i+"].isMulti").val();
			//是否可编辑
			var isEdit = $("[name='sysRuleSetParams["+i+"].isEdit").val();
			//同时存在才加入到数组中
			if(paramName && paramName != "" && paramType && paramId){
				//设置
				param['index'] = i;
				param['paramName'] = paramName;
				param['paramType'] = paramType;
				param['paramId'] = paramId;
				param['isMulti'] = isMulti;
				param['isEdit'] = isEdit;
				params.push(param);
			}
		}
		return params;
	}
	
	function switchType(value,obj){
		var $parent = $(obj).parents("td").eq(0);
		if(value && value.indexOf('ORG_TYPE_') !=-1){
			//组织架构类型，提供多选的选项
			$parent.find("div.isMulti").show();
		}else{
			$parent.find("div.isMulti").hide();
		}
		$parent.find("div.isMulti").find("input[name^='sysRuleSetParams'][name$='isMulti']").val("");
		$parent.find("div.isMulti").find("input[name^='_sysRuleSetParams'][name$='isMulti']").removeAttr("checked");
	}
	
	function updateParams(params){
		if(params && Object.prototype.toString.call(params)=='[object Array]'){
			var paramObjs = $("[name^='sysRuleSetParams'][name$='fdId']");
			for(var i=0; i<paramObjs.length; i++){
				var paramObj = paramObjs[i];
				var fdId = paramObj.value;
				for(var j=0; j<params.length; j++){
					if(params[j].paramId == fdId){//更新isEdit状态
						$("[name='sysRuleSetParams["+i+"].isEdit']").val(params[j].isEdit || "");
					}
				}
			}
		}
	}
	
	function addParams(params){
		if(params && Object.prototype.toString.call(params)=='[object Array]'){
			//获取角标i
			var i = $("table#paramSetting").find("tr").length - 1;
			var paramTypes = this.getParamTypes();
			for(var j=0; j<params.length; j++){
				var param = params[j];
				var paramIndex = this.getParamIndexById(param.paramId);
				if(paramIndex != -1){
					//若参数已经存在，更新属性值
					$("[name='sysRuleSetParams["+i+"].fdName']").val(param.paramName);
					$("[name='sysRuleSetParams["+i+"].fdType']").val(param.paramType);
					$("[name='sysRuleSetParams["+i+"].isEdit']").val(param.isEdit || "");
					var paramTypeObj = this.getParamTypeByValue(param.paramType, paramTypes);
					if(paramTypeObj){
						$("[name='fdTypeName["+i+"]']").val(paramTypeObj.text);
					}
				}else{
					var fieldValues = new Object();
					fieldValues["sysRuleSetParams["+i+"].fdName"] = param.paramName;
					fieldValues["sysRuleSetParams["+i+"].fdType"] = param.paramType;
					fieldValues["sysRuleSetParams["+i+"].fdId"] = param.paramId;
					fieldValues["sysRuleSetParams["+i+"].isEdit"] = param.isEdit || "";
					//增加一行
					DocList_AddRow("paramSetting",null,fieldValues);
					//删除原有的类型域，新增text类型的类型域
					var isMultiObj = $("div.isMulti").eq(i)[0];
					var isEditObj = $("[name='sysRuleSetParams["+i+"].isEdit']")[0];
					var tdObj = $(isMultiObj).parents("td").eq(0)[0];
					$(tdObj).empty();
					var fdTypeNameObj=document.createElement("input");
					$(fdTypeNameObj).attr("type","text");
					$(fdTypeNameObj).attr("class","inputsgl");
					$(fdTypeNameObj).attr("readonly","readonly");
					$(fdTypeNameObj).attr("name","fdTypeName["+i+"]");
					$(fdTypeNameObj).css("width","90%");
					var paramTypeObj = this.getParamTypeByValue(param.paramType, paramTypes);
					if(paramTypeObj){
						$(fdTypeNameObj).val(paramTypeObj.text);
					}
					var fdTypeObj = document.createElement("input");
					$(fdTypeObj).attr("type","hidden");
					$(fdTypeObj).attr("name","sysRuleSetParams["+i+"].fdType");
					$(fdTypeObj).val(param.paramType);
					$(tdObj).append(fdTypeNameObj);
					$(tdObj).append(fdTypeObj);
					$(tdObj).append(isMultiObj);
					$(tdObj).append(isEditObj);
					if(param.paramType && param.paramType.indexOf("ORG_TYPE_") != -1){
						$(isMultiObj).show();
						$(isMultiObj).find("[name^='_sysRuleSetParams'][name$='isMulti']").eq(0).attr("disabled","disabled");
					}
					i++;
				}
				//初始化字段
				this.initFieldsSelectByParamType(param.paramType);
			}
		}
	}
	
	function getParamIndexById(paramId){
		//获取参数表格的行数
		var paramLen = $("#paramSetting").find("tr").length - 1;

		for(var i=0; i<paramLen; i++){
			if($("[name='sysRuleSetParams["+i+"].fdId']").val() == paramId){
				return i;
			}
		}
		return -1;
	}
	
	function getParamById(paramId, params){
		if(!params || !Object.prototype.toString.call(params)=='[object Array]'){
			return null;
		}
		var paramObj;
		for(var i=0; i<params.length; i++){
			var param = params[i];
			if(paramId && paramId == param.paramId){
				return param;
			}
		}
		return null;
	}
	
	function getParamTypeByValue(value, paramTypes){
		var result;
		if(paramTypes && Object.prototype.toString.call(paramTypes)=='[object Array]'){
			paramTypes.forEach(function(paramType, index){
				if(paramType.value == value){
					result = paramType;
				}
			})
		}
		return result;
	}
	
	//========以下方法只在规则机制映射新增规则集文档时使用==========
	function initFieldsSelect(fields){
		if(!window.dialogObject || !window.dialogObject.Window){
			return;
		}
		var i = $("table#paramSetting").find("tr").length - 2;
		var fieldsSelect = $("select[name='fields["+i+"]']")[0];
		if(!fieldsSelect){
			return;
		}
		var topWin = window.dialogObject.Window.top;
		if(!fields && topWin && topWin.sysRuleTemplate){
			fields = topWin.sysRuleTemplate.getFields() || [];//如果没有，默认取第一个
		}
		this.fields = fields || [];
		var paramTypes = this.getParamTypes();
		$(fieldsSelect).html("");
		$(fieldsSelect).append('<option value="">'+Data_GetResourceString("sys-rule:rule.choose")+'</option>');
		//fieldsSelect.options.length = 0;
		//fieldsSelect.options.add(new Option("请选择",""));
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			if(this.getMatchParamType(field,paramTypes)){
				$(fieldsSelect).append('<option value="'+field.name+'">'+field.label+'</option>');
				//fieldsSelect.options.add(new Option(field.label, field.name));
			}
		}
	}

	function initFieldsSelectByParamType(type){
		if(!window.dialogObject || !window.dialogObject.Window){
			return;
		}
		var i = $("table#paramSetting").find("tr").length - 2;
		var fieldsSelect = $("select[name='fields["+i+"]']")[0];
		if(!fieldsSelect){
			return;
		}
		$(fieldsSelect).attr("onchange","");
		var topWin = window.dialogObject.Window.top;
		var fields = this.fields;
		var paramTypes = this.getParamTypes();
		$(fieldsSelect).html("");
		$(fieldsSelect).append('<option value="">请选择</option>');
		//fieldsSelect.options.length = 0;
		//fieldsSelect.options.add(new Option("请选择",""));
		for(var i=0; i<fields.length; i++){
			var field = fields[i];
			if(this.getMatchParamType(field,paramTypes)){
				if(type && field.type == getRealTypeVal(type)){
					$(fieldsSelect).append('<option value="'+field.name+'">'+field.label+'</option>');
					//fieldsSelect.options.add(new Option(field.label, field.name));
				}
			}
		}
	}

	function getMatchParamType(field, paramTypes){
		if(!field || !paramTypes || Object.prototype.toString.call(paramTypes)!='[object Array]' ){
			return null;
		}
		var result;
		paramTypes.forEach(function(paramType, index){
			var type = getRuleTypeVal(field.type);
			if(type && type.indexOf("[]") != -1){
				type = type.substring(0,type.indexOf("[]"));
			}
			if(paramType.value && paramType.value == type){
				result = paramType;
			}
		})
		return result;
	}

	function switchField(value, obj){
		var index = getIndex()-1;
		if(!window.dialogObject || !window.dialogObject.Window){
			return;
		}
		
		var topWin = window.dialogObject.Window.top;
		var fields = this.fields;
		var currentField;
		fields.forEach(function(field, index){
			if(field.name == value){
				currentField = field;
			}
		});
		//映射到参数，建立映射集
		$("[name='sysRuleSetParams["+index+"].fdName']").val("");
		$("[name='sysRuleSetParams["+index+"].fdType']").val("");
		$("[name='fdTypeName["+index+"]'").val("");
		$("[name='sysRuleSetParams["+index+"].isMulti']").val("");
		$("[name='sysRuleSetParams["+index+"].isMulti']").parents("div.isMulti").eq(0).hide();
		$("[name='_sysRuleSetParams["+index+"].isMulti']").removeAttr("disabled");
		if(currentField){
			$("[name='sysRuleSetParams["+index+"].fdName']").val(currentField.label);
			var type = getRuleTypeVal(currentField.type);
			var paramTypes = this.getParamTypes();
			var paramType = this.getMatchParamType(currentField, paramTypes);
			$("[name='sysRuleSetParams["+index+"].fdType']").val(type);
			$("[name='fdTypeName["+index+"]']").val(paramType.text);
			if(type && type.indexOf("[]") != -1){
				$("[name='sysRuleSetParams["+index+"].fdType']").val(type.substring(0,type.indexOf("[]")));
				$("[name='sysRuleSetParams["+index+"].isMulti']").val("1");
				$("[name='_sysRuleSetParams["+index+"].isMulti']").click();
				$("[name='sysRuleSetParams["+index+"].isMulti']").parents("div.isMulti").eq(0).show();
				$("[name='_sysRuleSetParams["+index+"].isMulti']").attr("disabled","disabled");
			}
		}
	}

	function getParamTypes(){
		var paramTypes;
		if(this.paramTypes.length > 0){
			paramTypes = this.paramTypes;
		}else{
			paramTypes = new KMSSData().AddBeanData("enumsDataService&enumsType=sys_rule_param_type").GetHashMapArray();
			this.paramTypes = paramTypes;
		}
		return paramTypes;
	}

})();
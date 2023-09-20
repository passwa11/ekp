/*根据类型切换控件*/
Com_IncludeFile("data.js");
function createControl(srcObj,parent){
	$(parent).html("");
	var mode = srcObj.mode;
	if(mode == "formula"){//公式定义
		createFormulaElement(srcObj,parent);
	}else{
		var rtnType = srcObj.rtnType;
		var isMulti = srcObj.isMulti;
		if(rtnType.indexOf("ORG_TYPE_") != -1){//组织架构类型
			var mulSelect = false;
			if(isMulti && isMulti == "1"){
				mulSelect = true;
			}
			if(rtnType == 'ORG_TYPE_POST|ORG_TYPE_PERSON'){
				var handlerAction = function(value,name){
					getFdHandlerRoleInfoIds(value,this);
				}
				createOrgElement(srcObj,parent,rtnType,mulSelect,handlerAction);
			}else{
				createOrgElement(srcObj,parent,rtnType,mulSelect);
			}
		}else if (rtnType == "DateTime"){//日期时间类型
			createDateTimeEle(srcObj,parent);
		}else if (rtnType == "Date"){//日期类型
			createDateEle(srcObj,parent);
		}else if (rtnType == "Time"){//时间选择
			createTimeEle(srcObj,parent);
		}else if (rtnType == 'Double' || rtnType == 'BigDecimal' || rtnType=='BigDecimal_Money'){//数字类型
			var value = srcObj.value;
			var input = document.createElement("input");
			input.type = "text";
			input.className = "inputsgl";
			input.width = "95%";
			input.name = srcObj.nameField;
			$(input).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
			/*#146936-当结果类型为数字的时候，没有进行校验-开始*/
			$(input).attr("validate","required number");
			$(input).attr("inType","isNumber");
			/*#146936-当结果类型为数字的时候，没有进行校验-结束*/
			$(input).attr("oninput","value=value.replace(/^\d+(\.\d+)?$/g,'')");
			$(input).css("width","50%");
			if(value && value[srcObj.nameField]){
				input.value = value[srcObj.nameField];
			}
			$(parent).append(input);
		}else if(rtnType == 'Boolean'){//布尔
			var value = srcObj.value;
			var select = document.createElement("select");
			select.width = "95%";
			select.name = srcObj.nameField;
			$(select).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
			$(select).attr("validate","required");
			$(select).css("width","50%");
			var option = document.createElement("option");
			option.value = "";
			option.innerText = Data_GetResourceString("sys-rule:rule.choose");
			select.appendChild(option);
			var trueOption = document.createElement("option");
			trueOption.value = true;
			trueOption.innerText = "TRUE";
			select.appendChild(trueOption);
			var falseOption = document.createElement("option");
			falseOption.value = false;
			falseOption.innerText = "FALSE";
			select.appendChild(falseOption);
			if(value && value[srcObj.nameField]){
				$(select).val(value[srcObj.nameField]);
				if(value[srcObj.nameField] && value[srcObj.nameField] == "true"){
					$(trueOption).attr("selected",true);
				}else{
					$(falseOption).attr("selected",true);
				}
			}
			parent.appendChild(select);
		}else{//其他类型变量
			var value = srcObj.value;
			var input = document.createElement("input");
			input.type = "text";
			input.className = "inputsgl";
			input.width = "95%";
			input.name = srcObj.nameField;
			$(input).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
			$(input).attr("validate","required");
			$(input).css("width","50%");
			if(value && value[srcObj.nameField]){
				input.value = value[srcObj.nameField];
			}
			$(parent).append(input);
		}
	}
	//添加必填星号
	$(parent).append('<span class="txtstrong">*</span>');
}
//创建日期元素
function createDateEle(srcObj,parent){
	var value = srcObj.value;
	
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","35%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectDate('"+srcObj.nameField+"',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	if(value && value[srcObj.nameField]){
		input.value = value[srcObj.nameField];
	}
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}
//创建时间元素
function createTimeEle(srcObj,parent){
	var value = srcObj.value;
	
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","35%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectTime('"+srcObj.nameField+"',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	if(value && value[srcObj.nameField]){
		input.value = value[srcObj.nameField];
	}
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}
//创建日期时间
function createDateTimeEle(srcObj,parent){
	var value = srcObj.value;
	
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","35%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectDateTime('"+srcObj.nameField+"',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	if(value && value[srcObj.nameField]){
		input.value = value[srcObj.nameField];
	}
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}

//创建地址本元素
function createOrgElement(srcObj,parent,orgType,mulSelect,action){
	var value = srcObj.value;
	
	var nameText = document.createElement("input");
	nameText.type = "text";
	nameText.className = "inputsgl";
	nameText.width = "95%";
	nameText.name = srcObj.nameField;
	$(nameText).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
	$(nameText).attr("validate","required");
	$(nameText).attr("readonly","readonly");
	$(nameText).css("width","35%");
	if(value && value[srcObj.nameField]){
		nameText.value = value[srcObj.nameField];
	}
	parent.appendChild(nameText);
	var idText = document.createElement("input");
	idText.type = "hidden";
	idText.name = srcObj.idField;
	if(value && value[srcObj.idField]){
		idText.value = value[srcObj.idField];
	}
	parent.appendChild(idText);
	var a = document.createElement("a");
	a.href = "#";
	$(a).attr("onclick","Dialog_Address("+mulSelect+", '"+srcObj.idField+"','"+srcObj.nameField+"'," + "null," +  orgType + "," + action+")");
	$(a).text(Data_GetResourceString("sys-rule:button.select"));
	parent.appendChild(a);
}

//解析人的身份
function getFdHandlerRoleInfoIds(dataObj,dialogObj){
	var fdId = (dataObj && dataObj.data && dataObj.data.length > 0)  ? dataObj.data[0].id : "";
	var dom = dialogObj.fieldList;
	for (var i = 0; i < dom.length; i++){
		if (dom[i].name && /_name$/.test(dom[i].name)){
			dom = dom[i];
			break;
		}
		if (dom[i].name && /_id$/.test(dom[i].name)){
			dom = dom[i];
			break;
		}
	}
	dom = (dom && dom.nodeType) ? $(dom).parent().find("select") : "";
	var options = new Array();
	var action = function(rtnVal){
		var isOk = rtnVal.data && rtnVal.data.length > 0 && rtnVal.data[0].type;
		if (isOk === "ok"){
			rtnVal = rtnVal.data[0].handlerRoleInfoIds.split(";");
			for (var i = 0;i < rtnVal.length; i++){
				var roleInfo = rtnVal[i].split("|");
				options.push("<option value='" + roleInfo[0] + "'>" + roleInfo[1] +"</option>");
			}
			if (dom && dom.length > 0){
				dom.empty();
				dom.append(options.join(""));
				dom.css("display","");
			}
		}
	};
	var data = new KMSSData();
	data.UseCache = false;
	data.AddHashMap({requestType:"getFdHandlerRoleInfoIds",fdId:fdId})
	data.SendToBean("sysFormulaSimulate", action);
}

//创建公式定义器控件
function createFormulaElement(srcObj,parent){
	var control = srcObj.control;
	if(control && control == "textarea"){
		var value = srcObj.value;
		
		var nameTextarea = document.createElement("textarea");
		nameTextarea.width = "95%";
		nameTextarea.name = srcObj.nameField;
		$(nameTextarea).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
		$(nameTextarea).css("width","95%");
		$(nameTextarea).attr("validate","required");
		$(nameTextarea).attr("readonly","readonly");
		if(value && value[srcObj.nameField]){
			nameTextarea.value = value[srcObj.nameField];
		}
		parent.appendChild(nameTextarea);
		var idText = document.createElement("input");
		idText.type = "hidden";
		idText.name = srcObj.idField;
		if(value && value[srcObj.idField]){
			idText.value = value[srcObj.idField];
		}
		parent.appendChild(idText);
		parent.appendChild(document.createElement("br"));
	}else{
		var value = srcObj.value;
		
		var nameText = document.createElement("input");
		nameText.type = "text";
		nameText.className = "inputsgl";
		nameText.width = "95%";
		nameText.name = srcObj.nameField;
		$(nameText).attr("subject",Data_GetResourceString("sys-rule:sysRuleSetDoc.defaultDisVal.validate"));
		$(nameText).attr("validate","required");
		$(nameText).attr("readonly","readonly");
		$(nameText).css("width","95%");
		if(value && value[srcObj.nameField]){
			nameText.value = value[srcObj.nameField];
		}
		parent.appendChild(nameText);
		var idText = document.createElement("input");
		idText.type = "hidden";
		idText.name = srcObj.idField;
		if(value && value[srcObj.idField]){
			idText.value = value[srcObj.idField];
		}
		parent.appendChild(idText);
	}
	
	var a = document.createElement("a");
	a.href = "#";
	$(a).attr("onclick","formulaEdit('"+srcObj.type+"')");
	$(a).text(Data_GetResourceString("sys-rule:sysRuleSetRule.mode.formula"));
	parent.appendChild(a);
}
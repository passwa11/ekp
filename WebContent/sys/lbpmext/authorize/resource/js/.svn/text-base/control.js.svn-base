/*根据类型切换控件*/
Com_IncludeFile("data.js");
var orgTypeMap = {
	"com.landray.kmss.sys.organization.model.SysOrgOrg":"ORG_TYPE_ORG",
	"com.landray.kmss.sys.organization.model.SysOrgPost":"ORG_TYPE_POST",
	"com.landray.kmss.sys.organization.model.SysOrgDept":"ORG_TYPE_DEPT",
	"com.landray.kmss.sys.organization.model.SysOrgPerson":"ORG_TYPE_PERSON",
	"com.landray.kmss.sys.organization.model.SysOrgElement":"ORG_TYPE_ALL"
}
//validate是校验方法，目前只在时间控件生效，其他控件如果后续有问题，可以在参数加上即可
function createControl(srcObj,parent,validate){
	$(parent).html("");
	var rtnType = srcObj.rtnType;
	var orgType = srcObj.orgType;
	var isMulti = srcObj.isMulti;
	if(rtnType.indexOf("com.landray.kmss.sys.organization.model") != -1){//组织架构类型
		var mulSelect = false;//这里先设置为单选
		/*if(isMulti){
			mulSelect = true;
		}*/
		if(!orgType){
			orgType = orgTypeMap[rtnType];
		}
		if(orgType == 'ORG_TYPE_POST|ORG_TYPE_PERSON'){
			var handlerAction = function(value,name){
				getFdHandlerRoleInfoIds(value,this);
			}
			createOrgElement(srcObj,parent,orgType,mulSelect,handlerAction);
		}else{
			createOrgElement(srcObj,parent,orgType,mulSelect);
		}
	}else if (rtnType == "DateTime" || rtnType == "DateTime[]"){//日期时间类型
		createDateTimeEle(srcObj,parent,validate);
	}else if (rtnType == "Date" || rtnType == "Date[]"){//日期类型
		createDateEle(srcObj,parent,validate);
	}else if (rtnType == "Time" || rtnType == "Time[]"){//时间选择
		createTimeEle(srcObj,parent,validate);
	}else if (rtnType == 'Double' || rtnType == 'Double[]' || rtnType == 'BigDecimal' || rtnType == 'BigDecimal[]' || rtnType=='BigDecimal_Money' || rtnType=='BigDecimal_Money[]'){//数字类型
		var value = srcObj.value;
		var input = document.createElement("input");
		input.type = "text";
		input.className = "inputsgl";
		input.width = "95%";
		input.name = srcObj.nameField;
		$(input).attr("subject","条件值");
		$(input).attr("validate","required");
		$(input).attr("oninput","value=value.replace(/^\d+(\.\d+)?$/g,'')");
		$(input).addClass("auth_condition_input");
		if(value && value[srcObj.nameField]){
			input.value = value[srcObj.nameField];
		}
		$(parent).append(input);
	}else if(rtnType == 'Boolean'){//布尔
		var value = srcObj.value;
		var select = document.createElement("select");
		select.width = "95%";
		select.name = srcObj.nameField;
		$(select).attr("subject","条件值");
		$(select).attr("validate","required");
		$(select).css("width","90%");
		$(select).addClass("auth_condition_select");
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
		$(input).attr("subject","条件值");
		$(input).attr("validate","required");
		$(input).addClass("auth_condition_input");
		if(value && value[srcObj.nameField]){
			input.value = value[srcObj.nameField];
		}
		$(parent).append(input);
	}
	//添加必填星号
	$(parent).append('<span class="txtstrong">*</span>');
}
//创建日期元素
function createDateEle(srcObj,parent,validate){
	var value = srcObj.value;

	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","90%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectDate('"+srcObj.nameField+"',null,"+validate+")");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject","条件值");
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	$(input).addClass("auth_condition_input");
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
function createTimeEle(srcObj,parent,validate){
	var value = srcObj.value;

	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","90%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectTime('"+srcObj.nameField+"',null,"+validate+")");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject","条件值");
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	$(input).addClass("auth_condition_input");
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
function createDateTimeEle(srcObj,parent,validate){
	var value = srcObj.value;

	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).css("width","90%");
	$(wrapperDiv).css("border","0");
	$(wrapperDiv).attr("onclick","selectDateTime('"+srcObj.nameField+"',null,"+validate+")");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	input.type = "text";
	input.className = "inputsgl";
	input.width = "95%";
	input.name = srcObj.nameField;
	$(input).attr("subject","条件值");
	$(input).attr("validate","required");
	$(input).attr("readonly","readonly");
	$(input).addClass("auth_condition_input");
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
	$(nameText).attr("subject","条件值");
	$(nameText).attr("validate","required");
	$(nameText).attr("readonly","readonly");
	$(nameText).css("width","70%");
	$(nameText).addClass("auth_condition_input");
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

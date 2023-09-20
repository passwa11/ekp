var simulatorObject = {};
var currentVarInfo = null;

//开始模拟
function startSimulateFormulaNew(src){
	var docId = document.getElementsByName("sysRuleSetDoc.fdId")[0].value;
	if(!currentVarInfo || !docId || docId == ""){
		var msg = Data_GetResourceString("sys-rule:simulator.validate.1");
		alert(msg);
		return;
	}
	$("#resultArea").val("");
	//获取变量区域的所有变量
	var varInputs = $("#variable").find("input");
	var obj = {};
	for (var i = 0; i < varInputs.length; i++){
		if (varInputs[i].name && /_name$/.test(varInputs[i].name)){
			continue;
		}
		if (varInputs[i].name){
			obj[varInputs[i].name.replace("_id","")] = varInputs[i].value;
			//如果name属性带有_id
			if (/_id$/.test(varInputs[i].name)){
				//如果有身份,使用身份
				var roleId = $("#" + varInputs[i].name.replace("_id","") + "roleInfoIds",$(varInputs[i]).parent()).val();
				if (roleId != null){
					obj[varInputs[i].name.replace("_id","")] = roleId;
				}
			}
			
		}
	}
	//提交到后台进行模拟
	var info = {};
	info["script"] = docId;
	for (var key in obj){
		info[key] = obj[key];
	}
	//特殊处理
	var extendDataXml = "<model>";
	for(var i=0; i<currentVarInfo.length; i++){
		var varInfoType = currentVarInfo[i].type;
		if(varInfoType == 'BigDecimal_Money'){
			varInfoType = 'BigDecimal';
		}
		extendDataXml += '<extendSimpleProperty name="'+currentVarInfo[i].name+'" label="'+currentVarInfo[i].label+'" type="'+varInfoType+'" />'
	}
	extendDataXml += '</model>';
	info["extendDataXml"] = extendDataXml;
	info["model"] = 'com.landray.kmss.km.review.model.KmReviewMain';
	//返回类型
	var rtnType = $("[name='sysRuleSetRule.returnType']").val();
	info["returnType"] = getRealTypeVal(getMutilTypeVal(rtnType,"1"));
	//取一个或在多个
	info["resultOption"] = $("[name='fdResultOption']:checked").val();
	
	var data = new KMSSData();
	data.UseCache = false;
	data.AddHashMap(info);
	//模拟公式后回写数据
	var action = function (rtnVal){
		var success = rtnVal.GetHashMapArray()[0].success;
		if(success=="1"){
			var result = rtnVal.GetHashMapArray();
			var arr = new Array();
			for (var i = 1; i < result.length; i++){
				arr.push(result[i].fdName);
			}
			$("#resultArea").val(arr.join(";"));
			$("#result").css("display","");
			validateResult = null;
		}else if (success=="0"){
			if(rtnVal.GetHashMapArray()[0].confirm){
				alert(rtnVal.GetHashMapArray()[0].confirm);
				validateResult = null;
			}else{
				validateResult = null;
			}
		}else{
			validateResult = null;
			alert(rtnVal.GetHashMapArray()[0].message);
		}
	}
	data.SendToBean("ruleFormulaSimulate", action);
}

//创建变量区域
function createVariableArea(varis,varTable){
	if(!varTable){
		varTable = document.getElementById("variable").getElementsByTagName("table")[0];
	}
	$(varTable).html("");
	for (var i = 0; i < varis.length; i++){
		if (i % 2 == 0){
			var tr = document.createElement("tr");
			tr.style.align = "center";	
			varTable.appendChild(tr);
		}
		var td = document.createElement("td");
		tr.appendChild(td)
		var label = document.createElement("label");
		$(label).text(varis[i].label);
		$(td).css("width","12%");
		td.appendChild(label);
		td = document.createElement("td");
		$(td).css("width","38%");
		tr.appendChild(td);
		var mulSelect = false;
		if(varis[i].type.indexOf("[]") != -1){
			mulSelect = true;
		}
		if ((varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgElement" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgElement[]" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgPerson" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgPerson[]") && !varis[i].controlType){
			createOrgElement(varis[i],td,'ORG_TYPE_ALL',mulSelect);
		}else if (varis[i].controlType && (varis[i].controlType == "address" || varis[i].controlType == "new_address")){//地址本控件
			createOrgElement(varis[i],td,varis[i].orgType,mulSelect);
		}else if (varis[i].type == "DateTime" || varis[i].type == "DateTime[]"){
			createDateTimeEle(varis[i],td);
		}
		else if (varis[i].type == "Date" || varis[i].type == "Date[]"){
			if (varis[i].orgType == "datetimeDialog"){//日期时间类型变量
				createDateTimeEle(varis[i],td);
			}else{
				createDateEle(varis[i],td);
			}
		}else if (varis[i].type == "Time" || varis[i].type == "Time[]"){//时间选择
			createTimeEle(varis[i],td);
		}else {//其他类型变量
			var input = document.createElement("input");
			$(input).addClass("inputsgl");
			$(input).attr("type","text");
			$(input).attr("name",varis[i].name);
			$(input).attr("validate","required");
			$(td).append(input);
		}
	}
	//绑定鼠标点击事件，如果所有的输入框都有值了,则将光标聚焦在输入框
	$("#startSimulaFormula").mousemove(function(){
		var isBlur = true;
		for (var i = 0; i < varis.length; i++){
			var ele = $("input[name='" + varis[i]["name"] + "']")[0] || $("input[name='" + varis[i]["name"] + "_name']")[0];
			if (!$(ele).val() || !$.trim($(ele).val())){
				isBlur = false;
			}
		}
		if (isBlur){
			$("#expression").focus();
		}
	})
}

//创建地址本元素
function createOrgElement(varInfo,parent,orgType,mulSelect){
	var nameText = document.createElement("input");
	nameText.type = "text";
	nameText.className = "inputsgl";
	nameText.width = "24%";
	nameText.name = varInfo["name"] + "_name";
	$(nameText).attr("validate","required");
	parent.appendChild(nameText);
	var idText = document.createElement("input");
	idText.type = "hidden";
	idText.name = varInfo["name"] + "_id";
	parent.appendChild(idText);
	var a = document.createElement("a");
	a.href = "#";
	$(a).attr("onclick","Dialog_Address("+mulSelect+", '" + varInfo.name + "_id','"  + varInfo.name + "_name'," + "null," +  orgType + ",function(value,name){" +
			"});");
	$(a).text(Data_GetResourceString("sys-rule:button.select"));
	parent.appendChild(a);
	//起草人身份
	$(parent).append($("<select id='" + varInfo['name'] + "roleInfoIds' style='display:none'></select>"));
}

//解析人的身份
function getFdHandlerRoleInfoIds(dataObj,simulatorObj){
	var fdId = (dataObj && dataObj.data && dataObj.data.length > 0)  ? dataObj.data[0].id : "";
	var dom = simulatorObj.fieldList;
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
	data.SendToBean("ruleFormulaSimulate", action);
}

//创建日期元素
function createDateEle(varInfo,parent){
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).attr("onclick","selectDate('" + varInfo.name + "',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	$(input).attr("name",varInfo.name);
	$(input).attr("validate","required");
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}
//创建时间元素
function createTimeEle(varInfo,parent){
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).attr("onclick","selectTime('" + varInfo.name + "',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	$(input).attr("name",varInfo.name);
	$(input).attr("validate","required");
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}
//创建日期时间
function createDateTimeEle(varInfo,parent){
	var wrapperDiv = document.createElement("div");
	$(wrapperDiv).addClass("inputselectsgl");
	$(wrapperDiv).attr("onclick","selectDateTime('" + varInfo.name + "',null,function(c){})");
	var innerDiv = document.createElement("div");
	$(innerDiv).addClass("input");
	var input = document.createElement("input");
	$(input).attr("name",varInfo.name);
	$(input).attr("validate","required");
	$(innerDiv).append(input);
	$(wrapperDiv).append(innerDiv);
	var dateIcon = document.createElement("div");
	$(dateIcon).addClass("inputdatetime");
	$(wrapperDiv).append(dateIcon);
	$(parent).append(wrapperDiv);
}

/*选择规则*/
function selectRuleSet(){
	Dialog_Tree(
			false,
			document.getElementsByName("sysRuleSetDoc.fdId")[0],
			document.getElementsByName("sysRuleSetDoc.fdName")[0], 
			null,
			"sysRuleSetDocService&parentId=!{value}&isAuth=false",
			Data_GetResourceString("sys-rule:tree.node.title.sysRuleSetDoc"),
			false,
			null);
}

function switchRtnType(value,obj){
	//设置模拟对象
	var model = "com.landray.kmss.km.review.model.KmReviewMain";
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	if(model!=null)
		funcBean += "&model=" + model;
	var funcInfo = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	var docId = $("[name='sysRuleSetDoc.fdId']").val();
	var varBean = "sysRuleSetDocService&fdId="+docId+"&type=ruleParam";
	var params = new KMSSData().AddBeanData(varBean).GetHashMapArray();
	var varInfo = [];
	//构造变量数组(参数)
	for(var i=0; i<params.length; i++){
		var param = params[i];
		var paramType = getMutilTypeVal(param.paramType,param.isMulti);
		varInfo.push({"name":param.paramId,"label":param.paramName,"type":getRealTypeVal(paramType)});
	}
	//默认内置其他变量
	var otherVarInfos = getOtherVarInfos();
	var otherVars = [];
	if(otherVarInfos && Object.prototype.toString.call(otherVarInfos) === '[object Array]'){
		//otherVars = otherVarInfos.vars;
		otherVarInfos.forEach(function(item,index){
			var vars = item.vars;
			for(var i=0; i<vars.length; i++){
				otherVars.push(vars[i]);
			}
		})
	}
	//var varInfoTemp = varInfo;
	for(var i=0; i<otherVars.length; i++){
		var key = "%";
		var key1 = "\.";
		var name = otherVars[i].value.replace(new RegExp(key,'g'),"").split(".").join("-");
		var label = otherVars[i].text;
		var type = otherVars[i].type;
		varInfo.push({"name":name,"label":label,"type":type});
	}
	//默认为多值
	var rtnType = getMutilTypeVal(value,"1");
	simulatorObject.formulaParameter = {
			varInfo: varInfo, 
			funcInfo: funcInfo, 
			returnType: getRealTypeVal(rtnType),
			funcs:"",
			model:model
	};
	
	//变量处理,创建变量区域
	createVariableArea(varInfo);
	currentVarInfo = varInfo;
}
var validateResult = null;
var dialogRtnValue = null;
if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}
//兼容Safari无法直接获取dialogArguments
if(!dialogObject && navigator.userAgent.indexOf("Safari") >= 0){
	dialogObject = opener.Com_Parameter.Dialog;
}
function validateFormulaByRule(action){
	if(validateResult!=null){
		alert(message_wait);
		return;
	}
	if (Com_Trim(document.getElementById('expression').value) == '') {
		dialogObject.rtnData = [{name:'', id:''}];
		dialogObject.AfterShow();
		close();
		return true;
	}
	//转换表达式
	var scriptIn = replaceSymbol(document.getElementById('expression').value);
	scriptIn=convertLocal2Key(scriptIn);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
  	    if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(scriptOut.indexOf("$$")>-1){
		alert(message_eval_error);
		return;
	}
	//其他变量的校验
	preInfo = {rightIndex:-1};
	var newScriptIn = scriptOut;
	var newScriptOut = '';
	var otherVars = [];
	for (var nxtInfo = getOtherNextInfo(newScriptIn, preInfo); nxtInfo!=null; nxtInfo = getOtherNextInfo(newScriptIn, nxtInfo)) {
		var varName = nxtInfo.varName;
		var varId = getOtherVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
  	    if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}
  	    newScriptOut += newScriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + varId;
		preInfo = nxtInfo;
		//添加变量到数组
		var key = "%";
		var otherVarType = getOtherVarTypeById(varId.replace(new RegExp(key,'g'),""));
		var otherVar = {
				"value":varId,
				"type":otherVarType,
				"text":varName
		}
		otherVars.push(otherVar);
	}
	newScriptOut += newScriptIn.substring(preInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(newScriptOut.indexOf("%%")>-1){
		alert(message_eval_error);
		return;
	}
	scriptOut = newScriptOut;
	//提交到后台进行校验
	var info = {};
	info["script"] = scriptOut;
	info["funcs"] = dialogObject.formulaParameter.funcs;
	info["model"] = dialogObject.formulaParameter.model;
	info["returnType"] = dialogObject.formulaParameter.returnType;
	var varInfo = dialogObject.formulaParameter.varInfo;
	var varType;
	for(var i=0; i<varInfo.length; i++){
		//兼容金额字段（BigDecimal_Money），金额字段实质上就是BigDecimal型  by 朱国荣 2016-08-18
		varType = varInfo[i].type
		if(varType && varType.indexOf('BigDecimal_') >  -1){
			varType = 'BigDecimal';
		}
		info[varInfo[i].name+".type"] = varType;
	}
	//加入其他变量
//	var otherVarInfo = getOtherVarInfos();
//	if(otherVarInfo && Object.prototype.toString.call(otherVarInfo) === '[object Array]'){
//		otherVarInfo.forEach(function(item,index){
//			var vars = item.vars;
//			for(var i=0; i<vars.length; i++){
//				otherVars.push(vars[i]);
//			}
//		})
//	}
	for(var i=0; i<otherVars.length; i++){
		var key = "%";
		var name = otherVars[i].value.replace(new RegExp(key,'g'),"");
		info[name+".type"] = otherVars[i].type;
	}
	var data = new KMSSData();
	data.AddHashMap(info);
	data.SendToBean("ruleFormulaValidate", action);
	validateResult = {name:scriptIn, id:scriptOut};
}
//获取下个变量位置的信息
function getOtherNextInfo(script, preInfo){
	var rtnVal = {};
	rtnVal.leftIndex = script.indexOf("%", preInfo==null?0:preInfo.rightIndex+1);
	if(rtnVal.leftIndex==-1)
		return null;
	rtnVal.rightIndex = script.indexOf("%", rtnVal.leftIndex+1);
	if(rtnVal.rightIndex==-1)
		return null;
	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
	rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
	return rtnVal;
}

//根据变量名取ID
function getOtherVarIdByName(varName){
	var varInfo = getOtherVarInfos();
	var otherVars = [];
	if(varInfo && Object.prototype.toString.call(varInfo) === '[object Array]'){
		varInfo.forEach(function(item,index){
			var vars = item.vars;
			for(var i=0; i<vars.length; i++){
				otherVars.push(vars[i]);
			}
		})
	}
	for(var i=0; i<otherVars.length; i++){
		if(otherVars[i].text==varName)
			return otherVars[i].value;
	}
}

//根据ID取变量名
function getOtherVarNameById(varId){
	var varInfo = getOtherVarInfos();
	var otherVars = [];
	if(varInfo && Object.prototype.toString.call(varInfo) === '[object Array]'){
		varInfo.forEach(function(item,index){
			var vars = item.vars;
			for(var i=0; i<vars.length; i++){
				otherVars.push(vars[i]);
			}
		})
	}
	for(var i=0; i<otherVars.length; i++){
		if(otherVars[i].value=='%'+varId+'%')
			return otherVars[i].text;
	}
}

//根据ID取变量名
function getOtherVarTypeById(varId){
	var varInfo = getOtherVarInfos();
	var otherVars = [];
	if(varInfo && Object.prototype.toString.call(varInfo) === '[object Array]'){
		varInfo.forEach(function(item,index){
			var vars = item.vars;
			for(var i=0; i<vars.length; i++){
				otherVars.push(vars[i]);
			}
		})
	}
	for(var i=0; i<otherVars.length; i++){
		if(otherVars[i].value=='%'+varId+'%')
			return otherVars[i].type;
	}
}

//初始化代码
window.onload = function(){
	var field = document.getElementById("expression");
	if(typeof window.ActiveXObject!="undefined") {
		field.onbeforedeactivate = getCaret;
	} else {
		field.onblur = getCaret;
	}
	
	var scriptInfo = dialogObject.valueData.GetHashMapArray()[0];
	var scriptIn = scriptInfo ? scriptInfo.id : "";
	var scriptDis = scriptInfo ? scriptInfo.name : "";
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	var errorFunc = "";
	var errorVar = "";
	var nxtInfoDis = getNextInfo(scriptDis);
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varName = getVarNameById(nxtInfo.varName, nxtInfo.isFunc);
		if(varName==null){
			varName = nxtInfoDis.varName;
			if(nxtInfo.isFunc){
				errorFunc += "; " + varName;
			}else{
				errorVar += "; " + varName;
			}
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varName + "$";
		preInfo = nxtInfo;
		nxtInfoDis = getNextInfo(scriptDis, nxtInfoDis);
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	var newScriptIn = scriptOut;
	var newScriptOut = '';
	preInfo = {rightIndex:-1};
	nxtInfoDis = getOtherNextInfo(scriptDis);
	for (var nxtInfo = getOtherNextInfo(newScriptIn, preInfo); nxtInfo!=null; nxtInfo = getOtherNextInfo(newScriptIn, nxtInfo)) {
		var varName = getOtherVarNameById(nxtInfo.varName, nxtInfo.isFunc);
		if(varName==null){
			varName = nxtInfoDis.varName;
			if(nxtInfo.isFunc){
				errorFunc += "; " + varName;
			}else{
				errorVar += "; " + varName;
			}
		}
		newScriptOut += newScriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "%" + varName + "%";
		preInfo = nxtInfo;
		nxtInfoDis = getOtherNextInfo(scriptDis, nxtInfoDis);
	}
	newScriptOut += newScriptIn.substring(preInfo.rightIndex+1);
	scriptOut = newScriptOut;
	field.value = scriptOut;
	var message = "";
	if(errorVar!=""){
		message = message_unknowvar + errorVar.substring(2);
	}
	if(errorFunc!=""){
		if(message!="")
			message += "\r\n";
		message += 	message_unknowfunc + errorFunc.substring(2);
	}
	if(message!="")
		alert(message);
};

//添加关闭事件
//Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});

//根据变量名取ID
function getVarIdByName(varName, isFunc){
	if(isFunc){
		var funcInfo = dialogObject.formulaParameter.funcInfo;
		for(var i=0; i<funcInfo.length; i++){
			
			if(funcInfo[i].text==varName||funcInfo[i].key==varName)
				return varName;
		}
	}else{
		var varInfo = dialogObject.formulaParameter.varInfo;
		for(var i=0; i<varInfo.length; i++){
			if(varInfo[i].label==varName)
				return varInfo[i].name;
		}
	}
}

//根据ID取变量名
function getVarNameById(varName, isFunc){
	if(isFunc){
		var funcInfo = dialogObject.formulaParameter.funcInfo;
		for(var i=0; i<funcInfo.length; i++){
			if(funcInfo[i].key==varName){
				return funcInfo[i].text;
			}
			if(funcInfo[i].text==varName)
				return varName;
		}
	}else{
		var varInfo = dialogObject.formulaParameter.varInfo;
		for(var i=0; i<varInfo.length; i++){
			if(varInfo[i].name==varName)
				return varInfo[i].label;
		}
	}
}

//根据变量名取变量类型
function getVarTypeByName(varName){
	var varInfo = dialogObject.formulaParameter.varInfo;
	for (var i = 0; i < varInfo.length; i++){
		if (varInfo[i].label == varName){
			return varInfo[i].type;
		}
	}
}

//根据变量名取控件类型
function getVarControlTypeByName(varName){
	var varInfo = dialogObject.formulaParameter.varInfo;
	for (var i = 0; i < varInfo.length; i++){
		if (varInfo[i].label == varName){
			return varInfo[i].controlType;
		}
	}
}

//根据变量名获取地址本类型
function getVarOrgTypeByName(varName){
	var varInfo = dialogObject.formulaParameter.varInfo;
	for (var i = 0; i < varInfo.length; i++){
		if (varInfo[i].label == varName){
			return varInfo[i].orgType;
		}
	}
}

//替换中文字符
function replaceSymbol(str){
	/*str = str.replace(/，/g, ",");
	str = str.replace(/。/g, ".");
	str = str.replace(/：/g, ":");
	str = str.replace(/；/g, ";");
	str = str.replace(/＋/g, "+");
	str = str.replace(/－/g, "-");
	str = str.replace(/×/g, "*");
	str = str.replace(/÷/g, "/");
	str = str.replace(/（/g, "(");
	str = str.replace(/）/g, ")");
	str = str.replace(/《/g, "<");
	str = str.replace(/》/g, ">");*/
	return str;
}

//获取下个变量位置的信息
function getNextInfo(script, preInfo){
	var rtnVal = {};
	rtnVal.leftIndex = script.indexOf("$", preInfo==null?0:preInfo.rightIndex+1);
	if(rtnVal.leftIndex==-1)
		return null;
	rtnVal.rightIndex = script.indexOf("$", rtnVal.leftIndex+1);
	if(rtnVal.rightIndex==-1)
		return null;
	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
	rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
	return rtnVal;
}
//var _formulaSimulaData = new KMSSData();
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

//创建地址本元素
function createOrgElement(varInfo,parent,orgType){
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
//	$(a).attr("onclick","Dialog_Address(false, '" + varInfo.name + "_id','"  + varInfo.name + "_name'," + "null, " + orgType + ");");
	$(a).attr("onclick","Dialog_Address(false, '" + varInfo.name + "_id','"  + varInfo.name + "_name'," + "null," +  orgType + ",function(value,name){" +
			"getFdHandlerRoleInfoIds(value,this)});");
	$(a).text("选择");
	parent.appendChild(a);
	//起草人身份
	$(parent).append($("<select id='" + varInfo['name'] + "roleInfoIds' style='display:none'></select>"));
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
//创建变量区域
function createVariableArea(varis,varTable){
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
		if ((varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgElement" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgElement[]" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgPerson" ||
				varis[i].type == "com.landray.kmss.sys.organization.model.SysOrgPerson[]") && !varis[i].controlType){
			createOrgElement(varis[i],td,'ORG_TYPE_ALL');
		}else if (varis[i].controlType && (varis[i].controlType == "address" || varis[i].controlType == "new_address")){//地址本控件
			createOrgElement(varis[i],td,varis[i].orgType);
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
	//必填校验
	for (var i = 0; i < varis.length; i++){
		var ele = $("input[name='" + varis[i]["name"] + "']")[0] || $("input[name='" + varis[i]["name"] + "_name']")[0];
		validateRequired(ele);
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

//模拟公式
function simulateFormula(self){
	//没有填写公式就点击模拟,先给出提示!
	if (Com_Trim(document.getElementById('expression').value) == ''){
		alert("请先填写公式!");
		return ;
	}
	//如果在模拟界面点击模拟按钮,则返回到运算符界面
	if ($("#operator").css("display") == "none"){
		$(self).attr("value","模拟");
		//变量域
		$("#variable").css("display","none");
		$("#variable").find("table").html("");
		//结果域
		$("#result").css("display","none");
		//运算符域
		$("#operator").css("display","");
		//开始模拟按钮
		$("#startSimulaFormula").css("display","none");
		//移除鼠标移动事件
		$("#startSimulaFormula").off( "mousemove", "**" )
		return;
	}
	//转换表达式
	var scriptIn = replaceSymbol(document.getElementById('expression').value);
	scriptIn=convertLocal2Key(scriptIn);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	//存放变量的数组
	var varis = new Array;
	//变量id数组,防止同个变量多次保存到数组
	var varIdsArr = new Array;
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
		if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}
		//如果是变量,将变量名和变量类型存放到数组
		if (!nxtInfo.isFunc){
			if ($.inArray(varId,varIdsArr) < 0){//避免一条公式，多个相同变量多次保存到变量数组中
				varIdsArr.push(varId);
				var type = getVarTypeByName(nxtInfo.varName);
				var controlType = getVarControlTypeByName(nxtInfo.varName);
				var orgType = getVarOrgTypeByName(nxtInfo.varName);
				//变量id,变量名,变量类型,控件类型,地址本类型
				var varInfo = {name:varId,label:nxtInfo.varName,type:type,controlType:controlType,orgType:orgType};
				varis.push(varInfo);
			}
		}
	}
	//每次开始模拟时，先清空上一次的模拟结果
	$("#resultArea").val("");
	$("#result").css("display","");
	//隐藏运算符区域
	$("#operator").css("display","none");
	//隐藏函数详情区域
	$("#funcDetail").css("display","none");
	//隐藏函数样例区域
	$("#expSummary").css("display","none");
	//显示变量区域
	$("#variable").css("display","");
	//将模拟按钮的显示文字改为返回,以便回到运算符界面
	$(self).attr("value","返回");
	//显示开始模拟按钮
	$("#startSimulaFormula").css("display","").css("margin-right","12px");
	//如果有变量,则将运算符区域隐藏掉,显示为变量输入框
	if (varis.length > 0){
		//生成变量输入框
		var varTable = document.getElementById("variable").getElementsByTagName("table")[0];
		createVariableArea(varis,varTable);
	}
}

/**
 * 校验公式中的变量是否和变量区域中的变量一致
 * @param obj 变量区域的变量
 * @param varis 表达式中的变量
 * @returns
 */
function validationExpAndVars(obj,varis){
	//判断表达式中是否存在变量,存在则判断与变量区域的变量是否一致
	var arr = new Array();
	if (varis && varis.length > 0){
		if (isEmpty(obj)) return false;
		for (var key in obj){
			arr.push(key);
		}
		if (arr.length == varis.length){
			for(var i = 0; i < varis.length; i++){
				if (varis[i].name != arr[i]){
					return false;
				}
			}
			return true;
		}
		return false;
	}else{
		return isEmpty(obj);
	}
}

/**
 * 判断是否变量是否为null或者{}
 * @param obj
 * @returns
 */
function isEmpty(obj){
	var isEmpty = true;
	if(obj == null){
		return isEmpty;
	}else{
		for (var key in obj){
			isEmpty = false;
			break;
		}
	}
	return isEmpty;
}

//开始模拟
function startSimulateFormula(src){
	$("#resultArea").val("");
	if (Com_Trim(document.getElementById('expression').value) == '') {
		alert("请先填写公式!")
		return true;
	}
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
	var varis = new Array();
	//变量id数组,防止同个变量多次保存到数组
	var varIdsArr = new Array;
	//转换表达式
	var scriptIn = replaceSymbol(document.getElementById('expression').value);
	scriptIn=convertLocal2Key(scriptIn);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
		if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}
		//如果是变量,将变量名和变量类型存放到数组
		if (!nxtInfo.isFunc){
			if ($.inArray(varId,varIdsArr) < 0){//避免一条公式，多个相同变量多次保存到变量数组中
				varIdsArr.push(varId);
				var type = getVarTypeByName(nxtInfo.varName);
				var controlType = getVarControlTypeByName(nxtInfo.varName);
				var orgType = getVarOrgTypeByName(nxtInfo.varName);
				//变量id,变量名,变量类型,控件类型,地址本类型
				var varInfo = {name:varId,label:nxtInfo.varName,type:type,controlType:controlType,orgType:orgType};
				varis.push(varInfo);
			}
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(scriptOut.indexOf("$$")>-1){
		alert("公式有误,请重新填写!");
		return;
	}
	var isSame = validationExpAndVars(obj,varis);
	//如果变量区域和表达式一致,则判断变量区域有没有值,没有则不发请求
	if (isSame){
		var hasValues = false;
		for (var key in obj){
			if (obj[key] === ""){
				hasValues = true;
				return;
			}
		}
	}else{
		//不一致,重新生成变量域,提示变量不能为空
		var varTable = document.getElementById("variable").getElementsByTagName("table")[0];
		varTable.innerHtml = '';
		createVariableArea(varis,varTable);
		return;
	}
	//提交到后台进行模拟
	var info = {};
	info["script"] = scriptOut;
	for (var key in obj){
		info[key] = obj[key];
	}
	//获取表单数据字典和主文档全类名
	if(window.showModalDialog){//ie模态窗口
		var win = dialogObject.window;
		var designer;
		var fdMetadataXml = "";
		if (win.Designer){
			designer = win.Designer;
		}else{
			var iframe = $("[id*='IFrame_FormTemplate_']",win.document);
			if (iframe[0]){
				var formtemplateObj = $("[id*='TB_FormTemplate_']",win.document);
				fdMetadataXml = $("[name*='fdMetadataXml']",formtemplateObj).val();
				designer = iframe[0].contentWindow.Designer;
			}
		}
		var flowChartObject = win.FlowChartObject || win._FlowChartObject;
		if (designer){
			info["extendDataXml"] = designer.instance.getXML();
			info["model"] = win.parent._xform_MainModelName;
		}else if (win.parent._xform_MainModelName){
			info["model"] = win.parent._xform_MainModelName;
			info["extendDataXml"] = designerInstance ? designerInstance.getXML() : (fdMetadataXml || '<model></model>');
		}else if(flowChartObject){
			info["extendDataXml"] = flowChartObject.Designer ? flowChartObject.Designer.getXML() 
					: (fdMetadataXml || '<model></model>');
			info["model"] = flowChartObject["MODEL_NAME"]  ||  'com.landray.kmss.km.review.model.KmReviewMain';
		}else{
			info["extendDataXml"] = fdMetadataXml || '<model></model>';
			info["model"] = 'com.landray.kmss.km.review.model.KmReviewMain';
		}
	}else{
		//表单获取Designer
		var designer = window.opener.Designer;
		//模板获取Designer
		var fdMetadataXml = "";
		if (!designer){
			var iframe = $("[id*='IFrame_FormTemplate_']",window.opener.window.document);
			if (iframe[0]){
				var formtemplateObj = $("[id*='TB_FormTemplate_']",window.opener.window.document);
				fdMetadataXml = $("[name*='fdMetadataXml']",formtemplateObj).val();
				designer = iframe[0].contentWindow.Designer;
			}
		}
		//流程中获取Designer ,_FlowChartObject为兼容机器人节点
		var flowChartObject = window.opener.FlowChartObject || window.opener._FlowChartObject; 
		var designerInstance = (designer ? designer.instance : null) || (flowChartObject ? flowChartObject.Designer : null);
		if (window.opener.parent._xform_MainModelName){
			info["model"] = window.opener.parent._xform_MainModelName;
		}else{
			info["model"] = (flowChartObject ? flowChartObject["MODEL_NAME"] : 'com.landray.kmss.km.review.model.KmReviewMain'); 
		}
		info["extendDataXml"] = designerInstance ? designerInstance.getXML() : (fdMetadataXml || '<model></model>');
	}
	
	var data = new KMSSData();
	data.UseCache = false;
	data.AddHashMap(info);
	//模拟公式后回写数据
	var action = function (rtnVal){
		var success = rtnVal.GetHashMapArray()[0].success;
		if(success=="1"){
//			dialogObject.rtnData = [validateResult];
			var result = rtnVal.GetHashMapArray();
			var arr = new Array();
			for (var i = 1; i < result.length; i++){
				arr.push(result[i].fdName);
			}
			$("#resultArea").val(arr.join(";"));
			$("#result").css("display","");
			validateResult = null;
//			alert(rtnVal.GetHashMapArray()[0].message);
		}else if (success=="0"){
//			if(confirm(rtnVal.GetHashMapArray()[0].confirm)){
//				dialogObject.rtnData = [validateResult];
//				close();
//			}else{
//				validateResult = null;
//			}
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
	data.SendToBean("sysFormulaSimulate", action);
	validateResult = {name:scriptIn, id:scriptOut};
}

/**
 * 必填校验
 * @param element
 * @returns
 */
function validateRequired(element){
	$KMSSValidation.validateElement(element);
}



//校验公式
function validateFormula(action){
	if(validateResult!=null){
		alert(message_wait);
		return;
	}
	if (Com_Trim(document.getElementById('expression').value) == '') {
		dialogObject.rtnData = [{name:'', id:''}];
		close();
		return true;
	}
	//转换表达式
	var scriptIn = replaceSymbol(document.getElementById('expression').value);
	scriptIn=convertLocal2Key(scriptIn);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
  	    if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(scriptOut.indexOf("$$")>-1){
		alert(message_eval_error);
		return;
	}
	//提交到后台进行校验
	var info = {};
	info["script"] = scriptOut;
	info["funcs"] = dialogObject.formulaParameter.funcs;
	info["model"] = dialogObject.formulaParameter.model;
	info["returnType"] = dialogObject.formulaParameter.returnType;
	var varInfo = dialogObject.formulaParameter.varInfo;
	var varType;
	for(var i=0; i<varInfo.length; i++){
		//兼容金额字段（BigDecimal_Money），金额字段实质上就是BigDecimal型  by 朱国荣 2016-08-18
		varType = varInfo[i].type
		if(varType && varType.indexOf('BigDecimal_') > -1){
			varType = 'BigDecimal';
		}
		info[varInfo[i].name+".type"] = varType;
	}
	var data = new KMSSData();
	data.AddHashMap(info);
	data.SendToBean("sysFormulaValidate", action);
	validateResult = {name:scriptIn, id:scriptOut};
}

//校验公式
function validateFormulaByJS(action){
	if(validateResult!=null){
		alert(message_wait);
		return;
	}
	if (Com_Trim(document.getElementById('expression').value) == '') {
		dialogObject.rtnData = [{name:'', id:''}];
		close();
		return true;
	}
	//转换表达式
	var scriptIn = replaceSymbol(document.getElementById('expression').value);
	scriptIn=convertLocal2Key(scriptIn);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	for (var nxtInfo = getNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptIn, nxtInfo)) {
		var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
		/*if(varId==null){
			alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
			return;
		}*/
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + (varId==null?nxtInfo.varName:varId) + "$";
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(scriptOut.indexOf("$$")>-1){
		alert(message_eval_error);
		return;
	}
	//提交到后台进行校验
	var info = {};
	info["script"] = scriptOut;
	info["funcs"] = dialogObject.formulaParameter.funcs;
	info["model"] = dialogObject.formulaParameter.model;
	info["returnType"] = dialogObject.formulaParameter.returnType;
	var varInfo = dialogObject.formulaParameter.varInfo;
	var varType;
	for(var i=0; i<varInfo.length; i++){
		//兼容金额字段（BigDecimal_Money），金额字段实质上就是BigDecimal型  by 朱国荣 2016-08-18
		varType = varInfo[i].type
		if(varType && varType.indexOf('BigDecimal_') > -1){
			varType = 'BigDecimal';
		}
		info[varInfo[i].name+".type"] = varType;
	}
	var data = new KMSSData();
	data.AddHashMap(info);
	data.SendToBean("sysFormulaValidateByJS", action);
	validateResult = {name:scriptIn, id:scriptOut};
}

//根据文本获取Key值
function GetFunKeyByText(text){
	var funcInfo = dialogObject.formulaParameter.funcInfo;
	for(var i=0; i<funcInfo.length; i++){
		if(funcInfo[i].text==text && funcInfo[i].key){
			return funcInfo[i].key;
		}
	}
	return text;
}
function convertLocal2Key(expression){
	expression=expression.replace(/\$([^\$]+)\$\(/g,function($1,$2){
		return $1.replace($2,GetFunKeyByText($2));
	});
	return expression;
}
function convertKey2Local(expression){
	return "";
}
//校验后提示信息
function validateMessage(rtnVal){
	validateResult = null;
	alert(rtnVal.GetHashMapArray()[0].message);
}

//校验后回写公式
function writeBack(rtnVal) {
	var success = rtnVal.GetHashMapArray()[0].success;
	if(success=="1"){
		dialogObject.rtnData = [validateResult];
		dialogObject.AfterShow();
		close();
	}else if (success=="0"){
		if(confirm(rtnVal.GetHashMapArray()[0].confirm)){
			dialogObject.rtnData = [validateResult];
			dialogObject.AfterShow();
			close();
		}else{
			validateResult = null;
		}
	}else{
		validateResult = null;
		alert(rtnVal.GetHashMapArray()[0].message);
	}
}

//往公式中添加字符
function opFormula(param, space){
	var area = document.getElementById("expression");
	area.focus();
	if (space == null)
		space = '';
	insertText(area, {value:space + param + space});
}

function insertText(obj, node) {
	obj.focus();
	if (document.selection) {
		var sel = document.selection.createRange();
		sel.text = node.value;
	} else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
		var startPos = obj.selectionStart, endPos = obj.selectionEnd, cursorPos = startPos, tmpStr = obj.value;   
		obj.value = tmpStr.substring(0, startPos) + node.value + tmpStr.substring(endPos, tmpStr.length);
		cursorPos += node.value.length;
		obj.selectionStart = obj.selectionEnd = cursorPos;
	} else {
		obj.value += node.value;
	}
	if(node.summary){
		document.getElementById("expSummary").innerHTML = node.summary;
		document.getElementById("expSummary").style.display = "";
		document.getElementById("funcDetail").style.display = "none";
	}
}

//公式输入框控制代码
var focusIndex = 0;
function getCaret() {
	var txb = document.getElementById("expression");
	if (document.selection) {
		var pos = 0;
		var s = txb.scrollTop;
		var r = document.selection.createRange();
		var t = txb.createTextRange();
		t.collapse(true);
		t.select();
		var j = document.selection.createRange();
		r.setEndPoint("StartToStart",j);
		var str = r.text;
		var re = new RegExp("[\\r\\n]","g");
		str = str.replace(re,"");
		pos = str.length;
		r.collapse(false);
		r.select();
		txb.scrollTop = s;
		focusIndex = pos;
	} else {
		focusIndex = txb.value.length;
	}
}

function setCaret() {
	var txb = document.getElementById("expression");
	if (document.selection) {
		var r = txb.createTextRange();
		r.collapse(true);
		r.moveStart('character', focusIndex);
		r.select();
	} else {
		focusIndex = txb.value.length;
	}
}

function clearExp() {
	document.getElementById('expression').value = '';
}

function loadFuncFormulaDetail(node) {
	document.getElementById("funcDetail").style.display = "";
	if(node.summary){
		document.getElementById("desc").innerHTML = node.summary;
		document.getElementById("expSummary").innerHTML = "";
	}
	var html = "";
	for (var n=1; n<10; n++) {
		if(node["example"+n]){
			if(html!=""){
				html += "<br/>"
			}
			html +=  node["example" + n] + ' <a href="javascript:void(0)" funcKey="'+ node.title+'" class="com_btn_link" onclick="insertExample(this,'+n+')">' + message_insert_formula + "</a>";
		} else {
			break;
		}
	}
	document.getElementById("example").innerHTML = html;
}

function insertExample(node, index){
	var funcInfo = dialogObject.formulaParameter.funcInfo;
	for(var i=0; i<funcInfo.length; i++){
		if(funcInfo[i].title==node.getAttribute("funcKey")){
			opFormula(funcInfo[i]["exampleFormula"+index]);
		}
	}
}
var validateResult = null;
var dialogRtnValue = null;
var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1
if(window.showModalDialog && flag){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}

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

function getLbpmNextInfo(script, preInfo){
	var rtnVal = {};
	rtnVal.leftIndex = script.indexOf("%", preInfo==null?0:preInfo.rightIndex+1);
	if(rtnVal.leftIndex==-1)
		return null;
	rtnVal.rightIndex = script.indexOf("%", rtnVal.leftIndex+1);
	if(rtnVal.rightIndex==-1)
		return null;
	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
	return rtnVal;
}

//根据变量名取ID
function getLbpmVarIdByName(varName){
	var varInfo = getLbpmCustomNotifyVars();
	for(var i=0; i<varInfo.length; i++){
		if(varInfo[i].text==varName)
			return varInfo[i].value;
	}
}

//根据ID取变量名
function getLbpmVarNameById(varName){
	var varInfo = getLbpmCustomNotifyVars();
	for(var i=0; i<varInfo.length; i++){
		if(varInfo[i].value=='%'+varName+'%')
			return varInfo[i].text;
	}
}

var lbpmVars = '';

function getLbpmCustomNotifyVars(){
	if(!lbpmVars){
		var handler = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.handler");
		var creator = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.creator");
		var creatorDept = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.creatorDept");
		var creatorPost = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.creatorPost");
		var identity = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.identity");
		var identityDept = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.identityDept");
		var docCreator = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.docCreator");
		var nodeName = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.nodeName");
		var oprName = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.oprName");
		var docSubject = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.docSubject");
		var auditNote = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.auditNote");
		var oprHandler = Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.oprHandler");
		lbpmVars = [{'text':handler,'value':'%handler%','title':handler},
						{'text':creator,'value':'%creator%','title':creator},
						{'text':creatorDept,'value':'%creatorDept%','title':creatorDept},
						{'text':creatorPost,'value':'%creatorPost%','title':creatorPost},
						{'text':identity,'value':'%identity%','title':identity},
						{'text':identityDept,'value':'%identityDept%','title':identityDept},
						{'text':docCreator,'value':'%docCreator%','title':docCreator},
						{'text':nodeName,'value':'%nodeName%','title':nodeName},
						{'text':oprName,'value':'%oprName%','title':oprName},
						{'text':docSubject,'value':'%docSubject%','title':docSubject},
						{'text':auditNote,'value':'%auditNote%','title':auditNote},
						{'text':oprHandler,'value':'%oprHandler%','title':oprHandler}
			        ];
	}
	return lbpmVars;
}

//校验公式
function validateFormula(action){
	if(validateResult!=null){
		alert(message_wait);
		return;
	}
//	if (Com_Trim(document.getElementById('expression').value) == '') {
//		dialogObject.rtnData = [{name:'', id:''}];
//		close();
//		return true;
//	}
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
	var myScriptIn = scriptOut;
	scriptOut = "";
	var preLbpmInfo = {rightIndex:-1};
	for (var nxtLbpmInfo = getLbpmNextInfo(myScriptIn, preLbpmInfo); nxtLbpmInfo!=null; nxtLbpmInfo = getLbpmNextInfo(myScriptIn, nxtLbpmInfo)) {
		var varId = getLbpmVarIdByName(nxtLbpmInfo.varName);
  	    if(varId==null){
			alert(message_unknowvar + nxtLbpmInfo.varName);
			return;
		}
		scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1, nxtLbpmInfo.leftIndex) + varId;
		preLbpmInfo = nxtLbpmInfo;
	}
	scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1);
	//校验两个变量并列的错误
	if(scriptOut.indexOf("$$")>-1){
		alert(message_eval_error);
		return;
	}
	if(dialogObject.formulaParameter.isLangSuportEnabled){
		var langJson = dialogObject.formulaParameter.langJson;
		for(var h=0;h<langJson["support"].length;h++){
			var lang = langJson["support"][h]["value"];
			//转换表达式
			if(Com_Trim($("[name='expression_"+lang+"']").val()) == ''){
				if(!validateResult){
					validateResult = {};
				}
				validateResult["id_"+lang] = "";
				validateResult["name_"+lang] = "";
				continue;
			}
			var scriptInLang = replaceSymbol($("[name='expression_"+lang+"']").val());
			scriptInLang=convertLocal2Key(scriptInLang);
			var preInfo = {rightIndex:-1};
			var scriptOutLang = "";
			for (var nxtInfo = getNextInfo(scriptInLang, preInfo); nxtInfo!=null; nxtInfo = getNextInfo(scriptInLang, nxtInfo)) {
				var varId = getVarIdByName(nxtInfo.varName, nxtInfo.isFunc);
		  	    if(varId==null){
					alert((nxtInfo.isFunc ? message_unknowfunc : message_unknowvar) + nxtInfo.varName);
					return;
				}
				scriptOutLang += scriptInLang.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + "$" + varId + "$";
				preInfo = nxtInfo;
			}
			scriptOutLang += scriptInLang.substring(preInfo.rightIndex+1);
			var myScriptIn = scriptOutLang;
			scriptOutLang = "";
			var preLbpmInfo = {rightIndex:-1};
			for (var nxtLbpmInfo = getLbpmNextInfo(myScriptIn, preLbpmInfo); nxtLbpmInfo!=null; nxtLbpmInfo = getLbpmNextInfo(myScriptIn, nxtLbpmInfo)) {
				var varId = getLbpmVarIdByName(nxtLbpmInfo.varName);
		  	    if(varId==null){
					alert(message_unknowvar + nxtLbpmInfo.varName);
					return;
				}
		  	    scriptOutLang += myScriptIn.substring(preLbpmInfo.rightIndex+1, nxtLbpmInfo.leftIndex) + varId;
				preLbpmInfo = nxtLbpmInfo;
			}
			scriptOutLang += myScriptIn.substring(preLbpmInfo.rightIndex+1);
			if(scriptOutLang.indexOf("$$")>-1){
				alert(message_eval_error);
				return;
			}
			if(!validateResult){
				validateResult = {};
			}
//			var info = {};
//			info["script"] = scriptOutLang;
//			info["model"] = dialogObject.formulaParameter.model;
//			info["returnType"] = dialogObject.formulaParameter.returnType;
//			var varInfo = dialogObject.formulaParameter.varInfo;
//			var varType;
//			for(var i=0; i<varInfo.length; i++){
//				//兼容金额字段（BigDecimal_Money），金额字段实质上就是BigDecimal型  by 朱国荣 2016-08-18
//				varType = varInfo[i].type
//				if(varType && varType.indexOf('BigDecimal_') > -1){
//					varType = 'BigDecimal';
//				}
//				info[varInfo[i].name+".type"] = varType;
//			}
//			var langData = new KMSSData();
//			langData.AddHashMap(info);
//			langData.SendToBean("sysFormulaValidate", function(rtnVal){
//				var success = rtnVal.GetHashMapArray()[0].success;
//				if(success=="1"){
//					
//				}else if (success=="0"){
//					if(!confirm(rtnVal.GetHashMapArray()[0].confirm)){
//						validateResult = null;
//					}
//				}else{
//					validateResult = null;
//					alert(rtnVal.GetHashMapArray()[0].message);
//				}
//			});
			validateResult["id_"+lang] = scriptOutLang;
			validateResult["name_"+lang] = scriptInLang;
		}
	}
	//提交到后台进行校验
//	var info = {};
//	info["script"] = scriptOut;
//	info["model"] = dialogObject.formulaParameter.model;
//	info["returnType"] = dialogObject.formulaParameter.returnType;
//	var varInfo = dialogObject.formulaParameter.varInfo;
//	var varType;
//	for(var i=0; i<varInfo.length; i++){
//		//兼容金额字段（BigDecimal_Money），金额字段实质上就是BigDecimal型  by 朱国荣 2016-08-18
//		varType = varInfo[i].type
//		if(varType && varType.indexOf('BigDecimal_') > -1){
//			varType = 'BigDecimal';
//		}
//		info[varInfo[i].name+".type"] = varType;
//	}
//	setTimeout(function(){
//		var data = new KMSSData();
//		data.AddHashMap(info);
//		data.SendToBean("sysFormulaValidate", action);
//	},500);
	if(!validateResult){
		validateResult = {};
	}
	validateResult["name"] = scriptIn;
	validateResult["id"] = scriptOut;
	
	dialogObject.rtnData = [validateResult];
	close();
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
		close();
	}else if (success=="0"){
		if(confirm(rtnVal.GetHashMapArray()[0].confirm)){
			dialogObject.rtnData = [validateResult];
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
	var area = focusTextArea||document.getElementById("expression");
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
		$("#expSummary").html(node.summary);
		$("#expSummary").show();
	}
}

//公式输入框控制代码
var focusIndex = 0;
function getCaret() {
	var txb = focusTextArea||document.getElementById("expression");
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
	var txb = focusTextArea||document.getElementById("expression");
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
	//document.getElementById('expression').value = '';
	$("[name^='expression']").val("");
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

//初始化代码
window.onload = function(){
	var field = document.getElementById("expression");
	if(typeof window.ActiveXObject!="undefined") {
		field.onbeforedeactivate = getCaret;
	} else {
		field.onblur = getCaret;
	}
	
	var scriptInfo = dialogObject.formulaParameter.valueInfo;
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
	
	var myScriptIn = scriptOut;
	scriptOut = "";
	var preLbpmInfo = {rightIndex:-1};
	var nxtLbpmInfoDis = getLbpmNextInfo(scriptDis);
	for (var nxtLbpmInfo = getLbpmNextInfo(myScriptIn, preLbpmInfo); nxtLbpmInfo!=null; nxtLbpmInfo = getLbpmNextInfo(myScriptIn, nxtLbpmInfo)) {
		var varName = getLbpmVarNameById(nxtLbpmInfo.varName);
		if(varName==null){
			varName = nxtLbpmInfoDis.varName;
			errorVar += "; " + varName;
		}
		scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1, nxtLbpmInfo.leftIndex) + "%" + varName + "%";
		preLbpmInfo = nxtLbpmInfo;
		nxtLbpmInfoDis = getLbpmNextInfo(scriptDis, nxtLbpmInfoDis);
	}
	scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1);
	field.value = scriptOut;
	if(dialogObject.formulaParameter.isLangSuportEnabled){
		if(dialogObject.formulaParameter.messKey){
			var langJson = dialogObject.formulaParameter.langJson;
			for(var h=0;h<langJson["support"].length;h++){
				var lang = langJson["support"][h]["value"];
				$(".lang_desc_"+lang).append("&nbsp;&nbsp;&nbsp;&nbsp;"+Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.default")+"\""+replaceValueToText(Data_GetResourceString(dialogObject.formulaParameter.messKey+".subject",lang))+"\"");
			}
		}
		if(dialogObject.formulaParameter.onloadInfo){
			var onloadInfo = dialogObject.formulaParameter.onloadInfo;
			for(var key in onloadInfo){
				var field = $("[name='expression_"+key+"']")[0];
				if(typeof window.ActiveXObject!="undefined") {
					field.onbeforedeactivate = getCaret;
				} else {
					field.onblur = getCaret;
				}
				
				var scriptIn = onloadInfo[key] ? onloadInfo[key].id : "";
				var scriptDis = onloadInfo[key] ? onloadInfo[key].name : "";
				var preInfo = {rightIndex:-1};
				var scriptOut = "";
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
				
				var myScriptIn = scriptOut;
				scriptOut = "";
				var preLbpmInfo = {rightIndex:-1};
				var nxtLbpmInfoDis = getLbpmNextInfo(scriptDis);
				for (var nxtLbpmInfo = getLbpmNextInfo(myScriptIn, preLbpmInfo); nxtLbpmInfo!=null; nxtLbpmInfo = getLbpmNextInfo(myScriptIn, nxtLbpmInfo)) {
					var varName = getLbpmVarNameById(nxtLbpmInfo.varName);
					if(varName==null){
						varName = nxtLbpmInfoDis.varName;
						errorVar += "; " + varName;
					}
					scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1, nxtLbpmInfo.leftIndex) + "%" + varName + "%";
					preLbpmInfo = nxtLbpmInfo;
					nxtLbpmInfoDis = getLbpmNextInfo(scriptDis, nxtLbpmInfoDis);
				}
				scriptOut += myScriptIn.substring(preLbpmInfo.rightIndex+1);
				field.value = scriptOut;
			}
		}
	}else{
		if(dialogObject.formulaParameter.messKey){
			$(field).after("<span>"+Data_GetResourceString("sys-lbpmservice-support:lbpmProcess.customNotify.default")+"\""+replaceValueToText(Data_GetResourceString(dialogObject.formulaParameter.messKey+".subject"))+"\"</span>");
		}
	}
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

function replaceValueToText(str){
	var varInfo = getLbpmCustomNotifyVars();
	return str.replace(/%(\w+)%/g,function($1,$2){
		for(var i = 0;i<varInfo.length;i++){
			if(varInfo[i].value == $1){
				return "%" + varInfo[i].text + "%";
			}
		}
		return $1;
	});
}

//添加关闭事件
Com_AddEventListener(window, "beforeunload", function(){dialogObject.AfterShow();});
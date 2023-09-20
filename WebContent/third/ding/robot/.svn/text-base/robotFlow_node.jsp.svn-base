<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script>
// 多语言对象
LangObject = parent.FlowChartObject.Lang;
// 当前节点多语言对象
LangNodeObject = LangObject.Node;
</script>
<script type="text/javascript" src="../../../resource/js/common.js"></script>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
Com_IncludeFile("docutil.js|doclist.js|dialog.js|data.js|formula.js");
</script>
<script>
// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
function returnValue() {
	if (!check()) return null;
	return "{" + getParameterJsonByTable() + "}";
};

function check() {
	var puId = getSelectValue(document.getElementById("dingProcessList"));
	if (null != puId) {
		if (puId == "choose") {
			//alert("${ lfn:message('third-im-kk:robotnode_hint_6') }");
			alert("请选择钉钉流程模板！");
			return false;
		}
	}
	else{
		alert("请选择钉钉流程模板！");
		return false;
	}
	return true;
};
function getParameterJsonByTable() {
	var rtnJson = new Array();
	var table = document.getElementById("main");
	for (var i = 2, length = table.rows.length; i < length; i++) {
		rtnJson.push(getParamterJsonByRow(table.rows[i]));
	}
	var puId = getSelectValue(document.getElementById("dingProcessList"));
	return "\"DingParams\":[" + rtnJson.join(",") + "]" + ",\"processCode\":\""  + puId + "\"";
};
function getSelectValue(select) {
	if (select != null) {
		var idx = select.selectedIndex, option, value;
		if (idx > -1) {
			option = select.options[idx];
			value = option.attributes.value;
			return (value && value.specified) ? option.value : option.text;
		}
	}
	return null;
}
function getParamterJsonByRow(trElement) {
	console.log(trElement);
	var rtnJson = new Array();
	rtnJson.push("{");
	// 表单字段名
	var fieldSelect = getElementsByParent(trElement.cells[0], "select")[0];
	var option = fieldSelect.options[fieldSelect.selectedIndex];
	rtnJson.push("\"field\":\"" + option.value + "\"");
	rtnJson.push(",\"fieldName\":\"" + option.text + "\"");
	// 数据类型
	var selectedIndex = getIndexFromFieldList(option.value), fieldList = parent.FlowChartObject.FormFieldList;
	rtnJson.push(",\"dataType\":\"" + ((selectedIndex == null) ? "Object" : fieldList[selectedIndex].type) + "\"");
	// 更新值
	var inputArr = getElementsByParent(trElement.cells[1], "input");
	rtnJson.push(",\"idField\":\"" + parent.formatJson(inputArr[0].value) + "\"");
	rtnJson.push(",\"nameField\":\"" + parent.formatJson(inputArr[1].value) + "\"");
	// 结束
	rtnJson.push("}");
	
	return rtnJson.join('');
};
function getElementsByParent(oParent, tagName) {
	var rtnResult = new Array(), child;
	for (var i = 0, length = oParent.childNodes.length; i < length; i++) {
		child = oParent.childNodes[i];
		if (child.tagName && child.tagName.toLowerCase() == tagName)
			rtnResult.push(child);
	}
	return rtnResult;
};
function getIndexFromFieldList(value) {
	var _value = value || '';
	if (_value == '') return null;
	var fieldList = parent.FlowChartObject.FormFieldList;
	if (!fieldList) return null;
	for (var i = 0, length = fieldList.length; i < length; i++) {
		if (fieldList[i].name == value) return i;
	}
	return null;
};



//追加行
function appendRow(table, paramObject) {
	var _table = table || document.getElementById('main');
	createRow(_table.insertRow(_table.rows.length), paramObject);
};
//删除行
function deleteRow(owner) {
	var findNode = owner;
	for (;findNode.tagName && findNode.tagName.toLowerCase() != 'tr'; findNode = findNode.parentNode) {}
	if (findNode && findNode.tagName) findNode.parentNode.removeChild(findNode);
};
//paramObject格式为：{field:'', fieldName:'', idField:'', nameField:''}
function createRow(trElement, paramObject) {
	var _paramObject = paramObject || {field:'', fieldName:'', value:'', showValue:''}
	var tdElement, html;
	// 表单字段名
	tdElement = trElement.insertCell(-1);
	tdElement.setAttribute('align', 'center');
	tdElement.innerHTML = createSelectElementHTML(transFormFieldList(), _paramObject.field, _paramObject.fieldName);
	// 更新值
	tdElement = trElement.insertCell(-1);
	html = createInputElementHTML(_paramObject.idField, 'hidden');
	html += createInputElementHTML(_paramObject.nameField, '');
/* 	html += '<br>' + createHrefElementHTML(LangNodeObject['selectFormList'], 'openExpressionEditor(this);'); */
	tdElement.innerHTML = html;
	// 删除操作
	tdElement = trElement.insertCell(-1);
	tdElement.setAttribute('align', 'center');
	tdElement.innerHTML = createHrefElementHTML(LangObject.Operation['operationDelete'], 'deleteRow(this);');
};
function createHrefElementHTML(text, actionJs) {
	var _actionJs = actionJs || '';
	return '<a href=\'JavaScript:void(0);\' onclick=\'' + _actionJs + '\'>' + text + '</a>';
};
function createSelectElementHTML(options, checkValue, checkName) {
	var rtnResult = new Array();
	if (options == null || options.length == 0) {
		var value = checkValue || '', name = checkName || '';
		if (name != '')	rtnResult.push('<option value=\'' + value + '\' selected>' + name + '</option>');
	} else {
		for (var i = 0, length = options.length; i < length; i++) {
			var option = options[i], value = option.value || option.name;
			rtnResult.push('<option value=\'' + value + '\'');
			if (value == checkValue) rtnResult.push(' selected');
			rtnResult.push('>' + option.name + '</option>');
		}
	}
	return '<select>' + rtnResult.join('') + '</select>';
};
function transFormFieldList() {
	var rtnResult = new Array();
	var fieldList = parent.FlowChartObject.FormFieldList;
	if (!fieldList) return rtnResult;
	// 转换成option支持的格式
	for (var i = 0, length = fieldList.length; i < length; i++) {
		// 不支持在机器人节点里面往明细表里面的字段填值 by zhugr 2017-09-01		
		if((fieldList[i].name && fieldList[i].name.indexOf(".") > 0) || (fieldList[i].controlType && fieldList[i].controlType == 'detailsTable')){
			continue;
		}
		rtnResult.push({value:fieldList[i].name, name:fieldList[i].label});
	}
	return rtnResult;
};
function createInputElementHTML(value, status) {
	var _value = value || '', statusHTML = '', styleHTML = 'width:100%;';
	switch(status) {
	case 'readOnly':
		statusHTML += ' readOnly';
		break;
	case 'hidden':
		styleHTML += 'display:none;';
		break;
	}
	return '<input class=\'inputsgl\' value=\'' + Com_StrEscape(_value) + '\'' + statusHTML + ' style=\'' + styleHTML + '\'>';
};
/***********************************************
功能：转义代码中的敏感字符
***********************************************/
function Com_StrEscape(s){
	if(s==null || s=="")
		return "";
	var re = /&/g;
	s = s.replace(re, "&amp;");
	re = /\\/g;
	s = s.replace(re, "\\\\");
	re = /\"/g;
	s = s.replace(re, "&quot;");
	re = /'/g;
	s = s.replace(re, '&#39;');
	re = /</g;
	s = s.replace(re, "&lt;");
	re = /\r\n|[\r\n]/g;
	s = s.replace(re, "<BR>");
	re = />/g;
	s.replace(re, "&gt;");
	re = /\r\n|[\r\n]/g;
	return s = s.replace(re, "<BR>");
}


//输出参数
function OutputParamters(params) {
	var _params = params || null;
	if (_params == null) return;
	// 输出...
	var table = document.getElementById('main');
	for (var i = 0, length = _params.length; i < length; i++) {
		appendRow(table, _params[i],i);
	}
};

//选择钉钉流程模板
var dingProcessList;

function getDingProcessValues() {
	if (dingProcessList === undefined) {
		return new Array();
	}
	return dingProcessList;
}

function check() {
	return true;
}
var commonListTdParam = {
	"border" : false,
	"width" : "60px",
	"type" : "select",
	"id" : "dingProcessList",
	"defaultInputText" : "",
	"checkInputText" : "",
	"defaultSelectValue" : "",
	"checkSelectValue" : "",
	"innerText" : ""
};
function getSelectElementHTML(id, options, checkSelectValue,
		defaultSelectValue) {
	var rtnResult = new Array();
	rtnResult.push('<option value=\'choose\'>${ lfn:message("third-im-kk:robotnode_hint_3") }</option>');
	if (options == null || options.length == 0) {
		var value = checkSelectValue || defaultSelectValue;
		if (value != '')
			rtnResult.push('<option value=\'' + value + '\' selected>'
					+ value + '</option>');
	} else {
		var value = checkSelectValue || defaultSelectValue;
		for (var i = 0, length = options.length; i < length; i++) {
			var option = options[i], optionValue = option.processCode;
			rtnResult.push('<option value=\'' + optionValue + '\'');
			if (optionValue == value) {
				rtnResult.push(' selected');
			}
			rtnResult.push('>' + option.name + '</option>');
		}
	}
	if (id == "dingProcessList") {
		return '<select onchange="changeDescription(this);" id="' + id
				+ '"' + ' style=\'width: 160px;\'>' + rtnResult.join('')
				+ '</select>';
	}
	return '<select id="' + id + '"' + ' style=\'width: 160px;\'>'
			+ rtnResult.join('') + '</select>';
}
function changeDescription(select) {
	if (select.value == "choose") {
		document.getElementById("processCode").innerHTML = "";
	} else {
		var values = getDingProcessValues();
		for (var i = 0, length = values.length; i < length; i++) {
			if (values[i].value == select.value) {
				document.getElementById("processCode").innerHTML = values[i];
				break;
			}
		}
	}
}
function createDingProcess(trElement, tdParam, options) {
	var tdElement = trElement.insertCell(trElement.cells.length);
	tdElement.colSpan = 2;
	if (tdParam.border == true) {
		tdElement.style.borderTop = "0px";
	}
	if (tdParam.width != "") {
		tdElement.style.width = tdParam.width;
	}
	if (tdParam.type == "select") {
		tdElement.innerHTML = getSelectElementHTML(tdParam.id, options,
				tdParam.checkSelectValue, tdParam.defaultSelectValue)
				+ tdParam.innerText;
		console.log(tdElement);
		return tdElement;
	}
	tdElement.innerHTML = tdParam.innerText;
	return tdElement;
}
function initDingProcess(){
	var url = '<c:url value="/third/ding/third_ding_orm_temp/thirdDingOrmTemp.do?method=getProcess" />';
	$.ajax({
      type: "post",
      url: url,
      async : false,
      dataType: "json",
      success: function (data ,textStatus, jqXHR)
      {
    	 dingProcessList = data.processList;
    	 createDingProcess(document.getElementById("dingProcess"), commonListTdParam, dingProcessList);
      }
   });
};

function initDocument() {
	// 节点原配置的类型不是当前类型或没有获取到相关配置
	if (parent.NodeData.unid != parent.document.getElementById("type").value || !parent.NodeContent) {
		initDingProcess();
		appendRow();
		return;
	}
	// 获得内容对象
	console.log(parent.NodeContent);
	var json = eval('(' + parent.NodeContent + ')');
	console.log(json);
	// 输出参数
	OutputParamters(json.DingParams);
	commonListTdParam.defaultSelectValue = json.processCode;
	initDingProcess();
}


</script>
<body onload="initDocument();">
	<center>
		<table id="main" width="100%" class="tb_normal">
			<h3>表单字段映射</h3>
			<!-- <tr align="center">
				<td width="30%"><script>document.write(Data_GetResourceString("km-cogroup:robot.bizType.name"));</script></td>
				<td width="70%">
				<nobr>
				<input id="field1" name="field1" class="inputsgl" value="" style="width:10%;display:none;">
				<input id="field2" name="field2" class="inputsgl" value="" style="width:68%;float: left;" maxlength="8" onblur="checkWord(8,this)"><span class="txtstrong" style="float: left;">*(最多输入8个字符)</span>
				<input id="field9" name="field9" class="inputsgl" value="" style="width:10%;display:none;">
				<input id="field10" name="field10" class="inputsgl" value="" style="width:68%;float: left;" maxlength="8" onblur="checkWord(8,this)"><span style="float: left;">English</span>
				</nobr>
				</td>
			</tr> -->
			
			<tr align="center" id="dingProcess">
				<td>钉钉流程模板</td>
			</tr>
			<tr align="center">
				<td>表单字段</td>
				<td>钉钉表单字段名</td>
				<td><a href="JavaScript:appendRow();"><script>document.write(LangObject.Operation["operationAdd"]);</script></a></td>
			</tr>
		</table>
	</center>
</body>
</html>	
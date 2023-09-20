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
	return getParameterJsonByTable();
};

function check() {
	var field2=document.getElementById('field2').value;
	var field4=document.getElementById('field4').value;
	var field6=document.getElementById('field6').value;
	var field8=document.getElementById('field8').value;
	if("" == field2){
		alert("<bean:message key="robot.bizType.name.nonull" bundle="km-cogroup"/>");
		return false;
	}
	if("" == field4){
		alert("<bean:message key="robot.groupName.nonull" bundle="km-cogroup"/>");
		return false;
	}
	if("" == field6){
		alert("<bean:message key="robot.admin.nonull" bundle="km-cogroup"/>");
		return false;
	}
	if("" == field8){
		alert("<bean:message key="robot.users.nonull" bundle="km-cogroup"/>");
		return false;
	}
	return true;
};
function getParameterJsonByTable() {
	var field1=document.getElementById('field1').value;
	var field2=document.getElementById('field2').value;
	var field9=document.getElementById('field9').value;
	var field10=document.getElementById('field10').value;
	var field3=document.getElementById('field3').value;
	var field4=document.getElementById('field4').value;
	var field5=document.getElementById('field5').value;
	var field6=document.getElementById('field6').value;
	var field7=document.getElementById('field7').value;
	var field8=document.getElementById('field8').value;
	var f1="{\"idField\":\""+field1+"\",\"nameField\":\""+field2+"\"},";
	var f2="{\"idField\":\""+parent.formatJson(field3)+"\",\"nameField\":\""+parent.formatJson(field4)+"\"},";
	var f3="{\"idField\":\""+field5+"\",\"nameField\":\""+field6+"\"},";
	var f4="{\"idField\":\""+field7+"\",\"nameField\":\""+field8+"\"},";
	var f5="{\"idField\":\""+field9+"\",\"nameField\":\""+field10+"\"}";
	return "{\"coparams\":[" +f1+f2+f3+f4+f5 + "]}";
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


function openExpressionEditorByCogroup(owner) {
	var idField, nameField;
	var arrElement = getElementsByParent(owner.parentNode, "input");
	if (arrElement.length < 2) return;
		idField = arrElement[0];
		nameField = arrElement[1];
	// 获取相应配置的数据类型
	var selectValue = null;
	var selectedIndex = null, fieldList = parent.FlowChartObject.FormFieldList;
	// 调用公式定义器
	Formula_Dialog(
			idField,
			nameField,
			parent.FlowChartObject.FormFieldList,
			"Object",
			null,
			null,
			parent.FlowChartObject.ModelName);
};


//追加行
function appendRow(table, paramObject,n) {
	 document.getElementById('field'+(n*2+1)).value=paramObject.idField;
	 document.getElementById('field'+(n*2+2)).value=paramObject.nameField;
};

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

function initDocument() {
	if(parent.NodeContent!='' && typeof(parent.NodeContent)!='undefined')
	{
	// 获得内容对象
	console.log(parent.NodeContent);
	var json = eval('(' + parent.NodeContent + ')');
	console.log(json);
	if(json && json.coparams)
		console.log(json.coparams);
		OutputParamters(json.coparams);
	}
};

function checkWord(len,evt){
	//len为英文字符的个数，中文自动为其一般数量
	//evt是欲检测的对象
   var str = evt.value;
    var myLen = 0;
    for(i=0; i<str.length&&myLen<=len; i++){
        if(str.charCodeAt(i)>0&&str.charCodeAt(i)<128)
            myLen++;
        else
            myLen+=2;
        }
    if(myLen>len){
        evt.value = str.substring(0,i-1);
    }
}

</script>
<body onload="initDocument();">
<table id="main" width="100%" class="tb_normal">
	<tr align="center">
		<td width="30%"><script>document.write(Data_GetResourceString("km-cogroup:robot.bizType.name"));</script></td>
		<td width="70%">
		<nobr>
		<input id="field1" name="field1" class="inputsgl" value="" style="width:10%;display:none;">
		<input id="field2" name="field2" class="inputsgl" value="" style="width:68%;float: left;" maxlength="8" onblur="checkWord(8,this)"><span class="txtstrong" style="float: left;">*(最多输入8个字符)</span>
		<input id="field9" name="field9" class="inputsgl" value="" style="width:10%;display:none;">
		<input id="field10" name="field10" class="inputsgl" value="" style="width:68%;float: left;" maxlength="8" onblur="checkWord(8,this)"><span style="float: left;">English</span>
		</nobr>
		</td>
	</tr>
	<tr align="center">
		<td><script>document.write(Data_GetResourceString("km-cogroup:robot.groupName"));</script></td>
		<td>
		<nobr>
		<input id="field3" name="field3" class="inputsgl" value="" style="width:10%;display:none;">
		<input id="field4" name="field4" class="inputsgl" value="" readonly="" style="width:78%;"><span class="txtstrong">*</span>
		<a href="JavaScript:void(0);" onclick="openExpressionEditorByCogroup(this);"><script>document.write(Data_GetResourceString("km-cogroup:robot.formula"));</script></a>
		</nobr>
		</td>
	</tr>
	<tr align="center">
		<td><script>document.write(Data_GetResourceString("km-cogroup:robot.admin"));</script></td>
		<td>
		<nobr>
		<input id="field5" name="field5" class="inputsgl" value="" style="width:10%;display:none;">
		<input id="field6" name="field6" class="inputsgl" value="" readonly="" style="width:78%;"><span class="txtstrong">*</span>
		<a href="JavaScript:void(0);" onclick="openExpressionEditorByCogroup(this);"><script>document.write(Data_GetResourceString("km-cogroup:robot.formula"));</script></a>
		</nobr>
		</td>
	</tr>
	<tr align="center">
		<td><script>document.write(Data_GetResourceString("km-cogroup:robot.users"));</script></td>
		<td>
		<nobr>
		<input id="field7" name="field7" class="inputsgl" value="" style="width:10%;display:none;">
		<input id="field8" name="field8" class="inputsgl" value="" readonly="" style="width:78%;"><span class="txtstrong">*</span>
		<a href="JavaScript:void(0);" onclick="openExpressionEditorByCogroup(this);"><script>document.write(Data_GetResourceString("km-cogroup:robot.formula"));</script></a>
		</nobr>
		</td>
	</tr>
</table>
</body>
</html>	
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script>
	Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
	Com_IncludeFile("data.js");
</script>
</head>
<script type="text/javascript">

var dialogObject = parent.dialogObject;
</script>

<script>

var optData;
var optDataBackup = null;

function writeMessage(key){
	document.write(Com_Parameter.DialogLang[key]);
}

function writeButtonMessage(btnIds, keys){
	var btnArr = btnIds.split(";");
	var keyArr = keys==null?btnArr:keys.split(";");
	for(var i=0; i<btnArr.length;i++){
		document.getElementById(btnArr[i]).value = Com_Parameter.DialogLang[keyArr[i]];
	}
}

initComParameter();
function initComParameter(){
	var style = Com_GetUrlParameter(location.href, "s_css");
	if(style==null || style==""){
		var aCookie = document.cookie.split("; ");
		for (var i=0; i<aCookie.length; i++){
			var aCrumb = aCookie[i].split("=");
			if ("KMSS_Style" == aCrumb[0]){
				style = aCrumb[1];
				break;
			}
		}
	}
	if(style==null || style=="")
		style = "default";
	Com_Parameter.Style = style;
	Com_Parameter.StylePath = "../style/"+style+"/";
	Com_Parameter.DialogLang = new Object();
	var langArr = Data_GetResourceString("button.ok;button.cancel;dialog.selectNone;message.keyword;dialog.requiredKeyword;"+
		"button.search;button.clear;dialog.optList;dialog.selList;dialog.add;"+
		"dialog.delete;dialog.addAll;dialog.deleteAll;dialog.moveUp;dialog.moveDown;"+
		"dialog.description;dialog.currentValue;dialog.requiredSelect;sys-xform-maindata:sysXformJdbc.control.updateFunc;"+
		"sys-xform-maindata:sysXformJdbc.control.requiredFuncSelect;sys-xform-maindata:sysXformJdbc.control.requiredCateSelect;"+
		"sys-xform-maindata:sysXformJdbc.control.addFunc");
	Com_Parameter.DialogLang.btnOk = langArr[0]==null?"OK":langArr[0];
	Com_Parameter.DialogLang.btnCancel = langArr[1]==null?"Cancel":langArr[1];
	Com_Parameter.DialogLang.btnSelectNone = langArr[2]==null?"SelectNone":langArr[2];
	Com_Parameter.DialogLang.keyword = langArr[3]==null?"Keyword":langArr[3];
	Com_Parameter.DialogLang.requiredKeyword = langArr[4]==null?"Keyword could not be empty!":langArr[4];

	Com_Parameter.DialogLang.btnSearch = langArr[5]==null?"Search":langArr[5];
	Com_Parameter.DialogLang.btnClear = langArr[6]==null?"Clear":langArr[6];
	Com_Parameter.DialogLang.optList = langArr[7]==null?"Options":langArr[7];
	Com_Parameter.DialogLang.selList = langArr[8]==null?"Selected":langArr[8];
	Com_Parameter.DialogLang.btnAdd = langArr[9]==null?"Add":langArr[9];

	Com_Parameter.DialogLang.btnDelete = langArr[10]==null?"Remove":langArr[10];
	Com_Parameter.DialogLang.btnAddAll = langArr[11]==null?"Add All":langArr[11];
	Com_Parameter.DialogLang.btnDeleteAll = langArr[12]==null?"Remove All":langArr[12];
	Com_Parameter.DialogLang.btnMoveUp = langArr[13]==null?"Up":langArr[13];
	Com_Parameter.DialogLang.btnMoveDown = langArr[14]==null?"Down":langArr[14];
	
	Com_Parameter.DialogLang.description = langArr[15]==null?"Description: ":langArr[15];
	Com_Parameter.DialogLang.currentValue = langArr[16]==null?"":langArr[16];
	Com_Parameter.DialogLang.requiredSelect = langArr[17]==null?"Please select a value!":langArr[17];
	Com_Parameter.DialogLang.updateFunc = langArr[18]==null?"update":langArr[18];
	Com_Parameter.DialogLang.requiredFuncSelect = langArr[19]==null?"Please select a value!":langArr[19];
	Com_Parameter.DialogLang.requiredCateSelect = langArr[20]==null?"Please select a category!":langArr[20];
	Com_Parameter.DialogLang.addFunc = langArr[21]==null?"add":langArr[21];
}

function setOptData(data, isSearch){
	var obj = document.getElementById("btnClear");
	if(isSearch){
		if(optDataBackup==null)
			optDataBackup = optData;
		obj.disabled = false;
	}else{
		optDataBackup = null;
		obj.disabled = true;
	}
	if(data!=null){
		optData = data.Format("value:text");
	}
	if(!(optData instanceof KMSSData)){
		optData = new KMSSData(optData);
	}
	optData.PutToSelect("F_OptionList", "value", "text");
}
function showDescription(data, index){
	var hashMapArr = data.GetHashMapArray();
	if(hashMapArr[index]!=null){
		var hashMap = hashMapArr[index];
		var desc = hashMap.info||hashMap.text||"";
		document.getElementById("Span_MoreInfo").innerHTML = desc;
	}	
}
function searchUseXML(){
	var obj = document.getElementsByName("F_Keyword")[0];
	if(obj.value==""){
		alert(Com_Parameter.DialogLang.requiredKeyword);
		obj.focus();
		return;
	}
	var beanURL = dialogObject.searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(obj.value));
	setOptData(new KMSSData().AddXMLData(beanURL), true);
}
window.onload = function(){
	setOptData(dialogObject.optionData);
	if(dialogObject.valueData!=null){
		var data = dialogObject.valueData;
		if(data!=null)
			document.getElementById("Span_CurValue").innerHTML = Com_Parameter.DialogLang.currentValue+data;
	}
	writeButtonMessage("btnSearch;btnClear;btnOk;btnCancel");
}
function Com_DialogReturnValue(){
	var rtnVal = {};
	var obj = document.getElementsByName("F_OptionList")[0];
	if(obj.selectedIndex==-1){
		alert(Com_Parameter.DialogLang.requiredSelect);
	}else{
		rtnVal.value = obj.value;
		rtnVal.text = obj.options[obj.selectedIndex].text;
		parent.Com_DialogReturn(rtnVal);
	}
}
function Com_DialogUpdateFunc(){
	var rtnVal = new Array;
	var obj = document.getElementsByName("F_OptionList")[0];
	if(obj.selectedIndex==-1){
		alert(Com_Parameter.DialogLang.requiredFuncSelect);
	}else{
		var updateUrl = dialogObject.url+"?method=edit&fdId="+ obj.value;
		window.open(updateUrl);
	}
}
function Com_DialogAddFunc(){
	var categoryId = parent.treeFrame.getCategoryId();
	if(categoryId){
		var addUrl = dialogObject.url+"?method=add&categoryId="+categoryId;
		window.open(addUrl);
	}else{
		alert(Com_Parameter.DialogLang.requiredCateSelect);
	}
}
function Com_DialogReturnEmpty(){
	parent.Com_DialogReturn();
}
</script>
</head>
<body topmargin=10>
<span id="Span_CurValue"></span>
<table id="TB_Search" width=100% border=0 cellspacing=0 cellpadding=0 style="display:none">
	<tr valign=middle height=30>
		<td width=50><script>writeMessage("keyword");</script></td>
		<td>
			<input name="F_Keyword" value="" class="inputdialog" style="width:100%"
				onkeydown="if (Com_GetEventObject().keyCode==13){searchUseXML();return false;}">
		</td>
		<td width=100 align=right>
			<input id="btnSearch" type=button onclick="searchUseXML();" class="btndialog">
			<input id="btnClear" type=button onclick="setOptData(optDataBackup);" class="btndialog" disabled>
		</td>
	</tr>
</table>
<span id="Span_Search"><br></span>
<table border=1 cellspacing=2 cellpadding=0 bordercolor=#003048 width=100%>
	<tr valign=middle height=25 align=center>
		<td width=50%><script>writeMessage("optList");</script></td>
	</tr>
	<tr valign=middle align=center>
		<td style="padding:0px">
			<select name="F_OptionList" class="namelist" style="width:100%; height:200px;" size=2
				ondblclick="if(selectedIndex>-1)Com_DialogReturnValue();"
				onclick="showDescription(optData, selectedIndex);"
				onchange="showDescription(optData, selectedIndex);">
			</select>
		</td>
	</tr>
	<tr valign=middle height=35>
		<td align=center>
			<input id="btnOk" type=button class="btndialog" style="width:50px"
				onclick="Com_DialogReturnValue();">&nbsp;
			<input id="btnCancel" type=button class="btndialog" style="width:50px"
				onclick="parent.Com_DialogReturn();">
		</td>
	</tr>
	<tr valign=top height=75>
		<td style="padding:3px">
			<script>writeMessage("description");</script><span id="Span_MoreInfo"></span>
		</td>
	</tr>
</table>
</body>
</html>

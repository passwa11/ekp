<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<script>
var dialogRtnValue = null;
var dialogObject=null;
var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1
if(window.showModalDialog && flag){ //判断是window系统且是IE浏览器
	dialogObject = window.dialogArguments;
}else if (opener && opener.Com_Parameter.Dialog){
	dialogObject = opener.Com_Parameter.Dialog;
}else{
	dialogObject = (Com_Parameter.top || window.top).Com_Parameter.Dialog;
	isOpenWindow = false;
}
if (dialogObject) {
	Com_Parameter.XMLDebug = dialogObject.XMLDebug;
	Com_Parameter.Lang = dialogObject.Lang;
}
var Data_XMLCatche = new Object();
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
Com_IncludeFile("data.js");
</script>
<script>
initComParameter();
function initComParameter(){
	Com_Parameter.DialogLang = new Object();
	var langArr = Data_GetResourceString("button.ok;button.cancel;dialog.selectNone;message.keyword;dialog.requiredKeyword;"+
		"button.search;button.clear;dialog.optList;dialog.selList;dialog.add;"+
		"dialog.delete;dialog.addAll;dialog.deleteAll;dialog.moveUp;dialog.moveDown;"+
		"dialog.description;dialog.currentValue;dialog.requiredSelect");
	Com_Parameter.DialogLang.btnOk = langArr[0]==null?"OK":langArr[0];
	Com_Parameter.DialogLang.btnCancel = langArr[1]==null?"Cancel":langArr[1];
	Com_Parameter.DialogLang.btnSelectNone = langArr[2]==null?"SelectNone":langArr[2];
	Com_Parameter.DialogLang.keyword = langArr[3]==null?"Keyword":langArr[3];
	Com_Parameter.DialogLang.requiredKeyword = langArr[4]==null?"Keyword could not be empty!":langArr[4];

	Com_Parameter.DialogLang.btnSearch = langArr[5]==null?"Search":langArr[5];;
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
}

var optData;
var selData;
var treeParamVal = "";
function setOptData(data, isSearch){
	var obj = document.getElementById("btnClear");
	if(isSearch){
		if(optDataBackup==null)
			optDataBackup = optData;
		obj.disabled = false;
	}else{
		optDataBackup = null;
		//obj.disabled = true;
	}
	if(data!=null){
		optData = data.Format("id:name:info");
	}
	if(!(optData instanceof KMSSData))
		optData = new KMSSData(optData);
	optData.PutToSelect("F_OptionList", "id", "name");
}
function setSelData(data){
	if(data!=null)
		selData = data.Format("id:name:info");
	if(!(selData instanceof KMSSData))
		selData = new KMSSData(selData);
	selData.PutToSelect("F_SelectedList", "id", "name");
}
function optionAdd(isAll){
	if(isAll){
		selData.AddKMSSData(optData);
	}else{
		var optHashMapArr = optData.GetHashMapArray();
		var obj = document.getElementsByName("F_OptionList")[0].options;
		for(var i=0; i<obj.length; i++){
			if(obj[i].selected)
				selData.AddHashMap(optHashMapArr[i]);
		}
	}
	setSelData();
}
function optionDelete(isAll){
	if(isAll)
		setSelData(new KMSSData());
	else{
		var obj = document.getElementsByName("F_SelectedList")[0].options;
		for(var i=obj.length-1; i>=0; i--){
			if(obj[i].selected)
				selData.Delete(i);
		}
		setSelData();
	}
}
function optionMove(direct){
	var obj = document.getElementsByName("F_SelectedList")[0].options;
	var i, j, tmpData;
	var selIndex = new Array;
	var n1 = 0;
	var n2 = obj.length - 1;
	for(i=direct>0?obj.length-1:0; i>=0 && i<obj.length; i-=direct){
		j = i + direct;
		if(obj[i].selected){
			if(j>=n1 && j<=n2){
				selData.SwitchIndex(i, j);
				selIndex[selIndex.length] = j;
			}else{
				if(direct<0)
					n1 = i + 1;
				else
					n2 = i - 1;
				selIndex[selIndex.length] = i;
			}
		}
	}
	setSelData();
	for(i=0; i<selIndex.length; i++)
		obj[selIndex[i]].selected = true;
}
function showDescription(data, index){
	var hashMapArr = data.GetHashMapArray();
	if(hashMapArr[index]!=null)
		document.getElementById("Span_MoreInfo").innerHTML = hashMapArr[index].info;
}
function searchUseXML(){
	var obj = document.getElementsByName("F_Keyword")[0];
	if(obj.value==""){
		alert(Com_Parameter.DialogLang.requiredKeyword);
		obj.focus();
		return;
	}
	var beanURL = dialogObject.searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(obj.value));
	if(treeParamVal!=""){
		beanURL += treeParamVal;
	}
	setOptData(new KMSSData().AddXMLData(beanURL), true);
}
window.onload = function(){
	if(dialogObject.searchBeanURL!=null){
		document.getElementById("Span_Search").style.display = "none";
		document.getElementById("TB_Search").style.display = "";
	}
	if(dialogObject.winRemark!=null){
		document.getElementById("Span_Remark").innerHTML=dialogObject.winRemark;
	}
	setOptData(dialogObject.optionData);
	setSelData(dialogObject.valueData);
	writeButtonMessage("btnSearch;btnClear;btnAdd;btnDelete;btnAddAll;btnDeleteAll;btnMoveUp;btnMoveDown;btnOk;btnCancel");
}

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

function Com_DialogReturn(value){
	$dialog.hide(value);
}
</script>
<body topmargin=5>
<table id="TB_Search" width=100% border=0 cellspacing=0 cellpadding=0 style="display:none">
	<tr valign=middle height=30>
		<td width=50><script>writeMessage("keyword")</script></td>
		<td>
			<input name="F_Keyword" value="" class="inputdialog" style="width:100%"
				onkeydown="if (Com_GetEventObject().keyCode==13){searchUseXML();return false;}">
		</td>
		<td width=100 align=right>
			<input id="btnSearch" type=button onclick="searchUseXML();" class="btndialog">
			<input id="btnClear" type=button onclick="document.getElementsByName('F_Keyword')[0].value='';setOptData(optDataBackup);" class="btndialog">
		</td>
	</tr>
</table>
<span id="Span_Search"><br></span>
<table border=1 cellspacing=2 cellpadding=0 bordercolor=#eaeaea width=100%>
	<tr valign=middle height=25 align=center>
		<td width=40%><script>writeMessage("optList");</script></td>
		<td style="width:94px">&nbsp;</td>
		<td width=40%><script>writeMessage("selList");</script></td>
	</tr>
	<tr valign=middle align=center>
		<td style="padding:0px">
			<select name="F_OptionList" multiple class="namelist" style="width:100%; height:216px;"
				ondblclick="optionAdd();"
				onclick="showDescription(optData, selectedIndex);"
				onchange="showDescription(optData, selectedIndex);">
			</select>
		</td>
		<td>
			<input id="btnAdd" type=button class="btndialog" style="width:70px" onclick="optionAdd();">
			<br><br>
			<input id="btnDelete" type=button class="btndialog" style="width:70px" onclick="optionDelete();">
			<br><br>
			<input id="btnAddAll" type=button class="btndialog" style="width:70px" onclick="optionAdd(true);">
			<br><br>
			<input id="btnDeleteAll" type=button class="btndialog" style="width:70px" onclick="optionDelete(true);">
			<br><br>
			<input id="btnMoveUp" type=button class="btndialog" style="width:33px" onclick="optionMove(-1);">
			<input id="btnMoveDown" type=button class="btndialog" style="width:33px" onclick="optionMove(1);">
		</td>
		<td style="padding:0px">
			<select name="F_SelectedList" multiple class="namelist" style="width:100%; height:216px;"
				ondblclick="optionDelete();"
				onclick="showDescription(selData, selectedIndex);"
				onchange="showDescription(selData, selectedIndex);">
			</select>
		</td>
	</tr>
	<tr valign=middle height=35>
		<td colspan=3 align=center>
			<input id="btnOk" type=button class="btndialog" style="width:70px"
				onclick="Com_DialogReturn(selData.GetHashMapArray());">&nbsp;
			<input id="btnCancel" type=button class="btndialog" style="width:70px"
				onclick="Com_DialogReturn();">
		</td>
	</tr>
	<tr valign=top height=75>
		<td colspan=3 style="padding:3px">
			<script>writeMessage("description");</script><span id="Span_MoreInfo"></span>
			<br/><span id="Span_Remark"></span>
		</td>
	</tr>
</table>
</body>
</html>
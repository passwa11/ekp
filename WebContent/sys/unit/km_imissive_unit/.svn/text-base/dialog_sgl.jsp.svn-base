<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<html>
<style>
.btndialog{ 
	line-height:20px!important;
}
</style>
<script type="text/javascript">
var Com_Parameter = {
	ContextPath:parent.Com_Parameter.ContextPath,
	ResPath:parent.Com_Parameter.ResPath,
	Style:parent.Com_Parameter.Style,
	StylePath:parent.Com_Parameter.StylePath,
	JsFileList:new Array,
	DialogLang:parent.Com_Parameter.DialogLang,
	Lang:parent.Com_Parameter.Lang
};
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
var dialogObject = parent.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
</script>
<script type="text/javascript" src="../../../resource/js/common.js"></script>
<script>
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
Com_IncludeFile("data.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
var optData;
var optDataBackup = null;
var treeParamVal = "";
function setOptData(data, isSearch){
	//var obj = document.getElementById("btnClear");
	if(isSearch){
		if(optDataBackup==null)
			optDataBackup = optData;
		//obj.disabled = false;
	}else{
		optDataBackup = null;
		//obj.disabled = true;
	}
	if (data != null)
		optData = data.Format("id:name:info");
	if (!(optData instanceof KMSSData))
		optData = new KMSSData(optData);
	optData.PutToSelect("F_OptionList", "id", "name");
}

function showDescription(data, index) {
	var hashMapArr = data.GetHashMapArray();
	if (hashMapArr[index] != null)
		document.getElementById("Span_MoreInfo").innerText = hashMapArr[index].info;
}

function searchUseXML() {
	var obj = document.getElementsByName("F_Keyword")[0];
	if (obj.value == "") {
		alert(Com_Parameter.DialogLang.requiredKeyword);
		obj.focus();
		return;
	}

	var beanURL = dialogObject.searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(obj.value));
	;
	var selectType = window.top.sysUnitSelectType;
	if (selectType === 1) {
		beanURL = Com_SetUrlParameter(beanURL, "s_bean", "sysUnitListWithAuthByGroupService");
	}else if(selectType === 2){
		beanURL = Com_SetUrlParameter(beanURL, "s_bean", "sysUnitListWithAuthByDecService");
	}else if(selectType === 3){
		beanURL = Com_SetUrlParameter(beanURL, "s_bean", "sysUnitListWithAuthByCateService");
	}
	
	if(treeParamVal!=""){
		beanURL += treeParamVal;
	}
	
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
	if(dialogObject.notNull){ 
		document.getElementById("btnSelectNone").style.display = "none";
	}
	setOptData(dialogObject.optionData);
	if(dialogObject.valueData!=null){
		var data = dialogObject.valueData.GetHashMapArray();
		if(data!=null && data.length>0 && data[0]["name"]!=null && data[0]["name"]!="")
			document.getElementById("Span_CurValue").innerHTML = Com_Parameter.DialogLang.currentValue+data[0]["name"];
	}
	writeButtonMessage("btnSearch;btnOk;btnCancel;btnSelectNone");
}
function Com_DialogReturnValue(){
	var rtnVal = new Array;
	var obj = document.getElementsByName("F_OptionList")[0];
	if(obj.selectedIndex==-1){
		alert(Com_Parameter.DialogLang.requiredSelect);
	}else{
		rtnVal[0] = optData.GetHashMapArray()[obj.selectedIndex];
		parent.Com_DialogReturn(rtnVal);
	}
}
function Com_DialogReturnEmpty(){
	parent.Com_DialogReturn(new Array());
}

function addOuterUnit(){
	var url = Com_Parameter.ContextPath+'sys/unit/km_imissive_unit/kmImissiveUnit.do?method=add&unitFlag=out';
	if(treeParamVal!=""){
		if (window.top.sysUnitSelectType === 0) {
			var param  = Com_GetUrlParameter(treeParamVal, "parentId");
			if(param!="all"){
				 url +="&parentId="+param;
			}
		}
	}
	 Com_OpenWindow(url,'_blank');
	}
</script>
</head>
<body topmargin=10>
<span id="Span_CurValue"></span>
<div>
	<div style="width: 82%;float: left">
		<table id="TB_Search" width=100% border=0 cellspacing=0 cellpadding=0 style="display:none">
			<tr valign=middle height=30>
				<td width=50><script>writeMessage("keyword");</script></td>
				<td>
					<input name="F_Keyword" value="" class="inputdialog" style="width:100%"
						onkeydown="if (Com_GetEventObject().keyCode==13){searchUseXML();return false;}">
				</td>
				<td width=50 align=left style="padding-left:8px">
					<input id="btnSearch" type=button onclick="searchUseXML();" class="btndialog">
				</td>
			</tr>
		</table>
	</div>
	<div style="width: 17%;float: right;padding-top: 4px;padding-bottom: 4px;padding-left: 4px">
		<kmss:auth requestURL="/sys/unit/km_imissive_unit/kmImissiveUnit.do?method=add&unitFlag=out">
			<input type=button id=addOuterUnit  class="btndialog" value='<bean:message  bundle="sys-unit" key="kmImissiveUnit.btn.add"/>'  onclick="addOuterUnit();">
		</kmss:auth>
	</div>
</div>
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
	<tr valign=top height=75>
		<td style="padding:3px">
			<script>writeMessage("description");</script><span id="Span_MoreInfo"></span>
		</td>
	</tr>
	<tr valign=middle height=35>
		<td align=center>
			<input id="btnOk" type=button class="btndialog" style="width:50px"
				onclick="Com_DialogReturnValue();">&nbsp;
			<input id="btnCancel" type=button class="btndialog" style="width:50px"
				onclick="parent.Com_DialogReturn();">
			<input id="btnSelectNone" type=button class="btndialog" style="width:80px"
				onclick="Com_DialogReturnEmpty();">
		</td>
	</tr>
</table>
</body>
</html>

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
var selData;
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
	for (i = 0; i < selIndex.length; i++)
		obj[selIndex[i]].selected = true;
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

	var beanURL = dialogObject.searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(obj.value));;
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
	setOptData(new KMSSData().AddXMLData(beanURL), true);
}
window.onload = function(){
	if(dialogObject.searchBeanURL!=null){
		document.getElementById("Span_Search").style.display = "none";
		document.getElementById("TB_Search").style.display = "";
	}
	setOptData(dialogObject.optionData);
	setSelData(dialogObject.valueData);
	writeButtonMessage("btnSearch;btnAdd;btnDelete;btnAddAll;btnDeleteAll;btnMoveUp;btnMoveDown;btnOk;btnCancel");
}

function addOuterUnit(){
	var url = Com_Parameter.ContextPath+'sys/unit/km_imissive_unit/kmImissiveUnit.do?method=add&unitFlag=out';
	
	if(treeParamVal!=""){
		if ( window.top.sysUnitSelectType === 0) {
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
<body topmargin=5>
<div>
	<div style="width: 82%;float: left">
		<table id="TB_Search" width=100% border=0 cellspacing=0 cellpadding=0 style="display:none">
			<tr valign=middle height=30>
				<td width=50><script>writeMessage("keyword")</script></td>
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
			<input type=button id=addOuterUnit class="btndialog" value='<bean:message  bundle="sys-unit" key="kmImissiveUnit.btn.add"/>'  onclick="addOuterUnit();">
		</kmss:auth>
	</div>
</div>
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
	<tr valign=top height=75>
		<td colspan=3 style="padding:3px">
			<script>writeMessage("description");</script><span id="Span_MoreInfo"></span>
		</td>
	</tr>
	<tr valign=middle height=35>
		<td colspan=3 align=center>
			<input id="btnOk" type=button class="btndialog" style="width:50px"
				onclick="parent.Com_DialogReturn(selData.GetHashMapArray());">&nbsp;
			<input id="btnCancel" type=button class="btndialog" style="width:50px"
				onclick="parent.Com_DialogReturn();">
		</td>
	</tr>
</table>
</body>
<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>
</html>
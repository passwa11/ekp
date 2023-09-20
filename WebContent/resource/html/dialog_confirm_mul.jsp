﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
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
		document.getElementById(btnArr[i]).title = Com_Parameter.DialogLang[keyArr[i]];
	}
}
var dialogObject = parent.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
</script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript">
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
var Selected_Data = null;
window.onload = function(){
	loadDefaultData();
	writeButtonMessage("btnOk;btnCancel;btnDelete;btnDeleteAll;btnMoveUp;btnMoveDown");
}
function loadDefaultData(){
	try{
		Selected_Data = parent.treeFrame.Selected_Data;
	}catch(e){
	}
	if(Selected_Data==null){
		setTimeout("loadDefaultData()", 100);
		return;
	}
	refreshSelectedList();
}

function refreshSelectedList(){
	Selected_Data.PutToSelect(document.getElementsByName("F_SelectedList")[0], "id", "name");
}

function optionMove(direct){
	if(Selected_Data==null)
		return;
	var obj = document.getElementsByName("F_SelectedList")[0].options;
	var i, j, tmpData;
	var selIndex = new Array;
	var n1 = 0;
	var n2 = obj.length - 1;
	for(i=direct>0?obj.length-1:0; i>=0 && i<obj.length; i-=direct){
		j = i + direct;
		if(obj[i].selected){
			if(j>=n1 && j<=n2){
				Selected_Data.SwitchIndex(i, j);
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
	refreshSelectedList();
	for(i=0; i<selIndex.length; i++)
		obj[selIndex[i]].selected = true;
}

function optionDelete(isAll){
	if(Selected_Data==null)
		return;
	if(isAll){
		Selected_Data.Clear();
	}else{
		var obj = document.getElementsByName("F_SelectedList")[0].options;
		for(var i=obj.length-1; i>=0; i--){
			if(obj[i].selected)
				Selected_Data.Delete(i);
		}
	}
	refreshSelectedList();
	parent.treeFrame.onSelectedDataDelete();
}

</script>
</head>
<body topmargin=10>

<table border=0 cellspacing=0 cellpadding=0 width=100%>
	<tr>
		<td>
			<table border=1 cellspacing=2 cellpadding=0 bordercolor=#eaeaea width=100%>
				<tr valign=middle height=25 align=center>
					<td><script>writeMessage("selList");</script></td>
				</tr>
				<tr valign=middle align=center>
					<td style="padding:0px">
						<select name="F_SelectedList" multiple class="namelist" style="width:100%; height:300px;"
							ondblclick="optionDelete();">
						</select>
					</td>
				</tr>
			</table>
			<div style="text-align:center">
				<br><br>
				<input id="btnOk" type=button class="btndialog" style="width:70px;"
					onclick="parent.treeFrame.Com_DialogReturnValue();">&nbsp;
				<input id="btnCancel" type=button class="btndialog" style="width:50px"
					onclick="parent.Com_DialogReturn();">
			</div>
		<td width="80px" align="center">
			<input id="btnDelete" type=button class="btndialog" style="width:70px" onclick="optionDelete();">
			<br><br>
			<input id="btnDeleteAll" type=button class="btndialog" style="width:70px" onclick="optionDelete(true);">
			<br><br>
			<input id="btnMoveUp" type=button class="btndialog" style="width:33px" onclick="optionMove(-1);">
			<input id="btnMoveDown" type=button class="btndialog" style="width:42px" onclick="optionMove(1);">
		</td>
	</tr>
</table>
<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>
</body>
</html>
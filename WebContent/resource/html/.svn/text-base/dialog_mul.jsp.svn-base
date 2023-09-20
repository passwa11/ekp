<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<style>
.selectedListUl{
	width:96%; 
	height:216px;
	list-style: none;
	padding-left:0;
    text-align: left;
    cursor: text;
    border: 1px solid;
    margin-top: 10px;
    overflow: auto;
}
.selectedListUl li{
	line-height: 18px;
    cursor: pointer;
    padding-left:5px;
}
.selectedListUl li.selected{
	background-color: #1E90FF;
}
.li_placeholder{
	height: 18px;
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
function encodeHTML(str){
	return str.replace(/&/g,"&amp;")
			.replace(/</g,"&lt;")
			.replace(/>/g,"&gt;")
			.replace(/\"/g,"&quot;")
			.replace(/¹/g, "&sup1;")
			.replace(/²/g, "&sup2;")
			.replace(/³/g, "&sup3;");
};
var dialogObject = parent.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
</script>
<script type="text/javascript" src="../js/common.js"></script>
<script>
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
Com_IncludeFile("jquery.js|data.js");
Com_IncludeFile('jquery.ui.js', 'js/jquery-ui/');
//左侧分类对应数据
var cateData;
//待选框实时数据
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
		//onload-初次加载
		if(data != null){
			cateData = data.Format("id:name:info");
		}
	}
	if(data!=null){
		optData = data.Format("id:name:info");
	}
	if(!(optData instanceof KMSSData))
		optData = new KMSSData(optData);
	optData.PutToSelect("F_OptionList", "id", "name");
}
function setSelData(data,callback){
	if(data!=null)
		selData = data.Format("id:name:info");
	if(!(selData instanceof KMSSData))
		selData = new KMSSData(selData);
	selData.PutToSelect("F_SelectedList", "id", "name");
	$(".selectedListUl").html('');
	var _mData = selData.GetHashMapArray();
	setTimeout(function(){
		for(var i =0;i<_mData.length;i++){
			var option = _mData[i];
			$(".selectedListUl").append("<li data-id='"+option.id+"' onclick='_liclick(this);' ondblclick='_lidblclick(this);'>"+encodeHTML(option.name)+"</li>")
		}
		if(typeof (callback) != 'undefined' && typeof (callback) == 'function'){
			callback.call();
		}
	},0)
}
function _liclick(dom){
	$(".selectedListUl li").removeClass("selected");
	$(dom).addClass("selected");
	var _id = $(dom).data("id");
	$("[name='F_SelectedList'] option").prop("selected",false);
	$("[name='F_SelectedList'] option[value='"+_id+"']").prop("selected","true");
	$("[name='F_SelectedList']").trigger($.Event("change"));
}
function _lidblclick(dom){
	var _id = $(dom).data("id");
	$("[name='F_SelectedList'] option[value='"+_id+"']").prop("selected","true");
	optionDelete();
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
	setSelData(null,function callback(){
		for(i=0; i<selIndex.length; i++){
			obj[selIndex[i]].selected = true;
			$(".selectedListUl li:eq("+selIndex[i]+")").addClass("selected");
		}
	});
}
function showDescription(data, index){
	var hashMapArr = data.GetHashMapArray();
	if(hashMapArr[index]!=null)
		document.getElementById("Span_MoreInfo").innerText = hashMapArr[index].info;
}
function searchUseXML(){
	var obj = document.getElementsByName("F_Keyword")[0];
	if(obj.value==""){
		alert(Com_Parameter.DialogLang.requiredKeyword);
		obj.focus();
		return;
	}
	if(dialogObject.searchBeanURL!=null){
		var beanURL = dialogObject.searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(obj.value));
		if(treeParamVal!=""){
			beanURL += treeParamVal;
		}
		setOptData(new KMSSData().AddXMLData(beanURL), true);
	}else{
		var newData = new KMSSData();
		var dataArr = dialogObject.optionData.data || [];
		//父层数据项为空，则筛选本层数据项
		if(!dataArr || dataArr.length == 0){
			dataArr = cateData.data || [];
		}
		for(var i = 0;i<dataArr.length;i++){
			if(dataArr[i].name.indexOf(obj.value)>-1){
				newData.data.push(dataArr[i]);
			}
		}
		setOptData(newData, true);
	}
}
window.onload = function(){
	document.getElementById("Span_Search").style.display = "none";
	document.getElementById("TB_Search").style.display = "";
	if(dialogObject.winRemark!=null){
		document.getElementById("Span_Remark").innerHTML=dialogObject.winRemark;
	}
	setOptData(dialogObject.optionData);
	setSelData(dialogObject.valueData);
	writeButtonMessage("btnSearch;btnClear;btnAdd;btnDelete;btnAddAll;btnDeleteAll;btnMoveUp;btnMoveDown;btnOk;btnCancel");
	$(".selectedListUl").sortable({
		items : "li",
		placeholder : 'li_placeholder',
		update:function(event){
			var getData = function(id){
				for(var i = 0;i<selData.data.length;i++){
					if(selData.data[i].id==id){
						return selData.data[i];
					}
				}
			}
			var newData = [];
			$(".selectedListUl li").map(function(index,item){
				newData[index] = getData($(item).data("id"));
			})
			selData.data = newData;
			setSelData();
		},
		start : function(event){
			_liclick(event.toElement);
		}
	})
}
</script>
</head>
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
			<select name="F_SelectedList" multiple class="namelist" style="width:100%; height:216px;display: none;"
				ondblclick="optionDelete();"
				onclick="showDescription(selData, selectedIndex);"
				onchange="showDescription(selData, selectedIndex);">
			</select>
			<ul class="selectedListUl"></ul>
		</td>
	</tr>
	<tr valign=middle height=35>
		<td colspan=3 align=center>
			<input id="btnOk" type=button class="btndialog" style="width:70px"
				onclick="parent.Com_DialogReturn(selData.GetHashMapArray());">&nbsp;
			<input id="btnCancel" type=button class="btndialog" style="width:70px"
				onclick="parent.Com_DialogReturn();">
		</td>
	</tr>
	<tr valign=top height=75>
		<td colspan=3 style="padding:3px">
			<script>writeMessage("description");</script><span id="Span_MoreInfo"></span>
			<br/><span id="Span_Remark"></span>
		</td>
	</tr>
</table>
<%@ include file="/resource/jsp/watermarkPcDialog.jsp" %>
</body>
</html>
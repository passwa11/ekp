<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
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
		$(".lui_widget_btn_txt").eq(i).html(Com_Parameter.DialogLang[keyArr[i]]);
	}
}
var dialogObject = parent.dialogObject;
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
var Data_XMLCatche = dialogObject.XMLCatche;
</script>
<script type="text/javascript" src="../js/common.js"></script>
<script type="text/javascript">
Com_IncludeFile("dialog.js", "style/"+Com_Parameter.Style+"/dialog/");
window.onload = function(){
	if(dialogObject.notNull){
		document.getElementById("btnSelectNone").style.display = "none";
	}
	if(dialogObject.valueData!=null){
		var data = dialogObject.valueData.GetHashMapArray();
		if(data!=null && data.length>0 && data[0]["name"]!=null && data[0]["name"]!=""){
			var s = Com_HtmlEscape(Com_Parameter.DialogLang.currentValue+data[0]["name"]);
			if(data.length>1)
				s += ";..."
			document.getElementById("Span_CurValue").innerHTML = s;
			document.getElementById("Span_CurValue").setAttribute("title",s);
		}
	}
	/* writeButtonMessage("btnOk;btnCancel;btnSelectNone"); */
}
</script>

<center>
	<span id="Span_CurValue" style=" display: block;height: 29px; text-overflow: break-word;overflow: hidden; white-space: nowrap; text-overflow: ellipsis;padding: 0 5px 0 8px;"></span>
	<ui:button text="${ lfn:message('button.ok') }" onclick="parent.treeFrame.Com_DialogReturnValue();" style="width:70px"></ui:button>
    <ui:button text="${ lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="parent.Com_DialogReturn();" style="width:70px"></ui:button>
    <ui:button text="${ lfn:message('dialog.selectNone') }" styleClass="lui_toolbar_btn_gray" onclick="parent.treeFrame.Com_DialogReturnEmpty();" style="width:140px"></ui:button>
</center>
	</template:replace>
</template:include>
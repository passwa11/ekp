<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=6"/>
</head>
<script>
var dialogRtnValue = null;
var dialogObject=null;
var parentCom_Parameter = null;
if(window.showModalDialog){
	dialogObject = window.parent.dialogArguments;
	parentCom_Parameter = dialogObject.parameters.Com_Parameter;
}else{
	dialogObject = parent.opener.Com_Parameter.Dialog;
	parentCom_Parameter = dialogObject.parameters.Com_Parameter;
}
var Com_Parameter = {
	ContextPath:parentCom_Parameter.ContextPath,
	ResPath:parentCom_Parameter.ResPath,
	JsFileList:new Array
};
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
Com_Parameter.Lang = dialogObject.Lang;
//var Data_XMLCatche = dialogObject.XMLCatche;
var Data_XMLCatche = new Object();
</script>
<script type="text/javascript" src="<c:url value="/resource/js/common.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resource/js/data.js"/>"></script>
<script>
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
	Com_Parameter.StylePath = parentCom_Parameter.StylePath.replace(parentCom_Parameter.Style+"/","")+style+"/";
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

Com_AddEventListener(parent, "beforeunload", beforeClose);
function Com_DialogReturn(value){
	dialogRtnValue = value;
	parent.close();
}
function beforeClose(){
	dialogObject.rtnData = dialogRtnValue;
	dialogObject.AfterShow();
}
if(dialogObject.winTitle!=null)
	document.title = dialogObject.winTitle;
function getHtmlContent(){
	var dialogType = dialogObject.mulSelect?"mul":"sgl";
	return '<FRAMESET cols="*" framespacing=1 bordercolor=#003048 frameborder=1>' +
	'<FRAME name=optFrame frameborder=0 src="'+Com_Parameter.ResPath+'html/dialog_'+dialogType+'.jsp">' +
	'</FRAMESET>';
}
document.write(getHtmlContent());
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=6"/>
</head>
<script type="text/javascript">
var Com_Parameter = {
	ContextPath:"../../",
	ResPath:"../",
	JsFileList:new Array
};
</script>
<script type="text/javascript" src="../js/common.js"></script>
<script>
var dialogRtnValue = null;
var dialogObject=null;
var flag = (navigator.userAgent.indexOf('MSIE') > -1) || navigator.userAgent.indexOf('Trident') > -1
if(window.showModalDialog && flag){ //判断是window系统且是IE浏览器
	dialogObject = window.dialogArguments;
}else{
	dialogObject = opener.Com_Parameter.Dialog;
}
Com_Parameter.XMLDebug = dialogObject.XMLDebug;
Com_Parameter.Lang = dialogObject.Lang;
//var Data_XMLCatche = dialogObject.XMLCatche;
var Data_XMLCatche = new Object();
Com_IncludeFile("data.js");
</script>
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
	Com_Parameter.StylePath = "../style/"+style+"/";
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

//Com_AddEventListener(window, "beforeunload", beforeClose);
function Com_DialogReturn(value){
	dialogRtnValue = value;
	dialogObject.rtnData = dialogRtnValue;
	dialogObject.AfterShow();
	close();
}

function Com_DialogCleanValue(value){
	dialogRtnValue = value;
	dialogObject.rtnData = dialogRtnValue;
	dialogObject.AfterShow();
}


function beforeClose(){
	dialogObject.rtnData = dialogRtnValue;
	dialogObject.AfterShow();
}
if(dialogObject.winTitle!=null)
	document.title = dialogObject.winTitle;
function getHtmlContent(){
	var dialogType = dialogObject.mulSelect?"mul":"sgl";
	if(dialogObject.tree==null){
		return '<FRAMESET cols="*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=optFrame frameborder=0 src="dialog_'+dialogType+'.jsp">' +
			'</FRAMESET>';
	}
	if(dialogObject.showOption){
		return '<FRAMESET cols="180,*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=treeFrame frameborder=0 src="dialog_tree.jsp">' +
			'<FRAME name=optFrame frameborder=0 src="dialog_'+dialogType+'.jsp">' +
			'</FRAMESET>';
	}
	if(dialogObject.mulSelect){
		return '<FRAMESET cols="*,*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=treeFrame frameborder=0 src="dialog_tree_mul.jsp"> ' +
			'<FRAME noresize scrolling=no name=optFrame frameborder=0 src="dialog_confirm_mul.jsp">'+
			'</FRAMESET>';
	}
	return '<FRAMESET rows="*,75" framespacing=1 bordercolor=#003048 frameborder=1>' +
		'<FRAME name=treeFrame frameborder=0 src="dialog_tree_sgl.jsp"> ' +
		'<FRAME noresize scrolling=no name=optFrame frameborder=0 src="dialog_confirm_sgl.jsp">'+
		'</FRAMESET>';
}
document.write(getHtmlContent());
</script>
</html>
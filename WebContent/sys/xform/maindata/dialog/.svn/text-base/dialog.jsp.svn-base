<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
</head>
<script type="text/javascript">
Com_IncludeFile("data.js");
Com_IncludeFile("treeview.js");
</script>
<script type="text/javascript">
var dialogRtnValue = null;
var dialogObject=null;
if(window.showModalDialog){
	dialogObject = window.dialogArguments;
}else{
	dialogObject = window.opener.Com_Parameter.Dialog;
}
//dialogObject = window.opener.Com_Parameter.Dialog.data;

dialogObject.url = "${KMSS_Parameter_ContextPath}${JsParam.url}";
dialogObject.showOption = true;
dialogObject.valueData = dialogObject.data._keyName;
dialogObject.winTitle = "选择";
dialogObject.mulSelect = false;
//alert("${JsParam.springBean}&serviceBean="+ dialogObject._source+",url=${JsParam.url}");
dialogObject.tree = new TreeView("LKSTree",'<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_attrFun"/>');
var showSearchBtnDataType = ["MAINDATAINSYSTEM","MAINDATACUSTOM","JDBCXFORM"];
for (var i = 0; i < showSearchBtnDataType.length; i++){
	if (showSearchBtnDataType[i] === dialogObject.data._source){
		dialogObject.searchBeanURL = "${JsParam.springBean}&serviceBean=${JsParam.infoBean}&selectId=!{value}&keyword=!{keyword}&type=search&range=${JsParam.range}";
		break;
	}
}

var node = dialogObject.tree.treeRoot;
node.AppendBeanData("${JsParam.springBean}&serviceBean=${JsParam.infoBean}&selectId=!{value}&type=cate",
		"${JsParam.springBean}&serviceBean=${JsParam.infoBean}&selectId=!{value}&type=func&range=${JsParam.range}"
);
node.parameter="${JsParam.springBean}&serviceBean=${JsParam.infoBean}&selectId=!{value}&type=func&range=${JsParam.range}";
</script>
<script type="text/javascript">
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
/* 	var langArr = Data_GetResourceString("button.ok;button.cancel;dialog.selectNone;message.keyword;dialog.requiredKeyword;"+
		"button.search;button.clear;dialog.optList;dialog.selList;dialog.add;"+
		"dialog.delete;dialog.addAll;dialog.deleteAll;dialog.moveUp;dialog.moveDown;"+
		"dialog.description;dialog.currentValue;dialog.requiredSelect;sys-xform-maindata:sysXformJdbc.control.updateFunc;"+
		"sys-xform-maindata:sysXformJdbc.control.requiredFuncSelect;sys-xform-maindata:sysXformJdbc.control.requiredCateSelect;"+
		"sys-xform-maindata:sysXformJdbc.control.addFunc"); */
	var btnOk = "${lfn:message('button.ok')}";
	var btnCancel = "${lfn:message('button.cancel')}";
	var btnSelectNone = "${lfn:message('dialog.selectNone')}";
	var keyword = "${lfn:message('message.keyword')}";
	var requiredKeyword = "${lfn:message('dialog.requiredKeyword')}";
	var btnSearch = "${lfn:message('button.search')}";
	var btnClear = "${lfn:message('button.clear')}";
	var optList = "${lfn:message('dialog.optList')}";
	var selList = "${lfn:message('dialog.selList')}";
	var btnAdd = "${lfn:message('dialog.add')}";
	var btnDelete = "${lfn:message('dialog.delete')}";
	var btnAddAll = "${lfn:message('dialog.addAll')}";
	var btnDeleteAll = "${lfn:message('dialog.deleteAll')}";
	var btnMoveUp = "${lfn:message('dialog.moveUp')}";
	var btnMoveDown = "${lfn:message('dialog.moveDown')}";
	var description = "${lfn:message('dialog.description')}";
	var currentValue = "${lfn:message('dialog.currentValue')}";
	var requiredSelect = '${lfn:message("dialog.requiredSelect")}';
	var updateFunc = "${lfn:message('sys-xform-maindata:sysXformJdbc.control.updateFunc')}";
	var requiredFuncSelect = "${lfn:message('sys-xform-maindata:sysXformJdbc.control.requiredFuncSelect')}";
	var requiredCateSelect = "${lfn:message('sys-xform-maindata:sysXformJdbc.control.requiredCateSelect')}";
	var addFunc = "${lfn:message('sys-xform-maindata:sysXformJdbc.control.addFunc')}";
	Com_Parameter.DialogLang.btnOk = btnOk==null?"OK":btnOk;
	Com_Parameter.DialogLang.btnCancel = btnCancel==null?"Cancel":btnCancel;
	Com_Parameter.DialogLang.btnSelectNone = btnSelectNone==null?"SelectNone":btnSelectNone;
	Com_Parameter.DialogLang.keyword = keyword==null?"Keyword":keyword;
	Com_Parameter.DialogLang.requiredKeyword = requiredKeyword==null?"Keyword could not be empty!":requiredKeyword;

	Com_Parameter.DialogLang.btnSearch = btnSearch==null?"Search":btnSearch;
	Com_Parameter.DialogLang.btnClear = btnClear==null?"Clear":btnClear;
	Com_Parameter.DialogLang.optList = optList==null?"Options":optList;
	Com_Parameter.DialogLang.selList = selList==null?"Selected":selList;
	Com_Parameter.DialogLang.btnAdd = btnAdd==null?"Add":btnAdd;

	Com_Parameter.DialogLang.btnDelete = btnDelete==null?"Remove":btnDelete;
	Com_Parameter.DialogLang.btnAddAll = btnAddAll==null?"Add All":btnAddAll;
	Com_Parameter.DialogLang.btnDeleteAll = btnDeleteAll==null?"Remove All":btnDeleteAll;
	Com_Parameter.DialogLang.btnMoveUp = btnMoveUp==null?"Up":btnMoveUp;
	Com_Parameter.DialogLang.btnMoveDown = btnMoveDown==null?"Down":btnMoveDown;
	
	Com_Parameter.DialogLang.description = description==null?"Description: ":description;
	Com_Parameter.DialogLang.currentValue = currentValue==null?"":currentValue;
	Com_Parameter.DialogLang.requiredSelect = requiredSelect==null?"Please select a value!":requiredSelect;
	Com_Parameter.DialogLang.updateFunc = updateFunc==null?"update":updateFunc;
	Com_Parameter.DialogLang.requiredFuncSelect = requiredFuncSelect==null?"Please select a value!":requiredFuncSelect;
	Com_Parameter.DialogLang.requiredCateSelect = requiredCateSelect==null?"Please select a category!":requiredCateSelect;
	Com_Parameter.DialogLang.addFunc = addFunc==null?"add":addFunc;
}

//Com_AddEventListener(window, "beforeunload", beforeClose);
function Com_DialogReturn(value){
	if (value != null && value.length != 0) {
		var returnJson = {"_key" : dialogObject.data._source +"_"+ value[0] ,"_keyName" : value[1]};
		dialogRtnValue = returnJson;
	}
	beforeClose();
	close();
}
function beforeClose(){
	//window.returnValue = dialogRtnValue;
	var callback=null;
	if(window.showModalDialog){
		callback = dialogObject.AfterShow;
	}else{
		callback = window.opener.Com_Parameter.Dialog.AfterShow;
	}
	if (callback) {
		callback(dialogRtnValue);
	}
}
if(dialogObject.winTitle!=null)
	document.title = dialogObject.winTitle;
function getHtmlContent(){
	var dialogType = dialogObject.mulSelect?"mul":"sgl";
	/*if(dialogObject.tree==null){
		return '<FRAMESET cols="*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=optFrame frameborder=0 src="dialog_'+dialogType+'.html">' +
			'</FRAMESET>';
	}*/
	if(dialogObject.showOption){
		return '<FRAMESET cols="180,*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=treeFrame frameborder=0 src="dialog_tree.jsp">' +
			'<FRAME name=optFrame frameborder=0 src="dialog_'+dialogType+'.jsp">' +
			'</FRAMESET>';
	}
	/*if(dialogObject.mulSelect){
		return '<FRAMESET cols="*,*" framespacing=1 bordercolor=#003048 frameborder=1>' +
			'<FRAME name=treeFrame frameborder=0 src="dialog_tree_mul.html"> ' +
			'<FRAME noresize scrolling=no name=optFrame frameborder=0 src="dialog_confirm_mul.html">'+
			'</FRAMESET>';
	}
	return '<FRAMESET rows="*,75" framespacing=1 bordercolor=#003048 frameborder=1>' +
		'<FRAME name=treeFrame frameborder=0 src="dialog_tree_sgl.html"> ' +
		'<FRAME noresize scrolling=no name=optFrame frameborder=0 src="dialog_confirm_sgl.html">'+
		'</FRAMESET>';*/
}
document.write(getHtmlContent());
</script>
</html>
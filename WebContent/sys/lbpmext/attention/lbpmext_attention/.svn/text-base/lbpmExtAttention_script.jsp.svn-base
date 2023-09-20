<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script language="JavaScript">
var _validation = $KMSSValidation(document.forms['lbpmExtAttentionForm']);
Com_IncludeFile("dialog.js|formula.js");
function afterClickedScopeDialogAction(obj){
	if(obj == null){
		return null;
	}
	var fdScopeFormAttentionCateIdsObj = document.getElementsByName("fdScopeFormAttentionCateIds")[0];
	var fdScopeFormAttentionCateNamesObj = document.getElementsByName("fdScopeFormAttentionCateNames")[0];
	var fdScopeFormModelNamesObj = document.getElementsByName("fdScopeFormModelNames")[0];
	var fdScopeFormModuleNamesObj = document.getElementsByName("fdScopeFormModuleNames")[0];
	var fdScopeFormTemplateIdsObj = document.getElementsByName("fdScopeFormTemplateIds")[0];
	var fdScopeFormTemplateNamesObj = document.getElementsByName("fdScopeFormTemplateNames")[0];
	var fdScopeFormAttentionCateShowtextsObj = document.getElementsByName("fdScopeFormAttentionCateShowtexts")[0];
	var scopeTempObj = document.getElementsByName("scopeTempValues")[0];
	var fdScopeFormAttentionCateIds = "";
	var fdScopeFormAttentionCateNames = "";
	var fdScopeFormModelNames = "";
	var fdScopeFormModuleNames = "";
	var fdScopeFormTemplateIds = "";
	var fdScopeFormTemplateNames = "";
	var fdScopeFormAttentionCateShowtexts = "";
	var scopeTempValues="";
	for(var o in obj){
		var urlValue = obj[o].id;
		var showText = GetUrlParameter_Unescape(urlValue, "showText");
		var categoryId = GetUrlParameter_Unescape(urlValue, "categoryId");
		var categoryName = GetUrlParameter_Unescape(urlValue, "categoryName");
		var modelName = GetUrlParameter_Unescape(urlValue, "modelName");
		var moduleName = GetUrlParameter_Unescape(urlValue, "moduleName");
		var templateId = GetUrlParameter_Unescape(urlValue, "templateId");
		var templateName = GetUrlParameter_Unescape(urlValue, "templateName");
		fdScopeFormAttentionCateIds += (categoryId == null?" ":categoryId) + ";";
		fdScopeFormAttentionCateNames += (categoryName == null?" ":categoryName) + ";";
		fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
		fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
		fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
		fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
		fdScopeFormAttentionCateShowtexts += (showText == null?" ":showText) + ";";
		scopeTempValues+=urlValue+";";
	}
	fdScopeFormAttentionCateIdsObj.value = fdScopeFormAttentionCateIds;
	fdScopeFormAttentionCateNamesObj.value = fdScopeFormAttentionCateNames;
	fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
	fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
	fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
	fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
	fdScopeFormAttentionCateShowtextsObj.value = fdScopeFormAttentionCateShowtexts;
	scopeTempObj.value=scopeTempValues;
}

/**
 * 获取URL中的参数（使用unescape对返回参数值解码）
 */
function GetUrlParameter_Unescape(url, param){
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null) {
		return null;
	} else {
		return unescape(arr[1]);
	}
}

function importAttentionCateDialog(){
	editAttentionDoc();
}

function validateSubmitForm(method){
	Com_Submit(document.lbpmExtAttentionForm, method);
}

function editAttentionDoc() {
	var dialog = new KMSSDialog();
	dialog.SetAfterShow(function(rtnRelaData) {
		var rtnVal = null;
		if(rtnRelaData == null){
			rtnVal = $.parseJSON(window._rela_dialog.rtnRelaData);
		}else{
			rtnVal = rtnRelaData;
		}
		if(typeof(rtnVal) == "undefined" || rtnVal==null || $.isEmptyObject(rtnVal)) return;
		// 更新数据
		setTimeout(function(){
			afterClickedScopeDialogAction(rtnVal);
		},100);
	});
	var url='<c:url value="/sys/lbpmext/attention/lbpmext_attention/configuration_type/lbpmExtAttentionConfig.jsp"/>';
	dialog.URL = url;
	window._rela_dialog = dialog;
	var scopeTempValues = document.getElementsByName("scopeTempValues")[0].value;
	window._rela_dialog.attentionEntry=scopeTempValues;
	
	dialog.Show(800, 670); 
}
_validation.addValidator('comparePerson', "${lfn:message('sys-lbpmext-attention:message.focuson.yourself')}", function(v, e, o) {
	if(!v){
		return;
	}
	var currentUserId = '${currentUserId}';
	var currentValue = $('[name=fdPersonId]').val();
	if (currentUserId==currentValue) {
		return false;
	}
	return true;
});
_validation.addValidator('checkUniquePerson', "${lfn:message('sys-lbpmext-attention:message.repeat.attention')}", function(v, e, o) {
	if(!v){
		return;
	}
	var currentValue = $('[name=fdPersonId]').val();
	var url = Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=lbpmExtAttentionService&fdPersonId=" + currentValue+"&fdId=${JsParam.fdId}";
	return _CheckUnique(url);
});
// 检查是否唯一
function _CheckUnique(url) {
	var xmlHttpRequest;
	if (window.XMLHttpRequest) { // Non-IE browsers
		xmlHttpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		try {
			xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (othermicrosoft) {
			try {
				xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (failed) {
				xmlHttpRequest = false;
			}
		}
	}
	if (xmlHttpRequest) {
		xmlHttpRequest.open("GET", url, false);
		xmlHttpRequest.send();
		var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
		if (result != "") {
			return false;
		}
	}
	return true;
}
</script>

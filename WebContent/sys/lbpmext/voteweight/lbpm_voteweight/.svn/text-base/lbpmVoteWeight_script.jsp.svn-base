<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
Com_IncludeFile("dialog.js");

function afterClickedScopeDialogAction(obj){
	if(obj == null){
		return null;
	}
	var fdScopeFormVoteWeightCateIdsObj = document.getElementsByName("fdScopeFormVoteWeightCateIds")[0];
	var fdScopeFormVoteWeightCateNamesObj = document.getElementsByName("fdScopeFormVoteWeightCateNames")[0];
	var fdScopeFormModelNamesObj = document.getElementsByName("fdScopeFormModelNames")[0];
	var fdScopeFormModuleNamesObj = document.getElementsByName("fdScopeFormModuleNames")[0];
	var fdScopeFormTemplateIdsObj = document.getElementsByName("fdScopeFormTemplateIds")[0];
	var fdScopeFormTemplateNamesObj = document.getElementsByName("fdScopeFormTemplateNames")[0];
	var fdScopeFormVoteWeightCateShowtextsObj = document.getElementsByName("fdScopeFormVoteWeightCateShowtexts")[0];
	var fdScopeFormVoteWeightCateIds = "";
	var fdScopeFormVoteWeightCateNames = "";
	var fdScopeFormModelNames = "";
	var fdScopeFormModuleNames = "";
	var fdScopeFormTemplateIds = "";
	var fdScopeFormTemplateNames = "";
	var fdScopeFormVoteWeightCateShowtexts = "";
	for(var i = 0; i < obj.data.length; i++){
		var urlValue = obj.data[i].id;
		var showText = GetUrlParameter_Unescape(urlValue, "showText");
		var categoryId = GetUrlParameter_Unescape(urlValue, "categoryId");
		var categoryName = GetUrlParameter_Unescape(urlValue, "categoryName");
		var modelName = GetUrlParameter_Unescape(urlValue, "modelName");
		var moduleName = GetUrlParameter_Unescape(urlValue, "moduleName");
		var templateId = GetUrlParameter_Unescape(urlValue, "templateId");
		var templateName = GetUrlParameter_Unescape(urlValue, "templateName");
		fdScopeFormVoteWeightCateIds += (categoryId == null?" ":categoryId) + ";";
		fdScopeFormVoteWeightCateNames += (categoryName == null?" ":categoryName) + ";";
		fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
		fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
		fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
		fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
		fdScopeFormVoteWeightCateShowtexts += (showText == null?" ":showText) + ";";
	}
	fdScopeFormVoteWeightCateIdsObj.value = fdScopeFormVoteWeightCateIds;
	fdScopeFormVoteWeightCateNamesObj.value = fdScopeFormVoteWeightCateNames;
	fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
	fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
	fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
	fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
	fdScopeFormVoteWeightCateShowtextsObj.value = fdScopeFormVoteWeightCateShowtexts;
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

function importVoteWeightCateDialog(){
	var voterId = document.getElementsByName("fdVoterId")[0];
	if (voterId.value=="") {
		alert('<bean:message key="lbpmVoteWeight.fdVoter.isNull" bundle="sys-lbpmext-voteweight"/>');
		return false;
	}
	var voteWeightId = document.getElementsByName("fdId")[0];
	
	Dialog_Tree(true, 'scopeTempValues', 
			'fdScopeFormVoteWeightCateShowtexts', 
				 ';', 
				 'lbpmVoteWeightScopeTreeService&top=true&voterId=' + voterId.value + '&voteWeightId=' +voteWeightId.value, 
				 '<bean:message key="lbpmVoteWeight.lbpmVoteWeightScope" bundle="sys-lbpmext-voteweight"/>',
				  null, afterClickedScopeDialogAction,
				   null, null, null,
				   '<bean:message key="lbpmVoteWeight.lbpmVoteWeightScope" bundle="sys-lbpmext-voteweight"/>');
}

function validateSubmitForm(method){
	var fdVoteWeight = document.getElementsByName("fdVoteWeight")[0];
	if (fdVoteWeight.value == 1) {
		alert('<bean:message key="lbpmVoteWeight.fdVoteWeight.isDefault" bundle="sys-lbpmext-voteweight"/>');
		return false;
	}
	Com_Submit(document.lbpmVoteWeightForm, method);
}

</script>

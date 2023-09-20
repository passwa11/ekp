<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script language="JavaScript">
Com_IncludeFile("calendar.js|dialog.js");
Com_AddEventListener(window, "load", authorize_InitialContextParams);
function authorize_InitialContextParams(){
	var currentUserRoleIds = document.getElementsByName("currentUserRoleIds")[0].value;
	var currentUserRoleNames = document.getElementsByName("currentUserRoleNames")[0].value;
	var fdSysAuthorizeItemIds = document.getElementsByName("fdLbpmAuthorizeItemIds")[0].value;
	//var currentUserRoleIds = document.getElementById("currentUserRoleIds").value;
	//var currentUserRoleNames = document.getElementById("currentUserRoleNames").value;
	//var fdSysAuthorizeItemIds = document.getElementById("fdLbpmAuthorizeItemIds").value;
	var fdSysAuthorizeItemDiv = document.getElementById("fdLbpmAuthorizeItemDiv");
	var itemIdArrays = currentUserRoleIds.split(";");
	var itemNameArrays = currentUserRoleNames.split(";");
	var html = "";
	for(var i = 0; i < itemIdArrays.length; i++){
		html += "<label><input name='fdLbpmAuthorizeItem' type='checkbox' value='" + itemIdArrays[i] + ":" + itemNameArrays[i] + "' onclick='authorizeItemCheckBoxClickEvent();'";
		if(fdSysAuthorizeItemIds.indexOf(itemIdArrays[i]) != -1){
			html += " checked "; 
		}
		html += ">" + itemNameArrays[i] + "</label>&nbsp;&nbsp;";
	}
	fdSysAuthorizeItemDiv.innerHTML = html; 
	var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
	if(fdAuthorizeType == '1'){
		var processTypeRow = document.getElementById("processTypeRow");
		processTypeRow.style.display = "none";
	}
}

function authorizeTypeChanged(radioObj){
	var processTypeRow = document.getElementById("processTypeRow");
	if(radioObj.value == 0 || radioObj.value==2){
		processTypeRow.style.display = "";
	}else{
		processTypeRow.style.display = "none";
	}
	
	if(radioObj.value==2){
		$("#expireRecoverLabel").show();
	}
	else{
		$("#expireRecoverLabel").hide();
	}
}

function authorizeItemCheckBoxClickEvent(){
	var fdSysAuthorizeItems = document.getElementsByName("fdLbpmAuthorizeItem");
	var fdSysAuthorizeItemIds = document.getElementsByName("fdLbpmAuthorizeItemIds")[0];
	var fdSysAuthorizeItemNames = document.getElementsByName("fdLbpmAuthorizeItemNames")[0];
	var itemIds = "", itemNames = "";
	for(var i = 0; i < fdSysAuthorizeItems.length; i++){
		if(fdSysAuthorizeItems[i].checked){
			var itemValue = fdSysAuthorizeItems[i].value;
			var itemArray = itemValue.split(":");
			itemIds += itemArray[0] + ";";
			itemNames += itemArray[1] + ";";
		}
	}
	if(itemIds != ""){
		itemIds = itemIds.substring(0, itemIds.length - 1);
		itemNames = itemNames.substring(0, itemNames.length - 1);
	}
	
	fdSysAuthorizeItemIds.value = itemIds;
	fdSysAuthorizeItemNames.value = itemNames;
}

function afterClickedScopeDialogAction(obj){
	if(obj == null){
		return null;
	}
	var fdScopeFormAuthorizeCateIdsObj = document.getElementsByName("fdScopeFormAuthorizeCateIds")[0];
	var fdScopeFormAuthorizeCateNamesObj = document.getElementsByName("fdScopeFormAuthorizeCateNames")[0];
	var fdScopeFormModelNamesObj = document.getElementsByName("fdScopeFormModelNames")[0];
	var fdScopeFormModuleNamesObj = document.getElementsByName("fdScopeFormModuleNames")[0];
	var fdScopeFormTemplateIdsObj = document.getElementsByName("fdScopeFormTemplateIds")[0];
	var fdScopeFormTemplateNamesObj = document.getElementsByName("fdScopeFormTemplateNames")[0];
	var fdScopeFormAuthorizeCateShowtextsObj = document.getElementsByName("fdScopeFormAuthorizeCateShowtexts")[0];
	var fdScopeFormAuthorizeCateIds = "";
	var fdScopeFormAuthorizeCateNames = "";
	var fdScopeFormModelNames = "";
	var fdScopeFormModuleNames = "";
	var fdScopeFormTemplateIds = "";
	var fdScopeFormTemplateNames = "";
	var fdScopeFormAuthorizeCateShowtexts = "";
	for(var i = 0; i < obj.data.length; i++){
		var urlValue = obj.data[i].id;
		var showText = GetUrlParameter_Unescape(urlValue, "showText");
		var categoryId = GetUrlParameter_Unescape(urlValue, "categoryId");
		var categoryName = GetUrlParameter_Unescape(urlValue, "categoryName");
		var modelName = GetUrlParameter_Unescape(urlValue, "modelName");
		var moduleName = GetUrlParameter_Unescape(urlValue, "moduleName");
		var templateId = GetUrlParameter_Unescape(urlValue, "templateId");
		var templateName = GetUrlParameter_Unescape(urlValue, "templateName");
		fdScopeFormAuthorizeCateIds += (categoryId == null?" ":categoryId) + ";";
		fdScopeFormAuthorizeCateNames += (categoryName == null?" ":categoryName) + ";";
		fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
		fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
		fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
		fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
		fdScopeFormAuthorizeCateShowtexts += (showText == null?" ":showText) + ";";
	}
	fdScopeFormAuthorizeCateIdsObj.value = fdScopeFormAuthorizeCateIds;
	fdScopeFormAuthorizeCateNamesObj.value = fdScopeFormAuthorizeCateNames;
	fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
	fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
	fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
	fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
	fdScopeFormAuthorizeCateShowtextsObj.value = fdScopeFormAuthorizeCateShowtexts;
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

function importAuthorizeCateDialog(){
	//window.location.reload();
	//Data_XMLCatche = new Array();
	Dialog_Tree(true, 'scopeTempValues', 
			'fdScopeFormAuthorizeCateShowtexts', 
				 ';', 
				 'lbpmAuthorizeScopeTreeService&top=true', 
				 '<bean:message key="lbpmAuthorize.lbpmAuthorizeScope" bundle="sys-lbpmext-authorize"/>',
				  null, afterClickedScopeDialogAction,
				   null, null, null,
				   '<bean:message key="lbpmAuthorize.lbpmAuthorizeScope" bundle="sys-lbpmext-authorize"/>');
}

function validateSubmitForm(method){
	var processTypeRow = document.getElementById("processTypeRow");
	if(processTypeRow.style.display != "none"){
		var fdAuthorizedPersonId = document.getElementsByName("fdAuthorizedPersonId")[0];
		if(fdAuthorizedPersonId.value == ""){
			alert('<bean:message key="lbpmAuthorize.fdAuthorizedPersonId.isNull" bundle="sys-lbpmext-authorize"/>');
			return ;
		}
		
		var fdStartTime = document.getElementsByName("fdStartTime")[0];
		if(fdStartTime.value == ""){
			alert('<bean:message key="lbpmAuthorize.fdStartTime.isNull" bundle="sys-lbpmext-authorize"/>');
			return ;
		}
		var fdEndTime = document.getElementsByName("fdEndTime")[0];
		if(fdEndTime.value == ""){
			alert('<bean:message key="lbpmAuthorize.fdEndTime.isNull" bundle="sys-lbpmext-authorize"/>');
			return ;
		}
		var today = new Date();
		var todayStr = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate() + " " + today.getHours() + ":" +today.getMinutes();
  		if(WorkFlow_CompareDate(Authorize_formatDate(fdStartTime.value), todayStr) < 0){
  			alert('<bean:message key="lbpmAuthorizeForm.startTimeSmallerThanCurrentTime" bundle="sys-lbpmext-authorize"/>');
  			return;
  		}　　
  		if(WorkFlow_CompareDate(Authorize_formatDate(fdEndTime.value), todayStr) < 0){
  			alert('<bean:message key="lbpmAuthorizeForm.endTimeSmallerThanCurrentTime" bundle="sys-lbpmext-authorize"/>');
  			return;
  		}　　
  		if(WorkFlow_CompareDate(Authorize_formatDate(fdStartTime.value), Authorize_formatDate(fdEndTime.value)) > 0){
  			alert('<bean:message key="lbpmAuthorizeForm.startTimeLargerThanEndTime" bundle="sys-lbpmext-authorize"/>');
  			return;
  		}　　
 	}
	
	var fdSysAuthorizeItemObjs = document.getElementsByName("fdLbpmAuthorizeItem");
	var itemFlag = false;
	for(var i = 0; i < fdSysAuthorizeItemObjs.length; i++){
		if(fdSysAuthorizeItemObjs[i].checked){
			itemFlag = true;
			break;
		}
	}
	if(!itemFlag){
		alert('<bean:message key="lbpmAuthorize.lbpmAuthorizeItem.unSelected" bundle="sys-lbpmext-authorize"/>');
		return;
	}
	
	var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
	// 授权阅读时，提交的时候去掉开始时间与结束时间的校验，提交失败则还原
	if(fdAuthorizeType == '1'){
		var startTimeValidate = $('input[name="fdStartTime"]').attr("validate");
		var endTimeValidate = $('input[name="fdEndTime"]').attr("validate");
		$('input[name="fdStartTime"]').attr("validate","");
		$('input[name="fdEndTime"]').attr("validate","");
		if (!Com_Submit(document.lbpmAuthorizeForm, method)) {
			$('input[name="fdStartTime"]').attr("validate",startTimeValidate);
			$('input[name="fdEndTime"]').attr("validate",endTimeValidate);
		}
	} else {
		Com_Submit(document.lbpmAuthorizeForm, method)
	}
}

//比较两个日期的大小
function WorkFlow_CompareDate(dateOne,dateTwo){ 
	//var oneMonth = dateOne.substring(5,dateOne.lastIndexOf ("-"));
	//var oneDay = dateOne.substring(dateOne.length, dateOne.lastIndexOf ("-")+1);
	//var oneYear = dateOne.substring(0,dateOne.indexOf ("-"));
	//var twoMonth = dateTwo.substring(5,dateTwo.lastIndexOf ("-"));
	//var twoDay = dateTwo.substring(dateTwo.length, dateTwo.lastIndexOf ("-")+1);
	//var twoYear = dateTwo.substring(0,dateTwo.indexOf ("-"));
	//if (Date.parse(oneMonth+"/"+oneDay+"/"+oneYear) > Date.parse(twoMonth+"/"+twoDay+"/"+twoYear)){
	//	return 1;
	//}else if(Date.parse(oneMonth+"/"+oneDay+"/"+oneYear) == Date.parse(twoMonth+"/"+twoDay+"/"+twoYear)){
	//	return 0;
	//}else{
	//	return -1;
	//}
	
	dateOne = dateOne.replace(/-/g,"\/");
	dateTwo = dateTwo.replace(/-/g,"\/");
	if (Date.parse(dateOne) > Date.parse(dateTwo)){
		return 1;
	}else if(Date.parse(dateOne) == Date.parse(dateTwo)){
		return 0;
	}else{
		return -1;
	}

}

function Authorize_formatDate(date){
	var dateFormat = Data_GetResourceString("date.format.datetime");
	var formatList = dateFormat.split(/\/|-| |:/);
	var dateList = date.split(/\/|-| |:/);
	var dateDetile = {};
	for(var i=0;i<formatList.length;i++){
		dateDetile[formatList[i]] = dateList[i];
	}
	return dateDetile["yyyy"] + "-" + dateDetile["MM"] + "-" + dateDetile["dd"] + " " + dateDetile["HH"] + ":" +dateDetile["mm"];
}

function showAuthorizeItems(){
	var fdAuthorizerId = document.getElementsByName("fdAuthorizerId")[0].value;
	if(fdAuthorizerId == ""){
		return;
	}
	$.get(Com_Parameter.ContextPath+'sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=getAuthInfo&authId='+fdAuthorizerId,
			function(data) {loadAuthorizeUserInfoReturnFlowContent(data);}, 'text');
}

function loadAuthorizeUserInfoReturnFlowContent(data){
   	var ids = Com_GetUrlParameter(data, "ids");
   	var names = Com_GetUrlParameter(data, "names");
   	while (names.indexOf("nbsp;") != -1) {
   		names = names.replace("&nbsp;", " ");
	}
		var fdSysAuthorizeItemDiv = document.getElementById("fdLbpmAuthorizeItemDiv");
		var fdLbpmAuthorizeItemIds = document.getElementsByName("fdLbpmAuthorizeItemIds")[0].value;
		
		var itemIdArrays = ids.split(";");
		var itemNameArrays = names.split(";");
		var html = "";
		for(var i = 0; i < itemIdArrays.length; i++){
			html += "<label><input name='fdLbpmAuthorizeItem' type='checkbox' value='" + itemIdArrays[i] + ":" + itemNameArrays[i] + "' onclick='authorizeItemCheckBoxClickEvent();'";
			if(fdLbpmAuthorizeItemIds.indexOf(itemIdArrays[i]) != -1){
				html += " checked ";
			}
			html += ">" + itemNameArrays[i] + "</label>&nbsp;&nbsp;";
		}
		fdLbpmAuthorizeItemDiv.innerHTML = html; 
}

</script>

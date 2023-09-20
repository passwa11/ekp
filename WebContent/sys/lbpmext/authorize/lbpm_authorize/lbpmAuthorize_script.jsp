<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/sys/lbpmext/businessauth/lbpmext_businessauth_util/lbpmextBusinessAuth_util.jsp"%>
<script language="JavaScript">
Com_IncludeFile("calendar.js|dialog.js|doclist.js|formula.js");
Com_IncludeFile("lbpmAuthorize_edit.js","${LUI_ContextPath}/sys/lbpmext/authorize/resource/js/","js",true);
Com_IncludeFile("lbpmAuthorize_common.js","${LUI_ContextPath}/sys/lbpmext/authorize/resource/js/","js",true);
Com_IncludeFile("control.js","${LUI_ContextPath}/sys/lbpmext/authorize/resource/js/","js",true);
Com_IncludeFile("edit.css","${LUI_ContextPath}/sys/lbpmext/authorize/resource/css/","css",true);
Com_AddEventListener(window, "load", authorize_InitialContextParams);
var SettingInfoObj = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
var frezzType = false;
function authorize_InitialContextParams(){
	var currentUserRoleIds = document.getElementsByName("currentUserRoleIds")[0].value;
	var currentUserRoleNames = document.getElementsByName("currentUserRoleNames")[0].value;
	var fdSysAuthorizeItemIds = document.getElementsByName("fdLbpmAuthorizeItemIds")[0].value;
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
		$(".lbpm_authorize_other_row").hide();
		$(".lbpm_authorize_read_row").show();
		_$validation.addElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
		_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"required");
		KMSSValidation_HideWarnHint($("input[name='fdAuthorizedPersonName']")[0]);
		$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").hide();
		$("input[name='fdAuthorizedReaderIds']").parent().next(".txtstrong").show();
	}else{
		$(".lbpm_authorize_other_row").show();
		$(".lbpm_authorize_read_row").hide();
		_$validation.removeElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
	}
	if(fdAuthorizeType==4){
		$(".lbpm_authorize_row").hide();
		$(".lbpm_description_row").hide();
		$(".lbpm_businessautho_row").show();
		_$validation.addElements($("input[name='fdStartTime']")[0],"checktime");
		_$validation.addElements($("input[name='fdEndTime']")[0],"checktime checkendtime");
		_$validation.addElements($("input[name='fdAuthorizedPersonName']")[0],"checkrequired");
		_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"required");
		_$validation.removeElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
		_$validation.addElements($("input[name='fdAuthorizedPostName']")[0],"checkrequired");
		$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").hide();
		$("input[name='fdAuthorizedReaderIds']").parent().next(".txtstrong").hide();
	}else{
		_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"checkrequired");
		_$validation.removeElements($("input[name='fdAuthorizedPostName']")[0],"checkrequired");
	}
	
	//控制描述语
	if("${lbpmAuthorizeForm.method_GET}" == "add"){
		switchAuthorizeTypeDesc(fdAuthorizeType);
	}
}

function authorizeTypeChanged(radioObj){
	if(frezzType) {
		return;
	}
	
	switchAuthorizeTypeDesc(radioObj.value);
	
	var processTypeRow = document.getElementById("processTypeRow");
	if(radioObj.value == 0 || radioObj.value==2 || radioObj.value==3 || radioObj.value==4){
		processTypeRow.style.display = "";
	}else{
		processTypeRow.style.display = "none";
	}
	var fdAuthorizeCategory = $("input[name='fdAuthorizeCategory']:checked").val();
	
	if(radioObj.value==4){
		$(".lbpm_authorize_row").hide();
		$(".lbpm_description_row").hide();
		$(".lbpm_businessautho_row").show();
		$(".lbpm_authorize_other_row").show();
		$(".lbpm_authorize_read_row").hide();
		_$validation.addElements($("input[name='fdStartTime']")[0],"checktime");
		_$validation.addElements($("input[name='fdEndTime']")[0],"checktime checkendtime");
		_$validation.addElements($("input[name='fdAuthorizedPersonName']")[0],"checkrequired");
		_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"required");
		$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").hide();
		_$validation.removeElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
		$("input[name='fdAuthorizedReaderIds']").parent().next(".txtstrong").hide();
		_$validation.addElements($("input[name='fdAuthorizedPostName']")[0],"checkrequired");
		KMSSValidation_HideWarnHint($("input[name='fdAuthorizedPostName']")[0]);
		//清除被授权人信息
		var address = Address_GetAddressObj("fdAuthorizedReaderNames",0);
		address.emptyAddress(false);
	}
	else{
		if(fdAuthorizeCategory == "0"){//若选择的是常规模式，才显示
			$(".lbpm_authorize_row").show();
		}
		$(".lbpm_description_row").show();
		$(".lbpm_businessautho_row").hide();
		_$validation.removeElements($("input[name='fdStartTime']")[0],"checktime");
		_$validation.removeElements($("input[name='fdEndTime']")[0],"checktime");
		_$validation.removeElements($("input[name='fdEndTime']")[0],"checkendtime");
		_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"checkrequired");
		_$validation.addElements($("input[name='fdAuthorizedPersonName']")[0],"required");
		$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").show();
		_$validation.removeElements($("input[name='fdAuthorizedPostName']")[0],"checkrequired");
		if(radioObj.value==1){
			_$validation.addElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
			$("input[name='fdAuthorizedReaderIds']").parent().next(".txtstrong").show();
			_$validation.removeElements($("input[name='fdAuthorizedPersonName']")[0],"required");
			$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").hide();
			$(".lbpm_authorize_other_row").hide();
			$(".lbpm_authorize_read_row").show();
			//清除被授权人信息
			var address = Address_GetAddressObj("fdAuthorizedPersonName",0);
			address.emptyAddress(false);
		}else{
			_$validation.addElements($("input[name='fdAuthorizedPersonName']")[0],"required");
			$("input[name='fdAuthorizedPersonId']").parent().next(".txtstrong").show();
			_$validation.removeElements($("input[name='fdAuthorizedReaderNames']")[0],"required");
			$("input[name='fdAuthorizedReaderIds']").parent().next(".txtstrong").hide();
			$(".lbpm_authorize_other_row").show();
			$(".lbpm_authorize_read_row").hide();
			//清除被授权人信息
			var address = Address_GetAddressObj("fdAuthorizedReaderNames",0);
			address.emptyAddress(false);
		}
	}
	if(radioObj.value==2){
		$("#expireRecoverLabel").show();
	}
	else{
		$("#expireRecoverLabel").hide();
	}
}

function switchAuthorizeTypeDesc(value){
	//控制描述语
	if(value == 0 || value==1 || value==2){
		var desc = "${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorize.desc')}";
		if(value == 0){
			$("#authorizeTypeDesc").html(desc+"${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorize.description0')}");
		}else if(value == 1){
			$("#authorizeTypeDesc").html(desc+"${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorize.description1')}");
		}else if(value == 2){
			$("#authorizeTypeDesc").html(desc+"${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorize.description2')}");
		}
		$("#authorizeTypeDesc").show();
	}else{
		$("#authorizeTypeDesc").html("");
		$("#authorizeTypeDesc").hide();
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
		return false;
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
	var isChange = false;
	if(fdScopeFormAuthorizeCateIdsObj.value != fdScopeFormAuthorizeCateIds || fdScopeFormTemplateIdsObj.value != fdScopeFormTemplateIds){
		isChange = true;
	}
	fdScopeFormAuthorizeCateIdsObj.value = fdScopeFormAuthorizeCateIds;
	fdScopeFormAuthorizeCateNamesObj.value = fdScopeFormAuthorizeCateNames;
	fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
	fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
	fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
	fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
	fdScopeFormAuthorizeCateShowtextsObj.value = fdScopeFormAuthorizeCateShowtexts;
	return isChange;
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
	var fdAuthorizeType = $('input[name="fdAuthorizeType"]:checked').val();
	var processTypeRow = document.getElementById("processTypeRow");
	var fdAuthorizeCategory = $("[name='fdAuthorizeCategory']:checked").val();
	var startTimeSmallerThanCurrentTime = false;
	if(processTypeRow.style.display != "none" && fdAuthorizeType != '4'){
		var fdAuthorizedPersonId = document.getElementsByName("fdAuthorizedPersonId")[0];
		if(fdAuthorizeCategory == "0" && fdAuthorizedPersonId.value == ""){
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
  			if(fdAuthorizeType == '2' && fdAuthorizeCategory == "0"){
  				startTimeSmallerThanCurrentTime = true;
  			}else{
  				alert('<bean:message key="lbpmAuthorizeForm.startTimeSmallerThanCurrentTime" bundle="sys-lbpmext-authorize"/>');
  	  			return;
  			}
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
	
	if(fdAuthorizeType != '4'){
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
	}
	
	// 授权阅读时，提交的时候去掉开始时间与结束时间的校验，提交失败则还原
	if(fdAuthorizeType == '1'){
		var startTimeValidate = $('input[name="fdStartTime"]').attr("validate");
		var endTimeValidate = $('input[name="fdEndTime"]').attr("validate");
		$('input[name="fdStartTime"]').attr("validate","");
		$('input[name="fdEndTime"]').attr("validate","");
		if (!formatDataBeforeSubmit() || !Com_Submit(document.lbpmAuthorizeForm, method)) {
			$('input[name="fdStartTime"]').attr("validate",startTimeValidate);
			$('input[name="fdEndTime"]').attr("validate",endTimeValidate);
		}
	} else if(startTimeSmallerThanCurrentTime){
		//当授权方式为为常规授权时的工作代理时且开始时间早于当前时间
		seajs.use([ 'lui/dialog', 'lui/topic' ], function(dialog, topic) {
			dialog.confirm("${lfn:message('sys-lbpmext-authorize:lbpmAuthorizeForm.startTimeSmallerThanCurrentTime2')}",function(value){
				if(value==true){
					Com_Submit(document.lbpmAuthorizeForm, method);
				}
			});
		});
	} else {
		//授权设置的校验和值的格式化
		if(!formatDataBeforeSubmit()){
			return;
		}
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

//业务授权
DocList_Info.push("TABLE_DocList_Details");

function checkBusinessAuthValidation(){
	return _$validation.validate();
}

function addDetail(){
	if(!checkBusinessAuthValidation()){
		return;
	}
	var param = {
			fdAuthorizerId:$("input[name='fdAuthorizerId']").val(),
			fdAuthorizerName:$("input[name='fdAuthorizerName']").val(),
			fdAuthorizedPersonId:$("input[name='fdAuthorizedPersonId']").val(),
			fdAuthorizedPersonName:$("input[name='fdAuthorizedPersonName']").val(),
			fdAuthorizedPostId:$("input[name='fdAuthorizedPostId']").val(),
			fdAuthorizedPostName:$("input[name='fdAuthorizedPostName']").val(),
			fdAuthStartTime:$("input[name='fdStartTime']").val(),
			fdAuthEndTime:$("input[name='fdEndTime']").val()
		};
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_detail/lbpmextBusinessAuthDetail.jsp?isAuthorize=true';
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='table.lbpmext.businessAuthDetail'/>",function(rtn){
			if(rtn){
				var fieldValues = new Object();
				for(var key in rtn){
					if(rtn[key]){
						fieldValues["fdAuthDetails[!{index}]."+key]=rtn[key];
					}
				}
				var newRow = DocList_AddRow("TABLE_DocList_Details",null,fieldValues);
				if(newRow){
					for(var key in rtn){
						if(rtn[key]){
							if(key=="fdType"){
								$(newRow).find("."+key).text(businessAuth.getControlTypeName(rtn[key]));
							}else{
								$(newRow).find("."+key).text(rtn[key]);
							}
						}
					}
					if(rtn["fdType"]==businessAuth.controlType.qualitative){
						$(newRow).find(".limitRange").hide();
					}else{
						$(newRow).find(".limitRange").show();
					}
				}
			}
		}
		,{width:500,height:550,close:false,params:param});
	});
}

function editDetail(dom,fdId){
	if(!checkBusinessAuthValidation()){
		return;
	}
	var url='/sys/lbpmext/businessauth/lbpmext_businessauth_detail/lbpmextBusinessAuthDetail.jsp?isAuthorize=true';
	var domRow = $(dom).closest("tr");
	var param = {
			fdAuthorizedPersonId:$("input[name='fdAuthorizedPersonId']").val(),
			fdAuthorizedPersonName:$("input[name='fdAuthorizedPersonName']").val(),
			fdAuthorizedPostId:$("input[name='fdAuthorizedPostId']").val(),
			fdAuthorizedPostName:$("input[name='fdAuthorizedPostName']").val(),
			fdAuthorizerId:domRow.find("input[name$='fdAuthorizerId']").val(),
			fdAuthorizerName:domRow.find("input[name$='fdAuthorizerName']").val(),
			fdAuthId:domRow.find("input[name$='fdAuthId']").val(),
			fdAuthName:domRow.find("input[name$='fdAuthName']").val(),
			fdNumber:domRow.find("input[name$='fdNumber']").val(),
			fdStartTime:domRow.find("input[name$='fdStartTime']").val(),
			fdEndTime:domRow.find("input[name$='fdEndTime']").val(),
			fdType:domRow.find("input[name$='fdType']").val(),
			fdLimit:domRow.find("input[name$='fdLimit']").val(),
			fdMinLimit:domRow.find("input[name$='fdMinLimit']").val(),
			fdAuthStartTime:$("input[name='fdStartTime']").val(),
			fdAuthEndTime:$("input[name='fdEndTime']").val()
	};
	//编辑已有的，直接掉lbpmBusinessAuthDetail.do的edit
	var isUpdate = domRow.attr("isUpdate")=="true";
	if(fdId&&!isUpdate){
		param = {
				fdStartTime:$("input[name='fdStartTime']").val(),
				fdEndTime:$("input[name='fdEndTime']").val()
		};
		url = '/sys/lbpmext/businessauth/lbpmBusinessAuthDetail.do?method=edit&fdId='+fdId+'&isAuthorize=true';
	}
	Com_Parameter.Dialog = param;
	seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
		dialog.iframe(url,"<bean:message bundle='sys-lbpmext-businessauth' key='table.lbpmext.businessAuthDetail'/>",function(rtn){
			if(rtn){
				var row = $(dom).closest("tr");
				row.attr("isUpdate","true");
				for(var key in rtn){
					if(rtn[key]){
						row.find("input[name$='"+key+"']").val(rtn[key]);
						if(key=="fdType"){
							$(row).find("."+key).text(businessAuth.getControlTypeName(rtn[key]));
						}else{
							$(row).find("."+key).text(rtn[key]);
						}
					}
				}
				if(rtn["fdType"]==businessAuth.controlType.qualitative){
					$(row).find(".limitRange").hide();
				}else{
					$(row).find(".limitRange").show();
				}
			}
		}
		,{width:500,height:550,close:false,params:param});
	});
}

function popwindow(url, width, height, param) {
	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;
	var isWebKit = navigator.userAgent.indexOf('AppleWebKit') != -1;
	var isSafari = navigator.userAgent.indexOf('Safari') > -1 && navigator.userAgent.indexOf('Chrome') == -1;
	if ((window.showModalDialog && !isWebKit) || isSafari) {
		var winStyle = "resizable:1;scroll:1;dialogwidth:" + width
				+ "px;dialogheight:" + height + "px;dialogleft:" + left
				+ ";dialogtop:" + top;
		var rtnVal = window.showModalDialog(url, param, winStyle);
		if (param.AfterShow)
			param.AfterShow(rtnVal);
	} else {
		var winStyle = "resizable=1,scrollbars=1,width=" + width + ",height="
				+ height + ",left=" + left + ",top=" + top
				+ ",dependent=yes,alwaysRaised=1";
		Com_Parameter.Dialog = param;
		var tmpwin = window.open(url, "_blank", winStyle);
		if (tmpwin) {
			tmpwin.onbeforeunload = function() {
				
				if (navigator.userAgent.indexOf("Edge") > -1) {
					if (param.AfterShow && !param.AfterShow._isShow) {
						param.AfterShow._isShow = true;
						param.AfterShow(tmpwin.returnValue,tmpwin.FlowChartObject ? tmpwin.FlowChartObject.otherContentInfo : {});
					}
			   }else{
				   setTimeout(function(){
						if (param.AfterShow && !param.AfterShow._isShow) {
							param.AfterShow._isShow = true;
							param.AfterShow(tmpwin.returnValue,tmpwin.FlowChartObject ? tmpwin.FlowChartObject.otherContentInfo : {});
						}
					},0);
			   }
				
			}
		}
	}
}

Com_Parameter.event["confirm"].push(function(){
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizerId']").val($("input[name='fdAuthorizerId']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizerName']").val($("input[name='fdAuthorizerName']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPersonId']").val($("input[name='fdAuthorizedPersonId']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPersonName']").val($("input[name='fdAuthorizedPersonName']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPostId']").val($("input[name='fdAuthorizedPostId']").val());
	$("#TABLE_DocList_Details").find("input[name$='fdAuthorizedPostName']").val($("input[name='fdAuthorizedPostName']").val());
	return true;
});
</script>

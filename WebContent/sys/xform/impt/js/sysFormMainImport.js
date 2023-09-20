
Com_IncludeFile("data.js");
Com_IncludeFile("doclist.js");
/**
 * 表单数据导入开关值改变事件处理函数
 * @param checked
 * @returns
 */
function importSwitchChange(checked){
	var selectUnImportWrapObj = $("#selectUnImportWrap");
	if (checked==true){
		selectUnImportWrapObj.show();
	} else {
		selectUnImportWrapObj.hide();
	}
}
var ImportFormData_LangArr = [];
$(function(){
	ImportFormData_LangArr = Data_GetResourceString('km-review:kmReviewTemplate.umImportFormField;km-review:kmReviewTemplate.formField');

})

/**
 * 选择不需要导入的表单字段
 * @param id
 * @param names
 * @returns
 */
function selectUnImportFields(idField,nameField){
	var dialog = new KMSSDialog(true, false);
	var node = dialog.CreateTree(ImportFormData_LangArr[1]);
	dialog.winTitle = ImportFormData_LangArr[0];
	node.isShowCheckBox = true;
	node.isMultSel = true;
	node.FetchChildrenNode = getVars;
	dialog.tree.isAutoSelectChildren = false;
	node.isExpanded = true;
	dialog.BindingField(idField, nameField, ";");
	dialog.SetAfterShow(function(rtnData){
	});
	dialog.Show();
}

/**
 * 获取所有符合条件的表单变量
 * @returns
 */
function getVars(){
	var fields = XForm_getXFormDesignerObj_reviewMainDoc();
	for(var i=0; i < fields.length; i++){
		if (isSkip(fields[i])){
			continue;
		}
		var dataType = fields[i].type;
		var textArr = fields[i].label.split(".");
		var pNode = this;
		var node;
		for(var j=0; j < textArr.length; j++){
			node = Tree_GetChildByText(pNode, textArr[j]);
			if(node == null){
				node = pNode.AppendChild(textArr[j]);
			}
			pNode = node;
		}
		node.value = fields[i].name;
		if (fields[i].controlType == "detailsTable"
			|| fields[i].businessType == "detailsTable"){
			node.isShowCheckBox = false;
		}
	}
}

/**
 * 判断生成树节点时,是否需要跳过此变量
 * @param fieldObj
 * @returns
 */
function isSkip(fieldObj){
	//不支持导入的控件数组
	var notExportControlType = ["attachment","uploadTemplateAttachment","Attachment"];
	//动态下拉会生成两个数据字典,需要跳过文本
	var needSkipDict = ["relationSelect","relationRadio","relationCheckbox","relationChoose"];
	//文档
	
	var isSkip = false;
	var sysDict = _XForm_GetSysDictObj(_xform_MainModelName);
	for (var i = 0; i < sysDict.length; i++){
		var obj = sysDict[i];
		if (obj && obj.name === fieldObj.name){
			isSkip = true;
		}
	}
	if(!fieldObj.label){
		isSkip = true;
	}
	var type = fieldObj.controlType || fieldObj.type;
	if (needSkipDict.indexOf(type) >= 0 
			&& /_text$/.test(fieldObj.name)){
		
		isSkip = true;
	}
	if (fieldObj.isShow === "false"){
		isSkip = true;
	}
	if (notExportControlType.indexOf(type) >= 0){
		isSkip = true;
	}
	return isSkip;
}

var importXFormData = {};

/**
 * 获取表单数据导入模板配置
 * @param fdTemplateModelName
 * @returns
 */
function getImportXFormDataSetting(fdTemplateModelName){
	var fdUseForm = $("input[name='fdUseForm']").val();
	if (fdUseForm === "false"){
		return;
	}
	var param = {};
	//主文档名
	param.modelName = $("input[name$='extendDataFormInfo.mainModelName']").val();
	//获取扩展文件
	param.extendFilePath = $("input[name$='extendDataFormInfo.extendFilePath']").val();
	//模板Id
	param.fdTemplateModelId = $("input[name='fdTemplateId']").val();
	//主文档id
	param.modelId = _xformMainModelId;
	param.fdTemplateModelName = fdTemplateModelName;
	//发送请求
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/sys_form/sysFormMainImport.do?method=getImportSetting",
		type: 'POST',
		async: false,
		dataType: 'json',
		data: param,
		success:function(data){
			if (typeof LUI !== "undefined" && !document.getElementById("optBarDiv")){
				LUI.ready(function(){
					if(data && data.fdIsImport){
						var button = LUI("importXFormData");
						if(button){
							button.element.show();
							importXFormData.setting = data;
							importXFormData.modelInfo = param;
						}
					}
				});
			}
			
		},
		error : function(message){
			console.log("获取模板Id: " + param.fdTemplateId + "的表单数据导入配置异常!");
		}
	});
}


/**
 * Excel导入
 * @param fdTemplateModelName
 * @returns
 */
function importXFormDataByExcel(){
	//给弹出框调用
	var xformFields = getXformflagFilelds(true);
	var propertyName = [];
	//模板下载不支持导出的控件
	var unsuportedControl = ['xform_chinavalue','xform_calculate','xform_relation_radio','xform_relation_checkbox','xform_relation_select','xform_relation_choose'];
	var notImportFields = (importXFormData.setting.fdUnImportFieldIds || "").split(";");
	for(var i = 0;i < xformFields.length;i++){
		var data = xformFields[i];
		// IE8不支持数组的indexof by zhugr 2017-08-26
		if($.inArray(data.fieldType,unsuportedControl) > -1){
			continue;
		}
		//模板配置的不需要导出的控件
		if($.inArray(data.fieldId,notImportFields) > -1){
			continue;
		}
		var fieldId = data.fieldId;
		propertyName.push(fieldId.substring(fieldId.lastIndexOf('.') + 1));
	}
	//去除相同的项
	propertyName = promiseUnique(propertyName);
	window._sysFormMainExcelUpload = {
		'modelName' : importXFormData.modelInfo.extendFilePath,
		'fdUnImportFieldIds' : importXFormData.setting.fdUnImportFieldIds,
		'propertyName' : propertyName.join(","),
		'field' : encodeURIComponent(JSON.stringify(xformFields)),
		'fdTemplateModelId' : importXFormData.modelInfo.fdTemplateModelId,
		'fdTemplateModelName' : importXFormData.modelInfo.fdTemplateModelName,
		'extendFilePath' : importXFormData.modelInfo.extendFilePath
	};
	
	var url = "/sys/xform/impt/sysFormMainData_upload.jsp";
	var height = document.documentElement.clientHeight * 0.9;
	var width = document.documentElement.clientWidth * 0.6;
	seajs.use(['lui/dialog'], function(dialog) {
		dialog.iframe(url,'表单数据导入',null,{width:width,height : height,close:true});
	});
}

/*
 * 通过xformflag标签获取控件ID
 * @param isFilter 是否需要过滤没有可供输入的表单元素区域
 * @return [{"fieldId":fd_xxxx,"fieldType":"xxxx"}]
 */
function getXformflagFilelds(isFilter){
	var fieldArray = [];
	$(document).find("xformflag").each(function(){
		// 若没有任何可供输入的元素，不需要
		if(isFilter){
			var isAddress = $(this).attr("_xform_type")=="address";
			var formElement = $(this).find("input[type!='hidden'],textarea,select");
			if(formElement.length == 0){
				// 没有表单元素就返回
				return;
			}else{
				// 如果存在表单元素，校验是否存在name
				var hasInput = false;
				for(var i = 0;i < formElement.length;i++){
					var e = formElement[i];
					if($(e).attr("name") ){
						// 下拉菜单没有只读属性
						//地址本无论是否只读，都会设置readOnly，故无法通过readOnly判断
						if(isAddress){
							if($(e).attr('_edit')=='true'){
								hasInput = true;
								break;
							}
						}else if($(e).data("rtf") && ($(e).attr('readOnly') == null || $(e).attr('readOnly') == false)){
							hasInput = true;
							break;
						}else{
							if($(e).css('display') != 'none' && ($(e).attr('readOnly') == null || $(e).attr('readOnly') == false)){
								hasInput = true;
								break;
							}
						}
					}
				}
				if(!hasInput){
					return;
				}
			}
		}
		if($(this).attr('flagid')){
			var fieldJSON = {};
			var fieldId = $(this).attr('flagid');
			fieldJSON.fieldId = fieldId;
			if(/\.(\d+)\./g.test(fieldId)){
				fieldJSON.fieldId = fieldId.replace(/\.(\d+)\./,".");
			}
			
			fieldJSON.fieldType = $(this).attr('flagtype'); 
			fieldArray.push(fieldJSON);
		}
	});
	return fieldArray;
}

function promiseUnique(array){
	var result = [], hash = {},elem;
	for (var i = 0; i < array.length; i++) {
		if(array[i]){
			elem = array[i];
			if (!hash[elem]) {
				result.push(elem);
				hash[elem] = true;
			}
		}	
	}
	return result;
}


function importData(contentList){
	var jsonData = eval(contentList);
	//获得弹出窗口的document
	if(window.frames["dialog_iframe"] && window.frames["dialog_iframe"].getElementsByTagName("iframe")[0]){
		subIframe = window.frames["dialog_iframe"].getElementsByTagName("iframe")[0];
	}
	if(subIframe && subIframe != null){
		uploadProcess = subIframe.contentDocument.getElementById("bar");
		subIframe.contentDocument.getElementById("resultTr").style.display = '';
		subIframe.contentDocument.getElementById("progressBar").style.display = '';
		subIframe.contentDocument.getElementById("progress").style.display = '';
	}
	for (var key in jsonData){
		if (key == "mainTable"){
			try{
				var mainTableData = jsonData["mainTable"];
				if (mainTableData){
					for (var i = 0; i < mainTableData.length; i++){
						var mainData = mainTableData[i];
						for(var fdId in mainData){
							var canonicalFdId = fdId.replace(/\.\S+/,"");
							var xformflagObj = $("xformflag[flagid='" + canonicalFdId + "'");
							var flagType = xformflagObj.attr("_xform_type");
							if (flagType === "address"){
								$form(canonicalFdId + ".id").val(mainData[canonicalFdId + ".id"]);	
								$form(canonicalFdId + ".name").val(mainData[canonicalFdId + ".name"]);	
							}else{
								$form(canonicalFdId).val(mainData[canonicalFdId]);
							}
						}
					}
					subIframe.contentDocument.getElementById("continueImport").style.display = 'none';
					if(subIframe && subIframe != null){
						subIframe.contentDocument.getElementById("importSuccessed").style.display='';
						subIframe.contentWindow.importExcelStatus = "finishImported";			
					}	
				}
			}catch(e){
				subIframe.contentWindow.importExcelStatus = "unUpload";
				return;
			}
		}else{
			DocList_importData("TABLE_DL_"+key,jsonData[key],true,200);
		}
	}
}

var viewLang;

seajs.use([ 'sys/ui/js/dialog' ,'lui/topic','lang!sys-modeling-base'],function(dialog,topic,lang) {
	viewLang=lang;
	//业务操作 列表添加按钮
	window.DocList_AddRow_Custom_Opers = function(optTb){
		DocList_AddRow(optTb);
		//更新角标
		var index = $('#'+optTb).find("> tbody > tr").last().find(".title-index").text();
		$('#'+optTb).find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$('#'+optTb).find("> tbody > tr").last().prev("tr").find("div.down").show();
		//刷新预览
		topic.publish("preview.refresh");
		//渲染之后出发点击
		//#155943:【低代码平台-优化】后台配置-查看视图，新增业务操作自动定位到新增操作配置区域
		var oper =$('#'+optTb).find("> tbody > tr").last().find(".model-edit-view-oper");
		switchSelectPosition(oper,"left");
		//146410:超过7个之后预览宽度都固定
		if($(".model-phone-opt-wrap").find(".model-phone-opt-item").length > 6){
			$(".model-body-wrap .model-body-content-phone .model-phone-opt-item").css("width","16.6%");
			//屏蔽缩小至50%会出现转行，现修改
			$(".model-phone-opt-wrap").css("height","38px");
			$(".model-phone-opt-wrap").css("overflow","hidden");
		}
	}
	//业务标签 列表添加按钮
	window.DocList_AddRow_Custom_Tabs = function(optTb,isMobile){
		var tableRow = DocList_AddRow(optTb);
		var dictBeanValue = getDictBeanValue($("select[name='fdModelId']").val());
		var optionsArr = loadDictProperties(dictBeanValue);
		var optionsHtml = getOptionHtmlByDataArray(optionsArr);
		var index = DocList_TableInfo['TABLE_DocList_listTabs_Form'].lastIndex - 1;
		if(isMobile){
			$(tableRow).find('select[name*="' + index + '\\]\\.fdRelationMainField"]').innerHTML = optionsHtml;
		}else{
			$('select[name*="' + index + '\\]\\.fdRelationMainField"]')[0].innerHTML = optionsHtml;
		}
		//更新角标
		var index = $('#'+optTb).find("> tbody > tr").last().find(".title-index").text();
		$('#'+optTb).find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$('#'+optTb).find("> tbody > tr").last().prev("tr").find("div.down").show();
		//刷新预览
		topic.publish("preview.refresh");
		//渲染之后出发点击
		$('#'+optTb).find("> tbody > tr").last().find(".model-edit-view-oper").click();
		//渲染之后"默认展开"绑定点击事件
		$(tableRow).find(".weui_switch").on("click","input[type='checkbox']",function(){
			if($(this).is(':checked')){
				$(this).closest("li").find("input[name*='fdIsOpen']").val("1");
			}else{
				$(this).closest("li").find("input[name*='fdIsOpen']").val("0");
			}
		});
		$(tableRow).find(".weui_switch input[type='checkbox']").attr("checked","checked");
		$(tableRow).find("[name*='fdIsOpen']").val("1");
		$(tableRow).find("[name*='fdType']").closest("div").find(".view_flag_radio_yes").click();
	}
	//业务操作名称选择
	window.operationSelectViewOperation = function(rtnData, isMobile,isNew){
		isMobile = isMobile === true ? true : false;
		var index = getElemIndex();
		var action = function(rtnData){
			//更新标题
			$("#TABLE_DocList_listOpers_Form").find(">tbody>tr").eq(index).find(".validation-container").remove();
			if(rtnData.length > 0) {
				var name = rtnData[0].fdName;
				$("#TABLE_DocList_listOpers_Form").find(">tbody>tr").eq(index).find(".validation-advice").remove();
				$("#TABLE_DocList_listOpers_Form").find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(name);
				//刷新预览
				topic.publish("preview.refresh");
			}
		}
		if(isNew){
			if(!isMobile){
				dialogSelect(false,'sys_modeling_operation_selectViewOperation','listOpers_pc[*].fdOperationId','listOpers_pc[*].fdOperationName',action, null, isMobile);
			}else{
				dialogSelect(false,'sys_modeling_operation_selectViewOperation','listOpers_mobile[*].fdOperationId','listOpers_mobile[*].fdOperationName',action, null, isMobile);
			}
		}else{
			dialogSelect(false,'sys_modeling_operation_selectViewOperation','listOpers_Form[*].fdOperationId','listOpers_Form[*].fdOperationName',action, null, isMobile);
		}
	}
	//业务标签名称更新
	window.tagNameUpdate = function(value,obj){
	    var fdLabelName=lang['modelingAppViewtab.fdName'];
		var a=$(obj).parents(".docListTr").eq(0);
		var v=$(a).find(".validation-container");
		for (var i = 0; i <v.length ; i++) {
			var node=v[i];
			var name=$(node).find(".validation-advice-title").html();
			if(fdLabelName==name){
				$(node).remove();
			}
		}
		$(obj).parents("tr").eq(0).find(".model-edit-view-oper-head-title span").html(value);
		//刷新预览
		topic.publish("preview.refresh");
		//修复新版查看视图数据校验提示信息顺序
		fixNewViewValidateInfo(obj);
		//修复旧版查看视图数据校验提示信息顺序
		fixOldViewValidateInfo(obj);

	}
	// 选择业务标签数据源
	window.chooseTabSource = function(dom, curAppId,isMobile,isNew){
		var resetTabSource = function(rtn){
			if(rtn && rtn.data){
				var names = [];
				var $container = $(dom).closest(".item-content");

				var appInfo = rtn.data["1"];
				names.push(appInfo.name);
				$container.find("[name*='fdApplicationId']").val(appInfo.id);

				var modelInfo = rtn.data["2"];
				names.push(modelInfo.name);
				var $modelDom = $container.find("[name*='fdModelId']");
				$modelDom.val(modelInfo.id);
				listTabsModelChange(modelInfo.id, $modelDom[0],isMobile,isNew);

				$(dom).find(".item-content-element-label").html(names.join(" / "));
			}
		}

		var params = [{
			"index" : "1",
			"text" : lang['table.modelingApplication'],
			"sourceUrl" : "/sys/modeling/base/modelingApplication.do?method=getAllAppInfos&c.eq.fdValid=true",
			"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/appRender.html",
			"curAppId" : curAppId || "",
			"viewWgt" : "AppView"
		}, "modelViewInfo"];

		openStepDialog(params, resetTabSource);
	}

	//修复新版查看视图数据校验提示信息顺序
	function fixNewViewValidateInfo(obj){
		//列表
		var docListTr = $(obj).closest(".docListTr");
		var nameValidateId = "advice-" + $(obj).attr("__validate_serial");
		var $listview = docListTr.find("[name^='listTabs_pc'][name$='fdListviewId']");
		//表单
		var $fdRelationMainFieldview = docListTr.find("[name^='listTabs_pc'][name$='fdRelationMainField']");
		//链表
		var $Linkview = docListTr.find("[name^='listTabs_pc'][name$='fdLinkParams']");
		var tempId;
		if($listview.is(":visible")){
			tempId= "advice-" + $listview.attr("__validate_serial");
		}
		if($fdRelationMainFieldview.is(":visible")){
			tempId= "advice-" + $fdRelationMainFieldview.attr("__validate_serial");
		}
		if($Linkview.is(":visible")){
			tempId= "advice-" + $Linkview.attr("__validate_serial");
		}
		if(tempId){
			changeDivByErrorInfo(docListTr,docListTr.find("#"+nameValidateId),docListTr.find("#"+tempId));
		}

	}
	//修复旧版查看视图数据校验提示信息顺序
	function fixOldViewValidateInfo(obj){
		//列表
		var docListTr = $(obj).closest(".docListTr");
		var nameValidateId = "advice-" + $(obj).attr("__validate_serial");
		var $validations = docListTr.find(".validation-container");
		if($validations && $validations.length>0){
			changeDivByErrorInfo(docListTr,docListTr.find("#"+nameValidateId),$validations[0]);
		}else{
			//针对上面无法取到的逻辑，新增一个交换方法
			var $validationAdvice = docListTr.find(".validation-advice");
			if($validationAdvice && $validationAdvice.length>0){
				changeDivByErrorInfo(docListTr,docListTr.find("#"+nameValidateId),$validationAdvice[0]);
			}
		}
	}

	function changeDivByErrorInfo(docListTr, $div1,$div3 ){
		var $temobj1 = $("<div></div>");
		var $temobj2 = $("<div></div>");
		$temobj1.insertBefore($div1);
		$temobj2.insertBefore($div3);
		$div1.insertAfter($temobj2);
		$div3.insertAfter($temobj1);
		$temobj1.empty();
		$temobj2.empty();
	};

	// 弹出"应用-表单-列表视图"选择框
	function openStepDialog(viewInfos, cb){
		viewInfos = viewInfos || ["appViewInfo","modelViewInfo"];
		var url = "/sys/modeling/base/resources/js/dialog/step/stepDialog.jsp";
		dialog.iframe(url,lang['behavior.select'],function(rtn){
			if(cb){
				cb(rtn);
			}
		},{
			width:900,
			height:500,
			close:true,
			params : {
				viewInfos : viewInfos
			}
		});
	}
});

function getElemIndex(){
	var row = DocListFunc_GetParentByTagName("TR");
	var table = DocListFunc_GetParentByTagName("TABLE",row);
	for(var i=0; i<table.rows.length; i++){
		if(table.rows[i]==row){
			return i;
		}
	}
}

function getIndex(o){
	var fdName = o.name;
	var index = fdName.substring(fdName.indexOf("[") + 1, fdName.indexOf("]"));
	return index;
}

//页签明细表选择 表单or列表
function fdTypeChange(v, o){
	if(o.item){
		o = o[0];
	}
	var index = getIndex(o);
	var fdRelationMainFieldDiv = $(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdRelationMainField");
	var fdListviewDiv = $(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListview");
	var fdLinkDiv = $(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdLink");
	var tabSourceLi = $(o).closest(".model-edit-view-oper-content").find("[id^='tabSourceLi_index_']");
	var itemTitle = fdLinkDiv.parent('.item-content').parent(".model-edit-view-oper-content-item").find('.item-title');
	if(v == 0){
		//目标表单显示
		tabSourceLi.show();
		//修改标题
		itemTitle.text(viewLang['modelingAppViewtab.showData']);
		// 表单
		fdListviewDiv.hide();
		fdListviewDiv.find('select').attr('validate','');
		fdLinkDiv.hide();
		fdLinkDiv.find('input').attr('validate','');
		fdRelationMainFieldDiv.find('select').attr('validate','required');
		fdRelationMainFieldDiv.show();
	}else if(v == 1){
		//目标表单显示
		tabSourceLi.show();
		//修改标题
		itemTitle.text(viewLang['modelingAppViewtab.showData']);
		// 列表
		fdRelationMainFieldDiv.hide();
		fdRelationMainFieldDiv.find('select').attr('validate','');
		fdLinkDiv.hide();
		fdLinkDiv.find('input').attr('validate','');
		fdListviewDiv.find('select').attr('validate','required');
		fdListviewDiv.show();
	}else if(v == 2){
		//目标表单隐藏
		tabSourceLi.hide();
		//设置fdModelId fdAppId
		tabSourceLi.find("[name='listTabs_pc\\[" + index + "\\]\\.fdApplicationId']").val(param.fdAppId);
		tabSourceLi.find("[name='listTabs_pc\\[" + index + "\\]\\.fdModelId']").val(param.fdModelId);
		tabSourceLi.find("[name='listTabs_mobile\\[" + index + "\\]\\.fdApplicationId']").val(param.fdAppId);
		tabSourceLi.find("[name='listTabs_mobile\\[" + index + "\\]\\.fdModelId']").val(param.fdModelId);
		//旧版视图
		tabSourceLi.find("[name='listTabs_Form\\[" + index + "\\]\\.fdApplicationId']").val(param.fdAppId);
		tabSourceLi.find("[name='listTabs_Form\\[" + index + "\\]\\.fdModelId']").val(param.fdModelId);
		//修改标题
		itemTitle.text(viewLang['modeling.link.address']);

		// 链接
		fdListviewDiv.hide();
		fdListviewDiv.find('select').attr('validate','');
		fdRelationMainFieldDiv.hide();
		fdRelationMainFieldDiv.find('select').attr('validate','');
		fdLinkDiv.show();
		fdLinkDiv.find('input').attr('validate','urlCustomize urlLength');
	}
	//隐藏非空提示
	fdListviewDiv.closest('td').find('.validation-advice').hide();
}

//拼接URL：根据业务模块ID查：业务模块字段的数据字典
function getDictBeanValue(v){
	var dictBeanValue = "modelingAppListviewModelDictService&fdAppModelId=!{cateid}&modelDictOnly=true";
	dictBeanValue = decodeURIComponent(dictBeanValue);
	dictBeanValue = replaceCateid(dictBeanValue, v);
	return dictBeanValue;
}

//拼接URL：根据业务模块ID查：列表视图的数据字典
function getListviewDictBeanValue(v,isMobile){
	var dictBeanValue = "modelingAppListviewService&fdModelId=!{cateid}";
	if(isMobile){
		dictBeanValue = "modelingAppMobileListViewService&modelId=!{cateid}";
	}
	dictBeanValue = decodeURIComponent(dictBeanValue);
	dictBeanValue = replaceCateid(dictBeanValue, v);
	return dictBeanValue;
}

//拼接URL：根据业务模块ID查：新版列表视图的数据字典
function getCollectionListviewDictBeanValue(v,isMobile){
	var dictBeanValue = "modelingAppCollectionViewService&fdModelId=!{cateid}";
	dictBeanValue = decodeURIComponent(dictBeanValue);
	dictBeanValue = replaceCateid(dictBeanValue, v);
	return dictBeanValue;
}
//新版列表视图入参
function getCollectionListviewIncParamsBeanValue(v,isMobile,tabId){
	var dictBeanValue = "modelingAppCollectionViewService&method=getIncParams&fdId=!{cateid}";
	dictBeanValue = decodeURIComponent(dictBeanValue);
	dictBeanValue = replaceCateid(dictBeanValue, v,isMobile,tabId);
	return dictBeanValue;
}

//拼接URL：根据列表视图ID查：入参列表
function getListviewIncParamsBeanValue(v,isMobile,tabId){
	if(isMobile){
		var dictBeanValue = "modelingAppMobileListViewService&method=getIncParams&fdId=!{cateid}&fdMobileTabId=!{tabid}";
	}else{
		var dictBeanValue = "modelingAppListviewService&method=getIncParams&fdId=!{cateid}";
	}
	dictBeanValue = decodeURIComponent(dictBeanValue);
	/*if(!v){
		v = initData.fdListviewId;
	}*/
	dictBeanValue = replaceCateid(dictBeanValue, v,isMobile,tabId);
	return dictBeanValue;
}

//根据数据字典获取下拉框中的内容
function loadDictProperties(dictBeanValue, o) {
	var url = dictBeanValue;
	var kmssData = new KMSSData();
	var datas = kmssData.AddBeanData(url).GetHashMapArray();
	if(!datas[0])
		return "";
	var arrStr = datas[0].key0;
	if(!arrStr)
		return "";
	var dataArray = $.parseJSON(getValidJSONArray(arrStr));
	if(!dataArray){
		return "";
	}
	return dataArray;
}

//根据数据字典获取下拉框中的内容(新版列表视图用)
function loadDictPropertiesCollectionView(dictBeanValue, o) {
	var url = dictBeanValue;
	var kmssData = new KMSSData();
	var datas = kmssData.AddBeanData(url).GetHashMapArray();
	if(!datas[0])
		return "";
	var arrStr = datas[0].key0;
	if(!arrStr)
		return "";
	var dataArray = $.parseJSON(getValidJSONArray(arrStr));
	if(!dataArray){
		return "";
	}
	return dataArray;
}

function loadMobileDictProperties(dictBeanValue, o){
	var url = dictBeanValue;
	var kmssData = new KMSSData();
	var datas = kmssData.AddBeanData(url).GetHashMapArray();
	if(!datas)
		return "";
	return datas;
}
function replaceCateid(url, cateid,isMobile,tabId) {
	var re = /!\{cateid\}/gi;
	url = url.replace(/!\{cateid\}/gi, cateid);
	if(isMobile){
		url = url.replace(/!\{tabid\}/gi, tabId);
	}
	return url;
}

function getValidJSONArray(arr){
	/*if(!arr || !arr.startsWith("[") || !arr.endsWith("]")){
		return "[]";
	}*/
	var startStr = "\\[";
	var endStr = "\\]";
	var startReg =new RegExp("^"+startStr);
	var endReg = new RegExp(endStr+"$");
	if(!arr || !startReg.test(arr) || !endReg.test(arr)){
		return "[]";
	}
	return arr;
}

/**
 * 根据dataArray绘制下拉框
 */
function getOptionHtmlByDataArray(dataArray, selected,isListviewIncParam,fieldType){
	if(!dataArray){
		return "";
	}
	var html = "";
	html += "<option value=''>"+viewLang['relation.please.choose']+"</option>";
	for(var i = 0;i < dataArray.length;i++){
		var data = dataArray[i];
		if(!isListviewIncParam){
			//屏蔽明细表
			if (data.isSubTableField === "true")
				continue
		}
		//明细表入参时下拉列表的选择进行过滤
		if(fieldType){
			if(fieldType == data.fieldType){
				html += "<option value='" + data.field + "'";
				if (data.isSubTableField === "true"){
					html += "data-subtableid='"+ data.subTableId + "'";
				}
				if(selected && selected == data.field){
					html += " selected ";
				}
				html += ">" + data.fieldText + "</option>";
			}
		}else{
			html += "<option value='" + data.field + "'";
			if(selected && selected == data.field){
				html += " selected ";
			}
			html += ">" + data.fieldText + "</option>";
		}
	}
	return html;
}

/**
 * 根据dataArray绘制下拉框
 */
function getMobileOptionHtmlByDataArray(dataArray, selected,isListviewIncParam,fieldType){
	var html = "";
	html += "<option value=''>"+viewLang['relation.please.choose']+"</option>";
	for(var i = 0;i < dataArray.length;i++){
		var data = dataArray[i];
		if(!isListviewIncParam){
			//屏蔽明细表
			if (data.isSubTableField === "true")
				continue
		}
		//明细表入参时下拉列表的选择进行过滤
		if(fieldType){
			if(fieldType == data.fieldType){
				html += "<option value='" + data.value + "'";
				if (data.isSubTableField === "true"){
					html += "data-subtableid='"+ data.subTableId + "'";
				}
				if(selected && selected == data.value){
					html += " selected ";
				}
				html += ">" + data.text + "</option>";
			}
		}else{
			html += "<option value='" + data.value + "'";
			if(selected && selected == data.value){
				html += " selected ";
			}
			html += ">" + data.text + "</option>";
		}
	}
	return html;
}


/**
 * 根据dataArray绘制列表视图入参
 */
function getListviewIncParamsHtml(index, dataArray, selectedArray,isNew){
	var html = "";
	if(isNew) {
		html += "<div class='item-dynamic-title' style='margin-top:16px;'>"+viewLang['modeling.dynamic.setting']+"</div>";
	}
	if (!(dataArray instanceof Array)){
		return "";
	}
	var selectedArrayIndex = [];
	if(dataArray.length >0){
	for(var i = 0;i < dataArray.length;i++){
		var data = dataArray[i];
		//需要选中的值
		var selected = getSelectedValue(data, selectedArray, selectedArrayIndex);
		//地址本类型："com.landray.kmss.sys.organization.model.SysOrgPerson|String"，
		//但mainModelOptionDataArray里面是com.landray.kmss.sys.organization.model.SysOrgPerson,故分隔
		var fieldType = data.fieldType.split("|")[0];
		if(isNew){
			var fieldOptionHtml = getOptionHtmlByDataArray(mainModelOptionDataArray, selected,true,fieldType);
			html += "<div class='inc-param-field' style='margin-top:8px;'>" + "&nbsp;" + data.paramText + "&nbsp;" +"</div>"+ "&nbsp;" + data.operator+ "&nbsp;";
			html += "<select name='listTabs_Form[" + index + "].fdListviewIncParams' data-param='" + data.paramName + "' class='inputsgl inc-param-select'>" + fieldOptionHtml + "</select>";
			html += "</br>";
		}else{
			var fieldOptionHtml = getOptionHtmlByDataArray(mainModelOptionDataArray, selected,true,fieldType);
			html += viewLang['modelingTreeView.parameter']+"：" + data.paramText + "&nbsp;" + data.operator;
			html += "<select name='listTabs_Form[" + index + "].fdListviewIncParams' data-param='" + data.paramName + "' class='inputsgl'>" + fieldOptionHtml + "</select>";
			html += "</br>";
		}
	}
	}else {
		html +="<div class='no-inc-param-tip' ><p>"+viewLang['modelingAppView.input.parameter.tip']+
			"</p><p>"+viewLang['modelingAppView.input.parameter.tip2']+
			"</p><p>"+viewLang['modelingAppView.input.parameter.tip3']+ "</p></div>"
	}
	var subTableId = "";
	if(mainModelOptionDataArray) {
		for (var i = 0; i < mainModelOptionDataArray.length; i++) {
			if (selected && selected == mainModelOptionDataArray[i].field) {
				subTableId = mainModelOptionDataArray[i].subTableId;
			}
		}
	}	html += "<input type='hidden'name='listTabs_Form[" + index + "].fdIncParamsSubTableId' value='"+subTableId+"'>";
	return html;
}

/**
 * 根据data.paramName对比selectedArray[?].param
 * @param data 当前的入参选项json
 * @param selectedArray 所有选中的选项jsonArray
 * @param selectedArrayIndex 已使用过的选中项（param可以重复，而且是有序的，所以按顺序一个个安置）
 * @returns selectedArray[?].field
 */
function getSelectedValue(data, selectedArray, selectedArrayIndex){
	if(selectedArray){
		for(var i in selectedArray){
			var selectedJSON = selectedArray[i];
			if(selectedJSON.param == data.paramName && !(selectedArrayIndex.indexOf(i) != -1)){
				selectedArrayIndex.push(i);
				return selectedJSON.field;
			}
		}
	}
}

//主业务模块发生变动 （当前只用于进入页面时的初始化）
function modelChange(v,o, isInit) {
	var dictBeanValue = getDictBeanValue($("[name='fdModelId']").val());
	mainModelOptionDataArray = loadDictProperties(dictBeanValue, o);
	mainModelFieldOptionHtml = getOptionHtmlByDataArray(mainModelOptionDataArray);
	//关联主模块字段 可选项变动
	$('select[name*="\\]\\.fdRelationMainField"]').each(function(){
		//初始化时,内容行中的数据无需初始化
		if(!isInit)
			this.innerHTML = mainModelFieldOptionHtml;
	});
	//列表视图参数 可选项变动
	$('select[name*="\\]\\.fdListviewIncParams"]').each(function(){
		this.innerHTML = mainModelFieldOptionHtml;
	});
	//业务操作 判断条件变动
	/*$('input[name*="\\]\\.fdJudgeType"]').each(function(){
		if(!isInit)
			this.innerHTML = mainModelFieldOptionHtml;
		onFdJudgeTypeChange(this.value, this);
	});*/
	//业务操作 可选项变动
	$('select[name*="\\]\\.fdField"]').each(function(){
		if(!isInit)
			this.innerHTML = mainModelFieldOptionHtml;
		onFdFieldChange(this.value, this);
	});
}

//入参查询 列表添加按钮
function DocList_AddRow_Custom_IncPara(optTb){
	DocList_AddRow(optTb);
	//更新角标
	var index = $('#'+optTb).find("> tbody > tr").last().find(".title-index").text();
	$('#'+optTb).find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
	//更新向下的图标
	$('#'+optTb).find("> tbody > tr").last().prev("tr").find("div.down").show();
	//更新标题
	var id = $("select[name='listIncPara_Form\\[" + index + "\\]\\.fdField']").val();
	var text = $("select[name='listIncPara_Form\\[" + index + "\\]\\.fdField']").find("option[value='"+id+"']").text();
	if(text){
		$("#"+optTb).find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(text);
	}
}

//列表中业务应用模块变动
function listTabsApplicationChange(value, dom){
	var $content = $(dom).closest(".model-edit-view-oper-content");
	// 联动业务字段变更
	var $modelDom = $content.find("[name*='fdModelId']");
	$modelDom.html("");
	var html = "";
	var options = getModelOptionsByAppId(value);
	$modelDom.html(getOptionHtmlByDataArray(options));
}

function getModelOptionsByAppId(fdAppId){
	var options = [];
	var kmssData = new KMSSData();
	options = kmssData.AddBeanData("modelingAppModelService&fdAppId=" + fdAppId).GetHashMapArray();
	for(var i = 0;i < options.length;i++){
		options[i].field = options[i].value;
		options[i].fieldText = options[i].text;
	}
	return options;
}

//列表中业务模块变动
function listTabsModelChange(v, o,isMobile,isNew){
	var index = getIndex(o);
	var value = $("[name='listTabs_Form\\[" + index + "\\]\\.fdModelId").val();
	if(!value){
		//新版查看视图
		value = o.parentNode.children[1].defaultValue;
	}
	var dictBeanValue = getListviewDictBeanValue(value,isMobile);
	var collectionListViewdictBeanValue = getCollectionListviewDictBeanValue(value,isMobile);
	if(isMobile){
		var optionsArr = loadMobileDictProperties(dictBeanValue, o);
		var optionsHtml = getMobileOptionHtmlByDataArray(optionsArr);
		$("select[name*='" + index + "\\]\\.fdMobileListViewId']")[0].innerHTML = optionsHtml;
	}else{
		var optionsArr = loadDictProperties(dictBeanValue, o);
		var collectionArr = loadDictPropertiesCollectionView(collectionListViewdictBeanValue,o);
		optionsArr = optionsArr.concat(collectionArr);
		var optionsHtml = getOptionHtmlByDataArray(optionsArr);
		$("select[name*='" + index + "\\]\\.fdListviewId']")[0].innerHTML = optionsHtml;
	}
	if(isNew){
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdTabViews").empty();
		$(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").empty();
	}else{
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdTabViews").empty();
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").empty();
	}

	var fdType = $("[name='listTabs_Form\\[" + index + "\\]\\.fdType']:checked").val();
	if(isNew){
		fdType = $(o).closest(".model-edit-view-oper-content").find("[name='listTabs_pc\\[" + index + "\\]\\.fdType']").val() || $(o).closest(".model-edit-view-oper-content").find("[name='listTabs_mobile\\[" + index + "\\]\\.fdType']").val();
	}
	if(fdType == 1){	//1 列表  0 表单
		//显示“列表视图”的div
		if(v){
			$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListview").show();
		}else{
			$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListview").hide();
		}
		if(isNew){
			$(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html("");
		}else{
			//列表入参
			$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html("");
		}
	}
}

//列表视图变动
function onFdListviewChange(v, o, selectedArray,isMobile,tabId){
	var index = getIndex(o);
	//根据所选列表视图查找所有参数
	var url = getListviewIncParamsBeanValue(v,isMobile,tabId);
	var kmssData = new KMSSData();
	var datas = kmssData.AddBeanData(url).GetHashMapArray();
	var arrStr = datas[0].key0;
	if(!arrStr)
		return "";
	var dataArray = $.parseJSON(getValidJSONArray(arrStr));
	if(!dataArray){
		return "";
	}
	if(dataArray.length == 0) {
		dataArray = IsCollectionViewIncParams(v,isMobile,tabId);
	}
	var html = "";
	if($(o).attr("name").indexOf("pc") > -1 || ($("input[name*='fdMobileTabId']").length > 0 && $("input[name*='fdMobileTabId']").attr("name").indexOf("mobile") > -1)){
		//新版
		html = getListviewIncParamsHtml(index, dataArray, selectedArray,true);
	}else{
		html = getListviewIncParamsHtml(index, dataArray, selectedArray);
	}
	//$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html(html);
	$(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html(html);
	//明细表入参的明细表id
	if(!$("[name='listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams']")){
		$("[name='listTabs_pc\\[" + index + "\\]\\.fdListviewIncParams']").on("change",function(){
			$("[name='listTabs_pc\\["+index+"\\]\\.fdIncParamsSubTableId']").val($(this).find('option:selected').data("subtableid"));
		})
		$("[name='listTabs_mobile\\[" + index + "\\]\\.fdListviewIncParams']").on("change",function(){
			$("[name='listTabs_mobile\\["+index+"\\]\\.fdIncParamsSubTableId']").val($(this).find('option:selected').data("subtableid"));
		})
	}else{
		$("[name='listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams']").on("change",function(){
			$("[name='listTabs_Form\\["+index+"\\]\\.fdIncParamsSubTableId']").val($(this).find('option:selected').data("subtableid"));
		})
	}
}

//判断是否是新版列表视图
function IsCollectionViewIncParams(v,isMobile,tabId) {
	if (v == ""){
		return "";
	}
	var collectionUrl = getCollectionListviewIncParamsBeanValue(v,isMobile,tabId);
	var kmssData = new KMSSData();
	var datasCollection = kmssData.AddBeanData(collectionUrl).GetHashMapArray();
	if(datasCollection[0]){
		var arrStr = datasCollection[0].key0;
		if(!arrStr)
			return "";
		var collectionDataArray = $.parseJSON(getValidJSONArray(arrStr));
		if(!collectionDataArray){
			return "";
		}
	}
	return collectionDataArray;
}

function onFdMobileListviewChange(v, o, selectedArray,incParamsArray){
	var index = getIndex(o);
	var value = $("[name='listTabs_Form\\[" + index + "\\]\\.fdModelId").val();
	if(!value){
		//新版查看视图
		value = $(o).closest(".model-edit-view-oper-content").find("[name='listTabs_pc[" + index + "].fdModelId']").val() || $(o).closest(".model-edit-view-oper-content").find("[name='listTabs_mobile[" + index + "].fdModelId']").val();
	}
	var dictBeanValue = getListviewDictBeanValue(value,true);
	var optionsArr = loadMobileDictProperties(dictBeanValue, o);
	var tabView = "";
	var tabDataArray = [];
	for(var i = 0;i < optionsArr.length;i++){
		if(v === optionsArr[i].value){
			tabView = optionsArr[i].viewsJson;
			tabDataArray = $.parseJSON(getValidJSONArray(tabView));
		}
	}
	if(tabDataArray.length == 0){
		//新版的
		tabDataArray = IsCollectionViewIncParams(v,true,"");
		var html = "";
		if($(o).attr("name").indexOf("pc") > -1 || $(o).attr("name").indexOf("mobile") > -1){
			//新版
			//根据参数在下方绘制
			html = getListviewIncParamsHtml(index, tabDataArray, incParamsArray,true);
		}else{
			//根据参数在下方绘制
			html = getListviewIncParamsHtml(index, tabDataArray, incParamsArray);
		}
		//$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html(html);
		$(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").html(html);
		//明细表入参的明细表id
		$("[name='listTabs_mobile\\[" + index + "\\]\\.fdListviewIncParams']").on("change",function(){
			$("[name='listTabs_mobile\\["+index+"\\]\\.fdIncParamsSubTableId']").val($(this).find('option:selected').data("subtableid"));
		})
	}else{
		var optionsHtml = getMobileTabHtmlByDataArray(tabDataArray,v,index,selectedArray);
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdTabViews").empty();
		//$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").empty();
		$(o).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdListviewIncParams").empty();
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdTabViews").html(optionsHtml);
		$("#_xform_listTabs_Form\\[" + index + "\\]\\.fdTabViews").off("change","[name='listTabs_Form["+index+"].fdMobileTabId']")
			.on("change","[name='listTabs_Form["+index+"].fdMobileTabId']",function () {
				//选择tab后绘制入参
				var incParamsArray = getFdListviewIncParamsArray(index);
				var value = $("[name='listTabs_Form["+index+"].fdMobileListViewId']").val();
				if(!value){
					value = $("[name='listTabs_mobile["+index+"].fdMobileListViewId']").val();
				}
				onFdListviewChange(value,this,incParamsArray,true,this.value);
				$("input[name*='" + index + "\\]\\.fdMobileTabId'").val(this.value);
			})
	}
}

function getMobileTabHtmlByDataArray(optionsArr,v,index,selectedArray){
	if(!optionsArr){
		return "";
	}
	var html = viewLang['modeling.choose.tab'];
	html += "<select name= \"listTabs_Form["+index+"].fdMobileTabId\" class=\"inputsgl\" style='margin-top:5px;'>";
	html += "<option value=''>"+viewLang['relation.please.choose']+"</option>";
	for(var i = 0; i < optionsArr.length;i++){
		html += "<option value='"+optionsArr[i].value+"'";
		if(selectedArray === optionsArr[i].value){
			html += "selected='selected'";
		}
		html += ">"+optionsArr[i].text+"</option>";
	}
	html += "</select>";

	return html;
}

var mainModelOptionDataArray;
var mainModelFieldOptionHtml;
//初始化
Com_AddEventListener(window, 'load', dataInit);
function dataInit() {
	// 设置应用表单的文本值
	$("[name*='fdApplicationId']").each(function (index, dom) {
		var $container = $(dom).closest(".item-content");
		var names = [];
		var appName = $(dom).attr("data-model-name");
		if (appName) {
			names.push(appName);
		}

		var modelName = $container.find("[name*='fdModelId']").attr("data-model-name");
		if (modelName) {
			names.push(modelName);
		}

		$container.find(".item-content-element-label").html(names.join(" / "));
	});
	//触发业务模块的下拉框变动事件
	$("[name*='fdModelId']").each(function (index, dom) {
		modelChange(this.value, this, true);
	});

	//新版列表视图的model和旧版的不一样，为兼容旧数据查看视图业务标签处的展示数据换成了组件的方式，
	//这里加个延时，等页面画完再去初始化（临时方案）
	setTimeout(function () {
		//触发列表视图的下拉框变动事件
		$("select[name*='\\]\\.fdListviewId']").each(function () {
			var index = getIndex(this);
			var incParamsArray = getFdListviewIncParamsArray(index);
			onFdListviewChange(this.value, this, incParamsArray);
		});

		//触发移动列表视图的下拉框变动事件
		$("select[name*='\\]\\.fdMobileListViewId']").each(function () {
			var index = getIndex(this);
			var fdMobileTabId = $("input[name*='" + index + "\\]\\.fdMobileTabId'").val();
			var incParamsArray = getFdListviewIncParamsArray(index);
			onFdMobileListviewChange(this.value, this, fdMobileTabId, incParamsArray);
			$("select[name*='" + index + "\\]\\.fdMobileTabId'").trigger("change");
		});

	},500);
}

//获取业务标签显示情况
function showTabByOper(isNewPc){
	var data = {};
	var dataObjArr = getViewData();
	if(isNewPc === "true"){
		dataObjArr = getNewViewData();
	}else if(isNewPc === "false"){
		dataObjArr = getNewViewData4m();
	}
	var listOperArr = dataObjArr[0].listOperations;		//业务操作
	var listTagsArr = dataObjArr[0].listTags;			//业务标签
	for(var j = 0;j < listTagsArr.length;j++){
		for(var i = 0;i < listOperArr.length;i++){
			listTagsArr[j].isNewPc = isNewPc || "";
			if(listTagsArr[j].name === "沉淀记录" && listOperArr[i].name === "沉淀"){
				listTagsArr[j].show = "true";
			}
			if(listTagsArr[j].name === "传阅记录" && listOperArr[i].name === "传阅"){
				listTagsArr[j].show = "true";
			}
		}
	}
	data.listTags = listTagsArr;
	return data;
}

//显示或隐藏沉淀记录和传阅记录
function showOrHideTab(tabDataShow){
	var listTabArr = tabDataShow.listTags;
	for(var i = 0;i < listTabArr.length;i++){
		if(listTabArr[i].name === "沉淀记录"){
			var $input = {};
			if(listTabArr[i].isNewPc === "true"){
				$input = $("input[name*='listTabs_pc'][value='沉淀记录']").closest("tr");
			}else if(listTabArr[i].isNewPc === "false"){
				$input = $("input[name*='listTabs_mobile'][value='沉淀记录']").closest("tr");
			}else{
				$input = $("input[name*='listTabs_Form'][value='沉淀记录']").closest("tr");
			}
			showOrHide($input,listTabArr[i],i);
		}
		if(listTabArr[i].name === "传阅记录"){
			var $input = {};
			if(listTabArr[i].isNewPc === "true"){
				$input = $("input[name*='listTabs_pc'][value='传阅记录']").closest("tr");
			}else if(listTabArr[i].isNewPc === "false"){
				$input = $("input[name*='listTabs_mobile'][value='传阅记录']").closest("tr");
			}else{
				$input = $("input[name*='listTabs_Form'][value='传阅记录']").closest("tr");
			}
			showOrHide($input,listTabArr[i],i);
		}
	}
}

function showOrHide($input,listTabObj,i){
	if(listTabObj.show && listTabObj.show === "true"){
		$input.css("display","block");
		if(listTabObj.isNewPc && listTabObj.isNewPc === "true"){
			$(".preview-pc .tag-btn").find("[data-lui-position='fdTag-"+i+"']").css("display","block");
		}else{
			$(".preview-mobile .tag-btn").find("[data-lui-position='fdTag-"+i+"']").css("display","block");
			$(".preview-mobile .model-body-content-phone-tab").find("[data-lui-position='fdTag-"+i+"']").css("display","inline-block");
			//新版查看视图移动端预览样式调整
			if(listTabObj.name == "传阅记录"){
				$(".model-body-content-phone-tab .model-edit-view-btn-item").css("width","65px");
				$(".model-tab-bottom").css("margin-left","16px");
			}
		}
		/*if($input.find("input[name*='fdIsShow']").val() == "0"){
			$input.find("input[name*='fdIsShow']").next().find("input[type='checkbox']").click();
		}
		if($input.find("input[name*='fdIsOpen']").val() == "0"){
			$input.find("input[name*='fdIsOpen']").next().find("input[type='checkbox']").click();
		}*/
	}else{
		$input.css("display","none");
		if(listTabObj.isNewPc && listTabObj.isNewPc === "true"){
			$(".preview-pc .tag-btn").find("[data-lui-position='fdTag-"+i+"']").css("display","none");		//预览
		}else{
			$(".preview-mobile .tag-btn").find("[data-lui-position='fdTag-"+i+"']").css("display","none");		//预览
			$(".preview-mobile .model-body-content-phone-tab").find("[data-lui-position='fdTag-"+i+"']").css("display","none");		//预览
			//新版查看视图移动端预览样式调整
			if(listTabObj.name == "传阅记录") {
				$(".model-body-content-phone-tab .model-edit-view-btn-item").css("width", "90px");
				$(".model-tab-bottom").css("margin-left", "33px");
			}
		}
		$input.find("input[name*='fdIsShow']").val("0");
		$input.find("input[name*='fdIsShow']").next().find("input[type='checkbox']").removeAttr("checked");		//关闭显示
		$input.find("input[name*='fdIsOpen']").val("0");
		$input.find("input[name*='fdIsOpen']").next().find("input[type='checkbox']").removeAttr("checked");		//关闭展开
	}
}

//进入页面时展示列表视图的入参
function getFdListviewIncParamsArray(index){
	var fdListviewIncParams = $("input[name*='" + index + "\\]\\.fdListviewIncParams'").val();
	/*if(!fdListviewIncParams || !fdListviewIncParams.startsWith("[") || !fdListviewIncParams.endsWith("]")){
		fdListviewIncParams = "[]";
	}*/
	fdListviewIncParams = getValidJSONArray(fdListviewIncParams);
	fdListviewIncParams = fdListviewIncParams.replace(/&quot;/g,'"');
	var incParamsArray = $.parseJSON(getValidJSONArray(fdListviewIncParams));
	return incParamsArray;
}

function validateLabel(){
	var opPass= true;
	var labelPass= true;
	var pcPass= true;
	var mobilePass= true;
	var $KMSSValidation = $GetKMSSDefaultValidation();
	//$(".validation-container").remove();
	$(".validation-advice").remove();
	//操作
	$("input[name^='listOpers_Form'][name$='fdOperationName']").each(function(){
		var validate = $(this).attr("validate");
		if(validate) {
			var required = $KMSSValidation.getValidator("required");
			if (!required.test($(this).val())) {
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + viewLang['modelingAppViewopers.fdOperation'] + "</span>" +
					viewLang['kmReviewMain.notNull'] + "</td></tr></tbody></table></div></div>"));
				opPass = false;
			}
		}
	});
	//标签名
	$("input[name^='listTabs_Form'][name$='fdName']").each(function(){
		var validate = $(this).attr("validate");
		if(validate) {
			var required = $KMSSValidation.getValidator("required");
			if (!required.test($(this).val())) {
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + viewLang['modelingAppViewtab.fdName'] + "</span>" +
					" " + viewLang['kmReviewMain.notNull'] + "</td></tr></tbody></table></div></div>"));
				labelPass = false;
			}
		}
	});
	//展示数据
	$("select[name^='listTabs_Form'][name$='fdRelationMainField']").each(function(){
		var validate = $(this).attr("validate");
		if(validate) {
			var required = $KMSSValidation.getValidator("required");
			var name = $(this).attr("name");
			var index = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
			var type = $("[name='listTabs_Form[" + index + "].fdType']").val();
			var errTitle = viewLang['modelingAppViewtab.showData'];
			if (type == "2") {
				errTitle = viewLang['modelingAppViewtab.linkAddress'];
			}
			if (!required.test($(this).val())) {
				$(this).attr("onclick", "changeShowData(this)");
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + errTitle + "</span>" +
					" " + viewLang['kmReviewMain.notNull'] + "</td></tr></tbody></table></div></div>"));
				pcPass = false;
			}
		}
	});
	//pc端展示数据
	$("select[name^='listTabs_Form'][name$='ListviewId']").each(function(){
		var validate = $(this).attr("validate");
		if(validate) {
			var required = $KMSSValidation.getValidator("required");
			var name = $(this).attr("name");
			var index = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
			var type = $("[name='listTabs_Form[" + index + "].fdType']").val();
			var errTitle = viewLang['modelingAppViewtab.showData'];
			if (type == "2") {
				errTitle = viewLang['modelingAppViewtab.linkAddress'];
			}
			if (!required.test($(this).val())) {
				$(this).attr("onclick", "changeShowData(this)");
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + errTitle + "</span>" +
					" " + viewLang['kmReviewMain.notNull'] + "</td></tr></tbody></table></div></div>"));
				pcPass = false;
			}
		}
	});
	//移动端展示数据
	$("select[name^='listTabs_Form'][name$='fdMobileListViewId']").each(function(){
		var validate = $(this).attr("validate");
		if(validate) {
			var required = $KMSSValidation.getValidator("required");
			var name = $(this).attr("name");
			var index = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
			var type = $("[name='listTabs_Form[" + index + "].fdType']").val();
			if (type == "2") {
				//链接不校验
				return;
			}
			if (!required.test($(this).val())) {
				$(this).attr("onclick", "changeShowData(this)");
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">" + viewLang['modelingAppViewtab.showData'] + "</span>" +
					" " + viewLang['kmReviewMain.notNull'] + "</td></tr></tbody></table></div></div>"));
				mobilePass = false;
			}
		}
	});
	$KMSSValidation.addValidator('urlCustomize','<span class="validation-advice-title">'+viewLang['modeling.link.address']+"</span> "+viewLang['view.enter.valid.url'],function(v) {
		var pattern = /^\/[a-zA-Z0-9\w-./?%&=]+/;
		return !validation.getValidator('isEmpty').test(v) && pattern.test(v);
	});
	$KMSSValidation.addValidator('urlLength','<span class="validation-advice-title">'+viewLang['modeling.link.address']+"</span> "+viewLang['view.URL.address.cannot.exceed.1000'],function(v) {
		if(v){
			return !validation.getValidator('isEmpty').test(v) && (v.length <1000);
		}else{
			return false;
		}
	});
	//外部链接
	$("input[name^='listTabs_Form'][name$='fdLinkParams']").each(function(){
		$(this).blur(function(){
			$(this).closest("tr").find(".validation-container").remove();
		})
		var validate = $(this).attr("validate");
		if(validate){
			var required = $KMSSValidation.getValidator("urlLength");
			var required2 = $KMSSValidation.getValidator("urlCustomize");
			if(!required.test($(this).val()) || !required2.test($(this).val())){
				$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
					"<table class=\"validation-table\"><tbody><tr><td>" +
					"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
					"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+viewLang['modelingAppViewtab.linkAddress']+" </span>" +
					viewLang['view.enter.valid.url']+
					"</td></tr></tbody></table></div></div>"));
				labelPass = false;
			}
		}
	});
	return opPass&&labelPass&&pcPass&&mobilePass;
}

function validateTabNameOverSize(){
	var labelPass= true;
	var $KMSSValidation = $GetKMSSDefaultValidation();
	$(".validation-advice").remove();
	$("input[name^='listTabs_Form'][name$='fdName']").each(function(){
		var required = $KMSSValidation.getValidator("maxLength(18)");
		if(!required.test($(this).val())){
			$(this).closest("td").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
				"<table class=\"validation-table\"><tbody><tr><td>" +
				"<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
				"<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">"+viewLang['modelingAppViewtab.fdName']+"</span>" +
				" "+viewLang['modelingAppViewtab.fdNameOverSize1']+" "+required.options.length+" "+viewLang['modelingAppViewtab.fdNameOverSize2']+"</td></tr></tbody></table></div></div>"));
			labelPass = false;
		}
	});
	return labelPass;
}

function changeShowData(obj){
	var showData=viewLang['modelingAppViewtab.showData'];
	var a=$(obj).parents(".docListTr").eq(0);
	var v=$(a).find(".validation-container");
	for (var i = 0; i <v.length ; i++) {
		var node=v[i];
		var name=$(node).find(".validation-advice-title").html();
		if(showData==name){
			$(node).remove();
		}
	}
}

//提交前
function submit_packageData(){
	jointFdListviewIncParams();
	converTextAndExpressToFormula();
	//提交前设置操作多值
	handlerCheckBoxVal();
}
//提交
function submit_custom(method){
	var fdName = $("[name='fdName']").val();
	if(!fdName){
		$("[name='fdName']").val(viewLang['view.untitled.view'])
	}
	submit_packageData();
	Com_Submit(document.modelingAppViewForm, method)

}
//处理业务操作固定值的多选操作
function handlerCheckBoxVal(){
	var $fdOperators = $("[name^='listOpers_Form'][name$='fdOperator']");
	for(var i=0; i<$fdOperators.length; i++){
		var operator = $($fdOperators[i]).val();
		if(operator === 'contain'){//包含，存在多值
			var $fdInputValues = $("input[name='_listOpers_Form["+i+"].fdInputValue'][type='checkbox']:checked");
			if($fdInputValues && $fdInputValues.length > 0){//存在多选
				//合并多选值
				var value = '';
				for(var j=0; j<$fdInputValues.length; j++){
					value += $($fdInputValues[j]).val()+";";
				}
				if(value){
					value = value.substring(0,value.length-1);
				}
				$("input[name='listOpers_Form["+i+"].fdInputValue'][type='hidden']").val(value);
				continue;
			}
		}
		var inputVal = $("input[name='_listOpers_Form["+i+"].fdInputValue'][type='radio']:checked").val();
		$("input[name='listOpers_Form["+i+"].fdInputValue'][type='hidden']").val(inputVal || $("input[name='_listOpers_Form["+i+"].fdInputValue']").val() || "");
	}
	if($fdOperators.length == 0){
		//pc处理
		var $fdOperators = $("[name^='listOpers_pc'][name$='fdOperator']");
		for(var i=0; i<$fdOperators.length; i++){
			var operator = $($fdOperators[i]).val();
			if(operator === 'contain'){//包含，存在多值
				var $fdInputValues = $("input[name='_listOpers_pc["+i+"].fdInputValue'][type='checkbox']:checked");
				if($fdInputValues && $fdInputValues.length > 0){//存在多选
					//合并多选值
					var value = '';
					for(var j=0; j<$fdInputValues.length; j++){
						value += $($fdInputValues[j]).val()+";";
					}
					if(value){
						value = value.substring(0,value.length-1);
					}
					$("input[name='listOpers_pc["+i+"].fdInputValue'][type='hidden']").val(value);
					continue;
				}
			}
			var inputVal = $("input[name='_listOpers_pc["+i+"].fdInputValue'][type='radio']:checked").val();
			$("input[name='listOpers_pc["+i+"].fdInputValue'][type='hidden']").val(inputVal || $("input[name='_listOpers_pc["+i+"].fdInputValue']").val() || "");
		}
		//移动处理
		var $fdOperators = $("[name^='listOpers_mobile'][name$='fdOperator']");
		for(var i=0; i<$fdOperators.length; i++){
			var operator = $($fdOperators[i]).val();
			if(operator === 'contain'){//包含，存在多值
				var $fdInputValues = $("input[name='_listOpers_mobile["+i+"].fdInputValue'][type='checkbox']:checked");
				if($fdInputValues && $fdInputValues.length > 0){//存在多选
					//合并多选值
					var value = '';
					for(var j=0; j<$fdInputValues.length; j++){
						value += $($fdInputValues[j]).val()+";";
					}
					if(value){
						value = value.substring(0,value.length-1);
					}
					$("input[name='listOpers_mobile["+i+"].fdInputValue'][type='hidden']").val(value);
					continue;
				}
			}
			var inputVal = $("input[name='_listOpers_mobile["+i+"].fdInputValue'][type='radio']:checked").val();
			$("input[name='listOpers_mobile["+i+"].fdInputValue'][type='hidden']").val(inputVal || $("input[name='_listOpers_mobile["+i+"].fdInputValue']").val() || "");
		}
	}
}

//提交是拼接公式定义字段
function converTextAndExpressToFormula() {
	$("[name$='\\.fdFormula']").each(function(idx,dom){
		var name = $(dom).attr("name");
		var index = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
		var fdFormula_text = $("[name=\"listOpers_Form["+index+"].fdFormula_text\"]").val();
		var fdFormula_express = $("[name=\"listOpers_Form["+index+"].fdFormula_express\"]").val();
		if(typeof fdFormula_text == "undefined" || typeof fdFormula_express == "undefined"){
			//新版查看视图
			if(name.indexOf("pc") > -1){
				fdFormula_text = $("[name=\"listOpers_pc["+index+"].fdFormula_text\"]").val();
				fdFormula_express = $("[name=\"listOpers_pc["+index+"].fdFormula_express\"]").val();
			}else{
				fdFormula_text = $("[name=\"listOpers_mobile["+index+"].fdFormula_text\"]").val();
				fdFormula_express = $("[name=\"listOpers_mobile["+index+"].fdFormula_express\"]").val();
			}
		}
		var formula = {
			"text":fdFormula_text,
			"express":fdFormula_express
		}
		$(dom).val(JSON.stringify(formula))
	});
}
//提交时拼接列表视图的入参
function jointFdListviewIncParams(){
	if(DocList_TableInfo['TABLE_DocList_listTabs_Form']){
		var index = DocList_TableInfo['TABLE_DocList_listTabs_Form'].lastIndex - 1;
		if(index > -1){
			for(var i=0; i < index + 1; i++){
				var fdListviewIncParams = [];
				$("select[name*='" + i + "\\]\\.fdListviewIncParams'").each(function(){
					var json = {
						"param" : $(this).attr("data-param"),
						"field" : $(this).val()
					};
					fdListviewIncParams.push(json);
				});
				//设置隐藏输入框的值
				$("input[name*='" + i + "\\]\\.fdListviewIncParams'").val(JSON.stringify(fdListviewIncParams));
			}
		}
	}
}

//业务操作-值类型 改变事件
function onFdValueChange(v, o){
	if(!o){
		return;
	}
	var index = getIndex(o);
	var name = $(o).attr("name");
	var nameType = name.substring(name.indexOf("_") + 1, name.indexOf("["));
	var fdField = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdField']");
	if(nameType == "mobile"){
		fdField = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdField']");
	}else if(nameType == "pc"){
		fdField = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdField']");
	}
	var type = getFieldTypeByField(fdField.val());
	if(type){
		var inputValueHtml = getInputValueHtml(nameType,index, type, v, fdField.val());
		var fdInputValueDiv = $("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue");
		fdInputValueDiv.nextAll(".validation-advice").hide();
		fdInputValueDiv.html(inputValueHtml);
		$(fdInputValueDiv).css("width","67%");
		fdInputValueDiv.children().eq(0).css({
			"vertical-align":"middle",
			"width":"96%"
		});
		fdInputValueDiv.children().eq(0).addClass("height28");
	}
}

//入参-条件字段 改变事件
function onFdFieldChangeIncpara(v, o){
	if(!o){
		return;
	}
	var index = getIndex(o);
	var name = $(o).attr("name");
	var nameType = name.substring(name.indexOf("_") + 1, name.indexOf("["));
	//根据是否选择"条件字段"显示/隐藏后面的选项
	if (!v || v == "") {
		$("#_xform_listIncPara_Form\\[" + index + "\\]\\.fdOperator").hide();
		if(nameType == "mobile"){
			$("select[name='listIncPara_mobile\\[" + index + "\\]\\.fdValue']").hide();
			$("select[name='listIncPara_mobile\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listIncPara_mobile\\[" + index + "\\]\\.fdValue']").val("fix");
		}else if(nameType == "pc"){
			$("select[name='listIncPara_pc\\[" + index + "\\]\\.fdValue']").hide();
			$("select[name='listIncPara_pc\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listIncPara_pc\\[" + index + "\\]\\.fdValue']").val("fix");
		}else{
			$("select[name='listIncPara_Form\\[" + index + "\\]\\.fdValue']").hide();
			$("select[name='listIncPara_Form\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listIncPara_Form\\[" + index + "\\]\\.fdValue']").val("fix");
		}
	} else {
		$("#_xform_listIncPara_Form\\[" + index + "\\]\\.fdOperator").show();
		if(nameType == "mobile"){
			$("select[name='listIncPara_mobile\\[" + index + "\\]\\.fdValue']").show();
		}else if(nameType == "pc"){
			$("select[name='listIncPara_pc\\[" + index + "\\]\\.fdValue']").show();
		}else{
			$("select[name='listIncPara_Form\\[" + index + "\\]\\.fdValue']").show();
		}
	}
	//根据条件字段绘制
	var fdField = $("select[name='listIncPara_Form\\[" + index + "\\]\\.fdField']");
	var fdOperatorSelect = $("select[name='listIncPara_Form\\[" + index + "\\]\\.fdOperator']");
	var fdInputValue = $("input[name='listIncPara_Form\\[" + index + "\\]\\.fdInputValue']");
	var type = getFieldTypeByField(fdField.val());
	if(type){
		//运算符下拉框
		var operratorHtml = getOperatorOptionHtml(type, fdOperatorSelect.val());
		fdOperatorSelect.html(operratorHtml);
	}
	//更新标题
	var text = $("select[name='listIncPara_Form\\[" + index + "\\]\\.fdField']").find("option[value='"+fdField.val()+"']").text();
	$("#TABLE_DocList_listIncPara_Form").find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(text);
}

//业务操作-条件字段 改变事件
function onFdFieldChange(v, o){
	if(!o){
		return;
	}
	var index = getIndex(o);
	var name = $(o).attr("name");
	var nameType = name.substring(name.indexOf("_") + 1, name.indexOf("["));
	$("select[name='listOpers_Form\\[" + index + "\\]\\.fdOperator']").closest("li").show();
	$("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']").closest("li").show();
	//根据是否选择"条件字段"显示/隐藏后面的选项
	if (!v || v == "") {
		if(nameType === "pc"){
			$(o).closest("li").nextAll("li").hide();
			$("select[name='listOpers_pc\\[" + index + "\\]\\.fdValue']").hide();
			$("select[name='listOpers_pc\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listOpers_pc\\[" + index + "\\]\\.fdValue']").val("fix");
			$("input[name='listOpers_pc\\[" + index + "\\]\\.fdInputValue']").val("");
		}else if(nameType === "mobile"){
			$(o).closest("li").nextAll("li").hide();
			$("select[name='listOpers_mobile\\[" + index + "\\]\\.fdValue']").hide();
			$("select[name='listOpers_mobile\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listOpers_mobile\\[" + index + "\\]\\.fdValue']").val("fix");
			$("input[name='listOpers_mobile\\[" + index + "\\]\\.fdInputValue']").val("");
		}else{
			//旧版
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdOperator").hide();
			$("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']").hide();
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue").hide();
			$("select[name='listOpers_Form\\[" + index + "\\]\\.fdOperator']").val("equals");
			$("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']").val("fix");
			$("input[name='listOpers_Form\\[" + index + "\\]\\.fdInputValue']").val("");
		}
	} else {
		if(nameType === "pc"){
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdOperator").show();
			$(o).closest("li").nextAll("li").show();
			$("select[name='listOpers_pc\\[" + index + "\\]\\.fdValue']").show();
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue").css({
				"display":"inline-block",
				"width":"67%",
				"position":"relative",
				"top":"-1px"
			})
		}else if(nameType === "mobile"){
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdOperator").show();
			$(o).closest("li").nextAll("li").show();
			$("select[name='listOpers_mobile\\[" + index + "\\]\\.fdValue']").show();
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue").css({
				"display":"inline-block",
				"width":"67%",
				"position":"relative",
				"top":"-1px"
			})
		}else{
			//旧版
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdOperator").show();
			$("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']").show();
			$("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue").css({
				"display":"inline-block",
				"width":"67%",
				"position":"relative",
				"top":"-1px"
			})
		}
	}
	//根据条件字段绘制
	var fdField = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdField']");
	var fdOperatorSelect = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdOperator']");
	var fdInputValue = $("input[name='listOpers_Form\\[" + index + "\\]\\.fdInputValue']");
	var fdJudgeType = $("input[name='listOpers_Form\\[" + index + "\\]\\.fdJudgeType']:checked");
	if(nameType == "pc"){
		fdField = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdField']");
		fdOperatorSelect = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdOperator']");
		fdInputValue = $("input[name='listOpers_pc\\[" + index + "\\]\\.fdInputValue']");
		fdJudgeType = $("input[name='listOpers_pc\\[" + index + "\\]\\.fdJudgeType']:checked");
	}else if(nameType == "mobile"){
		fdField = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdField']");
		fdOperatorSelect = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdOperator']");
		fdInputValue = $("input[name='listOpers_mobile\\[" + index + "\\]\\.fdInputValue']");
		fdJudgeType = $("input[name='listOpers_mobile\\[" + index + "\\]\\.fdJudgeType']:checked");
	}else{
		//旧版视图，根据是否选择"条件字段|0"显示/隐藏后面的选项
		var v = fdJudgeType.val();
		if (!v || v == "" || v =="0") {
			$("[data-lui-mark=\"view-oper-formula-" + index + "\"]").hide();
			$("[data-lui-mark=\"view-oper-field-" + index + "\"]").show();
		} else {
			$("[data-lui-mark=\"view-oper-formula-" + index + "\"]").show();
			$("[data-lui-mark=\"view-oper-field-" + index + "\"]").hide();
		}
	}
	fdJudgeType.closest("div").find(".fdJudgeType_"+index).filter("[value='"+fdJudgeType.val()+"']").addClass("active");
	fdJudgeType.closest("div").find(".fdJudgeType_"+index).filter("[value='"+fdJudgeType.val()+"']").siblings().removeClass("active");
	var type = getFieldTypeByField(fdField.val());
	var isSubTableField = IsFieldSubTable(fdField.val());
	if(type){
		//1.运算符下拉框
		var operratorHtml = getOperatorOptionHtml(type, fdOperatorSelect.val(),isSubTableField);
		fdOperatorSelect.html(operratorHtml);
		//2.固定值输入框
		var fdValue = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']");
		if(nameType == "pc"){
			fdValue = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdValue']");
		}else if(nameType == "mobile"){
			fdValue = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdValue']");
		}
		var inputValueHtml = getInputValueHtml(nameType,index, type, fdValue.val(), fdField.val(), fdInputValue.val(),fdOperatorSelect.val());
		var fdInputValueDiv = $("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue");
		if(nameType == "pc"){
			fdInputValueDiv = $($("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue")[0]);
		}else if(nameType == "mobile"){
			fdInputValueDiv = $($("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue")[1]);
		}
		fdInputValueDiv.nextAll(".validation-advice").hide();
		fdInputValueDiv.html(inputValueHtml);
		fdInputValueDiv.children().eq(0).css({
			"vertical-align":"middle",
			"width":"96%"
		});
		fdInputValueDiv.children().eq(0).addClass("height28");
	}else{
		$("select[name='listOpers_Form\\[" + index + "\\]\\.fdOperator']").closest("li").hide();
		$("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']").closest("li").hide();
	}
}
//运算符改变事件
function onOperatorChange(v, o){
	if(!o){
		return;
	}
	var index = getIndex(o);
	var name = $(o).attr("name");
	var nameType = name.substring(name.indexOf("_") + 1, name.indexOf("["));
	//根据条件字段绘制
	var fdField = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdField']");
	var fdOperatorSelect = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdOperator']");
	var fdInputValue = $("input[name='listOpers_Form\\[" + index + "\\]\\.fdInputValue']");
	if(nameType == "mobile"){
		//根据条件字段绘制
		fdField = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdField']");
		fdOperatorSelect = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdOperator']");
		fdInputValue = $("input[name='listOpers_mobile\\[" + index + "\\]\\.fdInputValue']");
	}else if(nameType == "pc"){
		//根据条件字段绘制
		fdField = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdField']");
		fdOperatorSelect = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdOperator']");
		fdInputValue = $("input[name='listOpers_pc\\[" + index + "\\]\\.fdInputValue']");
	}
	var type = getFieldTypeByField(fdField.val());
	if(type){
		//1.运算符下拉框
		var operratorHtml = getOperatorOptionHtml(type, fdOperatorSelect.val());
		fdOperatorSelect.html(operratorHtml);
		//2.固定值输入框
		var fdValue = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdValue']");
		if(nameType == "mobile"){
			fdValue = $("select[name='listOpers_mobile\\[" + index + "\\]\\.fdValue']");
		}else if(nameType == "pc"){
			fdValue = $("select[name='listOpers_pc\\[" + index + "\\]\\.fdValue']");
		}
		var inputValueHtml = getInputValueHtml(nameType,index, type, fdValue.val(), fdField.val(), fdInputValue.val(),fdOperatorSelect.val());
		var fdInputValueDiv = $("#_xform_listOpers_Form\\[" + index + "\\]\\.fdInputValue");
		fdInputValueDiv.nextAll(".validation-advice").hide();
		fdInputValueDiv.html(inputValueHtml);
		fdInputValueDiv.children().eq(0).css({
			"vertical-align":"middle",
			"width":"96%"
		});
		fdInputValueDiv.children().eq(0).addClass("height28");
	}
}

//业务操作-条件判断字段 改变事件
function onFdJudgeTypeChange(v, o,isNew){
	if(!o){
		return;
	}
	if (o instanceof  NodeList)
		o = o[0]
	var index = getIndex(o);
	//根据是否选择"条件字段|0"显示/隐藏后面的选项
	if (!v || v == "" || v =="0") {
		var val = $("select[name='listOpers_Form\\[" + index + "\\]\\.fdField']").val();
		$("[data-lui-mark=\"view-oper-formula-" + index + "\"]").hide();
		if(!val && !isNew){
			$($("[data-lui-mark=\"view-oper-field-" + index + "\"]")[0]).show();
		}else{
			$("[data-lui-mark=\"view-oper-field-" + index + "\"]").show();
		}

	} else {
		$("[data-lui-mark=\"view-oper-formula-" + index + "\"]").show();
		$("[data-lui-mark=\"view-oper-field-" + index + "\"]").hide();
	}
	if(isNew){
		$(".fdJudgeType_"+index).filter("[value='"+v+"']").addClass("active");
		$(".fdJudgeType_"+index).filter("[value='"+v+"']").siblings().removeClass("active");
		$("input[name='listOpers_pc["+index+"].fdJudgeType']").val(v);
		//触发条件字段的change事件，显示或隐藏下面的dom
		if (!v || v == "" || v =="0") {
			$("[name='listOpers_pc[" + index + "].fdField']").trigger("change");
		}

		$("input[name='listOpers_mobile["+index+"].fdJudgeType']").val(v);
		//触发条件字段的change事件，显示或隐藏下面的dom
		if (!v || v == "" || v =="0") {
			$("[name='listOpers_mobile[" + index + "].fdField']").trigger("change");
		}
	}
}

/**
 * 业务标签，显示范围 改变事件
 * @param value
 * @param object
 * @param channel
 */
function onFdShowTypeChange(value, object, channel){
	if(!object){
		return;
	}
	if (object instanceof  NodeList){
		object = object[0];
	}
	var index = getIndex(object);
	//根据是否选择"显示范围"显示/隐藏后面的选项
	if (!value || value == "" || value == "0") {
		$("[data-lui-mark=\"view-tabs-field-"+channel +"-"+ index + "\"]").hide();
	} else {
		$("[data-lui-mark=\"view-tabs-field-"+channel +"-"+ index + "\"]").show();
	}
	$("input[name='listTabs_"+channel+"\\[" + index + "\\]\\.fdShowType']").val(value);

}
/**
 * 根据条件字段类型绘制"运算符"下拉框
 */
function getOperatorOptionHtml(type, currentValue,isSubTableField){
	var operatorArray = getOperatorByType(type);
	var operatorArr = [];
	for(var i = 0;i < operatorArray.length;i++){
		if(isSubTableField == "true"
			&& (operatorArray[i].value == "!{equal}"
				|| operatorArray[i].value == "!{notequal}")){
			continue;
		}
		operatorArr.push(operatorArray[i]);
	}
	return buildOperatorSelectHtml(operatorArr, currentValue);
}

/**
 * 根据字段名获取字段对应类型
 */
function getFieldTypeByField(field){
	for(i in mainModelOptionDataArray){
		if(field == mainModelOptionDataArray[i].field){
			return mainModelOptionDataArray[i].fieldType;
		}
	}
}

/**
 * 判断字段是否是明细表字段
 * @param field
 * @constructor
 */
function IsFieldSubTable(field){
	for(i in mainModelOptionDataArray){
		if(field == mainModelOptionDataArray[i].field
			&& mainModelOptionDataArray[i].hasOwnProperty("isSubTableField")){
			return mainModelOptionDataArray[i].isSubTableField;
		}
	}
}


//构建运算符的HTML
function buildOperatorSelectHtml(operatorArray, value){
	var html = "";
	for(var i = 0;i < operatorArray.length; i++){
		var operatorJson = operatorArray[i];
		if(operatorJson.value){
			var optionValue = operatorJson.value.replace("!{" , "").replace("}" , "");
			var optionText = operatorJson.text.replace("!{" , "").replace("}" , "");
			html += "<option value='" + optionValue + "'";
			if(value != null && value == optionValue){
				html += " selected='selected'";
			}
			html += ">" + optionText + "</option>";
		}
	}
	return html;
}

//根据类型返回对应的运算符
function getOperatorByType(type){
	if(type != null){
		type = type.toLowerCase();
		var operatorArray = [];
		if(type == "string" || type == "rtf"){
			operatorArray = xform_main_data_getEnumType("operatorString");
		}else if(type == "long" || type == "integer" || type == "double" || type == "bigdecimal"){
			operatorArray = xform_main_data_getEnumType("operatorNum");
		}else if(type == "datetime" || type == "date" || type == "time"){
			operatorArray = xform_main_data_getEnumType("operatorTime");
		}else if(type == "boolean"){
			operatorArray = xform_main_data_getEnumType("operatorEnum");
		}else if(type.indexOf("com.landray.kmss") > -1){
			operatorArray = [{"value" : "equal", "text" : "ID等于"}];
		}else{
			// 无法匹配的，统一按字符串处理
			operatorArray = xform_main_data_getEnumType("operatorString");
		}
		return operatorArray;
	}
}

//获取enum type根据MainDataInsystemEnumUtil getAllEnum（）里面的key匹配
function xform_main_data_getEnumType(type){
	if(_main_data_insystem_enumCollection.hasOwnProperty(type)){
		return _main_data_insystem_enumCollection[type];
	}
	return [];
}

//输入值后面的dom type:属性的类型；	valueType:值的类型	field:属性名（在枚举类型的时候需要根据属性名取相应的选项）
function getInputValueHtml(nameType,index, type, valueType, field, inputValue,operator){
	if(type != null && valueType != null){
		if(!inputValue)
			inputValue = "";
		type = type.toLowerCase();
		var html = "";
		//固定值
		if(xform_main_data_getEnumType("inputValue")[0].value.indexOf(valueType) != -1){
			if(type == "datetime" || type == "date" || type == "time"){
				//时间控件
				var functionName = "xform_main_data_triggleSelect" + type + "(event,this);";
				var validateName = "__" + type;
				html += "<div class='inputselectsgl' style='width:150px;' onclick=\"" + functionName + "\">";
				html += "<div class='input'><input name='" + getInputValueName(index,nameType) + "' type='text' validate='" + validateName + "' value='"+ inputValue +"'></div>";
				html += "<div class='inputdatetime'></div>";
				html += "</div>";
			}else if(type == "boolean"){
				var booleanShowValueArray = ['是','否'];
				var booleanRealValueArray = ['1','0'];
				for(var i = 0; i < booleanShowValueArray.length;i++){
					html += "<label><input type='radio' name='"+ getInputValueName(index,nameType) + "'";
					if(inputValue != '' && inputValue == booleanRealValueArray[i]){
						html += " checked";
					}
					html += " value='" + booleanRealValueArray[i] + "'>"+ booleanShowValueArray[i] +"</label>";
				}
			}else if(type == 'enum'){
				//枚举型
				var items = xform_main_data_findItemByDict(field);
				if(items){
					var valuesArray = items.enumValues;
					var values;
					// 默认隔开空格个数
					var appendSpaceHtml = '&nbsp;&nbsp;';
					// 例如inputValue为： m;f
					if(inputValue){
						values = inputValue.split(";");
					}
					// 枚举的修改为单选
					var type = "radio"
					if(operator == "contain" || operator == "notContain"){
						type = "checkbox";
					}
					for(var i = 0; i < valuesArray.length;i++){
						html += "<label><input type='"+type+"' name='"+ getInputValueName(index,nameType) + "' ";
						// 只要存在于该数组里面
						if(values && $.inArray(valuesArray[i].fieldEnumValue,values) > -1){
							html += " checked";
						}
						html += " value='" + valuesArray[i].fieldEnumValue + "'>"+ valuesArray[i].fieldEnumLabel +"</label>" + appendSpaceHtml;
					}
				}
			}else if(type == 'double' || type == 'float' || type == 'long' || type == 'int' || type == 'bigdecimal'){
				//数字
				html += "<input type='text' name='" + getInputValueName(index,nameType) + "' class='inputsgl' validate='number' value='"+ inputValue +"'/>";
			}else if(type.indexOf("com.landray.kmss.sys.organization.model") > -1){
				var inputName = getInputValueName(index,nameType);
				//地址本
				var orgSelectType = "ORG_TYPE_ALL";
				//根据字段获取当前地址本的类型
				for(i in mainModelOptionDataArray){
					if(field == mainModelOptionDataArray[i].field){
						orgType =  mainModelOptionDataArray[i].orgType;
						break;
					}
				}
				if(orgType === "ORG_TYPE_PERSON"){
					orgSelectType = "ORG_TYPE_PERSON";
				} else if(orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT"){
					orgSelectType = "ORG_TYPE_DEPT";
				}
				var inputValueText = "";
				//由于一开始设计的缺陷，地址本的文本值没有持久化，获取一下
				var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppView.do?method=getAddressText&addressId="+inputValue;
				$.ajax({
					url : url,
					type : "get",
					dataType : "json",
					async : false,
					success : function(rtn) {
						if(rtn){
							inputValueText = rtn.addressId || "";
						}
					}
				});
				html += "<div class='inputselectsgl' onclick='Dialog_Address(false, \"" + inputName + "\",\"" + inputName + "_name\", \";\", " + orgSelectType + ");'>" +
					"<input name='" + inputName + "' type='hidden' value='"+inputValue+"'>" +
					"<div class='input'><input placeholder='"+viewLang['modeling.page.choose']+"' name='" + inputName + "_name' type='text' value='"+inputValueText+"'></div>" +
					"<div class='selectitem'></div>" +
					"</div>";
			}else{
				html += "<input type='text' name='" + getInputValueName(index,nameType) + "' class='inputsgl' value='"+ inputValue +"'/>";
			}
		} else if(xform_main_data_getEnumType("inputValue")[3].value.indexOf(valueType) != -1){
			//公式定义
			var inputName = getInputValueName(index,nameType);
			html += "<div class='inputselectsgl' style='width:150px;' onclick=\"onClick_Formula_Dialog('" + inputName + "','"+ inputName + "', 'String');\">";
			html += "<div class='input'><input name='" + inputName + "' type='text' value='"+ inputValue +"'></div>";
			html += "<div class='selectitem'></div>";
			html += "</div>";
		} else {
			//入参、空值
			html = "";
		}
		return html;
	}
}

// 根据属性值去数据字典变量里面查找对应的选项
function xform_main_data_findItemByDict(field){
	var dictData = mainModelOptionDataArray;
	if(dictData && field){
		for(var i = 0; i < dictData.length; i++){
			if(dictData[i].field == field){
				return dictData[i];
			}
		}
	}
	return null;
}

function getInputValueName(index,nameType){
	if(nameType == "mobile"){
		return "_listOpers_mobile[" + index + "].fdInputValue";
	}else if(nameType == "pc"){
		return "_listOpers_pc[" + index + "].fdInputValue";
	}else{
		return "_listOpers_Form[" + index + "].fdInputValue";
	}
}

//datetime控件触发
function xform_main_data_triggleSelectdatetime(event,dom){
	var input = $(dom).find("input[name^='_listOpers_Form']");
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_pc']");
	}
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_mobile']");
	}
	selectDateTime(event,input);
}
//date控件触发
function xform_main_data_triggleSelectdate(event,dom){
	var input = $(dom).find("input[name^='_listOpers_Form']");
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_pc']");
	}
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_mobile']");
	}
	selectDate(event,input);
}
//date控件触发
function xform_main_data_triggleSelecttime(event,dom){
	var input = $(dom).find("input[name^='_listOpers_Form']");
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_pc']");
	}
	if(input.length == 0){
		input = $(dom).find("input[name^='_listOpers_mobile']");
	}
	selectTime(event,input);
}

/**
 * 业务操作对话框
 *
 * exceptValue 需要排除的值，格式为字符串id;id
 */
function dialogSelect(mul, key, idField, nameField, action,exceptValue, isMobile){
	var index = getElemIndex();
	$("#TABLE_DocList_listOpers_Form").find(">tbody>tr").eq(index).find(".validation-container").remove();
	isMobile = isMobile === true ? true : false;
	var rowIndex;
	if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
		var tr=DocListFunc_GetParentByTagName('TR');
		var tb= DocListFunc_GetParentByTagName("TABLE");
		var tbInfo = DocList_TableInfo[tb.id];
		rowIndex=tr.rowIndex-tbInfo.firstIndex;
	}
	var dialogCfg = formOption.dialogs[key];
	if(dialogCfg){
		var params='';
		var inputs=getDialogInputs(idField);
		if(inputs){
			for(var i=0;i<inputs.length;i++){
				var argu = inputs[i];
				var modelVal;
				if(argu["value"].indexOf('*')>-1){
					//入参来自明细表
					modelVal=$form(argu["value"],idField.replace("*",rowIndex)).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo=viewLang['Gantt.current.dialog.box.miss.parameter']+argu["label"]+viewLang['Gantt.check.form.configuration']
							alert(errorInfo);
							return;
						}
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}else{
					//入参来自主表
					modelVal=$form(argu["value"]).val();
					if(modelVal==null||modelVal==''){
						if(argu["required"]=="true"){
							var errorInfo=viewLang['Gantt.current.dialog.box.miss.parameter']+argu["label"]+viewLang['Gantt.check.form.configuration'];
							alert(errorInfo);
							return;
						}
						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
					}else{
						params+='&'+argu["key"]+'='+modelVal;
					}
				}
			}
		}
		params+='&isMobile=' + isMobile;
		params=encodeURI(params);
		var tempUrl = 'sys/modeling/base/resources/jsp/dialog_select_template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_' + idField.replace('[*]','---') + '&exceptValue='+exceptValue;
		if(mul==true){
			tempUrl += '&mulSelect=true';
		}else{
			tempUrl += '&mulSelect=false';
		}
		var dialog = new KMSSDialog(mul,true);
		dialog.URL = Com_Parameter.ContextPath + tempUrl;
		var source = dialogCfg.sourceUrl;
		var propKey = '__dialog_' + idField + '_dataSource';
		dialog[propKey] = function(){
			if(idField.indexOf('*')>-1){
				var initField=idField.replace('*',rowIndex);
				return {url:source+params,init:document.getElementsByName(initField)[0].value};
			}else{
				return {url:source+params,init:document.getElementsByName(idField)[0].value};
			}
		};
		window[propKey] = dialog[propKey];
		propKey =  'dialog_' + idField;
		dialog[propKey] = function(rtnInfo){
			if(rtnInfo==null) return;
			//142838 选择业务操作按钮后，还是提示操作不能为空
			var inputField = idField.replace("*",rowIndex);
			$("[name='"+inputField+"']").closest(".docListTr").find(".validation-advice").remove();
			var datas = rtnInfo.data;
			var rtnDatas=[],ids=[],names=[],fdOperationScenarios =[],fdDefTypes=[];
			for(var i=0;i<datas.length;i++){
				var rowData = domain.toJSON(datas[i]);
				rtnDatas.push(rowData);
				ids.push($.trim(rowData[rtnInfo.idField]));
				names.push($.trim(rowData[rtnInfo.nameField]));
				fdOperationScenarios.push($.trim(rowData["fdOperationScenario"]));
				fdDefTypes.push($.trim(rowData["fdDefType"]));
			}
			if(idField.indexOf('*')>-1){
				//明细表
				$form(idField,idField.replace("*",rowIndex)).val(ids.join(";"));
				$form(nameField,idField.replace("*",rowIndex)).val(names.join(";"));
				tipsShow(rowIndex,fdDefTypes.join(";"),fdOperationScenarios.join(";"));
			}else{
				//主表
				$form(idField).val(ids.join(";"));
				$form(nameField).val(names.join(";"));
			}
			if(action){
				action(rtnDatas);
			}
			//出参处理
			var outputs=getDialogOutputs(idField);
			if(outputs){
				if(rtnDatas.length==1){
					for(var i=0;i<outputs.length;i++){
						var output=outputs[i];
						if(output["value"].indexOf('*')>-1){
							$form(output["value"],output["value"].replace("*",rowIndex)).val(rtnDatas[0][output["key"]]);
						}else{
							$form(output["value"]).val(rtnDatas[0][output["key"]]);
						}
					}
				}
			}
		};
		domain.register(propKey,dialog[propKey]);
		dialog.Show(800,550);
	}
}

//显示开启机制提示信息
function tipsShow(rowIndex,fdDefType,fdOperationScenario){
	//fdDefType:8-打印、 9-导入、 10-批量打印、  11-归档  支付场景: fdDefType = null fdOperationScenario-1
	if("8"===fdDefType|| "9" ===fdDefType|| "10"===fdDefType ||"11"===fdDefType ||
		((fdDefType == null || fdDefType == undefined || fdDefType == "") && "1" === fdOperationScenario)){
		this.tips = $("#listOpers_pc\\["+rowIndex+"\\]\\.tips");
		this.divTips = $("<div class='operateTips' />");
		var html = viewLang['modeling.model.mechanism.tips'];
		this.divTips.html(html);
		this.tips.html(this.divTips);
	}else {
		//如果存在旧的tips，则删除掉
		this.tips = $("#listOpers_pc\\["+rowIndex+"\\]\\.tips");
		if(this.tips) {
			this.tips.html("");
		}
	}
}

//返回列表页面
function returnListPage(type){
	var url = param.contextPath+'sys/modeling/base/view/config/index_body.jsp?fdModelId='+param.fdModelId+'&fdMobile=false';
	if(type == "mobile"){
		url = param.contextPath+'sys/modeling/base/view/config/index_body.jsp?fdModelId='+param.fdModelId+'&fdMobile=true';
	}
	if(type == "new"){
		url = param.contextPath+'sys/modeling/base/view/config/new/index_body.jsp?fdModelId='+param.fdModelId+'&fdMobile=false';
	}
	var iframe = window.parent.document.getElementById("trigger_iframe");
	$(iframe).attr("src",url);
	//修改样式
	$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
	$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","block");
	return false;
}

//获取查看视图的数据
function getViewData(){
	var datas = [];
	var data = {};
	var listOperations = [];//业务操作
	var listTags = [];//业务标签
	data.listOperations = listOperations;
	data.listTags = listTags;

	//业务操作
	var fdOperationNames = $("[name^='listOpers_Form'][name$='fdOperationName']");
	for(var i=0; i<fdOperationNames.length; i++){
		var fdOperationName = $(fdOperationNames[i]).val() || "";
		var listOperation = {};
		listOperation.name = fdOperationName;
		listOperations.push(listOperation);
	}

	//业务标签
	var listTagNames = $("[name^='listTabs_Form'][name$='fdName']");
	for(var i=0; i<listTagNames.length; i++){
		var listTagName = $(listTagNames[i]).val();
		var listTag = {};
		listTag.name = listTagName;
		listTags.push(listTag);
	}

	datas.push(data);
	return datas;
}

//获取新版查看视图的数据
function getNewViewData(){
	var datas = [];
	var data = {};
	var listOperations = [];//业务操作
	var listTags = [];//业务标签
	data.listOperations = listOperations;
	data.listTags = listTags;
	//业务操作
	var fdOperationNames = $("[name^='listOpers_pc'][name$='fdOperationName']");
	for(var i=0; i<fdOperationNames.length; i++){
		var fdOperationName = $(fdOperationNames[i]).val() || "";
		var listOperation = {};
		listOperation.name = fdOperationName;
		listOperations.push(listOperation);
	}
	//#158103 【专项任务12】【低代码平台专项】查看视图配置页面，有两个编辑按钮，点击第二个编辑按钮报错 注释了该段代码，因PC端构建了移动端的编辑按钮。
	/*var fdOperationNameMobiles = $("[name^='listOpers_mobile'][name$='fdOperationName']");
	for(var i=0; i<fdOperationNameMobiles.length; i++){
		var fdOperationNameMobile = $(fdOperationNameMobiles[i]).val() || "";
		var listOperation = {};
		listOperation.name = fdOperationNameMobile;
		listOperations.push(listOperation);
	}*/
	//业务标签
	var listTagNames = $("[name^='listTabs_pc'][name$='fdName']");
	for(var i=0; i<listTagNames.length; i++){
		var listTagName = $(listTagNames[i]).val();
		var listTag = {};
		listTag.name = listTagName;
		listTags.push(listTag);
	}
	datas.push(data);
	return datas;
}

//获取新版查看视图的数据
function getNewViewData4m(){
	var datas = [];
	var data = {};
	var listOperations = [];//业务操作
	var listTags = [];//业务标签
	data.listOperations = listOperations;
	data.listTags = listTags;
	//业务操作
	var fdOperationNames = $("[name^='listOpers_mobile'][name$='fdOperationName']");
	for(var i=0; i<fdOperationNames.length; i++){
		var fdOperationName = $(fdOperationNames[i]).val() || "";
		var listOperation = {};
		listOperation.name = fdOperationName;
		listOperations.push(listOperation);
	}
	//业务标签
	var listTagNames = $("[name^='listTabs_mobile'][name$='fdName']");
	for(var i=0; i<listTagNames.length; i++){
		var listTagName = $(listTagNames[i]).val();
		var listTag = {};
		listTag.name = listTagName;
		listTags.push(listTag);
	}
	datas.push(data);
	return datas;
}

//切换选择位置
function switchSelectPosition(obj,direct,parentOrChild){
	Com_EventStopPropagation();
	$("[data-lui-position]").removeClass('active');
	$(".model-edit-view-content-top").removeClass("active");
	$(".model-edit-view-content-bottom").removeClass("active");
	$(".view-btn-border").removeClass("active");
	$(".tag-border").removeClass("active");
	var position = $(obj).attr("data-lui-position");
	$("[data-lui-position='"+position+"']").addClass("active");
	$("[data-lui-position='"+position.split("-")[0]+"']").addClass("active");
	$("[data-lui-position='"+position+"']").parents(".model-edit-view-content-top").addClass("active");
	$("[data-lui-position='"+position+"']").parents(".model-edit-view-content-bottom").addClass("active");
	$(".dots.active").removeClass("active");
	$("#moreList").hide();
	$("[data-lui-position='"+position+"']").parent(".view-btn-border").addClass("active");
	$("[data-lui-position='"+position+"']").parent(".tag-border").addClass("active");
	//进行滚轮处理
	if(direct=='left' && position){
		var panel = $("[data-lui-position='"+position+"']").parents(".model-edit-view-content").eq(0);
		var target = $(".model-edit-right").find("[data-lui-position='"+position+"']").eq(0);
		var scrollTop = target.offset().top - panel.offset().top + panel.scrollTop() - 50;
		panel.scrollTop(scrollTop)
	}
	lastSelectPostionObj = obj;
	lastSelectPostionDirect = direct;
	return false;
}

//双击编辑查看视图名称
function modifyFdName(element) {
	var oldhtml = element.innerHTML.replace(/\s*/g,"");
	var newobj = document.createElement('input');
	newobj.type = 'text';
	newobj.value = oldhtml;
	newobj.style = "border: 1px solid #dfdfdf;border-radius: 2px;height: 20px;color: #666666;";
	newobj.onblur = function() {
		element.innerHTML = this.value == oldhtml ? oldhtml : this.value;
		element.setAttribute("ondblclick", "modifyFdName(this);");
		$("[name='fdName']").val(this.value);
	}
	element.innerHTML = '';
	element.appendChild(newobj);
	newobj.setSelectionRange(0, oldhtml.length);
	newobj.focus();
	newobj.parentNode.setAttribute("ondblclick", "");
}



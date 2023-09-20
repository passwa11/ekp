var xform_main_data_insystem_validation = $KMSSValidation();
	
// 删除功能模板HTML
var xform_main_data_insystem_delTempHtml = "<a href='javascript:void(0);' onclick='xform_main_data_delTrItem(this);' style='color:#1b83d8;'>"+listviewOption.lang.deleteIt+"</a>";

// 上下移功能模板HTML
var xform_main_data_insystem_sortTempHtml = "<a href='javascript:void(0);' onclick='xform_main_data_moveTr(-1,this);' style='color:#1b83d8;'>"+listviewOption.lang.up+"</a>";
xform_main_data_insystem_sortTempHtml += "&nbsp;<a href='javascript:void(0);' onclick='xform_main_data_moveTr(1,this);' style='color:#1b83d8;'>"+listviewOption.lang.down+"</a>";

// 移动 -1：上移       1：下移
function xform_main_data_moveTr(direct,dom){
	var tb = $(dom).closest("table")[0];
	var $tr = $(dom).closest("tr");
	var curIndex = $tr.index();
	var lastIndex = tb.rows.length - 1;
	if(direct == 1){
		if(curIndex >= lastIndex){
			alert(listviewOption.lang.alreadyToDown);
			return;
		}
		$tr.next().after($tr);
	}else{
		if(curIndex < 2){
			alert(listviewOption.lang.alreadyToUp);
			return;
		}
		$tr.prev().before($tr);			
	}
}

// 控制列表列的显示和隐藏
function xform_main_data_subject_display(selectedItem,callback){
	if(!insystemContext.hasDictData()){
		return;
	}
	var flag = false;
	var $dom = $("div[name='xform_main_data_range']");
	var fdRangeRTF = $dom.find("input[name='fdRangeRTF']").val();
	var fdRangeRelation = $dom.find("input[name='fdRangeRelation']").val();
	if(fdRangeRTF == 'true' || fdRangeRelation == 'true'){
		flag = true;
	}
	var $tr = $("#xform_main_data_subject");
	if(flag){
		var $select = $tr.find("select");
		// 如果还没有构建select，则构建
		if($select.length == 0){
			xform_main_data_subject_init($tr,selectedItem);
		}
		$tr.show();
	}else{
		$tr.hide();
	}
	if(callback){
		callback($tr);
	}
}

function xform_main_data_subject_init($tr,selectedField){
	var html = "";
	html += xform_main_data_getFieldOptionHtml(insystemContext.strDictData,'fdRTFSubject','true',selectedField ? selectedField : (insystemContext.nameProperty ? insystemContext.nameProperty.field : ''));
	$tr.find("td:last").append(html);
}

//自定义校验方法
xform_main_data_insystem_validation.addValidator('myAlphanum',listviewOption.lang.fdKeyWaring,function(v, e, o){
	return this.getValidator('isEmpty').test(v) || !/\W/.test(v);
});

// 全局变量，存储当前model的信息
var insystemContext = new Insystem_Context();

//添加属性行
function xform_main_data_addAttrItem(tableId,datas,selectedItem,callback){
	if(!insystemContext.hasDictData()){
		alert(listviewOption.lang.chooseModuleFirst);
		return;
	}
	var $selectTable = $("#" + tableId);
	var $tr = $("<tr>");
	var html = "";
	//属性
	html += "<td>";
	html += xform_main_data_getFieldOptionHtml(datas,'fdAttrField','true',selectedItem == null ? null : selectedItem.field,selectedItem);
	html += "</td>";
	//删除
	html += "<td>"+ xform_main_data_insystem_delTempHtml +"</td>";
	$tr.append(html);
	$selectTable.append($tr);
	if(callback){
		callback($tr,selectedItem);
	}
}

//业务操作行删除
function xform_main_data_beforeDelOperationRow(){
	var index = getElemIndex();
	//获取当前行id和name
	var fdId = $("[name='listOperationIdArr["+index+"]']").val();
	var fdName = $("[name='listOperationNameArr["+index+"]']").val();
	
	var listOperationIds = $("[name='listOperationIds']").val();
	var listOperationNames = $("[name='listOperationNames']").val();
	//删除数据，进行两次替换，兼容第一个和非第一个
	listOperationIds = listOperationIds.replace(fdId+";","");
	listOperationNames = listOperationNames.replace(fdName+";","");
	listOperationIds = listOperationIds.replace(fdId,"");
	listOperationNames = listOperationNames.replace(fdName,"");
	$("[name='listOperationIds']").val(listOperationIds);
	$("[name='listOperationNames']").val(listOperationNames);
	$("[name='listOperationIds_last']").val(listOperationIds);
	$("[name='listOperationNames_last']").val(listOperationNames);
}

function getElemIndex(){
	var row = DocListFunc_GetParentByTagName("TR");
	var table = DocListFunc_GetParentByTagName("TABLE",row);
	for(var i=0; i<table.rows.length; i++){
		if(table.rows[i]==row){
			return i;
		}
	}
}

//增加查询条件(旧)
function xform_main_data_addWhereItem_old(selectedItem){
	if(!insystemContext.hasDictData()){
		alert(listviewOption.lang.chooseModuleFirst);
		return;
	}
	var $selectTable = $("#xform_main_data_whereTable");
	var html = "";
	html += "<tr>";
	//属性
	html += "<td>";
	html += xform_main_data_getFieldOptionHtml(insystemContext.filterDictData,'fdAttrField',null ,selectedItem == null ? null : selectedItem.field,selectedItem == null ? null : selectedItem);
	html += "</td>";
	//运算符
	html += "<td>";
	if(selectedItem){
		html += xform_main_data_getOperatorOptionHtml(selectedItem.fieldType,selectedItem.fieldOperator,selectedItem);
	}else{
		html += xform_main_data_getOperatorOptionHtml(insystemContext.dictData);	
	}
	
	html += "</td>";
	//值
	html += "<td>";
	if(selectedItem){
		html += xform_main_data_getFieldvalueOptionHtml(selectedItem.fieldType,selectedItem);	
	}else{
		html += xform_main_data_getFieldvalueOptionHtml(insystemContext.dictData);
	}
	
	html += "</td>";
	//操作
	html += '<td class="model-panel-child-table-item">' +
		'<p class="upbtn" alt="up">'+
		'<a href="javascript:void(0);" onclick="xform_main_data_moveTr(-1,this);" >'+listviewOption.lang.up+'</a>'+
		'</p>'+
		'<p class="downbtn" alt="down">'+
		'<a href="javascript:void(0);" onclick="xform_main_data_moveTr(1,this);">'+listviewOption.lang.down+'</a>'+
		'</p>'+
		'<p class="delbtn" alt="del"><a href="javascript:void(0);" onclick="xform_main_data_delTrItem(this);" >'+listviewOption.lang.deleteIt+'</a></p>'+
		'</td>';
	html += "</tr>";
	$selectTable.append(html);	
	//修改样式
	$selectTable.find("[name='fdAttrField']").css("width","80%");
	$selectTable.find("[name='fdWhereSelectFieldOperator']").css("width","80%");
	$selectTable.find("div.xform_main_data_fieldDomWrap").css("position","absolute");
}

//增加排序设置(旧）
function xform_main_data_addOrderbyItem_old(selectedItem){
	if(!insystemContext.hasDictData()){
		alert(listviewOption.lang.chooseModuleFirst);
		return;
	}
	var $selectTable = $("#xform_main_data_orderbyTable");
	var html = "";
	html += "<tr>";
	//字段
	html += "<td class='model-panel-child-table-title'>";
	html += xform_main_data_getFieldOptionHtml(insystemContext.filterDictData,'fdAttrField',null ,selectedItem == null ? null : selectedItem.field,selectedItem == null ? null : selectedItem);
	html += "</td>";
	//排序
	var ascSelected;
	var descSelected;
	if(selectedItem && selectedItem.hasOwnProperty('orderType') && selectedItem.orderType == 'desc'){
		descSelected = "selected";
	}else{
		ascSelected = "selected";
	}
	html += "<td class='model-panel-child-table-title'><select name='fdOrderType' type='checkbox' class='inputsgl' style='margin:0 4px'>";
	html += "<option value='asc' " + ascSelected + ">" + listviewOption.lang.asc + "</option>";
	html += "<option value='desc'" + descSelected + ">" + listviewOption.lang.desc + "</option>";
	html += "</select></td>";
	//操作
	html += '<td class="model-panel-child-table-item">' +
		'<p class="upbtn" alt="up">'+
		'<a href="javascript:void(0);" onclick="xform_main_data_moveTr(-1,this);">'+listviewOption.lang.up+'</a>'+
		'</p>'+
		'<p class="downbtn" alt="down">'+
		'<a href="javascript:void(0);" onclick="xform_main_data_moveTr(1,this);">'+listviewOption.lang.down+'</a>'+
		'</p>'+
		'<p class="delbtn" alt="del"><a href="javascript:void(0);" onclick="xform_main_data_delTrItem(this);" >'+listviewOption.lang.deleteIt+'</a></p>'+
		'</td>';
	html += "</tr>";
	$selectTable.append(html);	
	//修改样式
	$selectTable.find("[name='fdAttrField']").css("width","80%");
	$selectTable.find("[name='fdOrderType']").css("width","80%");
}

//选择模块后的回调函数
function xform_main_data_setAttr(value){
	if(value){
		// 设置属性
		try{
			var data = $.parseJSON(value);
			xform_main_data_setGlobal(data);
		}catch(e){
			alert(listviewOption.lang.lookLog);
		}
		xform_main_data_initAfterSetModel();
	}
}

// 在设置确定完model之后，初始化页面
function xform_main_data_initAfterSetModel(){
	xform_main_data_emptyAllTr();
	// 默认添加上id和name，兼容历史数据
	xform_main_data_addSelectBlockIdAndName();
	// 处理rtf标题列隐藏和显示
	xform_main_data_subject_display(null,xform_main_data_subject_delSelect);
}

function xform_main_data_subject_delSelect($tr){
	$tr.find("select").remove();
	xform_main_data_subject_init($tr);
}

//设置权限js变量的数据字典变量和权限变量
function xform_main_data_setGlobal(data){
	insystemContext.clear();
	insystemContext.initialize(data);
}

// 封装数据并提交
function Listview_PackageData(form,method,callBack) {
//处理业务操作
	var listOperationIdArr = "";
	$("input[name^='listOperationIdArr[']").each(function () {
		listOperationIdArr += $(this).val() + ";";
	});
	$("input[name='listOperationIds']").val(listOperationIdArr);
	$("input[name='fdOperOrder']").val(listOperationIdArr);
	var listOperationNameArr = "";
	$("input[name^='listOperationNameArr[']").each(function () {
		listOperationNameArr += $(this).val() + ";";
	});
	$("input[name='listOperationNames']").val(listOperationNameArr);
	$("input[name='fdOperNameOrder']").val(listOperationNameArr);

	//处理显示项样式
	var displayCssSetConfig = {};
	if(typeof displayCssSetIns != "undefined"){
		displayCssSetConfig = displayCssSetIns.getKeyData();
	}
	$("[name='fdDisplayCssSet']").val(JSON.stringify(displayCssSetConfig));
	
	//处理查询条件
	var $selectTr = $(".model-query-content").find("tr:not(.xform_main_data_tableTitle)");
	var selectArray = [];
	for(var i = 0;i < $selectTr.length; i++){
		var tr = $selectTr[i];
		selectArray.push(xform_main_data_detailSelectWhere(tr));
	}
	//处理排序设置
	$selectTr = $("#xform_main_data_orderbyTable").find("tr:not(.xform_main_data_tableTitle)");
	var orderbyArray = [];
	for(var i = 0;i < $selectTr.length; i++){
		var tr = $selectTr[i];
		orderbyArray.push(xform_main_data_detailOrderbyWhere(tr));
	}

	//保存时，"筛选项"将过滤"预定义查询"中已使用的字段
	if (selectArray && selectArray.length > 0) {
		var fdCondition = listviewOption.modelingAppListviewForm.fdCondition;
		var startStr = "\\[";
		var endStr = "\\]";
		var startReg =new RegExp("^"+startStr);
		var endReg = new RegExp(endStr+"$");
		if (fdCondition && startReg.test(fdCondition) && endReg.test(fdCondition)) {
			var resultFdConditionArray = [];
			var fdConditionArray = JSON.parse(fdCondition);
			for (var i in fdConditionArray) {
				var condition = fdConditionArray[i];
				var isInWhereBlock = false;
				for (var j in selectArray) {
					var where = selectArray[j];
					if(where.whereType === '1')
						continue;
					//根据字段名判断,WhereBlock中格式为"field":"fd_382c69377ea860|fdId"，condition中格式为"field":"fd_382c69377ea860"
					if (where.field.indexOf(condition.field) !== -1) {
						isInWhereBlock = true;
						break;
					}
				}
				if (!isInWhereBlock) {
					resultFdConditionArray.push(condition);
				}
			}
			//提示
			if(fdConditionArray.length !== resultFdConditionArray.length){
				seajs.use(["lui/dialog"], function(dialog) {
					dialog.confirm("\"筛选项\"选择了\"查询条件\"中已使用的字段，保存时将被过滤，是否继续？", function (value) {
						if (value === true) {
							listviewOption.modelingAppListviewForm.fdCondition = JSON.stringify(resultFdConditionArray);
							setFormHiddenElementsValue(selectArray, orderbyArray);
							callBack(form, method);
						}
					});
				});
				return false;
			}
		}
	}
	
	setFormHiddenElementsValue(selectArray, orderbyArray);
	if(callBack){
		callBack(form, method);
	}
	return false;
}
function Listview_Submit(form, method){
	//处理业务操作
	var pdFlag = Listview_PackageData(form,method,Com_Submit)
	if (pdFlag ){
		Com_Submit(form, method);
	}
}

function setFormHiddenElementsValue(selectArray, orderbyArray){
	$("input[name='fdWhereBlock']").val(JSON.stringify(selectArray));
	$("input[name='fdOrderBy']").val(JSON.stringify(orderbyArray));
	$("input[name='fdCondition']").val(listviewOption.modelingAppListviewForm.fdCondition);
	$("input[name='fdDisplay']").val(listviewOption.modelingAppListviewForm.fdDisplay);
}

//提交时处理搜索设置
function xform_main_data_detailOrderbyWhere(tr){
	var valueJSON = {};
	//处理查询属性
	var attrOptions = $(tr).find("select[name='fdAttrField']");
	var fieldVal = "";
	var fieldType = "";
	for(var i = 0;i < attrOptions.length;i++){
		var fieldOption = $(attrOptions[i]).find("option:selected");
		var ty = fieldOption.attr('data-type');
		fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
		fieldType += ty + _xform_main_data_insystem_split;
	}
	valueJSON.field = fieldVal.substring(0,fieldVal.length - 1);
	valueJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
	// 处理显示值
	var show = $(tr).find("select[name='fdOrderType']");
	if(show.length > 0){
		valueJSON.orderType = show.val();
	}
	return valueJSON;
}

Com_AddEventListener(window, 'load', xform_main_data_initVar);

//初始化数据，主要用于edit编辑页面
function xform_main_data_initVar(){	
	//触发业务模块的下拉框变动事件
	$("select[name='fdModelId']").each(function() {
		modelChange(this.value, this, true);
	});
	
	//初始化数据字典变量
	var dictData = listviewOption.modelingAppListviewForm.modelDict;
	dictData = dictData.replace(/&quot;/g,"\"");
	if(dictData){
		var dictJSON = $.parseJSON(getValidJSONArray(dictData));
		xform_main_data_setGlobal(dictJSON);
		//处理预定义查询
		var whereData = listviewOption.modelingAppListviewForm.fdWhereBlock;
		var whereDataJsonArray = $.parseJSON(getValidJSONArray(whereData));
		//遍历预定义查询数据
		// 把查询条件按类型做分组
		// 按照分组调用新增行方法
		var predefineArr = [];
		var sysArr = [];
		var $predefineTable = $(".model-query-content-cust").find(".model-edit-view-oper-content-table");
		var $sysTable = $(".model-query-content-sys").find(".model-edit-view-oper-content-table");
		for(var i = 0;i < whereDataJsonArray.length;i++){
			if($.isEmptyObject(whereDataJsonArray[i])){
				continue;
			}
			//查询条件地址本字段单选变多选后不做渲染
			if(dictData.indexOf(whereDataJsonArray[i].field.split("|")[0]) <= -1){
				continue;
			}
			var whereType = whereDataJsonArray[i].whereType;
			if(whereType == '0'){
				predefineArr.push(whereDataJsonArray[i]);
			}else if(whereType == '1'){
				sysArr.push(whereDataJsonArray[i]);
			}
			//xform_main_data_addWhereItem(whereDataJsonArray[i], true);
		}
		for(var i = 0;i < predefineArr.length;i++){
			xform_main_data_addWhereItem(predefineArr[i], $($predefineTable),'0');
		}
		
		for(var i = 0;i < sysArr.length;i++){
			xform_main_data_addWhereItem(sysArr[i], $($sysTable),'1');
		}
		//处理排序设置
		var orderbyData = listviewOption.modelingAppListviewForm.fdOrderBy;
		var orderbyDataJsonArray = $.parseJSON(getValidJSONArray(orderbyData));
		for(var i = 0;i < orderbyDataJsonArray.length;i++){
			if($.isEmptyObject(orderbyDataJsonArray[i])){
				continue;
			}
			xform_main_data_addOrderbyItem(orderbyDataJsonArray[i], true);
		}
	}
	xform_main_data_custom_enumChangeEvent("xform_main_data_returnValueTable");
	xform_main_data_custom_enumChangeEvent("xform_main_data_searchTable");
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
 * 从allDatas中找到datas的对应显示文字，并返回数组
 * @param datas
 * @param allDatas
 * @returns
 */
function getSelectText(datas, allDatas){
	var text = [];
	for(i in datas){
		for(j in allDatas){
			if(datas[i].field == allDatas[j].field){
				text.push(allDatas[j].text);
				break;
			}
		}
	}
	return text;
}

function getOptionHtmlByDatas(datas, dictJSON){
	var optioinHtml = "";
	if(datas){
		var dataArr = datas.split(";");
		for(var i in dictJSON){
			if(dataArr.includes(dictJSON[i].field)){
				optioinHtml += "<option value=\"" + dictJSON[i].field + "\">" + dictJSON[i].fieldText + "</option>";
			}
		}
	}
	return optioinHtml;
}

// 增加id和名称行
function xform_main_data_addSelectBlockIdAndName(arr){
	if(insystemContext.idProperty){
		var pro = insystemContext.idProperty;
		// 找到对应的列，取出来
		if(arr){
			pro = xform_main_data_delSameProInArr(pro,arr);
		}
	}
	if(insystemContext.nameProperty){
		var pro = insystemContext.nameProperty;
		if(arr){
			pro = xform_main_data_delSameProInArr(pro,arr);
		}
	}
}

// 删除arr里面跟pro相同的值
function xform_main_data_delSameProInArr(pro,arr){
	var index;
	for(var i = 0;i < arr.length;i++){
		if(pro.field == arr[i].field){
			pro = arr[i];
			index = i;
			break;
		}
	}
	if(typeof(index) != 'undefined'){
		arr.splice(index,1);	
	}
	return pro;
}

// 调整id和name行的样式
function xform_main_data_setSelectDisabled($tr,selectedItem){
	var color = "#848080";
	var select = $tr.find("select");
	select.css('color',color);
	select.attr('disabled','disabled');
	var lastTd = $tr.find('td:last');
	lastTd.empty();
}

/**********************
 * 模块变更相关页面联动 start
 **********************/
function replaceCateid(url, cateid) {
	var re = /!\{cateid\}/gi;
	url = url.replace(/!\{cateid\}/gi, cateid);
	return url;
}
function replaceKey(url, key) {
	var re = /!\{key\}/gi;
	url = url.replace(/!\{key\}/gi, key);
	return url;
}

/**
 * 修改业务模块时触发
 */
function modelChange(v, o, isInit) {
	//删除现有选择
	if(!isInit){
		xform_main_data_emptyAllTr();
		listviewOption.modelingAppListviewForm.fdCondition = null;
		$("input[name='fdConditionText']").val("");
		listviewOption.modelingAppListviewForm.fdDisplay = null;
		$("input[name='fdDisplayText']").val("");
		if(!v){
			return;
		}
	}
	var dictBeanValue = "modelingAppListviewModelDictService&key=!{key}&fdAppModelId=!{cateid}";
	dictBeanValue = decodeURIComponent(dictBeanValue);
	dictBeanValue = replaceCateid(dictBeanValue, v);
	dictBeanValue = replaceKey(dictBeanValue, "modelingApp");
	loadDictProperties(dictBeanValue);
}

function loadDictProperties(url) {
	var kmssData = new KMSSData();
	var datas = kmssData.AddBeanData(url).GetHashMapArray();

	if(datas[0] && datas[0].key0){
		//预定义查询、排序项
		xform_main_data_setAttr(datas[0].key0);
		//数据字典
		listviewOption.modelingAppListviewForm.modelDict = datas[0].key0;
	}
	if(datas[1] && datas[1].key0){
		listviewOption.modelingAppListviewForm.fdModelName = datas[1].key0;
		var fdModelName = document.getElementsByName('fdModelName')[0];
		if(fdModelName)
			fdModelName.value = datas[1].key0;
	}
	return;
}
/**********************
 * 模块变更相关页面联动 end
 **********************/

/**
 * 选择弹框
 */
seajs.use([ 'sys/ui/js/dialog' ,'lui/topic'],function(dialog,topic) {
	window.selectDisplay= function(thisObj){
		//Com_EventStopPropagation();
		//切换样式
		switchSelectPosition(thisObj,'right','fdDisplay');
		topic.publish("switch_select_status",{'position':'fdConditions'});
		var url='/sys/modeling/base/listview/config/dialog.jsp?type=display';
		var modelingAllFild=listviewOption.modelingAppListviewForm.allField;
		//转义换行符
		modelingAllFild = modelingAllFild.replace(/\n/g,"\\\\n");
		//转义mac \r\n换行符
		modelingAllFild = modelingAllFild.replace(/\r/g,"\\\\r");
		//过滤明细表的属性
		modelingAllFild = JSON.stringify(window.filterSubTableField(modelingAllFild));
		//modelingAllFild=modelingAllFild.replace("docCreator.fdName","docCreator");

		dialog.iframe(url,listviewOption.lang.selectDisplay,function(data){
			if(!data){
				return; 
			}
			data=data.replace("docCreator.fdName","docCreator");
			data = $.parseJSON(data);
			//回调
			if(JSON.stringify(data.selected).length >= 3000){
				dialog.confirm("显示项所选字段太多，请重新选择");
			}else{
				listviewOption.modelingAppListviewForm.fdDisplay = JSON.stringify(data.selected);
				$("input[name='fdDisplayText']").val(data.text.join(";"));
				//显示项样式改变事件
				topic.publish("modeling.selectDisplay.change",{'thisObj':thisObj,'data':data});
				
				//更新预览
				topic.publish("preview.refresh");
			}
			
		},{
			width : 900,
			height : 500,
			params : {
				selected : listviewOption.modelingAppListviewForm.fdDisplay,
				allField : modelingAllFild,
				modelDict : listviewOption.modelingAppListviewForm.modelDict
			}
		});
	}
	
	/*过滤明细表的字段*/
	window.filterSubTableField = function(fields){
		var allField = fields || [];
		if(typeof allField === 'string'){
			allField = $.parseJSON(fields);
		}
		var newAllField = [];
		for(var i=0; i<allField.length; i++){
			var field = allField[i];
			if(field.isSubTableField){
				continue;
			}
			newAllField.push(field);
		}
		return newAllField;
	}
	
	window.selectCondition= function(thisObj){
		//Com_EventStopPropagation();
		//切换样式
		switchSelectPosition(thisObj,'right','fdCondition');
		var url='/sys/modeling/base/listview/config/dialog.jsp?type=condition';
		dialog.iframe(url,listviewOption.lang.selectCondition,function(data){
			if(!data){
				return;
			}
			//#120818:筛选项选择创建者不回显
			data=data.replace("docCreator.fdName","docCreator");
			data = $.parseJSON(data);
			//回调
			if(JSON.stringify(data.selected).length >= 3000){
				dialog.confirm("筛选项所选字段太多，请重新选择");
			}else{
				listviewOption.modelingAppListviewForm.fdCondition = JSON.stringify(data.selected);
				$("input[name='fdConditionText']").val(data.text.join(";"));
				//更新预览
				topic.publish("preview.refresh");
			}
		},{
			width : 900,
			height : 500,
			params : {
				selected : listviewOption.modelingAppListviewForm.fdCondition,
				allField : listviewOption.modelingAppListviewForm.allField,
				modelDict : listviewOption.modelingAppListviewForm.modelDict
			}
		});
	}
	//增加业务操作
	window.xform_main_data_addOperation = function(optTb){
		DocList_AddRow(optTb);
		//更新角标
		var index = $('#'+optTb).find("> tbody > tr").last().find(".title-index").text();
		$('#'+optTb).find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$('#'+optTb).find("> tbody > tr").last().prev("tr").find("div.down").show();
		//刷新预览
		topic.publish("preview.refresh");
		//渲染之后出发点击
		$('#'+optTb).find("> tbody > tr").last().find(".model-edit-view-oper").click();
	}
	
	//增加排序设置
	window.xform_main_data_addOrderbyItem = function(selectedItem){
		console.log("ddd",listviewOption);
		if(!insystemContext.hasDictData()){
			alert(listviewOption.lang.chooseModuleFirst);
			return;
		}
		var dictData = window.filterSubTableField(insystemContext.filterDictData);
		dictData = window.filterOrderByField(dictData);
		var $selectTable = $("#xform_main_data_orderbyTable");
		var rowIndex = $selectTable.find("tr.orderbyTr").length+1;
		var trObj = $("<tr class='orderbyTr'>");
		var html = "";
		html += "<td><div class='model-edit-view-oper' data-lui-position='fdOrderBy-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')>";
		//头部
		html += "<div class='model-edit-view-oper-head'>";
		html += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div><span>排序项<span class='title-index'>"+(rowIndex-1)+"</span></span></div>";
		html += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,null,this);xform_main_data_delTrItem(this);updateRowAttr()'><i></i></div><div class='down' onclick='xform_main_data_moveTr_new(1,this);updateRowAttr(1,null,this)'><i></i></div><div class='up' onclick='xform_main_data_moveTr_new(-1,this);updateRowAttr(-1,null,this)'><i></i></div></div>";
		html += "</div>";
		//内容
		html += "<div class='model-edit-view-oper-content'>";
		html += "<ul>";
		//字段
		html += "<li class='model-edit-view-oper-content-item first-item'><div class='item-title'>"+listviewOption.lang.field+"</div>";
		html += "<div class='item-content'>";
		var field = selectedItem == null ? null : selectedItem.field;
		if (field && field.indexOf("|") !== -1) {
			field = field.substring(0, field.indexOf("|"));
		}
		html += xform_main_data_getFieldOptionHtml(dictData,'fdAttrField', 'true', field,selectedItem == null ? null : selectedItem,true);
		html += "</div></li>";
		//排序
		var ascSelected;
		var descSelected;
		if(selectedItem && selectedItem.hasOwnProperty('orderType') && selectedItem.orderType == 'desc'){
			descSelected = "selected";
		}else{
			ascSelected = "selected";
		}
		html += "<li class='model-edit-view-oper-content-item last-item'><div class='item-title'>"+listviewOption.lang.fdOutSort+"</div>";
		html += "<div class='item-content'><select name='fdOrderType' type='checkbox' class='inputsgl' style='margin:0 4px;width:100%'>";
		html += "<option value='asc' " + ascSelected + ">" + listviewOption.lang.asc + "</option>";
		html += "<option value='desc'" + descSelected + ">" + listviewOption.lang.desc + "</option>";
		html += "</select></div></li>";
		html += "</ul>";
		html += "</div></div></td>";
		trObj.append(html);
		$selectTable.append(trObj);
		//更新角标
		var index = $selectTable.find("> tbody > tr").last().find(".title-index").text();
		$selectTable.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$selectTable.find("> tbody > tr").last().prev("tr").find("div.down").show();
		$(trObj).find("div.down").hide();
		//修改默认标题
		var fdAttrFieldId = $(trObj).find("[name='fdAttrField']").val();
		var fdAttrFieldText = $(trObj).find("[name='fdAttrField']").find("option[value='"+fdAttrFieldId+"']").text();
		$selectTable.find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(fdAttrFieldText);
		//刷新预览
		topic.publish("preview.refresh");
		//渲染之后出发点击
		$(trObj).find(".model-edit-view-oper").click();
	}

	/*过滤排序的字段*/
	window.filterOrderByField = function(fields){
		var allField = fields || [];
		if(typeof allField === 'string'){
			allField = $.parseJSON(fields);
		}
		var newAllField = [];
		for(var i=0; i<allField.length; i++){
			var field = allField[i];
			//组织架构字段过滤（不包含创建者）
			if(field.fieldType && field.fieldType.indexOf("com.landray.kmss.sys.organization.model") !== -1 && field.field !== "docCreator") {
				//只可选择不为空的字段
				if(field.isNotNull === undefined || field.isNotNull === null || field.isNotNull === false){
					console.log("非必填：");
					console.log(field);
					continue;
				}
				//不可选择多选的字段
				if(field.isMutiValue !== undefined && field.isMutiValue != null && field.isMutiValue === true){
					console.log("多选：");
					console.log(field);
					continue;
				}
			}
			newAllField.push(field);
		}
		return newAllField;
	}

	/**
	 * 绘制预定义查询-内置类型的html
	 */
	function getPredefinedWhereTypeHtml(selectedItem, show) {
		var value = '';
		if(selectedItem)
			value = selectedItem.predefined;
		var clazz = show ? '' : 'liHidden';
		var html = "";
		html += "<li class='model-edit-view-oper-content-item first-item last-item "+clazz+"' name='predefinedLi'><div class='item-title'>内置查询</div>";
		html += "<div class='item-content'>";
		//下拉框
		html += "<select name='fdPredefinedWhereType' class='inputsgl' onchange='changePredefined(this)' style='margin:0 4px;width:100%'>";

		var selectArray = xform_main_data_getEnumType("predefindedWhereType");
		for(var i = 0;i < selectArray.length; i++){
			var json = selectArray[i];
			if(json.value){
				//无流程文档只可选择'我创建的'
				if(listviewOption.modelingAppListviewForm.fdEnableFlow !== 'true' && json.value !== "create") {
					continue;
				}
				html += "<option value='" + json.value + "'";
				if(value && value === json.value){
					html += " selected='selected'";
				}
				html += ">" + json.text + "</option>";
			}
		}
		html += "</select>";
		html += "</div></li>";
		return html;
	}

	/**
	 * 绘制预定义查询-自定义类型的html
	 */
	function getCustomWhereTypeHtml(selectedItem, show) {
		var clazz = show ? "" : "liHidden";
		var html = "";
		//过滤明细表的属性
		var dictData = window.filterSubTableField(insystemContext.filterDictData);
		//字段
		html += "<li class='model-edit-view-oper-content-item first-item "+clazz+"'><div class='item-title'>"+listviewOption.lang.field+"</div>";
		html += "<div class='item-content'>";
		html += xform_main_data_getFieldOptionHtml(dictData, 'fdAttrField', null, selectedItem == null ? null : selectedItem.field, selectedItem == null ? null : selectedItem);
		html += "</div></li>";
		//运算符
		html += "<li class='model-edit-view-oper-content-item "+clazz+"'><div class='item-title'>"+listviewOption.lang.fdOperator+"</div>";
		html += "<div class='item-content'>";
		if (selectedItem) {
			html += xform_main_data_getOperatorOptionHtml(selectedItem.fieldType, selectedItem.fieldOperator, selectedItem);
		} else {
			html += xform_main_data_getOperatorOptionHtml(insystemContext.dictData);
		}
		html += "</div></li>";
		//值
		html += "<li class='model-edit-view-oper-content-item last-item "+clazz+"'><div class='item-title'>"+listviewOption.lang.fdValue+"</div>";
		html += "<div class='item-content'>";
		if (selectedItem) {
			html += xform_main_data_getFieldvalueOptionHtml(selectedItem.fieldType, selectedItem);
		} else {
			html += xform_main_data_getFieldvalueOptionHtml(insystemContext.dictData);
		}
		html += "</div></li>";
		return html;
	}
	
//增加预定义查询
	window.xform_main_data_addWhereItem = function(selectedItem,currentDom, type){
		if(!insystemContext.hasDictData()){
			alert(listviewOption.lang.chooseModuleFirst);
			return;
		}
		var $selectTable = $(currentDom).closest(".model-query-cont").find(".model-edit-view-oper-content-table");
		var rowIndex = $selectTable.find("tr.whereTr").length+1;
		var trObj = $("<tr class='whereTr'>");
		var html = "";
		html += "<td><div class='model-edit-view-oper'>";
		//头部
		html += "<div class='model-edit-view-oper-head'>";
		html += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div><span>排序项<span class='title-index'>"+(rowIndex-1)+"</span></span></div>";
		html += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,\"no-position\",this);xform_main_data_delTrItem(this);'><i></i></div><div class='down' onclick='xform_main_data_moveTr_new(1,this);updateRowAttr(1,\"no-position\",this)'><i></i></div><div class='up' onclick='xform_main_data_moveTr_new(-1,this);updateRowAttr(-1,\"no-position\",this)'><i></i></div></div>";
		html += "</div>";
		//内容
		html += "<div class='model-edit-view-oper-content'>";
		html += "<ul>";
		//查询类型
		var whereType = (selectedItem && selectedItem.whereType) ? selectedItem.whereType : '0';
		var showPredefined = type === '1';
		html += getWhereTypeHtml(type);
		html += getCustomWhereTypeHtml(selectedItem, !showPredefined);
		html += getPredefinedWhereTypeHtml(selectedItem, showPredefined);
		html += "</ul>";
		html += "</div></div></td>";
		trObj.append(html);
		$selectTable.append(trObj);
		var valueType = selectedItem && selectedItem.fieldValue ? selectedItem.fieldValue:"";
		updateOperatorTdByFieldValue(trObj,valueType);
		//控制 内置查询项 与 预定义查询项 的显示
		$selectTable.find('.model-edit-view-oper-content-item').each(function () {
			var name = $(this).attr('name');
			if(name === 'whereTypeLi')
				return;
			if ((name === 'predefinedLi' && type === '1')
				|| (name !== 'predefinedLi' && type === '0'))
				$(this).removeClass("liHidden");
			else
				$(this).addClass("liHidden");
		});
		//更新角标
		var index = $selectTable.find("> tbody > tr").last().find(".title-index").text();
		$selectTable.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
		//更新向下的图标
		$selectTable.find("> tbody > tr").last().prev("tr").find("div.down").show();
		$(trObj).find("div.down").hide();
		//修改默认标题
		if(showPredefined)
			changeUlTitle($(trObj).find('select[name="fdPredefinedWhereType"]'));
		else
			changeUlTitle($(trObj).find('select[name="fdAttrField"]'));
	}
	//业务操作弹框
	window.xform_main_data_operationDialog = function(){
		var index = getElemIndex();
		//业务操作弹框回调
		var action = function(rtnData){
			var listOperationIdLasts = $("[name='listOperationIds_last']").val() || "";
			var listOperationNameLasts = $("[name='listOperationNames_last']").val() || "";
			var oprIdArr = listOperationIdLasts.split(";");
			var oprNameArr = listOperationNameLasts.split(";");
			var selectedOprLastIndex = oprIdArr.length - 1;
			var fdId = rtnData[0].fdId;
			var fdName = rtnData[0].fdName;
			if(fdId && listOperationIdLasts.indexOf(fdId) == -1 ){
				if(index > selectedOprLastIndex){
					if(listOperationIdLasts.lastIndexOf(";") != listOperationIdLasts.length-1){
						listOperationIdLasts += ";" + fdId + ";";
						listOperationNameLasts += ";" + fdName + ";";
					}else{
						listOperationIdLasts += fdId + ";";
						listOperationNameLasts += fdName + ";";
					}
				}else{
					oprIdArr[index] = fdId;
					oprNameArr[index] = fdName;
					listOperationIdLasts = oprIdArr.join(";");
					listOperationNameLasts = oprNameArr.join(";");
				}
				//处理显示内容
				$("p.listOperationName").eq(index).text(fdName);
				$("[name='listOperationIdArr["+index+"]']").val(fdId);
				$("[name='listOperationNameArr["+index+"]']").val(fdName);
				$("[name='listOperationIdArr["+index+"]']").closest("td").find(".validation-advice").css("display","none");
				//替换head标题
				$(".model-edit-view-oper-head-title").eq(index).find("span").text(fdName);
			}
			$("[name='listOperationIds']").val(listOperationIdLasts);
			$("[name='listOperationNames']").val(listOperationNameLasts);
			$("[name='listOperationIds_last']").val(listOperationIdLasts);
			$("[name='listOperationNames_last']").val(listOperationNameLasts);
			//更新标题
			$("#operationTable").find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(fdName);
			//刷新预览
			topic.publish("preview.refresh");
		}
		var exceptValue = $("[name='listOperationIds_last']").val() || "";
		var currentValue = $("[name='listOperationIdArr["+index+"]']").val();
		if(currentValue){
			exceptValue = exceptValue.replace(currentValue+";","");
			exceptValue = exceptValue.replace(currentValue,"");
		}
		dialogSelect(false,'sys_modeling_operation_selectListviewOperation','listOperationIds','listOperationNames', action, exceptValue);
	}
}); 

/**
 * 业务操作对话框
 * 
 * exceptValue 需要排除的值，格式为字符串id;id
 */
function dialogSelect(mul, key, idField, nameField, action, exceptValue){
	var rowIndex;
	if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
		var tr=DocListFunc_GetParentByTagName('TR');
		var tb= DocListFunc_GetParentByTagName("TABLE");
		var tbInfo = DocList_TableInfo[tb.id];
		rowIndex=tr.rowIndex-tbInfo.firstIndex;
	}
	var dialogCfg = listviewOption.dialogs[key];
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
							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
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
							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
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
		params=encodeURI(params);
		var tempUrl = 'sys/modeling/base/resources/jsp/dialog_select_template.jsp?dialogType=opener&modelName=' + dialogCfg.modelName + '&_key=dialog_' + idField + '&exceptValue='+exceptValue;
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
			var datas = rtnInfo.data;
			var rtnDatas=[],ids=[],names=[];
			for(var i=0;i<datas.length;i++){
				var rowData = domain.toJSON(datas[i]);
				rtnDatas.push(rowData);
				ids.push($.trim(rowData[rtnInfo.idField]));
				names.push($.trim(rowData[rtnInfo.nameField]));
			}
			if(idField.indexOf('*')>-1){
				//明细表
				$form(idField,idField.replace("*",rowIndex)).val(ids.join(";"));
				$form(nameField,idField.replace("*",rowIndex)).val(names.join(";"));
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

function getDialogInputs(idField){
	var dialogLinks=listviewOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.inputs;
		}
	}
	return null;
}
function getDialogOutputs(idField){
	var dialogLinks=listviewOption.dialogLinks;
	if(dialogLinks==null||dialogLinks.length==0){
		return null;
	}
	for(var i=0;i<dialogLinks.length;i++){
		var dialogLink=dialogLinks[i];
		if(idField==dialogLink.idField){
			return dialogLink.outputs;
		}
	}
	return null;
}
//获取数据
function getListViewData(){
	var datas = [];
	var data = {};
	var fdConditions = [];//筛选项
	var fdOrderBys = [];//排序项
	var listOperations = [];//业务操作
	var fdDisplays = [];//显示项
	data.fdConditions = fdConditions;
	data.fdOrderBys = fdOrderBys;
	data.listOperations = listOperations;
	data.fdDisplays = fdDisplays;
	
	//筛选项
	var fdConditionTexts = ($("[name='fdConditionText']").val() || "").split(";") || [];
	for(var i=0; i<fdConditionTexts.length; i++){
		if(fdConditionTexts[i]){
			var fdCondition = {};
			fdCondition.text = fdConditionTexts[i];
			fdConditions.push(fdCondition);
		}
	}
	//排序项
	var $orderbyTrs = $("#xform_main_data_orderbyTable").find("tr");
	for(var i=0; i<$orderbyTrs.length; i++){
		var fdAttrFieldId = $($orderbyTrs[i]).find("[name='fdAttrField']").val();
		var fdAttrFieldText = $($orderbyTrs[i]).find("[name='fdAttrField']").find("option[value='"+fdAttrFieldId+"']").text();
		var fdOrderBy = {};
		var fdAttrField = {};
		fdAttrField.text = fdAttrFieldText;
		fdOrderBy.fdAttrField = fdAttrField;
		fdOrderBys.push(fdOrderBy);
	}
	var listOperationNameObjs = $("[name^='listOperationNameArr']");
	for(var i=0; i<listOperationNameObjs.length; i++){
		var listOperationNameObj = listOperationNameObjs[i];
		var listOperationName = $(listOperationNameObj).val() || "";
		var listOperation = {};
		listOperation.name = listOperationName;
		listOperations.push(listOperation);
	}
	//显示项
	var fdDisplayTexts = ($("[name='fdDisplayText']").val() || "").split(";") || [];
	for(var i=0; i<fdDisplayTexts.length; i++){
		if(fdDisplayTexts[i]){
			var fdDisplay = {};
			fdDisplay.text = fdDisplayTexts[i];
			fdDisplays.push(fdDisplay);
		}
	}
	
	datas.push(data);
	return datas;
}
//切换选择位置
function switchSelectPosition(obj,direct,toPosition){
	Com_EventStopPropagation();
	$("[data-lui-position]").removeClass('active');
	$(".model-edit-view-content-top").removeClass("active");
	$(".model-edit-view-content-bottom").removeClass("active");
	$("[data-lui-position='fdCondition']").parents("div[onclick]").eq(0).removeClass("active");
	$("[data-lui-position='fdDisplay']").parents("div[onclick]").eq(0).removeClass("active");
	$(".dots.active").removeClass("active");
	$("#moreList").hide();
	var position = $(obj).attr("data-lui-position");
	if(!position){
		$(obj).addClass("active");
		$('[data-lui-position="'+toPosition+'"]').addClass("active");
	}else{
		$("[data-lui-position='"+position+"']").addClass("active");
		//$("td[data-lui-position='"+position.split("-")[0]+"']").addClass("active");
		$("[data-lui-position='"+position.split("-")[0]+"']").addClass("active");
		$("[data-lui-position='"+position+"']").parents(".model-edit-view-content-top").addClass("active");
		$("[data-lui-position='"+position+"']").parents(".model-edit-view-content-bottom").addClass("active");
		$("[data-lui-position='"+position+"']").parents(".model-edit-view-content-center-wrap").addClass("active");
	}
	//进行特殊处理
	if(position && position.split("-")[0] == 'fdCondition'){
		//搜索项
		$("[data-lui-position='"+position.split("-")[0]+"']").parents("div[onclick]").eq(0).addClass("active");
	}else if(position && position.split("-")[0] == 'fdDisplay'){
		//显示项
		$("[data-lui-position='"+position.split("-")[0]+"']").parents("div[onclick]").eq(0).addClass("active");
	}
	//进行滚轮处理
	if(direct=='left' && position){
		//#138943
		if (position.indexOf("-")){
			positionArr = position.split("-");
			position = positionArr[0];
		}
		var panel = $("[data-lui-position='"+position+"']").parents(".model-edit-view-content").eq(0);
		var target = $(".model-edit-right").find("[data-lui-position='"+position+"']").eq(0);
		var scrollTop = target.offset().top - panel.offset().top + panel.scrollTop() - 50;
		panel.scrollTop(scrollTop)
	}
	lastSelectPostionObj = obj;
	lastSelectPostionDirect = direct;
	lastSelectPosition = toPosition;
	return false;
}
//返回列表页面
function returnListPage(type){
	var url = listviewOption.param.contextPath+'sys/modeling/base/listview/config/index_body.jsp?fdModelId='+listviewOption.param.fdModelId+'&method=edit';
	if(type == "mobile"){
		url =  listviewOption.param.contextPath+'sys/modeling/base/mobile/viewDesign/listView/config/index_body.jsp?fdModelId='+listviewOption.param.fdModelId+'&method=edit&actionUrl=/sys/modeling/base/mobile/modelingAppMobileListView.do';
	}
	var iframe = window.parent.document.getElementById("trigger_iframe");
	$(iframe).attr("src",url);
	//修改样式
	$(iframe).parents(".lui_modeling_main.aside_main").eq(0).css("padding-top","10px");
	$(iframe).parents(".lui_modeling_main.aside_main").eq(0).parents(".lui_modeling").eq(0).find("#modelingAside").css("display","block");
	return false;
}

//移动 -1：上移       1：下移
function xform_main_data_moveTr_new(direct,dom){
	var tb = $(dom).closest("table")[0];
	var $tr = $(dom).closest("tr");
	var curIndex = $tr.index();
	var lastIndex = tb.rows.length - 1;
	if(direct == 1){
		if(curIndex >= lastIndex){
			alert(listviewOption.lang.alreadyToDown);
			return;
		}
		$tr.next().after($tr);
	}else{
		if(curIndex < 1){
			alert(listviewOption.lang.alreadyToUp);
			return;
		}
		$tr.prev().before($tr);			
	}
}
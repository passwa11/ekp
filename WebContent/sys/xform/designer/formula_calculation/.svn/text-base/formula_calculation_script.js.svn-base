/*
* @Author: liwenchang
* @Date:   2017-09-15 14:14:55
* @Last Modified by:   liwenchang
* @Last Modified time: 2017-09-16 02:31:13
*/

/**
 * [description 页面加载完成事件]
 * @param  {[type]} ){} [description]
 * @return {[type]}       [description]
 */
$(function(){
	//获取所有的公式加载控件dom元素
	var formulaDoms = $("[formula_calculation='true']:not([name*='!{index}'])");
	$(document).on("table-add",function(event,source){
		tableAdd(event,source);
	});
	
	//处理返回类型为地址本的情况
	_formulaCalculation_processAddressType();
	
	formulaDoms.each(function(index,domEle){
		setListener(domEle);
	});
})


function setListener(obj,curVal){
	var xformFlag = $(obj).closest("xformflag");
	var flagid;
	if(xformFlag){
		flagid =  xformFlag.attr("flagid");
	}
	var inDetailTable = false;
	var rowIndex;
	if(/\.(\d+)\./g.test(flagid)){
		inDetailTable = true;
	}else{
		inDetailTable = false;
	}
	// 监控的控件
	var controlIdStr= $(obj).attr('controlsid');
	if(controlIdStr && controlIdStr != ''){
		// 如果是多个输入，则每个输入都需要监听
		var controlIdArray = controlIdStr.split(';');
		for(var i = 0;i < controlIdArray.length;i++){
			var controlId = controlIdArray[i];
			if(/-fd(\w+)/g.test(controlId)){
				controlId = controlId.match(/(\S+)-/g)[0].replace("-","");
			}
			// 如果是在明细表里面，处理id
			if(/(\w+)\.(\w+)/g.test(controlId)){
				if(inDetailTable){
					rowIndex = flagid.match(/\.(\d+)\./g);
					rowIndex = rowIndex ? rowIndex:[];
					controlId = controlId.replace(".",rowIndex[0]);	
				}
			}
			// 绑定动态事件的初始化完成事件 用于当输入控件是动态控件（动态单选、动态多选）时，设置初始值
			$("xformflag[flagid*='" + controlId + "']").on('relation_dynamicLoaded',function(){
				loadData($(obj),true);
			});
			//获取绑定的事件控件对象
			var bindStr = document.getElementById(controlId)?"#" + controlId:'[name*="' + controlId + '"]';
			if(isManualLoad($(obj))){
				$(bindStr).on('change',function(){
					setTimeout(function(){
						loadData($(obj));
					},0);
				});
			}
		}
	}
	loadData($(obj),true);
}

function _formulaCalculation_processAddressType(){
	XFormOnValueChangeFuns.push(function(value,dom){
		clearAddressValueIfNecessary(dom);
	});
}

//清除返回类型为地址本的数据
function clearAddressValueIfNecessary(dom){
	if(dom.tagName && dom.tagName.toLowerCase()=='select'){
		
	}else if (dom && dom.length && dom.length > 0) {//单选,复选,地址本等控件会传进dom数组
		dom = dom[0];
	}
	var NEWADDRESSPREFIX = "extendDataFormInfo.value(";
	var NEWADDRESSIDSUFFIX = ".id)";
	var NEWADDRESSNAMESUFFIX = ".name)";
	var MANUALLOADREG = /manualLoad/;
	
	var name = dom["name"] || "";
	name = name.replace(/\.id|\.name/gi,"");
	var fdId = /\((\S+)\)/.test(name) ? name.match(/\((\S+)\)/)["1"] : name;
	
	var formulaDoms = $("div[returnType='address']");
	for (var i = 0; i < formulaDoms.length; i++){
		var controlsId = $(formulaDoms[i]).attr("controlsid");
		var closestTR = $(formulaDoms[i]).closest("tr")[0];
		var isInDetailTable = false;
		if(closestTR){
			isInDetailTable = XForm_FormulaIsDetailTableRr(closestTR);
		}
		if (fdId.indexOf(".") > -1 && isInDetailTable){
			fdId = fdId.replace(/\.(\d+)\./,".");
			if (XForm_FormulaGetTableRr(dom) !== XForm_FormulaGetTableRr(formulaDoms[i])){
				continue;
			}
		}
		if (controlsId && controlsId.indexOf(fdId) > -1){
			var loadType = $(formulaDoms[i]).attr("loadtype");
			var id = $(formulaDoms[i]).closest("xformflag").attr("flagid");
			if (MANUALLOADREG.test(loadType)){
				$("[name='" + NEWADDRESSPREFIX + id + NEWADDRESSIDSUFFIX + "']").val("");
				$("[name='" +NEWADDRESSPREFIX + id + NEWADDRESSNAMESUFFIX + "']").val("");
			}
		}
	}
}

/**
 * [tableAdd 明细表添加行事件]
 * @param  {[type]} source [添加行的dom对象]
 * @return {[type]}        [description]
 */
function tableAdd(event,source){
	//获取明细表添加的行有没有公式加载控件
	var formulaDoms = $(source).find("[formula_calculation='true']");
	for (var i = 0; i < formulaDoms.length; i++){
		setListener(formulaDoms[i]);
	}
}

/**
 * [isAutoLoad 判断公式加载控件是否设置为进入页面立即加载]
 * @param  {[type]}  $formulaDom [公式加载控件jquery对象]
 * @return {Boolean}             [立即加载返回true,否则返回false]
 */
function isAutoLoad($formulaDom){
	var loadType = $formulaDom.attr("loadtype");
	if (loadType.indexOf("autoLoad") > -1){
		return true;
	}else{
		return false;
	}
}

/**
 * [isAutoLoad 判断公式加载控件是否设置为进入控件触发]
 * @param  {[type]}  $formulaDom [公式加载控件jquery对象]
 * @return {Boolean}             [控件触发返回true,否则返回false]
 */
function isManualLoad($formulaDom){
	var loadType = $formulaDom.attr("loadType");
	if (loadType.indexOf("manualLoad") > -1){
		return true;
	}else{
		return false;
	}
}

/**
 * [loadData 自动加载方式获取公式加载控件表达式执行的结果]
 * @param  {[type]} $formulaDom [公式加载控件jquery对象]
 * @return {[type]}             [description]
 */
function loadData($formulaDom,isInit){
	//如果权限区段为只读,则不加载数据
	var right = $formulaDom.attr("right");
	var method = Com_GetUrlParameter(location.href,"method");
	if (right == "view" || (right == "" && method == "view")){
		return;
	}
	//获取跟表达式相关的控件id
	var controlsId = $formulaDom.attr("controlsid");
	//获取公式加载控件是否在明细表内的属性
	var isRow = $formulaDom.attr("isRow");
	//获取数据展现形式
	var returnType = $formulaDom.attr("returnType");
	var dataType = $formulaDom.attr("dataType");
	//获取公式加载控件的id,含有索引行
	var formulaControlId = $formulaDom.parent("xformflag[flagtype='formula_calculation']").attr("flagid");
	//如果是在明细表内
	var controlsIdArr = [];
	//跟表达式相关联的控件的键值对对象
	var fieldsJSON = {};
	fieldsJSON["fdControlId"] = formulaControlId;
	fieldsJSON["returnType"] = returnType;
	fieldsJSON["dataType"] = dataType;
	if (controlsId != null && controlsId != "null"){
		controlsIdArr = controlsId.split(";");
		for (var i = 0; i < controlsIdArr.length; i++){
			//获取跟表达式相关的控件的值
			var value = getXFormFieldValue(isRow,controlsIdArr[i],$formulaDom);
			fieldsJSON[controlsIdArr[i]] = value;
		}
	}
	//封装请求参数
	var param = requestData(fieldsJSON);
    //xform_ajax_count++;
	//发送请求
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/sys_form/formulaCalculation.do?method=getDataJSON",
		type: 'POST',
		async: false,
		dataType: 'json',
		data: param,
		success:function(data){
			//渲染视图
			renderData(data,returnType,$formulaDom,isInit);
			//当再次编辑时,回显之前设置的值,如果公式加载控件的返回类型是单行文本框不需要处理
			if (returnType != 'text'){
				setValue(returnType,$formulaDom);
			}else{//发布值改变事件
				__xformDispatch(data, $formulaDom[0]);
			}
		},
        // complete : function() {
        //    xform_ajax_count--;
        // },
		error : function(message){
			console.log( "公式加载后台获取数据异常");
		}
	})
}

function XForm_FormulaIsDetailTableRr(dom) {
	if (dom.tagName == 'TR' && "true" != dom.getAttribute('data-celltr')) {
		for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
			if (parent.tagName == 'TABLE') {
				return (/^TABLE_DL_/.test(parent.id));
			}
		}
		return true;
	}
	return false;
}
function XForm_FormulaGetTableRr(dom) {
	for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
		if (XForm_FormulaIsDetailTableRr(parent)) {
			return parent;
		}
	}
	return null;
}

/**
 * [getXFormFieldValue 获取跟公式加载相关联控件的value值]
 * @param  {Boolean} isRow       [该控件是否在明细表内]
 * @param  {[type]}  contorlId 	 [控件id]
 * @return {[type]}              [description]
 */
function getXFormFieldValue(isRow,contorlId,$formulaDom){
	//获取公式加载控件的id,含有索引行
	var $xformflag = XForm_Formula_findXFormFlag($formulaDom);
	var formulaControlId = $xformflag.attr("flagid");
	var tmpId = contorlId;
	if (contorlId.indexOf(".") > -1 && formulaControlId.indexOf(".") > -1){//两者都在明细表里
		//获取明细表里面表单控件的索引号(.index.)
		var indexStr = formulaControlId.substring(formulaControlId.indexOf("."),formulaControlId.lastIndexOf(".") + 1);
		//因为formFiedList里面表单控件的id不含索引,所以要把索引加上去	
		tmpId = tmpId.replace(".",indexStr);
	}
	//根据控件id获取表单控件
	var fieldsInfo = GetXFormFieldById_ext(tmpId,true);
	if (fieldsInfo != null && fieldsInfo.length > 1){
		for(var j=0;j<fieldsInfo.length;j++){
			if(fieldsInfo[j].type=='text' && fieldsInfo[j].name==contorlId){
				var temp=fieldsInfo[j];
				fieldsInfo=[];
				fieldsInfo.push(temp);
				break;
			}
		} 
	}
	var valueInfo = [];
	//如果是radio或者checkbox(一个name，对应多个值)则将选中的值放在数组中
	for (var j = 0; j < fieldsInfo.length; j++){
		if (fieldsInfo[j].type == "radio" || fieldsInfo[j].type == "checkbox" || fieldsInfo[j].type == 'select' || fieldsInfo[j].type == 'select-one'){
			if(fieldsInfo[j].name == "_"+contorlId) {
				// 排除标签的特殊处理
				continue;
			}
		}
		if (fieldsInfo[j].type == "radio" || fieldsInfo[j].type == "checkbox"){
			if (fieldsInfo[j].checked){
				valueInfo.push(fieldsInfo[j].value);
			}
			continue;
		}
		var val = fieldsInfo[j].value;
        var fieldXFormFlag = $(fieldsInfo[j]).closest("xformflag");
        var flagType = fieldXFormFlag.attr("flagtype");
        if (flagType === "xform_address" || flagType === "xform_new_address") {
            var nameProperty = fieldXFormFlag.attr("property").replace(".id", ".name");
            var textVal = $("input[name='" + nameProperty + "']").val();
            if (val) {
                val += "," + textVal;
            }
        }
        valueInfo.push(val);//与表达式相关的控件的值
	}
	if (!valueInfo || valueInfo.length == 0){
		valueInfo = "null";
	}
	return valueInfo;
}
/**
 * [requestData 封装请求参数]
 * @param  {[type]} fieldsJSON [description]
 * @return {[type]}            [返回请求参数字符串]
 */
function requestData(fieldsJSON){
	var param = "";
	//请求参数数组
	var paramArray = new Array();
	//主文档名
	var modelName = $("input[name$='extendDataFormInfo.mainModelName']").val();
	//获取扩展文件
	var _extendFilePath = $("input[name$='extendDataFormInfo.extendFilePath']")[0];
	var extendFilePath = '';
	if (_extendFilePath){
		 extendFilePath = _extendFilePath.value;
	}
	//主文档id
	var modelId = _xformMainModelId;
	//url格式的参数
	for(var fieldId in fieldsJSON){
		if(fieldsJSON[fieldId] != "null") {
			paramArray.push(encodeURIComponent(fieldId)+"="+encodeURIComponent(fieldsJSON[fieldId]));
		}
	}
	//用户构建model,供后台解析用
	paramArray.push("modelName=" + encodeURIComponent(modelName));
	paramArray.push("extendFilePath=" + encodeURIComponent(extendFilePath));
	paramArray.push("modelId=" + modelId);
	param = paramArray.join("&");
	return param;
}

/**
 * [renderData 根据返回的数据和数据类型渲染视图]
 * @param  {[type]} data       [根据表达式获取的数据]
 * @param  {[type]} returnType [数据展现形式]
 * @return {[type]}            [description]
 */
function renderData(data,returnType,$formulaDom,isInit){
	//下拉列表
	if (returnType == "select"){
		//生成下拉列表
		buildSelect(data,$formulaDom);
	}
	//单选按钮
	if (returnType == "radio"){
		//生成单选按钮
		buildRadio(data,$formulaDom);
//		$formulaDom.trigger($.Event("exceRelationRule"),[$formulaDom]);
	}
	//多选按钮
	if (returnType == "checkbox"){
		//生成多选按钮
		buildCheckbox(data,$formulaDom);
//		$formulaDom.trigger($.Event("exceRelationRule"),[$formulaDom]);
	}
	//单行文本框
	if (returnType == "text"){
		//设置单行文本框的值
		buildText(data,$formulaDom,isInit);
	}
}


function buildSelect(data,$formulaDom){
	//当控件再次触发时,先获取已存在的单选按钮
	var option = $formulaDom.find("option");
	if (option.length != 0){
		//清空已经存在的下拉列表
		$formulaDom.empty();
	}
	var pleaseSelect = document.createElement("option");
	$(pleaseSelect).val("");
	$(pleaseSelect).text(XformObject_Lang.ControlPleaseSelect);
	$formulaDom.append(pleaseSelect);
	//如果数据不为空,生成下拉列表
	if (data.length > 0){
		for (var i = 0; i < data.length; i++){
			var option = document.createElement("option");
			for (var field in data[i]){
				if (field  == "fdId"){
					$(option).val(data[i][field]);
				}
				if (field == "fdName"){
					$(option).text(data[i][field]) ;
				}
			}
			$formulaDom.append(option);
		}
	}else{
		var $textFiled = XForm_Formula_findTextFiled($formulaDom);
		$textFiled.val("");
		$formulaDom.val("");
	}
}

function XForm_Formula_findXFormFlag($formulaDom){
	var $xformflag =  $formulaDom.parent("xformflag[flagtype='formula_calculation']");
	return $xformflag;
}

function XForm_Formula_getTextName($formulaDom){
	var name = $formulaDom.attr("name");
	//获取显示值隐藏域的name属性
	var textName = name.substring(0,name.indexOf(")")) + "_text)";
	return textName;
}

function XForm_Formula_findTextFiled($formulaDom){
	var $xformflag = XForm_Formula_findXFormFlag($formulaDom);
	var textName = XForm_Formula_getTextName($formulaDom);
	var $textField = $xformflag.find("input[name='" + textName + "']");
	return $textField;
}

function XForm_Formula_findValFiled($formulaDom){
	var $xformflag = XForm_Formula_findXFormFlag($formulaDom);
	var name =$formulaDom.attr("name");
	var $valField = $xformflag.find("input[name='" + name + "']");
	return $valField;
}

function XForm_Formula_ClearRadioOrCheckboxVal($formulaDom){
	var $textObj = XForm_Formula_findTextFiled($formulaDom);
	var $valueObj = XForm_Formula_findValFiled($formulaDom);
	$valueObj.val("");
	$textObj.val("");
}

/**
 * [buildRadio 根据返回值生成单选按钮]
 * @param  {[type]} data        [description]
 * @param  {[type]} $formulaDom [description]
 * @return {[type]}             [description]
 */
function buildRadio(data,$formulaDom){
	//当控件再次触发时,先获取已存在的单选按钮
	var radio = $formulaDom.find("input[type='radio']");
	
	var textDom = XForm_Formula_findTextFiled($formulaDom);
	var valueDom = XForm_Formula_findValFiled($formulaDom);
	//获取控件公式加载控件的name属性
	var name  = $formulaDom.attr("name");
	var radioName = "_" + name;
	//第一次加载无数据,必填，第二次加载有数据,要移除掉必填提示
	clearValidateTip(radioName);
	removeValidateTip(name + "_validateFlag");
	//清空已经存在的单选按钮
	$formulaDom.empty();
	//获取公式加载控件的必填属性
	var isRequired = $formulaDom[0].getAttribute("required");
	
	var subject = $formulaDom.attr("subject");
	if (data.length > 0){
		if (isRequired == "true"){
			$formulaDom.attr("validate","required");
		}
		for (var i = 0; i < data.length; i++){
			var label = document.createElement("label");
			//设置每个单选或多选的间隔
			var input = document.createElement("input");
			$(input).attr('type', "radio");
			$(input).attr('name',radioName);
			//如果公式加载控件设置为必填，在单选按钮中设置validate = required属性
			if (isRequired == "true"){
				$(input).attr("validate","required");
			}
			//设置标题
			$(input).attr("subject",subject);
			$formulaDom.append(label);
			$(label).append(input);
			var _val = data[i] || "";
			$(input).val(_val["fdId"]);
			//当data[i][field]为false时,$(label).append()不起作用,所以加个判断
			if (_val["fdName"] === false){
				$(label).append(_val["fdName"] + "");
			}else{
				$(label).append(_val["fdName"]);
			}
			$formulaDom.append("&nbsp;&nbsp;");
			
			var func = function(event) {
				//单选按钮值改变时设置隐藏域的值
				// 停止冒泡
				if (event.stopPropagation) {
					event.stopPropagation();
				}
				if (event.cancelBubble) {
					event.cancelBubble = true;
				}
				radioValueChange(this,valueDom,this.value);
				radioValueChange(this,textDom,$(this).parent("label").text());
			};
			$(input).bind({focusin : func,click : func});

		}
		if(isRequired =="true"){
			$formulaDom.append( "<span class='txtstrong'>*</span>");
		}
	}else{
		//移除值
		XForm_Formula_ClearRadioOrCheckboxVal($formulaDom);
		buildValidateDomIfNecessary($formulaDom,data);
	}
}

function buildValidateDomIfNecessary($formulaDom,data){
	//获取公式加载控件的必填属性
	var isRequired = $formulaDom[0].getAttribute("required");
	//获取控件公式加载控件的name属性
	var name  = $formulaDom.attr("name");
	var subject = $formulaDom.attr("subject");
	// 设置必填星号
	if(isRequired == "true"){
		var validateFlag = name + "_validateFlag";
		clearValidateTip("_" + name);
		removeValidateTip(validateFlag);
		if(data && data.length == 0){
			var element = $("input[name='" + validateFlag + "']");
			var html = "";
			// 如果没有数据的时候，虚构一个隐藏的input（不能是hidden），使其能够应用系统的校验框架
			if(element && element.length == 0){
				html += "<input name='" + validateFlag + "' type='text' style='width:0px;border:0px;'";
				html += " title='" + subject + "' subject='" + subject + "' validate='required' value='' />";
			}
		}
		html += "&nbsp;&nbsp;<span class='txtstrong'>*</span>";
		$formulaDom.append(html);
	}
}

//清楚校验信息,但不移除name所在的dom
function clearValidateTip(name){
	var element = $("[name='" + name + "']");
	// 清除已有的必填校验
	if(element && element.length > 0){
		var reminder = new Reminder(element[0]);
		var advice = reminder._getAdvice();
		if (advice.length >0) {
			// 隐藏提示信息
			advice.remove();
		}
	}
}

//清除校验信息,移除name所在的dom
function removeValidateTip(name){
	var element = $("[name='" + name + "']");
	// 清除已有的必填校验
	if(element && element.length > 0){
		var reminder = new Reminder(element[0]);
		var advice = reminder._getAdvice();
		if (advice.length >0) {
			// 隐藏提示信息
			advice.remove();
		}
		element.remove();
	}
}

/**
 * [buildCheckbox 根据返回值生成多选按钮]
 * @param  {[type]} data        [description]
 * @param  {[type]} $formulaDom [description]
 * @return {[type]}             [description]
 */
function buildCheckbox(data,$formulaDom){
	//当控件再次触发时,先获取已存在的单选按钮
	var checkbox = $formulaDom.find("input[type='checkbox']");
	
	var textDom = XForm_Formula_findTextFiled($formulaDom);
	var valueDom = XForm_Formula_findValFiled($formulaDom);
	//清空已经存在的多选按钮
	//第一次加载无数据,必填，第二次加载有数据,要移除掉必填提示
	//获取控件公式加载控件的name属性
	var name  = $formulaDom.attr("name");
	var checkboxName = "_" + name;
	clearValidateTip(checkboxName);
	removeValidateTip(name + "_validateFlag");
	$formulaDom.empty();
	if (data.length > 0){
		//获取公式加载控件的必填属性
		var isRequired = $formulaDom[0].getAttribute("required");
		if (isRequired == "true"){
			$formulaDom.attr("validate","required");
		}
		for (var i = 0; i < data.length; i++){
			var label = document.createElement("label");
			//设置每个单选或多选的间隔
			var input = document.createElement("input");
			$(input).attr('type', "checkbox");
			$(input).attr('name',checkboxName);
			//如果公式加载控件设置为必填，在单选按钮中设置validate = required属性
			if (isRequired == "true"){
				$(input).attr("validate","required");
			}
			//设置标题
			$(input).attr("subject",$formulaDom.attr("subject"));
			$formulaDom.append(label);
			$(label).append(input);
			var _val = data[i];
			$(input).val(_val["fdId"]);
			//当data[i][field]为false时,$(label).append()不起作用,所以加个判断
			if (_val["fdName"] === false){
				$(label).append(_val["fdName"] + "");
			}else{
				$(label).append(_val["fdName"]);
			}
			$formulaDom.append("&nbsp;&nbsp;");
			
			var setValue = function(event){
				var checkbox = $("input[type='checkbox']:checked",$formulaDom);
				var texts = new Array();
				var fdVals = new Array();
				//如果全都没有选中
				if (checkbox.length == 0){
					radioValueChange(this,valueDom,"");
					radioValueChange(this,textDom,"");
				}else{
					for (var j = 0; j < checkbox.length; j++){
						fdVals.push($(checkbox[j]).val());
						texts.push($(checkbox[j]).parent().text());
					}
					radioValueChange(this,valueDom,fdVals.join(";"));
					radioValueChange(this,textDom,texts.join(";"));
				}
			};
			
			var clickFunc = function(event) {
				//多选按钮值改变时设置隐藏域的值
				// 停止冒泡
				if (event.stopPropagation) {
					event.stopPropagation();
				}
				if (event.cancelBubble) {
					event.cancelBubble = true;
				}
				setValue(event);
			};
			//属性变更监听了change事件,如果这里不监听一下change事件，在IE中，还没设值就先触发了属性变更的事件，导致计算出来的值是上一次选项的
			$(input).bind({click : clickFunc , change : setValue});
		}
		if(isRequired =="true"){
			$formulaDom.append( "<span class='txtstrong'>*</span>");
		}
	}else{
		//移除值
		XForm_Formula_ClearRadioOrCheckboxVal($formulaDom);
		buildValidateDomIfNecessary($formulaDom,data);
	}
}

/**
 * [buildText 根据返回值生成单行文本框]
 * @param  {[type]} data        [description]
 * @param  {[type]} $formulaDom [description]
 * @return {[type]}             [description]
 */
function buildText(data,$formulaDom,isInit){
	//获取公式加载控件
	//var $idHiddenDom = XForm_Formula_findValFiled($formulaDom);
	var $xformflag = XForm_Formula_findXFormFlag($formulaDom);
	var name =$formulaDom.attr("name");
	if(name.indexOf("_text")){
		name = name.replace("_text","");
	}
	var $idHiddenDom = $xformflag.find("input[name='" + name + "']");
	if (data.length > 0){
		var values = [];
		var texts = [];
		for (var i = 0; i < data.length; i++){
			for (var field in data[i]){
				if (field.toLowerCase() == "fdid"){
					values.push(data[i][field]);
				}
				if (field.toLowerCase() == "fdname"){
					texts.push(data[i][field]);
				}
			}
		}
		var valueStr = values.join(";");
		var nameStr = texts.join(";");
		$idHiddenDom.val(valueStr);
		$formulaDom.val(nameStr);
		if(!isInit){
			//支持属性变更
			$idHiddenDom.trigger($.Event("change"));	
		}
	}else{
		$idHiddenDom.val("");
		$formulaDom.val("");
	}
}

/**
 * [radioValueChange 单选按钮点击事件,当值改变时,设置隐藏域的值]
 * @param  {[type]} src   [被点击的单选按钮dom元素]
 * @param  {[type]} $dom  [隐藏域dom元素的jq对象]
 * @param  {[type]} value [要设置的隐藏域的值]
 * @return {[type]}       [description]
 */
function radioValueChange(src,$dom,value){
	$dom.val(value);
	//发布值改变事件
	__xformDispatch(value,src);
}

/**
 * [selectValueChange 下拉列表值改变事件]
 * @param  {[type]} value  [description]
 * @param  {[type]} source [description]
 * @return {[type]}        [description]
 */
function selectValueChange(value,source){
	var option = $(source).find("option:selected");
	var $textField = XForm_Formula_findTextFiled($(source));
	//设置显示值隐藏域的值,如果是'==请选择==' value设置为""
	if (option.text() == XformObject_Lang.ControlPleaseSelect){
		$textField.val("");
	}else{
		$textField.val(option.text());
	}
	//发布值改变事件
	__xformDispatch(value,source);
}

/**
 * [setValue 再次编辑时回显隐藏域的值]
 * @param {[type]} returnType  [description]
 * @param {[type]} $formulaDom [description]
 */
function setValue(returnType,$formulaDom){
	if (returnType == "radio"){
		setRadioValue($formulaDom);
	}
	if (returnType == "checkbox"){
		setCheckboxValue($formulaDom);
	}
	if (returnType == "select"){
		setSelectValue($formulaDom);
	}
}

/**
 * [setRadioValue 再次编辑时，如果之前选中了值,则回显]
 * @param {[type]} $formulaDom [description]
 */
function setRadioValue($formulaDom){
	//获取值隐藏域
	var valueDom = XForm_Formula_findValFiled($formulaDom);
	//获取显示值隐藏域
	var textDom = XForm_Formula_findTextFiled($formulaDom);
	//获取隐藏id域的值
	var value = valueDom.val();
	var radio = $formulaDom.find("input[type='radio']");
	for (var i = 0; i < radio.length; i++){
		if ($(radio[i]).val() == value){
			radio[i].checked = true;
		}
	}
}
/**
 * [setCheckboxValue  再次编辑时,如果之前选中了值,则回显]
 * @param {[type]} $formulaDom [description]
 */
function setCheckboxValue($formulaDom){
	//获取值隐藏域
	var valueDom = XForm_Formula_findValFiled($formulaDom);
	//获取显示值隐藏域
	var textDom = XForm_Formula_findTextFiled($formulaDom);
	//获取隐藏id域的值
	var value = valueDom.val();
	var checkbox = $formulaDom.find("input[type='checkbox']");
	if (value != null && value != ""){
		value = value.split(";");
		for (var i = 0; i < value.length; i++){
			for (var j = 0; j < checkbox.length; j++){
				if (value[i] == $(checkbox[j]).val()){
					checkbox[j].checked = true;
					break;
				}
			}
		}
	}
}


/**
 * [setSelectValue 再次编辑时,如果之前选中了值,则回显]
 * @param {[type]} $formulaDom [description]
 */
function setSelectValue($formulaDom){
	//获取下拉列表选中的值
	var value = $formulaDom.attr("value");
	//获取显示值隐藏域
	var textHidden = XForm_Formula_findTextFiled($formulaDom);
	//获取下拉列表所有的选项
	var option = $formulaDom.find("option");
	for (var i = 0; i < option.length; i++){
		//如果下拉列表的值和隐藏域的值相等
		if (option[i].value == value){
			//设置改选项被选中
			option[i].selected = true;
			if ($(option[i]).text() != XformObject_Lang.ControlPleaseSelect){
				textHidden.val($(option[i]).text());
			}
		}
	}
}

//支持属性变更控件
if(window['$form']){
	var _regist = $form.regist;
	_regist({
		_support : function(target){
			if(target.type == 'formulaCalculation'){
				var cache = target.cache;
				// 设置附件对象，处理状态
				if(cache.formulaCalculation == null){
					var formulaCalculation = this.getFormulaCalculation(target);
					if(!formulaCalculation){
						cache.maxLevel = 0;
					}else{
						cache.maxLevel = 2;
					}
				}
				return true;
			}
			return false;
		},
		getFormulaCalculation : function(target){
			var _element = target.element;
			var fdId = /\S+\((\S+)\)\S*/.exec(target.field)[1] || "";
			var _textName = "extendDataFormInfo.value(" + fdId + "_text)";
			var _formula = $("[name='" + _textName + "']");
			if(_element && _element.length > 0){
				for (var i = 0; i < _element.length; i++){
					if ($(_element[i]).attr("formula_calculation") == "true"){
						return _element[i];
					}
					if(_formula.attr("formula_calculation") == "true"){
						return _formula[0];
					}
				}
			}
			return null;
		},
		required : function(target, value){
			//有值才设置必填
			var formulaCalculation;
			formulaCalculation = this.getFormulaCalculation(target);
			return xform_formulaLoad_requiredExec(value,formulaCalculation);
		}
		
	});
}

function xform_formulaLoad_requiredExec(value,formulaCalculation){
	var xformflag = $(formulaCalculation).closest("xformflag");
	var name =  $(formulaCalculation).attr("name");
	
	
	var returnType = $(formulaCalculation).attr("returnType");
	var requirdFlag = false;
	var span;
	if (returnType == "select" || returnType == "text"){
		var xformflag = $(formulaCalculation).closest("xformflag");
		span = xformflag.find("span.txtstrong");
	}else{
		span = $(formulaCalculation).find("span.txtstrong");
	}
	
	if(span.length > 0){
		requirdFlag = true;
		if (typeof value === "undefined") {
			return true;
		}
	} else {
		if (typeof value === "undefined") {
			return false;
		}
	}
	
	if(value){
		// 设置必填
		if(!requirdFlag){
			if (returnType == "radio" || returnType == "checkbox"){
				buildValidateDomIfNecessary($(formulaCalculation),[]);
			}else{
				$(formulaCalculation).attr("validate","required");
				$(formulaCalculation).after("<span class='txtstrong'>*</span>");
			}
		}
	}else{
		// 设置非必填
		if(requirdFlag){
			if (returnType == "radio" || returnType == "checkbox"){
				var objs = $(formulaCalculation).find("[type='" + returnType + "']");
				objs.each(function(i,elem){
					$(elem).removeAttr("validate");
				});
			}
			$(formulaCalculation).removeAttr("validate");
			span.remove();
			var name  = $(formulaCalculation).attr("name");
			var _name = name;
			if (returnType == "radio" || returnType == "checkbox") {
				_name = "_" + name;
			}
			clearValidateTip(_name);
			removeValidateTip(name + "_validateFlag");
		}
	}
	return true;
}


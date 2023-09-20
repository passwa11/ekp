/***********************************************
 JS文件说明：
 此文件为自定义表单校验控件，前端校验JS

 作者：李文昌
 版本：1.0 2018-2-28
 ***********************************************/
$(function(){

	onLoad();

	Com_Parameter["event"]["submit"].push(submitValidate);

	var XFORM_SELECTOR = "xformflag[flagtype='xform_validator']";

	var EXPRESSION_SELECTOR = "input[name='param']";

	var EXPRESSION_ATTRIBUTE = "param";

	var EXPRESSION_PASS_SHOW = "1";

	var EXPRESSION_NOTPASS_SHOW = "0";

	var ISIN_DETAILTABLE = "isrow";

	var EXPRESSIONID = "expressionId";

	var EXPRESSION_VAR_FLAG = "$";

	var VALUE_EMPTY_FLAG = "empty";

	var XFORM_FLAG = "xformflag";

	var FLAG_TYPE = "flagtype";

	var XFORM_RELATION_RADIO = "xform_relation_radio";

	var XFORM_RELATION_CHECKBOX = "xform_relation_checkbox";

	/**
	 * 表单提交事件处理函数
	 * @returns
	 */
	function submitValidate(){
		//暂存时，校验器不执行校验
		var target = Com_GetEventObject();
		if(Com_Parameter.preOldSubmit!=null){
			target = Com_Parameter.preOldSubmit;
		}
		var isDraft = ((target && target.currentTarget && target.currentTarget.title == lbpm.constant.BTNSAVEDRAFT)
			|| (target && target.srcElement && target.srcElement.innerText == lbpm.constant.BTNSAVEDRAFT)
			|| (target && target.srcElement && target.srcElement.title == lbpm.constant.BTNSAVEDRAFT));
		if(isDraft){
            return true;
		}
		var isSubmit = true;
		var validators = getCanApplyValidator() || [];
		var canApplyValidator;
		for (var i = 0; i < validators.length; i ++) {
			canApplyValidator  = validators[i];
			var param = $(canApplyValidator).attr(EXPRESSION_ATTRIBUTE);
			param = JSON.parse(param || "[]");
			for (var j = 0; j < param.length; j++){
				var rtnVal = tryExecute(param[j],canApplyValidator);//isRow,
				//isEmpty 标识输入值是否为空,若为空不做校验
				if (rtnVal && !rtnVal.isEmpty){
					if (!rtnVal.result && param[j].expressionShow ==
						EXPRESSION_NOTPASS_SHOW){
						isSubmit = false;
					}
					if (rtnVal.result && param[j].expressionShow ==
						EXPRESSION_PASS_SHOW){
						isSubmit = false;
					}
				}
			}
		}
		if (!isSubmit && canApplyValidator){
			$KMSSValidation_ElementFocus(canApplyValidator);
		}
		return isSubmit;
	}

	//兼容填充控件填充到只读单行输入框或者其它只读控件,没有触发XFormOnValueChange的情况
	window.XForm_ValidatorDoExecutorAll = function(){
		var validators = getCanApplyValidator() || [];
		for (var i = 0; i < validators.length; i ++) {
			canApplyValidator  = validators[i];
			var param = $(canApplyValidator).attr(EXPRESSION_ATTRIBUTE);
			param = JSON.parse(param || "[]");
			for (var j = 0; j < param.length; j++){
				tryExecute(param[j],canApplyValidator);
			}
		}
	}

	function onLoad() {
		XFormOnValueChangeFuns.push(doExecutor);

		//复制事件绑定
		$(document).on('table-copy','table[showStatisticRow]',function(){
			XForm_ValidatorDoExecutorAll();
		});
		// 新增行的时候，也自动计算
		$(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
			var row = argus.row;
			var validators = $(row).find("[validator='true']");
			for (var i = 0; i < validators.length; i ++) {
				var canApplyValidator  = validators[i];
				var param = $(canApplyValidator).attr(EXPRESSION_ATTRIBUTE);
				param = JSON.parse(param || "[]");
				for (var j = 0; j < param.length; j++){
					tryExecute(param[j],canApplyValidator);
				}
			}
		});
	}

	/**
	 * 值改变事件处理函数
	 * @param value
	 * @param dom 值改变控件dom元素
	 * @returns
	 */
	function doExecutor(value,dom) {
		if(dom.tagName && dom.tagName.toLowerCase()=='select'){

		}else if (dom && dom.length && dom.length > 0) {
			dom = dom[0];
		}
		var canApplyExpressionDoms = getApplyExpressionDoms(dom);
		//#79847 修复 校验器校验动态下拉显示值，结果相反，判断不正确 ,显示值的赋值会延迟，获取到的还是上一次的值，故加个延时
		setTimeout(function(){
			for (var i = 0; i < canApplyExpressionDoms.length; i ++) {
				var expressions = canApplyExpressionDoms[i].expression || [];
				var validatorDom = canApplyExpressionDoms[i].dom;
				for (var j = 0; j < expressions.length; j++){
					var rtnVal = tryExecute(expressions[j],validatorDom);//isRow,
				}
			}
		},100);
	}

	//获取所有的校验器控件
	function getAllXFormValidator(){
		var validatorElements = $(XFORM_SELECTOR);
		return validatorElements;
	}

	//获取可以使用的校验器
	function getCanApplyValidator(){
		var allXFormValidator = getAllXFormValidator();
		var validators = [];
		var canApplyValidator;
		for (var i = 0;i < allXFormValidator.length; i++){
			canApplyValidator = $(allXFormValidator[i]).find(EXPRESSION_SELECTOR)[0];
			if (canApplyValidator){
				validators.push(canApplyValidator);
			}
		}
		return validators;
	}

	/**
	 *
	 * @param dom 值改变控件dom元素
	 * @returns [{expression:[],dom:validatorDom}]
	 */
	function getApplyExpressionDoms(dom){
		var candidates = getAllXFormValidator();
		var fdId = getFdIdByDomName(dom);
		var rtnVal = [];
		for (var i = 0;i < candidates.length; i++){
			var $elem = $(candidates[i]);
			var $paramElems = $elem.find(EXPRESSION_SELECTOR);
			var canApplyExpressions = [];
			if ($paramElems.size() > 0){
				var param = JSON.parse($paramElems.attr(EXPRESSION_ATTRIBUTE) || "[]");
				for (var j =0; j < param.length; j++){
					var expressionId = param[j].expressionId;
					if (expressionId && expressionId.indexOf(fdId) > 0) {//过滤跟值改变控件无关的表达式
						if ( $paramElems.attr(ISIN_DETAILTABLE) == 'true' && fdId.indexOf('.') > 0) {
							if (getDetailTableTrByDom($paramElems[0]) === getDetailTableTrByDom(dom)) {//如果值改变在明细表内，过滤跟非同行校验器
								canApplyExpressions.push(param[j]);
							}
							continue;
						}
						canApplyExpressions.push(param[j]);
					}
				}
			}
			if (canApplyExpressions.length > 0){
				rtnVal.push({"expression":canApplyExpressions,"dom":$paramElems[0]});
			}
		}
		return rtnVal;
	}

	/**
	 * 根据dom元素获取控件id,如果dom是在明细表内,
	 * 则剔除了明细表的行索引fd_xxxx.1.fd_xxxxx 变成 fd_xxxxx.fd_xxxxxx
	 * @param dom
	 * @returns
	 */
	function getFdIdByDomName(dom) {
		if (dom.name == '' || dom.name == null) {
			return null;
		}
		var name = dom.name.toString();
		var sIndex = name.indexOf('value(');
		if (sIndex < 0) {
			sIndex = 0;
		}
		var eIndex = name.lastIndexOf(')');
		name = name.substring(sIndex + 6, eIndex);
		var dIndex = name.lastIndexOf('.');
		//兼容地址本
		var isAddress = /.id$/.test(name);
		if(/\.(\d+)\./g.test(name)){
			name = name.replace(/\.(\d+)\./g,".").replace(/.id$/,"");
		}
		name = name.replace("_text","");
		name = name.replace("_name","");
		name = name.replace(".name","");
		name = name.replace(".id","");
		return name;
	}

	var messageCache = new Array();
	function tryExecute(executor,dom) {//isRow,
		try {
			var rtnVal = executeExpression(executor, dom) || {};//isRow,
			var tip = findTipByExpressionId(rtnVal.expressionId,dom);
			showTip(rtnVal,dom,tip);
			return rtnVal;
		} catch(e) {
			if (window.console) {
				console.info("js:" + e.description + "\n" + executor + "\n" + e);
			}
		}
	}

	/**
	 * 显示或隐藏提示信息
	 * @param rtnVal
	 * @param dom
	 * @returns
	 */
	function showTip(rtnVal,dom,tip){
		var expression = findExpressionObj(rtnVal.expressionId,dom);
		var expressionShow = expression.expressionShow == 1;
		var cache = findTipCache(expression,dom);

		//若输入控件为空值,则忽视校验规则,隐藏提示
		if (rtnVal.isEmpty){
			if (cache && cache.isShow ){
				cache.advice.hide();
				cache.isShow = false;
			}
			return ;
		}

		if (cache){
			if (expression.expressionShow == 1){//通过显示
				if (rtnVal.result){//结果通过
					if (!cache.isShow){//隐藏改为显示
						cache.advice.show();
						cache.isShow = true;
					}
				}
				if (!rtnVal.result){//结果不通过
					if (cache.isShow){//显示改为隐藏
						cache.advice.hide();
						cache.isShow = false;
					}
				}
			}else{//不通过显示
				if (!rtnVal.result){//结果不通过
					if (!cache.isShow){//隐藏改为显示
						cache.advice.show();
						cache.isShow = true;
					}
				}
				if (rtnVal.result){//结果通过
					if (cache.isShow){//显示改为隐藏
						cache.advice.hide();
						cache.isShow = false;
					}
				}
			}
		}else{
			if (expression.expressionShow == 1 && rtnVal.result){//校验通过提示
				initTipElement(expression, dom, tip);
			}else if (expression.expressionShow == 0 && !rtnVal.result){//校验不通过提示
				initTipElement(expression, dom, tip);
			}
		}
	}

	//查找提示对象缓存
	function findTipCache(expression,dom){
		for (var i = 0; i < messageCache.length; i++){
			if (messageCache[i] &&
				expression.expressionId == messageCache[i].expressionId
				&& dom == messageCache[i].executeDom ){
				return messageCache[i];
			}
		}
		return null;
	}

	//创建提示dom元素
	function initTipElement(expression, dom, tip){
		var _elements = new Elements();
		_elements.serializeElement(dom);
		var reminder = new Reminder(dom, tip);
		reminder._getAdvice = getAdvice;
		reminder.show = reminderShow;
		reminder.options.where = "beforeend";
		advice = reminder.show();
		messageCache.push({"expressionId":expression.expressionId,"executeDom":dom,"advice":advice,"isShow":true});
	}

	function htmlEscape (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&/g, "&#38;");
		s = s.replace(/\"/g, "&#34;");
		s = s.replace(/</g, "&#60;");
		return s.replace(/>/g, "&#62;");
	}

	function htmlUnEscape (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/amp;/g, "&");
		s = s.replace(/quot;/g, "\"");
		s = s.replace(/lt;/g, "<");
		return s.replace(/gt;/g, ">");
	}

	//======================覆盖校验框架提示信息对象的创建和显示提示信息==============================

	function getAdvice(innerHtml , create) {
		var _element = this.element;
		if (_element.length < 1) return null;
		var _adviceID = 'advice-' + _element.attr(KMSSValidation.ValidateConfig.attribute);
		if(create){
			var aHtml = '<div class="validation-advice" id="' + _adviceID + '" _Reminder="true" />';
			var insertObject = this.options.insertPoint(_element.get(0));
			if (insertObject) {
				advice = $(KMSSValidation.insertHtml(this.options.where, insertObject.parentNode, aHtml)).addClass('validation-advice');
				advice.append(innerHtml);
			}
		}
		return advice;
	};

	function reminderShow() {
		// 按照设置的样式更新提示信息
		if (/\{Msg\}/ig.test(this._msgStyle))
			this.msg = this.msgStyle.replace(/\{Msg\}/ig, this.msg);
		// 替换{name}为字段描述名
		var _subject = this.element.attr('subject') || '';
		this.msg = this.msg.replace(/\{name\}/ig, "<span class=\"validation-advice-title\">"+_subject+"</span>");
		// 替换相应参数
		var rep = new RegExp(), pValue, pType;
		for (var param in this._params) {
			pValue = this._params[param];
			// 类型不是对象或不是未定义，则匹配参数
			pType = typeof(pValue);
			if (pType != 'object' && pType != 'function' && pType != 'undefined') {
				rep.compile('\\{' + param + '\\}', 'ig');
				this.msg = this.msg.replace(rep, pValue);
			}
		}
		var msg = htmlEscape(this.msg);
		var outPutHtml = this.options.layout.replace(/\{ShowMsg\}/ig, msg);
		//跟随提示
		var advice = this._getAdvice(outPutHtml,true);
		advice.show();
		// rtf则判断它的父元素是否显示
		var rtf_visible = !$(this.element).data('rtf')
			|| !$(this.element).parent().is(':visible');
		// 新ui顶端提示
		if (!(this.element.is(':visible')) && rtf_visible && $(this.element).attr('xform-type') !== 'newAddressHidden' && $(this.element).attr('validator') !== 'true') {
			var luiAdvice = this._getLuiAdvice(outPutHtml, true);
			if (luiAdvice != null) {
				luiAdvice.show();
				$("html,body").scrollTop(0);
			}
		}
		return advice;
	};
	//======================覆盖校验框架提示信息对象的一个方法=============================

	//根据表达式获取提示信息
	function findTipByExpressionId(expressionId,dom){
		var Tip = XformObject_Lang.validatorNotPass;
		if (!expressionId){
			return Tip;
		}
		var expressionObj = findExpressionObj(expressionId,dom);
		if (!expressionObj.message){
			return Tip;
		}
		return expressionObj.message;
	}

	//获取单个表达式完整信息
	function findExpressionObj(expressionId,dom){
		var param = $(dom).attr(EXPRESSION_ATTRIBUTE);
		param = JSON.parse(param);
		for (var i = 0; i < param.length; i++){
			var info = param[i];
			var temp = param[i][EXPRESSIONID];
			if (temp.replace(/\s/g,"") === expressionId.replace(/\s/g,"")){
				return param[i];
			}
		}
		return {};
	}

	/**
	 * 执行表达式
	 * @param expression {expressionId:xxx,expressionName:xxx,message,expressionShow}
	 * @param isRow 校验器控件是否在明细表内
	 * @param dom 校验器dom元素
	 * @returns
	 */
	function executeExpression(param, dom) {//isRow,
		//对当前获取到的dom对象存储到全局变量里面，以防在函数里面需要调用
		XForm_Validator_Execute_Dom = dom;
		var expressionId = param[EXPRESSIONID];
		if (expressionId == '' || expressionId == null) {
			return '';
		}
		var scriptIn = preDealExpression(expressionId);
		var preInfo = {rightIndex:-1};
		var scriptOut = "";
		for (var nxtInfo = findNextVarInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = findNextVarInfo(scriptIn, nxtInfo)) {
			var varId = nxtInfo.isFunc ? nxtInfo.varName : getVarValueById(nxtInfo.varName, dom,scriptIn); // 后续需要考虑公式的处理isRow,
			if (varId === VALUE_EMPTY_FLAG){//没有输入的控件值用empty来标识,没有输入值,不做校验
				if (_XForm_Validator_ElementRequired(nxtInfo.varName)){
					return {"result":false,"expressionId":expressionId,"isEmpty":true};
				}else{
					return {"result":true,"expressionId":expressionId,"isEmpty":true};
				}
			}
			scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + varId ;
			preInfo = nxtInfo;
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1);
		scriptOut = scriptOut.replace(".name","");
		var result = (new Function('return (' + scriptOut + ');'))();
		if (result != null && result != '' && !isNaN(result)) { // 修复运算错误
			var c = result.toString();
			if (/\.\d*999999/.test(c) || /\.\d*0000000/.test(c)) {
				var _m = Math.pow(10, 6);
				result = Math.round(parseFloat(result)*_m)/_m;//result.toFixed(6);
			}
		}
		return {"result":result,"expressionId":expressionId};
	}

	function findNextVarInfo(script, preInfo) {
		var rtnVal = {};
		rtnVal.leftIndex = script.indexOf(EXPRESSION_VAR_FLAG, preInfo==null?0:preInfo.rightIndex+1);
		if(rtnVal.leftIndex==-1)
			return null;
		rtnVal.rightIndex = script.indexOf(EXPRESSION_VAR_FLAG, rtnVal.leftIndex+1);
		if(rtnVal.rightIndex==-1)
			return null;
		rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
		rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
		return rtnVal;
	}

	/**
	 * 根据控件id获取控件值
	 * @param fieldId
	 * @param isRow
	 * @param dom
	 * @returns
	 */
	function getVarValueById(fieldId, dom, scriptIn) {//isRow,
		var oldFieldId = fieldId;//保留控件id
		var i = fieldId.lastIndexOf('.');
		var isRow = false;
		if (i > -1) {
			fieldId = fieldId.substring(i + 1, fieldId.length);
			isRow = true;
		}
		var _objs = GetXFormFieldById(fieldId, true);
		//处理查看状态没有编辑权限的控件
		if (_objs.length == 0){
			var $xformflags = $("xformflag[id*='" + fieldId + "']");
			var type = getDataTypeObj(oldFieldId);
			var dataType = type["dataType"];
			if(isRow){
				var $xformflag = $(dom).closest(XFORM_SELECTOR);
				var flagId = $xformflag.attr("flagid");
				var lastIndexOf = flagId.lastIndexOf(".");
				var prefix = flagId.substring(0, lastIndexOf);
				if (xform_data_hide[prefix + "." + fieldId]) {
					value = xform_data_hide[prefix + "." + fieldId];
					return convertVarValue(value,dataType,true)
				} else {
					for (var index = 0; index < $xformflags.size(); index++){
						if (getDetailTableTrByDom(dom) == getDetailTableTrByDom($xformflags[index])){
							var value =  $($xformflags[index]).text();
							value = value.trim();
							value = value.replace(/\r\n|\n|\r/g,"");
							if (value == ""){
								return VALUE_EMPTY_FLAG;
							}else{
								return convertVarValue(value,dataType,true);
							}
						}
					}
				}
			}else{
				if (xform_data_hide[oldFieldId]) {
					value = xform_data_hide[oldFieldId];
					value = convertVarValue(value,dataType,true)
					return value;
				} else if ($xformflags.size() == 1) {
					var value = $xformflags.text();
					value = value.trim();
					value = value.replace(/\r\n|\n|\r/g,"");
					//空值设为empty
					if(value == ""){
						return VALUE_EMPTY_FLAG;
					}else{
						return convertVarValue(value,dataType,true);
					}
				}
			}
			return VALUE_EMPTY_FLAG;
		}

		var objs = [],type = getDataTypeObj(oldFieldId);
		var dataType = type["dataType"];
		var controlType = type["controlType"];
		if (/address/.test(controlType)){//兼容地址本
			var isGetName = scriptIn.indexOf("$" + oldFieldId + "$.name" ) > -1;//用来判断地址本是获取id还是获取文本值
			if (isRow){
				tr = getDetailTableTrByDom(dom);
				for (n = 0; n < _objs.length; n++) {
					sameTr = getDetailTableTrByDom(_objs[n]);
					if (sameTr === tr) {
						if (/.name\)$/.test(_objs[n].name) && isGetName){//获取地址本文本值
							return "'" + _objs[n].value.trim() + "'";
						}
						if (/.id\)$/.test(_objs[n].name) && !isGetName){//获取地址本id
							return "'" + _objs[n].value.trim() + "'";
						}
					}
				}
			}else{
				for (var index = 0; index < _objs.length; index++ ){
					if (/.name\)$/.test(_objs[index].name) && isGetName){//获取地址本文本值
						return "'" + _objs[index].value.trim() + "'";
					}
					if (/.id\)$/.test(_objs[index].name) && !isGetName){//获取地址本id
						return	"'" + _objs[index].value.trim() + "'";
					}
				}
			}
		}
		// 目前假设全是input text
		var isCheckbox = false;
		var isCheckboxNoRadio = false;
		for (var k = 0; k < _objs.length; k ++) {
			if (_objs[k].type == 'radio' || _objs[k].type == 'checkbox') {
				isCheckbox = true;
				if(_objs[k].type == 'checkbox'){//只是checkbox
					isCheckboxNoRadio = true;
				}
				if (_objs[k].checked) objs.push(_objs[k]);
				continue;
			}
			if (_objs[k].type === "hidden" && isCheckbox){//跳过checkbox的值隐藏域
				continue;
			}
			//跳过复选下拉
			if ($(_objs[k]).closest(".fs-option").length > 0) {
				continue;
			}
			//过滤
			objs.push(_objs[k]);
		}
		objs = findCanApplyField(oldFieldId,objs,isRow,dom);
		var sum = '', num, n, sameTr, tr;

		var $dom = $(dom);
		var paramIsDefault = true;

		if (isRow) {
			tr = getDetailTableTrByDom(dom);
			for (n = 0; n < objs.length; n ++) {
				sameTr = getDetailTableTrByDom(objs[n]);
				if (sameTr === tr) {
					num = convertVarValue(objs[n].value,dataType,true);
					return num;
				}
			}
		} else {
			if (objs.length == 0) {
				return VALUE_EMPTY_FLAG;
			}
			if (objs.length == 1) {
				//对于动态单选,动态多选,如果表达式中是显示值,则要特殊处理，不然获取到的还是旧值
				var flagType =  $(objs[0]).closest(XFORM_FLAG).attr(FLAG_TYPE);
				var val = objs[0].value;
				if (flagType == XFORM_RELATION_RADIO){
					var $radio = $("input:checked",$(objs[0]).closest(XFORM_FLAG));
					var textVal = $radio.attr("textvalue");
					if (!isCheckbox){
						val = textVal;
					}
				}
				num = convertVarValue(val,dataType,true);
				return num;
			}
			for (n = 0; n < objs.length; n ++) {
				num = convertVarValue(objs[n].value,dataType,false);
				sum = sum + ',' + num;
			}
			if (sum.length < 1) return '[]';
			return "'" + sum.substring(1,sum.length) + "'";
		}
		return VALUE_EMPTY_FLAG;
	}

	/**
	 * 判断指定dom元素是否有必填属性
	 * @param dom
	 * @returns
	 */
	function _XForm_Validator_ElementRequired(fieldId){
		var element = null;
		var i = fieldId.lastIndexOf('.');
		if (i > -1) {
			fieldId = fieldId.substring(i + 1, fieldId.length);
		}
		var _objs = GetXFormFieldById(fieldId, true);
		if (_objs.length == 0){
			return false;
		}

		if(_objs.length > 0){
			element = _objs[0];
		}

		if (_objs.length >= 2 && (/.id\)$/.test(_objs[0].name) || /.name\)$/.test(_objs[0].name))){
			for (var index = 0; index < _objs.length; index++ ){
				if (/.name\)$/.test(_objs[index].name)){
					element = _objs[index];
				}
			}
		}

		if (!element || !element.tagName) return false;
		var tag = element.tagName.toLowerCase();
		//对象被删除的情况
		var name = element.name;
		if(name!=null && name!=""){
			var target = document.getElementsByName(name);
			if(target==null || target.length<1) return false;
		}
		if($(element).parents("tr[KMSS_IsReferRow='1']").length>0 || (name && name.indexOf("!{index}")>=0)) return false;

		var id = element.id;
		if(id!=null && id!=""){
			var target = document.getElementById(id);
			if(target==null) return false;
		}
		// 是否有符合校验规则
		if(_XForm_Validator_Elements_IsValidate(element)){
			return true;
		}
		return false;
	}

	function _XForm_Validator_Elements_IsValidate(element) {
		return element.getAttribute('validate')
			&& element.getAttribute('validate') != ''
			&& element.getAttribute('validate').indexOf("required") > -1;
	}

	/**
	 * 根据dom元素获取控件类型
	 * @param objs
	 * @returns
	 */
	function getControlTypeByObj(objs){
		if (objs.length == 0){
			return "";
		}
		var controlType = $(objs[0]).closest("xformflag").attr("flagtype");
		return {controlType:controlType,dom:objs[0]};
	}

	/**
	 * 根据dom元素获取控件类型和数据类型
	 * @param objs
	 * @returns
	 */
	function getDataTypeObj(fieldId){
		var properties = Xform_ObjectInfo.properties;
		if (properties) {
			for (var i = 0; i < properties.length; i++) {
				var property = properties[i];
				if (property.name === fieldId) {
					return {dataType:property.type,controlType:property.controlType};
				}
			}
		}
		return {};
	}


	/**
	 *
	 * @param field 表达式中的控件id
	 * @param objs  含有field的input
	 * @param isRow field是否在明细表中
	 * @param dom  校验器dom对象
	 * @returns
	 */
	function findCanApplyField(field,objs,isRow,dom){
		var candidateFields = [];
		for (var k = 0; k < objs.length; k ++) {
			var name = objs[k].name;
			var fdId = /\((\S+)\)/.test(name) ? name.match(/\((\S+)\)/)["1"] : name;
			if (isRow){
				var fieldTr = getDetailTableTrByDom(objs[k]);
				var validatorDomTr =  getDetailTableTrByDom(dom);
				if (fieldTr === validatorDomTr){
					fdId = fdId.replace(/\.\d+\./,".");
				}
			}
			if (fdId === field){
				candidateFields.push(objs[k]);
			}
		}
		return candidateFields;
	}

	/**
	 * 获取指定dom元素所在的明细表行
	 * @param dom
	 * @returns
	 */
	function getDetailTableTrByDom(dom) {
		for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
			if (domIsInDetailTable(parent)) {
				return parent;
			}
		}
		return null;
	}

	function domIsInDetailTable(dom) {
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

	/**
	 * 转换value值
	 * @param value
	 * @returns
	 */
	function convertVarValue(value,dataType, isWrapSingleQuotes) {
		if (value == '' || value == null) {
			return VALUE_EMPTY_FLAG;
		}
		dataType = dataType || "";
		dataType = dataType.toLocaleLowerCase();
		var dataTypeDetail = dataType;
		dataTypeDetail = dataTypeDetail.replace("[]","");
		dataTypeDetail = dataTypeDetail.toLowerCase();
		var isTime = (dataTypeDetail == "datetime" || dataTypeDetail=="time") && /(\d:)/g.test(value);
		var isDate1 = /^\d{4}-\d{2}-\d{2}/.test(value);
		var isDate2 = /^\d{2}\/\d{2}\/\d{4}/.test(value);
		var d,t;
		if (isTime && (isDate1 || isDate2)) {
			var tmp = value.split(/ +/);
			d = parseDate(tmp[0], isDate1);
			t = parseTime(tmp[1]);
			return (new Date(d[0], parseFloat(d[1]) - 1, d[2], t[0], t[1], [2])).getTime();
		}
		if (isTime) {
			t = parseTime(value);
			var time = 0;
			for(var i = 0;i < t.length;i++){
				time += parseFloat(t[i] * Math.pow(60,t.length - i));
			}
			time = time * 1000;
			return time;
		}
		if ((isDate1 || isDate2)) {
			d = parseDate(value, isDate1);
			return (new Date(d[0], parseFloat(d[1]) - 1, d[2])).getTime();
		}
		if (isNumber(dataTypeDetail)){
			var originVal = value;
			value = parseFloat(value);
			if (isNaN(value)) {
				value = originVal.trim();
				value = originVal.replace(/\r\n|\n|\r/g,"");
				if (isWrapSingleQuotes) {
					value = "'" + value + "'";
				}
			}
			return value;
		} else {
			value = value.trim();
			value = value.replace(/\r\n|\n|\r/g,"");
			value = value.replace(/'/g,"&#39;");
			if (isWrapSingleQuotes) {
				value = "'" + value + "'";
			}
			return value;
		}
	}

	function isNumber(dataType) {
		if (dataType === "number" || dataType === "float" || dataType === "int"
			|| dataType === "double" || dataType === "bigdecimal") {
			return true;
		}
		return false;
	}

	function parseDate(value, mode) {
		var rtn, array;
		if (mode) {
			array = value.split('-');
			return array;
		} else {
			rtn = [];
			array = value.split('/');
			rtn[0] = array[2];
			rtn[1] = array[0];
			rtn[2] = array[1];
		}
		return rtn;
	}
	function parseTime(value) {
		return value.split(':');
	}

	function preDealExpression(expression){
		var tmpDiv = $("<div></div>").append(expression);
		return tmpDiv.text();
	}

});
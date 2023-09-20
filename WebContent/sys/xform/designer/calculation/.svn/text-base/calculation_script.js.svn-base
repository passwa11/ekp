/***********************************************
JS文件说明：
	此文件为自定义表单计算控件，前端计算JS

作者：傅游翔
版本：1.0 2010-5-31
***********************************************/
function XForm_CalculationExecuteExpression(expression, isRow, dom) {
	//对当前获取到的dom对象存储到全局变量里面，以防在函数里面需要调用 by朱国荣 2016-07-22
	XForm_Calculation_Execute_Dom = dom;
	if (expression == '' || expression == null) {
		return '';
	}
	var scriptIn = _XForm_PreDealExpression(expression);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	var varArr = [];
	for (var nxtInfo = XForm_CalculationFindNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = XForm_CalculationFindNextInfo(scriptIn, nxtInfo)) {
		if (!nxtInfo.isFunc) {
			varArr.push(nxtInfo);
		}
		var varId = nxtInfo.isFunc ? nxtInfo.varName : XForm_CalculationGetVarValueById(nxtInfo.varName, isRow, dom,nxtInfo); // 后续需要考虑公式的处理
		if (varId == null) {
			return '';
		}
		scriptOut += scriptIn.substring(preInfo.rightIndex+1, nxtInfo.leftIndex) + varId;
		preInfo = nxtInfo;
	}
	scriptOut += scriptIn.substring(preInfo.rightIndex+1);
	if (skipCalculationIfNecessary(varArr)) return "skip";
	var result = (new Function('return (' + scriptOut + ');'))();
	if (result != null && result != '' && !isNaN(result)) { // 修复运算错误
		var c = result.toString();
		if(c =="Infinity" || c =="-Infinity"){
			result = c;
			return result;
		}
		if (/\.\d*999999/.test(c) || /\.\d*0000000/.test(c)) {
			var _m = Math.pow(10, 6);
			result = Math.round(parseFloat(result)*_m)/_m;//result.toFixed(6);
		}
	}
	return result;
}

function skipCalculationIfNecessary(varInfos) {
	if (!varInfos || varInfos.length == 0) {
		return false;
	}
	var skip = false;
	var emptySize = 0;
	for (var i = 0; i < varInfos.length; i++) {
		var varInfo = varInfos[i];
		if (varInfo.isEmpty) {
			emptySize++;
		}
		//当变量所在的明细表零行时,不跳过
		var varName = varInfo.varName;
		if (varName && varName.indexOf(".") > 0) {
			var detailTableId = varName.split(".")[0];
			if (DocList_TableInfo) {
				var detailTableInfo = DocList_TableInfo["TABLE_DL_" + detailTableId];
				if (detailTableInfo) {
					if (detailTableInfo.lastIndex && detailTableInfo.lastIndex 
							&& (detailTableInfo.lastIndex - detailTableInfo.lastIndex == 0)) {
						return false;
					}
				}
			}
		}
	}
	if (emptySize == varInfos.length) {
		skip = true;
	}
	return skip;
}

function XForm_CalculationFindNextInfo(script, preInfo) {
	var rtnVal = {};
	rtnVal.leftIndex = script.indexOf("$", preInfo==null?0:preInfo.rightIndex+1);
	if(rtnVal.leftIndex==-1)
		return null;
	rtnVal.rightIndex = script.indexOf("$", rtnVal.leftIndex+1);
	if(rtnVal.rightIndex==-1)
		return null;
	rtnVal.varName = script.substring(rtnVal.leftIndex + 1, rtnVal.rightIndex);
	rtnVal.isFunc = script.charAt(rtnVal.rightIndex+1)=="(";
	return rtnVal;
}
function XForm_CalculationParseDate(value, mode) {
	var rtn, array;
	if (mode) {
		array = value.split('-');
		return array;
	} else {
		rtn = [];
		array = value.split('/');
		var dateFormat = Data_GetResourceString("date.format.date");
		if ('dd/MM/yyyy'==dateFormat){
			rtn[0] = array[2];
			rtn[1] = array[1];
			rtn[2] = array[0];
		}else{
			rtn[0] = array[2];
			rtn[1] = array[0];
			rtn[2] = array[1];
		}
	}
	return rtn;
}
function XForm_CalculationParseTime(value) {
	return value.split(':');
}
function XForm_CalculationConverVarValue(value) {
	if (value == '' || value == null) {
		return NaN;
	}
	var isTime = /(\d:)/g.test(value);
	var isDate1 = /^\d{4}-\d{2}-\d{2}/.test(value);
	var isDate2 = /^\d{2}\/\d{2}\/\d{4}/.test(value);
	calculation_isTime = {};
	calculation_isTime = {isTime:(isTime && (!isDate1 && !isDate2))};
	var d,t;
	if (isTime && (isDate1 || isDate2)) {
		calculation_isTime.isDateTime = true;
		var tmp = value.split(/ +/);
		d = XForm_CalculationParseDate(tmp[0], isDate1);
		t = XForm_CalculationParseTime(tmp[1]);
		return (new Date(d[0], parseFloat(d[1]) - 1, d[2], t[0], t[1], [2])).getTime();
	}
	if (isTime) {
		t = XForm_CalculationParseTime(value);
		var time = 0;
		for(var i = 0;i < t.length;i++){
			time += parseFloat(t[i] * Math.pow(60,t.length - i));
		}
		// 返回毫秒数 by zhugr 2017-07-04
		time = time * 1000;
		return time;
	}
	if ((isDate1 || isDate2)) {
		calculation_isTime.isDate = true;
		d = XForm_CalculationParseDate(value, isDate1);
		return (new Date(d[0], parseFloat(d[1]) - 1, d[2])).getTime();
	}
	//去除千分位分隔符
	if(/,/.test(value)) {
		value = value.replace(/,/g,"");
		return parseFloat(value);
	} else {
		return parseFloat(value);
	}
}
function XForm_CalculationGetVarValueById(fieldId, isRow, dom,nxtInfo) {
	var i = fieldId.lastIndexOf('.');
	var fieldIdTemp=fieldId;
	var flagFieldFlag=false;
	__calculation_empty = false;
	if (i > -1) {
		fieldId = fieldId.substring(i + 1, fieldId.length);
		flagFieldFlag=true;
	} else {
		isRow = false;
	}
	var _objs = GetXFormFieldById(fieldId, true), objs = [];
	//兼容地址本 为了添加工时计算函数
	if (_objs.length >= 2 && (/.id\)$/.test(_objs[0].name) || /.name\)$/.test(_objs[0].name))){
		if (isRow){
			tr = XForm_CalculationGetTableRr(dom);
			for (n = 0; n < _objs.length; n++) {
				sameTr = XForm_CalculationGetTableRr(_objs[n]);
				if (sameTr === tr) {
					if (/.id\)$/.test(_objs[n].name)){
						return "'" + _objs[n].value + "'";
					}
				}
			}
		}else{
			for (var index = 0; index < _objs.length; index++ ){
				if (/.id\)$/.test(_objs[index].name)){
					return "'" + _objs[index].value + "'";
				}
			}
		}
	}
	if (_objs.length == 0) {
		nxtInfo.isEmpty = true;
	} else {
		nxtInfo.isEmpty = false;
	}
	// 目前假设全是input text
	for (var k = 0; k < _objs.length; k ++) {
		if(flagFieldFlag){//若是明细表 
			//排除基准行（导入时会新增一个基准行），避免计算个数的时候出问题 ，控制角标为1是锁定导入的情况，缩小影响范围
			var $hiddenTr = $(_objs[k]).parents("tr:eq(0)");
			if($hiddenTr && $hiddenTr.length > 0 && $hiddenTr.css('display') == 'none'){
				continue;
			}
		}
		
		if (_objs[k].type == 'radio' || _objs[k].type == 'checkbox') {
			if (_objs[k].checked) objs.push(_objs[k]);
			continue;
		}
		//过滤掉有显示值的标签
		if (_objs[k].name.indexOf('_text)') > 0) {
			continue;
		}
		//判断明细表匹配到其他空间，所以讲字符带.名称的拆分开，用获取到的控件名称都来匹配拆分开的filedId
		var filedSplit=[];
		if(flagFieldFlag){
			filedSplit=fieldIdTemp.split(".");	
		}
		
		if(filedSplit.length>1){
			var mxbIndex=0;
			//明细表判断截取字符串filedId是不是都包含，只有都包含了才匹配此控件
			for(var z=0;z<filedSplit.length;z++){
				if(_objs[k].name.indexOf(filedSplit[z])>-1){
					mxbIndex++;
				}
			}
			
			if(mxbIndex!=filedSplit.length){
				continue;
			}
		}
		
		objs.push(_objs[k]);
	} 
	var sum = '', num, n, sameTr, tr;
	// 前端计算控件增加一个参数是否默认为0的配置项，故如果没有输入值，则默认为0 by zhugr 2017-03-29
	var $dom = $(dom);
	var paramIsDefault = false;
	if($dom.attr('paramdefault') && $dom.attr('paramdefault') != null && $dom.attr('paramdefault') == 'true'){
		paramIsDefault = true;
	}
	var _defaultVarValue = 0;
	if (isRow) {
		tr = XForm_CalculationGetTableRr(dom);
		for (n = 0; n < objs.length; n ++) {
			sameTr = XForm_CalculationGetTableRr(objs[n]);
			if (sameTr === tr) {
				num = XForm_CalculationConverVarValue(objs[n].value);
				if (isNaN(num)){
					if(paramIsDefault){
						num = _defaultVarValue;
					}else{
						return num;
					}
				} 
				return num;
			}
		}
	} else {
		if (objs.length == 0) {
			if(paramIsDefault){
				__calculation_empty = flagFieldFlag;
				return 0;
			}
			return '';
		}
		if (objs.length == 1) {
			num = XForm_CalculationConverVarValue(objs[0].value);
			if (isNaN(num) && paramIsDefault) num = _defaultVarValue;
			return num;
		}
		for (n = 0; n < objs.length; n ++) {
			num = XForm_CalculationConverVarValue(objs[n].value);
			if (isNaN(num) && paramIsDefault) num = _defaultVarValue;
			sum = sum + ',' + num;
		}
		if (sum.length < 1) return '[]';
		return '[' + sum.substring(1,sum.length) + ']';
	}
	return '';
}
function XForm_CalculationIsDetailTableRr(dom) {
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
function XForm_CalculationGetTableRr(dom) {
	for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
		if (XForm_CalculationIsDetailTableRr(parent)) {
			return parent;
		}
	}
	return null;
}

/***********************************************
触发明细表里面全部的前端计算控件
*************************************************/
function XForm_calculationDoExecutorOnlyDetailTable(){
	var executors = XForm_CalculationGetDetailTableAutoContrals();
	for (var i = 0; i < executors.length; i ++) {
		XForm_CalculationTryExecute(executors[i]);
	}
}

/***********************************************
获得明细表里面全部的前端计算控件
*************************************************/
function XForm_CalculationGetDetailTableAutoContrals(){
	var forms = document.forms;
	var objs = [], executor;
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			executor = elems[j];
			if (executor.name != null && executor.getAttribute && executor.getAttribute("calculation") == 'true' && executor.getAttribute("autoCalculate") == 'true') {
				objs.push(elems[j]);
			}
		}
	}
	return objs;
}

function XForm_CalculationGetAllContral(dom) {
	var forms = document.forms;
	var objs = [], executor;
	var varName = XForm_CalculationParseVar(dom);
	if (varName == null) {return objs;}
	varName = varName.replace(".id","");
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			executor = elems[j];
			if (executor.name != null && executor.getAttribute && executor.getAttribute("calculation") == 'true') {
				if (executor.getAttribute("expression").indexOf(varName) > 0) {
					if ( executor.getAttribute("isRow") == 'true' && varName.indexOf('.') > 0) {
						if (XForm_CalculationGetTableRr(executor) === XForm_CalculationGetTableRr(dom)) {
							objs.push(elems[j]);
						}
						continue;
					}
					objs.push(elems[j]);
				}
				continue;
			}
		}
	}
	return objs;
}

function XForm_CalculationGetAllAutoContrals() {
	var forms = document.forms;
	var objs = [], executor;
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			executor = elems[j];
			if (executor.name != null && executor.getAttribute && executor.getAttribute("calculation") == 'true' && executor.getAttribute("autoCalculate") == 'true') {
				if (executor.getAttribute("isRow") == 'true') {
					continue;
				}
				objs.push(elems[j]);
			}
		}
	}
	return objs;
}
function XForm_CalculationParseVar(dom) {
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
	if(/\.(\d+)\./g.test(name)){
		name = name.replace(/\.(\d+)\./g,".").replace(/.id$/,"");
	}
	return name;
}

var XForm_CalculationScale;

function XForm_CalculationTryExecute(executor) {
	try {
		var oldValue = executor.value;
		var expression = executor.getAttribute("expression");
		if (expression == null || expression == ''){
			return ;
		}
		XForm_CalculationScale = executor.getAttribute("scale");
		value = XForm_CalculationExecuteExpression(expression, executor.getAttribute("isRow") == 'true', executor);
		if (value == null || isNaN(value)) {
			if (value == "skip") return;
			executor.value = '';
			value='';
		}else {
			if (executor.getAttribute("scale") != null && executor.getAttribute("scale") != '') {
				var scale = parseInt(executor.getAttribute("scale"));
				if (!isNaN(scale)) {
					if(!(value == "Infinity" || value == "-Infinity")){
						value = XForm_CalculationRound(value, scale);
					}
				}
			}
			executor.value = value;
		}
		 //字符串的空值和数字的0是相等的，在 ‘’!= 0 的时候，判断为false，故需加多个判断
		if(oldValue!=value){
			if(oldValue == '' && value == '0'){
				__xformDispatch(value, executor);				
			}else{
				// 非首次加载时，值改变才需要触发
				// 触发值改变事件
				__xformDispatch(value, executor);	
				$(executor).trigger($.Event("change"));	
			}
		}
		XForm_CalculationSetViewModeTextVal(executor);
	} catch(e) {
		if (window.console) {
			console.info("js:" + e.description + "\n" + executor.getAttribute("expression") + "\n" + e);
		}
	}
}

//前端计算控件查看状态实时计算
function XForm_CalculationSetViewModeTextVal(executor){
	var val = executor.value;
	var thousandshow = $(executor).attr("thousandshow");
	var xformflag = $(executor).closest("xformflag[flagtype='xform_calculate']");
	var dom = $(".val",xformflag);
	if ($(executor).attr("type") == "hidden" && dom.length === 1){//区分查看状态还是编辑状态
		if (thousandshow === "true"){//显示千分位
			val = val.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
			//上面的正则表达式会给小数位后面也加上千分位,所以还要把小数点后面的,给替换掉
			var index = val.indexOf(".");
			if (index > 0){
				var substring = val.substring(index);
				substring = substring.replace(/,/g,"");
				val = val.substring(0,index) + substring;
			}
		}
		dom.text(val);
	}
}

function XForm_CalculationRound(a, b) {
	var result = (Math.round( XForm_CalculationAccMul(a,Math.pow(10, b))) / Math.pow(10, b));
	if (result && b && b > 0) {
		result = result.toString();
		var posDecimal = result.indexOf('.'); 
		if (posDecimal < 0) { 
		　　posDecimal = result.length; 
		　　result += '.'; 
		}
		while (result.length <= posDecimal + b) { 
		　　result += '0'; 
		} 
	}
	return result;
}

function XForm_CalculationAccMul(arg1,arg2){
	var m = 0;
	var s1 = arg1.toString();
	var s2 = arg2.toString();
	try{
		m += s1.split(".")[1].length
	}catch(e){}
	try{
		m += s2.split(".")[1].length
	}catch(e){}
	return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
}

function XForm_CalculationDoExecutor(value, dom) {
	if(dom.tagName && dom.tagName.toLowerCase()=='select'){
		
	}
	//只要有长度属性，就当做是数组，取第一个 by zhugr 2017-08-28
	else if (dom && dom.length && dom.length > 0) {
		dom = dom[0];
	}
	var executors = XForm_CalculationGetAllContral(dom), executor;
	for (var i = 0; i < executors.length; i ++) {
		if (executors[i].getAttribute("autoCalculate") == 'true')
			XForm_CalculationTryExecute(executors[i]);
	}
}
function XForm_CalculationDoExecutorAll() {
	var executors = XForm_CalculationGetAllAutoContrals();
	for (var i = 0; i < executors.length; i ++) {
		XForm_CalculationTryExecute(executors[i]);
	}
}
//页面初始化的时候加载
function XForm_CalculationDoExecutorAll_init() {
	var executors = XForm_CalculationGetAllAutoContrals();
	for (var i = 0; i < executors.length; i ++) {
		if(!executors[i].defaultValue){
			XForm_CalculationTryExecute(executors[i]);
		}
	}
}
function XForm_CalculationDoAction(btn) {
	var parent = btn.parentNode;
	var inputs = parent.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i ++) {
		if (inputs[i].name != null && inputs[i].getAttribute && inputs[i].getAttribute("calculation") == 'true') {
			XForm_CalculationTryExecute(inputs[i]);
			break;
		}
	}
}

var XForm_Calculation_Loaded = false;

function XForm_CalculationOnLoad() {
    XForm_Calculation_Ready();
}

/** 监听高级明细表 */
$(document).on("detailsTable-init-begin", function(e, tbObj){
    XForm_Calculation_Ready();
})

function XForm_Calculation_Ready() {
    if (!XForm_Calculation_Loaded) {
		XForm_Calculation_Loaded = true;
        XFormOnValueChangeFuns.push(XForm_CalculationDoExecutor);
        //明细表初始化完执行计算
        $(document).on('detaillist-init',function(){
			XForm_CalculationDoExecutorAll_init();
        });
        //复制事件绑定
        $(document).on('table-copy-new','table[showStatisticRow]',function(){
            XForm_CalculationDoExecutorAll();
        });
        // 新增行的时候，也自动计算
        $(document).on('table-add-new','table[showStatisticRow]',function(e,argus){
            var row = argus.row;
            var executors = $(row).find("[calculation='true'][autoCalculate='true']");
            for (var i = 0; i < executors.length; i ++) {
                XForm_CalculationTryExecute(executors[i]);
            }
        });
        // 新建的时候，需要执行自动计算，以防变量里面有默认值
        if(Com_GetUrlParameter(location.href,"method") == "add"){
            XForm_CalculationDoExecutorAll();
        }
    }
}

Com_AddEventListener(window, 'load', XForm_CalculationOnLoad);
//浮点数相加的函数，防止出现 一个长串小数  作者 曹映辉 #日期 2013年11月22日
function accAdd(num1,num2){ 
    var r1,r2,m; 
    try{ 
        r1 = num1.toString().split(".")[1].length; 
    }catch(e){ 
        r1 = 0; 
    } 
    try{ 
        r2=num2.toString().split(".")[1].length; 
    }catch(e){ 
        r2=0; 
    } 
    m=Math.pow(10,Math.max(r1,r2)); 
    return Math.round(num1*m+num2*m)/m; 
} 
// 公式
function XForm_CalculatioFuns_Sum() {
	var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
	var sun = 0, num;
	for (var i = 0; i < array.length; i ++) {
		num = parseFloat(array[i]);
		if (isNaN(num)) num = 0;
		sun = accAdd(sun,num);
	}
	return sun;
}
function XForm_CalculatioFuns_Count() {
	var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
	var sun = 0;
	for (var i = 0; i < array.length; i ++) {
		if (array[i] != null && array[i] !== '' && !isNaN(array[i])) {
			if (typeof __calculation_empty != "undefined" && __calculation_empty) {
				continue;
			}
			sun ++;
		}
	}
	__calculation_empty = false;
	return sun;
}
function XForm_CalculatioFuns_Avg() {
	var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
	var sun = 0, num, count = 0;
	for (var i = 0; i < array.length; i ++) {
		num = parseFloat(array[i]);
		if (isNaN(num)) {
			continue;
		}
		sun = accAdd(sun,num);
		count ++;
	}
	return (sun / count);
}
function XForm_CalculatioFuns_DefaultValue(value, def) {
	if (value == null || isNaN(value)) {
		return def;
	}
	return value;
}

/*
 * 获取时间差，返回毫秒数 
 * @param notAllowSubmit 校验不通过时, 是否允许提交, 默认允许
 */
function XForm_CalculatioFuns_TimeDistance(startDate, endDate, notAllowSubmit){
	var executeDom = $(XForm_Calculation_Execute_Dom);
	var domValidate = document.getElementById(executeDom.attr('name')+"_validateId");
	var resourceStringTip = XformObject_Lang.controlCalculation_func_timeWarning;
    XForm_Calculation_SetAllowSubmit(startDate, endDate, notAllowSubmit, executeDom);
	if(startDate > endDate || (!isNaN(startDate) && isNaN(endDate))){
		if(executeDom){
			//做标准校验 
			if(domValidate){
				domValidate.style.display = 'block';
			}else{
				//如果没有validate对象，则证明是第一次出现校验，第一次就增加这个对象	
				executeDom.parents("td").eq(0).append(XForm_CalculatioFuns_standardValidate(executeDom,resourceStringTip));
			}
		}else{
			//如果找不到当前dom，就用弹框校验
			alert(resourceStringTip);
		}
		
		return null;
	}
	if(domValidate){
		domValidate.style.display = "none";
	}else{
		var tipObjs = $(executeDom).parents("td").eq(0).find(".validation-advice-title");
		var lastTip;
		if(tipObjs && tipObjs.length >0){
			for(var i=0; i<tipObjs.length; i++){
				var tipObj = tipObjs[i];
				lastTip = $(tipObj).parents("td").eq(0).text();
				lastTip = lastTip.replace(/\s*/g,"");
				var subject = executeDom.attr('subject');
				var currentTip = subject.replace(/\s*/g,"")+resourceStringTip.replace(/\s*/g,"");
				if(lastTip == currentTip){
					//移除校验
					$(tipObj).parents("div.validation-advice").eq(0).remove();
				}
			}
		}
	}
    if (XForm_Calculation_IsReturnNaN(startDate, endDate)) {
        return NaN;
    }
    var timeDifference = parseFloat(endDate) - parseFloat(startDate) ;
	return timeDifference;
}

/**
 * 设置是否允许提交, 当notAllowSubmit为true并且开始时间大于结束时间, 则不允许提交
 * @param startDate 开始时间
 * @param endDate 结束时间
 * @param notAllowSubmit 是否允许提交，默认允许
 * @constructor
 */
function XForm_Calculation_SetAllowSubmit(startDate, endDate, notAllowSubmit, executeDom) {
    if((startDate > endDate || (!isNaN(startDate) && isNaN(endDate))) && notAllowSubmit) {
        executeDom.attr("notAllowSubmit", true);
    } else {
        executeDom.attr("notAllowSubmit", false);
    }
}

/**
 * 日期类函数, 如果前端计算没有勾选默认参数为并且只要有一个参数为空, 则计算结果返回null
 * @param startDate 开始时间
 * @param endDate   结束时间
 * @returns {boolean} true表示计算结果返回NaN
 */
function XForm_Calculation_IsReturnNaN(startDate, endDate) {
    if (!XForm_Calculation_IsParamDefault() && (((isNaN(startDate) &&  isNaN(endDate)) || (isNaN(startDate) && !isNaN(endDate))))) {
        return true;
    }
    return false;
}

/**
 * 判断前端计算控件是否勾选了默认参数为0
 * @returns {boolean} true表示勾选, false表示没有勾选
 * @constructor
 */
function XForm_Calculation_IsParamDefault() {
    var paramDefault = $(XForm_Calculation_Execute_Dom).attr('paramdefault');
    return paramDefault === "true";
}


 /*
  * 获取日期差，返回天数 
  * @param notAllowSubmit 校验不通过时, 是否允许提交, 默认允许
  */
function XForm_CalculatioFuns_DayDistance(startDate, endDate, notAllowSubmit){
	var executeDom = $(XForm_Calculation_Execute_Dom);
	var domValidate = document.getElementById(executeDom.attr('name')+"_validateId");
	var resourceStringTip = XformObject_Lang.controlCalculation_func_timeWarning;
    XForm_Calculation_SetAllowSubmit(startDate, endDate, notAllowSubmit, executeDom);
	if(startDate > endDate || (!isNaN(startDate) && isNaN(endDate))){
		if(executeDom){
			//做标准校验 
			if(domValidate){
				domValidate.style.display = 'block';
			}else{
				//如果没有validate对象，则证明是第一次出现校验，第一次就增加这个对象					 
				executeDom.parents("td").eq(0).append(XForm_CalculatioFuns_standardValidate(executeDom,resourceStringTip));
			}
		}else{
			//如果找不到当前dom，就用弹框校验
			alert(resourceStringTip);
		}

		return null;
	}

	if(domValidate){
		domValidate.style.display = "none";
	}else{
		var tipObjs = $(executeDom).parents("td").eq(0).find(".validation-advice-title");
		var lastTip;
		if(tipObjs && tipObjs.length >0){
			for(var i=0; i<tipObjs.length; i++){
				var tipObj = tipObjs[i];
				lastTip = $(tipObj).parents("td").eq(0).text();
				lastTip = lastTip.replace(/\s*/g,"");
				var subject = executeDom.attr('subject');
				var currentTip = subject.replace(/\s*/g,"")+resourceStringTip.replace(/\s*/g,"");
				if(lastTip == currentTip){
					//移除校验
					$(tipObj).parents("div.validation-advice").eq(0).remove();
				}
			}
		}
	}
    if (XForm_Calculation_IsReturnNaN(startDate, endDate)) {
        return NaN;
    }
	var defaultVal = 1;
	if (startDate === 0 && endDate === 0) {
		defaultVal = 0;
	}
	//默认加1
	var timeDifference = parseInt((parseFloat(endDate) - parseFloat(startDate))/86400000 + defaultVal); 
	return timeDifference;
}

/**
 * 格式化日期,转为yyyy-mm-dd格式
 * @param date
 * @returns
 */
function formatDateTime(date) {
	
	var _year = date.getFullYear()

	var _month = date.getMonth();

	var _date = date.getDate();

	_month = _month + 1;

	if(_month < 10){_month = "0" + _month;}

	if(_date<10){_date="0"+_date  }

	return  _year + "-" + _month + "-" + _date;

}

/**
 * 功能:工时小时计算函数,单位/小时
 * @param $dom
 * @param validateTip
 * @param notAllowSubmit 校验不通过时, 是否允许提交, 默认允许
 * @returns
 */
function XForm_CalculationFuns_manHoursHourDistance(userId,startDate,endDate,dateType, notAllowSubmit){
	//如果没有传入人员,则采用自然日计算
	if (!userId){
		var rs = (parseFloat(XForm_CalculatioFuns_TimeDistance(startDate, endDate)))/3600000;
		//没有配置小数位数，则默认为2位
		return XForm_CalculationRound(rs, XForm_CalculationScale || 2);
	}else{
		var isDate = calculation_isTime.isDate;
		if(dateType){
			isDate = dateType=="date";
		}
		if (calculation_isTime.isTime){
			var date = new Date();
			date = formatDateTime(date);
			date = XForm_CalculationConverVarValue(date);
			startDate = new Date(date + startDate).getTime();
			endDate = new Date(date + endDate).getTime();
		}
		var executeDom = $(XForm_Calculation_Execute_Dom);
		var domValidate = document.getElementById(executeDom.attr('name')+"_validateId");
		var resourceStringTip = XformObject_Lang.controlCalculation_func_timeWarning;
        XForm_Calculation_SetAllowSubmit(startDate, endDate, notAllowSubmit, executeDom);
		if(startDate > endDate || (!isNaN(startDate) && isNaN(endDate))){
			if(executeDom){
				//做标准校验 
				if(domValidate){
					domValidate.style.display = 'block';
				}else{
					//如果没有validate对象，则证明是第一次出现校验，第一次就增加这个对象					 
					executeDom.parents("td").eq(0).append(XForm_CalculatioFuns_standardValidate(executeDom,resourceStringTip));
					//executeDom.after(XForm_CalculatioFuns_standardValidate(executeDom,resourceStringTip));
				}
			}else{
				//如果找不到当前dom，就用弹框校验
				alert(resourceStringTip);
			}
			return null;
		}
		if(domValidate){
			domValidate.style.display = "none";
		}else{
			var tipObjs = $(executeDom).parents("td").eq(0).find(".validation-advice-title");
			var lastTip;
			if(tipObjs && tipObjs.length >0){
				for(var i=0; i<tipObjs.length; i++){
					var tipObj = tipObjs[i];
					lastTip = $(tipObj).parents("td").eq(0).text();
					lastTip = lastTip.replace(/\s*/g,"");
					var subject = executeDom.attr('subject');
					var currentTip = subject.replace(/\s*/g,"")+resourceStringTip.replace(/\s*/g,"");
					if(lastTip == currentTip){
						//移除校验
						$(tipObj).parents("div.validation-advice").eq(0).remove();
					}
				}
			}
		}
        if (XForm_Calculation_IsReturnNaN(startDate, endDate)) {
            return NaN;
        }
		var manHoursTimeDifference;
		//为了计算同一天的日期类型的工时
		if(isDate){//startDate==endDate && 
			endDate += 86400000;
		}
		$.ajax({
			type:'post',
			async: false, //指定是否异步处理
			data:{userId:userId,startDate:startDate,endDate:endDate,flag:"hour"},
			dataType: "text",
			url:Com_Parameter.ContextPath+"sys/xform/designer/formula_calculation/jsonp.jsp?s_bean=sysTimeCountService",
			success:function(data){
				manHoursTimeDifference = data;
			}
		});
		var rs = (parseFloat(manHoursTimeDifference))/3600000;
		//没有配置小数位数，则默认为2位
		return XForm_CalculationRound(rs, XForm_CalculationScale || 2);
	}
}

/*
 * 返回标准校验的HTML
 * 
 */
function XForm_CalculatioFuns_standardValidate($dom, validateTip){
	//去除原来的明细表相同校验不做提示的功能，原先的校验会导致明细表时间校验多行提示不显示，提交按钮点不动的情况 by 王丽永
	/*var tipObjs = document.getElementsByClassName("validation-advice-title");
	var lastTip;
	if(tipObjs && tipObjs.length >0){
		for(var i=0; i<tipObjs.length; i++){
			var tipObj = tipObjs[i];
			lastTip = $(tipObj).parents("td").eq(0).text();
			lastTip = lastTip.replace(/\s*!/g,"");
			var subject = $dom.attr('subject');
			var currentTip = subject.replace(/\s*!/g,"")+validateTip.replace(/\s*!/g,"");
			if(lastTip == currentTip){
				//已经存在相同校验，不进行显示
				return "";
			}
		}
	}*/
	var html = "<div id='"
				+$dom.attr('name')+"_validateId"
				+"' class='validation-advice' style='margin-bottom: 16px;'>"
				+"<table class='validation-table'>"
				+"<tbody><tr><td>"
				+"<div class='lui_icon_s lui_icon_s_icon_validator'></div></td>"
				+"<td class='validation-advice-msg'><span id='_option_validate_tip' class='validation-advice-title' style=''>" 
				+$dom.attr('subject')+"</span>"
				+validateTip+"</td></tr></tbody></table></div>"; 
	return html;
}

function _XForm_PreDealExpression(expression){
	var tmpDiv = $("<div></div>").append(expression);
	return tmpDiv.text();
}

/**
 * 为了解决前端计算控件，在流程草稿,审批中，设置的样式不生效的问题(div中包含span的)。
 */
setTimeout(function() {
	if ($("div.xform_Calculation.xform_calculation_readonly").length > 0) {
		$("div.xform_Calculation.xform_calculation_readonly").each(function() {
			if($(this).find('span').length > 0){
			var newStyle = $(this).attr("attrdivstyle");
			if (newStyle != "" || newStyle != null) {
				$(this).removeAttr("style");
				$(this).attr("style", newStyle);
				$(this).removeAttr("attrdivstyle");
					if ($(this).find('input').length > 0) {
						// 如果包含有input元素的，移除掉style属性，防止出现重复样式造成的影响。
						// 例如：div上有margin值，input上也有margin的情况
						$(this).find('input').removeAttr("style");
					}
				}
			}
		});
	}
},100);
// #159716 前端计算的只读状态也添加数字校验
$(function (){
	if($("input.xform_Calculation[readonly]").length>0){
		for(var i = 0; i < $("input.xform_Calculation[readonly]").length; i++) {
			var validate = $($("input.xform_Calculation[readonly]")[i]).attr("validate");
			var allValidate = "number";
			if (validate) {
				allValidate = validate + " number";
			}
			$($("input.xform_Calculation[readonly]")[i]).attr("validate", allValidate);
		}
	}
	// #159716 套了权限区段的前端计算的可见状态通过提交事件添加数字校验
	Com_Parameter.event["submit"].push(function (){
		var hiddenCalculations = $("input.xform_Calculation[type='hidden']");
		var isReturn = true;
		if(hiddenCalculations.length>0){
			$(hiddenCalculations).each(function(){
				if(!(!isNaN(this.value) && !/^\s+$/.test(this.value)&& /^.{1,20}$/.test(this.value) && /(\.)?\d$/.test(this.value) )){
					if(this.value.indexOf("Infinity")>-1){
						alert(this.value+"不是一个有效数字!!!");
						isReturn = false;
						return false;
					}
				}
			})

		}
		return isReturn;
	});
});
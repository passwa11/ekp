/**
 * 表单校验扩展
 */
Com_IncludeFile("data.js");
var XFormExtendValidation = {};

//前端计算控件
XFormExtendValidation.XForm_CalculatioFuns_Validate = function(elem){
	if(!elem){
		return;
	}
	var expression = elem.getAttribute("expression");
	if (expression == '' || expression == null) {
		return true;
	}
	var id = $(elem).attr('name')+"_validateId";
	var validateObj = document.getElementById(id);
	if(validateObj){
		$(validateObj).remove();//如果存在，则移除
	}
	var isRow = false;
	if(elem.getAttribute("isRow") == 'true'){
		isRow = true;
	}
	var scriptIn = _XForm_PreDealExpression(expression);
	var preInfo = {rightIndex:-1};
	var scriptOut = "";
	var startDate, endDate;
	for (var nxtInfo = XForm_CalculationFindNextInfo(scriptIn, preInfo); nxtInfo!=null; nxtInfo = XForm_CalculationFindNextInfo(scriptIn, nxtInfo)) {
		var varId = nxtInfo.isFunc ? nxtInfo.varName : XForm_CalculationGetVarValueById(nxtInfo.varName, isRow, elem,nxtInfo); // 后续需要考虑公式的处理
		if (varId == null) {
			return '';
		}
		//判断是否为时间或者日期类型
		var _objs = GetXFormFieldById(nxtInfo.varName, true);
		var isDateOrTime = false;
		for(var i=0; i<_objs.length; i++){//必须都同时是时间或者日期类型才算正确
			var isTime = /(\d:)/g.test(_objs[i].value);
			var isDate1 = /^\d{4}-\d{2}-\d{2}/.test(_objs[i].value);
			var isDate2 = /^\d{2}\/\d{2}\/\d{4}/.test(_objs[i].value);
			if(isTime || isDate1 || isDate2){//属于时间类型
				isDateOrTime = true;
			}else{
				isDateOrTime = false;
			}
		}
		if(!nxtInfo.isFunc && isDateOrTime){
			if(startDate){
				endDate = parseFloat(varId);
			}else{
				startDate = parseFloat(varId);
			}
		}
		preInfo = nxtInfo;
	}
	if (typeof calculation_isTime != "undefined" && calculation_isTime.isTime){
		var date = new Date();
		date = formatDateTime(date);
		date = XForm_CalculationConverVarValue(date);
		startDate = new Date(date + startDate).getTime();
		endDate = new Date(date + endDate).getTime();
	}
	if(startDate && endDate && startDate > endDate){
		return false;
	}
	return true;
}

//#148797 修复服务单问题 增加前端计算控件计算日期查提交校验
Com_Parameter.event["submit"].push(function(){
	var flag = true;
	if (typeof XForm_CalculationGetDetailTableAutoContrals != "undefined") {
        var valida = XForm_CalculationGetDetailTableAutoContrals();
        if(null != valida){
            for(var i=0;i<valida.length;i++){
                var result = valida[i].name+"_validateId";
                var notAllowSubmit = $(valida[i]).attr("notAllowSubmit");
                var isShow = $(document.getElementById(result)).is(":visible");
                if(isShow && (notAllowSubmit === "true")){
                    flag = false;
                    break;
                }
            }
        }
	}
    return flag;
});

XFormExtendValidation.XForm_AddressMaxPersonNum_Validate = function(v, e, o){
	var num = isNaN(o['num']) ? 0 : parseInt(o['num']);
	if (num == 0 || !v) return true;
	o['maxNum'] = num; 
	if(v.split(";").length > num){
		return false;
	}
	return true;
}
//启动初始化
Com_AddEventListener(window, 'load', function(){
	//扩展的校验器定义
	XFormExtendValidation.xform_extendValidator = {
		'xform_calculatioFuns' : {
			error : "{name}"+XformObject_Lang.controlCalculation_func_timeWarning,
			test  : function(value, elem) {return XFormExtendValidation.XForm_CalculatioFuns_Validate(elem);}
		},
		'xform_addressMaxPersonNum(num)':{
			error : "{name} "+XformObject_Lang.controlAttrMaxPersonNumValidate,
			test  : function(v, e, o) {return XFormExtendValidation.XForm_AddressMaxPersonNum_Validate(v, e, o);}
		}
	}
	var form = document.forms[0];
	var _validation;
	if (form && $KMSSValidation) {
		if($KMSSValidation.forms && $KMSSValidation.forms[form.name || form]){
			_validation = $KMSSValidation.forms[form.name || form];
		}
		if (_validation ==  null) {
			_validation = $GetKMSSDefaultValidation();
		}
	}
	if (_validation != null) {
		_validation.addValidators(XFormExtendValidation.xform_extendValidator);
		if (Com_Parameter.event["submit"]) {
			Com_Parameter.event["submit"].unshift(function() {
				return _validation.validate();
			});
		}
	}
});

define( ["dojo/_base/declare", "mui/form/validate/Validator", "mui/form/validate/Reminder", 
         "mui/form/validate/dom-value", "dojo/dom-attr", "dojo/_base/lang", "mui/form/_FormBase", "mui/i18n/i18n!:error", "mui/form/validate/Elements"], 
         function(declare, Validator, Reminder, domValue, domAttr, lang, FormBase, Msg, Elements) {
	//常用检验类型配置
	var Validates = {
		'skip': {//跳过验证
			test: function(v) {return true}
		},
		'required':{//必填
			error : Msg['errors.required'].replace("{0}","{name}"),
			test  : function(v) {return !this.getValidator('isEmpty').test(v);}
		},
		'number' : {//数字类型
			error : Msg['errors.number'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || (!isNaN(v) && !/^\s+$/.test(v)&& /^.{1,20}$/.test(v) && /(\.)?\d$/.test(v) );}
		},
		'digits' : {//整数类型
			error :  Msg['errors.integer'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || /^-?\d+$/.test(v);}
		},
		'alpha' : {//英文字符
			error : Msg['errors.alpha'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || /^[a-zA-Z]+$/.test(v);}
		},
		'alphanum' : {//英文字符或数字
			error : Msg['errors.alphanum'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || !/\W/.test(v);}
		},
		'date' : {  //日期类型
			error : Msg['errors.date'].replace("{0}","{name}"),
			test  : function(v) {
				var res=false;
				var regDate=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
				if(dojoConfig.locale!=null && (dojoConfig.locale!='zh-cn' && dojoConfig.locale!='zh-hk' && dojoConfig.locale!='ja-jp')){
					//格式:MM/dd/yyyy
					regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				// 格式:dd/MM/yyyy
				if(dojoConfig.Date_format && dojoConfig.Date_format.match(/^d.*M.*y.*$/)) {
					regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				var regTime=/^[0-2][\d]:[0-5][\d]$/;
				if(v)
				{
					var dateAry=v.split(/\s+/);
					
					if(dateAry.length==1){
						res=regDate.test(v) || regTime.test(v);
					}else if(dateAry.length==2){
						res=regDate.test(dateAry[0])&&regTime.test(dateAry[1]);
					}
				}
				return this.getValidator('isEmpty').test(v) || res;
			}
		},
		'email' : { //电子邮件校验
			error : Msg['errors.email'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || /\w{1,}[@][\w\-]{1,}([.]([\w\-]{1,})){1,3}$/.test(v);}
		},
		'url' : {  //URL地址校验
			error : Msg['errors.url'].replace("{0}","{name}"),
			test  : function(v) {return this.getValidator('isEmpty').test(v) || /^(http|https|ftp):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?\/?/i.test(v);}
		},
		'currency-dollar' : {//货币格式
			error : Msg['errors.dollar'].replace("{0}","{name}"),
			test  : function(v) {
				// [$]1[##][,###]+[.##]
				// [$]1###+[.##]
				// [$]0.##
				// [$].##
				return this.getValidator('isEmpty').test(v) || /^\$?\-?([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/.test(v);
			}
		},
		'maxLength(length)' : {//最大长度校验
			error : Msg['errors.maxLength.simple'].replace("{0}","{name}").replace("{1}","{maxLength}"),
			test  : function(v, e, o) {
				var length = isNaN(o['length']) ? 0 : parseInt(o['length']);
				if (length == 0 || this.getValidator('isEmpty').test(v)) return true;
				o['maxLength'] = length; //Math.floor(length/30)*20;
				var newvalue = v.replace(/[^\x00-\xff]/g, "***");
				return newvalue.length <= length;
			}
		},
		'range(min,max)' : {//阈值范围
			error : Msg['errors.range'].replace("{0}","{name}").replace("{1}","{min}").replace("{2}","{max}"),
			test : function(v, e, o) {
				if (this.getValidator('isEmpty').test(v)) return true;
				if ((!isNaN(v) && !/^\s+$/.test(v))) {
					var min = isNaN(o['min']) ? 0 : parseFloat(o['min']);
					var max = isNaN(o['max']) ? 0 : parseFloat(o['max']);
					var value = parseFloat(v);
					return (min <= value && value <= max);
				}
				return false;
			}
		},
		'max(num)' : {//不小于
			error : Msg['errors.max'].replace("{0}","{name}").replace("{1}","{num}"),
			test : function(v, e, o) {
				if (this.getValidator('isEmpty').test(v)) return true;
				if ((!isNaN(v) && !/^\s+$/.test(v))) {
					var num = isNaN(o['num']) ? 0 : parseFloat(o['num']);
					var value = parseFloat(v);
					return (value <= num);
				}
				return false;
			}
		},
		'min(num)' : {//不大于
			error : Msg['errors.min'].replace("{0}","{name}").replace("{1}","{num}"),
			test : function(v, e, o) {
				if (this.getValidator('isEmpty').test(v)) return true;
				if ((!isNaN(v) && !/^\s+$/.test(v))) {
					var num = isNaN(o['num']) ? 0 : parseFloat(o['num']);
					var value = parseFloat(v);
					return (value >= num);
				}
				return false;
			}
		},
		'scaleLength(num)' : {//小数位判断
			error : Msg['errors.scaleLength'].replace("{0}","{name}").replace("{1}","{num}"),
			test : function(v, e, o) {
				if (this.getValidator('isEmpty').test(v)) return true;
				if ((!isNaN(v) && !/^\s+$/.test(v))) {
					//v = parseFloat(v).toString();
					var lasIndex = v.lastIndexOf('.');
					if (lasIndex < 0) return true;
					var num = isNaN(o['num']) ? 0 : parseInt(o['num']);
					var value = v.substr(lasIndex + 1).length;
					return (value <= num);
				}
				return false;
			}
		},
		'before':{//与当前比较判断，不早于当前时间
			error : Msg['errors.time.before'].replace("{0}","{name}"),
			test : function(v,e,o){
				if (this.getValidator('isEmpty').test(v)) return true;
				var today = new Date();
				today.setSeconds(0, 0);
				var temDate = null;
				if(dojoConfig.locale!=null && (dojoConfig.locale!='zh-cn' && dojoConfig.locale!='zh-hk')){
					//temDate = Date.parse(v);
					temDate = Com_GetDate(v);
				}else{
					var arr = v.split(/[^0-9]/);
					temDate = new Date(parseInt(arr[0],10),parseInt(arr[1],10)-1,parseInt(arr[2],10),arr[3]==null?0:parseInt(arr[3],10),
							arr[4]==null?0:parseInt(arr[4],10),arr[5]==null?0:parseInt(arr[5],10),arr[6]==null?0:parseInt(arr[6],10),
							arr[7]==null?0:parseInt(arr[7],10)).getTime();
				}
				
				// 格式:dd/MM/yyyy
				if(dojoConfig.Date_format && dojoConfig.Date_format.match(/^d.*M.*y.*$/)){
					var arr = v.split(/[^0-9]/);
					temDate = new Date(parseInt(arr[2],10),parseInt(arr[1],10)-1,parseInt(arr[0],10),arr[3]==null?0:parseInt(arr[3],10),
							arr[4]==null?0:parseInt(arr[4],10),arr[5]==null?0:parseInt(arr[5],10),arr[6]==null?0:parseInt(arr[6],10),
							arr[7]==null?0:parseInt(arr[7],10)).getTime();
				}
				
				if(temDate <= today.getTime())
					return true;
				return false;
			}
		},
		'after':{//与当前比较判断，不晚于当前时间
			error : Msg['errors.time.after'].replace("{0}","{name}"),
			test : function(v,e,o){
				if (this.getValidator('isEmpty').test(v)) return true;
				var today = new Date();
				today.setSeconds(0, 0);
				var temDate = null;
				if(dojoConfig.locale!=null && (dojoConfig.locale!='zh-cn' && dojoConfig.locale!='zh-hk')){
					//temDate = Date.parse(v);
					temDate = Com_GetDate(v);
				}else{
					var arr = v.split(/[^0-9]/);
					temDate = new Date(parseInt(arr[0],10),parseInt(arr[1],10)-1,parseInt(arr[2],10),arr[3]==null?0:parseInt(arr[3],10),
									arr[4]==null?0:parseInt(arr[4],10),arr[5]==null?0:parseInt(arr[5],10),arr[6]==null?0:parseInt(arr[6],10),
									arr[7]==null?0:parseInt(arr[7],10)).getTime();
				}
				
				// 格式:dd/MM/yyyy
				if(dojoConfig.Date_format && dojoConfig.Date_format.match(/^d.*M.*y.*$/)){
					var arr = v.split(/[^0-9]/);
					temDate = new Date(parseInt(arr[2],10),parseInt(arr[1],10)-1,parseInt(arr[0],10),arr[3]==null?0:parseInt(arr[3],10),
									arr[4]==null?0:parseInt(arr[4],10),arr[5]==null?0:parseInt(arr[5],10),arr[6]==null?0:parseInt(arr[6],10),
									arr[7]==null?0:parseInt(arr[7],10)).getTime();
				}
				
				if(temDate >= today.getTime())
					return true;
				return false;
			}	
		},
		'__date' : {//时间格式判断
			error : Msg['errors.date'].replace("{0}","{name}"),
			test  : function(v) {
				var res=false;
				//格式:yyyy-MM-dd
				var regDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
				if(dojoConfig.locale!=null && (dojoConfig.locale!='zh-cn' && dojoConfig.locale!='zh-hk')){
					//格式:MM/dd/yyyy
					regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				// 格式:dd/MM/yyyy
				if(dojoConfig.Date_format && dojoConfig.Date_format.match(/^d.*M.*y.*$/)) {
					regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				if(v){
					res=regDate.test(v);
				}
				return this.getValidator('isEmpty').test(v) || res;
			}
		},
		'__datetime' : {//时间日期格式判断
			error : Msg['errors.datetime'].replace("{0}","{name}"),
			test  : function(v) {
				var res=false;
				var regDate=/^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
				if(dojoConfig.locale!=null && (dojoConfig.locale!='zh-cn' && dojoConfig.locale!='zh-hk')){
					regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				// 格式:dd/MM/yyyy
				if(dojoConfig.Date_format && dojoConfig.Date_format.match(/^d.*M.*y.*$/)) {
					regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
				}
				
				var regTime=/^[0-2][\d]:[0-5][\d]$/;
				if(v)
				{
					var dateAry=v.split(/\s+/);
					if(dateAry.length==2){
						res = regDate.test(dateAry[0])&&regTime.test(dateAry[1]);
					}
				}
				return this.getValidator('isEmpty').test(v) || res;
			}
		},
		'__time': {//时间格式判断
			error : Msg['errors.time'].replace("{0}","{name}"),
			test  : function(v) {
				var res=false;
				var regTime=/^[0-2][\d]:[0-5][\d]$/;
				if(v){
					res= regTime.test(v);
				}
				return this.getValidator('isEmpty').test(v) || res;
			}
		},
		'phoneNumber' : {
			error : Msg['error.noValidPhoneNumber'],
			test  : function(v, e, o) {
				if (this.getValidator('isEmpty').test(v)) {
					return true;
				}
				//判断是否有-
				if(v.indexOf("-") != -1 ){
					//判断是否有+
					if(v.slice(0, "+".length) === "+"){
						//+86是国内手机号，其他为+XX是不国内手机号
						if(v.slice(0, "+86".length) === "+86") {
							return /^(\+86)-1(\d{10})$/.test(v);
						} else {
							return /^(\+\d{1,5})-(\d{6,11})$/.test(v);
						}
					}else {
						if(v.slice(0, "86".length) === "86") {
							return /^(\86)-1(\d{10})$/.test(v);
						}else {
							return /^(\d{1,5})-(\d{6,11})$/.test(v);
						}
					}
				}else{
					if(v.slice(0, "+".length) === "+"){
						//+86是国内手机号，其他为+XX是不国内手机号
						if(v.slice(0, "+86".length) === "+86") {
							return /^(\+86)1(\d{10})$/.test(v);
						} else {
							return /^(\+\d{1,5})(\d{6,11})$/.test(v);
						}
					}else {
						if(v.slice(0, "86".length) === "86") {
							return /^(\86)1(\d{10})$/.test(v);
						}else {
							//既没有+也没有-的手机号默认为国内手机号
							return /^1(\d{10})$/.test(v);
						}
					}
				}
			}
		},
		'idCard' : {
			error : Msg['error.noValidIDCard'],
			test  : function(v, e, o) {
				var isEmpty = this.getValidator('isEmpty').test(v);
				var idCardFlag = false;
				if(!isEmpty && v.length == 18){
					v = v.toUpperCase();
					var coefficientArr = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
					var sum = 0;
					for(var i = 0;i < coefficientArr.length; i++){
						sum += parseInt(v.charAt(i),10) * coefficientArr[i];
					}
					// 余数数组
					var remainderArr = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ];
					// 身份证号码第18位数组
					var lastArr = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
					var matchDigit = "";
					for (var j = 0; j < remainderArr.length; j++) {
						if (remainderArr[j] == sum % 11) {
							matchDigit = lastArr[j];
						}
					}
					idCardFlag = matchDigit == v.substring(v.length - 1);
				}
				return isEmpty || idCardFlag;
			}
		}
	};
	var Validation = declare("mui.form.validate.Validation",  null , {
		
		serialAttrName:'__validate_serial',
		
		_validateSerialPrefix:'_validate_',
		
		//校验器对象缓存
		Validators:{},
		
		constructor:function(){
			this.inherited(arguments);
			if(!window._validateSerialIndex)
				window._validateSerialIndex = 1;
			this.addValidator('isEmpty','',function(v) {
				return ((v == null) ||v.replace(/^(\s+|\u00A0+)|(\s+|\u00a0+)$/g,"")==''|| (v.length == 0));
			});
			for (var type in Validates) {
				this.addValidator(type, Validates[type].error, Validates[type].test);
			}
		},
		
		//增加校验器
		addValidator:function(type , error , testFun){
			var _parseArray = this._parseValidatorName(type);
			var _type = _parseArray.shift();
			if(!this.Validators[_type]){
				this.Validators[_type] =  new Validator(_type, error, testFun, _parseArray);
			}
		},
		
	    hideWarnHint:function(element) {
			var _Reminder = new Reminder(this._getValidatorDom(element));
			_Reminder.hide();
		},
		
		//获取校验器
		getValidator:function(name){
			var _parseArray = this._parseValidatorName(name);
			var _type = _parseArray.shift();
			var _validator = this.Validators[_type] ? this.Validators[_type] : new Validator();
			_validator.setParamValues(_parseArray);
			_validator.setOwner(this);
			return _validator;
		},
		
		//校验单个组件或dom元素
		validateElement:function(element){
			var validate = this._getValidatorName(element);
			if(!validate) return true;
			this._serializeElement(element);
			var vos = validate.split(' ');
			for(var i=0; i<vos.length; i++){
				var tmpValidate = vos[i].trim();
				if(tmpValidate!=''){
					var tmpValidator = this.getValidator(tmpValidate);
					if(tmpValidator ){
						// 常规的用validator校验即可
						if(tmpValidator instanceof Validator){
							if(!this._doValidateElement(element, tmpValidator)){
								return false;		
							}
						}else if(tmpValidator.doOwnValidate){
							// 可以自定义校验器，执行自己的校验规则，例如：携程控件
							if(!tmpValidator.doOwnValidate(element)){
								return false;		
							}
						}
					}
				}
			}
			return true;
		},
		
		_doValidateElement:function(element,validator){
			var isPass = this._isElementEnable(element) && validator.test(this._getValidatorValue(element), element);
			var options = lang.mixin(validator.options,{
				'name' : this._getValidatorLabel(element), 
				'validator' : validator,
				'widget' : element
			});
			var _reminder = new Reminder(this._getValidatorDom(element) , validator.error, options);
			if(!(/validatorControl_\w+/.test(validator["type"]))){
				if(isPass){
					 _reminder.hide();
				}else{
					var showCallBack = null;
					if(element.showValiReminder && typeof(element.showValiReminder) === "function"){
						showCallBack = lang.hitch(element, "showValiReminder");
					}
					_reminder.show(showCallBack);
				}
			}
			return isPass;
		},
		
		_isElementEnable:function(element){
			return true;
		},
		
		_serializeElement:function(element){
			var serlialDom = this._getValidatorDom(element);
			if(serlialDom!=null){
				if(domAttr.get(serlialDom,this.serialAttrName)==null){
					domAttr.set(serlialDom, this.serialAttrName, this._validateSerialPrefix + window._validateSerialIndex);
					window._validateSerialIndex = window._validateSerialIndex + 1;
				}
			}
			return;
		},
		
		_getValidatorDom:function(element){
			if(element instanceof FormBase){
				return element.domNode;
			}
			return element;
		},
		
		//获取元素的标题
		_getValidatorLabel:function(element){
			if(element instanceof FormBase){
				return element.subject;
			}
			return domAttr.get(element,'subject');
		},
		
		//获取组件的值
		_getValidatorValue:function(element){
			if(element instanceof FormBase){
				return element.get('value');
			}
			var params = domValue.get(element);
			return params ? params[1] : '';
		},
		
		//获取元素的校验信息
		_getValidatorName:function(element){
			var validate = null;
			if(element.getAttribute){
				validate = element.getAttribute("validate");
				if(validate!=null && validate!=''){
					validate = validate.trim();
				}
			}
			if(validate!=null && validate!=''){
				return validate;
			}
			if(element.validate && element.validate!=''){
				return element.validate;
			}
			return validate;
		},
		
		//解析参数key或value
		_parseValidatorName:function(name){
			var _name = name.replace('(', ',').replace(')', '');
			return _name.split(',');
		},
		
		// 增加校验方法
		addElements : function(element , validateName){
			var _elements = new Elements();
			var ele = this._getValidatorDom(element);
			_elements.addElementValiate(ele,validateName);
		},
		
		// 删除校验方法
		removeElements : function(element , validateName){
			var _elements = new Elements();
			var ele = this._getValidatorDom(element);
			_elements.removeElementValiate(ele,validateName);
		}
		
	});
	return Validation;
});
define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-prop", "sys/xform/mobile/controls/xformUtil", "dijit/registry", 
         "mui/form/Input", "dojo/query","dojo/dom", "dojo/dom-class","dojo/dom-attr","mui/form/validate/Reminder","dojo/dom-style",
         "mui/form/validate/Validation","mui/form/_ValidateMixin","dojo/topic","dojo/_base/lang","mui/i18n/i18n!sys-xform-base:mui","dojo/NodeList-traverse"
         ], function(declare, domConstruct, domProp, xUtil, registry, Input, query,
        		 dom,domClass,domAttr,Reminder,domStyle,validation,validateMixin,topic,lang,Msg) {
	var claz = declare("sys.xform.mobile.controls.Validator", [ Input], {
		inputClass: 'muiInput muiFormCalc',
		
		TipCache : [],
		
		validation : new validation(),
		
		buildRendering : function() {
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		
		startup:function(){
			this.inherited(arguments);
			this.domNode.setAttribute("validate","validatorControl_" + this.id);
			domStyle.set(this.domNode,{'display':'inline'});
			var nodes = [];
			if("true"==this.isrow){
				nodes = query(this.domNode).parents(".detailTableNormalTd table tr");
				if(nodes.length==0){
					nodes = query(this.domNode).parents(".detailTableSimpleTd table tr");
				}
			}
			if(nodes.length>0){
				this.xformAreaNode = nodes[0];
			}else{
				/*#140134-合同审批电脑可审批，kk移动端不能审批*/
				/*this.xformAreaNode = document.forms[0];*/
				this.xformAreaNode = document;
			}
			var formulaMethod = Com_GetUrlParameter(window.top.location.href,'method');
			/*if (formulaMethod != "view"){*/
				//切换视图校验框架自动调用,校验不通过，不允许提交
				this.validation.addValidator("validatorControl_" + this.id , null , this.proxy());
				this.subscribe(this.EVENT_VALUE_CHANGED, lang.hitch(this, this._calcValue));
			/*	this.subscribe("performTransition", '_performTransition');//订阅视图切换事件,当点击下一页的时候会触发处理事件
*/		/*	}*/
		},
		
		proxy:function(){//更改上下文
			var self = this;
			return function(){
				return self._performTransition.call(self);
			}
		},
		
		buildEdit: function() {
			this.inherited(arguments);
		},
		
		buildHidden: function(){
			this.inherited(arguments);
		},
		
		buildView:function(){
			this.inherited(arguments);
		},
		
		_performTransition:function(){
			if(lbpm && lbpm.globals.saveDraft){
				return true;
			}
			var result = this._calc();
			var isSubmit = true;
			for (var i = 0; i < result.length; i++){
				var obj = result[i];
				if (obj){
					var expression = obj.expression || {};
					if (!obj.isPass && expression.expressionShow == "0" 
							&& !obj.isEmpty){
						isSubmit = false;
					}
					if (obj.isPass && expression.expressionShow == "1" 
							&& !obj.isEmpty){
						isSubmit = false;
					}
				}
			}
			return isSubmit;
		},
			
		_calcValue: function(srcObj , val){
			var isValidate = this._isValidate(srcObj);//过滤跟表达式无关的控件的值改变事件,只有相关的才进行校验
			if(this != srcObj && isValidate){//过滤校验器本身
				this._calc();
			}
			domClass.add(this.domNode,'showTitle');
		},
		_isValidate:function (srcObj){//判断值改变事件源是否跟校验表达式相关
			if (!srcObj){
				return false;
			}
			var tmpName = xUtil.parseXformName(srcObj);
			if (!this.expression){
				return false;
			}
			//this._preDealExpression();
			this.expression = this._htmlUnEscape(this.expression);
			var arr = JSON.parse(this.expression || "");
			var isValidate = false;
			var expressionId;
			for (var i = 0;i < arr.length; i++){
				var expression = arr[i] || {};
				expressionId = expression.expressionId || "";
				if (tmpName!=null &&  tmpName!='' && (expressionId.indexOf(tmpName)>-1)){
					isValidate = true;
					break;
				}
			}
			return isValidate;
		},
		
		htmlEscape : function (s){
			if (s == null || s ==' ') return '';
			  s = s.replace(/&/g, "&#38;");
			  s = s.replace(/\"/g, "&#34;");
			  s = s.replace(/</g, "&#60;");
			  return s.replace(/>/g, "&#62;");
		},
		
		_htmlUnEscape : function  (s){
			if (s == null || s ==' ') return '';
			s = s.replace(/&amp;/g, "&");
			s = s.replace(/&quot;/g, "\"");
			s = s.replace(/&#39;/g, "\'");
			s = s.replace(/&lt;/g, "<");
			return s.replace(/&gt;/g, ">");
		},

		_calc: function(){
			var executeResult = [];
			if("true" == this.validator){ 
				//this._preDealExpression();
				this.expression = this._htmlUnEscape(this.expression);
				if (this.expression==null || this.expression=='' 
					|| this.expression == "undefined") {
					return true;
				}
				var domArr = query('[widgetid]',this.xformAreaNode);
				var arr = JSON.parse(this.expression || "");
				if(domArr.length>0){
					var calcContexts = null;
					for (var index = 0; index < arr.length; index++){
						var expressionId = arr[index].expressionId || "";
						calcContexts={};
						var isEmpty = false;
						//有些表达式相关联的控件都没有生成dom结构,比如套了不可见权限的单行或者其它控件
						var noRelationControl = true;
						for ( var i = 0; i < domArr.length; i++) { //获取所有的表单元素
							var wgt = registry.byNode(domArr[i]);
							if((wgt.name) && (wgt.name).indexOf("extendDataFormInfo.value")!=0){
								continue;
							}
							var tmpName = xUtil.parseXformName(wgt);
							var tmpTextName = tmpName + "_text";
							if(tmpName!=null && tmpName!='' && (expressionId.indexOf(tmpName)>-1)){
								var isGetTextVal = false;
								noRelationControl = false;
								if (wgt.textId){
									var textId = wgt.textId.replace(/\.\d\./,".");
									if (expressionId.indexOf(textId) > -1){
										isGetTextVal = true;
									}
								}
								//判断值改变控件和前端计算控件是否在明细表的同一行
								if(tmpName.indexOf(".") > -1 && "true" == this.isrow){
									if (this._getTableRr(this.domNode) == this._getTableRr(domArr[i])){
										var values = calcContexts[tmpName];
										if(!values){
											values = [];
											calcContexts[tmpName] = [];
										}
										var value = this._parserCalcValue(wgt,expressionId);
										var textValue = this._parserCalcValue(wgt,expressionId,true);
										if (value === "empty"){//如果某个控件的没有输入值,又不是必填则校验通过
											isEmpty = true;
											break;
										}
										values.push(value);
										calcContexts[tmpName] = values;
										values = [];
										values.push(textValue);
										calcContexts[tmpTextName] = values;
									}
								}else{
									if(calcContexts==null){
										calcContexts={};
									}
									var values = calcContexts[tmpName];
									if(!values){
										values = [];
										calcContexts[tmpName] = [];
									}
									var value = this._parserCalcValue(wgt,expressionId);
									var textValue = this._parserCalcValue(wgt,expressionId,true);
									if (value === "empty"){//如果某个控件的没有输入值,又不是必填则校验通过
										isEmpty = true;
										break;
									}
									values.push(value);
									calcContexts[tmpName] = values;
									values = [];
									values.push(textValue);
									calcContexts[tmpTextName] = values;
								}
							}
						}
						//如果某个控件的没有输入值,又不是必填则校验通过
						var result = (isEmpty || noRelationControl)  ? true : this._fillValue(expressionId,calcContexts);
						var tip = this._getTip(expressionId);
						var tipContext = {};
						//result为isEmpty,标识表达式中有变量没有获取到值,这种情况应该校验通过
						if ("isEmpty" === result) {
							tipContext.isEmpty = true;
							result = true;
						}
						tipContext.isPass = result;
						tipContext.expressionId = expressionId;
						if (isEmpty || tipContext.isEmpty){
							tipContext.isEmpty = true;
							executeResult.push({isPass:result,expression:arr[index],"isEmpty": true});
						}else{
							executeResult.push({isPass:result,expression:arr[index]});
						}
						this._showTip(tipContext,this.domNode,tip);
					}
				}
			}
			return executeResult;
		},
		/**
		 * 显示或隐藏提示信息
		 * @param rtnVal
		 * @param dom
		 * @returns
		 */
		_showTip:function (rtnVal,dom,tip){
			var expression = this._findExpression(rtnVal.expressionId);
			
			if (expression){
				var expressionShow = expression.expressionShow == 1;
				var cache = this._findTipCache(expression.expressionId,dom);
				
				//若输入控件为空值,则忽视校验规则,隐藏提示
				if (rtnVal.isEmpty){
					if (cache && cache.isShow ){
						this._isShowAdvice(false,cache.advice);
						cache.isShow = false;
					}
					return ;
				}
				
				if (cache){
					if (expression.expressionShow == 1){//通过显示
						if (rtnVal.isPass){//结果通过
							if (!cache.isShow){//隐藏改为显示
								this._isShowAdvice(true,cache.advice);
								cache.isShow = true;
							}
						}
						if (!rtnVal.isPass){//结果不通过
							if (cache.isShow){//显示改为隐藏
								this._isShowAdvice(false,cache.advice);
								cache.isShow = false;
							}
						}
					}else{//不通过显示
						if (!rtnVal.isPass){//结果不通过
							if (!cache.isShow){//隐藏改为显示
								this._isShowAdvice(true,cache.advice);
								cache.isShow = true;
							}
						}
						if (rtnVal.isPass){//结果通过
							if (cache.isShow){//显示改为隐藏
								this._isShowAdvice(false,cache.advice);
								cache.isShow = false;
							}
						}
					}
				}else{
					if (expression.expressionShow == 1 && rtnVal.isPass){//校验通过提示
						this._initTipElement(expression.expressionId, dom, tip);
					}else if (expression.expressionShow == 0 && !rtnVal.isPass){//校验不通过提示
						this._initTipElement(expression.expressionId, dom, tip);
					}
				}
			}
			
		},
		
		//查找表达式
		_findExpression : function(targetExpressionId){
			var expressions = JSON.parse(this.expression || "");
			var param;
			var expressionId;
			for (var i = 0;i < expressions.length; i++){
				param = expressions[i];
				expressionId = param.expressionId || "";
				if (expressionId.replace(/\s/g,"") === targetExpressionId.replace(/\s/g,"")){
					return param;
				}
			}
		},
		
		_isShowAdvice : function(isShow,advice){
			if (isShow){
				domStyle.set(advice,{'display':'block'});
			}else{
				domStyle.set(advice,{'display':'none'});
			}
		},
		
		//查找提示缓存
		_findTipCache : function(targetExpressionId,dom){
			for (var i = 0; i < this.TipCache.length; i++){
				if (this.TipCache[i] && 
						targetExpressionId == this.TipCache[i].expressionId 
						&& dom == this.TipCache[i].executeDom ){
					return this.TipCache[i];
				}
			}
			return null;
		},
		
		//创建提示dom元素
		_initTipElement : function(expressionId, dom, tip){
			var showTip = this.htmlEscape(tip);
			var reminder = new Reminder(dom.parentNode, showTip);
			//序列化提示信息id,否则view页面校验不通过提示信息会被隐藏
			domAttr.remove(dom.parentNode,this.validation.serialAttrName);
			this.validation._serializeElement(dom.parentNode || dom);
			var validate_id = domAttr.get(dom.parentNode,this.validation.serialAttrName)
			reminder.show = this._validaorReminderShow;
			reminder._buildRenderDom = this._buildRenderDom;
			reminder.show(validate_id);
			var advice = reminder.renderDom;
			this.TipCache.push({expressionId:expressionId,executeDom:dom,advice:advice,isShow:true});
		},
		
		_buildRenderDom: function(id){
			this.renderDom = domConstruct.place("<div></div>",this.element,'last');
			domClass.add(this.renderDom,'muiValidate');
			domAttr.set(this.renderDom,"id",id);
			this.contentDom = domConstruct.create("div", {className:'muiValidateContent'}, this.renderDom);
			domConstruct.create("div", {'className':'muiValidateShape'}, this.contentDom);
			var infoDiv = domConstruct.create("div", {className:'muiValidateInfo'}, this.contentDom);
			domConstruct.create("span", {'className':'muiValidateIcon'}, infoDiv);
			domConstruct.create("span", {'className':'muiValidateMsg',innerHTML:this._resolverError()}, infoDiv);
		},
		
		_validaorReminderShow:function(reminderId){
			this._buildRenderDom(reminderId);
			domStyle.set(this.renderDom,{'display':'block'});
		},
		
		_getTip:function (targetExpressionId){
			if (!targetExpressionId){
				return Msg["mui.validator.checkFailMsg"];
			}
			var expression = JSON.parse(this.expression || "");
			for (var i = 0;i < expression.length; i++){
				var  expressionId = expression[i].expressionId;
				if (expressionId.replace(/\s/g,"") === targetExpressionId.replace(/\s/g,"")){
					return expression[i].message ||  Msg["mui.validator.checkFailMsg"];
				}
			}
			return Msg["mui.validator.checkFailMsg"];
		},
		
		_isDetailTableRr : function(dom) {
			if (dom.tagName == 'TR' && "true" != dom.getAttribute('data-celltr')) {
				for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
					if (parent.tagName == 'TABLE') {
						return (/^TABLE_DL_/.test(parent.id));
					}
				}
				return true;
			}
			return false;
		},
		_getTableRr : function(dom) {
			for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
				if (this._isDetailTableRr(parent)) {
					return parent;
				}
			}
			return null;
		},
		_fillValue:function(expressionId,calcContexts){
			if(calcContexts==null || calcContexts=={}){
				return ;
			}
			var scriptStr = expressionId;
			var re = new RegExp();
			
			for(var key in calcContexts){
				var valStr = "";
				valStr = calcContexts[key].join(" + ");
				re.compile("\\$" + key + "\\$", "gi");
				scriptStr = scriptStr.replace(re , valStr);
			}
			scriptStr = scriptStr.replace(".name","");
			if(scriptStr.indexOf("$")>-1) return "isEmpty";//替换后，还有变量说明某些控件被权限控件设置为不可见，导致获取不到值
			var calcValue = false;
			try{
				calcValue = new Function('return (' + scriptStr + ');').apply(this);
				return calcValue;
			}catch(e){
				calcValue = false;
				return calcValue;
			}
		},
			
		_parserCalcValue:function(wgt,expressionId,isTextVal){
			//为了添加工时计算函数,兼容地址本 
			var empty = "empty";
			if (wgt.declaredClass == "mui.form.Address" || 
					wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"){
				if(!(wgt.get("validate") && wgt.get("validate").indexOf("required") > -1) 
						&& wgt.curIds == ""){ //没有值又不是必填,返回标识符
					return empty;
				}
				var tmpName = xUtil.parseXformName(wgt);
				if (expressionId && expressionId.indexOf("$" + tmpName + "$.name") > -1){
					return "'" + wgt.curNames + "'";
				}
				return "'" + wgt.curIds + "'";
			}
			var type = this._getDataTypeObj(wgt);
            var dataType = type.dataType || "String";
            dataType = dataType.replace("[]","");
            dataType = dataType.toLocaleLowerCase();
			var val = wgt.get('value');
			if(!(wgt.get("validate") && wgt.get("validate").indexOf("required") > -1) 
					&& (val == "" || val == null)){//没有值又不是必填,返回标识符
				return empty;
			}
			val = "" + val;
			var isTime = (dataType == "datetime" || dataType=="time") && /(\d:)/g.test(val);
			var isDate1 = /^\d{4}-\d{2}-\d{2}/.test(val);
			var isDate2 = /^\d{2}\/\d{2}\/\d{4}/.test(val);
			//#166988 添加年月判断
			var isDate3 = /^\d{4}-\d{2}/.test(val);
			var isDate4 = /^\d{2}\/\d{4}/.test(val);
			var d,t;
			if (isTime && (isDate1 || isDate2)) {
				var tmp = val.split(/ +/);
				d = this._parseDate(tmp[0], isDate1);
				t = this._parseTime(tmp[1]);
				return (new Date(d[0], parseFloat(d[1]) - 1, d[2], t[0], t[1], [2])).getTime();
			}
			if (isTime) {
				t = this._parseTime(val);
				var time = 0;
				for(var i = 0;i < t.length;i++){
					time += parseFloat(t[i] * Math.pow(60,t.length - i));
				}
				time = time * 1000;
				return time;
			}
			if ((isDate1 || isDate2)) {
				d = this._parseDate(val, isDate1);
				return (new Date(d[0], parseFloat(d[1]) - 1, d[2])).getTime();
			}
			//#166988 添加年月判断
			if (isDate3||isDate4){
				// 只判断年月类型“yyyy-mm”
				if (isDate3 && val.split("-").length == 2){
					return (new Date(val)).getTime();
					// 英文：“mm/yyyy”
				}else if (isDate4 && val.split("/").length == 2){
					let d = val.split("/");
					return (new Date(d[1],d[0])).getTime();
				}
			}
			val = val.replace(/\,/g,"");
			if (type &&  type["dataType"] && type["dataType"].toLocaleLowerCase() === "string"){
				if (isTextVal){
					val = wgt.get("text");
				}
				val = val || "";
                val = val.replace(/'/g,"&#39;");
				//#172532
				val = val.replace(/\n/g,"");
				val = val.replace("<br/>","");
				val = "'" + val +"'";
			}else{
				val =  parseFloat(val);
			}
			return val;
		},
		
		_parseDate:function(dateStr,chinaFmt){
			var rtn;
			if (chinaFmt) {
				return dateStr.split('-');
			} else {
				rtn = [];
				var tmpArray = dateStr.split('/');
				rtn[0] = tmpArray[2];
				rtn[1] = tmpArray[0];
				rtn[2] = tmpArray[1];
			}
			return rtn;
		},
		
		_parseTime:function(timeStr){
			return timeStr.split(':');
		},
		
		/**
		 * 根据dom元素获取控件类型和数据类型
		 * @param objs
		 * @returns
		 */
		_getDataTypeObj:function(wgt){
			var obj = this._getControlTypeByObj(wgt);
			var dataType = "string";
			var controlType = "";
			if (obj && obj["controlType"] === "inputText"){
				var tempType = wgt.get("datatype");
				if (tempType === "number"){
					dataType = "number";
				}else{
					if (wgt.get("validate")&& wgt.get("validate").indexOf("number") > -1){
						dataType = "number";
					}
				}
				controlType = "inputText";
			}
			if (obj){
				if (obj["controlType"] === "datetime"){
					dataType = "datetime";
					controlType = "datetime";
				}
				if (obj["controlType"] === "calculation"){
					dataType = "number";
					controlType = "calculation";
				}
				if (obj["controlType"] === "address" 
					|| obj["controlType"] === "new_address"){
					dataType = "string";
					controlType = "address";
				}
				if (obj["controlType"] === "inputRadio"){
					dataType = wgt.get("datatype");
					controlType = "inputRadio";
				}
				if (obj["controlType"] === "select"){
					dataType = wgt.get("datatype");
					controlType = "select";
				}
			}
			return {dataType:dataType,controlType:controlType};
		},
		
		/**
		 * 根据dom元素获取控件类型
		 * @param objs
		 * @returns
		 */
		_getControlTypeByObj:function(wgt){
			if (wgt == null){
				return "";
			}
			var name = wgt.get("name");
			var srcNodeRef = wgt.domNode;
			var xformflag = dom.byId("_xform_" + name);
			var controlType = xformflag.getAttribute("_xform_type");
			return {controlType:controlType,dom:srcNodeRef};
		},
		
		_default_value:function(value, defaultValue){
			if (value == null || isNaN(value)) {
				return defaultValue;
			}
			return value;
		},
		
		_preDealExpression:function(){//预处理表达式，避免html代码混入
			var scriptDom = domConstruct.create("div",{innerHTML:this.expression});
			this.expression = domProp.get(scriptDom,"textContent");
		}
	});
	return claz;
});
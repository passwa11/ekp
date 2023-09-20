define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-prop", "sys/xform/mobile/controls/xformUtil", "dijit/registry", 
         "mui/form/Input", "dojo/query","dojo/dom", "dojo/dom-class","dojo/dom-attr","mui/i18n/i18n!sys-xform-base", "dojo/dom-style",
         "mui/util","mui/dialog/Tip","dojo/ready","dojo/_base/lang"], function(declare, domConstruct, domProp, xUtil, registry, Input, query,dom,domClass,domAttr,Msg,domStyle,util,Tip,ready,lang) {
	var claz = declare("sys.xform.mobile.controls.Calculation", [ Input ], {
		inputClass: 'muiInput muiFormItem muiFormCalc',
		
		_calcFun:{'XForm_CalculatioFuns_Sum':'this._detail_sum',
		          'XForm_CalculatioFuns_Count':'this._detail_count',
		          'XForm_CalculatioFuns_Avg':'this._detail_avg',
		          'XForm_CalculatioFuns_DayDistance':'this._date_distance',
		          'XForm_CalculatioFuns_TimeDistance':'this._time_distance',
		          'XForm_CalculatioFuns_DefaultValue':'this._default_value',
		          "XForm_CalculationFuns_manHoursHourDistance" : 'this._manHours_hourDistance'},
		          
		scaleInt:null,
		
		isReadOnly : "true",
		
		opt : true,
		
		isInDetail : false,
		
		expression : "",

		buildRendering : function() {
	    	var scale = 0;
	    	if(this.scale!=null && this.scale!=''){
	    		this.scaleInt = parseInt(this.scale, 10);
	    	}
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},

		showRight: function (show) {
			if (this.rightIcon) {
				if(show!==''){
					domClass.toggle(this.rightIcon, "muiSelInputRightShow", show);
				}else{
					domClass.toggle(this.rightIcon, "muiSelInputRightShow", true);
				}
			}
		},

		startup:function(){
			this.inherited(arguments);
			var nodes = [];
//			if("true"==this.isrow){
//				nodes = query(this.domNode).parents(".detailTableNormalTd table tr");
//				if(nodes.length==0){
//					nodes = query(this.domNode).parents(".detailTableSimpleTd table tr");
//				}
//			}
//			if(nodes.length>0){
//				this.xformAreaNode = nodes[0];
//			}else{
				this.xformAreaNode = document.forms[0];
//			}
			// 新建的时候，需要执行自动计算，以防变量里面有默认值
			if(util.getUrlParameter(location.href,"method") == "add"){
				this._calc();
			}
			this.isInDetail = xUtil.isInDetail(this);
			this.subscribe(this.EVENT_VALUE_CHANGED, '_calcValue');
			this.subscribe("/mui/form/rowValueChanged", '_calcValue');
			this.bindEvents();
		},
		bindEvents: function (){
			// #159716 套了权限区段的前端计算的可见状态通过提交事件添加数字校验
			ready(lang.hitch(this,function(){
				if(typeof this.inputNode != 'undefined' && this.inputNode.type && this.inputNode.type === 'hidden'){
					var self = this;
					Com_Parameter.event["submit"].push(function (){
						var isReturn = true;
						var val = self.inputNode.value;
						if(!(!isNaN(val) && !/^\s+$/.test(val)&& /^.{1,20}$/.test(val) && /(\.)?\d$/.test(val) )){
							if(val.indexOf("Infinity")>-1){
								Tip.fail({text:val+"不是一个有效数字!!!"});
								isReturn = false;
							}
						}
						return isReturn;
					});
				}

			}));

		},

        /**
         * 设置是否允许提交, 当notAllowSubmit为true并且开始时间大于结束时间, 则不允许提交
         * @param startDate 开始时间
         * @param endDate 结束时间
         * @param notAllowSubmit 是否允许提交，默认允许
         * @constructor
         */
        setAllowSubmit: function(startDate, endDate, notAllowSubmit) {
            if((startDate > endDate || (!isNaN(startDate) && isNaN(endDate))) && notAllowSubmit) {
                domAttr.set(this.domNode, "notAllowSubmit", "true");
            } else {
                domAttr.set(this.domNode, "notAllowSubmit", "false");
            }
        },

        /**
         * 日期类函数, 如果前端计算没有勾选默认参数为并且只要有一个参数为空, 则计算结果返回null
         * @param startDate 开始时间
         * @param endDate   结束时间
         * @returns {boolean} true表示计算结果返回NaN
         */
        isReturnNaN: function (startDate, endDate) {
            if (!this.isParamDefault() && (((isNaN(startDate) &&  isNaN(endDate)) || (isNaN(startDate) && !isNaN(endDate))))) {
                return true;
            }
            return false;
        },

        isParamDefault: function() {
            return this.paramdefault === "true";
        },

		buildOptIcon: function(optContainer) {
			if(this.isReadOnly == "false"){
				var calcBtn = domConstruct.create("span", {
					className : 'mui mui-calc'
				}, optContainer);
				this.connect(calcBtn, 'click', '_calcPrevent');
			}
		},

		//阻止冒泡
		_calcPrevent:function(event){
			event = event || window.event;
			if (event.preventDefault){
				event.preventDefault();
			}
			if (event.stopPropagation) {
				event.stopPropagation();
			}
			this._calc();
		},

		buildEdit: function() {
			if(this.isReadOnly == "true"){
				this.buildReadOnly();
				// #159716 前端计算的只读状态也添加数字校验
				if(this.validate){
					this.validate = this.validate + " number";
				}else{
					this.validate = "number";
				}
			}else{
				this.inherited(arguments);
			}
		},
		
		buildView:function(){
			this.inherited(arguments);
			this.inputNode = domConstruct.create('input', {
				name : this.name,
				className : this.inputClass,
				type:'hidden'
			}, this.domNode);
		},
		
		viewValueSet:function(val){
			var num = val + "";
			if (val == null || num == "" || isNaN(num)) {
				this.textNode.innerHTML = "";
				this.inputNode.value = '';
				return;   
			} 
			this.inputNode.value = num;
			if(this.scaleInt!=null){		//小数点显示
				/*num = (parseFloat(num).toFixed(this.scaleInt) + '');*/
				if (this.dataType === "Double"){
					num = this._round(num,this.scaleInt) + '';
				}else{
					num = (parseFloat(num).toFixed(this.scaleInt) + '');
				}
			}
			if('true' == this.thousandshow){//千分位显示
				/*num = num.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');*/
				num = num.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
				//上面的正则表达式会给小数位后面也加上千分位,所以还要把小数点后面的,给替换掉
				var index = num.indexOf(".");
				if (index > 0){
					var substring = num.substring(index);
					substring = substring.replace(/,/g,"");
					num = num.substring(0,index) + substring;
				}
			}
			this.textNode.innerHTML = num;
		},
		
		//获取标准校验的Dom
		getStandardValidateDom:function(){
			//清除所有的校验内容
			var xformflagNode = query(this.domNode).parent("xformflag")[0];
			query(xformflagNode).children("div.muiValidate").remove();
			//提交校验内容
			//if(!this.validateDom){
				var html = "";
				html = "<div class='muiValidate'><div class='muiValidateContent'><div class='muiValidateShape'></div>"
				html += "<div class='muiValidateInfo'><span class='muiValidateIcon'></span><span class='muiValidateMsg'>";
				html += "<span class='muiValidateTitle'>" + this.subject + "</span>"+Msg["mui.calculation.validateMsg"]+"</span></div>";
				html += "</div></div>";
				this.validateDom = domConstruct.toDom(html);
				domConstruct.place(this.validateDom,this.domNode,'after');
			//}
			return this.validateDom;
		},
		
		// 是否是相关控件触发了计算
		_isRelationControl : function(sourceName){
			var expression = this.expression;
			var name = xUtil.parseName(this);
			var flag = false;
			//前端计算控件在明细表
			if(/\.(\d+)\./g.test(name)){
				// sourceName在明细表
				if(/\.(\d+)\./g.test(sourceName)){
					// 判断索引是否一致
					var calIndex = name.match(/\.(\d+)\./g)[0];
					var sourceIndex = sourceName.match(/\.(\d+)\./g)[0];
					if(calIndex == sourceIndex){
						if(expression.indexOf(sourceName.replace(sourceIndex,".")) > -1){
							flag = true;							
						}
					}
				}else{
					if(expression.indexOf(sourceName) > -1){
						flag = true;							
					}
				}
			}else if(this.isInDetail){
				// 前端计算控件在明细表的统计航里面时，name不含明细表ID
				// 清掉索引
				var indexs = sourceName.match(/\.(\d+)\./g);
				indexs = indexs?indexs:[];
				if(indexs.length > 0){
					sourceName = sourceName.replace(indexs[0],".");
				}
				if(expression.indexOf(sourceName) > -1){
					flag = true;							
				}
			}else{
				sourceName =  sourceName ? sourceName.replace(/\.(\d+)\./g,".") : sourceName;
				if(expression.indexOf(sourceName) > -1){
					flag = true;							
				}
			}
			return flag;
		},
		
		_calcValue: function(srcObj , argus){
			if(srcObj == null && argus && argus.eventType && argus.eventType == 'detailsTable-delRow'){
				// 明细表删除行时，如果删除行里面含有前端计算控件的表达式相关控件，需要重新计算
				var tableId = argus.tableId;
				if(this.expression && this.expression.indexOf(tableId) > -1){
					if(!xUtil.isInDetail(this) || xUtil.isInStatisticTr(this)){
						//加个属性判断是否删除 用于计算删除到最后一个的时候的逻辑处理
						this.isDeleteCount = true;
						this._calc();
					}
				}
			}
			//明细表添加行自动计算
			else if (srcObj == null && argus && argus.eventType && argus.eventType == 'detailsTable-addRow'){
				if(this.isReadOnly == 'false'){
					// 可编辑的前端计算控件，同行才算
					if(argus.row){
						//if(query(this.domNode).parents(argus.row).length > 0){
							this._calc();
						//}
					}
				}else{
					this._calc();//不做范围限制，有可能是明细表内的前端计算控件计算明细表外的					
				}
			}
			else if(srcObj != null && this != srcObj){
				// 判断源控件是否是表达式里面中的变量，只有相关控件才能出发计算
				var tmpName = xUtil.parseName(srcObj);
				if(tmpName!=null && tmpName!='' && this._isRelationControl(tmpName)){
					this._calc();					
				}
			}
			domClass.add(this.domNode,'showTitle');
		},
		_calc: function(){
			if("true" == this.calculation){
				this._preDealExpression();
				if (this.expression==null || this.expression=='') {
					return;
				}
				var domArr = query('[widgetid]',this.xformAreaNode);
				if(domArr.length>0){
					var calcContexts = null;
					for ( var i = 0; i < domArr.length; i++) {
						var wgt = registry.byNode(domArr[i]);
						var tmpName = xUtil.parseXformName(wgt);
						if(tmpName!=null && tmpName!='' && (this.expression.indexOf(tmpName)>-1)){
							//修复 移动端,前端计算控件在使用新的明细表组件情况下,计算错误问题 by liwenchang
							//判断值改变控件和前端计算控件是否在明细表的同一行
							if(tmpName.indexOf(".") > -1 && "true" == this.isrow){
								if (this.XForm_CalculationGetTableRr(this.domNode) == this.XForm_CalculationGetTableRr(domArr[i])){
									if(calcContexts==null){
										calcContexts={};
									}
									var values = calcContexts[tmpName];
									if(!values){
										values = [];
										calcContexts[tmpName] = [];
									}
									values.push(this._parserCalcValue(wgt));
									calcContexts[tmpName] = values;
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
								values.push(this._parserCalcValue(wgt));
								calcContexts[tmpName] = values;
							}
						}
					}
					this._fillValue(calcContexts);
				}
			}
		},
		XForm_CalculationIsDetailTableRr : function(dom) {
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
		XForm_CalculationGetTableRr : function(dom) {
			for (var parent = dom.parentNode; parent != null; parent = parent.parentNode) {
				if (this.XForm_CalculationIsDetailTableRr(parent)) {
					return parent;
				}
			}
			return null;
		},
		_fillValue:function(calcContexts){
			/*if(calcContexts==null || calcContexts=={}){
				return ;
			}*/
			var scriptStr = this.expression;
			var re = new RegExp();
			var isFun = false;
			for(var funKey in this._calcFun){
				if(scriptStr.indexOf(funKey)>-1){
					re.compile("\\$" + funKey + "\\$", "gi");
					scriptStr = scriptStr.replace(re,this._calcFun[funKey]);
					if(!isFun){
						isFun = true;
					}
				}
			}
			for(var key in calcContexts){
				var valStr = "";
				if(isFun){
					valStr = calcContexts[key].join(", ");
				}else{
					valStr = calcContexts[key].join(" + ");
					valStr = "(" + valStr + ")";
				}
				re.compile("\\$" + key + "\\$", "gi");
				scriptStr = scriptStr.replace(re , valStr);
			}
			//前端计算控件增加了参数是否默认为0的配置
			if(this.paramdefault && this.paramdefault != null && this.paramdefault == 'true'){
				scriptStr = this._setDefaultVal(scriptStr);
			}
			if(scriptStr.indexOf("$")>-1) return;//替换后，还有变量则不处理
			var calcValue = 0;
			try{
				calcValue = new Function('return (' + scriptStr + ');').apply(this);
				if (calcValue!= null&&calcValue!= ''&&!isNaN(calcValue)) {
					var c = calcValue.toString();
					if (/\.\d*999999/.test(c) || /\.\d*0000000/.test(c)) {
						var _m = Math.pow(10, 6);
						calcValue = Math.round(parseFloat(calcValue)*_m)/_m;
					}
				}else{
                    if (calcValue == null || isNaN(calcValue)) {
                        calcValue = '';
                    } else {
                        calcValue = 0;
                    }
				}
			}catch(e){
                if (calcValue == null || isNaN(calcValue)) {
                    calcValue = '';
                } else {
                    calcValue = 0;
                }
			}
			if(this.scaleInt!=null && calcValue!=0){
				/*var num = (parseFloat(calcValue + '').toFixed(this.scaleInt) + '');*/
				if (this.dataType === "Double" || this.dataType === "BigDecimal" 
					|| this.dataType === "BigDecimal_Money" ){
					var num = this._round(calcValue,this.scaleInt);
				}else{
					var num = (parseFloat(calcValue + '').toFixed(this.scaleInt) + '');
				}
				if(num===0){
					num=num+"";
				}
				this.set("value",num);
				$("div[_name='" + this.name +"']").each(function () {
					var markAttr = $(this).attr('mark');
					if(markAttr === 'autoAdaptionView') {
						this.innerHTML = num;
					}
				})
			}else{
				if(calcValue===0){
					calcValue=calcValue+"";
				}
				this.set("value",calcValue);
				$("div[_name='" + this.name +"']").each(function () {
					var markAttr = $(this).attr('mark');
					if(markAttr === 'autoAdaptionView') {
						this.innerHTML = calcValue;
					}
				})
			}
		},
		
		_round: function(a, b) {
			var result = (Math.round( this._accMul(a,Math.pow(10, b))) / Math.pow(10, b));
			if (result && b && b > 0) {
				if(result == "Infinity" || result == Infinity){//#170397 如果是无穷大，则不保留小数位，直接返回，与pc端保持一致
					return result;
				}
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
		},
		
		_accMul : function (arg1,arg2){
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
		},
		
		_setDefaultVal:function(scriptStr){
			var re = new RegExp();
			re.compile("\\$(\\w)+(\\.)?(\\w)+\\$", "gi");
			return scriptStr.replace(re , 0);
		},
		_parserCalcValue:function(wgt){
			//为了添加工时计算函数,兼容地址本 
			if (wgt.declaredClass == "mui.form.Address" || 
					wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"){
				return "'" + wgt.curIds + "'";
			}
			var val = wgt.get('value');
			if (val == '' || val == null) {
				//前端计算控件增加了参数是否默认为0的配置 by zhugr 2017-03-29
				if(this.paramdefault && this.paramdefault != null && this.paramdefault == 'true'){
					return 0;
				}
				return NaN;
			}
			val = "" + val;
			var isTime = /(\d:)/g.test(val);
			var isDate1 = /^\d{4}-\d{2}-\d{2}/.test(val);
			var isDate2 = /^\d{2}\/\d{2}\/\d{4}/.test(val);
			calculation_isTime = {};
			calculation_isTime = {isTime:(isTime && (!isDate1 && !isDate2))};
			var d,t;
			if (isTime && (isDate1 || isDate2)) {
				calculation_isTime.isDateTime = true;
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
				// 返回毫秒数 by zhugr 2017-07-06
				time = time * 1000;
				return time;
			}
			if ((isDate1 || isDate2)) {
				calculation_isTime.isDate = true;
				d = this._parseDate(val, isDate1);
				return (new Date(d[0], parseFloat(d[1]) - 1, d[2])).getTime();
			}
			val = val.replace(/\,/g,"");
			return parseFloat(val);
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
		
		_default_value:function(value, defaultValue){
			if (value == null || isNaN(value)) {
				return defaultValue;
			}
			return value;
		},

        showValidateDom : function() {
            this.getStandardValidateDom().style.display = 'block';
        },

        hideValidateDom : function() {
            this.getStandardValidateDom().style.display = 'none';
        },
		
		_date_distance:function(startDate,endDate,notAllowSubmit){
			var timeDifference = 0;
			this.setAllowSubmit(startDate, endDate, notAllowSubmit);
			try{
				if(parseFloat(endDate) < parseFloat(startDate)  || (!isNaN(startDate) && isNaN(endDate))){
					if (!notAllowSubmit) {
                        this.showValidateDom();
                    }
                    return NaN;
				}else{
                    if (!this.notAllowSubmit) {
                        this.hideValidateDom();
                    }
				}
                if (this.isReturnNaN(startDate, endDate)) {
                    return NaN;
                }
				var defaultVal = 1;
				if (startDate === 0 && endDate === 0) {
					defaultVal = 0;
				}
				timeDifference = parseInt((parseFloat(endDate) - parseFloat(startDate))/86400000 + defaultVal); 
			}catch(e){}
			return timeDifference;
		},
		
		_time_distance:function(startDate,endDate, notAllowSubmit){
			var timeDifference = 0;
            this.setAllowSubmit(startDate, endDate, notAllowSubmit);
			try{
				endData = parseFloat(endDate);
				startDate = parseFloat(startDate);
				if(endData < startDate  || (!isNaN(startDate) && isNaN(endDate))){
                    if (!notAllowSubmit) {
                        this.showValidateDom();
                    }
                    return NaN;
				}else{
                    if (!this.notAllowSubmit) {
                        this.hideValidateDom();
                    }
				}
                if (this.isReturnNaN(startDate, endDate)) {
                    return NaN;
                }
				timeDifference = endData - startDate;
			}catch(e){}
			return timeDifference;
		},
		_detail_sum:function(){
			var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
			var sun = 0, num;
			for (var i = 0; i < array.length; i ++) {
				num = parseFloat(array[i]);
				if (isNaN(num)) num = 0;
				sun = this._accAdd(sun,num);
			}
			return sun;
			
		},
		_detail_avg:function(){
			var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
			var sun = 0, num, count = 0;
			for (var i = 0; i < array.length; i ++) {
				num = parseFloat(array[i]);
				if (isNaN(num)) {
					continue;
				}
				sun = this._accAdd(sun,num);
				count++;
			}
			return (sun / count);
		},
		_detail_count:function(){
			var array = (arguments.length > 1 || !(arguments[0] instanceof Array)) ? arguments : arguments[0];
			var sun = 0;
			//#107278 问题
			if(array.length == 1 && array[0] == 0 && this.isDeleteCount == true){
				this.isDeleteCount == false;
				return sun;
			}
			for (var i = 0; i < array.length; i ++) {
				if (array[i] != null && array[i] !== '' && !isNaN(array[i])) {
					sun++;
				}
			}
			return sun;
		},
		_accAdd:function(num1,num2){ 
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
		},
		/**
		 * 格式化日期,转为yyyy-mm-dd格式
		 * @param date
		 * @returns
		 */
		formatDateTime: function(date) {
			
			var _year = date.getFullYear()

			var _month = date.getMonth();

			var _date = date.getDate();

			_month = _month + 1;

			if(_month < 10){_month = "0" + _month;}

			if(_date<10){_date="0"+_date  }

			return  _year + "-" + _month + "-" + _date;
		},
		
		/**
		 * 工时计算函数
		 */
		_manHours_hourDistance : function(userId,startDate,endDate,dateType,notAllowSubmit){
			//如果没有传入人员,则采用自然日计算
			if (!userId){
				var rs = (parseFloat(this._time_distance(startDate, endDate)))/3600000;
				//没有配置小数位数，则默认为2位
				return this._round(rs, this.scaleInt || 2);
			}
            this.setAllowSubmit(startDate, endDate, notAllowSubmit);
			if (calculation_isTime.isTime){
				var date = new Date();
				date = this.formatDateTime(date);
				d = this._parseDate(date, "-");
				date = (new Date(d[0], parseFloat(d[1]) - 1, d[2])).getTime();
				startDate = new Date(date + startDate).getTime();
				endDate = new Date(date + endDate).getTime();
			}
			//校验
			if(endDate < startDate || (!isNaN(startDate) && isNaN(endDate))){
                if (!notAllowSubmit) {
                    this.showValidateDom();
                }
                return NaN;
			}else{
                if (!notAllowSubmit) {
                    this.hideValidateDom();
                }
			}
			var isDate = calculation_isTime.isDate;
			if(dateType){
				isDate = dateType=="date";
			}
			if (isDate){
				endDate += 86400000;
			}
			var manHoursTimeDifference = 0;
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
            if (this.isReturnNaN(startDate, endDate)) {
                return NaN;
            }
			var rs = (parseFloat(manHoursTimeDifference))/3600000;
			// 最多保留一位小数
			return this._round(rs, this.scaleInt || 2);
		},
		_preDealExpression:function(){//预处理表达式，避免html代码混入
			var scriptDom = domConstruct.create("div",{innerHTML:this.expression});
			this.expression = domProp.get(scriptDom,"textContent");
		},
		validateFun:function(){//提供扩展校验的方法
            var xformflagNode = query(this.domNode).parent("xformflag")[0];
            var validateDom = query(xformflagNode).children("div.muiValidate");
            var isShow = false;
            var notAllowSubmit = domAttr.get(this.domNode, "notAllowSubmit");
            if(notAllowSubmit === "true"){
                return false;
            }
			return true;
		}
	});
	return claz;
});
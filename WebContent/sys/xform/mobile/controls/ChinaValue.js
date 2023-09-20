define([ "dojo/_base/declare", "dojo/dom-construct", "sys/xform/mobile/controls/xformUtil",  
         "mui/form/Input", "dojo/query", "dojo/dom-style", "dojo/dom-class", "dojo/dom-attr", "dojo/NodeList-traverse"
         ], function(declare, domConstruct, xUtil, Input, query, domStyle, domClass, domAttr) {
	var claz = declare("sys.xform.mobile.controls.ChinaValue", [ Input], {

		inputClass: 'muiInput muiFormChina',
		
		_inDetailTable:null,
		
		opt : true,
		
		buildRendering: function(){
			this.inherited(arguments);
        	domClass.add(this.domNode,'muiFormChinaValue');
	    },
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup:function(){
			this.inherited(arguments);
			var nodes = [];
			if("true"==this.isrow && this.relatedid!=null && this.relatedid!='' && this.relatedid.indexOf(".")>-1){
				nodes = query(this.domNode).parents(".detailTableNormalTd table tr");
				if(nodes.length==0){
					nodes = query(this.domNode).parents(".detailTableSimpleTd table tr");
				}
			}
			if(nodes.length>0){
				this.xformAreaNode = nodes[0];
			}else{
				this.xformAreaNode = document.forms[0];
			}
			if(/\.(\d+)\./g.test(this.name)){
				this._inDetailTable = true;
			}else{
				this._inDetailTable = false;
			}
			//若关联控件为前端计算之类的，同时设置了初始值，此时应先绑定值改变事件，再计算初始值
			this.subscribe(this.EVENT_VALUE_CHANGED, '_upperValue');
			
			//去掉状态限制
			this._upper();
			
		},
	
		buildOptIcon: function(optContainer) {
			var chinaBtn = domConstruct.create("i", {
				className : 'mui mui-uper'
			}, optContainer);
			this.connect(chinaBtn, 'click', '_toupper');
		},

		buildEdit: function() {
			this.buildReadOnly();
			domStyle.set(this.inputNode,{'display':'none'});
			this.textNode = domConstruct.create('div', {
				className : "muiInput_View"
			},this.domNode);
			var xformStyle = this.buildXFormStyle();
			 if(xformStyle){
				domAttr.set(this.textNode,"style",xformStyle);
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
			this.textNode.innerHTML = val;
			this.inputNode.value = val;
		},
		
		_upperValue: function(srcObj , val){
			if(this.relatedid!=null && this.relatedid!=''){
				if(srcObj && this != srcObj){
					var evtObjName = srcObj.get("name");
					if(evtObjName == null || evtObjName == ''){
						return;
					}
					var bindDomId = this.relatedid;
					var rowIndex;
					// 获取索引
					if(this._inDetailTable == true){
						rowIndex = this.name.match(/\.(\d+)\./g);
						rowIndex = rowIndex ? rowIndex:[];
					}
					// 只有表单元素才处理
					if(evtObjName.indexOf('.value(') > -1){
						if(/\.(\d+)\./g.test(evtObjName)){
							evtObjName = evtObjName.match(/\((\w+)\.(\d+)\.(\w+)/g)[0].replace("(","");	
						}else{
							evtObjName = evtObjName.match(/\((\w+)/g)[0].replace("(","");
						}
					}else{
						return;
					}
					if(evtObjName != null && evtObjName != ''){
						var originalEvtObjName = evtObjName;
						if(this._inDetailTable == true){
							// 替换索引
							bindDomId = bindDomId.replace(".",rowIndex[0]);	
						} else {
							evtObjName = evtObjName.replace(/\.(\d+)\./g,".");
						}
						if(evtObjName == bindDomId){
							this._upper(originalEvtObjName);			
						}
					}
				}
				
			}
		},
		
		_toupper:function(evt,val){
			this.inputNode.focus();
			this._upper();
		},
		
		_upper: function(evtObjName){
			if(this.relatedid!=null && this.relatedid!=''){
				var wgt = xUtil.getXformWidgetsBlur(this.xformAreaNode, this.relatedid,true);
				var bindDomId = this.relatedid;
				var rowIndex;
				// 获取索引
				if(this._inDetailTable == true){
					rowIndex = this.name.match(/\.(\d+)\./g);
					rowIndex = rowIndex ? rowIndex:[];
				}
				if(this._inDetailTable == true){
					// 替换索引
					bindDomId = bindDomId.replace(".",rowIndex[0]);	
				}
				for(var i = 0;i<wgt.length;i++){
					var name = xUtil.parseName(wgt[i]);
					if (evtObjName && name != evtObjName) {
						continue;
					}
					//当前控件不在明细表内,但是绑定控件可能在明细表内,所以name中的索引替换掉
					if (!this._inDetailTable) {
						name = name.replace(/\.(\d+)\./g,".");
					}
					if(name.indexOf(bindDomId)>-1){
						this._fillValue(wgt[i]);
					}
				}
			}
		},
		
		_fillValue:function(wgt){
			// 用get方法，在wgt组件的值为0的时候，会去node节点的值
			var val = wgt.value;
			if(val == null){
				val = 0;
			}
			var value = val.toString().replace(/,/g,'');
			// 组件里面的值有百分号导致后面匹配不是数字
			value.replace(/%/g,'');
			var chinaStr = this._parseChinaValue(value);
			//若调用父类editValueSet多次时，会因堆栈影响，导致设text的值错误，这里手动设置
			this.inputNode.value = chinaStr;
			this.set("value",chinaStr);
		},
		
		editValueSet : function(value) {
			//this.inputNode.value = value;
			this.textNode.innerHTML = value;
			this.inputNode.value = value;
		},
		
		 _parseChinaValue:function(value){
			var chineseValue = ""; //转换后的汉字金额   
			//数字才做转化
			if(!isNaN(value)){
				//如果大于9999999999999,提示超出可计算范围
				if(value>9999999999999 || value <-9999999999999){
					chineseValue="超出大写可计算范围";
					return chineseValue;
				}
				//如果是负数,前面加"负"字
				if(value<0){
					chineseValue="负";
					value=Math.abs(value);
				}
				var numberValue = new String(Math.round(value * 100)); //数字金额   
				var String1 ='零壹贰叁肆伍陆柒捌玖'; //汉字数字   
				var String2 =' 万仟佰拾亿仟佰拾万仟佰拾元角分'; //对应单位   
				var len = numberValue.length; //   numberValue的字符串长度   
				var Ch1; //数字的汉语读法   
				var Ch2; //数字位的汉字读法   
				var nZero = 0; //用来计算连续的零值的个数   
				var String3; //指定位置的数值   
				if (numberValue == "0") {
					chineseValue = '零元整';
					return chineseValue;
				}
				String2 = String2.substr(String2.length - len, len); //   取出对应位数的STRING2的值   
				for ( var i = 0; i < len; i++) {
					String3 = parseInt(numberValue.substr(i, 1), 10); //   取出需转换的某一位的值   
					if (i != (len - 3) && i != (len - 7) && i != (len - 11)
							&& i != (len - 15)) {
						if (String3 == 0) {
							Ch1 = "";
							Ch2 = "";
							nZero = nZero + 1;
						} else if (String3 != 0 && nZero != 0) {
							Ch1 = '零'
									+ String1.substr(String3, 1);
							Ch2 = String2.substr(i, 1);
							/*#154524-大小写转换控件移动端-开始*/
							if(Ch2 == '角'){
								Ch1 = String1.substr(String3, 1);
							}else{
								Ch1 = '零' + String1.substr(String3, 1);
							}
							/*#154524-大小写转换控件移动端-结束*/
							nZero = 0;
						} else {
							Ch1 = String1.substr(String3, 1);
							Ch2 = String2.substr(i, 1);
							nZero = 0;
						}
						//该位是万亿，亿，万，元位等关键位   
					} else { 
						if (String3 != 0 && nZero != 0) {
							Ch1 = '零'
									+ String1.substr(String3, 1);
							Ch2 = String2.substr(i, 1);
							nZero = 0;
						} else if (String3 != 0 && nZero == 0) {
							Ch1 = String1.substr(String3, 1);
							Ch2 = String2.substr(i, 1);
							nZero = 0;
						} else if (String3 == 0 && nZero >= 3) {
							Ch1 = "";
							Ch2 = "";
							nZero = nZero + 1;
						} else {
							Ch1 = "";
							Ch2 = String2.substr(i, 1);
							nZero = nZero + 1;
						}
						//如果该位是亿位或元位，则必须写上   
						if (i == (len - 11) || i == (len - 3)) {
							Ch2 = String2.substr(i, 1);
						}
					}
					chineseValue = chineseValue + Ch1 + Ch2;
				}
				var String4 =0;
				if(len>2){
					String4=parseInt(numberValue.substr(len - 2, 1), 10);
				}
				//最后一位（分）为0时，加上“整”  
				if (String3 == 0 && String4 == 0) {  
					chineseValue = chineseValue;
				}
				var tempVal = new String(value);
				if (tempVal.indexOf(".") < 0) {
					chineseValue += "整";
				} else {
					var flag = true;
					tempVal = tempVal.substr(tempVal.indexOf(".") + 1);
					for ( var i = 0; i < tempVal.length; i++) {
						if (parseInt(tempVal[i]) !== 0) {
							flag = false;
							break;
						}
					}
					if (flag) {
						chineseValue += "整";
					}
				}
			}
			return chineseValue;
		}
	});
	return claz;
});
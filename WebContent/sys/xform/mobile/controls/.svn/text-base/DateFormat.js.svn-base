define([ "dojo/_base/declare","dojo/dom-construct","sys/xform/mobile/controls/xformUtil",
	"mui/form/Input", "dojo/query", "dojo/dom-style", "dojo/dom-class","dojo/date/locale"
],function(declare,domConstruct,xUtil,Input,query,domStyle,domClass,dateFmt){
	var claz = declare("sys.xform.mobile.controls.DateFormat", [Input], {
		inputClass: 'muiInput muiFormDate',

		_inDetailTable:null,

		opt: true,

		buildRendering: function(){
			this.inherited(arguments);
			domClass.add(this.domNode,'muiFormDateFormat');
		},

		postCreate: function(){
			this.inherited(arguments);
		},

		buildOptIcon: function(optContainer) {
			var chinaBtn = domConstruct.create("i", {
				className : 'mui mui-format'
			}, optContainer);
			this.connect(chinaBtn, 'click', '_toformat');
		},

		buildEdit: function() {
			this.buildReadOnly();
			domStyle.set(this.inputNode,{'display':'none'});
			this.textNode = domConstruct.create('div', {
				className : "muiInput_View"
			},this.domNode);
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


		startup: function(){
			this.inherited(arguments);
			var nodes = [];
			if("true"==this.isRow&& this.relatedid!=null && this.relatedid!=''&&this.relatedid.indexOf(".")>-1){
				nodes = query(this.nodes).parents('.detailTableNormalId table tr');
				if(nodes.length==0){
					nodes = query(this.nodes).parents('.detailTableSimpleId table tr');
				}
			}

			if(nodes.length>0){
				this.xformAreaNode = nodes[0];
			}else {
				this.xformAreaNode = document.forms[0];
			}

			if(/\.(\d+)\./g.test(this.name)){
				this._inDetailTable = true;
			}else{
				this._inDetailTable = false;
			}
			//  给关联控件绑定事件，当值改变时触发
			this.subscribe(this.EVENT_VALUE_CHANGED,'dateFormat');

			this._format();

		},
		dateFormat: function(srcObj,val){
			if(this.relatedid!=null && this.relatedid!=''){
				if(srcObj!=null&& this!=srcObj){

					var eveObjName = srcObj.get("name");
					if(eveObjName==null || eveObjName == ''){
						return;
					}
					// 关联的id
					var bindDomId = this.relatedid;

					var rowIndex;
					// 获取索引
					if(this._inDetailTable == true){
						rowIndex = this.name.match(/\.(\d+)\./g);
						rowIndex = rowIndex ? rowIndex:[];
					}

					// 只有表单元素才处理
					if(eveObjName.indexOf('.value(') > -1){
						if(/\.(\d+)\./g.test(eveObjName)){
							eveObjName = eveObjName.match(/\((\w+)\.(\d+)\.(\w+)/g)[0].replace("(","");
						}else{
							eveObjName = eveObjName.match(/\((\w+)/g)[0].replace("(","");
						}
					}else{
						return;
					}
					if(eveObjName!=null && eveObjName!=''){
						var oriObjName = eveObjName;
						if(this._inDetailTable == true){
							// 替换索引
							bindDomId = bindDomId.replace(".",rowIndex[0]);
						} else {
							eveObjName = eveObjName.replace(/\.(\d+)\./g,".");
						}
						if(eveObjName == bindDomId){
							this._format(oriObjName);
						}

					}

				}

			}

		},

		_toformat: function(evt,val){
			this.inputNode.focus();
			this._format();
		},

		_format: function(oriObjName){
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
					if (oriObjName && name != oriObjName) {
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
		// 开始赋值
		_fillValue: function(wgt){
			var value = wgt.value;
			var dateType = wgt.type;
			if(value == null){
				value = 0;
			}

			var val = this._parseDateValue(value,dateType,this.dateformattype);
			this.inputNode.value = val;
			this.set("value",val);

		},

		editValueSet : function(value) {
			this.textNode.innerHTML = value;
			this.inputNode.value = value;
		},
		splicValue : function(value,dateFormatType){
			var resultValue = "";
			var _number = (value.split('\/')).length-1
			if( _number==1 && value.length==7  ) {
				var yearMonths = value.split('\/');
				var yearTemp = null;
				var monthTemp = null;
				for (var z = 0; z < yearMonths.length; z++) {
					if (yearMonths[z].length === 4) {
						yearTemp = yearMonths[z];
					}
					if (yearMonths[z].length === 2) {
						monthTemp = parseInt(yearMonths[z]) - 1;
					}
				}


				var dateTemp = new Date();
				dateTemp.setFullYear(yearTemp, monthTemp, 1);

				resultValue = this.privateformat(dateTemp,Com_Parameter.Date_format);
			}else {
				resultValue = value;
			}
			return resultValue;
		},
		// 获取格式化的值
		getDateFormatValue : function(oldValue,newValue,dateFormatType){
			var resultValue = "";
			var _number = (oldValue.split('\/')).length-1
			if(oldValue.indexOf(":")>-1 && oldValue.indexOf("\/") == -1){
				// 如果日期格式是   12：30  这种格式 则直接返回
				return oldValue;
			}else{// 其他情况则走格式化转化的代码
				resultValue = this.privateformat(new Date(newValue),dateFormatType);
				//resultValue = new Date(newValue).Format(dateFormatType);
				if(_number ==1 && oldValue.indexOf(":") == -1){
					// 如果日期格式是   2029/05  这种格式 则直接返回
					if(dateFormatType.indexOf("月")>-1){
						resultValue = resultValue.substring(0,8);
					}else{
						resultValue = resultValue.substring(0,7);
					}
				}
				return resultValue;
			}

		},
		privateformat : function (date,fmt) {
			var o = {
				"M+": date.getMonth() + 1,                      //月份
				"d+": date.getDate(),                           //日
				"H+": date.getHours(),                          //小时
				"m+": date.getMinutes(),                        //分
				"s+": date.getSeconds(),                        //秒
				"q+": Math.floor((date.getMonth() + 3) / 3),    //季度
				"S": date.getMilliseconds()                     //毫秒
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
			for (var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		},
		_parseDateValue: function(value,dateType,dateFormatType){
			// 转换的时间格式化
			var dateFormatValue = "";

			if(value){
				try {
					//可能会导致错误的代码
					value=value.replace(new RegExp("-","g"),"\/");
					var newValue = this.splicValue(value,dateFormatType);
					dateFormatValue = this.getDateFormatValue(value,newValue,dateFormatType);
					/*if("date" === dateType){
                       dateFormatValue = dateFmt.format(new Date(value),　{selector:'date',　datePattern:dateFormatType});
                    }else if("datetime" === dateType){
                        // 若显示格式包含时间，进行正常转换
                        if(dateFormatType.search("HH")!=-1){
                            dateFormatValue = dateFmt.format(new Date(value),　{selector:'date',　datePattern:dateFormatType});
                        }else{
                            // 否则只截取日期进行格式化
                            strValue = value.substring(0,10);
                            dateFormatValue = dateFmt.format(new Date(strValue),　{selector:'date',　datePattern:dateFormatType});
                        }
                    }else if("time" === dateType){
                        dateFormatValue = dateFmt.format(new Date(value),　{selector:'date',　datePattern:dateFormatType});
                        if(dateFormatValue.search("0")!=-1){
                            dateFormatValue = dateFormatValue.replace("0","");
                        }
                    }*/
				} catch (error) {
					//在错误发生时的处理
					console.log(error.message);
				}
			}
			return dateFormatValue;
		},


	});

	return claz;

});
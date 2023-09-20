/**
 * 视图里面的基本单元组件
 * 
 * 组件会包含左侧视图的绘画draw方法和右侧的属性设置
 * 
 * 注意：
 * 1、右侧视图的变更，需要手动更新数据，具体可以参考"sys/modeling/base/mobile/design/mode/default/statistics.js"
 * 	1.1、添加和删除元素，需要手动更新数据（updateData）
 * 2、继承的控件建议重写formatData，用于格式化数据
 * 3、继承的控件需要定义widgetKey
 * 4、继承的控件需要定义draw方法，即左侧预览图的绘制
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	var strutil = require('lui/util/str');
	var modelingLang = require("lang!sys-modeling-base");
	
	function lowerFirst(str){
		return str.charAt(0).toLowerCase() + str.substring(1);
	}
	
	var MobileBaseWidget = base.Component.extend({
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.warning = false;
			this.data = {};
			// 理论上每个组件都需要定义自己的数据模型
			// this.data = {titl:"ddd"};
			// this.widgetKey = "";
	    },
		
		startup : function($super,cfg){
			$super(cfg);
		},
		
		// 发布触发事件，面板进行刷新
		active : function(){
			topic.channel("modeling").publish("mobile.design.widget.active",{data:this.data, widget:this});
		},
		
		destroy : function($super,cfg){
			// 父组件需要清除当前组件
			if(this.parent){
				for (var i = 0; i < this.parent.children.length; i++){
					if (this.parent.children[i] === this) {
						this.parent.children.splice(i, 1);
						break;
					}
				}
			}
			$super(cfg);
		},
		
		getKeyData : function(){
			var keyData = {};
			if(this.widgetKey){
				var target = {};
				this.onlyValueKey(this.data, target);
				keyData[this.widgetKey] = target;
			}
			return keyData;
		},
		
		// 仅获取value的值
		onlyValueKey : function(source, target){
			for(var key in source){
				if(key === "value"){
					target[key] = source[key];
				}else if(typeof(source[key]) === 'object'){
					target[key] = {};
					this.onlyValueKey(source[key], target[key]);
				}
			}
		},
		
		extend : function(tmpData, data){
			var datas = $.extend(true, {},tmpData);
			if(data && data.attr){
				for(var key in datas.attr){
					if(data.attr[key]){
						$.extend(true, datas.attr[key], data.attr[key]);
					}
				}
			}
			return datas;
		},
		
		/**************** 数据域的校验 start *****************/ 
		validateData : function(){
			var pass = true;
			var attr = this.data.attr;
			for(var key in attr){
				if(attr[key].validate){
					var dataValue = attr[key].value;
					pass = this.doValidateDatas(dataValue, attr[key].validate);
				}
				if(!pass){
					console.log("【移动设计】"+ (this.widgetKey || "") +"的"+ key +"属性校验不通过!");
					break;
				}
			}
			return pass;
		},
		
		doValidateDatas : function(dataValue, validateInfo){
			var pass = true;
			// 如果数据域是数组，则遍历数据校验
			if(Object.prototype.toString.call(dataValue) === "[object Array]"){
				for(var j = 0;j < dataValue.length;j++){
					var elementValue = dataValue[j];
					pass = this.doValidateData(elementValue, validateInfo);
					if(!pass){
						break;
					}
				}
			}else if(typeof(dataValue) === "object"){
				pass = this.doValidateData(dataValue, validateInfo);
			}else if(typeof(dataValue) === "string"){
				pass = this.executeValidataData(null , dataValue, validateInfo);
			}else{
				console.log("【移动首页设计】未知的类型:" + dataValue);
			}
			return pass;
		},
		
		doValidateData : function(dataValue, validateInfo){
			var pass = true;
			if(typeof(validateInfo) === "object"){
				for(var field in validateInfo){
					pass = this.executeValidataData(field, dataValue[field], validateInfo[field]);
					if(!pass){
						break;
					}
				}
			}else if(typeof(validateInfo) === "string" && typeof(dataValue) === "string"){
				pass = this.executeValidataData(null, dataValue, validateInfo);
			}
			return pass;
		},
		
		executeValidataData : function(field, value, validateStr){
			var pass = true;
			// #141735新旧首页的数据结果不一样，防止用户直接对旧的首页配置保存做的直接让newlistView pass
			if (field ==="newlistView"&&value ==undefined){
				return pass;
			}
			if(validateStr){
				var validateFuns = validateStr.split(" ");
				for(var i = 0;i < validateFuns.length;i++){
					if(this[validateFuns[i] + "DataValidate"]){
						pass = this[validateFuns[i] + "DataValidate"](value);
					}
					if(!pass){
						console.log("【移动首页设计】"+ (this.widgetKey || "") +"的"+ field +"\""+ validateFuns[i] + "DataValidate\"方法校验不通过!");
						break;
					}
				}
			}
			return pass;
		},
		/**************** 数据域的校验 end *****************/
		
		validateWarning : function(){
			var pass = this.validateData();
			this.toggleWarning(!pass);
			return pass;
		},
		
		// 切换警告状态
		toggleWarning : function(isWarning){
			if(isWarning){
				this.element.addClass("warning");
				this.warning = true;
			}else{
				this.element.removeClass("warning");
				this.warning = false;
			}
		},
		
		// 清除异常状态
		clearAbnormalStatus : function(){
			this.element.removeClass("warning");
			this.element.removeClass("active");
			this.warning = false;
		},
		
		getSketchpad : function(){
			return this.parent.getSketchpad();
		},
		
		// 数据全更新，低性能
		updateData : function(isUpdateView){
			var area = this.getSketchpad().element;
			if(this.data && this.data.attr){
	    		var attr = this.data.attr;
	    		for(var key in attr){
	    			var attrInfo = attr[key] || {};
	    			var funName = lowerFirst(attrInfo.type || attrInfo.drawType || "string") + "GetValue";
	    	    	if(this[funName]){
	    	    		attrInfo.value = this[funName](key, attrInfo, area);
	    	    	}
	    		}
	    		this.setData(this.data, isUpdateView);
	    	}
		},
		
		// 设置初始化数据，控件值放置在widgetKey的值，data来源于外部
		setInitData : function(data){
			data = data || {};
			this.setData(data[this.widgetKey] || {});
		},
		
		// 设置控件的值
		setData : function(data, isUpdateView){
			data = data || {};
			this.data = this.formatData(data);
			isUpdateView = isUpdateView === true ? true : false;
			if(isUpdateView){
				// 更新完数据，重新画
				this.draw();
			}
		},
		
		// 格式化数据，继承的每个组件应该覆盖该方法
		formatData : function(data){
			return data;
		},
		
		// 把字符串数组转换为json格式的字符串
		transStrToJson : function(str){
			str = str || "{}";
			return JSON.parse(str);
		},
		
		/************** 校验函数 start *******************/
		// 每个校验器必须含有元素的校验和数据的校验
		requiredValidate : function(dom){
			var formElement = $(dom).closest(".content_item_form_element");
			if(!this.requiredDataValidate(dom.value)){
				var msg = modelingLang['modeling.title.cannot.null'];
				var title = $(dom).attr("title") || modelingLang['modeling.form.Content'];
				if (dom.value.indexOf("[{")==0){
					msg = modelingLang['modeling.penetration.incorrectly'];
				}
				msg = strutil.variableResolver(msg,{
					title : title
				});
				formElement.addClass("warning");
				formElement.find(".content_opt_info").html(msg);
				formElement.find(".content_opt").css("border-color","#FF4444");
			}else{
				formElement.removeClass("warning");
				formElement.find(".content_opt_info").html("");
				formElement.find(".content_opt").css("border-color"," #DFE3E9");
			}
		},
		
		requiredDataValidate : function(v){
			if(typeof(v) === "string"){
				if (v.indexOf("[{")==0){
				var arr =JSON.parse(v);
					for (var i=0;i<arr.length;i++){
						var list = arr[i];
						if (list.text=="" || list.value==""){
							return false;
						}
					}
				}
				return !((v == null) || v.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"") == ''|| (v.length == 0));				
			}else if(Object.prototype.toString.call(v) === "[object Array]"){
				if ( v.length==0){
					return false
				}else {
					for (var i=0;i<v.length;i++){
						var list = v[i];
						if (list.tab=="" || list.value==""){
							return false;
						}
					}
					return true;
				}
			}else if(typeof(v) === "object"){
				return !$.isEmptyObject(v);
			}else if(typeof(v) === "undefined"){
				return false;
			}else{
				return v !== null;
			}
		},
		/************** 校验函数 end *******************/
		
		/***************** 内置属性面板的表单元素绘制(draw和getValue方法成对出现) start ***************************/
		inputDraw : function(key, info, container){
			var uuId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			var inputHtml = "";
			inputHtml += "<div class='content_item_form_element content_input'>" +
					"<p class='content_body_title'>"+modelingLang['modelingAppMobile.docSubject']+"</p>" +
					"<div class='content_opt'>" +
					"<input name='"+ uuId + "_" + key +"' type='text' placeholder='"+modelingLang['modeling.please.enter']+"' " +
							"value='"+ info.value +"'" +
							"data-validate='"+ info.validate +"'" +
							"data-update-event='input'" +
							"data-updateview='true'></div>" +
							"<p class='content_opt_info'></p>" +
							"</div>";
			return $(inputHtml).appendTo(container);
		},
		
		inputGetValue : function(key, info, area){
			return area.find("[name*='"+ key +"']").val() || "";
		},
		//获取多语言资源
		getModelingLang :function (){
			return modelingLang;
		},
		
		/***************** 内置属性面板的表单元素绘制 end ***************************/

	});
	
	exports.MobileBaseWidget = MobileBaseWidget;
})
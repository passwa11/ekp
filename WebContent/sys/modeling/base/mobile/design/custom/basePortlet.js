/**
 * 移动首页模板的基类
 * 
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var BasePortlet = base.DataView.extend({

		initProps: function($super,cfg) {
			$super(cfg);
			this.viewContainer = $(".panel-portlet-main");
			this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
			this.isCount = false;
			this.type = cfg.type;
		},

		doRender: function($super, cfg){
			$super(cfg);
			if(this.parent.wgtPortlet == null){
				$(".panel-portlet-header").show();
				$(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));
			}
		},

		draw:function ($super){
			$super();
			$(".panel-portlet-header").show();
			$(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
			$(".panel-portlet-header").hide();
		},

		addEvent : function(){
			//标题事件
			this.textTitleEvent();
		},

		triggerActiveWgt:function() {
			$(".model-edit-view-bar").find("[resPanel-bar-mark='content']").trigger($.Event("click"));
			$(".panel-portlet-header").hide();
			var $currentPreviewWgt = this.parent.element.find("[data-id='"+this.randomName+"']");
			$currentPreviewWgt.addClass("selected").siblings().removeClass("selected");
			this.element.show().siblings().hide();
			//隐藏背景色弹框
			$(".sp-container").addClass("sp-hidden");
		},

		getKeyData : function(){
			var keyData = {};
			return keyData;
		},

		textTitleEvent : function(){
			var self = this;
			this.element.find("[name=fdTitle]").on('input propertychange', function(){
				self.setLeftShow();
			});
			this.element.find("[name=fdTitleIsHide]").on('click', function(){
				self.setLeftShow();
			});
			this.addValidateElements(this.element.find("[name=fdTitle]")[0],"required");
		},

		addValidateElements: function(element,validateName){
			if(this.parent.customValidation){
				this.parent.customValidation.addElements(element,validateName);
			}
		},

		removeValidateElements: function(element,validateName){
			if(this.parent.customValidation){
				this.parent.customValidation.removeElements(element,validateName,false);
			}
		},

		validateElement: function(element){
			if(this.parent.customValidation){
				this.parent.customValidation.validateElement(element);
			}
		},

		// 去除views多余的字段属性
		formatViews : function(views){
			var rs = [];
			for(var i = 0;i < views.length;i++){
				var view = views[i];
				rs.push({text : view.text, value : view.value});
			}
			return rs;
		},

		// 设置总计页签对应哪个页签
		updateCountBlock : function(views, value, uuId, area){
			views = views || [];
			if(views.length > 0){
				area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
				area.find("[name*='lvCollection']").val(JSON.stringify(views));
			}else{
				area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
			}
		},

		createCustomSelect : function(views,value, uuId){
			var text = "===请选择===";
			var lvCountHtml = "";
			lvCountHtml += "<div class='model-mask-panel-table-select' style='margin-left:0px'>";
			lvCountHtml += "<p class='model-mask-panel-table-select-val'></p>";
			lvCountHtml += "<div class='model-mask-panel-table-option'>";
			for(var i = 0;i < views.length;i++){
				var lvTab = views[i];
				lvCountHtml += "<div option-value='"+ lvTab.value +"'";
				if(lvTab.value === value){
					text = lvTab.text;
				}
				lvCountHtml += ">"+ lvTab.text +"</div>";
			}
			lvCountHtml += "</div>";

			lvCountHtml += "<input name='"+ uuId +"_countLv' data-update-event='change' type='hidden' value='"+ value +"' data-validate='countLvRequired'/>";

			lvCountHtml += "</div>";
			var $select = $(lvCountHtml);
			$select.find(".model-mask-panel-table-select-val").html(text);
			// 添加事件
			$select.on("click", function(event) {
				event.stopPropagation();
				$(this).toggleClass("active")
			});
			$select.find(".model-mask-panel-table-option div").on("click", function () {
				var $tableSelect = $(this).closest(".lvCountWrap");
				var $p = $tableSelect.find(".model-mask-panel-table-select-val");
				$p.html($(this).html());

				var selectVal = $(this).attr("option-value");
				$tableSelect.closest(".content_item_form_element").find("[name*='countLv']").val(selectVal).trigger($.Event("change"));
			});
			return $select;
		},

		// 把字符串数组转换为json格式的字符串
		transStrToJson : function(str){
			str = str || "{}";
			return JSON.parse(str);
		},

		// 格式化数据，继承的每个组件应该覆盖该方法
		formatData : function(data){
			return data;
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
		
		validate : function(){
			var pass = true;
			var self = this;
			if(this.element && this.parent.customValidation){
				this.element.find("[data-type='validate'],[data-type='hidden']").each(function(){
					if(!self.parent.customValidation.validateElement(this)){
						pass = false;
					}
				})
			}
			return pass;
		},

		destroy : function($super) {
			this.removeValidateElements(this.element.find("[name=fdTitle]")[0],"required");
			$super();
		}
		
	});
	
	exports.BasePortlet = BasePortlet;
})
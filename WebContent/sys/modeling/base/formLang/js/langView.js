/**
 * 
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		str = require('lui/util/str'),
		dialog = require('lui/dialog'),
		source = require('lui/data/source'),
		render = require('lui/view/render'),
		topic = require('lui/topic');
	var modelingLang = require("lang!sys-modeling-base");
	var LangView = base.DataView.extend({
		
		initProps: function($super, cfg) {
			$super(cfg);
		},
		
		startup : function($super, cfg) {
			if (!this.render) {
				this.setRender(new render.Template({
					src : "/sys/modeling/base/formLang/js/langViewRender.html#",
					parent : this
				}));
				this.render.startup();
			}
			if (!this.source) {
				this.setSource(new source.Static({
					datas : this.config.vars.storedDatas,
					parent : this
				}));
				this.source.startup();
			}
			$super(cfg);
		},
		
		draw: function() {
			if(this.isDrawed)
				return;
			this.load();
			this.isDrawed = true;
		},
		
		show : function(cb) {
			this.element.fadeIn("normal", cb);
		},
		
		hide : function(){
			this.element.hide();
		},
		
		doRender : function($super, cfg){
			$super(cfg);
		},
		
		collectVal2KeyData : function(keyData){
			var self = this;
			this.element.find(".langFormElement").each(function(){
				var controlId = $(this).attr("data-control-id");
				var optionKey = $(this).attr("data-control-option");
				var optionIndex = $(this).attr("data-control-option-index");
				for(var i = 0;i < keyData.length;i++){
					var controlInfo = keyData[i];
					if(controlInfo.c_id === controlId){
						// 某些情况系会存在没有其他语言字段，比如原本是没有开启多语言，后面才开启
						if(!controlInfo["c_option"][optionKey][self.config.vars.key]){
							controlInfo["c_option"][optionKey][self.config.vars.key] = [];
						}
						controlInfo["c_option"][optionKey][self.config.vars.key][optionIndex] = $(this).val().replace(/\"/g,'&quot;');
						break;
					}
				}
			});
			return keyData;
		},
		getModelingLang :function (){
			return modelingLang;
		}
	});
	
	exports.LangView = LangView;
		
})
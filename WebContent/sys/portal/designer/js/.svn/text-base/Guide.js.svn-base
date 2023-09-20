define(function(require, exports, module) {	
	var $ = require("lui/jquery");
	var lang = require('lang!sys-portal');
	var Base = require("./Base");
	var Util = require("./Util");
	
	var GuideDesigner = Base.extend({
		initialize : function($super,config) {
			$super(config);
		},
		startup : function(){
			var self = this;
			this.guideEditor = (function(){
				if(self.element.children(".guideEditor").length >0){					 
					return self.element.children(".guideEditor"); 
				}else{
					var x = $("<div class='guideEditor'></div>"),
						guideElement = $('<a class="guideEditorElement"/>'),
						guideIcon = $('<i class="icon icon-affix"></i>').appendTo(guideElement),
						guideText = $('<span class="lui_portal_guide_text"/>').text(lang['sysPortalGuide.guide.setting']).appendTo(guideElement);
					guideElement.appendTo(x);
					self.element.append(x);
					return x;
				}
			})();
			this.fdGuide = (function(){
				if(self.element.children("[name='fdGuide']").length >0){					 
					return self.element.children("[name='fdGuide']"); 
				}else{
					var x = $("<input type='hidden' name='fdGuide'/>");
					self.element.append(x);
					return x;
				}
			})();
			this.fdGuideCfg = (function(){
				if(self.element.children("[name='fdGuideCfg']").length >0){					 
					return self.element.children("[name='fdGuideCfg']"); 
				}else{
					var x = $("<input type='hidden' name='fdGuideCfg' />");
					self.element.append(x);
					return x;
				}
			})();
			this.guideEditor.click(function(){
				var param = {};
				param.fdGuide = $(self.fdGuide).val();
				param.fdGuideCfg = $(self.fdGuideCfg).val();
				Util.showDialog(""+ lang['sysPortalGuide.guide.setting'] +"","/sys/portal/designer/jsp/configguide.jsp?",function(value){
					if(value==null)
						return;
					$(self.fdGuide).val(value.fdGuide);
					$(self.fdGuideCfg).val(value.fdGuideCfg);
				},500,300).dialogParameter = param;
			});
		}
	});
	
	module.exports = GuideDesigner;
	
});	
	
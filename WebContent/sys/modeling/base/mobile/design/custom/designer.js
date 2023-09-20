/**
 * 移动首页布局设计器
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var customPanel = require('sys/modeling/base/mobile/design/custom/customPanel');

	var Designer = base.Container.extend({
		
		initProps: function($super,cfg) {
			$super(cfg);
			this.data = cfg.data;
			this.mode = cfg.mode;
		},
		
		startup : function($super,cfg){
			$super(cfg);
			this.renderPanel();
			//右侧导航
			$(".model-edit-view-bar").find("div").on("click", function (e) {
				e.stopPropagation();
				$(".model-edit-view-bar").find("div").removeClass("barActive");
				var $t = $(this);
				$t.addClass("barActive");
				var mark = $t.attr("resPanel-bar-mark");
				$(".resPanel-bar-content").hide();
				$("[resPanel-bar-content='" + mark + "']").show();
			});
			$(".model-edit-view-bar").find("[resPanel-bar-mark='basic']").trigger($.Event("click"));
			$("[name=docSubject]").on("input",function(e) {
			    $(".model-mind-map-title.listviewName").text($(this).val());
			    $(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoTitleContent").text($(this).val());
			});
			//背景色
			$("[name=fdBackground]").on("click", function (e) {
				e.stopPropagation();
				if($(this).val() == "1"){
					$(".modelAppSpaceWidgetDemoContent .modelAppSpaceWidgetDemoBackground").css("background","url(../mobile/design/images/default_bg.jpg) no-repeat").css("background-size","100% 165px");
				}else if ($(this).val() == "2") {
					$(".modelAppSpaceWidgetDemoContent .modelAppSpaceWidgetDemoBackground").css("background","url(../mobile/design/images/collapse-bg.png) no-repeat").css("background-size","100% 165px");
				}else{
					$(".modelAppSpaceWidgetDemoContent .modelAppSpaceWidgetDemoBackground").css("background","#fff");
				}
			});

		},

		renderPanel:function() {
		  	this.customPanel = new customPanel.CustomPanel({parent:this});
			this.customPanel.startup();
			this.customPanel.draw();
		},
		
		getKeyData : function(){
			var keyData = {};
			keyData.fdBackground = $("[name='fdBackground']:checked").val();
			keyData.fdPortlets = this.customPanel.getKeyData();
			return keyData;
		},
		
		validate : function(){
			return this.customPanel.validate();
		},

		initByStoreData:function() {
			var self = this;
			var fdBackground = self.data.fdBackground || "0";
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoTitleContent").text($(".model-mind-map-title.listviewName").text());
			$("[name='fdBackground']").each(function() {
				if($(this).val() === fdBackground){
					$(this).attr("checked",true);
					$(this).trigger($.Event("click"));
				}else{
					$(this).removeAttr("checked");
				}
			})

			this.customPanel.initByStoreData(this.data.fdPortlets);
		}
		
	});
	
	exports.Designer = Designer;
})